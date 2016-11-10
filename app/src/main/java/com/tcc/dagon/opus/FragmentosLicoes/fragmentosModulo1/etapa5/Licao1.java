package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa5;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Licao1 extends Licao {

    private ImageView imagem1, imagem2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.viewRoot = inflater.inflate(R.layout.fragment_modulo1_etapa5_licao1,container,false);
        this.accessViews();
        adicionarZoomImagem(imagem1);
        adicionarZoomImagem(imagem2);
        super.listeners();

        return this.viewRoot;
    }

    protected void accessViews() {
        mViewPager = ((ContainerModulo1Etapa5)getActivity()).getPager();
        imagem1 = (ImageView) viewRoot.findViewById(R.id.imagem1Modulo1Etapa5Licao1);
        imagem2 = (ImageView) viewRoot.findViewById(R.id.imagem2Modulo1Etapa5Licao1);
        super.accessViews();
    }
}
