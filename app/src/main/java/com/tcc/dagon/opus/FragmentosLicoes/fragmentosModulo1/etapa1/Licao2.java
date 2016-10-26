package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;

import com.tcc.dagon.opus.R;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao2 extends Fragment {
    Button btnAvancar;
    ViewPager mViewPager;
    View viewRoot;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_modulo1_etapa1_licao2,container,false);
        btnAvancar = (Button) viewRoot.findViewById(R.id.btnAvancar);
        mViewPager = ((ContainerModulo1Etapa1)getActivity()).getPager();
        listeners();
        return viewRoot;

    }

    private void listeners() {
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });
    }
}