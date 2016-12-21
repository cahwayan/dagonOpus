package com.tcc.dagon.opus.Adapters.Modulo6;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa5.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa5.Licao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa5.Licao5;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa5.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa5.Completar1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa5.Completar2;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa5 extends Adapter {

    public AdapterEtapa5(FragmentManager fm, String[] tabTitulos) {
        super(fm, tabTitulos);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Licao1();
            case 1:
                return new Questao1();
            case 2:
                return new Licao3();
            case 3:
                return new Completar1();
            case 4:
                return new Licao5();
            case 5:
                return new Completar2();
            default:
                return null;
        }
    }

}
