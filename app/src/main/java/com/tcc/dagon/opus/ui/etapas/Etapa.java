package com.tcc.dagon.opus.ui.etapas;

/**
 * Created by cahwayan on 01/03/2017.
 */

interface Etapa {

    int BLOQUEADA = 0;
    int CURSANDO = 1;
    int COMPLETA = 2;

    void atualizarUI();
}
