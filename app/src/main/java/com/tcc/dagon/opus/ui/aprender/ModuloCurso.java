package com.tcc.dagon.opus.ui.aprender;

/**
 * Created by cahwayan on 01/03/2017.
 */

/* Interface que representa um módulo */
public interface ModuloCurso {

    int MODULO0 = 0;
    int MODULO1 = 1;
    int MODULO2 = 2;
    int MODULO3 = 3;
    int MODULO4 = 4;
    int MODULO5 = 5;
    int MODULO_CERTIFICADO = 6;

    int QTD_ETAPAS_MODULO0 = 8;
    int QTD_ETAPAS_MODULO1 = 9;
    int QTD_ETAPAS_MODULO2 = 9;
    int QTD_ETAPAS_MODULO3 = 9;
    int QTD_ETAPAS_MODULO4 = 9;
    int QTD_ETAPAS_MODULO5 = 9;

    void setStringNota(String nota);
    void setNumModulo(int numModulo);
    void configurarModulo();
    void setClickListener();
    int getQtdEtapas(int numModulo);
    void setQtdEtapas(int qtdEtapas);

}
