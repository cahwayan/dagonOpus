package com.tcc.dagon.opus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.ui.usuario.LoginActivity_;
import com.tcc.dagon.opus.ui.usuario.CadastroActivity;
import com.tcc.dagon.opus.data.DB;
import com.tcc.dagon.opus.common.gerenciadorsharedpreferences.GerenciadorPreferencesComSuporteParaLicoes;

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
    DB DB_PROGRESSO;
    GerenciadorPreferencesComSuporteParaLicoes preferencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencias = new GerenciadorPreferencesComSuporteParaLicoes(this);
        if(preferencias.getIsLogin()) {
            startActivity(new Intent(this, AprenderActivity_.class));
            //finish();
        } else {
            startActivity(new Intent(this, LoginActivity_.class));
            //finish();
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_launcher);

        DB_PROGRESSO = new DB(this);

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
                preferencias.setProgressoModulo(0);

                preferencias.setCompletouProva(0, false);
                preferencias.setCompletouProva(1, false);
                preferencias.setCompletouProva(2, false);
                preferencias.setCompletouProva(3, false);
                preferencias.setCompletouProva(4, false);
                preferencias.setCompletouProva(5, false);

            }
        });

        botaoDesbloquear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preferencias.setProgressoModulo(6);

                preferencias.setCompletouProva(0, true);
                preferencias.setCompletouProva(1, true);
                preferencias.setCompletouProva(2, true);
                preferencias.setCompletouProva(3, true);
                preferencias.setCompletouProva(4, true);
                preferencias.setCompletouProva(5, true);
            }
        });

        botaoDesbloquearPrimeiroModulo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preferencias.setProgressoEtapa(0, 8);
            }
        });

        botaoDesbloquearEtapas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preferencias.setProgressoEtapa(0, 9);
            }
        });

        botaoBloquearEtapas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preferencias.setProgressoEtapa(0, 0);
            }
        });

        botaoBloqLicoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                preferencias.setProgressoLicao(0,0,1);
                preferencias.setProgressoLicao(0,1,1);

            }
        });

        botaoDesbloqLicoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                preferencias.setCompletouProva(0, true);
                preferencias.setCompletouProva(1, true);
                preferencias.setCompletouProva(2, true);
                preferencias.setCompletouProva(3, true);
                preferencias.setCompletouProva(4, true);
                preferencias.setCompletouProva(5, true);

            }
        });

    }

}
