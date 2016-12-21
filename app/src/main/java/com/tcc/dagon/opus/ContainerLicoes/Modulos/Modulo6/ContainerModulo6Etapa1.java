package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa1;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo6Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 6;
        super.etapaAtual = 1;
        super.onCreate(savedInstanceState);
    }

}
