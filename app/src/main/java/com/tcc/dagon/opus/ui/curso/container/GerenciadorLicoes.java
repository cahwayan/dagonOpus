package com.tcc.dagon.opus.ui.curso.container;

import android.content.Context;
import android.util.Log;

import com.tcc.dagon.opus.data.DB;
import com.tcc.dagon.opus.common.gerenciadorsharedpreferences.GerenciadorPreferencesComSuporteParaLicoes;
import com.tcc.dagon.opus.data.DBQuestoes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caíque on 04/03/2017.
 * Essa classe faz o controle dos estados dos fragmentos em relação ao progresso.
 * Ela pode alterar o progresso atual dos módulos, etapas e lições
 * Esse objeto deve ser inicializado pelo ContainerLicoesActivity, que controla a interface do ViewPager,
 * e também se comunica com a classe que gerencia a interface dos fragmentos, fazendo eles reagirem de acordo.
 */

public class GerenciadorLicoes {

    private List<FragmentoLicao> listaLicoes;
    private GerenciadorPreferencesComSuporteParaLicoes preferenceManager;
    private DBQuestoes DB_QUESTOES;
    private int quantidadeLicoes;
    private int moduloAtual;
    private int etapaAtual;

    int getProgressoModulo() {
        return preferenceManager.getProgressoModulo();
    }

    void setProgressoModulo(int aumento) {
        preferenceManager.setProgressoModulo(getProgressoModulo() + aumento);
    }

    int getProgressoEtapa() {
        Log.d("PROGRESSO ETAPA: ", String.valueOf(preferenceManager.getProgressoEtapa(moduloAtual)));
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

    /*
     * @param context: o Context precisa ser passado para o gerenciador de shared preferences, já que para alterá-las,
      * é preciso um context.
      * @param quantidadeLicoes: a quantidade de lições que a classe terá que gerenciar
      * @param moduloAtual: o módulo em que o usuário se encontra.
      * @param etapaAtual: a etapa em que o usuário se encontra.
    */

    GerenciadorLicoes(Context context, int quantidadeLicoes, int moduloAtual, int etapaAtual) {
        preferenceManager = new GerenciadorPreferencesComSuporteParaLicoes(context);
        this.DB_QUESTOES = new DBQuestoes(moduloAtual, etapaAtual, context);
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

    void lerPontos(int moduloAtual) {
        preferenceManager.getPontos(moduloAtual);
    }

    void somarPontos(int moduloAtual, int pontos) {
        preferenceManager.somarPontos(moduloAtual, pontos);
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

    String fetchQuestionFromDatabase(int questaoAtual) {
        return DB_QUESTOES.buscarPergunta(questaoAtual);
    }

    String[] fetchAlternativesFromDatabase(int questaoAtual) {

        String[] alternativas = new String[4];

        for(int i = 0; i < alternativas.length; i++) {
            alternativas[i] = DB_QUESTOES.lerAlternativa(questaoAtual, i);
        }

        return alternativas;
    }

    /*
     * Método que busca a resposta para uma alternativa, para o exercício de Questão
    */
    public int verificaAlternativa(int questaoAtual, int alternativa) {

        final int RESPOSTA_ERRADA = 0;

        switch(alternativa) {
            case 0:
                return DB_QUESTOES.verificarResposta(questaoAtual, 0);
            case 1:
                return DB_QUESTOES.verificarResposta(questaoAtual, 1);
            case 2:
                return DB_QUESTOES.verificarResposta(questaoAtual, 2);
            case 3:
                return DB_QUESTOES.verificarResposta(questaoAtual, 3);
            default:
                return RESPOSTA_ERRADA;
        }
    }

}
