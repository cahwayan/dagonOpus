package com.tcc.dagon.opus.telas.fragments.adapter;

import android.app.Fragment;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telas.fragments.exercicios.CExercicio;
import com.tcc.dagon.opus.telas.fragments.exercicios.FragmentoConteudo;
import com.tcc.dagon.opus.telas.fragments.exercicios.Questao;
import com.tcc.dagon.opus.ui.licao.LicaoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cahwayan on 03/03/2017.
 */

public class GerenciadorListaExercicios extends ValoresExercicios {

    public static List<FragmentoConteudo> getListaExercicios(int moduloReferente, int etapaReferente) {
        List<FragmentoConteudo> listaExercicios = new ArrayList<>();
        listaExercicios.addAll(Arrays.asList(getArrayInstanciasExercicios(moduloReferente, etapaReferente)));
        return listaExercicios;
    }

    public static FragmentoConteudo[] getArrayInstanciasExercicios(int moduloReferente, int etapaReferente) {

        switch(moduloReferente) {
            case 0:
                return exercicios_modulo_0(etapaReferente);
        }

        return null;

    }

    private static FragmentoConteudo[] exercicios_modulo_0(int etapaReferente) {

        switch(etapaReferente) {
            case 0:

                return new FragmentoConteudo[]
                        {LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                         Questao.novaQuestao(MODULO1, ETAPA1, QUESTAO1)};
        }

        return null;
    }
}
