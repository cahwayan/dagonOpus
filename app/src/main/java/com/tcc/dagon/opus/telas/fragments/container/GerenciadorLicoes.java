package com.tcc.dagon.opus.telas.fragments.container;

import android.content.Context;
import android.util.Log;

import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorPreferencesComSuporteParaLicoes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caíque on 04/03/2017.
 * Essa classe faz o controle dos estados dos fragmentos em relação ao progresso.
 * Ela pode alterar o progresso atual dos módulos, etapas e lições
 * Esse objeto deve ser inicializado pelo ContainerLicoesActivity, que controla a interface do ViewPager,
 * e também se comunica com a classe que gerencia a interface dos fragmentos, fazendo eles reagirem de acordo.
 */

class GerenciadorLicoes {

    private List<FragmentoLicao> listaLicoes;
    private GerenciadorPreferencesComSuporteParaLicoes preferenceManager;
    private GerenciadorBanco DB_PROGRESSO;
    private int quantidadeLicoes;
    private int moduloAtual;
    private int etapaAtual;

    /*
     * @param context: o Context precisa ser passado para o gerenciador de shared preferences, já que para alterá-las,
      * é preciso um context.
      * @param quantidadeLicoes: a quantidade de lições que a classe terá que gerenciar
      * @param moduloAtual: o módulo em que o usuário se encontra.
      * @param etapaAtual: a etapa em que o usuário se encontra.
    */
    GerenciadorLicoes(Context context, int quantidadeLicoes, int moduloAtual, int etapaAtual) {
        preferenceManager = new GerenciadorPreferencesComSuporteParaLicoes(context);
        this.DB_PROGRESSO = new GerenciadorBanco(context);
        this.quantidadeLicoes = quantidadeLicoes;
        this.moduloAtual = moduloAtual;
        this.etapaAtual = etapaAtual;
        initListaFragmentos();
    }

    private void initListaFragmentos() {
        listaLicoes = new ArrayList<>();

        // Loop que instancia as lições e guarda na lista
        for(int i = 0; i <= quantidadeLicoes; i++) {
            listaLicoes.add(new FragmentoLicao(/* Informar para o fragmento o índice que ele ocupa na lista para comparar com o progresso */ i));
        }
    }

    GerenciadorPreferencesComSuporteParaLicoes getPreferences() {
        return this.preferenceManager;
    }

    int[] getEstadoLicoes() {
        int progressoAtual = preferenceManager.getProgressoLicao(moduloAtual, etapaAtual);
        int[] estadoLicoesEmRelacaoAoProgresso = new int[listaLicoes.size()];
        Log.d("TAMANHO LISTA: ", String.valueOf(listaLicoes.size()));

        for(int i = 0; i < estadoLicoesEmRelacaoAoProgresso.length; i++) {
            estadoLicoesEmRelacaoAoProgresso[i] = listaLicoes.get(i).getEstadoLicaoEmRelacaoAoProgresso(progressoAtual);
        }

        return estadoLicoesEmRelacaoAoProgresso;
    }

    int getProgressoModulo() {
        return preferenceManager.getProgressoModulo();
    }

    void setProgressoModulo(int aumento) {
        preferenceManager.setProgressoModulo(getProgressoModulo() + aumento);
    }

    int getProgressoEtapa() {
        return preferenceManager.getProgressoEtapa(moduloAtual);
    }

    void setProgressoEtapa(int aumento) {
        preferenceManager.setProgressoEtapa(moduloAtual, getProgressoEtapa() + aumento);
    }

    void setProgressoEtapa(int moduloReferente, int aumento) {
        preferenceManager.setProgressoEtapa(moduloReferente, aumento);
    }

    void liberarPrimeiraEtapaDoProximoModulo() {
        preferenceManager.setProgressoEtapa(moduloAtual + 1, /*Aumento em: */ 1);
    }

    int getProgressoLicao() {
        return preferenceManager.getProgressoLicao(moduloAtual, etapaAtual);
    }

    void setProgressoLicao(int progresso) {
        preferenceManager.setProgressoLicao(moduloAtual, etapaAtual, getProgressoLicao() + progresso);
    }

    String fetchQuestionFromDatabase(int questaoAtual) {
        return DB_PROGRESSO.puxarPergunta(moduloAtual, etapaAtual, questaoAtual);

    }

    String[] fetchAlternativesFromDatabase(int questaoAtual) {
        String[] alternativas = new String[4];
        for(int i = 0; i < alternativas.length; i++) {
            alternativas[i] = DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, (i + 1) );
        }

        return alternativas;

    }



}
