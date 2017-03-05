package com.tcc.dagon.opus.telas.fragments.adapter;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telas.fragments.exercicios.FragmentosConteudo;
import com.tcc.dagon.opus.telas.fragments.exercicios.Questao;
import com.tcc.dagon.opus.ui.licao.LicaoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cahwayan on 03/03/2017.
 */

public class GerenciadorListaExercicios extends ValoresExercicios {

    public static List<FragmentosConteudo> getListaExercicios(int moduloReferente, int etapaReferente) {
        List<FragmentosConteudo> listaExercicios = new ArrayList<>();
        listaExercicios.addAll(Arrays.asList(getArrayInstanciasExercicios(moduloReferente, etapaReferente)));
        return listaExercicios;
    }

    public static FragmentosConteudo[] getArrayInstanciasExercicios(int moduloReferente, int etapaReferente) {

        switch(moduloReferente) {
            case 0:
                return exercicios_modulo_0(etapaReferente);
        }

        return null;

    }

    private static FragmentosConteudo[] exercicios_modulo_0(int etapaReferente) {
        switch(etapaReferente)

        {

            case ETAPA0: return new FragmentosConteudo[]
                         {LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                          Questao.novaQuestao(MODULO0, ETAPA0, QUESTAO0)};

            case ETAPA1: return new FragmentosConteudo[]
                        {LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                            Questao.novaQuestao(MODULO0, ETAPA0, QUESTAO0),
                                LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                Questao.novaQuestao(MODULO0, ETAPA0, QUESTAO0),
                                LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                Questao.novaQuestao(MODULO0, ETAPA0, QUESTAO0),
                                LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                Questao.novaQuestao(MODULO0, ETAPA0, QUESTAO0)};


        }

        return null;
    }
}
