package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa4;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText linha1Palavra1,
                     linha3Palavra1,
                     linha4Palavra1,
                     linha5Palavra1,
                     linha5Palavra2;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo4_etapa4_licao2, container, false);
        super.moduloAtual = 4;
        super.etapaAtual  = 4;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo4Etapa4)getActivity()).getPager();
        tabStrip   = ((ContainerModulo4Etapa4)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo4Etapa4)getActivity()).getmTabLayout();

        linha1Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa4Pergunta1Linha1Palavra1);
        linha3Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa4Pergunta1Linha3Palavra1);
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa4Pergunta1Linha4Palavra1);

        linha5Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa4Pergunta1Linha5Palavra1);

        linha5Palavra2 = (EditText) rootView.findViewById(R.id.Modulo4Etapa4Pergunta1Linha5Palavra2);

        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha1Palavra1, linha3Palavra1,
                                          linha4Palavra1, linha5Palavra1, linha5Palavra2};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"inteiro", "a", "b", "a", "b"};
        respostasCertasAcentuadas = new String[]{"inteiro", "b", "a", "b", "a"};

        // VIEWS DA SUPERCLASSE
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
}