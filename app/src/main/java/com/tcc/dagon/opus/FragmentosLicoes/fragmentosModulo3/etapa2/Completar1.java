package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo3.etapa2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3.ContainerModulo3Etapa2;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText linha4Palavra1,
                     linha7Palavra1;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo3_etapa2_licao2, container, false);
        super.moduloAtual = 3;
        super.etapaAtual  = 2;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo3Etapa2)getActivity()).getPager();
        tabStrip   = ((ContainerModulo3Etapa2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo3Etapa2)getActivity()).getmTabLayout();

        linha4Palavra1 = (EditText) rootView.findViewById(R.id.Modulo3Etapa2Pergunta1Linha4Palavra1);
        linha7Palavra1 = (EditText) rootView.findViewById(R.id.Modulo3Etapa2Pergunta1Linha7Palavra1);

        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha4Palavra1, linha7Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        super.accessViews();

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"i", "i"};
        respostasCertasAcentuadas = new String[]{"i", "i"};
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