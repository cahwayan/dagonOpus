package com.tcc.dagon.opus.ui.aprender;

/**
 * Created by cahwayan on 01/03/2017.
 */

/* Interface que representa um módulo */
public interface ModuloCurso {

    void atualizarEstadoConformeProgressoAtual(int progressoAtual);
    int getNumModulo();
    void setNota(String nota);
    String getNota();
    int getQtdEtapasModulo();
    int getEstadoAtual();
    boolean isCertificadoLiberado(int progressoAtual);
    boolean isCertificadoBloqueado(int progressoAtual);
    boolean isCertificadoGerado(int progressoAtual);
}
