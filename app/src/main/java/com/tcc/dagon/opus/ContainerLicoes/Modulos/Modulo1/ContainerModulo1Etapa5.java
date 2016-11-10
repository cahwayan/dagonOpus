package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa5;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo1Etapa5 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo1_etapa5);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 1;
        super.etapaAtual = 5;
        accessViews();
        super.bloquearLicoes();
        super.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Etapa5);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_etapa5);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo1_etapa5);
        mViewPager.setAdapter(new AdapterEtapa5(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo1_etapa5)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

}
