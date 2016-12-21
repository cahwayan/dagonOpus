package com.tcc.dagon.opus.Adapters.Modulo1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa6.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa6.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa6.Questao2;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa6.Questao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa6.Questao4;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa6 extends Adapter {


    public AdapterEtapa6(FragmentManager fm, String[] tabTitulos) {
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
                return new Questao2();
            case 3:
                return new Questao3();
            case 4:
                return new Questao4();
            default:
                return null;
        }
    }

}
