package com.tcc.dagon.opus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

    RequestQueue requestQueue;
    ImageView foto;

    String URLMOSTRA = "http://dagonopus.esy.es/phpAndroid/mostrarFoto.php?EMAIL_MOSTRA=";
    String URLFIM;
    String caminho;
    String endFoto = "http://dagonopus.esy.es/phpAndroid/uploads/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_perfil);

        txtNome = (TextView)findViewById(R.id.txtNome);




        btnAprender = (Button)findViewById(R.id.btnAprender);
        btnAlterarSenha = (Button)findViewById(R.id.btnAlterar);
        btnLogout = (Button)findViewById(R.id.btnLogout);

        btnAprender.setOnClickListener(btnClickListner);
        btnAlterarSenha.setOnClickListener(btnClickListner);
        btnLogout.setOnClickListener(btnClickListner);


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        // Pegando informações da activity anterior e passando a activity upload
        Intent i = getIntent();

        email = i.getStringExtra("id");
        URLFIM = URLMOSTRA+email;

        i.putExtra("y",email);



        foto = (ImageView) findViewById(R.id.fotoPerfil);

        carregaLink();

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
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
        i.putExtra("y", email);
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
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
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
                  /*  JSONArray nome = response.getJSONArray("nome");
                    for(int y = 0; y< nome.length();y++){
                        JSONObject nomes = nome.getJSONObject(y);
                        nomeUser = nomes.getString("NOME_USUARIO");
                        txtNome.setText(nomeUser);

                    }*/
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

            }
            else if( btnLogout.getId() == v.getId() )
            {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
            else if( btnAlterarSenha.getId() == v.getId() )
            {


            }

        }

    };

}