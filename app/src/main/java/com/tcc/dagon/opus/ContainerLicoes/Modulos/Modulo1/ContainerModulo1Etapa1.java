package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.aspsine.fragmentnavigator.FragmentNavigator;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa1;
import com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1.Licao1;
import com.tcc.dagon.opus.R;

public class ContainerModulo1Etapa1 extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_modulo1_etapa1);

        accessViews();
        desabilitarScroll();
    }


    private void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Etapa1);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_etapa1);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo1_etapa1);
        mViewPager.setAdapter(new AdapterEtapa1(getSupportFragmentManager(),
                                                getResources().getStringArray(R.array.tab_modulo1_etapa1)));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void desabilitarScroll() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
    }


}
