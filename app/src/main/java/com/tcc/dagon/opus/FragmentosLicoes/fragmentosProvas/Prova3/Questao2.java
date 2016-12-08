package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.CompletarProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva3;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan 09/10/2016.
 */
public class Questao2 extends CompletarProva {

    private EditText linha4Palavra1,
                     linha4Palavra2,
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
        super.rootView = inflater.inflate(R.layout.fragment_modulo3_prova_questao2, container, false);

        // DECLARANDO O MODULO E A ETAPA ATUAL A QUAL PERTENCE ESSA LIÇÃO
        // SERVE PARA FINS DE DEFINIR PROGRESSO NO BANCO DE DADOS
        super.moduloAtual = 3;
        super.etapaAtual  = 3;

        //TRAZENDO AS VIEWS
        accessViews();

        super.listeners();
        // TRAZENDO OS LISTENERS
        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva3)getActivity()).getPager();
        tabStrip   = ((ContainerProva3)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva3)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva3)getActivity()).getVida01();
        vida02 = ((ContainerProva3)getActivity()).getVida02();
        vida03 = ((ContainerProva3)getActivity()).getVida03();
        vida04 = ((ContainerProva3)getActivity()).getVida04();
        vida05 = ((ContainerProva3)getActivity()).getVida05();

        // RESGATANDO A REFERENCIA DOS EDIT TEXTS QUE TERAO AS RESPOSTAS
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo3Pergunta2Linha4Palavra1);
        linha4Palavra2 = (EditText) rootView.findViewById(R.id.ProvaModulo3Pergunta2Linha4Palavra2);
        linha5Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo3Pergunta2Linha5Palavra1);
        linha7Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo3Pergunta2Linha7Palavra1);

        // CRIANDO UMA LISTA QUE VAI GUARDAR OS EDIT TEXTS
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[]   {linha4Palavra1,
                                            linha4Palavra2,
                                            linha5Palavra1,
                                            linha7Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // DEFININDO AS RESPOSTAS DO EXERCICIO, NA ORDEM EM QUE DEVEM SER ESCRITAS
        respostasCertas = new String[]{"numeros", "i", "i", "i"};

        respostasCertasAcentuadas = new String[]{"numeros", "i", "i", "i"};

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