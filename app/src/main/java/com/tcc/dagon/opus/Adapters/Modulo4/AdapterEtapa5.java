package com.tcc.dagon.opus.Adapters.Modulo4;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa5.Completar1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa5.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa5.Licao3;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa5.Licao5;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa5.Questao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa5.Questao2;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa5 extends FragmentPagerAdapter {

    private String[] tabTitulos;

    public AdapterEtapa5(FragmentManager fm, String[] tabTitulos) {
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
            case 2:
                return new Licao3();
            case 3:
                return new Questao2();
            case 4:
                return new Licao5();
            case 5:
                return new Completar1();
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
