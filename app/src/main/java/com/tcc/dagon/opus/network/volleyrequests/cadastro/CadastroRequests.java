package com.tcc.dagon.opus.network.volleyrequests.cadastro;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tcc.dagon.opus.app.AppController;
import com.tcc.dagon.opus.ui.usuario.StringsBanco;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cahwayan on 29/03/2017.
 */

public class CadastroRequests {

    private final String TAG = this.getClass().getSimpleName();

    private CallbackCadastro callbackCadastro;

    public CadastroRequests(Context context) {
        this.callbackCadastro = (CallbackCadastro) context;
    }

    public void cadastrarUsuario(final String tipoUsuario, final String email, final String senha, final String nome)
    {

        String tag_cadastro_usuario = "request_cadastrar_usuario " + tipoUsuario;

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptCadastro(), new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                callbackCadastro.onCadastro(response);
                Log.d(TAG, "Response Cadastro: " + response);
            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {
                callbackCadastro.onCadastro(error.getMessage());
                Log.d(TAG, error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("TIPO_USUARIO",  tipoUsuario);
                parameters.put("EMAIL_USUARIO", email);
                parameters.put("SENHA_USUARIO", senha);
                parameters.put("NOME_USUARIO",  nome);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(request, tag_cadastro_usuario);

    }

    public void usuarioExiste(final String tipoUsuario, final String email) {

        String tag_usuario_existe = "request_usuario_existe";

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getScriptUsuarioExiste(), new Response.Listener<String>()

        {
            @Override
            public void onResponse(String response)
            {
                callbackCadastro.onUsuarioExiste(response);

            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, error.getMessage());
                callbackCadastro.onUsuarioExiste(error.getMessage());
            }
        })
        {
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parameters = new HashMap<>();
                parameters.put("TIPO_USUARIO", tipoUsuario);
                parameters.put("EMAIL_USUARIO", email);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(request, tag_usuario_existe);

    }
}
