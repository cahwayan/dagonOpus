package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa3;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

public class ContainerModulo1Etapa3 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.moduloAtual = 1;
        super.etapaAtual = 3;
        super.onCreate(savedInstanceState);

    }

}
