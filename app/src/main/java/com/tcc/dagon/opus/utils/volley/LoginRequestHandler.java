package com.tcc.dagon.opus.utils.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tcc.dagon.opus.ui.usuario.StringsBanco;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cahwayan on 29/03/2017.
 */

public class LoginRequestHandler extends VolleyRequest {

    private final String TAG = this.getClass().getSimpleName();

    private CallbackLogin callbackLogin;

    public LoginRequestHandler(Context context) {
        super(context);
        this.callbackLogin = (CallbackLogin) context;
    }

    public void requestLogar(final String sEmail, final String sSenha) {

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptLogin(), new Response.Listener<String>()
        {

            public void onResponse(String response)
            {


                callbackLogin.callbackLogin(response);

                Log.d(TAG, "RESPOSTA LOGIN INTERNO: " + response);
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
    public void getNomeUsuario(final String sEmail)
    {
        StringRequest requestNome = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptGetNome(), new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                callbackLogin.callbackNome(response);
            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {

            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("EMAIL_USUARIO", sEmail);
                return hashMap;
            }
        };

        requestQueue.add(requestNome);
    }

    public void getID(final String tipoUsuario, final String email) {

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptGetId(), new Response.Listener<String>()
        {

            public void onResponse(String response)
            {

                callbackLogin.callbackId(response);

                Log.d(TAG, "RESPOSTA FIND ID: " + response);
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("TIPO_USUARIO", tipoUsuario);
                hashMap.put("EMAIL_USUARIO", email);
                return hashMap;
            }

        };

        requestQueue.add(request);
    }

}
