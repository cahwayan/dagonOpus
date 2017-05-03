package com.tcc.dagon.opus.network.volleyrequests.usuario;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tcc.dagon.opus.application.AppController;
import com.tcc.dagon.opus.common.ConexaoChecker;
import com.tcc.dagon.opus.network.volleyrequests.CustomJSONRequest;
import com.tcc.dagon.opus.network.volleyrequests.BancoRemoto;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cahwayan on 05/04/2017.
 * Lida com os requests no banco remoto através da biblioteca volley
 */

public class RequestsUsuario {

    private final String TAG = this.getClass().getSimpleName();

    // Listener que lida com os callbacks dos requests. Precisa ser implementado na classe cliente.
    private UsuarioListener usuarioListener;

    // Listener que responde ao método de sincronizar
    private SyncUserListener syncUserListener;

    // Informações do usuário
    private String tipoUsuario;
    private String id;
    private String email;

    private Context context;

    /**
     * Construtor para login
     * @param tipoUsuario: Definido na constante da classe BancoRemoto, define o tipo de login
     * @param usuarioCallbacks: Uma implementação da interface UsuarioListener, para lidar com os callbacks
     * @param email: o email do usuário. No momento do login, é o único recurso que a classe tem para encontrar o ID do usuário
     */
    public RequestsUsuario(String tipoUsuario, UsuarioListener usuarioCallbacks, String email) {
        this.tipoUsuario = tipoUsuario;
        this.email = email;
        this.usuarioListener = usuarioCallbacks;
    }

    /**
     * Construtor para requests gerais ao longo do app.
     * @param tipoUsuario: Definido na constante da classe BancoRemoto, define o tipo de login
     * @param usuarioCallbacks: Uma implementação da interface UsuarioListener, para lidar com os callbacks
     * @param id: o ID do usuário
     */
    public RequestsUsuario(String tipoUsuario, String id, UsuarioListener usuarioCallbacks) {
        this.tipoUsuario = tipoUsuario;
        this.usuarioListener = usuarioCallbacks;
        this.id = id;
    }

    public RequestsUsuario(Context context, String tipoUsuario, String id) {
        this.tipoUsuario = tipoUsuario;
        this.id = id;
        this.context = context;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSyncUserListener(SyncUserListener syncUserListener) {
        this.syncUserListener = syncUserListener;
    }

    /**
     * STRING REQUESTS
     * Esses requests retornam strings como resposta do banco remoto */

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

        AppController.getInstance().addToRequestQueue(request, tag_get_id);

    }

    // Busca o nome do usuário no banco remoto
    public void getNome() {
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

        AppController.getInstance().addToRequestQueue(requestNome, tag_get_nome);

    }

    // Busca o tempo de estudo no banco remoto
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

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Atualiza o tempo de estudo no banco remoto
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

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Busca o endereço da foto do usuário no cartão SD no banco remoto
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

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Atualiza o endereço da foto no banco remoto
    public void updateEnderecoFoto(final String novoEndereco) {

        final String tag = "request_update_endereco_foto";

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

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Busca o estado do certificado do usuário no banco remoto
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

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Atualiza o estado do certificado do usuário no banco remoto
    public void updateEstadoCertificado(final int novoEstado) {

        final String tag = "request_endereco_foto";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptEstadoCertificado(),
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

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    /**
     * SELECT COM JSON REQUESTS
     * Esses requests retornam um objeto JSON do banco remoto com os dados, para poder pegar todos
     * os dados em um request só.
     *
     * Os updates são realizados com string requests pois as colunas são atualizadas uma a uma
     * ao longo do uso do app. */

    // Busca o progresso do usuário no banco de dados remoto
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
                                usuarioListener.onErrorResponse(tag, error.toString());
                            }
                        });

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Atualiza o progresso do usuário (String request)
    public void updateProgressoModulo(final int valorProgresso) {

        final String tag = "request_update_progresso_modulo: ";

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
                params.put("COLUNA", BancoRemoto.Tabelas.Progresso.COL_PROGRESSO_MODULOS);
                params.put("VALOR", String.valueOf(valorProgresso));

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Atualiza o progresso do usuário (String request)
    public void updateProgressoEtapa(final int numModulo, final int valorProgresso) {

        final String tag = "request_update_progresso_etapa: ";

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

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Busca a pontuação dos módulos do usuário no banco remoto
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
                                usuarioListener.onErrorResponse(tag, error.toString());
                            }
                        });

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Atualiza a pontuação do usuário em determinado módulo no banco remoto (String request)
    public void updatePontuacao(final int numModulo, final int pontos) {

        final String tag = "request_update_pontuacao: ";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptPontuacao(),
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
                Map<String, String> params = new HashMap<>();
                params.put("TIPO_USUARIO", tipoUsuario);
                params.put("ID_USUARIO", id);
                params.put("ACTION", BancoRemoto.Action.UPDATE);
                params.put("COLUNA", BancoRemoto.Tabelas.Pontuacao.getColunaPontuacao(numModulo));
                params.put("VALOR", String.valueOf(pontos));

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Busca as conquistas do usuário no banco remoto
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
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                });

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Atualiza as conquistas do usuário no banco remoto
    public void updateConquistas(final int idConquista, final int valorConquista) {

        final String tag = "request_json_update_conquista: ";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptConquistas(),
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
                Map<String, String> params = new HashMap<>();
                params.put("TIPO_USUARIO", tipoUsuario);
                params.put("ID_USUARIO", id);
                params.put("ACTION", BancoRemoto.Action.UPDATE);
                params.put("COLUNA", BancoRemoto.Tabelas.Conquistas.getColunaConquista(idConquista));
                params.put("VALOR", String.valueOf(valorConquista));

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    // Reseta o progresso atual do usuário
    public void resetDadosUsuario() {

        final String tag = "request_resetar_progresso: ";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptDeleteDadosUsuario(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, tag + " " + response);
                        usuarioListener.onReset(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        usuarioListener.onErrorResponse(tag, error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("ID_USUARIO", id);
                        params.put("TIPO_USUARIO", tipoUsuario);
                        params.put("ACTION", BancoRemoto.Action.RESET);
                        return params;
                    }
                };

        AppController.getInstance().addToRequestQueue(request, tag);

    }

    public void syncUser() {

        if(ConexaoChecker.verificarSeHaConexaoDisponivel(this)) {

        final String tag = "request_sync_user: ";

        StringRequest request = new StringRequest(
                Request.Method.POST, BancoRemoto.getScriptSyncUser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        syncUserListener.onSyncSuccess(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        syncUserListener.onSyncError(tag, error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return new UserInfoMap(context).getUserInfoMap();
            }
        };

        AppController.getInstance().addToRequestQueue(request, tag);


    }

}
