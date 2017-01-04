package com.tcc.dagon.opus.Adapters.Provas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.ClassesPai.QuestaoProva;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova1.Questao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova1.Questao5;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova1.Questao6;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova1.Questao7;

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
                return new Questao2();
            case 2:
                return QuestaoProva.newInstance(MODULO1, ETAPA9, QUESTAO3);
            case 3:
                return QuestaoProva.newInstance(MODULO1, ETAPA9, QUESTAO4);
            case 4:
                return new Questao5();
            case 5:
                return new Questao6();
            case 6:
                return new Questao7();
            case 7:
                return QuestaoProva.newInstance(MODULO1, ETAPA9, QUESTAO8);
            default:
                return null;
        }
    }
}
