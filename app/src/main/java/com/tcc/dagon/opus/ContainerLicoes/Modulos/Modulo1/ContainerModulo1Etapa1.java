package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa1;
import com.tcc.dagon.opus.MainActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo1Activity;

public class ContainerModulo1Etapa1 extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_etapa1_modulo1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Etapa1);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_etapa1);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);

        mViewPager.setAdapter(new AdapterEtapa1(getSupportFragmentManager(),getResources().getStringArray(R.array.tab_modulo1_etapa1)));

        mTabLayout.setupWithViewPager(mViewPager);

    }


}
