package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa4;
import com.tcc.dagon.opus.R;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao3 extends Licao {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.viewRoot = inflater.inflate(R.layout.fragment_modulo6_etapa4_licao3,container,false);
        this.accessViews();
        super.listeners();

        return this.viewRoot;
    }

    protected void accessViews() {
        mViewPager = ((ContainerModulo6Etapa4)getActivity()).getPager();
        super.accessViews();
    }
}
