package com.tcc.dagon.opus.Adapters.Provas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao4;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao5;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao6;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao7;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao8;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6.Questao9;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterProva6 extends Adapter {

    public AdapterProva6(FragmentManager fm, String[] tabTitulos) {
        super(fm, tabTitulos);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Questao1();
            case 1:
                return new Questao2();
            case 2:
                return new Questao3();
            case 3:
                return new Questao4();
            case 4:
                return new Questao5();
            case 5:
                return new Questao6();
            case 6:
                return new Questao7();
            case 7:
                return new Questao8();
            case 8:
                return new Questao9();
            default:
                return null;
        }
    }

}
