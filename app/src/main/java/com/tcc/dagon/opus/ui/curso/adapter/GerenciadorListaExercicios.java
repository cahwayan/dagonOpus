package com.tcc.dagon.opus.ui.curso.adapter;

import android.support.v4.app.Fragment;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.curso.exercicios.ExercicioFragment;
import com.tcc.dagon.opus.ui.curso.exercicios.completar.CompletarFragment;
import com.tcc.dagon.opus.ui.curso.exercicios.completar.CompletarProvaFragment;
import com.tcc.dagon.opus.ui.curso.exercicios.questao.QuestaoMultiplaEscolhaProvaFragment;
import com.tcc.dagon.opus.ui.curso.exercicios.questao.QuestaoUnicaEscolhaFragment;
import com.tcc.dagon.opus.ui.curso.exercicios.questao.QuestaoUnicaEscolhaProvaFragment;
import com.tcc.dagon.opus.ui.curso.licao.LicaoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tcc.dagon.opus.ui.curso.constantes.EtapaConstants.*;
import static com.tcc.dagon.opus.ui.curso.constantes.ExercicioConstants.*;

/**
 * Created by cahwayan on 03/03/2017.
 */

public class GerenciadorListaExercicios {

    static List<Fragment> getListaExercicios(int moduloReferente, int etapaReferente) {
        List<Fragment> listaExercicios = new ArrayList<>();
        listaExercicios.addAll(Arrays.asList(getInstanciasExercicios(moduloReferente, etapaReferente)));
        return listaExercicios;
    }

    public static Fragment[] getInstanciasExercicios(int moduloReferente, int etapaReferente) {

        switch (moduloReferente) {
            case 0:
                return exercicios_modulo_0(etapaReferente);
        }

        // Caso não encontre
        return new Fragment[]{};

    }

    private static Fragment[] exercicios_modulo_0(int etapaReferente) {

        switch (etapaReferente)

        {

            case ETAPA0:
                return new Fragment[]
                                {LicaoFragment.newInstance(R.layout.fragment_modulo1_etapa1_licao1),
                                QuestaoUnicaEscolhaFragment.novaQuestaoUnicaEscolha(QUESTAO0)};

            case ETAPA1:
                return new Fragment[]
                                        {LicaoFragment.newInstance(R.layout.fragment_modulo1_etapa1_licao1),
                                        ExercicioFragment.novaQuestaoMultipla(QUESTAO0),

                                        LicaoFragment.newInstance(R.layout.fragment_modulo1_etapa1_licao1),
                                                                CompletarFragment.novoCompletar(R.layout.fragment_modulo1_etapa2_completar1,
                                                                QUESTAO1, 6 /* palavras */,
                                                                new String[]{"olhar", "para", "direita", "atravesse", "nao", "atravesse"})
                                        };
            case ETAPA2:
                return new Fragment[]
                        {CompletarProvaFragment.novoCompletarProva(R.layout.fragment_modulo1_prova_completar1,
                                                      QUESTAO0, 8 /* palavras*/,
                                                      new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"}),
                        QuestaoUnicaEscolhaProvaFragment.novaQuestaoUnicaEscolhaProva(QUESTAO1),
                        QuestaoMultiplaEscolhaProvaFragment.novaQuestaoMultiplaProva(QUESTAO2)

                        };

        }

            return null;
    }
}

