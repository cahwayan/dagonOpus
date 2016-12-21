package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo3.AdapterEtapa1;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerModulo3Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 3;
        super.etapaAtual = 1;
        super.onCreate(savedInstanceState);

    }


}