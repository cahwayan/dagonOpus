package com.tcc.dagon.opus.telas.fragments.licoes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tcc.dagon.opus.telas.fragments.container.ContainerEtapa;
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
    private int layoutID = R.layout.id_resource;

    public static Licao novaLicao(int layoutID) {
        Licao licao = new Licao();
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
        mViewPager = ((ContainerEtapa)getActivity()).getPager();
        btnAvancar = (Button) viewRoot.findViewById(R.id.btnAvancarLicao);
    }

    private void moveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

}
