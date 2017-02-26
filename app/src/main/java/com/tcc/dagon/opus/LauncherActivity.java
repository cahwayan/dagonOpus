package com.tcc.dagon.opus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.telas.login.LoginActivity_;
import com.tcc.dagon.opus.telas.login.CadastroActivity;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;

import com.tcc.dagon.opus.ui.aprender.AprenderActivity_;

/**
 * Created by Otávio Paulino on 21/09/2016.
 */
public class LauncherActivity extends AppCompatActivity {

    private Button login, cadastra, modulos, botaoBloquear, botaoDesbloquear, botaoDesbloquearEtapas, botaoBloquearEtapas,Modulo1,
                    botaoDesbloqLicoes, botaoBloqLicoes;

    private Button botaoDesbloquearPrimeiroModulo;

    private TextView txtInicial;
    RequestQueue requesQueue;
    GerenciadorBanco DB_PROGRESSO;
    GerenciadorSharedPreferences preferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = new GerenciadorSharedPreferences(this);
        if(preferencias.lerFlagBoolean(GerenciadorSharedPreferences.getIsLogin())) {
            startActivity(new Intent(this, AprenderActivity_.class));
            //finish();
        } else {
            startActivity(new Intent(this, LoginActivity_.class));
            //finish();
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

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
                Intent login = new Intent(LauncherActivity.this, LoginActivity_.class);
                startActivity(login);
            }
        });


        cadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastra = new Intent(LauncherActivity.this, CadastroActivity.class);
                startActivity(cadastra);
            }
        });

        modulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modulos = new Intent(LauncherActivity.this, AprenderActivity_.class);
                startActivity(modulos);
            }
        });

        botaoBloquear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preferencias.escreverFlagInt(preferencias.getPrefProgressoModulo(), 0);

                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva1(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva2(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva3(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva4(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva5(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva6(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagCertificadoGerado(), false);
            }
        });

        botaoDesbloquear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preferencias.escreverFlagInt(preferencias.getPrefProgressoModulo(), 6);


                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva1(), true);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva2(), true);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva3(), true);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva4(), true);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva5(), true);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva6(), true);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagCertificadoGerado(), true);
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
                DB_PROGRESSO.atualizaProgressoEtapa(2,6);
                DB_PROGRESSO.atualizaProgressoEtapa(3,3);
                DB_PROGRESSO.atualizaProgressoEtapa(4,6);
                DB_PROGRESSO.atualizaProgressoEtapa(5,1);
                DB_PROGRESSO.atualizaProgressoEtapa(6,10);
                //DB_PROGRESSO.atualizaProgressoEtapa(7,10);
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
                DB_PROGRESSO.atualizaProgressoLicao(1,1,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,2,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,3,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,4,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,5,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,6,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,7,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,8,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,9,1);

                DB_PROGRESSO.atualizaProgressoLicao(2,1,1);
                DB_PROGRESSO.atualizaProgressoLicao(2,2,1);
                DB_PROGRESSO.atualizaProgressoLicao(2,3,1);
                DB_PROGRESSO.atualizaProgressoLicao(2,4,1);
                DB_PROGRESSO.atualizaProgressoLicao(2,5,1);
                DB_PROGRESSO.atualizaProgressoLicao(2,6,1);

                DB_PROGRESSO.atualizaProgressoLicao(3,1,1);
                DB_PROGRESSO.atualizaProgressoLicao(3,2,1);
                DB_PROGRESSO.atualizaProgressoLicao(3,3,1);

                DB_PROGRESSO.atualizaProgressoLicao(4,1,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,2,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,3,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,4,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,5,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,6,1);

                DB_PROGRESSO.atualizaProgressoLicao(6,1,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,2,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,3,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,4,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,5,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,6,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,7,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,8,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,9,1);
                DB_PROGRESSO.atualizaProgressoLicao(6,10,1);

                DB_PROGRESSO.alterarPontuacaoTotal(1, 0);

                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva1(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva2(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva3(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva4(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva5(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva6(), false);

            }
        });

        botaoDesbloqLicoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DB_PROGRESSO.atualizaProgressoLicao(1,1,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,2,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,3,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,4,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,5,3);
                DB_PROGRESSO.atualizaProgressoLicao(1,6,4);
                DB_PROGRESSO.atualizaProgressoLicao(1,7,7);
                DB_PROGRESSO.atualizaProgressoLicao(1,8,1);
                DB_PROGRESSO.atualizaProgressoLicao(1,9,8);

                DB_PROGRESSO.atualizaProgressoLicao(4,1,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,2,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,3,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,4,1);
                DB_PROGRESSO.atualizaProgressoLicao(4,5,5);
                DB_PROGRESSO.atualizaProgressoLicao(4,6,4);


                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva1(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva2(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva3(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva4(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva5(), false);
                preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.getFlagProva6(), false);

            }
        });

    }

}
