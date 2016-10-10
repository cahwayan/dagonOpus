package com.tcc.dagon.opus.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.ModulosFragments.Modulo1_Fragment_1;
import com.tcc.dagon.opus.ModulosFragments.Modulo1_Fragment_2;
import com.tcc.dagon.opus.ModulosFragments.Modulo1_Fragment_3;
import com.tcc.dagon.opus.ModulosFragments.Modulo1_Fragment_4;
//import com.tcc.dagon.opus.ModulosFragments.Modulo1_Fragment_5;
//import com.tcc.dagon.opus.ModulosFragments.Modulo1_Fragment_6;
import com.tcc.dagon.opus.ModulosFragments.Modulo1_Fragment_7;

/**
 * Created by charlinho on 09/10/2016.
 */
public class FragmentPageAdapter extends FragmentPagerAdapter {
private String[] tabTitulos;

    public FragmentPageAdapter(FragmentManager fm,String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new Modulo1_Fragment_1();
            case 1:
                return new Modulo1_Fragment_2();
            case 2:
                return new Modulo1_Fragment_3();
            case 3:
                return new Modulo1_Fragment_4();
            case 4:
                //return new Modulo1_Fragment_5();
            case 5:
               // return new Modulo1_Fragment_6();
            case 6:
                return new Modulo1_Fragment_7();

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
