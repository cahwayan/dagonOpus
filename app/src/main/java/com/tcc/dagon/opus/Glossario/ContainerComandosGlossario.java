package com.tcc.dagon.opus.Glossario;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 04/12/2016.
 */

public class ContainerComandosGlossario extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_glossario);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 0;
        super.etapaAtual = 0;
        accessViews();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlossario);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_glossario);
        mViewPager = (ViewPager)findViewById(R.id.pager_glossario);
        mViewPager.setAdapter(new AdapterGlossario(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_glossario)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }
}