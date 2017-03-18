package com.tcc.dagon.opus.ui.curso.adapter;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.curso.exercicios.ConteudoWrapper;
import com.tcc.dagon.opus.ui.curso.exercicios.completar.Completar;
import com.tcc.dagon.opus.ui.curso.exercicios.completar.CompletarProva;
import com.tcc.dagon.opus.ui.curso.exercicios.questao.QuestaoMultiplaEscolha;
import com.tcc.dagon.opus.ui.curso.exercicios.questao.QuestaoMultiplaEscolhaProva;
import com.tcc.dagon.opus.ui.curso.exercicios.questao.QuestaoUnicaEscolha;
import com.tcc.dagon.opus.ui.curso.exercicios.questao.QuestaoUnicaEscolhaProva;
import com.tcc.dagon.opus.ui.curso.licao.LicaoFragment;

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

        switch (moduloReferente) {
            case 0:
                return exercicios_modulo_0(etapaReferente);
        }


        // Caso não encontre
        return new ConteudoWrapper[]{};

    }

    private static ConteudoWrapper[] exercicios_modulo_0(int etapaReferente) {

        switch (etapaReferente)

        {

            case ETAPA0:
                return new ConteudoWrapper[]
                        {LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                QuestaoUnicaEscolha.novaQuestao(QUESTAO0)};

            case ETAPA1:
                return new ConteudoWrapper[]
                                        {LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                        QuestaoMultiplaEscolha.novaQuestaoMultipla(QUESTAO0),

                                        LicaoFragment.novaLicao(R.layout.fragment_modulo1_etapa1_licao1),
                                                                Completar.novoCompletar(R.layout.fragment_modulo1_etapa2_completar1,
                                                                QUESTAO1, 6 /* palavras */,
                                                                new String[]{"olhar", "para", "direita", "atravesse", "nao", "atravesse"},
                                                                new String[]{"olhar", "para", "direita", "atravesse", "não", "atravesse"})
                                        };
            case ETAPA2:
                return new ConteudoWrapper[]
                        {CompletarProva.novoCompletarProva(R.layout.fragment_modulo1_prova_completar1,
                                                      QUESTAO0, 8 /* palavras*/,
                                                      new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"},
                                                      new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"}),
                        QuestaoUnicaEscolhaProva.novaQuestaoProva(QUESTAO1),
                        QuestaoMultiplaEscolhaProva.novaQuestaoMultiplaProva(QUESTAO2)

                        };

        }

            return null;
    }
}

