package com.tcc.dagon.opus.telasEtapas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.ClassesPai.TelaEtapas;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo5.ContainerModulo5Etapa1;

import com.tcc.dagon.opus.R;

public class EtapasModulo5Activity extends TelaEtapas {

    /*LAYOUTS*/
    private LinearLayout    etapa1;

    private TextView    txtEtapa1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapas_modulo_5);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        moduloAtual = 5;

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
        etapa1 = (LinearLayout) findViewById(R.id.Modulo5Etapa1);

        // BARRA INFERIOR DAS ETAPAS
        txtEtapa1 = (TextView) findViewById(R.id.txtQuestoesModulo5Etapa1);

        etapas = new LinearLayout[]{etapa1};

        barraInferiorEtapas = new TextView[] {txtEtapa1};

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
                    startActivity(new Intent(getApplicationContext(), ContainerModulo5Etapa1.class));
                    finish();
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }
            }
        });

    }



}
