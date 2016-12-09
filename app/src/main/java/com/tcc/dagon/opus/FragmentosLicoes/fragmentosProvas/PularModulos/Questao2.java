package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.PularModulos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.tcc.dagon.opus.ClassesPai.CompletarPular;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerPular1;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan 09/10/2016.
 */
public class Questao2 extends CompletarPular {

    private EditText
                    linha2Palavra1,
                    linha3Palavra1,
                    linha5Palavra1,
                    linha6Palavra1,
                    linha8Palavra1,
                    linha9Palavra1,
                    linha9Palavra2,
                    linha10Palavra1;

    private String[] respostasCertas ;
    private String[] respostasCertasAcentuadas;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_pular1_questao2, container, false);

        // DECLARANDO O MODULO E A ETAPA ATUAL A QUAL PERTENCE ESSA LIÇÃO
        // SERVE PARA FINS DE DEFINIR PROGRESSO NO BANCO DE DADOS
        super.moduloAtual = 1;
        super.etapaAtual  = 9;

        // TODA PRIMEIRA QUESTAO DA PROVA TEM QUE VIR COM 5 VIDAS
        super.mCallback.onArticleSelected(5);

        //TRAZENDO AS VIEWS
        accessViews();

        super.listeners();
        // TRAZENDO OS LISTENERS
        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerPular1)getActivity()).getPager();
        tabStrip   = ((ContainerPular1)getActivity()).getTabStrip();
        mTabLayout = ((ContainerPular1)getActivity()).getmTabLayout();

        vida01 = ((ContainerPular1)getActivity()).getVida01();
        vida02 = ((ContainerPular1)getActivity()).getVida02();
        vida03 = ((ContainerPular1)getActivity()).getVida03();
        vida04 = ((ContainerPular1)getActivity()).getVida04();
        vida05 = ((ContainerPular1)getActivity()).getVida05();

        // RESGATANDO A REFERENCIA DOS EDIT TEXTS QUE TERAO AS RESPOSTAS
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha2Palavra1);
        linha3Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha3Palavra1);
        linha5Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha5Palavra1);
        linha6Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha6Palavra1);
        linha8Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha8Palavra1);
        linha9Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha9Palavra1);
        linha9Palavra2 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha9Palavra2);
        linha10Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo1Pergunta2Linha10Palavra1);

        // CRIANDO UMA LISTA QUE VAI GUARDAR OS EDIT TEXTS
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha2Palavra1,
                                          linha3Palavra1,
                                          linha5Palavra1,
                                          linha6Palavra1,
                                          linha8Palavra1,
                                          linha9Palavra1,
                                          linha9Palavra2,
                                          linha10Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // DEFININDO AS RESPOSTAS DO EXERCICIO, NA ORDEM EM QUE DEVEM SER ESCRITAS
        respostasCertas = new String[]{"inteiro", "inteiro", "escreva", "leia", "numero2",
                                       "resultado", "numero2", "resultado"};

        respostasCertasAcentuadas = new String[]{"inteiro", "inteiro", "escreva",
                                                 "leia", "numero2",
                                                 "resultado", "numero2", "resultado"};

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