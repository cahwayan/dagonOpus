package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa3;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo2Etapa3 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 2;
        super.etapaAtual = 3;
        super.onCreate(savedInstanceState);
    }

}
