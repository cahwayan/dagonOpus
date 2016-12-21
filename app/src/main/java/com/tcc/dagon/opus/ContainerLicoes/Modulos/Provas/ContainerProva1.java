package com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Provas.AdapterProva1;
import com.tcc.dagon.opus.ClassesPai.ContainerProva;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 10/11/2016.
 */

public class ContainerProva1 extends ContainerProva  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 1;
        super.etapaAtual = 9;
        super.onCreate(savedInstanceState);
    }

}