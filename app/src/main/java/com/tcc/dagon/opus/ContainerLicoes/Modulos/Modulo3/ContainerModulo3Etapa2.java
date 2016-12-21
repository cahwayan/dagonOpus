package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.Adapters.Modulo3.AdapterEtapa2;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo3Etapa2 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 3;
        super.etapaAtual = 2;
        super.onCreate(savedInstanceState);
    }
}
