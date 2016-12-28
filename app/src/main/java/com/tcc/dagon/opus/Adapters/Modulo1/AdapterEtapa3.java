package com.tcc.dagon.opus.Adapters.Modulo1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.ClassesPai.Questao;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa3.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa3.Licao3;

/**
 * Created by cahwayan on 09/10/2016.
 */

public class AdapterEtapa3 extends Adapter {

    public AdapterEtapa3(FragmentManager fm, String[] tabTitulos) {
        super(fm, tabTitulos);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Licao1();
            case 1:
                return Questao.newInstance(MODULO1, ETAPA3, QUESTAO1);
            case 2:
                return new Licao3();
            case 3:
                return Questao.newInstance(MODULO1, ETAPA3, QUESTAO2);
            default:
                return null;
        }
    }
}
