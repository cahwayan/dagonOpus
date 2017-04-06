package com.tcc.dagon.opus.network.volleyrequests.usuario;

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

    private UsuarioListener usuarioListener;
    private String tipoUsuario;
    private String id;
    private String email;

    public RequestsUsuario(String tipoUsuario, String email, UsuarioListener activity) {
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.usuarioListener = activity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void getID() {

        final String tag_get_id = "Request get ID: ";

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptGetId(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, "RESPOSTA FIND ID: " + response);

                if(response.equals("erroid")) {
                    usuarioListener.onErrorResponse(tag_get_id, response);
                } else {
                    id = response;
                    usuarioListener.onGetId(response);
                }

            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());

                usuarioListener.onErrorResponse(tag_get_id, error.toString());
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

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag_get_id);
    }

    /* METODOS QUE RESTAURAM O USUÁRIO DE ACORDO COM A CONTA*/

    /*MÉTODO QUE FAZ UM REQUEST NO BANCO COM O E-MAIL DO USUÁRIO QUANDO O USUÁRIO LOGA
    PARA PEGAR O NOME REFERENTE AO E-MAIL E GUARDA ESSE NOME EM UMA SHARED PREFERENCE
    PARA USAR O NOME DELE NO PERFIL*/
    public void getNome()
    {
        final String tag_get_nome = "Request getNome: ";

        StringRequest requestNome = new StringRequest(
                Request.Method.POST, StringsBanco.getScriptGetNome(), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, tag_get_nome + " " + response);

                if(response.equals("erroNome")) {
                    usuarioListener.onErrorResponse(tag_get_nome, response);
                } else {
                    usuarioListener.onGetNome(response);
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());
                usuarioListener.onErrorResponse(tag_get_nome, error.toString());
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

    public void getTempoEstudo() {

        final String tag = "get_tempo_estudo: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetTempoEstudo(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag + " " + response);

                if(response.equals("erroTempoEstudo")) {
                    usuarioListener.onErrorResponse(tag, response);
                } else {
                    usuarioListener.onGetTempoEstudo(response);
                }

            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());
                usuarioListener.onErrorResponse(tag, error.toString());
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

    public void getEnderecoFoto() {

        final String tag = "tag_get_endereco_foto: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetCaminhoFoto(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                Log.d(TAG, tag + " " + response);

                if(response.equals("erroCaminhoFoto")) {
                    usuarioListener.onErrorResponse(tag, response);
                } else {
                    usuarioListener.onGetEnderecoFoto(response);
                }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());
                usuarioListener.onErrorResponse(tag, error.toString());
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

    public void getEstadoCertificado() {

        final String tag = "request_get_estado_certificado: ";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptGetEstadoCertificado(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, tag + " " + response);
                if(response.equals("erroCertificado")) {
                    usuarioListener.onErrorResponse(tag, response);
                } else {
                    usuarioListener.onGetEstadoCertificado(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getMessage());
                usuarioListener.onErrorResponse(tag, error.toString());
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

    public void getProgresso() {

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
                                if(response.toString().equals("erroProgresso")) {
                                    usuarioListener.onErrorResponse(tag, response.toString());
                                } else {
                                    usuarioListener.onGetProgresso(response);
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, tag + " " + error.toString());
                                usuarioListener.onErrorResponse(tag, error.toString());
                            }
                        });

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }

    public void getPontuacao() {

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

                                if(response.toString().equals("erroPontuacao")) {
                                    usuarioListener.onErrorResponse(tag, response.toString());
                                } else {
                                    usuarioListener.onGetPontuacao(response);
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, tag + " " + error.toString());
                                usuarioListener.onErrorResponse(tag, error.toString());
                            }
                        });

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void getConquistas() {

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

                        if(response.toString().equals("erroConquistas")) {
                            usuarioListener.onErrorResponse(tag, response.toString());
                        } else {
                            usuarioListener.onGetConquistas(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                });


        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }


}
