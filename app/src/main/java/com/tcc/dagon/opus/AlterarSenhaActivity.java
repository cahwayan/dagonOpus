package com.tcc.dagon.opus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AlterarSenhaActivity extends AppCompatActivity {
    private Button btAlterar;
    private EditText senhaAtual,edtRec,edtRecConfirm;
    private String emailAlterar,strSenha,strSenhaConfirma,strSenhaAnt;
    private StringsBanco StringsBanco = new StringsBanco();
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);

        Intent i = getIntent();
        emailAlterar = i.getStringExtra("emailUsuario");

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        btAlterar= (Button)findViewById(R.id.btAlterarSenha);

        senhaAtual = (EditText)findViewById(R.id.edtSenhaAtual);
        edtRec =(EditText)findViewById(R.id.edtNovaSenha);
        edtRecConfirm= (EditText)findViewById(R.id.edtConfirmaSenha);
        listClick();

    }
    public void listClick(){
    btAlterar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(strSenha.equals(strSenhaConfirma)){

                StringRequest request = new StringRequest(Request.Method.POST, "http://dagonopus.esy.es/phpAndroid/alterarSenha.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("certo")){
                            Intent i = new Intent(AlterarSenhaActivity.this, GerenciarPerfilActivity.class);
                            startActivity(i);
                        }else{

                            Toast.makeText(getApplicationContext(),
                                    "Confirme a nova senha por favor",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erro de conexão.", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("EMAIL_USUARIO", emailAlterar);
                        parameters.put("SENHA_USUARIO", edtRec.getText().toString());
                        parameters.put("SENHA_NOVA", edtRecConfirm.getText().toString());
                        return parameters;
                    }
                };

                requestQueue.add(request);
            }else{
                Toast.makeText(getApplicationContext(),
                        "Verifique os campos digitados, a senha deve ser igual",
                        Toast.LENGTH_SHORT).show();

            }
        }


    });

    }
}
