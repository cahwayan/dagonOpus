package com.tcc.dagon.opus.ui.etapas;

/**
 * Created by cahwayan on 01/03/2017.
 */

interface Etapa {
    int getEstadoAtual();
    int getNumEtapa();
    void atualizarEstadoConformeProgressoAtual(int progressoAtual);
}
