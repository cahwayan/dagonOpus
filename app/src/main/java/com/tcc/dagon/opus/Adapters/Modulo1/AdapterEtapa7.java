package com.tcc.dagon.opus.Adapters.Modulo1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ClassesPai.Questao;
import com.tcc.dagon.opus.ClassesPai.QuestaoMultipla;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa7 extends Adapter {

    public AdapterEtapa7(FragmentManager fm, String[] tabTitulos) {
        super(fm, tabTitulos);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return Licao.newInstance(R.layout.fragment_modulo1_etapa7_licao1);
            case 1:
                return QuestaoMultipla.newInstance(MODULO1, ETAPA7, QUESTAO1);
            case 2:
                return Licao.newInstance(R.layout.fragment_modulo1_etapa7_licao3);
            case 3:
                return Questao.newInstance(MODULO1, ETAPA7, QUESTAO2);
            case 4:
                return Licao.newInstance(R.layout.fragment_modulo1_etapa7_licao5);
            case 5:
                return Completar.newInstance(R.layout.fragment_modulo1_etapa7_completar1,
                                             MODULO1, ETAPA7, 7 /* PALAVRAS */,
                                             respostasCertas = new String[] {"falso", "verdadeiro", "verdadeiro", "falso", "verdadeiro", "falso", "verdadeiro"},
                                             respostasCertasAcentuadas = new String[] {"falso", "verdadeiro", "verdadeiro", "falso", "verdadeiro", "falso", "verdadeiro"});
            case 6:
                return Licao.newInstance(R.layout.fragment_modulo1_etapa7_licao7);
            case 7:
                return Completar.newInstance(R.layout.fragment_modulo1_etapa7_completar2,
                                             MODULO1, ETAPA7, 6 /* PALAVRAS */,
                                             respostasCertas = new String[] {"verdadeiro", "falso", "falso", "verdadeiro", "verdadeiro", "falso"},
                                             respostasCertasAcentuadas = new String[] {"verdadeiro", "falso", "falso", "verdadeiro", "verdadeiro", "falso"});
            default:
                return null;
        }
    }

}
