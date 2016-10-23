package com.tcc.dagon.opus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RecuperarSenhaActivity extends AppCompatActivity {

    private Button btRecuperar;
    private EditText emailRec;
    private String sEmailRec;
    private StringsBanco StringsBanco = new StringsBanco();
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        btRecuperar = (Button)findViewById(R.id.btRecuperar);
        emailRec = (EditText)findViewById(R.id.edtRecuperar);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEmailRec = emailRec.getText().toString();
                StringRequest request = new StringRequest(Request.Method.POST, StringsBanco.recuperarSenha, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Um link foi gerado e enviado ao seu email !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Verifique o email digitado.", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("EMAIL_USUARIO", sEmailRec);
                        return parameters;
                    }
                };

                // Abre a tela de login após cadastro
                requestQueue.add(request);


            }
        });
    }
}
