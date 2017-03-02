package com.tcc.dagon.opus.ui.aprender;

/**
 * Created by cahwayan on 02/03/2017.
 */

class ModuloCursoImp implements ModuloCurso {

    private int numModulo;
    private int progressoAtual;
    private int estadoAtual;
    private int qtdEtapas;
    private String stringNota;

    public static ModuloCursoImp factoryModulo(int numModulo, int progressoAtual, String stringNota) {
        ModuloCursoImp modulo = new ModuloCursoImp();
        modulo.numModulo = numModulo;
        modulo.progressoAtual = progressoAtual;
        modulo.qtdEtapas = modulo.getQtdEtapas(numModulo);
        modulo.stringNota = stringNota;
        modulo.atualizarEstado();
        return modulo;
    }

    public int getNumModulo() {
        return this.numModulo;
    }

    public String getNota() {
        return this.stringNota;
    }

    public int getQtdEtapas() {
        return this.qtdEtapas;
    }

    public void atualizarProgresso(int progressoAtual) {
        this.progressoAtual = progressoAtual;
    }

    @Override
    public int getEstado() {
        if(estadoAtual == IS_BLOQUEADO) {
            return IS_BLOQUEADO;
        } else if(estadoAtual == IS_CURSANDO) {
            return IS_CURSANDO;
        } else if(estadoAtual == IS_COMPLETO) {
            return IS_COMPLETO;
        } else if(estadoAtual == IS_CERTIFICADO) {
            return IS_CERTIFICADO;
        } else {
            return UNDEFINED;
        }
    }

    @Override
    public void atualizarEstado() {

        if(isModuloCertificado()) {
            this.estadoAtual = IS_CERTIFICADO;
            return;
        }

        if(isModuloBloqueado()) {
            this.estadoAtual = IS_BLOQUEADO;
        } else if(isModuloCursando()) {
            this.estadoAtual = IS_CURSANDO;
        } else if(isModuloCompleto()) {
            this.estadoAtual = IS_COMPLETO;
        } else {
            this.estadoAtual = UNDEFINED;
        }
    }

    private boolean isModuloCursando() {
        return numModulo == progressoAtual;
    }

    private boolean isModuloCompleto() {
        return progressoAtual > numModulo;
    }

    private boolean isModuloBloqueado() {
        return progressoAtual < numModulo;
    }

    private boolean isModuloCertificado() {
        return numModulo == MODULO_CERTIFICADO;
    }

    public boolean isCertificadoLiberado() {
        return progressoAtual == MODULO_CERTIFICADO;
    }

    public boolean isCertificadoBloqueado() {
        return progressoAtual < MODULO_CERTIFICADO;
    }

    public boolean isCertificadoGerado() {
        return progressoAtual > MODULO_CERTIFICADO;
    }

    private int getQtdEtapas(int numModulo) {
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


