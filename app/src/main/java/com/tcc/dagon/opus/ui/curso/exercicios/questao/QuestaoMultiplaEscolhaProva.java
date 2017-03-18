package com.tcc.dagon.opus.ui.curso.exercicios.questao;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.ui.curso.container.ContagemDeVidasListener;
import com.tcc.dagon.opus.ui.curso.container.ContainerProvaActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.AnimacaoVida;

/**
 * Created by cahwayan on 16/01/2017.
 */ /**/

public final class QuestaoMultiplaEscolhaProva extends QuestaoMultiplaEscolha {

    public ContagemDeVidasListener gerenciadorProva;

    public static QuestaoMultiplaEscolhaProva novaQuestaoMultiplaProva(int questaoAtual) {
        QuestaoMultiplaEscolhaProva questao = new QuestaoMultiplaEscolhaProva();
        questao.setQuestaoAtual(questaoAtual);
        return questao;
    }

    @Override
    protected void inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(inflater.inflate(R.layout.fragment_questao_multipla_escolha, container, false));
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
                    + " must implement contagemDeVidasListener");
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
