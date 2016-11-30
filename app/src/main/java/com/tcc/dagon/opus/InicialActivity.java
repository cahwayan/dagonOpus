package com.tcc.dagon.opus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

/**
 * Created by Otávio Paulino on 21/09/2016.
 */
public class InicialActivity extends AppCompatActivity {

    private Button login, cadastra, modulos, botaoBloquear, botaoDesbloquear, botaoDesbloquearEtapas, botaoBloquearEtapas,Modulo1,
                    botaoDesbloqLicoes, botaoBloqLicoes;

    private Button botaoDesbloquearPrimeiroModulo;

    private TextView txtInicial;
    RequestQueue requesQueue;
    GerenciadorBanco DB_PROGRESSO;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(readFlag()) {
            startActivity(new Intent(this, AprenderActivity.class));

        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
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
        botaoBloqLicoes         = (Button)findViewById(R.id.botaoBloqLicoes);
        botaoDesbloqLicoes      = (Button)findViewById(R.id.botaoDesbloqLicoes);
        requesQueue             = Volley.newRequestQueue(getApplicationContext());
        botaoDesbloquearPrimeiroModulo = (Button)findViewById(R.id.botaoDesbloquearPrimeiroModulo);



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

        botaoDesbloquearPrimeiroModulo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoEtapa(1,9);
            }
        });

        botaoDesbloquearEtapas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoEtapa(1,9);
                DB_PROGRESSO.atualizaProgressoEtapa(2,5);
                DB_PROGRESSO.atualizaProgressoEtapa(3,5);
                DB_PROGRESSO.atualizaProgressoEtapa(4,5);
                DB_PROGRESSO.atualizaProgressoEtapa(5,5);
                DB_PROGRESSO.atualizaProgressoEtapa(6,4);
                DB_PROGRESSO.atualizaProgressoEtapa(7,5);
                DB_PROGRESSO.atualizaProgressoEtapa(8,4);
            }
        });

        botaoBloquearEtapas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoEtapa(1,1);
                DB_PROGRESSO.atualizaProgressoEtapa(2,0);
                DB_PROGRESSO.atualizaProgressoEtapa(3,0);
                DB_PROGRESSO.atualizaProgressoEtapa(4,0);
                DB_PROGRESSO.atualizaProgressoEtapa(5,0);
                DB_PROGRESSO.atualizaProgressoEtapa(6,0);
                DB_PROGRESSO.atualizaProgressoEtapa(7,0);
                DB_PROGRESSO.atualizaProgressoEtapa(8,0);
            }
        });

        botaoBloqLicoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoLicao(1,1,2);
                DB_PROGRESSO.atualizaProgressoLicao(1,2,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,3,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,4,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,5,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,6,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,7,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,8,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,9,1);
            }
        });

        botaoDesbloqLicoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoLicao(1,1,2);
                DB_PROGRESSO.atualizaProgressoLicao(1,2,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,3,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,4,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,5,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,6,4);
                DB_PROGRESSO.atualizaProgressoLicao(1,7,7);
                DB_PROGRESSO.atualizaProgressoLicao(1,8,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,9,8);
                writeFlag(true);
            }
        });

    }

    // LER FLAG PARA VER SE O USUARIO JA SE LOGOU
    public boolean readFlag() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("isLogin", false);
    }

    // MODIFICAR FLAG PARA LOGOUT
    public void writeFlag(boolean flag) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("completouTeste1", flag);
        editor.apply();
    }

}
