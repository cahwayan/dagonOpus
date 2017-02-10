package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.tcc.dagon.opus.telas.login.StringsBanco;
import java.util.HashMap;
import java.util.Map;

import com.tcc.dagon.opus.ui.aprender.AprenderActivity_;

import static android.content.ContentValues.TAG;

/**
 * Created by cahwayan on 22/12/2016.
 */ /**/

public class VolleyRequest {

    public interface VolleyCallBack {
        void respostaEmail(boolean resultado);
    }

    private final GerenciadorSharedPreferences preferencesManager;
    private RequestQueue requestQueue;
    private ToastManager toastManager;
    private VolleyCallBack volleyCallBack;

    public VolleyRequest(Context context, VolleyCallBack volleyCallBack) {
        this.volleyCallBack = volleyCallBack;
        preferencesManager = new GerenciadorSharedPreferences(context);
        requestQueue = Volley.newRequestQueue(context);
        toastManager = new ToastManager(context);

    }

    public VolleyRequest(Context context) {
        preferencesManager = new GerenciadorSharedPreferences(context);
        requestQueue = Volley.newRequestQueue(context);
        toastManager = new ToastManager(context);

    }

    public void requestCadastrarDados(final Activity activity, final String email, final String senha, final String nome)
    {

        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getInsereUrl(), new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                if(response.equals("certo"))
                {
                    preferencesManager.escreverFlagString(GerenciadorSharedPreferences.getNomeUsuario(), nome);
                    toastManager.toastShort("Você foi cadastrado com sucesso!");
                    activity.finish();
                } else if(response.equals("erroExiste"))
                {
                    toastManager.toastLong("Email já cadastrado");
                }

                Log.d("RESP REQUESTCADASTR: ", response);

            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {
                toastManager.toastShort("Ocorreu um erro ao cadastrar. Você está conectado?");
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("EMAIL_USUARIO", email);
                parameters.put("SENHA_USUARIO", senha);
                parameters.put("NOME_USUARIO", nome);
                return parameters;
            }
        };

        requestQueue.add(request);

    }

    public void requestLogar(final Activity activity, final String sEmail, final String sSenha) {

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getLoginUrl(), new Response.Listener<String>()
        {

            public void onResponse(String response)
            {
                if(response.trim().equals("certo")){
                    preferencesManager.escreverFlagString(GerenciadorSharedPreferences.getEmailUsuario(), sEmail);
                    gravarNomeInterno(sEmail);
                    activity.startActivity(new Intent(activity, AprenderActivity_.class));
                    activity.finish();
                }else{
                    toastManager.toastShort("Email ou senha inválidos");
                }

                Log.d("RESPOSTA LOGIN: ", response);
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                toastManager.toastShort("Ocorreu um erro ao logar. Você está conectado?");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("EMAIL_USUARIO", sEmail);
                hashMap.put("SENHA_USUARIO", sSenha);
                return hashMap;
            }

        };

        requestQueue.add(request);
    }

    /*MÉTODO QUE FAZ UM REQUEST NO BANCO COM O E-MAIL DO USUÁRIO QUANDO O USUÁRIO LOGA
    PARA PEGAR O NOME REFERENTE AO E-MAIL E GUARDA ESSE NOME EM UMA SHARED PREFERENCE
    PARA USAR O NOME DELE NO PERFIL*/
    private void gravarNomeInterno(final String sEmail)
    {
        StringRequest requestNome = new StringRequest(
                Request.Method.POST, StringsBanco.getNomeUrl(), new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                preferencesManager.escreverFlagString(GerenciadorSharedPreferences.getNomeUsuario(), response);
                Log.d(TAG, "Nome gravado: " + response);
            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("EMAIL_USUARIO", sEmail);
                return hashMap;
            }
        };

        requestQueue.add(requestNome);
    }

    public void requestCadastrarDadosGoogle(final GoogleSignInAccount acct)
    {
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getInsereGoogle(), new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                Log.d("DADOS GOOGL CADASTRAD: ", response);
            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("EMAIL_GOOGLE", acct.getEmail());
                parameters.put("NOME_GOOGLE", acct.getDisplayName());
                return parameters;
            }
        };

        requestQueue.add(request);
    }

    public void requestUsuarioExiste(final String email) {
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getUsuarioExiste(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                boolean usuarioExiste = response.equals("sim");
                if(volleyCallBack != null) {
                    volleyCallBack.respostaEmail(usuarioExiste);
                }
            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {

            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("EMAIL_USUARIO", email);
                return parameters;
            }
        };

        requestQueue.add(request);

    }

}
