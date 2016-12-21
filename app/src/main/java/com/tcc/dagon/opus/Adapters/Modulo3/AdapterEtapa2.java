package com.tcc.dagon.opus.Adapters.Modulo3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo3.etapa2.Completar1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo3.etapa2.Licao1;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa2 extends Adapter {

    public AdapterEtapa2(FragmentManager fm, String[] tabTitulos) {
        super(fm, tabTitulos);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Licao1();
            case 1:
                return new Completar1();
            default:
                return null;
        }
    }
}
