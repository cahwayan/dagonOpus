package com.tcc.dagon.opus.telas.fragments.adapter;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telas.fragments.exercicios.ConteudoWrapper;
import com.tcc.dagon.opus.telas.fragments.exercicios.Questao;
import com.tcc.dagon.opus.ui.licao.LicaoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cahwayan on 03/03/2017.
 */

public class GerenciadorListaExercicios extends ValoresExercicios {

    static List<ConteudoWrapper> getListaExercicios(int moduloReferente, int etapaReferente) {
        List<ConteudoWrapper> listaExercicios = new ArrayList<>();
        listaExercicios.addAll(Arrays.asList(getInstanciasExercicios(moduloReferente, etapaReferente)));
        return listaExercicios;
    }

    public static ConteudoWrapper[] getInstanciasExercicios(int moduloReferente, int etapaReferente) {

        switch(moduloReferente) {
            case 0:
                return exercicios_modulo_0(etapaReferente);
        }


        // Caso não encontre
        return new ConteudoWrapper[]{};

    }

    private static ConteudoWrapper[] exercicios_modulo_0(int etapaReferente) {
        switch(etapaReferente)

        {

            case ETAPA0: return new ConteudoWrapper[]
                         {LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                          Questao.novaQuestao(QUESTAO0)};

            case ETAPA1: return new ConteudoWrapper[]
                        {LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                            Questao.novaQuestao(QUESTAO0),
                                LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                Questao.novaQuestao(QUESTAO0),
                                LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                Questao.novaQuestao(QUESTAO0),
                                LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                Questao.novaQuestao(QUESTAO0)};


        }

        return null;
    }
}
