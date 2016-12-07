package com.tcc.dagon.opus.telasEtapas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.ClassesPai.TelaEtapas;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa1;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa2;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa3;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa4;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa5;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa6;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa7;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa8;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa9;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva6;
import com.tcc.dagon.opus.R;

public class EtapasModulo6Activity extends TelaEtapas {

    /*LAYOUTS*/
    private LinearLayout  etapa1,
                          etapa2,
                          etapa3,
                          etapa4,
                          etapa5,
                          etapa6,
                          etapa7,
                          etapa8,
                          etapa9,
                          etapa10;

    private TextView  txtEtapa1,
                      txtEtapa2,
                      txtEtapa3,
                      txtEtapa4,
                      txtEtapa5,
                      txtEtapa6,
                      txtEtapa7,
                      txtEtapa8,
                      txtEtapa9,
                      txtEtapa10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapas_modulo_6);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        moduloAtual = 6;

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
        etapa1 = (LinearLayout) findViewById(R.id.etapa1);
        etapa2 = (LinearLayout) findViewById(R.id.etapa2);
        etapa3 = (LinearLayout) findViewById(R.id.etapa3);
        etapa4 = (LinearLayout) findViewById(R.id.etapa4);
        etapa5 = (LinearLayout) findViewById(R.id.etapa5);
        etapa6 = (LinearLayout) findViewById(R.id.etapa6);
        etapa7 = (LinearLayout) findViewById(R.id.etapa7);
        etapa8 = (LinearLayout) findViewById(R.id.etapa8);
        etapa9 = (LinearLayout) findViewById(R.id.etapa9);
        etapa10 = (LinearLayout) findViewById(R.id.etapa10);

        // BARRA INFERIOR DAS ETAPAS
        txtEtapa1 = (TextView) findViewById(R.id.txtQuestoesEtapa1);
        txtEtapa2 = (TextView) findViewById(R.id.txtQuestoesEtapa2);
        txtEtapa3 = (TextView) findViewById(R.id.txtQuestoesEtapa3);
        txtEtapa4 = (TextView) findViewById(R.id.txtQuestoesEtapa4);
        txtEtapa5 = (TextView) findViewById(R.id.txtQuestoesEtapa5);
        txtEtapa6 = (TextView) findViewById(R.id.txtQuestoesEtapa6);
        txtEtapa7 = (TextView) findViewById(R.id.txtQuestoesEtapa7);
        txtEtapa8 = (TextView) findViewById(R.id.txtQuestoesEtapa8);
        txtEtapa9 = (TextView) findViewById(R.id.txtQuestoesEtapa9);
        txtEtapa10 = (TextView) findViewById(R.id.txtQuestoesEtapa10);

        etapas = new LinearLayout[]{etapa1, etapa2, etapa3, etapa4, etapa5, etapa6, etapa7, etapa8, etapa9, etapa10};

        barraInferiorEtapas = new TextView[] {txtEtapa1, txtEtapa2, txtEtapa3, txtEtapa4, txtEtapa5,
                                              txtEtapa6, txtEtapa7, txtEtapa8, txtEtapa9, txtEtapa10};

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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa1.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa2.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa3.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa4.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa5.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa6.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa7.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa8.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo6Etapa9.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

        // setar um click listener
        etapa10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) >= 10) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerProva6.class));
                    finish();
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });
    }

}
