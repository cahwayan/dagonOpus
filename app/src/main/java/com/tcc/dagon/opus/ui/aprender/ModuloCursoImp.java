package com.tcc.dagon.opus.ui.aprender;

/**
 * Created by cahwayan on 02/03/2017.
 */

class ModuloCursoImp implements ModuloCurso {

    private static int m_progressoAtual;
    private final int numModulo;
    private int estadoAtual;
    private int qtdEtapas;
    private String stringNota;

    ModuloCursoImp(int numModulo, int progressoAtual, String stringNota) {
        this.numModulo = numModulo;
        this.stringNota = stringNota;
        this.qtdEtapas = getQtdEtapas(numModulo);
        m_progressoAtual = progressoAtual;
    }

    public static void setProgressoAtual(int progressoAtual) {
        m_progressoAtual = progressoAtual;
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
        return numModulo == m_progressoAtual;
    }

    private boolean isModuloCompleto() {
        return m_progressoAtual > numModulo;
    }

    private boolean isModuloBloqueado() {
        return m_progressoAtual < numModulo;
    }

    private boolean isModuloCertificado() {
        return numModulo == MODULO_CERTIFICADO;
    }

    public boolean isCertificadoLiberado() {
        return m_progressoAtual == MODULO_CERTIFICADO;
    }

    public boolean isCertificadoBloqueado() {
        return m_progressoAtual < MODULO_CERTIFICADO;
    }

    public boolean isCertificadoGerado() {
        return m_progressoAtual > MODULO_CERTIFICADO;
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


