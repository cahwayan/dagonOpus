package com.tcc.dagon.opus;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.databases.GerenciadorBanco;


/**
 * Created by Otávio Paulino on 21/09/2016.
 */
public class InicialActivity extends AppCompatActivity {

    private Button login, cadastra, modulos, botaoBloquear, botaoDesbloquear;
    private TextView txtInicial;
    RequestQueue requesQueue;
    GerenciadorBanco DB_PROGRESSO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        DB_PROGRESSO = new GerenciadorBanco(this);
        txtInicial = (TextView)findViewById(R.id.txtInicial);
        login = (Button) findViewById(R.id.btn_Login);
        cadastra = (Button) findViewById(R.id.btn_cadastra);
        modulos = (Button) findViewById(R.id.btn_modulos);
        botaoBloquear = (Button) findViewById(R.id.botaoBloquear);
        botaoDesbloquear = (Button) findViewById(R.id.botaoDesbloquear);
        requesQueue = Volley.newRequestQueue(getApplicationContext());


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(InicialActivity.this, MainActivity.class);
                startActivity(login);
            }
        });


        cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastra = new Intent(InicialActivity.this, CadastroActivity.class);
                startActivity(cadastra);
            }
        });

        modulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modulos = new Intent(InicialActivity.this, AprenderActivity.class);
                startActivity(modulos);
            }
        });

        botaoBloquear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoModulo(1);
            }
        });

        botaoDesbloquear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoModulo(8);
            }
        });

    }

}
