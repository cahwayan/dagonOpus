package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.CompletarProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva1;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan 09/10/2016.
 */
public class Questao6 extends CompletarProva {

    private EditText
            linha1Palavra1,
            linha2Palavra1,
            linha3Palavra1,
            linha4Palavra1,
            linha5Palavra1;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo1_prova_questao6, container, false);

        // DECLARANDO O MODULO E A ETAPA ATUAL A QUAL PERTENCE ESSA LIÇÃO
        // SERVE PARA FINS DE DEFINIR PROGRESSO NO BANCO DE DADOS
        super.moduloAtual = 1;
        super.etapaAtual  = 9;

        //TRAZENDO AS VIEWS
        accessViews();

        // TRAZENDO OS LISTENERS
        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva1)getActivity()).getPager();
        tabStrip   = ((ContainerProva1)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva1)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva1)getActivity()).getVida01();
        vida02 = ((ContainerProva1)getActivity()).getVida02();
        vida03 = ((ContainerProva1)getActivity()).getVida03();
        vida04 = ((ContainerProva1)getActivity()).getVida04();
        vida05 = ((ContainerProva1)getActivity()).getVida05();

        // RESGATANDO A REFERENCIA DOS EDIT TEXTS QUE TERAO AS RESPOSTAS
        linha1Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta6Linha1Palavra1);
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta6Linha2Palavra1);
        linha3Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta6Linha3Palavra1);
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta6Linha4Palavra1);
        linha5Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta6Linha5Palavra1);

        // CRIANDO UMA LISTA QUE VAI GUARDAR OS EDIT TEXTS
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[]    {linha1Palavra1, linha2Palavra1, linha3Palavra1,
                                             linha4Palavra1, linha5Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // CHAMANDO AS VIEWS DA SUPER CLASSE
        super.accessViews();

        // DEFININDO AS RESPOSTAS DO EXERCICIO, NA ORDEM EM QUE DEVEM SER ESCRITAS
        respostasCertas = new String[]{"falso", "verdadeiro", "verdadeiro", "falso", "falso"};

        respostasCertasAcentuadas = new String[]{"falso", "verdadeiro", "verdadeiro", "falso", "falso"};
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

        linha1Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha1Palavra1.getText().length() == 5) {
                    linha2Palavra1.requestFocus();
                }
            }
        });

        linha2Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha2Palavra1.getText().length() == 10) {
                    linha3Palavra1.requestFocus();
                }
            }
        });

        linha3Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha3Palavra1.getText().length() == 10) {
                    linha4Palavra1.requestFocus();
                }
            }
        });

        linha4Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha4Palavra1.getText().length() == 5) {
                    linha5Palavra1.requestFocus();
                }
            }
        });

        linha5Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha5Palavra1.getText().length() == 5) {
                    escondeTeclado();
                }
            }
        });


    }

}