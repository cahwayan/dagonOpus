package com.tcc.dagon.opus.Adapters.Modulo6;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa6.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa6.Questao1;


/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa6 extends FragmentPagerAdapter {

    private String[] tabTitulos;

    public AdapterEtapa6(FragmentManager fm, String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Licao1();
            case 1:
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
