package com.tcc.dagon.opus.Adapters.Modulo6;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Completar1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Questao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Licao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Licao5;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Licao7;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa7.Questao2;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa7 extends Adapter {

    public AdapterEtapa7(FragmentManager fm, String[] tabTitulos) {
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
                return new Questao2();
            case 4:
                return new Licao5();
            case 5:
                return new Questao3();
            case 6:
                return new Licao7();
            case 7:
                return new Completar1();
            default:
                return null;
        }
    }

}
