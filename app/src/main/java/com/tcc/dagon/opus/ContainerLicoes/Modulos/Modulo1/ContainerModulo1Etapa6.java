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
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo1_etapa6);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 1;
        super.etapaAtual = 6;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Etapa6);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_etapa6);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo1_etapa6);
        mViewPager.setAdapter(new AdapterEtapa6(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo1_etapa6)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

    @Override
    protected void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                mTabLayout.getTabAt(0).setIcon(R.drawable.icon_licao);
                tabStrip.getChildAt(0).setClickable(true);
                tabStrip.getChildAt(0).setEnabled(true);

                for(int i = 0; i <= progresso - 1; i += 1) {
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