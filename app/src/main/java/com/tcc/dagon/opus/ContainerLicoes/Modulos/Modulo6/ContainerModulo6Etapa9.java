package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa9;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo6Etapa9 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo6_etapa9);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 6;
        super.etapaAtual = 9;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.viewpagerSemSwipe);
        mViewPager.setAdapter(new AdapterEtapa9(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo6_etapa9)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }


}
