package com.tcc.dagon.opus.ui.curso.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/*
* Created by Caíque on 10/03/2017
 * Essa classe é um adapter que encontra de maneira automática os fragmentos com lições e questões
 * que precisam ir em um viewpager para serem mostrados ao usuário. O adapter encontra os fragmentos através das variáveis
 * moduloAtual e etapaAtual, buscando eles no GerenciadorListaExercicios no momento de sua construção.
 * O valor das variáveis vem da classe ContainerLicoesActivity, que é uma activity que envelopa _todo o conteúdo dos fragmentos
 * e viewpager juntos.
*/

public class Adapter extends FragmentPagerAdapter {

    private final String[] tabTitulos;
    private final List<Fragment> listaExercicios;

    public Adapter(FragmentManager fm, String[] tabTitulos, int moduloAtual, int etapaAtual) {
        super(fm);
        listaExercicios = GerenciadorListaExercicios.getListaExercicios(moduloAtual, etapaAtual);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        return this.listaExercicios.get(position);
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
