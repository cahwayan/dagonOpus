package com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Provas.AdapterProva2;
import com.tcc.dagon.opus.ClassesPai.ContainerProva;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 01/12/2016.
 */

public class ContainerProva2 extends ContainerProva {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 2;
        super.etapaAtual = 6;
        super.onCreate(savedInstanceState);
    }

}
