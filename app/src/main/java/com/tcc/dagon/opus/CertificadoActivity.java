package com.tcc.dagon.opus;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class CertificadoActivity extends AppCompatActivity {

    private Button btnCertificado;
    private StringRequest request;
    private RequestQueue requestQueue;
    StringsBanco StringsBanco = new StringsBanco();
    GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);
    String sEmail, sNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // VOLLEY
        requestQueue  = Volley.newRequestQueue(this);

        Typeface harabara = Typeface.createFromAsset(getAssets(), "fonts/harabara.ttf");

        btnCertificado = (Button) findViewById(R.id.btnCertificado);
        if(btnCertificado != null) {
            btnCertificado.setTypeface(harabara);
        }

        btnCertificado.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sEmail = preferencias.lerFlagString(GerenciadorSharedPreferences.NomePreferencia.emailUsuario);
                sNome = preferencias.lerFlagString(GerenciadorSharedPreferences.NomePreferencia.nomeUsuario);

                request = new StringRequest(Request.Method.POST,
                        StringsBanco.certificadoUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.trim().equals("certo")){
                                    Toast.makeText(getApplicationContext(), "Seu pedido de certificado foi aberto. Aguarde a chegada no seu e-mail cadastrado!", Toast.LENGTH_LONG).show();
                                    preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.NomePreferencia.flagCertificadoGerado, true);
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),
                                            "Ocorreu um erro ao gerar o pedido de certificado" + response,
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erro ao conectar. Verifique sua conexão e tente novamente.", Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("EMAIL_USUARIO", sEmail);
                        hashMap.put("NOME_USUARIO", sNome);

                        return hashMap;
                    }

                };
                requestQueue.add(request);

            }
        });

    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
