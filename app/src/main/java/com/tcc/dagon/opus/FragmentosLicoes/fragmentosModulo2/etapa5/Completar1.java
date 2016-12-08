package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo2.etapa5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo2.ContainerModulo2Etapa5;
import com.tcc.dagon.opus.R;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Completar1 extends Completar {
    private EditText linha1Palavra1,
                     linha2Palavra1,
                     linha3Palavra1,
                     linha5Palavra1,
                     linha7Palavra1;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo2_etapa5_licao2, container, false);
        super.moduloAtual = 2;
        super.etapaAtual  = 5;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo2Etapa5)getActivity()).getPager();
        tabStrip   = ((ContainerModulo2Etapa5)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo2Etapa5)getActivity()).getmTabLayout();

        linha1Palavra1 = (EditText) rootView.findViewById(R.id.Modulo2Etapa5Pergunta1Linha1Palavra1);
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.Modulo2Etapa5Pergunta1Linha2Palavra1);
        linha3Palavra1 = (EditText) rootView.findViewById(R.id.Modulo2Etapa5Pergunta1Linha3Palavra1);
        linha5Palavra1 = (EditText) rootView.findViewById(R.id.Modulo2Etapa5Pergunta1Linha5Palavra1);
        linha7Palavra1 = (EditText) rootView.findViewById(R.id.Modulo2Etapa5Pergunta1Linha7Palavra1);

        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha1Palavra1, linha2Palavra1, linha3Palavra1,
                                          linha5Palavra1, linha7Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"repita", "num", "soma", "continuar", "soma"};
        respostasCertasAcentuadas = new String[]{"repita", "num", "soma", "continuar", "soma"};

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