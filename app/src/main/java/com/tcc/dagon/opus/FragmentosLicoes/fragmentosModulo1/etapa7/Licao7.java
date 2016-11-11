package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa7;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Licao7 extends Licao {

    private ImageView imagem1, imagem2, imagem3, imagem4, imagem5;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.viewRoot = inflater.inflate(R.layout.fragment_modulo1_etapa7_licao7,container,false);
        this.accessViews();
        adicionarZoomImagem(imagem1);
        adicionarZoomImagem(imagem2);
        adicionarZoomImagem(imagem3);
        adicionarZoomImagem(imagem4);
        adicionarZoomImagem(imagem5);
        super.listeners();

        return this.viewRoot;
    }

    protected void accessViews() {
        mViewPager = ((ContainerModulo1Etapa7)getActivity()).getPager();
        imagem1 = (ImageView) viewRoot.findViewById(R.id.imagem1Modulo1Etapa7Licao4);
        imagem2 = (ImageView) viewRoot.findViewById(R.id.imagem2Modulo1Etapa7Licao4);
        imagem3 = (ImageView) viewRoot.findViewById(R.id.imagem3Modulo1Etapa7Licao4);
        imagem4 = (ImageView) viewRoot.findViewById(R.id.imagem4Modulo1Etapa7Licao4);
        imagem5 = (ImageView) viewRoot.findViewById(R.id.imagem5Modulo1Etapa7Licao4);
        super.accessViews();
    }
}
