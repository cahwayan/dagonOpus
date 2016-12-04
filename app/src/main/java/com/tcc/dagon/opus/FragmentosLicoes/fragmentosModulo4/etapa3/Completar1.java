package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo4.etapa3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo4.ContainerModulo4Etapa3;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText linha2Palavra1,
                     linha2Palavra2,
                     linha3Palavra1,
                     linha6Palavra1,
                     linha10Palavra1,
                     linha12Palavra1;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo4_etapa3_licao2, container, false);
        super.moduloAtual = 4;
        super.etapaAtual  = 3;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo4Etapa3)getActivity()).getPager();
        tabStrip   = ((ContainerModulo4Etapa3)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo4Etapa3)getActivity()).getmTabLayout();

        linha2Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa3Pergunta1Linha2Palavra1);
        linha2Palavra2 = (EditText) rootView.findViewById(R.id.Modulo4Etapa3Pergunta1Linha2Palavra2);
        linha3Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa3Pergunta1Linha3Palavra1);
        linha6Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa3Pergunta1Linha6Palavra1);
        linha10Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa3Pergunta1Linha10Palavra1);
        linha12Palavra1 = (EditText) rootView.findViewById(R.id.Modulo4Etapa3Pergunta1Linha12Palavra1);

        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha2Palavra1, linha2Palavra2,
                                          linha3Palavra1, linha6Palavra1, linha10Palavra1, linha12Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        super.accessViews();

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"procedimento", "topo", "inicio", "topo", "topo", "topo"};
        respostasCertasAcentuadas = new String[]{"procedimento", "topo", "início", "topo", "topo", "topo"};
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