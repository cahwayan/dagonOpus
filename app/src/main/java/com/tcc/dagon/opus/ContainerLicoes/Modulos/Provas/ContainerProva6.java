package com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Provas.AdapterProva6;
import com.tcc.dagon.opus.ClassesPai.ContainerProva;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 01/12/2016.
 */

public class ContainerProva6 extends ContainerProva {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo6_prova);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 6;
        super.etapaAtual = 10;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        mViewPager = (ViewPager)findViewById(R.id.viewpagerSemSwipe);
        mViewPager.setAdapter(new AdapterProva6(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo6_prova)));
        mTabLayout.setupWithViewPager(mViewPager);

        vida01 = (ImageView) findViewById(R.id.vida01);
        vida02 = (ImageView) findViewById(R.id.vida02);
        vida03 = (ImageView) findViewById(R.id.vida03);
        vida04 = (ImageView) findViewById(R.id.vida04);
        vida05 = (ImageView) findViewById(R.id.vida05);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

}
