package com.tcc.dagon.opus.telas.aprender.menulateral.glossario;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.ui.aprender.AprenderActivity;
import com.tcc.dagon.opus.telas.fragments.container.ContainerLicoes;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 04/12/2016.
 */

public class ContainerComandosGlossario extends ContainerLicoes {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_glossario);
        super.onCreate(savedInstanceState);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        super.instanciaObjetos();
        super.moduloAtual = 0;
        super.etapaAtual = 0;
        init();
    }

    protected void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGlossario);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout_glossario);
        viewPager = (ViewPager)findViewById(R.id.pager_glossario);
        viewPager.setAdapter(new AdapterGlossario(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_glossario)));
        tabLayout.setupWithViewPager(viewPager);

        tabStrip = ((LinearLayout) tabLayout.getChildAt(0));

    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), AprenderActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/
}
