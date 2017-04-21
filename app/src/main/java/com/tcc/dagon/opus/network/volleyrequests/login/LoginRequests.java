package com.tcc.dagon.opus.network.volleyrequests.login;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tcc.dagon.opus.application.AppController;
import com.tcc.dagon.opus.network.volleyrequests.BancoRemoto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cahwayan on 29/03/2017.
 */

public class LoginRequests {

    public String resposta;

    private final String TAG = LoginRequests.class.getSimpleName();

    private CallbackLogin callbackLogin;

    public LoginRequests(CallbackLogin activity) {
        this.callbackLogin = activity;
    }

    public void requestLogar(final String sEmail, final String sSenha) {

        String tag_login = "Request Login: ";

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptLogin(), new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "RESPOSTA LOGIN INTERNO: " + response);
                callbackLogin.onLogin(response);
                resposta = response;
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                callbackLogin.onLogin(error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("EMAIL_USUARIO", sEmail);
                hashMap.put("SENHA_USUARIO", sSenha);
                return hashMap;
            }

        };

        AppController.getInstance().addToRequestQueue(request, tag_login);
    }



}
