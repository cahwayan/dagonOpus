package com.tcc.dagon.opus.Adapters.Provas;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao4;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao5;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao6;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao7;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos.Questao8;

import java.util.Collections;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterPular1 extends FragmentPagerAdapter {

    private String[] tabTitulos;

    public AdapterPular1(FragmentManager fm, String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Questao2();
            case 1:
                return new Questao5();
            case 2:
                return new Questao3();
            case 3:
                return new Questao4();
            case 4:
                return new Questao8();
            case 5:
                return new Questao7();
            case 6:
                return new Questao1();
            case 7:
                return new Questao6();
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
