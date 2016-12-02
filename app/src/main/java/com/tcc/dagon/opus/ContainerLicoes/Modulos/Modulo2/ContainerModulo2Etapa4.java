package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa4;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo2Etapa4 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo2_etapa4);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 2;
        super.etapaAtual = 4;
        accessViews();
        super.bloquearLicoes();
        super.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo2Etapa4);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo2_etapa4);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo2_etapa4);
        mViewPager.setAdapter(new AdapterEtapa4(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo2_etapa4)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

}
