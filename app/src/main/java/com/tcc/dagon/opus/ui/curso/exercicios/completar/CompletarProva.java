package com.tcc.dagon.opus.ui.curso.exercicios.completar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tcc.dagon.opus.ui.curso.container.ContagemDeVidasListener;

/**
 * Created by cahwayan on 11/11/2016.
 */

public final class CompletarProva extends Completar {

    ContagemDeVidasListener gerenciadorProva;

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
