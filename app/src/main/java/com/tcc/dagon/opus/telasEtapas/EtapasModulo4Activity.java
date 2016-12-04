package com.tcc.dagon.opus.telasEtapas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.ClassesPai.TelaEtapas;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa1;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa2;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa3;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa4;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa5;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva4;
import com.tcc.dagon.opus.R;

public class EtapasModulo4Activity extends TelaEtapas {

    /*LAYOUTS*/
    private LinearLayout    etapa1,
                            etapa2,
                            etapa3,
                            etapa4,
                            etapa5,
                            etapa6;

    private TextView    txtEtapa1,
                        txtEtapa2,
                        txtEtapa3,
                        txtEtapa4,
                        txtEtapa5,
                        txtEtapa6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapas_modulo_4);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        moduloAtual = 4;

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
        etapa1 = (LinearLayout) findViewById(R.id.Modulo4Etapa1);
        etapa2 = (LinearLayout) findViewById(R.id.Modulo4Etapa2);
        etapa3 = (LinearLayout) findViewById(R.id.Modulo4Etapa3);
        etapa4 = (LinearLayout) findViewById(R.id.Modulo4Etapa4);
        etapa5 = (LinearLayout) findViewById(R.id.Modulo4Etapa5);
        etapa6 = (LinearLayout) findViewById(R.id.Modulo4Etapa6);

        // BARRA INFERIOR DAS ETAPAS
        txtEtapa1 = (TextView) findViewById(R.id.txtQuestoesModulo4Etapa1);
        txtEtapa2 = (TextView) findViewById(R.id.txtQuestoesModulo4Etapa2);
        txtEtapa3 = (TextView) findViewById(R.id.txtQuestoesModulo4Etapa3);
        txtEtapa4 = (TextView) findViewById(R.id.txtQuestoesModulo4Etapa4);
        txtEtapa5 = (TextView) findViewById(R.id.txtQuestoesModulo4Etapa5);
        txtEtapa6 = (TextView) findViewById(R.id.txtQuestoesModulo4Etapa6);

        etapas = new LinearLayout[]{etapa1, etapa2, etapa3, etapa4, etapa5, etapa6};

        barraInferiorEtapas = new TextView[] {txtEtapa1, txtEtapa2, txtEtapa3, txtEtapa4, txtEtapa5, txtEtapa6};

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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo4Etapa1.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo4Etapa2.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo4Etapa3.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo4Etapa4.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo4Etapa5.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerProva4.class));
                    finish();
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

    }

}
