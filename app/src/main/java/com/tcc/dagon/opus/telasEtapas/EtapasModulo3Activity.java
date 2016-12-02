package com.tcc.dagon.opus.telasEtapas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tcc.dagon.opus.ClassesPai.TelaEtapas;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3.ContainerModulo3Etapa1;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3.ContainerModulo3Etapa2;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva3;
import com.tcc.dagon.opus.R;

public class EtapasModulo3Activity extends TelaEtapas {

    /*LAYOUTS*/
    private LinearLayout    etapa1,
                            etapa2,
                            etapa3;

    private TextView    txtEtapa1,
                        txtEtapa2,
                        txtEtapa3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapas_modulo_3);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        moduloAtual = 3;

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
        etapa1 = (LinearLayout) findViewById(R.id.Modulo3Etapa1);
        etapa2 = (LinearLayout) findViewById(R.id.Modulo3Etapa2);
        etapa3 = (LinearLayout) findViewById(R.id.Modulo3Etapa3);

        // BARRA INFERIOR DAS ETAPAS
        txtEtapa1 = (TextView) findViewById(R.id.txtQuestoesModulo3Etapa1);
        txtEtapa2 = (TextView) findViewById(R.id.txtQuestoesModulo3Etapa2);
        txtEtapa3 = (TextView) findViewById(R.id.txtQuestoesModulo3Etapa3);

        etapas = new LinearLayout[]{etapa1, etapa2, etapa3};

        barraInferiorEtapas = new TextView[] {txtEtapa1, txtEtapa2, txtEtapa3};

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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo3Etapa1.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo3Etapa2.class));
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
                    startActivity(new Intent(getApplicationContext(), ContainerProva3.class));
                    finish();
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

    }

}
