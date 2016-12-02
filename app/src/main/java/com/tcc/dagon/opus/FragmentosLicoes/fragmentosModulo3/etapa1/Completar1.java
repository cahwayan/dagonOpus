package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo3.etapa1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3.ContainerModulo3Etapa1;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText linha3Palavra1,
                     linha3Palavra2;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo3_etapa1_licao4, container, false);
        super.moduloAtual = 3;
        super.etapaAtual  = 1;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo3Etapa1)getActivity()).getPager();
        tabStrip   = ((ContainerModulo3Etapa1)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo3Etapa1)getActivity()).getmTabLayout();

        linha3Palavra1 = (EditText) rootView.findViewById(R.id.Modulo3Etapa1Pergunta2Linha3Palavra1);
        linha3Palavra2 = (EditText) rootView.findViewById(R.id.Modulo3Etapa1Pergunta2Linha3Palavra2);

        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha3Palavra1, linha3Palavra2};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        super.accessViews();

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"2", "x"};
        respostasCertasAcentuadas = new String[]{"2", "x"};
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