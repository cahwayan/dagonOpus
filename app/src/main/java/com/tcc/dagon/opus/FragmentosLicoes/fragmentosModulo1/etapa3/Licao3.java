package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa3;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa3;
import com.tcc.dagon.opus.R;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao3 extends Fragment {
    Button btnAvancar;
    ViewPager mViewPager;
    View viewRoot;
    private ImageView thumbImagem1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_modulo1_etapa3_licao3,container,false);

        thumbImagem1 = (ImageView) viewRoot.findViewById(R.id.imagem1Modulo1Etapa3Licao3);
        PhotoViewAttacher photoView = new PhotoViewAttacher(thumbImagem1);
        photoView.update();

        btnAvancar = (Button) viewRoot.findViewById(R.id.btnAvancar);
        mViewPager = ((ContainerModulo1Etapa3)getActivity()).getPager();
        listeners();
        return viewRoot;
    }

    private void listeners() {
        btnAvancar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                moveNext(mViewPager);
            }
        });
    }

    private void moveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }
}
