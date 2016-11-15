package com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Provas.AdapterProva1;
import com.tcc.dagon.opus.ClassesPai.ContainerProva;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerProva1 extends ContainerProva  {

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo1_prova);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 1;
        super.etapaAtual = 9;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();

        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        if(readFlag()) {
            for(int i = 0; i <= progresso - 1; i += 1) {
                if(mTabLayout.getTabAt(i) != null) {
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
            }
        }
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Prova);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_prova);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo1_prova);
        mViewPager.setAdapter(new AdapterProva1(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo1_prova)));
        mTabLayout.setupWithViewPager(mViewPager);

        vida01 = (ImageView) findViewById(R.id.vida01);
        vida02 = (ImageView) findViewById(R.id.vida02);
        vida03 = (ImageView) findViewById(R.id.vida03);
        vida04 = (ImageView) findViewById(R.id.vida04);
        vida05 = (ImageView) findViewById(R.id.vida05);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

    @Override
    protected void onDestroy() {
        if(!readFlag()) {
            DB_PROGRESSO.atualizaProgressoLicao(1,9,1);
        }
        super.onDestroy();
    }

    // LER FLAG PARA VER SE O USUARIO JA TERMINOU O TESTE ALGUMA VEZ
    public boolean readFlag() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("completouTeste1", false);
    }

}