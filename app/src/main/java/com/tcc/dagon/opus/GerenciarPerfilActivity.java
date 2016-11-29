package com.tcc.dagon.opus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import static com.tcc.dagon.opus.MainActivity.googleApiClient;
import static java.lang.String.valueOf;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GerenciarPerfilActivity extends Activity {

    private static final String TAG = GerenciarPerfilActivity.class.getSimpleName();


    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private Button btnAprender,btnAlterarSenha,btnLogout;

    private String imgfim;

    private String email,nomeUser;

    private TextView txtNome;

    private Handler handler = new Handler();

    private NovaJanelaAlerta alertaOperacaoFinalizada;

    private String imageUrl;

    private ProgressBar pbProfile;

    private RoundCornerProgressBar barraGeral;

    private GerenciadorBanco DB_PROGRESSO;

    RequestQueue requestQueue;
    Context context = this;
    ImageView foto;

    String URLMOSTRA = "http://dagonopus.esy.es/phpAndroid/mostrarFoto.php?EMAIL_MOSTRA=";
    String URLFIM;
    String caminho;
    String endFoto = "http://dagonopus.esy.es/phpAndroid/uploads/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_perfil);
        DB_PROGRESSO = new GerenciadorBanco(this);
        txtNome = (TextView) findViewById(R.id.txtNome);

        btnAprender = (Button) findViewById(R.id.btnAprender);
        btnAlterarSenha = (Button) findViewById(R.id.btnAlterar);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        barraGeral = (RoundCornerProgressBar) findViewById(R.id.barraGeral);
        barraGeral.setProgress(Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(1) +
                                                        DB_PROGRESSO.verificaProgressoEtapa(2) +
                                                        DB_PROGRESSO.verificaProgressoEtapa(3) +
                                                        DB_PROGRESSO.verificaProgressoEtapa(4) +
                                                        DB_PROGRESSO.verificaProgressoEtapa(5) +
                                                        DB_PROGRESSO.verificaProgressoEtapa(6) +
                                                        DB_PROGRESSO.verificaProgressoEtapa(7) +
                                                        DB_PROGRESSO.verificaProgressoEtapa(8))) );

        btnAprender.setOnClickListener(btnClickListner);
        btnAlterarSenha.setOnClickListener(btnClickListner);
        btnLogout.setOnClickListener(btnClickListner);

        foto = (ImageView) findViewById(R.id.fotoPerfil);

        alertaOperacaoFinalizada = new NovaJanelaAlerta(this);

        if (verificarFotoTrocada()) {
            File imgFile = new File(lerCaminhoFoto());
            Bitmap arquivoFoto = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            foto.setImageBitmap(arquivoFoto);
        } else if(googleApiClient.isConnected()) {
            try {
                Person p = Plus.PeopleApi.getCurrentPerson(googleApiClient);
                // Abre a tela de login após cadastro
                //carrega icone de imagem do perfil do google
                Log.i("Script", "IMG before: "+imageUrl);
                imageUrl = imageUrl.substring(0, imageUrl.length() - 2)+"200";
                Log.i("Script", "IMG after: "+imageUrl);
                loadImage(foto, pbProfile, imageUrl);
            } catch(NullPointerException e) {
                foto.setImageResource(R.drawable.icon_foto);
            }

        }else {
            foto.setImageResource(R.drawable.icon_foto);
        }

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Pegando informações da activity anterior e passando a activity upload
        Intent i = getIntent();

        email = i.getStringExtra("emailUsuario");
        email = lerEmail("emailUsuario");

        URLFIM = URLMOSTRA+email;

        //i.putExtra("emailUsuario", email);

        carregaLink();

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(GerenciarPerfilActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    getPermissions();
                }

                if(ContextCompat.checkSelfPermission(GerenciarPerfilActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    captureImage();
                } else {
                    alertaOperacaoFinalizada.alertDialogBloqueado("Aviso", "Foto cancelada");
                }
            }
        });


        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Desculpe ,seu dispositivo não tem suporte",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        if(!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }

        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLink();
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri = savedInstanceState.getParcelable("file_uri");
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                launchUploadActivity(true);


            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(),
                        "Cancelado !", Toast.LENGTH_SHORT)
                        .show();

            } else {
                Toast.makeText(getApplicationContext(),
                        "Ocorreu um erro ao capturar a imagem", Toast.LENGTH_SHORT)
                        .show();
            }

        }
       }

    private void launchUploadActivity(boolean isImage){
        Intent i = new Intent(GerenciarPerfilActivity.this, UploadActivity.class);
        i.putExtra("filePath", fileUri.getPath());
        i.putExtra("isImage", isImage);
        i.putExtra("emailUsuario", email);
        startActivity(i);
    }


    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                StringsBanco.IMAGE_DIRECTORY_NAME);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Falha ao criar o arquivo "
                        + StringsBanco.IMAGE_DIRECTORY_NAME + " directory");
                //return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaFile = null;

        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            //return null;
        }

        return mediaFile;
    }

    public void loadImg(){

        imgfim = endFoto + caminho;
        new Thread(){
            public void run(){
                Bitmap img = null;

                try{
                    URL url = new URL(imgfim);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream input = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(input);
                }
                catch(IOException e){}

                final Bitmap imgAux = img;
                handler.post(new Runnable(){
                    public void run(){
                        ImageView iv = new ImageView(getBaseContext());
                        foto.setImageBitmap(imgAux);
                    }
                });
            }
        }.start();
    }

    public void carregaLink() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URLFIM, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response.toString());
                try {
                    JSONArray students = response.getJSONArray("students");
                 for (int i = 0; i < students.length(); i++) {
                        JSONObject student = students.getJSONObject(i);

                        caminho = student.getString("END_FOTO");
                        nomeUser = student.getString("NOME_USUARIO");
                        txtNome.setText(nomeUser);
                            if(caminho.equals("null")){

                            }else {
                                loadImg();
                            }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());

            }
        }){

        };

        requestQueue.add(jsonObjectRequest);
    }

    View.OnClickListener btnClickListner = new View.OnClickListener()
    {
        @Override
        public void onClick( View v )
        {
            if( btnAprender.getId() == v.getId() )
            {
                Intent intent = new Intent(GerenciarPerfilActivity.this, AprenderActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();

            }
            else if( btnLogout.getId() == v.getId() )
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                writeFlag(false);
                fotoTrocada(false);
                signOut();
                finish();
            }
            else if( btnAlterarSenha.getId() == v.getId() )
            {
                Intent i = new Intent(GerenciarPerfilActivity.this, AlterarSenhaActivity.class);
                i.putExtra("emailUsuario", email);
                startActivity(i);
                finish();



            }

        }

    };

    // MÉTODO QUE VERIFICA A PERMISSÃO DO USUÁRIO
    private void getPermissions() {
        int permissaoConta = ContextCompat.checkSelfPermission(GerenciarPerfilActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 0;
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.GET_ACCOUNTS)) {

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
        }
    }

    // MODIFICAR FLAG PARA LOGOUT
    public void writeFlag(boolean flag) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", flag);
        editor.apply();
    }

    public String lerEmail(String emailUsuario) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("emailUsuario", emailUsuario);
    }

    public void fotoTrocada(boolean flag) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("fotoTrocada", flag);
        editor.apply();
    }

    public boolean verificarFotoTrocada() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("fotoTrocada", false);
    }

    public String lerCaminhoFoto() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("caminhoFotoPerfil", "default");
    }

    // SIGN OUT GOOGLE
    private void signOut() {
        if(googleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(googleApiClient);
            googleApiClient.disconnect();
            //googleApiClient.connect();
        }

    }

    //FUNÇAO PARA CARREGAR IMAGEM COM PROGRESSBAR
    public void loadImage(final ImageView ivImg, final ProgressBar pbImg, final String urlImg){
        RequestQueue rq = Volley.newRequestQueue(GerenciarPerfilActivity.this);
        ImageLoader il = new ImageLoader(rq, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                pbImg.setVisibility(View.GONE);
            }
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        pbImg.setVisibility(View.VISIBLE);
        il.get(urlImg, il.getImageListener(ivImg, pbImg.getId(), pbImg.getId()));
    }



}