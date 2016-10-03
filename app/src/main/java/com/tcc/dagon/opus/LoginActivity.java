package com.tcc.dagon.opus;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // Componentes visuais Email e senha
    private EditText email,password;
    // String dos componentes email e senha
    private String sEmail, sPassword;
    // Botão de login
    private Button btn_login;
    private SignInButton mGoogleSignIn;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private boolean mInfoPopulated;
    private EditText edtTeste;
    public String nomeGoogle;
    private TextView txtCriarConta;

    // Variáveis de conexão
    private RequestQueue requestQueue;
    private StringRequest request;

    StringsBanco StringsBanco = new StringsBanco();
    public GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Componentes da tela

        email = (EditText) findViewById(R.id.edt_email);
        password = (EditText) findViewById(R.id.edt_senha);
        btn_login = (Button) findViewById(R.id.btn_Login);
        txtCriarConta = (TextView) findViewById(R.id.txtCriarConta);
        mGoogleSignIn = (SignInButton) findViewById(R.id.btSignInDefault);
        requestQueue = Volley.newRequestQueue(this);

        // Chamando os listeners
        listenersLogin();
        listenerGoogleLogin();

    }

    @Override
    protected void onStop() {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        if(mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    public void listenersLogin() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sEmail    = email.getText().toString().trim();
                sPassword = password.getText().toString().trim();

                // VERIFICA SE OS CAMPOS ESTÃO VAZIOS E INVOCA O TECLADO + FOCO CASO ESTEJAM
                if(sEmail.matches("")) {
                    // mensagem
                    Toast.makeText(getApplicationContext(), "Campo email vazio!" , Toast.LENGTH_SHORT).show();
                    // comando que foca na textview
                    email.requestFocus();
                    // código que invoca o teclado
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else if (sPassword.matches("")) {
                    Toast.makeText(getApplicationContext(), "Campo senha vazio!" , Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                } else {
                    request = new StringRequest(Request.Method.POST, StringsBanco.loginUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("certo")){
                                startActivity(new Intent(getApplicationContext(), AprenderActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro ao logar. Verifique suas credenciais!" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

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
    }
    public void listenerGoogleLogin(){
        mGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mGoogleApiClient.isConnecting()){
                    mSignInClicked = true;
                    ResolveSignError();
                }

            }
        });


    }
    public void ResolveSignError(){
        if (mGoogleApiClient.isConnected()) {
            return;

        }
        if(mConnectionResult.hasResolution()){
            mIntentInProgress=true;
            try {
                startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),0,null,0,0,0);

            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress=false;
                mGoogleApiClient.connect();
            }


        }
    }
    protected void OnActivityResult(int requestCode, Result resultCode, Intent data)
    {
        if (requestCode == 0)
        {
            if (resultCode != null)
            {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting())
            {
                mGoogleApiClient.connect();
            }
        }
    }


    //METODOS IMPLEMENTADAOS LOGIN GOOGLE, TODOS SAO NECESSARIOS
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(Plus.PeopleApi.getCurrentPerson(mGoogleApiClient)!=null){
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                if(currentPerson.hasDisplayName()){
                    //AQUI BUSCA O NOME DO USUARIO DA GOOGLE
                    nomeGoogle = currentPerson.getDisplayName();

                }
        }

        startActivity(new Intent(getApplicationContext(), AprenderActivity.class));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(connectionResult.hasResolution()){
    try{
        connectionResult.startResolutionForResult(this, connectionResult.getErrorCode());

    }catch(IntentSender.SendIntentException e){
        mGoogleApiClient.connect();

    }

}

    }
}