package com.tcc.dagon.opus.Adapters.Provas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova4.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova4.Questao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova4.Questao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova4.Questao4;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova4.Questao5;


/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterProva4 extends FragmentPagerAdapter {

    private String[] tabTitulos;

    public AdapterProva4(FragmentManager fm, String[] tabTitulos) {
        super(fm);
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

    @Override
    public int getCount() {
        return this.tabTitulos.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitulos[position];
    }




}
