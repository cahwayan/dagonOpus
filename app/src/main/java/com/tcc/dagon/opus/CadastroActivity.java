package com.tcc.dagon.opus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
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

import java.util.HashMap;
import java.util.Map;


public class CadastroActivity extends AppCompatActivity {

    //Instanciando objetos

    private StringsBanco StringsBanco = new StringsBanco();

    //Declarando botões e elementos da tela
    private Button btn_cadastra;
    private TextView nome, senha, csenha, email;
    private TextView txtCadastro;

    // String dos componentes
    private String sNome, sSenha, sCsenha, sEmail;

    //Declarando variavel de conexao
    RequestQueue requestQueue;
    private StringRequest request;
    private ProgressDialog progresso;

    // metodo construtor
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        //BOTÕES
        btn_cadastra = (Button) findViewById(R.id.btn_cadastra);
        //TEXT VIEWS
        txtCadastro = (TextView)findViewById(R.id.txtCadastro);
        Typeface font_adam = Typeface.createFromAsset(getAssets(), "fonts/adam.otf");
        txtCadastro.setTypeface(font_adam);

        nome   = (TextView) findViewById(R.id.textNome);
        senha  = (TextView) findViewById(R.id.textSenha);
        csenha = (TextView) findViewById(R.id.textCSenha);
        email  = (TextView) findViewById(R.id.textEmail);


        //VARIAVEIS DE CONEXAO
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // CHAMANDO OS LISTENERS
        listenersOnClick();
    }

    // MÉTODO QUE CONFIGURA OS LISTENERS DOS COMPONENTES
    public void listenersOnClick() {
        // BOTÃO CADASTRAR
        btn_cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        }
                    }, new Response.ErrorListener() {


                        public void onErrorResponse(VolleyError error) {
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
                    progresso.dismiss();
                    // Abre a tela de login após cadastro
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    requestQueue.add(request);

                }
            }
        });


    }

}
