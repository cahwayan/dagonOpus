package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa3;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

public class ContainerModulo1Etapa3 extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private GerenciadorBanco DB_PROGRESSO;
    private LinearLayout tabStrip;

    public ContainerModulo1Etapa3() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_modulo1_etapa3);
        DB_PROGRESSO = new GerenciadorBanco(this);
        accessViews();
        bloquearLicoes();
        desbloquearLicoes();
    }

    @Override
    public void onBackPressed() {

        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 1){
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 2) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 3) {
            movePrevious(mViewPager);
        } else {
            super.onBackPressed();
        }
    }


    private void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Etapa3);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_etapa3);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo1_etapa3);
        mViewPager.setAdapter(new AdapterEtapa3(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo1_etapa3)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));
    }

    private void bloquearLicoes() {
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(R.drawable.icon_lock_licao);
            tabStrip.getChildAt(i).setClickable(false);
            tabStrip.getChildAt(i).setEnabled(false);
        }
    }

    private void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(1,3);
        switch(progresso) {
            default:
                for(int i = 0; i <= progresso; i += 2) {
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_licao);
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }

                for (int i=1; i <= progresso; i += 2) {
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
                break;
        }
    }

    public ViewPager getPager(){
        return mViewPager;
    }

    public LinearLayout getTabStrip() {
        return tabStrip;
    }

    public TabLayout getmTabLayout() {
        return mTabLayout;
    }

    private void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }



}
