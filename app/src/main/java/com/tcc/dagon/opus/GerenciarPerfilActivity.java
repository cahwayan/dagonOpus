package com.tcc.dagon.opus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GerenciarPerfilActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GerenciarPerfilActivity.class.getSimpleName();

    // INICIO TESTE

    private static int RESULT_LOAD_IMAGE = 1;

    static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 1;

    // FIM TESTE


    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private Button btnAprender,btnAlterarSenha,btnLogout;

    private TextView txtNome;

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

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // OBJETO BANCO PARA LER O PROGRESSO
        DB_PROGRESSO = new GerenciadorBanco(this);

        // OBJETO DE CONEXÃO COM API GOOGLE
        googleBuilder();

        // VIEWS DA CLASSE
        accessViews();

        // CARREGANDO CLICK LISTENERS
        clickListeners();


        // CARREGA PROGRESSO DA BARRA
        carregarProgresso();

        // VERIFICA SE O DISPOSITIVO POSSUI CÂMERA
        /*verificarCamera();*/



        // CONTEXT PARA CHAMADA VOLLEY
        requestQueue = Volley.newRequestQueue(getApplicationContext());


        // MÉTODO JSON
        /*carregaLink();*/




    }

    private void accessViews() {
        txtNome = (TextView) findViewById(R.id.txtNome);

        if(lerNomeUsuario().equals("default")) {
            txtNome.setText("Nome");
        } else {
            txtNome.setText(lerNomeUsuario());
        }


        btnAprender = (Button) findViewById(R.id.btnAprender);
        btnAlterarSenha = (Button) findViewById(R.id.btnAlterar);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        barraGeral = (RoundCornerProgressBar) findViewById(R.id.barraGeral);
        foto = (ImageView) findViewById(R.id.fotoPerfil);

        Log.d(TAG, "SharedPref caminho: " + lerCaminhoFoto());

        if(foto != null){
            if(lerCaminhoFoto() == "default") {
                foto.setImageResource(R.drawable.icon_foto);
            } else {
                try {
                    foto.setImageBitmap(BitmapFactory.decodeFile(lerCaminhoFoto()));
                } catch(NullPointerException e) {
                    foto.setImageResource(R.drawable.icon_foto);
                }

            }
        }


    }

    private void carregarProgresso() {
        barraGeral.setProgress(Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(1) +
                DB_PROGRESSO.verificaProgressoEtapa(2) +
                DB_PROGRESSO.verificaProgressoEtapa(3) +
                DB_PROGRESSO.verificaProgressoEtapa(4) +
                DB_PROGRESSO.verificaProgressoEtapa(5) +
                DB_PROGRESSO.verificaProgressoEtapa(6) +
                DB_PROGRESSO.verificaProgressoEtapa(7) +
                DB_PROGRESSO.verificaProgressoEtapa(8))) );
    }

    private void clickListeners() {
        btnAprender.setOnClickListener(btnClickListner);
        btnAlterarSenha.setOnClickListener(btnClickListner);
        btnLogout.setOnClickListener(btnClickListner);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermissions();

            }
        });

    }

    // MÉTODO QUE CHECA PERMISSÕES
    private void checkPermissions() {
        // SE NÃO TIVER PERMISSÃO
        if (ContextCompat.checkSelfPermission(GerenciarPerfilActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // PEDIR PERMISSÕES
            getPermissions();

        }else {
            Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }

    }

    // MÉTODO QUE PEGA A PERMISSÃO DO USUÁRIO
    private void getPermissions() {
        int MY_PERMISSIONS_REQUEST_READ_STORAGE = 1;

        // DEVEMOS MOSTRAR UMA EXPLICAÇÃO?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // MOSTRA UMA EXPLICAÇÃO PARA O USUÁRIO SOBRE O PORQUE DA NECESSIDADE
            // DA PERMISSÃO
        } else {
            // CASO NÃO PRECISE DE EXPLICAÇÕES, VAMOS PEDIR A PERMISSÃO
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_STORAGE);

            // MY_PERMISSIONS_REQUEST_GET_ACCOUNTS É UMA CONSTANTE DO APLICATIVO
        }
    }

    // MÉTODO QUE LIDA COM O RESULTADO DA PERMISSÃO DO USUÁRIO
    // SE ELE CONCEDEU OU NÃO
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // PERMISSAO GARANTIDA
                    Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);

                } else {
                    // PERMISSAO NEGADA
                    Toast.makeText(getApplicationContext(), "Você precisa conceder autorização para utilizar esse recurso", Toast.LENGTH_LONG).show();

                }
                return;
            }

        }
    }

    // MÉTODO QUE LIDA COM A FOTO ESCOLHIDA PELO USUARIO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            if(cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) findViewById(R.id.fotoPerfil);

                if(imageView != null ){
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    gravarCaminhoFoto(picturePath);
                }
            }

        }
    }

    private void googleBuilder() {
        // Configura um objeto que contém o perfil básico do usuário: Perfil, Foto, E-Mail e ID Público
        // Perfil e ID estão incluídos no DEFAULT_SIGN_IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Constrói um objeto de conexão que tem acesso à API Google e as características do objeto GSO
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* Essa atividade, precisa ser um fragmento */, this /* Listener de erro de conexão */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
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
    }


    View.OnClickListener btnClickListner = new View.OnClickListener()
    {
        @Override
        public void onClick( View v )
        {
            if( btnAprender.getId() == v.getId() )
            {
                finish();

            }
            else if( btnLogout.getId() == v.getId() )
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                writeFlag(false);
                signOut();
                finish();
            }
            else if( btnAlterarSenha.getId() == v.getId() )
            {
                Intent i = new Intent(GerenciarPerfilActivity.this, AlterarSenhaActivity.class);
                i.putExtra("emailUsuario", lerEmail());
                startActivity(i);
                finish();
            }

        }

    };

    // MODIFICAR FLAG PARA LOGOUT
    public void writeFlag(boolean flag) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", flag);
        editor.apply();
    }

    public void gravarNomeUsuario(String nome) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nomeUsuario", nome);
        editor.apply();
    }

    public String lerNomeUsuario() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("nomeUsuario", "default");
    }



    public void gravarCaminhoFoto(String caminho) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("caminhoFotoPerfil", caminho);
        editor.apply();
    }

    public String lerCaminhoFoto() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("caminhoFotoPerfil", "default");
    }

    public String lerEmail() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("emailUsuario", "default");
    }

    // SIGN OUT GOOGLE
    private void signOut() {
        if(googleApiClient.isConnected()) {
            googleApiClient.clearDefaultAccountAndReconnect();
            googleApiClient.disconnect();
            googleApiClient.connect();
            gravarCaminhoFoto(null);
            gravarNomeUsuario(null);
            writeFlag(false);
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}