package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1.Licao1;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

public class ContainerModulo1Etapa1 extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    GerenciadorBanco DB_PROGRESSO;
    LinearLayout tabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_modulo1_etapa1);
        DB_PROGRESSO = new GerenciadorBanco(this);

        accessViews();
        desabilitarScroll();
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
            mTabLayout.getTabAt(i).setIcon(R.drawable.icon_lock_etapa);
            tabStrip.getChildAt(i).setClickable(false);
        }
    }

    private void desbloquearLicao1() {
        mTabLayout.getTabAt(0).setText("1");

        mTabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        mTabLayout.setClickable(true);
                    }
                });
    }

    private void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(1,1);
        switch(DB_PROGRESSO.verificaProgressoLicao(1,1)) {
            case 1:
                for(int i = 0; i < progresso; i++) {
                    tabStrip.getChildAt(i).setClickable(true);
                }
                desbloquearLicao1();
                break;
            default:
                break;
        }
    }



    private void desabilitarScroll() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}
