package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.Adapters.Modulo3.AdapterEtapa2;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo3Etapa2 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.container_modulo3_etapa2);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 3;
        super.etapaAtual = 2;
        accessViews();
        super.bloquearLicoes();
        super.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo3Etapa2);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo3_etapa2);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo3_etapa2);
        mViewPager.setAdapter(new AdapterEtapa2(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo3_etapa2)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));
    }

}
