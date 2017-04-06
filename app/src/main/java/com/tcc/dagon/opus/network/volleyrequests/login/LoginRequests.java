package com.tcc.dagon.opus.network.volleyrequests.login;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tcc.dagon.opus.app.AppController;
import com.tcc.dagon.opus.network.volleyrequests.CustomJSONRequest;
import com.tcc.dagon.opus.ui.usuario.StringsBanco;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cahwayan on 29/03/2017.
 */

public class LoginRequests {

    public String resposta;

    private final String TAG = LoginRequests.class.getSimpleName();

    private CallbackLogin callbackLogin;

    public LoginRequests(Context context) {
        this.callbackLogin = (CallbackLogin) context;
    }

    public void requestLogar(final String sEmail, final String sSenha) {

        String tag_login = "Request Login: ";

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptLogin(), new Response.Listener<String>()
        {

            @Override
            public void onResponse(String response)
            {
                callbackLogin.callbackLoginInterno(response);
                resposta = response;
                Log.d(TAG, "RESPOSTA LOGIN INTERNO: " + response);
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                callbackLogin.callbackLoginInterno(error.toString());
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
