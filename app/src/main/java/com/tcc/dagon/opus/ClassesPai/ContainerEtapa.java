package com.tcc.dagon.opus.ClassesPai;

import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class ContainerEtapa extends AppCompatActivity {

    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected GerenciadorBanco DB_PROGRESSO = null;
    protected LinearLayout tabStrip;
    protected int moduloAtual, etapaAtual;

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 1){
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 2) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 3) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 4) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 5) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 6) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 7) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 8) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 9) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 10) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 11) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 12) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 13) {
            movePrevious(mViewPager);
        }else {
            super.onBackPressed();
        }
    }

    protected void bloquearLicoes() {
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(R.drawable.icon_lock_licao);
            tabStrip.getChildAt(i).setClickable(false);
            tabStrip.getChildAt(i).setEnabled(false);
        }
    }

    protected void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                for(int i = 0; i <= progresso; i += 1) {
                    if(mTabLayout.getTabAt(i) != null) {
                        if( i % 2 == 0) {
                           mTabLayout.getTabAt(i).setIcon(R.drawable.icon_licao);
                        } else {
                           mTabLayout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                        }

                    }

                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
                break;
        }
    }

    protected void instanciaObjetos() {
        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
        }

    }

    public ViewPager getPager(){
        return mViewPager;
    }

    public LinearLayout getTabStrip() {
        return tabStrip;
    }

    public TabLayout getmTabLayout() {
        return mTabLayout;
    }

    protected void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

}
