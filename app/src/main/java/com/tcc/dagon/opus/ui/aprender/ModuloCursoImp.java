package com.tcc.dagon.opus.ui.aprender;

/**
 * Created by cahwayan on 02/03/2017.
 */

class ModuloCursoImp implements ModuloCurso {

    private final int numModulo;
    private int estadoAtual;
    private int qtdEtapasModulo;
    private String stringNota;

    ModuloCursoImp(int numModulo, String stringNota) {
        this.numModulo = numModulo;
        this.stringNota = stringNota;
        this.qtdEtapasModulo = getQtdEtapasModulo(numModulo);
    }

    public int getNumModulo() {
        return this.numModulo;
    }

    public String getNota() {
        return this.stringNota;
    }

    public int getQtdEtapasModulo() {
        return this.qtdEtapasModulo;
    }

    public int getEstadoAtual() {
        return this.estadoAtual;
    }

    @Override
    public void atualizarEstadoConformeProgressoAtual(int progressoAtual) {

        if(isModuloCertificado()) {
            this.estadoAtual = IS_CERTIFICADO;
            return;
        }

        if(isModuloBloqueado(progressoAtual)) {
            this.estadoAtual = IS_BLOQUEADO;
        } else if(isModuloCursando(progressoAtual)) {
            this.estadoAtual = IS_CURSANDO;
        } else if(isModuloCompleto(progressoAtual)) {
            this.estadoAtual = IS_COMPLETO;
        } else {
            this.estadoAtual = UNDEFINED;
        }
    }

    private boolean isModuloCursando(int progressoAtual) {
        return numModulo == progressoAtual;
    }

    private boolean isModuloCompleto(int progressoAtual) {
        return progressoAtual > numModulo;
    }

    private boolean isModuloBloqueado(int progressoAtual) {
        return progressoAtual < numModulo;
    }

    private boolean isModuloCertificado() {
        return numModulo == MODULO_CERTIFICADO;
    }

    public boolean isCertificadoLiberado(int progressoAtual) {
        return progressoAtual == MODULO_CERTIFICADO;
    }

    public boolean isCertificadoBloqueado(int progressoAtual) {
        return progressoAtual < MODULO_CERTIFICADO;
    }

    public boolean isCertificadoGerado(int progressoAtual) {
        return progressoAtual > MODULO_CERTIFICADO;
    }

    private int getQtdEtapasModulo(int numModulo) {
        switch(numModulo) {
            case MODULO0:
                return QTD_ETAPAS_MODULO0;
            case MODULO1:
                return QTD_ETAPAS_MODULO1;
            case MODULO2:
                return QTD_ETAPAS_MODULO2;
            case MODULO3:
                return QTD_ETAPAS_MODULO3;
            case MODULO4:
                return QTD_ETAPAS_MODULO4;
            case MODULO5:
                return QTD_ETAPAS_MODULO5;
            default: return UNDEFINED;
        }
    }
}


