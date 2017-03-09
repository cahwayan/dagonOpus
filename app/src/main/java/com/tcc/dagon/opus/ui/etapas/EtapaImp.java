package com.tcc.dagon.opus.ui.etapas;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telas.fragments.container.ContainerLicoes_;

/**
 * Created by cahwayan on 09/03/2017.
 */

class EtapaImp implements Etapa {

    private final int numEtapa;
    private int estadoEtapa;

    EtapaImp(int numEtapa) {
        this.numEtapa = numEtapa;
    }

    private void setEstadoEtapa(int estadoEtapa) {
        this.estadoEtapa = estadoEtapa;
    }

    @Override
    public int getNumEtapa() { return this.numEtapa; };

    @Override
    public int getEstadoAtual() {
        return this.estadoEtapa;
    }

    @Override
    public void atualizarEstadoConformeProgressoAtual(int progressoAtual) {

        if(progressoAtual == numEtapa) {
            this.estadoEtapa = CURSANDO;
        } else if(progressoAtual < numEtapa) {
            this.estadoEtapa = BLOQUEADA;
        } else {
            this.estadoEtapa = COMPLETA;
        }

    }

}
