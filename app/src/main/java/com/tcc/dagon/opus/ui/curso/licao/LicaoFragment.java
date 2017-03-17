package com.tcc.dagon.opus.ui.curso.licao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tcc.dagon.opus.ui.curso.container.ContainerLicoesActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.curso.exercicios.ConteudoWrapper;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by cahwayan on 04/11/2016.
 */ /**/

public class LicaoFragment extends ConteudoWrapper {

    protected Button btnAvancar;
    protected ViewPager mViewPager;
    protected View viewRoot;
    protected PhotoViewAttacher photoView;
    private int layoutID = R.layout.id_resource;

    public static LicaoFragment novaLicao(int layoutID) {
        LicaoFragment licao = new LicaoFragment();
        licao.setContentView(layoutID);
        return licao;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateView(inflater, container, savedInstanceState);
        this.viewRoot = inflater.inflate( setContentView(this.layoutID), container, false);
        this.accessViews();
        this.listeners();
        return this.viewRoot;
    }

    private int setContentView(int layoutID) {
        this.layoutID = layoutID;
        return this.layoutID;
    }

    private void listeners() {
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveNext(mViewPager);
            }
        });
    }

    private void adicionarZoomImagem(ImageView imagem) {
        photoView = new PhotoViewAttacher(imagem);
        photoView.update();
    }

    private void accessViews() {
        mViewPager = ((ContainerLicoesActivity)getActivity()).getViewPager();
        btnAvancar = (Button) viewRoot.findViewById(R.id.btnAvancarLicao);
    }

    private void moveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

}
