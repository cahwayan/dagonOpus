package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa1;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

public class ContainerModulo1Etapa1 extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    GerenciadorBanco DB_PROGRESSO;
    LinearLayout tabStrip;

    public ContainerModulo1Etapa1() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_modulo1_etapa1);
        DB_PROGRESSO = new GerenciadorBanco(this);
        accessViews();
        bloquearScroll();
        bloquearLicoes();
        desbloquearLicoes();
    }


    private void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Etapa1);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_etapa1);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo1_etapa1);
        mViewPager.setAdapter(new AdapterEtapa1(getSupportFragmentManager(),
                                                getResources().getStringArray(R.array.tab_modulo1_etapa1)));
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
        int progresso = DB_PROGRESSO.verificaProgressoLicao(1,1);
        switch(DB_PROGRESSO.verificaProgressoLicao(1,1)) {
            default:
                for(int i = 0; i <= progresso; i += 2) {
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_licao);
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                    //tabStrip.getChildAt(i).setBackgroundResource(R.drawable.borda_licao);
                }

                for (int i=1; i <= progresso; i += 2) {
                    //tabStrip.getChildAt(i).setBackgroundResource(R.drawable.borda_licao);
                    mTabLayout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
            break;
        }
    }

    private void bloquearScroll() {
        mViewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
    }

}
