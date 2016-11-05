package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.R;


/**
 * Created by cahwayan on 09/10/2016.
 */
public class Licao3 extends Licao {
    ImageView imagem1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.viewRoot = inflater.inflate(R.layout.fragment_modulo1_etapa1_licao2,container,false);
        accessViews();
        super.adicionarZoomImagem(imagem1);
        super.listeners();

        return this.viewRoot;
    }

    protected void accessViews() {
        mViewPager = ((ContainerModulo1Etapa1)getActivity()).getPager();
        imagem1 = (ImageView) getActivity().findViewById(R.id.imagem1Modulo1Etapa2Licao3);
        super.accessViews();
    }

}
