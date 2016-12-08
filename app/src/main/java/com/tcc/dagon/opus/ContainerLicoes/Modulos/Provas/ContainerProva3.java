package com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Provas.AdapterProva3;
import com.tcc.dagon.opus.ClassesPai.ContainerProva;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 01/12/2016.
 */

public class ContainerProva3 extends ContainerProva {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo3_prova);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 3;
        super.etapaAtual = 3;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo2Prova);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo3_prova);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo3_prova);
        mViewPager.setAdapter(new AdapterProva3(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo3_prova)));
        mTabLayout.setupWithViewPager(mViewPager);

        vida01 = (ImageView) findViewById(R.id.vida01);
        vida02 = (ImageView) findViewById(R.id.vida02);
        vida03 = (ImageView) findViewById(R.id.vida03);
        vida04 = (ImageView) findViewById(R.id.vida04);
        vida05 = (ImageView) findViewById(R.id.vida05);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

}
