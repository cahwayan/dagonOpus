package com.tcc.dagon.opus.utils.volley;

import android.app.Activity;
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

public class CadastroRequestHandler extends VolleyRequest {

    private final String TAG = this.getClass().getSimpleName();

    private CallbackCadastro callbackCadastro;

    public CadastroRequestHandler(Context context) {
        super(context);
        this.callbackCadastro = (CallbackCadastro) context;
    }

    public void cadastrarUsuario(final String tipoUsuario, final String email, final String senha, final String nome)
    {

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getInsereUrl(), new Response.Listener<String>()
        {
            public void onResponse(String response)
            {
                callbackCadastro.callbackCadastro(response);
                Log.d(TAG, "Response Cadastro: " + response);
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
                parameters.put("TIPO_USUARIO",  tipoUsuario);
                parameters.put("EMAIL_USUARIO", email);
                parameters.put("SENHA_USUARIO", senha);
                parameters.put("NOME_USUARIO",  nome);
                return parameters;
            }
        };

        requestQueue.add(request);

    }

    public void usuarioExiste(final String tipoUsuario, final String email) {

        StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.getUsuarioExiste(), new Response.Listener<String>()

        {
            @Override
            public void onResponse(String response)
            {
                boolean usuarioExiste = response.equals("sim");

                callbackCadastro.callbackEmailExiste(usuarioExiste);

            }
        }, new Response.ErrorListener()
        {
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "Erro de resposta ao verificar se usuario existe. Script: scriptVerificarUsuarioExiste");
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

        requestQueue.add(request);

    }
}
