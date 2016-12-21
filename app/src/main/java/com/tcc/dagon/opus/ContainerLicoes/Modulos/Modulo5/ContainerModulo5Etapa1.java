package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo5;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo5.AdapterEtapa1;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo5Activity;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerModulo5Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 5;
        super.etapaAtual = 1;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            startActivity(new Intent(getApplicationContext(), EtapasModulo5Activity.class));
            finish();
        } else if (mViewPager.getCurrentItem() == 1) {
            movePrevious(mViewPager);
        }
    }

}