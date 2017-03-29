package com.tcc.dagon.opus.ui.usuario;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.OnOffClickListener;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.VerificarConexao;
import com.tcc.dagon.opus.utils.volley.CadastroRequestHandler;
import com.tcc.dagon.opus.utils.volley.CallbackCadastro;
import com.tcc.dagon.opus.utils.volley.CallbackLogin;
import com.tcc.dagon.opus.utils.volley.LoginRequestHandler;
import com.tcc.dagon.opus.utils.volley.VolleyRequest;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.tcc.dagon.opus.ui.aprender.AprenderActivity_;

import static android.content.ContentValues.TAG;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, CallbackCadastro, CallbackLogin {

    /* CALLBACKS */

    /*
        @param resultado: Se True, o usuário existe. Se False, o usuário não existe

        Esse callback é para o login com o google. Ele verifica se o usuário que está logando já existe no banco de dados.
         Caso ele não exista, é preciso fazer um request para cadastrar o usuário.
         Caso já exista, é preciso fazer requests para restaurar o progresso do usuario.
        O resultado desse request vem sempre depois que o login
        com o google recebe o status sucesso. Como o login com o google funciona ao mesmo tempo como um login e
        um cadastro, é preciso verificar se o resultado deu sucesso antes de prosseguir.

     */
    @Override
    public void callbackEmailExiste(boolean resultado) {

        // Se o usuário já existe
        if(resultado) {

            // TODO: Aqui é onde será restaurado o progresso do usuário que já existe e está logando com o google
            // Salvar o ID dele
            // preferencias.setIdUsuario(Id);
            Toast.makeText(this, "Bem-vindo de volta!", Toast.LENGTH_LONG).show();

            //startLogin();
        } else {
            cadastroRequestHandler.cadastrarUsuario(StringsBanco.USUARIO_GOOGLE, acct.getEmail(), "", acct.getDisplayName());
        }

    }


    /*
        @param resultado: Se 'certo', as informações do usuário, e as tabelas relacionadas a ele foram gravadas
         na base de dados via php.
                          Se 'erroExiste', o usuário já existe.
         Esse callback é a resposta da tentativa de gravar os dados do usuário na base de dados remota via VolleyRequest e PHP.
         Esse método é chamado após o resultado do Google ter dado success, e depois do app verificar se o usuário existe ou não.
         Caso o usuário não exista, o request de cadastro será chamado, e esse callback será executado para lidar com o resultado.
         O próximo passo depois de cadastrar o usuário, é salvar as informações dele em sharedpreferences:
         ID
     */
    @Override
    public void callbackCadastro(String resultado) {
        if(resultado.equals("certo")) {
            // TODO: Salvar o ID do usuário antes de dar startLogin
            // preferencias.setId(PegarId);
            startLogin();
        } else {
            Log.d(TAG, resultado);
        }
    }

    @Override
    public void callbackLogin(String response) {

        if(response.trim().equals("certo")){
            // TODO: Aqui é onde será feito os requests de restaurar o progresso do usuário interno.
            // Colocar para abrir a activity de login no callback do último request
            preferencias.setEmailUsuario(sEmail);
            loginRequestHandler.getNomeUsuario(sEmail);

            //startLogin();
        } else {
            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }

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

    protected GerenciadorSharedPreferences preferencias;

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
    protected LoginRequestHandler loginRequestHandler;
    protected CadastroRequestHandler cadastroRequestHandler;

    /* FIM ATRIBUTOS */

    /*MÉTODOS DE CICLO DE VIDA DO APP*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DON'T DO THIS !! It will throw a NullPointerException, myTextView is not set yet.
        // myTextView.setText("Date: " + new Date());
    }

    @Override
    public void onStart() {
        super.onStart();
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

        preferencias = new GerenciadorSharedPreferences(this);
        loginRequestHandler = new LoginRequestHandler(this);

        loadClickListeners();

    }

    /*FIM INICIALIZAÇÃO COMPONENTES*/

    /* CLICK LISTENERS */

    private void loadClickListeners() {
        OnOffClickListener clickListenerLogin = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                sEmail    = textEmail.getText().toString().trim();
                sSenha = textSenha.getText().toString().trim();

                // VERIFICA SE OS CAMPOS ESTÃO VAZIOS E INVOCA O TECLADO + FOCO CASO ESTEJAM
                if(VerificarConexao.verificarConexao()) {
                    if(verificarCredenciais(sEmail, sSenha)) {
                        loginRequestHandler.requestLogar(getActivity(), sEmail, sSenha);
                        //w

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Sem conexão", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnLogar.setOnClickListener(clickListenerLogin);

        OnOffClickListener clickListenerGoogle = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                if(VerificarConexao.verificarConexao()) {
                    if(!googleApiClient.isConnecting()){
                        verificarPermissaoGoogleLogin();
                    }
                } else {
                    Toast.makeText(context, "Por favor, verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show();
                }
            }
        };

        btnLogarComGoogle.setOnClickListener(clickListenerGoogle);

        OnOffClickListener clickListenerAlterarSenha = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RecuperarSenhaActivity.class);
                startActivity(intent);
            }
        };

        btnAlterarSenha.setOnClickListener(clickListenerAlterarSenha);

        OnOffClickListener clickListenerCadastrar = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                btnCriarConta.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                startActivity(new Intent(getApplicationContext(), CadastroActivity_.class));
            }
        };

        btnCriarConta.setOnClickListener(clickListenerCadastrar);
    }

    public Activity getActivity() {
        return this;
    }

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
            pedirPermissaoGoogleLogin(); //y
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
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess() + result);
        if (result.isSuccess()) {
            // O USUÁRIO SE CONECTOU COM SUCESSO,
            acct = result.getSignInAccount();

            if(acct != null) {
                getDataProfile();
            }

        } else {
            // SE NAO DEU TUDO CERTO, MOSTRAR UMA MENSAGEM DE ERRO
            Log.d("ERRO GOOGLE: ",result.getStatus().toString() + " | " + result.toString() + " | ");
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

        preferencias.setNomeUsuario(acct.getDisplayName());
        preferencias.setEmailUsuario(acct.getEmail());
        preferencias.setCaminhoFoto("default");
        cadastroRequestHandler.usuarioExiste(StringsBanco.USUARIO_GOOGLE, acct.getEmail());

    }

    public void startLogin() {
        startActivity(new Intent(getApplicationContext(), AprenderActivity_.class));
        finish();
    }
}


