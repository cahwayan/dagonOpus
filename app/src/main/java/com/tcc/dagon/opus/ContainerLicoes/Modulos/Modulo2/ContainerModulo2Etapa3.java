package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa3;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo2Etapa3 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.container_modulo2_etapa3);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 2;
        super.etapaAtual = 3;
        accessViews();
        super.bloquearLicoes();
        super.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo2Etapa3);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo2_etapa3);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo2_etapa3);
        mViewPager.setAdapter(new AdapterEtapa3(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo2_etapa3)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));
    }

}
