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

public class MainActivity extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
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

    // VARIÁVEIS GOOGLE
    public String name,
                  emailG,
                  imageUrl;

    // STRINGS DO EMAIL E SENHA
    private String sEmail,
                   sPassword;

    Context context = this;

    // Variavel que verifica se o usuario está logado
    private boolean isLogin;


    // VARIÁVEIS DE CONEXÃO
    private RequestQueue requestQueue;
    private StringRequest request;
    StringsBanco StringsBanco = new StringsBanco();
    GerenciarPerfilActivity gerenc = new GerenciarPerfilActivity();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // CONSTRUINDO O OBJETO DE CONEXÃO GOOGLE
        googleBuilder();

        // VERIFICANDO SE O USUÁRIO ESTÁ LOGADO
        if(readFlag()) {
            Intent intent = new Intent(this, AprenderActivity.class);
            startActivityForResult(intent, 1);
            finish();
        }

        setContentView(R.layout.activity_main);

        // VOLLEY
        requestQueue  = Volley.newRequestQueue(this);

        // ADICIONANDO OS COMPONENTES DA TELA
        accessViews();

        // ADICIONANDO OS LISTENERS DOS BOTÕES
        listenersLogin();


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
        btSignOut.setOnClickListener(MainActivity.this);
        btRevokeAccess.setOnClickListener(MainActivity.this);
        btAprender.setOnClickListener(MainActivity.this);


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
                                Intent i = new Intent(MainActivity.this, GerenciarPerfilActivity.class);
                                i.putExtra("id",sEmail);
                                startActivity(i);

                                //startActivity(new Intent(getApplicationContext(), GerenciarPerfilActivity.class));

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
        googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    public void onStart(){
        super.onStart();
        if(googleApiClient != null){
            googleApiClient.connect();
        }
    }


    @Override
    public void onStop(){
        super.onStop();
        if(googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == SIGN_IN_CODE){
            isConsentScreenOpened = false;

            if(resultCode != RESULT_OK){
                isSignInButtonClicked = false;
            }

            if(!googleApiClient.isConnecting()){
                googleApiClient.connect();
            }
        }
    }

    public void showUi(boolean status, boolean statusProgressBar){
        if(!statusProgressBar){
            llContainerAll.setVisibility(View.VISIBLE);
            pbContainer.setVisibility(View.GONE);

            llLoginForm.setVisibility(status ? View.GONE : View.VISIBLE);
            llConnected.setVisibility(!status ? View.GONE : View.VISIBLE);
        }
        else{
            llContainerAll.setVisibility(View.GONE);
            pbContainer.setVisibility(View.VISIBLE);
        }
    }

    //FUNÇAO PARA CARREGAR IMAGEM COM PROGRESSBAR
    public void loadImage(final ImageView ivImg, final ProgressBar pbImg, final String urlImg){
        RequestQueue rq = Volley.newRequestQueue(MainActivity.this);
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

    public void resolveSignIn(){
        if(connectionResult != null && connectionResult.hasResolution()){
            try {
                isConsentScreenOpened = true;
                connectionResult.startResolutionForResult(MainActivity.this, SIGN_IN_CODE);

            }
            catch(SendIntentException e) {
                isConsentScreenOpened = false;
                googleApiClient.connect();
            }
        }
    }

    //FUNÇÃO QUE RETORNA TODOS OS DADOS DE PERFIL DO GOOGLE
    public void getDataProfile(){
            Person p = Plus.PeopleApi.getCurrentPerson(googleApiClient);
            if(p != null){
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
                        parameters.put("EMAIL_GOOGLE", emailG);
                        parameters.put("NOME_GOOGLE", name);
                        return parameters;
                    }
                };

                String id = p.getId();
                name = p.getDisplayName();
                String language = p.getLanguage();
                String profileUrl = p.getUrl();
                imageUrl = p.getImage().getUrl();
                emailG = Plus.AccountApi.getAccountName(googleApiClient);

                tvId.setText(id);
                tvLanguage.setText(language);
                tvName.setText(name);
                tvEmail.setText(emailG);

                tvUrlProfile.setText(profileUrl);
                Linkify.addLinks(tvUrlProfile, Linkify.WEB_URLS);

                // Abre a tela de login após cadastro
                //carrega icone de imagem do perfil do google
                Log.i("Script", "IMG before: "+imageUrl);
                imageUrl = imageUrl.substring(0, imageUrl.length() - 2)+"200";
                Log.i("Script", "IMG after: "+imageUrl);
                loadImage(ivProfile, pbProfile, imageUrl);
            }

    }

    // MÉTODO QUE VERIFICA A PERMISSÃO DO USUÁRIO
    private void getPermissions() {
        int permissaoConta = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.GET_ACCOUNTS);
        int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 0;
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.GET_ACCOUNTS)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {

            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
            // MY_PERMISSIONS_REQUEST_GET_ACCOUNTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    // MÉTODO QUE PEGA A PERMISSÃO DO USUÁRIO
    private void getAccounts() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {
            getPermissions();
        }

        AccountManager accountManager = AccountManager.get(this);

        Account[] accounts = accountManager.getAccountsByType("com.google");

        for (Account a : accounts){
            String accountName = a.name;
            String domain = accountName.substring(accountName.indexOf("@") + 1, accountName.length());
            Log.d(TAG, "account domain: " + domain);
        }

    }
    // LISTENERS
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btSignInDefault){
            if(!googleApiClient.isConnecting()){
                getAccounts();
                isSignInButtonClicked = true;
                showUi(false, true);
                resolveSignIn();
            }
        }else if(v.getId() == R.id.btSignInCustom){
            Intent intent = new Intent(MainActivity.this, RecuperarSenhaActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.btSignOut){
            if(googleApiClient.isConnected()){
                Plus.AccountApi.clearDefaultAccount(googleApiClient);
                googleApiClient.disconnect();
                googleApiClient.connect();
                showUi(false, false);
                writeFlag(false);
            }
        }
        else if(v.getId() == R.id.btRevokeAccess){
            if(googleApiClient.isConnected()){
                Plus.AccountApi.clearDefaultAccount(googleApiClient);
                Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient).setResultCallback(new ResultCallback<Status>(){
                    @Override
                    public void onResult(Status result) {
                        writeFlag(false);
                        finish();
                    }
                });
            }
        }

        else if(v.getId() == R.id.bt_AprenderActivity){
            Intent intent = new Intent(MainActivity.this, AprenderActivity.class);
            String perfilEmail = emailG;
            String perfilNome = name;
            intent.putExtra("emailBundle", perfilEmail);
            intent.putExtra("nomeBundle", perfilNome);
            this.startActivity(intent);
        }
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        isSignInButtonClicked = false;
        showUi(true, false);
        getDataProfile();
    }


    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
        showUi(false, false);
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
                resolveSignIn();
            }
        }
    }

    // FLAG PARA MARCAR O USUARIO LOGaDO
    public void writeFlag(boolean flag) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", flag);
        editor.apply();
    }

    // LER FLAG PARA VER SE O USUARIO JA SE LOGOU
    public boolean readFlag() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("isLogin", false);
    }
}