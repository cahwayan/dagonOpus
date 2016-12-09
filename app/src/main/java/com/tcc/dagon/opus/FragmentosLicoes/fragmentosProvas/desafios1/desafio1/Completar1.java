package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.desafios1.desafio1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo5.ContainerModulo5Etapa1;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText linha1Palavra1,

                     linha2Palavra1, linha2Palavra2,

                     linha3Palavra1, linha3Palavra2,

                     linha4Palavra1, linha4Palavra2,

                     linha5Palavra1,

                     linha6Palavra1, linha6Palavra2, linha6Palavra3, linha6Palavra4,
                     linha6Palavra5, linha6Palavra6, linha6Palavra7,

                     linha7Palavra1, linha8Palavra1, linha8Palavra2,

                     linha9Palavra1, linha9Palavra2, linha9Palavra3, linha9Palavra4, linha9Palavra5,

                     linha10Palavra1, linha10Palavra2,

                     linha11Palavra1,

                     linha12Palavra1,

                     linha13Palavra1;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo5_etapa1_licao2, container, false);
        super.moduloAtual = 5;
        super.etapaAtual  = 1;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo5Etapa1)getActivity()).getPager();
        tabStrip   = ((ContainerModulo5Etapa1)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo5Etapa1)getActivity()).getmTabLayout();


        linha1Palavra1 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha1Palavra1);

        linha2Palavra1 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha2Palavra1);

        linha2Palavra2 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha2Palavra2);

        linha3Palavra1 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha3Palavra1);
        linha3Palavra2 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha3Palavra2);


        linha4Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha4Palavra1);

        linha4Palavra2 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha4Palavra2);

        linha5Palavra1 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha5Palavra1);

        linha6Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha6Palavra1);
        linha6Palavra2  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha6Palavra2);
        linha6Palavra3  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha6Palavra3);
        linha6Palavra4  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha6Palavra4);
        linha6Palavra5  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha6Palavra5);
        linha6Palavra6  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha6Palavra6);
        linha6Palavra7  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha6Palavra7);

        linha7Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha7Palavra1);
        linha8Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha8Palavra1);
        linha8Palavra2  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha8Palavra2);

        linha9Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha9Palavra1);
        linha9Palavra2  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha9Palavra2);
        linha9Palavra3  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha9Palavra3);
        linha9Palavra4  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha9Palavra4);
        linha9Palavra5  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha9Palavra5);

        linha10Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha10Palavra1);
        linha10Palavra2  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha10Palavra2);

        linha11Palavra1 = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha11Palavra1);

        linha12Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha12Palavra1);

        linha13Palavra1  = (EditText) rootView.findViewById(R.id.Modulo5Etapa1Pergunta1Linha13Palavra1);

        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[]   {linha1Palavra1,

                                            linha2Palavra1, linha2Palavra2,

                                            linha3Palavra1, linha3Palavra2,

                                            linha4Palavra1, linha4Palavra2,

                                            linha5Palavra1,

                                            linha6Palavra1, linha6Palavra2, linha6Palavra3, linha6Palavra4,
                                            linha6Palavra5, linha6Palavra6, linha6Palavra7,

                                            linha7Palavra1, linha8Palavra1, linha8Palavra2,

                                            linha9Palavra1, linha9Palavra2, linha9Palavra3, linha9Palavra4, linha9Palavra5,

                                            linha10Palavra1, linha10Palavra2,

                                            linha11Palavra1,

                                            linha12Palavra1,

                                            linha13Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]          {"var", "i", "inteiro", "num", "inteiro", "maiorNum", "inteiro", "inicio", "para",
                                                 "i", "de", "1", "ate", "5", "faca", "escreva", "leia", "num", "se", "num", ">",
                                                 "maiorNum", "entao", "maiorNum", "num", "fimSe", "fimPara", "escreva" };

        respostasCertasAcentuadas = new String[]{"var", "x", "inteiro", "num", "inteiro", "maiorNum",
                                                 "inteiro", "inicio", "para", "x", "de", "1", "ate",
                                                 "5", "faca", "escreva", "leia", "num", "se", "num", ">",
                                                 "maiorNum", "entao", "maiorNum", "num", "fimSe", "fimPara",
                                                 "escreva"};

        super.accessViews();

        // ESSE LOOP PEGA A RESPOSTA NO INDICE I E ATRIBUI AO VETOR
        // QUE GUARDA O TAMANHO DESSA PALAVRA NO VETOR DE TAMANHO
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            tamanhoPalavras[i] = respostasCertas[i].length();
        }

    }

    protected void listeners() {
        super.listeners();

        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespostasCompletar(respostasCertas, respostasCertasAcentuadas);
            }
        });

        btnTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente(respostasCertas, respostasCertasAcentuadas);
            }
        });

    }

    @Override
    protected void completarFinal() {
        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO

        if(this.DB_PROGRESSO.verificaProgressoModulo() <= moduloAtual) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.DB_PROGRESSO.atualizaProgressoModulo(moduloAtual + 1);
            // atualizar progresso do módulo 2 para 1
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual + 1, 1);
        }

        // INICIANDO ATIVIDADE DOS MODULOS

        startActivity(new Intent(getActivity(), AprenderActivity.class));
        // TERMINANDO COM ESSA ATIVIDADE
        this.getActivity().finish();
    }
}