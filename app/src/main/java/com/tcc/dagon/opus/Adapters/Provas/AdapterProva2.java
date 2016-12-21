package com.tcc.dagon.opus.Adapters.Provas;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova2.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova2.Questao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova2.Questao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova2.Questao4;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova2.Questao5;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterProva2 extends Adapter {

    public AdapterProva2(FragmentManager fm, String[] tabTitulos) {
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
            default:
                return null;
        }
    }
}
