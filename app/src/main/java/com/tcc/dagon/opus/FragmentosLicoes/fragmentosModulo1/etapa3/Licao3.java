package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa3;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa3;
import com.tcc.dagon.opus.R;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao3 extends Licao {
    ImageView imagem1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.viewRoot = inflater.inflate(R.layout.fragment_modulo1_etapa3_licao3,container,false);
        this.accessViews();
        super.adicionarZoomImagem(imagem1);
        super.listeners();

        return this.viewRoot;
    }

    protected void accessViews() {
        mViewPager = ((ContainerModulo1Etapa3)getActivity()).getPager();
        imagem1 = (ImageView) viewRoot.findViewById(R.id.imagem1Modulo1Etapa3Licao3);
        super.accessViews();
    }
}
