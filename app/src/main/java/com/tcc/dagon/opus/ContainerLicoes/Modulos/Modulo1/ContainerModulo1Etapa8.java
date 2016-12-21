package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa8;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerModulo1Etapa8 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        super.moduloAtual = 1;
        super.etapaAtual = 8;
        super.onCreate(savedInstanceState);


    }
}