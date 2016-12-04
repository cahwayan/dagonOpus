package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa2;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText linha1Palavra1,
                     linha1Palavra2,
                     linha2Palavra1,
                     linha4Palavra1;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo4_etapa2_licao2, container, false);
        super.moduloAtual = 4;
        super.etapaAtual  = 2;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo4Etapa2)getActivity()).getPager();
        tabStrip   = ((ContainerModulo4Etapa2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo4Etapa2)getActivity()).getmTabLayout();


        linha1Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa2Pergunta1Linha1Palavra1);
        linha1Palavra2 = (EditText) rootView.findViewById(R.id.Modulo4Etapa2Pergunta1Linha1Palavra2);
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa2Pergunta1Linha2Palavra1);
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa2Pergunta1Linha4Palavra1);


        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha1Palavra1, linha1Palavra2, linha2Palavra1, linha4Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        super.accessViews();

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"procedimento", "procLiquidificador", "inicio", "fimProcedimento"};
        respostasCertasAcentuadas = new String[]{"procedimento", "procLiquidificador", "inicio", "fimProcedimento"};
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