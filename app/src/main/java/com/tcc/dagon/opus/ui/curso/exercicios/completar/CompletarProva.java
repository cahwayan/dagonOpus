package com.tcc.dagon.opus.ui.curso.exercicios.completar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.ui.curso.container.ContagemDeVidasListener;
import com.tcc.dagon.opus.ui.curso.container.ContainerProvaActivity;
import com.tcc.dagon.opus.utils.AnimacaoVida;

/**
 * Created by cahwayan on 11/11/2016.
 */

public final class CompletarProva extends Completar {

    ContagemDeVidasListener gerenciadorProva;
    /**/
    /* MÉTODO ESTÁTICO DE INSTÂNCIA. COMO FRAGMENTOS NÃO POSSUEM SUPORTE DECENTE PARA O USO DE MÉTODOS CONSTRUTORES
    * (NA VERDADE NÃO É RECOMENDADO NEM SOBRESCREVER O CONSTRUTOR DE UM FRAGMENT)
    * CRIAMOS UM MÉTODO ESTÁTICO, QUE PODE SER ACESSADO DE QUALQUER LUGAR, QUE SERVE PARA INSTANCIAR A CLASSE COMO UM MÉTODO CONSTRUTOR.
    * ESSE MÉTODO RECEBE OS PARÂMETROS, E PASSA PARA O ONCREATE ATRAVÉS DE UM BUNDLE. LÁ ENTÃO, PODEMOS PEGAR ESSES VALORES E TRABALHAR COM ELES.
    * É IMPORTANTE SABER QUE AS MODIFICAÇÕES FEITAS NESSE MÉTODO, SÃO REALIZADAS ANTES DO MÉTODO ONCREATE SER EXECUTADO, POR ISSO, SERVE PERFEITAMENTE
    * COMO UM CONSTRUTOR*/

    public static CompletarProva novoCompletarProva(int layoutID, int questaoAtual, int quantidadePalavras, String[] respostasCertas, String[] respostasCertasAcentuadas) {
        CompletarProva completar = new CompletarProva();
        completar.setLayoutID(layoutID);
        completar.setQuestaoAtual(questaoAtual);
        completar.setQuantidadePalavras(quantidadePalavras);
        completar.setRespostasCertas(respostasCertas);
        completar.setRespostasCertasAcentuadas(respostasCertasAcentuadas);
        return completar;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            gerenciadorProva = (ContagemDeVidasListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ContagemDeVidasListener");
        }
    }

    @Override
    protected void inflateRootView(int layoutID, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(inflater.inflate(layoutID, container, false));
    }

    @Override
    protected void questaoFinal() {

        gerenciadorProva.setCompletouProva(true);

        if (!usuarioJaCompletouEsseModuloAntes()) {
            refreshListener.avancarProgressoModulo(1);
            avancarProgressoEtapa();
            atualizarPontuacao();
            refreshListener.setNota(refreshListener.calcularNota());
        }

        this.getActivity().finish();
    }

    @Override
    protected void avancarProgresso() {
        if(!usuarioJaCompletouEssaLicaoAntes()) {
            refreshListener.setProgressoLicao(/* AUMENTO EM */ 1);
            atualizarPontuacao();
        }

        refreshListener.refreshUI();
    }


    @Override
    public void respostaErrada() {
        super.respostaErrada();
        gerenciadorProva.removerVida();
    }

}
