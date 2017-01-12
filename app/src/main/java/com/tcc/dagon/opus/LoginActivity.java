package com.tcc.dagon.opus;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.tcc.dagon.opus.Activities.AppCompatActivity.Aprender.AprenderActivity_;
import com.tcc.dagon.opus.PerfilUsuario.RecuperarSenhaActivity;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;
import com.tcc.dagon.opus.utils.VerificarConexao;
import com.tcc.dagon.opus.utils.VolleyRequest;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import static android.content.ContentValues.TAG;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {

    /* INÍCIO ATRIBUTOS */

    /*TOKEN GOOGLE*/
    protected static final int SIGN_IN_CODE = 56465;

    /*API*/
    public static GoogleApiClient googleApiClient;

    /* CONNECTION RESULT */
    protected ConnectionResult connectionResult;

    protected boolean isConsentScreenOpened, isSignInButtonClicked;

    /* VIEWS */
    @ViewById protected EditText textEmail;
    @ViewById protected EditText textSenha;
    @ViewById protected Button btnLogar;
    @ViewById protected SignInButton btnLogarComGoogle;
    @ViewById protected Button btnAlterarSenha;
    @ViewById protected TextView btnCriarConta;

    protected GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);

    /* Objeto que pega as infos do usuário*/
    GoogleSignInAccount acct;

    /* Resultado da permissão */
    final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 1;

    /* VARIÁVEIS GOOGLE */

    public String name;

    /*STRINGS DO EMAIL E SENHA*/
    protected String sEmail,
                     sSenha;

    /* Context */
    protected Context context = this;

    /* INSTANCIAÇÃO DE OBJETOS */
    protected VolleyRequest volleyRequest;

    /* FIM ATRIBUTOS */

    /*MÉTODOS DE CICLO DE VIDA DO APP*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DON'T DO THIS !! It will throw a NullPointerException, myTextView is not set yet.
        // myTextView.setText("Date: " + new Date());
    }

    @Override
    protected void onStop(){
        super.onStop();

        if(googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    /*FIM DOS DICLOS DE VIDA DO APP*/

    /* MÉTODOS DE CICLO DE VIDA DO OBJETO DE CONEXÃO GOOGLE*/

    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInButtonClicked = false;
        getDataProfile();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if(!result.hasResolution()){
            return;
        }

        if(!isConsentScreenOpened){
            connectionResult = result;
            if(isSignInButtonClicked){
                signInGoogle();
            }
        }
    }

    /*FIM MÉTODOS DE CICLO DE VIDA DO OBJETO DE CONEXÃO GOOGLE*/

    /*MÉTODO DE INICIALIZAÇÃO DE COMPONENTES*/

    @AfterViews
    protected void init() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // CRIAÇÃO DO OBJETO DE CONEXÃO GOOGLE
        googleBuilder();

        volleyRequest = new VolleyRequest(this);

    }

    /*FIM INICIALIZAÇÃO COMPONENTES*/

    /* CLICK LISTENERS */

    @Click
    protected void btnLogar() {

        sEmail    = textEmail.getText().toString().trim();
        sSenha = textSenha.getText().toString().trim();

        // VERIFICA SE OS CAMPOS ESTÃO VAZIOS E INVOCA O TECLADO + FOCO CASO ESTEJAM
        if(VerificarConexao.verificarConexao()) {
            if(verificarCredenciais(sEmail, sSenha)) {
                if(volleyRequest.requestLogar(sEmail, sSenha)) {
                    startLogin();
                }

            }

        } else {
            Toast.makeText(getApplicationContext(), "Sem conexão", Toast.LENGTH_SHORT).show();
        }

    }



    @Click
    protected void btnLogarComGoogle() {
        if(VerificarConexao.verificarConexao()) {
            if(!googleApiClient.isConnecting()){
                verificarPermissaoGoogleLogin();
            }
        } else {
            Toast.makeText(context, "Por favor, verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show();
        }
    }

    @Click
    protected void btnAlterarSenha() {
        Intent intent = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
        startActivity(intent);
    }

    @Click
    protected void btnCriarConta() {
        btnCriarConta.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
        startActivity(new Intent(getApplicationContext(), CadastroActivity_.class));
    }

    /* FIM CLICK LISTENERS */

    /* VERIFICAÇÃO CONSISTÊNCIA CREDENCIAIS */
    protected boolean verificarCredenciais(String sEmail, String sSenha) {

        /* VERIFICAÇÃO CAMPO EMAIL VAZIO */
        if( sEmail.matches("") ) {

            Toast.makeText(getApplicationContext(), "Campo email vazio", Toast.LENGTH_SHORT).show();
            textEmail.requestFocus();

            /* INVOCANDO TECLADO CASO CREDENCIAIS ESTEJAM ERRADAS */
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

            return false;

        /* VERIFICAÇÃO CAMPO SENHA VAZIO */
        } else if ( sSenha.matches("") ) {

            Toast.makeText(getApplicationContext(), "Campo senha vazio", Toast.LENGTH_SHORT).show();
            textSenha.requestFocus();

            /* INVOCANDO TECLADO CASO CREDENCIAIS ESTEJAM ERRADAS */
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

            return false;

        /* VERIFICAR CREDENCIAIS NO BANCO*/
        } else {

            return true;

        }
    }

    /* GOOGLE BUILDER */

    protected void googleBuilder() {
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

    /*FIM GOOGLE BUILDER*/

    // VERIFICA A PERMISSÃO ATUAL E PEDE PRO USUÁRIO CASO NÃO ESTEJA PERMITIDO
    protected void verificarPermissaoGoogleLogin() {
        // CASO A PERMISSÃO ATUAL SEJA DIFERENTE DE PERMITIDA
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            // PEDIR PERMISSÕES
            pedirPermissaoGoogleLogin();
        } else {
            // SE AS PERMISSÕES JÁ FORAM GARANTIDAS, FAZER O SIGNIN
            signInGoogle();
        }
    }

    // PEDE EFETIVAMENTE A PERMISSÃO DO USUÁRIO
    protected void pedirPermissaoGoogleLogin() {
        int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 1;
        // DEVEMOS MOSTRAR UMA EXPLICAÇÃO?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.GET_ACCOUNTS)) {
            // MOSTRA UMA EXPLICAÇÃO PARA O USUÁRIO SOBRE O PORQUE DA NECESSIDADE
            // DA PERMISSÃO
        } else {
            // CASO NÃO PRECISE DE EXPLICAÇÕES, VAMOS PEDIR A PERMISSÃO
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
            // MY_PERMISSIONS_REQUEST_GET_ACCOUNTS É UMA CONSTANTE DO APLICATIVO
        }
    }

    /*LIDA COM O RESULTADO DA PERMISSÃO DO USUÁRIO, SE ELE CONCEDEU OU NÃO*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String  permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GET_ACCOUNTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    signInGoogle();

                } else {

                    Toast.makeText(getApplicationContext(), "Permissão recusada. Seu perfil ficará incompleto.",Toast.LENGTH_LONG).show();
                    signInGoogle();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /*REALIZA O SIGN IN COM A CONTA GOOGLE*/
    protected void signInGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    // PEGA O RESULTADO DO LOGIN DO USUÁRIO
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // RESULTADO DO INTENT DE LOGIN
        if (requestCode == SIGN_IN_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    // MÉTODO QUE LIDA COM O SUCESSO OU A FALHA NO LOGIN
    protected void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess() + result);
        if (result.isSuccess()) {
            // O USUÁRIO SE CONECTOU COM SUCESSO,
            acct = result.getSignInAccount();
            preferencias.escreverFlagString(NomePreferencia.nomeUsuario, acct.getDisplayName());
            preferencias.escreverFlagString(NomePreferencia.emailUsuario, acct.getEmail());
            /*PEGAR OS DADOS DO USUÁRIO E PREPARAR O REQUEST PRA GUARDAR NO BANCO AS INFOS DO USUÁRIO
            * QUE SE LOGOI PELA GOOGLE*/
            getDataProfile();
            // FECHAR ATIVIDADE
            startLogin();
        } else {
            // SE NAO DEU TUDO CERTO, MOSTRAR UMA MENSAGEM DE ERRO
            Toast.makeText(context, "Ocorreu um erro", Toast.LENGTH_LONG).show();
            if(googleApiClient.isConnected()) {
                googleApiClient.clearDefaultAccountAndReconnect();
                googleApiClient.disconnect();
                googleApiClient.reconnect();
            }
        }
    }

    //FUNÇÃO QUE RETORNA TODOS OS DADOS DE PERFIL DO GOOGLE
    /* PEGA OS DADOS DO GOOGLE E GUARDA NO NOSSO BANCO*/
    protected void getDataProfile(){
            if(acct != null){
                volleyRequest.requestCadastrarDadosGoogle(acct);
            }
    }

    public void startLogin() {
        startActivity(new Intent(getApplicationContext(), AprenderActivity_.class));
        this.finish();
    }
}


