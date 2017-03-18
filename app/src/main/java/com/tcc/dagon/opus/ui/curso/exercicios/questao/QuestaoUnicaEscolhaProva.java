package com.tcc.dagon.opus.ui.curso.exercicios.questao;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tcc.dagon.opus.ui.curso.container.ContagemDeVidasListener;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 11/11/2016.
 */ /**/

public final class QuestaoUnicaEscolhaProva extends QuestaoUnicaEscolha {

    public ContagemDeVidasListener gerenciadorProva;

    public static QuestaoUnicaEscolhaProva novaQuestaoProva(int questaoAtual) {
        QuestaoUnicaEscolhaProva questao = new QuestaoUnicaEscolhaProva();
        questao.setQuestaoAtual(questaoAtual);
        return questao;
    }

    @Override
    protected void inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(inflater.inflate(R.layout.fragment_questao, container, false));
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
    public void avancarProgresso() {
        if(!usuarioJaCompletouEssaLicaoAntes()) {
            refreshListener.setProgressoLicao(/* AUMENTO EM */ 1);
            atualizarPontuacao();
        }

        refreshListener.refreshUI();
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    @Override
    public void respostaErrada() {
        super.respostaErrada();
        gerenciadorProva.removerVida();
    }

    @Override
    public void questaoFinal() {

        gerenciadorProva.setCompletouProva(true);

        if (!usuarioJaCompletouEsseModuloAntes()) {
            refreshListener.avancarProgressoModulo(1);
            avancarProgressoEtapa();
            atualizarPontuacao();
            refreshListener.setNota(refreshListener.calcularNota());
        }

        this.getActivity().finish();
    }

}
