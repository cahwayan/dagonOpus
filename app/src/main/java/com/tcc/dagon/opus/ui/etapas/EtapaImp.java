package com.tcc.dagon.opus.ui.etapas;

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
