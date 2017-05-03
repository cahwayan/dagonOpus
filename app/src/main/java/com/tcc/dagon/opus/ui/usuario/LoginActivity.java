package com.tcc.dagon.opus.ui.usuario;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
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
import com.tcc.dagon.opus.application.AppController;
import com.tcc.dagon.opus.network.volleyrequests.BancoRemoto;
import com.tcc.dagon.opus.network.volleyrequests.usuario.RequestsUsuario;
import com.tcc.dagon.opus.common.OnOffClickListener;
import com.tcc.dagon.opus.common.ProgressDialogHelper;
import com.tcc.dagon.opus.common.ValidarEmail;
import com.tcc.dagon.opus.data.sharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.common.ConexaoChecker;
import com.tcc.dagon.opus.network.volleyrequests.cadastro.CadastroRequests;
import com.tcc.dagon.opus.network.volleyrequests.cadastro.CallbackCadastro;
import com.tcc.dagon.opus.network.volleyrequests.login.CallbackLogin;
import com.tcc.dagon.opus.network.volleyrequests.login.LoginRequests;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.tcc.dagon.opus.network.volleyrequests.usuario.UsuarioListenerImp;
import com.tcc.dagon.opus.ui.aprender.AprenderActivity_;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, CallbackCadastro, CallbackLogin {



    /*TOKEN GOOGLE*/
    protected static final int SIGN_IN_CODE = 56465;

    private final String TAG = this.getClass().getSimpleName();

    private RequestsUsuario requests;
    private UsuarioListenerImp usuarioListener;

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
    private ProgressDialog progressDialog;

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
    protected LoginRequests login;
    protected CadastroRequests cadastro;

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

    private Activity getActivity() {
        return this;
    }

    /*MÉTODO DE INICIALIZAÇÃO DE COMPONENTES*/

    @AfterViews
    protected void init() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // CRIAÇÃO DO OBJETO DE CONEXÃO GOOGLE
        googleBuilder();

        preferencias = new GerenciadorSharedPreferences(this);
        login = new LoginRequests(this);
        cadastro = new CadastroRequests(this);

        loadClickListeners();

    }

    /*FIM INICIALIZAÇÃO COMPONENTES*/

    /*FIM DOS DICLOS DE VIDA DO APP*/

    /* CLICK LISTENERS */

    private void loadClickListeners() {
        OnOffClickListener clickListenerLogin = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {

                sEmail    = textEmail.getText().toString().trim();
                sSenha = textSenha.getText().toString().trim();

                // VERIFICA SE OS CAMPOS ESTÃO VAZIOS E INVOCA O TECLADO + FOCO CASO ESTEJAM
                if(ConexaoChecker.verificarSeHaConexaoDisponivel(getActivity())) {
                    if(verificarCredenciais(sEmail, sSenha)) {
                        showProgressDialog(R.string.carregando);
                        login.requestLogar(sEmail, sSenha);
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
                if(ConexaoChecker.verificarSeHaConexaoDisponivel(getActivity())) {
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

    /* MÉTODOS DE CICLO DE VIDA DO OBJETO DE CONEXÃO GOOGLE*/

    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInButtonClicked = false;
        //getDataProfile();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull  ConnectionResult result) {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String  permissions[], @NonNull int[] grantResults) {
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
                showProgressDialog(R.string.carregando);
                cadastro.usuarioExiste(BancoRemoto.USUARIO_GOOGLE, acct.getEmail());
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

    /**
     * Callback de um request que verifica se o email do usuário já existe na base de dados (cadastro google).
     *
     * O resultado desse request vem sempre depois que o login com o google recebe o status sucesso.
     * Como o login com o google funciona ao mesmo tempo como um login e
     * um cadastro, é preciso verificar se o usuário está se logando novamente ou se é a primeira vez.
     *
     * Se o usuário existir, é preciso apenas restaurar o progresso dele.
     * Caso não exista, é necessário cadastrar o usuário.
     *
     * @param resultado: Se 'sim', o usuário existe. Se 'nao', o usuário não existe.
     */

    @Override
    public void onUsuarioExiste(String resultado) {

        if(resultado.equals("sim")) {
            restaurarUsuario(BancoRemoto.USUARIO_GOOGLE, acct.getEmail());
        } else if(resultado.equals("nao")) {
            cadastro.cadastrarUsuario(BancoRemoto.USUARIO_GOOGLE, acct.getEmail(), "", acct.getDisplayName());
        } else {
            hideProgressDialog();
            Toast.makeText(this, "Ocorreu um pequeno problema. Você está conectado?", Toast.LENGTH_LONG).show();
        }

        AppController.decreaseRequestCount();
    }

    /**
     *Esse callback é a resposta da tentativa de gravar os dados do usuário na base de dados remota via VolleyRequest e PHP.
     *Esse método é chamado após o resultado do Google ter dado success, e depois do app verificar se o usuário existe ou não.
     *Caso o usuário não exista, o request de cadastro será chamado, e esse callback será executado para lidar com o resultado.
     *O próximo passo depois de cadastrar o usuário, é salvar as informações dele em sharedpreferences:
     *ID
     *
     *@param resultado: Se 'certo', as informações do usuário, e as tabelas relacionadas a ele foram gravadas
     *na base de dados via php.
     *Se 'erroExiste', o usuário já existe.
     */
    @Override
    public void onCadastro(String resultado) {

        if(resultado.equals("certo")) {
            restaurarUsuario(BancoRemoto.USUARIO_GOOGLE, acct.getEmail());
        } else {
            hideProgressDialog();
            Toast.makeText(this, "Ocorreu um erro ao cadastrar. Você está conectado?", Toast.LENGTH_LONG).show();
            Log.d(TAG, resultado);
        }

        AppController.decreaseRequestCount();

    }

    @Override
    public void onLogin(String response) {

        if(response.trim().equals("certo")){

            restaurarUsuario(BancoRemoto.USUARIO_INTERNO, sEmail);

        } else if(response.trim().equals("erroCredenciais")) {
            Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
        } else {
            Toast.makeText(this, "Erro desconhecido. Tente novamente.", Toast.LENGTH_SHORT).show();
            hideProgressDialog();
        }

        AppController.decreaseRequestCount();

    }

    public void showProgressDialog(int resId) {
        showProgressDialog(getString(resId));
    }

    public void showProgressDialog(String msg) {

        this.progressDialog = ProgressDialogHelper.buildDialog(this, msg);

        if(!this.progressDialog.isShowing() && this.progressDialog != null) {

            this.progressDialog.show();
        }
    }

    public void hideProgressDialog() {
        if(this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

    private void restaurarUsuario(String tipoUsuario, String email) {

        switch(tipoUsuario) {

            case BancoRemoto.USUARIO_INTERNO:
                preferencias.setEmailUsuario(email);
                break;

            case BancoRemoto.USUARIO_GOOGLE:
                preferencias.setEmailUsuario(acct.getEmail());
                break;

            default:
                hideProgressDialog();
                Toast.makeText(this, "Tipo de usuário inválido.", Toast.LENGTH_LONG).show();
                return;
        }

        preferencias.setTipoUsuario(tipoUsuario);

        requests = getRequestsUsuario(tipoUsuario, email);

        requests.getID();

        AppController.setRequestCountdown(AppController.getRequestCount());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "Iniciando espera pelo request de ID");
                    AppController.getCountdownLatch().await();
                    Log.i(TAG, "Espera pelo request de ID terminada... prosseguindo");

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            requests.getNome();
                            requests.selectEnderecoFoto();
                            requests.selectTempoEstudo();
                            requests.selectEstadoCertificado();
                            requests.selectProgresso();
                            requests.selectPontuacao();
                            requests.selectConquistas();
                            AppController.setRequestCountdown(AppController.getRequestCount());
                            esperarFilaRequestsTerminarEConcluirLogin();

                        }
                    });

                } catch(InterruptedException e ) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private RequestsUsuario getRequestsUsuario(String tipoUsuario, String email) {
        return new RequestsUsuario(tipoUsuario, getUsuarioListener(), email);

    }

    public UsuarioListenerImp getUsuarioListener() {
        usuarioListener = new UsuarioListenerImp(this, progressDialog);
        return usuarioListener;
    }

    public void esperarFilaRequestsTerminarEConcluirLogin() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    AppController.getCountdownLatch().await();
                    Log.d(TAG, "COUNTDOWN TERMINADA . . . PROSSEGUINDO");

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            if(!usuarioListener.getHouveramErrosAoRestaurarUsuario()) {
                                Log.d(TAG, "CONCLUINDO LOGIN");
                                startActivity(new Intent(getApplicationContext(), AprenderActivity_.class));
                                preferencias.setIsLogin(true);
                                hideProgressDialog();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro. Você está conectado?", Toast.LENGTH_LONG).show();
                                hideProgressDialog();
                                usuarioListener.setHouveramErrosAoRestaurarUsuario(false);
                            }

                        }
                    });

                } catch(InterruptedException e ) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    /* VERIFICAÇÃO CONSISTÊNCIA CREDENCIAIS */
    public boolean verificarCredenciais(String sEmail, String sSenha) {

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
        } else if(!ValidarEmail.validarEmail(sEmail)) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return false;

        } else {

            return true;

        }
    }
}


