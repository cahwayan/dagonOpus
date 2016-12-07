package com.tcc.dagon.opus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;
import com.tcc.dagon.opus.utils.ValidarEmail;

import java.util.HashMap;
import java.util.Map;


public class CadastroActivity extends AppCompatActivity {

    //Instanciando objetos
    private StringsBanco StringsBanco = new StringsBanco();

    //Declarando botões e elementos da tela
    private Button btn_cadastra;
    private TextView nome,
                     senha,
                     csenha,
                     email;

    // String dos componentes
    private String  sNome,
                    sSenha,
                    sCsenha,
                    sEmail;

    //Declarando variavel de conexao
    RequestQueue requestQueue;
    private ProgressDialog progresso;

    private GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //VARIAVEIS DE CONEXAO
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // INVOCANDO OS COMPONENTES
        accessViews();

        // CHAMANDO OS LISTENERS
        listenersOnClick();
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // MÉTODO QUE CONFIGURA OS LISTENERS DOS COMPONENTES
    public void listenersOnClick() {
        // BOTÃO CADASTRAR
        btn_cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrar();

            }
        });
    }

    public void cadastrar() {

        // Strings das TextViews (permite trabalhar os dados com mais facilidade depois)
        sNome   = nome.getText().toString().trim();
        sSenha  = senha.getText().toString().trim();
        sCsenha = csenha.getText().toString().trim();
        sEmail  = email.getText().toString().trim();

        // VERIFICAÇÕES DE CAMPOS VAZIOS

        if ((sNome.matches("")) ||
                sSenha.matches("") ||
                sCsenha.matches("") ||
                sEmail.matches("")) {
            Toast.makeText(getApplicationContext(), "Há campos em branco", Toast.LENGTH_LONG).show();
        } else if (sNome.length() < 3 ) {
            Toast.makeText(getApplicationContext(), "Informe um nome maior que 3 caracteres", Toast.LENGTH_LONG).show();
            nome.requestFocus();
        } else if (!ValidarEmail.validarEmail(sEmail)) {
            // VERIFICAÇÃO DO EMAIL
            Toast.makeText(getApplicationContext(), "Por favor informe um email válido!", Toast.LENGTH_LONG).show();
            email.requestFocus();
        } else if (sSenha.length() < 6) {
            Toast.makeText(getApplicationContext(), "A senha precisa conter no mínimo 6 caracteres", Toast.LENGTH_LONG).show();
            senha.requestFocus();
        } else if (!sSenha.equals(sCsenha)) {
            Toast.makeText(getApplicationContext(), "A senha e a confirmação de senha estão diferentes!", Toast.LENGTH_LONG).show();
            senha.requestFocus();
        } else {
            // MOSTRA JANELA DE PROGRESSO ENQUANTO GRAVA O CADASTRO NO BANCO
            progresso = ProgressDialog.show(CadastroActivity.this, "Cadastrando", "Aguarde", true);
            StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.insereUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    preferencias.escreverFlagString(NomePreferencia.nomeUsuario, sNome);
                    finish();
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Ocorreu um erro ao cadastrar. Por favor verifique sua conexão.", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                //INSERE DADOS EM BANCO DE DADOS, LEMBRANDO QUE DEVE SER PERFEITAMENTE IGUAL OS NOMES DAS TABELAS
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("EMAIL_USUARIO", sEmail);
                    parameters.put("SENHA_USUARIO", sSenha);
                    parameters.put("NOME_USUARIO", sNome);
                    return parameters;
                }
            };

            // Abre a tela de login após cadastro
            requestQueue.add(request);
            progresso.dismiss();
        }

    }

    public void accessViews() {
        //BOTÕES
        btn_cadastra = (Button) findViewById(R.id.btn_cadastra);

        nome   = (TextView) findViewById(R.id.textNome);
        senha  = (TextView) findViewById(R.id.textSenha);
        csenha = (TextView) findViewById(R.id.textCSenha);
        email  = (TextView) findViewById(R.id.textEmail);
    }
}
