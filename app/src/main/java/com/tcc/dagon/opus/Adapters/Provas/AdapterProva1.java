package com.tcc.dagon.opus.Adapters.Provas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.ClassesPai.CompletarProva;
import com.tcc.dagon.opus.ClassesPai.QuestaoProva;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterProva1 extends Adapter {

    public AdapterProva1(FragmentManager fm, String[] tabTitulos) {
        super(fm, tabTitulos);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return QuestaoProva.newInstance(MODULO1, ETAPA9, QUESTAO1);
            case 1:
                return CompletarProva.newInstance(R.layout.fragment_modulo1_prova_completar1,
                                                  MODULO1, ETAPA9, 8 /* PALAVRAS */,
                                                  respostasCertas = new String[] {"inteiro", "inteiro", "escreva", "leia",
                                                                                  "numero2", "resultado", "numero2", "resultado"},
                                                  respostasCertasAcentuadas = new String[] {"inteiro", "inteiro", "escreva", "leia",
                                                                                            "numero2", "resultado", "numero2", "resultado"});
            case 2:
                return QuestaoProva.newInstance(MODULO1, ETAPA9, QUESTAO3);
            case 3:
                return QuestaoProva.newInstance(MODULO1, ETAPA9, QUESTAO4);
            case 4:
                return CompletarProva.newInstance(R.layout.fragment_modulo1_prova_completar1,
                        MODULO1, ETAPA9, 8 /* PALAVRAS */,
                        respostasCertas = new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"},
                        respostasCertasAcentuadas = new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"});
            case 5:
                return CompletarProva.newInstance(R.layout.fragment_modulo1_prova_completar1,
                        MODULO1, ETAPA9, 8 /* PALAVRAS */,
                        respostasCertas = new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"},
                        respostasCertasAcentuadas = new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"});
            case 6:
                return CompletarProva.newInstance(R.layout.fragment_modulo1_prova_completar1,
                        MODULO1, ETAPA9, 8 /* PALAVRAS */,
                        respostasCertas = new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"},
                        respostasCertasAcentuadas = new String[] {"inteiro", "inteiro", "escreva", "leia", "numero2", "resultado", "numero2", "resultado"});
            case 7:
                return QuestaoProva.newInstance(MODULO1, ETAPA9, QUESTAO8);
            default:
                return null;
        }
    }
}
