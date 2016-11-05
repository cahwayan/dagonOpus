package com.tcc.dagon.opus.ClassesPai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa2;
import com.tcc.dagon.opus.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class Licao extends Fragment {
    protected Button btnAvancar;
    protected ViewPager mViewPager;
    protected View viewRoot;
    protected PhotoViewAttacher photoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    protected void listeners() {
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveNext(mViewPager);
            }
        });
    }

    protected void adicionarZoomImagem(ImageView imagem) {
        photoView = new PhotoViewAttacher(imagem);
        photoView.update();
    }

    protected void accessViews() {
        btnAvancar = (Button) viewRoot.findViewById(R.id.btnAvancarLicao);
    }

    protected void moveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    protected void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

}
