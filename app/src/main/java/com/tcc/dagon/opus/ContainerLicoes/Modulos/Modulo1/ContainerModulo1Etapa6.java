package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa6;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerModulo1Etapa6 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 1;
        super.etapaAtual = 6;
        super.onCreate(savedInstanceState);
        this.desbloquearLicoes();
    }

    @Override
    protected void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                mTabLayout.getTabAt(0).setIcon(R.drawable.icon_licao);
                tabStrip.getChildAt(0).setClickable(true);
                tabStrip.getChildAt(0).setEnabled(true);

                mTabLayout.getTabAt(1).setIcon(R.drawable.icon_pergunta);
                tabStrip.getChildAt(1).setClickable(true);
                tabStrip.getChildAt(1).setEnabled(true);

                for(int i = 1; i <= progresso - 1; i += 1) {
                    if(mTabLayout.getTabAt(i) != null) {
                        if( i > 0) {
                            mTabLayout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                            tabStrip.getChildAt(i).setClickable(true);
                            tabStrip.getChildAt(i).setEnabled(true);
                        }

                    }
                }
                break;
        }
    }

}