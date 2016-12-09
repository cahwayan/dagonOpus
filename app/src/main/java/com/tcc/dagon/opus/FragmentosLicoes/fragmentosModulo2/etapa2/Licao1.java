package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo2.etapa2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo2.ContainerModulo2Etapa2;
import com.tcc.dagon.opus.R;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao1 extends Licao {
    ImageView imagem1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.viewRoot = inflater.inflate(R.layout.fragment_modulo2_etapa2_licao1,container,false);
        this.accessViews();
        super.listeners();
        super.adicionarZoomImagem(imagem1);
        return this.viewRoot;
    }

    protected void accessViews() {
        imagem1 = (ImageView) viewRoot.findViewById(R.id.imagem1Modulo2Etapa2Licao1);
        mViewPager = ((ContainerModulo2Etapa2)getActivity()).getPager();
        super.accessViews();
    }
}
