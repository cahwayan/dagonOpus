package com.tcc.dagon.opus.Adapters.Modulo1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1.Licao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1.Questao1;

/**
 * Created by charlinho on 09/10/2016.
 */

public class AdapterEtapa1 extends FragmentPagerAdapter {

private String[] tabTitulos;

    public AdapterEtapa1(FragmentManager fm, String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Licao1();
            case 1:
                return new Licao2();
            case 2:
                return new Questao1();
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
