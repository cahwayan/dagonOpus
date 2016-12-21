package com.tcc.dagon.opus.ClassesPai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2.Completar1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2.Licao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2.Questao1;

/**
 * Created by cahwayan on 21/12/2016.
 */

public class Adapter extends FragmentPagerAdapter {

    protected String[] tabTitulos;

    public Adapter(FragmentManager fm, String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
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
