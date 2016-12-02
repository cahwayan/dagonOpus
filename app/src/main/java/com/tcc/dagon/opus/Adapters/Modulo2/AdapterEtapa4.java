package com.tcc.dagon.opus.Adapters.Modulo2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo2.etapa4.Completar1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo2.etapa4.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo2.etapa4.Licao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo2.etapa4.Completar2;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa4 extends FragmentPagerAdapter {

    private String[] tabTitulos;

    public AdapterEtapa4(FragmentManager fm, String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Licao1();
            case 1:
                return new Completar1();
            case 2:
                return new Licao3();
            case 3:
                return new Completar2();
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
