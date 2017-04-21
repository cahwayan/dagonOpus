package com.tcc.dagon.opus.network.volleyrequests.usuario;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.tcc.dagon.opus.application.AppController;
import com.tcc.dagon.opus.network.volleyrequests.CustomJSONRequest;
import com.tcc.dagon.opus.network.volleyrequests.BancoRemoto;
import com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants;

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

    public RequestsUsuario(String tipoUsuario, UsuarioListener activity, String email) {
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.usuarioListener = activity;
    }

    public RequestsUsuario(String tipoUsuario, String id, UsuarioListener activity) {
        this.tipoUsuario = tipoUsuario;
        this.usuarioListener = activity;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void getID() {

        final String tag_get_id = "Request get ID: ";

        /* INÍCIO REQUEST*/
        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptGetId(), new Response.Listener<String>()
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
                Request.Method.POST, BancoRemoto.getScriptGetNome(), new Response.Listener<String>()
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

    public void selectTempoEstudo() {

        final String tag = "get_tempo_estudo: ";

        StringRequest request = new StringRequest(Request.Method.POST, BancoRemoto.getScriptTempoEstudo(), new Response.Listener<String>() {
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
                values.put("ACTION", BancoRemoto.Action.SELECT);
                values.put("COLUNA", BancoRemoto.Tabelas.TempoEstudo.COL_TEMPO_ESTUDO);
                return values;
            }
        };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void updateTempoEstudo(final String novoTempo) {

        final String tag = "request_update_tempo_estudo";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptTempoEstudo(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(tag, response);
                        usuarioListener.onUpdate(response);
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                })
                {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> values = new HashMap<>();

                        values.put("ID_USUARIO", id);
                        values.put("TIPO_USUARIO", tipoUsuario);
                        values.put("COLUNA", BancoRemoto.Tabelas.TempoEstudo.COL_TEMPO_ESTUDO);
                        values.put("ACTION", BancoRemoto.Action.UPDATE);
                        values.put("VALOR", novoTempo);

                        return values;
                    }
                };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void selectEnderecoFoto() {

        final String tag = "tag_get_endereco_foto: ";

        StringRequest request = new StringRequest(Request.Method.POST, BancoRemoto.getScriptCaminhoFoto(), new Response.Listener<String>() {

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
                values.put("ACTION", BancoRemoto.Action.SELECT);
                values.put("COLUNA", BancoRemoto.Tabelas.EndFoto.COL_END_FOTO);
                return values;
            }
        };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void updateEnderecoFoto(final String novoEndereco) {

        final String tag = "request_endereco_foto";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptCaminhoFoto(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(tag, response);
                        usuarioListener.onUpdate(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                })
                {

                    @Override
                    protected HashMap<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> values = new HashMap<>();
                        values.put("ID_USUARIO", id);
                        values.put("TIPO_USUARIO", tipoUsuario);
                        values.put("ACTION", BancoRemoto.Action.UPDATE);
                        values.put("COLUNA", BancoRemoto.Tabelas.EndFoto.COL_END_FOTO);
                        values.put("VALOR", novoEndereco);
                        return values;
                    }
                };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }


    public void selectEstadoCertificado() {

        final String tag = "request_get_estado_certificado: ";

        StringRequest request = new StringRequest(Request.Method.POST, BancoRemoto.getScriptEstadoCertificado(), new Response.Listener<String>() {
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
                values.put("ACTION", BancoRemoto.Action.SELECT);
                values.put("COLUNA", BancoRemoto.Tabelas.Certificado.COL_ESTADO_CERTIFICADO);
                return values;
            }
        };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }

    public void updateEstadoCertificado(final int novoEstado) {

        final String tag = "request_endereco_foto";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptCaminhoFoto(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(tag, response);
                        usuarioListener.onUpdate(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                })
        {

            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> values = new HashMap<>();
                values.put("ID_USUARIO", id);
                values.put("TIPO_USUARIO", tipoUsuario);
                values.put("ACTION", BancoRemoto.Action.UPDATE);
                values.put("COLUNA", BancoRemoto.Tabelas.EndFoto.COL_END_FOTO);
                values.put("VALOR", String.valueOf(novoEstado));
                return values;
            }
        };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    /* JSON REQUESTS*/

    public void selectProgresso() {

        Map<String, String> params = new HashMap<>();
        params.put("TIPO_USUARIO", tipoUsuario);
        params.put("ID_USUARIO", id);
        params.put("ACTION", BancoRemoto.Action.SELECTALL);

        final String tag = "request_json_progresso: ";

        CustomJSONRequest request = new CustomJSONRequest
                (Request.Method.POST, BancoRemoto.getScriptProgresso(), params,

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

    public void updateProgresso(final int numModulo, final int valorProgresso) {

        final String tag = "request_json_update_progresso: ";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptProgresso(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(tag, response);
                        usuarioListener.onUpdate(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(tag, error.toString());
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("TIPO_USUARIO", tipoUsuario);
                        params.put("ID_USUARIO", id);
                        params.put("ACTION", BancoRemoto.Action.UPDATE);
                        params.put("COLUNA", BancoRemoto.Tabelas.Progresso.getColunaProgressoEtapas(numModulo));
                        params.put("VALOR", String.valueOf(valorProgresso));

                        return params;
                    }
                };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }

    public void selectPontuacao() {

        final String tag = "request_get_pontuacao: ";

        Map<String, String> params = new HashMap<>();
        params.put("TIPO_USUARIO", tipoUsuario);
        params.put("ID_USUARIO", id);
        params.put("ACTION", BancoRemoto.Action.SELECTALL);

        CustomJSONRequest request = new CustomJSONRequest
                (Request.Method.POST, BancoRemoto.getScriptPontuacao(), params,
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

    public void updatePontuacao(final int numModulo, final int pontos) {

        final String tag = "request_json_update_pontuacao: ";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptProgresso(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(tag, response);
                        usuarioListener.onUpdate(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(tag, error.toString());
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TIPO_USUARIO", tipoUsuario);
                params.put("ID_USUARIO", id);
                params.put("ACTION", BancoRemoto.Action.UPDATE);
                params.put("COLUNA", BancoRemoto.Tabelas.Pontuacao.getColunaPontuacao(numModulo));
                params.put("VALOR", String.valueOf(pontos));

                return params;
            }
        };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);
    }


    public void selectConquistas() {

        final String tag = "request_get_conquistas: ";

        Map<String, String> params = new HashMap<>();
        params.put("TIPO_USUARIO", tipoUsuario);
        params.put("ID_USUARIO", id);
        params.put("ACTION", BancoRemoto.Action.SELECTALL);

        CustomJSONRequest request = new CustomJSONRequest
                (Request.Method.POST, BancoRemoto.getScriptConquistas(), params,
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

    public void updateConquistas(final int idConquista, final int valorConquista) {

        final String tag = "request_json_update_conquista: ";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptProgresso(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(tag, response);
                        usuarioListener.onUpdate(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(tag, error.toString());
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TIPO_USUARIO", tipoUsuario);
                params.put("ID_USUARIO", id);
                params.put("ACTION", BancoRemoto.Action.UPDATE);
                params.put("COLUNA", BancoRemoto.Tabelas.Conquistas.getColunaConquista(idConquista));
                params.put("VALOR", String.valueOf(valorConquista));

                return params;
            }
        };

        AppController.increaseRequestCount();
        AppController.getInstance().addToRequestQueue(request, tag);

    }

}
