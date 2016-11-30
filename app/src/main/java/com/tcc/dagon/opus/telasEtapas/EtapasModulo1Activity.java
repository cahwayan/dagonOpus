package com.tcc.dagon.opus.telasEtapas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tcc.dagon.opus.ClassesPai.TelaEtapas;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa2;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa3;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa4;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa5;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa6;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa7;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa8;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva1;
import com.tcc.dagon.opus.R;

public class EtapasModulo1Activity extends TelaEtapas {

    /*LAYOUTS*/
    private LinearLayout  etapa1,
                          etapa2,
                          etapa3,
                          etapa4,
                          etapa5,
                          etapa6,
                          etapa7,
                          etapa8,
                          etapa9;

    private TextView  txtEtapa1,
                      txtEtapa2,
                      txtEtapa3,
                      txtEtapa4,
                      txtEtapa5,
                      txtEtapa6,
                      txtEtapa7,
                      txtEtapa8,
                      txtEtapa9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapas_modulo_1);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        moduloAtual = 1;

        // INSTANCIANDO O BANCO E A JANELA ALERTA
        super.instanciaObjetos();

        //ACESSANDO AS VIEWS
        accessViews();

        clickListeners();

        // DESBLOQUEANDO ETAPAS
        desbloquearEtapas(listaEtapas);
    }


    protected void accessViews() {

        /*RECUPERANDO ITENS LAYOUT*/
        // QUADRADOS ETAPAS
        etapa1 = (LinearLayout) findViewById(R.id.Modulo1Etapa1);
        etapa2 = (LinearLayout) findViewById(R.id.Modulo1Etapa2);
        etapa3 = (LinearLayout) findViewById(R.id.Modulo1Etapa3);
        etapa4 = (LinearLayout) findViewById(R.id.Modulo1Etapa4);
        etapa5 = (LinearLayout) findViewById(R.id.Modulo1Etapa5);
        etapa6 = (LinearLayout) findViewById(R.id.Modulo1Etapa6);
        etapa7 = (LinearLayout) findViewById(R.id.Modulo1Etapa7);
        etapa8 = (LinearLayout) findViewById(R.id.Modulo1Etapa8);
        etapa9 = (LinearLayout) findViewById(R.id.Modulo1Etapa9);

        // BARRA INFERIOR DAS ETAPAS
        txtEtapa1 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa1);
        txtEtapa2 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa2);
        txtEtapa3 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa3);
        txtEtapa4 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa4);
        txtEtapa5 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa5);
        txtEtapa6 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa6);
        txtEtapa7 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa7);
        txtEtapa8 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa8);
        txtEtapa9 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa9);

        etapas = new LinearLayout[]{etapa1, etapa2, etapa3, etapa4, etapa5, etapa6, etapa7, etapa8, etapa9};

        barraInferiorEtapas = new TextView[] {txtEtapa1, txtEtapa2, txtEtapa3, txtEtapa4, txtEtapa5,
                                              txtEtapa6, txtEtapa7, txtEtapa8, txtEtapa9};

        super.accessViews();

    }



    private void clickListeners() {
        // setar um click listener
        etapa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 1) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa1.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 2) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa2.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 3) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa3.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 4) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa4.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 5) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa5.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 6) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa6.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 7) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa7.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 8) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa8.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 9) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerProva1.class));
                    finish();
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });
    }

}
