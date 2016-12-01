package com.tcc.dagon.opus;
import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int SIGN_IN_CODE = 56465;
    static GoogleApiClient googleApiClient;
    private ConnectionResult connectionResult;

    private boolean isConsentScreenOpened,
                    isSignInButtonClicked;

    // VIEWS
    private LinearLayout llLoginForm,
                         llContainerAll,
                         llConnected;

    private Button btSignIn,
                   btSignInCustom,
                   btAprender;

    private SignInButton btSignInDefault;

    private ImageView ivProfile,
                      txtLogin;

    private ProgressBar pbProfile,
                        pbContainer;

    private TextView tvId,
                     tvLanguage,
                     tvName,
                     tvUrlProfile,
                     tvEmail,
                     btSignOut,
                     btRevokeAccess,
                     email,password,
                     botaoCriarConta;

    GoogleSignInAccount acct;

    final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 1;

    // VARIÁVEIS GOOGLE
    public String name,
                  emailG,
                  imageUrl;

    // STRINGS DO EMAIL E SENHA
    private String sEmail,
                   sPassword;

    Context context = this;


    // VARIÁVEIS DE CONEXÃO
    private RequestQueue requestQueue;
    private StringRequest request;
    StringsBanco StringsBanco = new StringsBanco();
    GerenciarPerfilActivity gerenc = new GerenciarPerfilActivity();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // CONSTRUINDO O OBJETO DE CONEXÃO GOOGLE
        googleBuilder();

        setContentView(R.layout.activity_main);

        // VOLLEY
        requestQueue  = Volley.newRequestQueue(this);

        // ADICIONANDO OS COMPONENTES DA TELA
        accessViews();

        // ADICIONANDO OS LISTENERS DOS BOTÕES
        listenersLogin();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop(){
        super.onStop();

        if(googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }


    // COMPONENTES DA INTERFACE
    public void accessViews(){
        txtLogin        = (ImageView)findViewById(R.id.txtLogin);
        botaoCriarConta = (TextView)findViewById(R.id.botaoCriarConta);
        llContainerAll  = (LinearLayout) findViewById(R.id.llContainerAll);
        pbContainer     = (ProgressBar) findViewById(R.id.pbContainer);

        // sem conexão
        llLoginForm     = (LinearLayout) findViewById(R.id.llLoginForm);
        btSignIn        = (Button) findViewById(R.id.btSignIn);
        btSignInCustom  = (Button) findViewById(R.id.btSignInCustom);
        btSignInDefault = (SignInButton) findViewById(R.id.btSignInDefault);
        btAprender      = (Button)findViewById(R.id.bt_AprenderActivity);

        Typeface adam   =  Typeface.createFromAsset(getAssets(), "fonts/adam.otf");
        botaoCriarConta.setTypeface(adam);

        // CONECTADO
        llConnected     = (LinearLayout) findViewById(R.id.llConnected);
        ivProfile       = (ImageView) findViewById(R.id.ivProfile);
        pbProfile       = (ProgressBar) findViewById(R.id.pbProfile);
        tvId            = (TextView) findViewById(R.id.tvId);
        tvLanguage      = (TextView) findViewById(R.id.tvLanguage);
        tvName          = (TextView) findViewById(R.id.tvName);
        tvUrlProfile    = (TextView) findViewById(R.id.tvUrlProfile);
        tvEmail         = (TextView) findViewById(R.id.tvEmail);
        btSignOut       = (Button) findViewById(R.id.btSignOut);
        btRevokeAccess  = (Button) findViewById(R.id.btRevokeAccess);
        email           = (EditText) findViewById(R.id.edt_email);
        password        = (EditText) findViewById(R.id.edt_senha);

        // LISTENERS
        btSignIn.setOnClickListener(MainActivity.this);


        btSignInDefault.setOnClickListener(MainActivity.this);
        btSignInCustom.setOnClickListener(MainActivity.this);


    }

    public void listenersLogin() {
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sEmail    = email.getText().toString().trim();
                sPassword = password.getText().toString().trim();
                // VERIFICA SE OS CAMPOS ESTÃO VAZIOS E INVOCA O TECLADO + FOCO CASO ESTEJAM
                if( sEmail.matches("") ) {
                    // MENSAGEM DE ERRO
                    Toast.makeText(getApplicationContext(),
                                   "Campo email vazio!",
                                   Toast.LENGTH_SHORT).show();
                    // FOCA NA TEXTVIEW APÓS ERRO
                    email.requestFocus();
                    // CÓDIGO QUE INVOCA O TECLADO APÓS O ERRO
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                                        InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else if ( sPassword.matches("") ) {
                    Toast.makeText(getApplicationContext(), // PARÂMETRO PADRÃO TOAST
                                   "Campo senha vazio!" ,  // MENSAGEM TOAST
                                   Toast.LENGTH_SHORT).show(); // TAMANHO DO TOAST E MÉTODO PARA MOSTRAR
                    password.requestFocus();

                    // CÓDIGO QUE INVOCA O TECLADO APÓS O ERRO
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else { // FAZ TENTATIVA DE CONEXÃO
                    request = new StringRequest(Request.Method.POST,
                                                StringsBanco.loginUrl,
                                                new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("certo")){
                                Intent i = new Intent(MainActivity.this, AprenderActivity.class);
                                gravarEmail(sEmail);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),
                                               "Login ou senha inválidos",
                                               Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Erro ao conectar. Verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("EMAIL_USUARIO", sEmail);
                            hashMap.put("SENHA_USUARIO", sPassword);
                            return hashMap;
                        }

                    };
                    requestQueue.add(request);
                }

            }
        });

        botaoCriarConta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v ){
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
            }
        });
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

    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, SIGN_IN_CODE);
    }

    // ESSE MÉTODO DECIDE O QUE FAZER COM A RESPOSTA DO LOGIN DO USUÁRIO
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
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // O USUÁRIO SE CONECTOU COM SUCESSO,
            acct = result.getSignInAccount();
            getDataProfile();
            // FECHAR ATIVIDADE
            finish();
            // ABRIR A TELA DE MÓDULOS
            startActivity(new Intent(this, AprenderActivity.class));
        } else {
            // SE NAO DEU TUDO CERTO, MOSTRAR UMA MENSAGEM DE ERRO E RECRIAR ATIVIDADE
            Toast.makeText(context, "Ocorreu um erro", Toast.LENGTH_LONG).show();
            googleApiClient.clearDefaultAccountAndReconnect();
            googleApiClient.disconnect();
            googleApiClient.connect();
            recreate();
        }
    }


    //FUNÇÃO QUE RETORNA TODOS OS DADOS DE PERFIL DO GOOGLE
    public void getDataProfile(){

            if(acct != null){
                StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.insereGoogle, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    //INSERE DADOS EM BANCO DE DADOS, LEMBRANDO QUE DEVE SER PERFEITAMENTE IGUAL OS NOMES DAS TABELAS
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("EMAIL_GOOGLE", acct.getEmail());
                        parameters.put("NOME_GOOGLE", acct.getDisplayName());
                        return parameters;
                    }
                };

            }

    }

    // MÉTODO QUE VERIFICA A PERMISSÃO DO USUÁRIO
    private void getPermissions() {
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

    // MÉTODO QUE PEGA EFETIVAMENTE A PERMISSÃO DO USUÁRIO
    private void getAccounts() {
        // CASO A PERMISSÃO ATUAL SEJA DIFERENTE DE PERMITIDA
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            // PEDIR PERMISSÕES
            getPermissions();
        } else {
            // SE AS PERMISSÕES JÁ FORAM GARANTIDAS, FAZER O SIGNIN
            signIn();
        }

        AccountManager accountManager = AccountManager.get(this);

        Account[] accounts = accountManager.getAccountsByType("com.google");

        for (Account a : accounts){
            String accountName = a.name;
            String domain = accountName.substring(accountName.indexOf("@") + 1, accountName.length());
            Log.d(TAG, "account domain: " + domain);
        }

    }

    // MÉTODO QUE LIDA COM O RESULTADO DA PERMISSÃO DO USUÁRIO
    // SE ELE CONCEDEU OU NÃO
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GET_ACCOUNTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    signIn();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.



                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    // LISTENERS
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btSignInDefault){
            //if(verificarConexao()) {
                if(!googleApiClient.isConnecting()){
                    getAccounts();
                }
            //} else {
            //    Toast.makeText(context, "Sem conexão", Toast.LENGTH_LONG).show();
           // }

        }else if(v.getId() == R.id.btSignInCustom){
            Intent intent = new Intent(MainActivity.this, RecuperarSenhaActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInButtonClicked = false;
        getDataProfile();

    }

    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }

    public void gravarEmail(String emailUsuario) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("emailUsuario", emailUsuario);
        editor.apply();
    }

    public Boolean verificarConexao() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }




    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if(!result.hasResolution()){
//            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), MainActivity.this, 0).show();
            return;
        }

        if(!isConsentScreenOpened){
            connectionResult = result;

            if(isSignInButtonClicked){
                signIn();
            }
        }
    }
}