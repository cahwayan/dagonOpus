package com.tcc.dagon.opus.PerfilUsuario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.tcc.dagon.opus.LoginActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.StringsBanco;
import com.tcc.dagon.opus.utils.ValidarEmail;

import java.util.HashMap;
import java.util.Map;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private Button btRecuperar;
    private EditText emailRec;
    private String sEmailRec;
    private com.tcc.dagon.opus.StringsBanco StringsBanco = new StringsBanco();
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        // SETA VOLTAR NA BARRA DE MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btRecuperar = (Button)findViewById(R.id.btRecuperar);
        emailRec = (EditText)findViewById(R.id.edtRecuperar);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        listeners();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void listeners() {
        btRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmailRec = emailRec.getText().toString();
                if(sEmailRec.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Digite seu e-mail", Toast.LENGTH_SHORT).show();
                } else if(!ValidarEmail.validarEmail(sEmailRec)) {
                    Toast.makeText(getApplicationContext(), "Por favor, digite um e-mail válido", Toast.LENGTH_SHORT).show();
                } else {
                    StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.recuperarSenha, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Um link foi gerado e enviado ao seu textEmail !", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Erro de conexão.", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("EMAIL_USUARIO", sEmailRec);
                            return parameters;
                        }
                    };

                    // Abre a tela de login após informar o e-mail
                    requestQueue.add(request);
                }



            }
        });
    }


}
