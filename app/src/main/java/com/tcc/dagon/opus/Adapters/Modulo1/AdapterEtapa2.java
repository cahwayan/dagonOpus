package com.tcc.dagon.opus.Adapters.Modulo1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ClassesPai.Questao;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2.Licao1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2.Licao3;
import com.tcc.dagon.opus.R;

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
                return Questao.newInstance(MODULO1, ETAPA2, QUESTAO1);
            case 2:
                return new Licao3();
            case 3:
                return Completar.newInstance(R.layout.fragment_modulo1_etapa2_completar1,
                                             MODULO1, ETAPA2, 6, /* Palavras */
                                             respostasCertas = new String[] {"olhar", "para", "direita", "atravesse", "nao", "atravesse"},
                                             respostasCertasAcentuadas = new String[] {"olhar", "para", "direita", "atravesse", "não", "atravesse"}
                );
            default:
                return null;
        }
    }
}
