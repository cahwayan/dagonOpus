package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo3.AdapterEtapa1;
import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerModulo3Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo3_etapa1);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 3;
        super.etapaAtual = 1;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo3Etapa1);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo3_etapa1);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo3_etapa1);
        mViewPager.setAdapter(new AdapterEtapa1(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo3_etapa1)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}