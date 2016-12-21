package com.tcc.dagon.opus.Adapters.Provas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova3.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova3.Questao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova3.Questao3;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterProva3 extends Adapter {

    public AdapterProva3(FragmentManager fm, String[] tabTitulos) {
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
            default:
                return null;
        }
    }
}
