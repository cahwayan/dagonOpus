package com.tcc.dagon.opus.telas.fragments.exercicios;

/**
 * Created by cahwayan on 10/03/2017.
 */

    /*
     * Através dessa interface, o fragmento pode se comunicar com o container para ler informações
     * a respeito do progresso atual, mandar mensagens para o container atualizar a interface dele,
     * também pode mandar mensagens para que o container altere o progresso atual de acordo com o desempenho
     * do usuário através do aplicativo.
    */
public interface RefreshListener {

    void refreshUI();
    boolean isLastExercise();

    void avancarProgressoModulo(int aumento);
    int getProgressoModulo();

    void avancarProgressoEtapa(int aumento);
    int getProgressoEtapa();

    void avancarProgressoLicao(int aumento);
    int getProgressoLicao();

    int getEtapaAtual();
    int getModuloAtual();

    boolean isSomDesativado();
}
