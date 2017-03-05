package com.tcc.dagon.opus.telas.aprender.menulateral.glossario;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by charlinho on 09/10/2016.
 */

public class AdapterGlossario extends FragmentPagerAdapter {

    private String[] tabTitulos;

    public AdapterGlossario(FragmentManager fm, String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new FragmentComandosGlossario();
            case 1:
                return new FragmentTelaInterfaceGlossario();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.tabTitulos.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.tabTitulos[position];
    }




}
