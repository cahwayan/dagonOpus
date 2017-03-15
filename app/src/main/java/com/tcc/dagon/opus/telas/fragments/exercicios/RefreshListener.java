package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.tcc.dagon.opus.telas.fragments.container.GerenciadorLicoes;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorPreferencesComSuporteParaLicoes;

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

    GerenciadorLicoes getManager();

    void refreshUI();
    boolean isLastExercise();
    boolean isSomDesativado();

    void avancarProgressoModulo(int aumento);
    int getProgressoModulo();

    void setProgressoEtapa(int aumento);
    void setProgressoEtapa(int moduloReferente, int aumento);
    int getProgressoEtapa();

    void setProgressoLicao(int aumento);
    int getProgressoLicao();

    int getEtapaAtual();
    int getModuloAtual();

    String fetchQuestionFromDatabase(int questaoAtual);
    String[] fetchAlternativesFromDatabase(int questaoAtual);


    void tocarSomRespostaCerta();
    void tocarSomRespostaErrada();

    void moveNext();
    void movePrevious();

    ViewPager getViewPager();
    TabLayout getTabLayout();

    void somarPontos(int pontos);




}
