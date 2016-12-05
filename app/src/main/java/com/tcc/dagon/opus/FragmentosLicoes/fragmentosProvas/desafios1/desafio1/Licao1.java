package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.desafios1.desafio1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo5.ContainerModulo5Etapa1;
import com.tcc.dagon.opus.R;

/**
 * Created by cw on 09/10/2016.
 */
public class Licao1 extends Licao {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.viewRoot = inflater.inflate(R.layout.fragment_modulo5_etapa1_licao1,container,false);
        this.accessViews();
        super.listeners();

        return this.viewRoot;
    }

    protected void accessViews() {
        mViewPager = ((ContainerModulo5Etapa1)getActivity()).getPager();
        super.accessViews();
    }
}
