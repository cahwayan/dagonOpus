package com.tcc.dagon.opus.ui.telasatransferir.aprender.menulateral;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.QTD_MODULOS;
import static java.lang.String.valueOf;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.tcc.dagon.opus.ui.aprender.ModuloCurso;
import com.tcc.dagon.opus.ui.usuario.LoginActivity_;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.usuario.AlterarSenhaActivity;
import com.tcc.dagon.opus.ui.usuario.LoginActivity;
import com.tcc.dagon.opus.utils.OnOffClickListener;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;

import com.tcc.dagon.opus.ui.aprender.AprenderActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_gerenciar_perfil)
public class GerenciarPerfilActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GerenciarPerfilActivity.class.getSimpleName();

    @ViewById Button btnAprender;
    @ViewById Button btnAlterar;
    @ViewById Button btnLogout;
    @ViewById RoundCornerProgressBar barraGeral;
    @ViewById TextView txtNome;
    @ViewById ImageView fotoPerfil;

    @ViewById TextView textPontos;
    @ViewById TextView textConquistas;
    @ViewById TextView textTempo;


    private GerenciadorSharedPreferences preferencias;
    private RequestQueue requestQueue;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        if(!LoginActivity.googleApiClient.isConnected()) {
            LoginActivity.googleApiClient.connect();
        }

        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @AfterViews
    protected void init() {
        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferencias  = new GerenciadorSharedPreferences(this);

        // OBJETO DE CONEXÃO COM API GOOGLE
        googleBuilder();

        getNomeUsuario();
        loadFotoPerfil();
        loadOnClickListeners();
        loadProgressBar();
        loadPontuacaoUsuario();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        Long tempoEstudo = Long.valueOf(preferencias.getTempoEstudo());

        if(tempoEstudo > 0) {
             tempoEstudo /= 60000;
        }

        String stringTempoEstudo = String.valueOf(tempoEstudo) + " min.";

        textTempo.setText(stringTempoEstudo);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), AprenderActivity_.class));
        finish();
    }

    private void getNomeUsuario() {
        if(preferencias.getNomeUsuario().equals("default")) {
            txtNome.setText("Nome");
        } else {
            txtNome.setText(preferencias.getNomeUsuario());
        }
    }

    private void loadPontuacaoUsuario() {

        int pontos = 0;

        for(int i = 0; i < QTD_MODULOS; i++) {
            pontos += preferencias.getPontos(i);
        }

        if(pontos > 0) {
            textPontos.setText(String.valueOf(pontos));
        } else {
            textPontos.setText("0");
        }
    }

    private void loadFotoPerfil() {
        /* DEBUG, PARA VERIFICAR O CAMINHO DA FOTO SALVA
        Log.d(TAG, "SharedPref caminho: " + preferenceManager.lerFlagString(NomePreferencia.caminhoFoto));*/

        if(preferencias.getCaminhoFoto().equals("default")) {
            fotoPerfil.setImageResource(R.drawable.icon_foto);
        } else {
            try {
                fotoPerfil.setImageBitmap(BitmapFactory.decodeFile(preferencias.getCaminhoFoto()));
            } catch(NullPointerException e) {
                fotoPerfil.setImageResource(R.drawable.icon_foto);
            }
        }
    }

    private void loadProgressBar() {
        int progressoGeral = 0;
        for(int i = 0; i < QTD_MODULOS; i++) {
            progressoGeral += preferencias.getProgressoEtapa(i);
        }

        barraGeral.setProgress(Float.parseFloat(valueOf(progressoGeral) ));
    }

    private void loadOnClickListeners() {

        OnOffClickListener btnClickListener = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                if( btnAprender.getId() == v.getId() )
                {
                    Intent i = new Intent(getApplicationContext(), AprenderActivity_.class);
                    startActivity(i);
                    finish();

                }
                else if( btnLogout.getId() == v.getId() )
                {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity_.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    preferencias.setIsLogin(false);
                    signOut();
                    finish();
                }
                else if( btnAlterar.getId() == v.getId() )
                {
                    Intent i = new Intent(GerenciarPerfilActivity.this, AlterarSenhaActivity.class);
                    i.putExtra("emailUsuario", preferencias.getEmailUsuario());
                    startActivity(i);
                }
            }
        };

        btnAprender.setOnClickListener(btnClickListener);
        btnAlterar.setOnClickListener(btnClickListener);
        btnLogout.setOnClickListener(btnClickListener);

        fotoPerfil.setOnClickListener(new View.OnClickListener() {
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
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // PERMISSAO GARANTIDA
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                } else {
                    // PERMISSAO NEGADA
                    Toast.makeText(getApplicationContext(), "Você precisa conceder autorização para utilizar esse recurso", Toast.LENGTH_LONG).show();
                }
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
                    preferencias.setCaminhoFoto(picturePath);
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
        LoginActivity.googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* Essa atividade, precisa ser um fragmento */, this /* Listener de erro de conexão */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    // SIGN OUT GOOGLE
    private void signOut() {
        if(LoginActivity.googleApiClient.isConnected()) {
            LoginActivity.googleApiClient.clearDefaultAccountAndReconnect();
            LoginActivity.googleApiClient.disconnect();
            LoginActivity.googleApiClient.connect();
            preferencias.setCaminhoFoto(null);
            preferencias.setNomeUsuario(null);
            preferencias.setIsLogin(false);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {

    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), AprenderActivity_.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}