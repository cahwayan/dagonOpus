package com.tcc.dagon.opus.ui.curso.licao;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.tcc.dagon.opus.ui.curso.container.ContainerLicoesActivity;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 04/11/2016.
 */ /**/

public class LicaoFragment extends Fragment {

    private Button btnAvancar;
    private ViewPager mViewPager;
    private View viewRoot;
    private int layoutID;

    public static LicaoFragment newInstance(int layoutID) {
        LicaoFragment licao = new LicaoFragment();
        licao.setContentView(layoutID);
        return licao;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.viewRoot = inflater.inflate( setContentView(this.layoutID), container, false);
        this.accessViews();
        this.setListeners();
        return this.viewRoot;
    }

    private int setContentView(int layoutID) {
        this.layoutID = layoutID;
        return this.layoutID;
    }

    private void accessViews() {
        mViewPager = ((ContainerLicoesActivity)getActivity()).getViewPager();
        btnAvancar = (Button) viewRoot.findViewById(R.id.btnAvancarLicao);
    }

    private void setListeners() {
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveNext();
            }
        });
    }

    private void moveNext() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    private void movePrevious() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

}
