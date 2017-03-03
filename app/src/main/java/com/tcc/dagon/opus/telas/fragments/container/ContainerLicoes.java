package com.tcc.dagon.opus.telas.fragments.container;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.telas.fragments.adapter.Adapter;
import com.tcc.dagon.opus.telas.fragments.adapter.GerenciadorListaExercicios;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by cahwayan on 04/11/2016.
 */

@EActivity(R.layout.container_etapa)
public class ContainerLicoes extends AppCompatActivity {

    @ViewById TabLayout tab_layout;
    @ViewById ViewPager view_pager;
    @ViewById Toolbar toolbar;

    protected GerenciadorBanco DB_PROGRESSO;
    protected LinearLayout tabStrip;
    protected String tituloEtapa;

    public void setModuloAtual(int moduloAtual) {
        this.moduloAtual = moduloAtual;
    }

    public void setEtapaAtual(int etapaAtual) {
        this.etapaAtual = etapaAtual;
    }

    protected int moduloAtual, etapaAtual;

    final String TAG1 = "Debug!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.tituloEtapa = getIntent().getStringExtra("tituloEtapa");
        this.moduloAtual = getIntent().getIntExtra("moduloAtual", 0);
        this.etapaAtual  = getIntent().getIntExtra("etapaAtual", 0);

    }

    @Override
    public void onBackPressed() {
        if (view_pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else if (view_pager.getCurrentItem() == 1){
            movePrevious(view_pager);
        } else if (view_pager.getCurrentItem() == 2) {
            super.onBackPressed();
        } else if (view_pager.getCurrentItem() == 3) {
            movePrevious(view_pager);
        } else if (view_pager.getCurrentItem() == 4) {
            super.onBackPressed();
        } else if (view_pager.getCurrentItem() == 5) {
            movePrevious(view_pager);
        } else if (view_pager.getCurrentItem() == 6) {
            super.onBackPressed();
        } else if (view_pager.getCurrentItem() == 7) {
            movePrevious(view_pager);
        } else if (view_pager.getCurrentItem() == 8) {
            super.onBackPressed();
        } else if (view_pager.getCurrentItem() == 9) {
            movePrevious(view_pager);
        } else if (view_pager.getCurrentItem() == 10) {
            super.onBackPressed();
        } else if (view_pager.getCurrentItem() == 11) {
            movePrevious(view_pager);
        } else if (view_pager.getCurrentItem() == 12) {
            super.onBackPressed();
        } else if (view_pager.getCurrentItem() == 13) {
            movePrevious(view_pager);
        }else {
            super.onBackPressed();
        }
    }

    @AfterViews
    protected void init() {

        instanciaObjetos();

        setSupportActionBar(toolbar);

        if(this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(tituloEtapa);
        }

        String[] tabTitulos = new String[GerenciadorListaExercicios.getArrayInstanciasExercicios(moduloAtual, etapaAtual).length];

        // Setando o adapter que vai retornar os fragmentos
        Adapter adapterEtapa = new Adapter(getSupportFragmentManager(),
                tabTitulos,
                moduloAtual,
                etapaAtual);

        view_pager.setAdapter(adapterEtapa);

        // Setup do Viewpager
        tab_layout.setupWithViewPager(view_pager);
        tabStrip = ((LinearLayout) tab_layout.getChildAt(0));

        bloquearLicoes();
        desbloquearLicoes();
    }

    protected void bloquearLicoes() {
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tab_layout.getTabAt(i).setIcon(R.drawable.icon_lock_licao);
            tabStrip.getChildAt(i).setClickable(false);
            tabStrip.getChildAt(i).setEnabled(false);
        }
    }

    protected void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                for(int i = 0; i <= progresso; i += 1) {
                    if(tab_layout.getTabAt(i) != null) {
                        if( i % 2 == 0) {
                           tab_layout.getTabAt(i).setIcon(R.drawable.icon_licao);
                        } else {
                           tab_layout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                        }

                    }

                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
                break;
        }
    }

    protected void instanciaObjetos() {
        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
        }
    }

    public ViewPager getPager(){
        return view_pager;
    }

    public LinearLayout getTabStrip() {
        return tabStrip;
    }

    public TabLayout getTab_layout() {
        return tab_layout;
    }

    protected void movePrevious(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }
}
