package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.tcc.dagon.opus.StringsBanco;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by cahwayan on 22/12/2016.
 */

public class VolleyRequest {

    private GerenciadorSharedPreferences preferencias;
    private StringsBanco stringsBanco = new StringsBanco();
    private RequestQueue requestQueue;
    Activity activity;
    private boolean isLogin = false;

    public VolleyRequest(Activity activity) {
        /* OBJETO DE CONEXÃO */
        this.activity = activity;
        requestQueue = Volley.newRequestQueue(activity);
        preferencias = new GerenciadorSharedPreferences(activity);
    }

    public void requestCadastrarDados(final String email, final String senha, final String nome) {
        /* REQUEST NO BANCO */
        StringRequest request = new StringRequest(Request.Method.POST, stringsBanco.getInsereUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(activity, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                preferencias.escreverFlagString(GerenciadorSharedPreferences.NomePreferencia.nomeUsuario, nome);
                activity.finish();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Ocorreu um erro ao cadastrar. Por favor verifique sua conexão.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            //INSERE DADOS EM BANCO DE DADOS, LEMBRANDO QUE DEVE SER PERFEITAMENTE IGUAL OS NOMES DAS TABELAS
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("EMAIL_USUARIO", email);
                parameters.put("SENHA_USUARIO", senha);
                parameters.put("NOME_USUARIO", nome);
                return parameters;
            }
        };

        // Abre a tela de login após cadastro
        requestQueue.add(request);
    }

    public boolean requestLogar(final String sEmail, final String sSenha) {
        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(Request.Method.POST,
                /* ENDEREÇO QUE O REQUEST IRÁ BUSCAR */
                stringsBanco.getLoginUrl(),
                new Response.Listener<String>() {
                    @Override
                    /* LISTENER DA RESPOSTA DO SERVIDOR */
                    public void onResponse(String response) {
                        /* SE A RESPOSTA FOR CERTO */
                        if(response.trim().equals("certo")){
                            /* FAZER LOGIN */
                            preferencias.escreverFlagString(GerenciadorSharedPreferences.NomePreferencia.emailUsuario, sEmail);
                            //lerNomeInterno();
                            gravarNomeInterno(sEmail);
                            isLogin = true;
                        }else{
                            Toast.makeText(activity,
                                    "Login ou textSenha inválidos",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Erro ao conectar. Verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("EMAIL_USUARIO", sEmail);
                hashMap.put("SENHA_USUARIO", sSenha);
                return hashMap;
            }

        };
        requestQueue.add(request);

        return isLogin;
    }

    /*MÉTODO QUE FAZ UM REQUEST NO BANCO COM O E-MAIL DO USUÁRIO QUANDO O USUÁRIO LOGA
    PARA PEGAR O NOME REFERENTE AO E-MAIL E GUARDA ESSE NOME EM UMA SHARED PREFERENCE
    PARA USAR O NOME DELE NO PERFIL*/
    protected void gravarNomeInterno(final String sEmail) {
        StringRequest requestNome = new StringRequest(Request.Method.POST,
                stringsBanco.getNomeUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        preferencias.escreverFlagString(GerenciadorSharedPreferences.NomePreferencia.nomeUsuario, response);
                        Log.d(TAG, "Nome gravado: " + response);

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
                return hashMap;
            }

        };
        requestQueue.add(requestNome);
    }

    public void requestCadastrarDadosGoogle(final GoogleSignInAccount acct) {
        StringRequest request = new StringRequest(Request.Method.POST, stringsBanco.getInsereGoogle(), new Response.Listener<String>() {
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

        // Abre a tela de login após cadastro
        requestQueue.add(request);
    }
}
