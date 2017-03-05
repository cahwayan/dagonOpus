package com.tcc.dagon.opus.telas.fragments.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tcc.dagon.opus.telas.fragments.exercicios.FragmentosConteudo;

import java.util.List;

/**
 * Created by cahwayan on 09/10/2016.
 *
 * CLASSE ADAPTER
 * ESSA CLASSE É RESPONSÁVEL POR INSTANCIAR OS FRAGMENTOS DE ACORDO COM A NAVEGAÇÃO DO USUÁRIO.
 * HÁ CINCO TIPOS DE INSTÂNCIAS PARA O CONTEÚDO DO CURSO:
 * LIÇÃO: PRECISA RECEBER UM LAYOUT COMO PARÂMETRO
 * QUESTÃO DE ALTERNATIVA: PRECISA RECEBER O MÓDULO, ETAPA E O NÚMERO DA QUESTÃO COMO PARÂMETROS
 * EXERCÍCIO DE COMPLETAR: PRECISA RECEBER UM LAYOUT, O MÓDULO ATUAL, A ETAPA ATUAL, A QUANTIDADE DE PALAVRAS,
 *                         AS RESPOSTAS CERTAS SEM ACENTO, E AS RESPOSTAS CERTAS COM ACENTUAÇÃO.
 * QUESTÃO DE ALTERNATIVAS PROVA: MESMO DA QUESTÃO DE ALTERNATIVA
 * EXERCÍCIO COMPLETAR PROVA: MESMO DO COMPLETAR NORMAL
 *
 * ESTA CLASSE POSSUI CONSTANTES QUE FACILITAM A LEITURA DAS INSTÂNCIAS DAS CLASSES FILHAS*/

public class Adapter extends FragmentPagerAdapter {

    private String[] tabTitulos;
    private List<FragmentosConteudo> listaExercicios;

    /* RESPOSTAS QUE IRÃO PARA A INSTÂNCIA DO EXERCÍCIO DE COMPLETAR
    *  ELAS PRECISAM IR EM UM VETOR*/
    protected String respostasCertas[];
    protected String respostasCertasAcentuadas[];


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
