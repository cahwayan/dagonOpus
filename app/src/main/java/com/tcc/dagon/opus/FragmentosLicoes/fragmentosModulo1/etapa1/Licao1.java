package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.view.View.OnClickListener;

import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.R;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao1 extends Fragment {
    Button btnAvancar;
    ViewPager mViewPager;
    View viewRoot;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewRoot = inflater.inflate(R.layout.fragment_modulo1_etapa1_licao1,container,false);
        btnAvancar = (Button) viewRoot.findViewById(R.id.btnAvancarModulo1Etapa1Licao1);
        mViewPager = ((ContainerModulo1Etapa1)getActivity()).getPager();
        listeners();
        return viewRoot;
    }

    private void listeners() {
        btnAvancar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
    }
}
