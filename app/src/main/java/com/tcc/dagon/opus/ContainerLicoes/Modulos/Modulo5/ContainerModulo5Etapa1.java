package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo5.AdapterEtapa1;
import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo5Activity;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerModulo5Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo5_etapa1);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 5;
        super.etapaAtual = 1;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo5Etapa1);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo5_etapa1);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo5_etapa1);
        mViewPager.setAdapter(new AdapterEtapa1(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo5_etapa1)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            startActivity(new Intent(getApplicationContext(), EtapasModulo5Activity.class));
            finish();
        } else if (mViewPager.getCurrentItem() == 1) {
            movePrevious(mViewPager);
        }
    }

}