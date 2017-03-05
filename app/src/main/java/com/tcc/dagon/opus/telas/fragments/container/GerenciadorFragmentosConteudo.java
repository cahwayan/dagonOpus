package com.tcc.dagon.opus.telas.fragments.container;

import android.content.Context;
import android.util.Log;

import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorPreferencesComSuporteParaLicoes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felipe on 04/03/2017.
 */

class GerenciadorFragmentosConteudo {

    private GerenciadorPreferencesComSuporteParaLicoes preferenceManager;
    private List<FragmentoConteudo> listaFragmentosConteudo;
    private int quantidadeFragmentos;
    private int moduloAtual;
    private int etapaAtual;

    GerenciadorFragmentosConteudo(Context context, int quantidadeFragmentos, int moduloAtual, int etapaAtual) {
        preferenceManager = new GerenciadorPreferencesComSuporteParaLicoes(context);
        this.quantidadeFragmentos = quantidadeFragmentos;
        this.moduloAtual = moduloAtual;
        this.etapaAtual = etapaAtual;
        initListaFragmentos();
    }

    private void initListaFragmentos() {
        listaFragmentosConteudo = new ArrayList<>();

        int i = 0;
        while(i <= quantidadeFragmentos) {
            listaFragmentosConteudo.add(new FragmentoConteudo(/* Informar para o fragmento o índice que ele ocupa na lista para comparar com o progresso */ i));
            i++;
        }
    }

    public int[] getEstadoLicoes() {
        int progressoAtual = preferenceManager.getProgressoLicao(moduloAtual, etapaAtual);
        int[] arrayProgresso = new int[listaFragmentosConteudo.size()];
        Log.d("TAMANHO LISTA: ", String.valueOf(listaFragmentosConteudo.size()));

        for(int i = 0; i < arrayProgresso.length; i++) {
            arrayProgresso[i] = listaFragmentosConteudo.get(i).getEstadoLicaoEmRelacaoAoProgresso(progressoAtual);
        }

        return arrayProgresso;
    }

    public int getProgressoLicao() {
        return preferenceManager.getProgressoLicao(moduloAtual, etapaAtual);
    }

    public void setProgressoLicao(int progresso) {
        preferenceManager.setProgressoLicao(moduloAtual, etapaAtual, progresso);
    }

    public int getProgressoEtapa() {
        return preferenceManager.getProgressoEtapa(moduloAtual);
    }

    public void setProgressoEtapa(int aumento) {
        preferenceManager.setProgressoEtapa(moduloAtual, aumento);
    }

}
