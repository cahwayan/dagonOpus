package com.tcc.dagon.opus;

import android.content.Context;
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
import com.tcc.dagon.opus.Modulos.Modulo_1;

import java.io.File;
import java.io.IOException;

/**
 * Created by Otávio Paulino on 21/09/2016.
 */
public class InicialActivity extends AppCompatActivity {

    private Button login, cadastra, modulos, botaoBloquear, botaoDesbloquear, botaoDesbloquearEtapas, botaoBloquearEtapas,Modulo1;
    private TextView txtInicial;
    RequestQueue requesQueue;
    GerenciadorBanco DB_PROGRESSO;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        DB_PROGRESSO = new GerenciadorBanco(this);
        txtInicial              = (TextView)findViewById(R.id.txtInicial);
        login                   = (Button) findViewById(R.id.btn_Login);
        cadastra                = (Button) findViewById(R.id.btn_cadastra);
        modulos                 = (Button) findViewById(R.id.btn_modulos);
        botaoBloquear           = (Button) findViewById(R.id.botaoBloquear);
        botaoDesbloquear        = (Button) findViewById(R.id.botaoDesbloquear);
        botaoDesbloquearEtapas  = (Button) findViewById(R.id.botaoDesbloquearEtapas);
        botaoBloquearEtapas     = (Button) findViewById(R.id.botaoBloquearEtapas);
        Modulo1              = (Button) findViewById(R.id.botaoModulo1);
        requesQueue             = Volley.newRequestQueue(getApplicationContext());


        Modulo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mdl1 = new Intent(InicialActivity.this,Modulo_1.class);
                startActivity(mdl1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(InicialActivity.this, com.tcc.dagon.opus.MainActivity.class);
                startActivity(login);
            }
        });


        cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastra = new Intent(InicialActivity.this, com.tcc.dagon.opus.CadastroActivity.class);
                startActivity(cadastra);
            }
        });

        modulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modulos = new Intent(InicialActivity.this, com.tcc.dagon.opus.AprenderActivity.class);
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
                DB_PROGRESSO.atualizaProgressoModulo(9);
            }
        });

        botaoDesbloquearEtapas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoEtapa(1,9);
                DB_PROGRESSO.atualizaProgressoEtapa(2,10);
                DB_PROGRESSO.atualizaProgressoEtapa(3,7);
                DB_PROGRESSO.atualizaProgressoEtapa(4,5);
                DB_PROGRESSO.atualizaProgressoEtapa(5,6);
                DB_PROGRESSO.atualizaProgressoEtapa(6,7);
                DB_PROGRESSO.atualizaProgressoEtapa(7,10);
                DB_PROGRESSO.atualizaProgressoEtapa(8,10);
            }
        });

        botaoBloquearEtapas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoEtapa(1,0);
                DB_PROGRESSO.atualizaProgressoEtapa(2,0);
                DB_PROGRESSO.atualizaProgressoEtapa(3,0);
                DB_PROGRESSO.atualizaProgressoEtapa(4,0);
                DB_PROGRESSO.atualizaProgressoEtapa(5,0);
                DB_PROGRESSO.atualizaProgressoEtapa(6,0);
                DB_PROGRESSO.atualizaProgressoEtapa(7,0);
                DB_PROGRESSO.atualizaProgressoEtapa(8,0);
            }
        });

    }

}
