package com.tcc.dagon.opus.telas.fragments.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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

    protected String[] tabTitulos;

    /* CONSTANTES NÚMEROS MÓDULOS */
    protected final int MODULO1  = 1;
    protected final int MODULO2  = 2;
    protected final int MODULO3  = 3;
    protected final int MODULO4  = 4;
    protected final int MODULO5  = 5;
    protected final int MODULO6  = 6;
    protected final int MODULO7  = 7;
    protected final int MODULO8  = 8;
    protected final int MODULO9  = 9;
    protected final int MODULO10 = 10;

    /* CONSTANTES ETAPAS */
    protected final int ETAPA1 = 1;
    protected final int ETAPA2 = 2;
    protected final int ETAPA3 = 3;
    protected final int ETAPA4 = 4;
    protected final int ETAPA5 = 5;
    protected final int ETAPA6 = 6;
    protected final int ETAPA7 = 7;
    protected final int ETAPA8 = 8;
    protected final int ETAPA9 = 9;
    protected final int ETAPA10 = 10;
    protected final int ETAPA11 = 11;
    protected final int ETAPA12 = 12;
    protected final int ETAPA13 = 13;
    protected final int ETAPA14 = 14;
    protected final int ETAPA15 = 15;

    protected final int QUESTAO1  = 1;
    protected final int QUESTAO2  = 2;
    protected final int QUESTAO3  = 3;
    protected final int QUESTAO4  = 4;
    protected final int QUESTAO5  = 5;
    protected final int QUESTAO6  = 6;
    protected final int QUESTAO7  = 7;
    protected final int QUESTAO8  = 8;
    protected final int QUESTAO9  = 9;
    protected final int QUESTAO10 = 10;
    protected final int QUESTAO11 = 11;
    protected final int QUESTAO12 = 12;
    protected final int QUESTAO13 = 13;
    protected final int QUESTAO14 = 14;
    protected final int QUESTAO15 = 15;

    /* RESPOSTAS QUE IRÃO PARA A INSTÂNCIA DO EXERCÍCIO DE COMPLETAR
    *  ELAS PRECISAM IR EM UM VETOR*/
    protected String respostasCertas[];
    protected String respostasCertasAcentuadas[];


    public Adapter(FragmentManager fm, String[] tabTitulos) {
        super(fm);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
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