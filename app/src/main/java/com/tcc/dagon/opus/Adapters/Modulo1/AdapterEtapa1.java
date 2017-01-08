package com.tcc.dagon.opus.Adapters.Modulo1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.tcc.dagon.opus.ClassesPai.Adapter;
import com.tcc.dagon.opus.ClassesPai.Licao;
import com.tcc.dagon.opus.ClassesPai.Questao;
import com.tcc.dagon.opus.R;

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
 */

public class AdapterEtapa1 extends Adapter {

    public AdapterEtapa1(FragmentManager fm, String[] tabTitulos) {
        super(fm, tabTitulos);
        this.tabTitulos = tabTitulos;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return Licao.newInstance(R.layout.fragment_modulo1_etapa1_licao2);
            case 1:
                return Questao.newInstance(MODULO1, ETAPA1, QUESTAO1);
            default:
                return null;
        }
    }
}
