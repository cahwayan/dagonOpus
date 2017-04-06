package com.tcc.dagon.opus.network.volleyrequests.usuario;

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
 * Created by cahwayan on 05/04/2017.
 */

public class RequestsUsuario {

    private final String TAG = this.getClass().getSimpleName();

    private CallbackUsuario callbackUsuario;

    public RequestsUsuario(Context context) {
        this.callbackUsuario = (CallbackUsuario) context;
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
                callbackUsuario.callbackGetId(tipoUsuario, response);
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
    public void getNome(final String tipoUsuario, final String id)
    {
        final String tag_get_nome = "Request getNome: ";

        StringRequest requestNome = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptGetNome(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, tag_get_nome + " " + response);
                callbackUsuario.callbackGetNome(response);
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

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(requestNome, tag_get_nome);
    }

    public void getTempoEstudo(final String tipoUsuario, final String id) {

        final String tag = "get_tempo_estudo: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetTempoEstudo(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag + " " + response);
                callbackUsuario.callbackGetTempoEstudo(response);
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

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void getEnderecoFoto(final String tipoUsuario, final String id) {

        final String tag = "tag_get_endereco_foto: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetCaminhoFoto(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, tag + " " + response);
                callbackUsuario.callbackGetEnderecoFoto(response);
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

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void getEstadoCertificado(final String tipoUsuario, final String id) {

        final String tag = "request_get_estado_certificado: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetEstadoCertificado(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag + " " + response);
                callbackUsuario.callbackGetEstadoCertificado(response);
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

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }

    /* JSON REQUESTS*/

    public void getProgresso(final String tipoUsuario, final String id) {

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
                                callbackUsuario.callbackGetProgresso(response);
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, tag + " " + error.toString());
                                error.getCause();
                                error.printStackTrace();
                            }
                        });

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }

    public void getPontuacao(final String tipoUsuario, final String id) {

        final String tag = "request_get_pontuacao: ";

        Map<String, String> params = new HashMap<>();
        params.put("TIPO_USUARIO", tipoUsuario);
        params.put("ID_USUARIO", id);

        CustomJSONRequest request = new CustomJSONRequest
                (Request.Method.POST, StringsBanco.getScriptGetPontuacao(), params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, tag + " Sucesso: " + response.toString());
                                callbackUsuario.callbackGetPontuacao(response);
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void getConquistas(String tipoUsuario, String id) {

        final String tag = "request_get_conquistas: ";

        Map<String, String> params = new HashMap<>();
        params.put("TIPO_USUARIO", tipoUsuario);
        params.put("ID_USUARIO", id);

        CustomJSONRequest request = new CustomJSONRequest
                (Request.Method.POST, StringsBanco.getScriptGetConquistas(), params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, tag + " SUCESSO: " + response.toString());
                        callbackUsuario.callbackGetConquistas(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });


        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }


}
