package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa1;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo6Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo6_etapa1);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 6;
        super.etapaAtual = 1;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.viewpagerSemSwipe);
        mViewPager.setAdapter(new AdapterEtapa1(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo6_etapa1)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }


}
