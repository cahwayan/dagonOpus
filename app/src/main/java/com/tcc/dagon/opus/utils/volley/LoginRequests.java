package com.tcc.dagon.opus.utils.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.tcc.dagon.opus.app.AppController;
import com.tcc.dagon.opus.ui.usuario.StringsBanco;

import org.json.JSONArray;
import org.json.JSONException;
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

    public void getID(final String tipoUsuario, final String email) {

        String tag_get_id = "Request get ID: ";

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptGetId(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "RESPOSTA FIND ID: " + response);
                callbackLogin.callbackGetId(tipoUsuario, response);


            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());
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

        AppController.getInstance().addToRequestQueue(request, tag_get_id);
    }

    /* METODOS QUE RESTAURAM O USUÁRIO DE ACORDO COM A CONTA*/

    /*MÉTODO QUE FAZ UM REQUEST NO BANCO COM O E-MAIL DO USUÁRIO QUANDO O USUÁRIO LOGA
    PARA PEGAR O NOME REFERENTE AO E-MAIL E GUARDA ESSE NOME EM UMA SHARED PREFERENCE
    PARA USAR O NOME DELE NO PERFIL*/
    public void getNomeUsuario(final String tipoUsuario, final String id)
    {
        final String tag_get_nome = "Request getNome: ";

        StringRequest requestNome = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptGetNome(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, tag_get_nome + " " + response);
                callbackLogin.callbackGetNome(response);
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("ID_USUARIO", id);
                hashMap.put("TIPO_USUARIO", tipoUsuario);
                return hashMap;
            }
        };

        AppController.upRequestCount();
        AppController.getInstance().addToRequestQueue(requestNome, tag_get_nome);
    }

    public void getTempoEstudo(final String tipoUsuario, final String id) {

        final String tag = "get_tempo_estudo: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetTempoEstudo(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag + " " + response);
                callbackLogin.callbackGetTempoEstudo(response);
            }

        }, new Response.ErrorListener()
        {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.d(TAG, error.getMessage());
                }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> values = new HashMap<>();
                values.put("TIPO_USUARIO", tipoUsuario);
                values.put("ID_USUARIO", id);
                return values;
            }
        };

        AppController.upRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void getEnderecoFoto(final String tipoUsuario, final String id) {

        final String tag = "tag_get_endereco_foto: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetCaminhoFoto(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, tag + " " + response);
                callbackLogin.callbackGetEnderecoFoto(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> values = new HashMap<>();
                values.put("TIPO_USUARIO", tipoUsuario);
                values.put("ID_USUARIO", id);

                return values;
            }
        };

        AppController.upRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void getEstadoCertificado(final String tipoUsuario, final String id) {

        final String tag = "request_get_estado_certificado: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetEstadoCertificado(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag + " " + response);
                callbackLogin.callbackGetEstadoCertificado(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> values = new HashMap<>();
                values.put("TIPO_USUARIO", tipoUsuario);
                values.put("ID_USUARIO", id);
                return values;
            }
        };

        AppController.upRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }



    /* JSON REQUESTS*/

    public void getProgressoUsuario(final String tipoUsuario, final String id) {

        Map<String, String> params = new HashMap<>();
        params.put("TIPO_USUARIO", tipoUsuario);
        params.put("ID_USUARIO", id);


        final String tag = "request_json_progresso: ";

        CustomJSONRequest request = new CustomJSONRequest
                (Request.Method.POST, StringsBanco.getScriptGetProgresso(), params,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, tag + " SUCESSO:" + response.toString());

                        callbackLogin.callbackGetProgresso(response);



                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, tag + " " + error.toString());
                        error.getCause();
                        error.printStackTrace();
                    }
                })
                {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    //headers.put("Content-Type", "application/json");
                    headers.put("TIPO_USUARIO", tipoUsuario);
                    headers.put("ID_USUARIO", id);
                    return headers;
                    }

                };


        AppController.upRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }

    public void getPontuacaoUsuario(String tipoUsuario, String id) {
        AppController.upRequestCount();
    }

    public void getConquistasUsuario(String tipoUsuario, String id) {
        AppController.upRequestCount();
    }

}
