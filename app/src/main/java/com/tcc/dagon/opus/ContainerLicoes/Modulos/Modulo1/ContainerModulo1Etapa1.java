package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa1;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

public class ContainerModulo1Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo1_etapa1);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 1;
        super.etapaAtual = 1;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo1Etapa1);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo1_etapa1);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo1_etapa1);
        mViewPager.setAdapter(new AdapterEtapa1(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo1_etapa1)));
        mTabLayout.setupWithViewPager(mViewPager);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

    @Override
    protected void desbloquearLicoes() {

        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                mTabLayout.getTabAt(2).setIcon(R.drawable.icon_pergunta);
                tabStrip.getChildAt(2).setClickable(true);
                tabStrip.getChildAt(2).setEnabled(true);

                for(int i = 0; i < progresso; i++) {
                    if(mTabLayout.getTabAt(i) != null) {
                            mTabLayout.getTabAt(i).setIcon(R.drawable.icon_licao);
                    }
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
                break;
        }
    }

}
