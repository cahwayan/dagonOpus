package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.tcc.dagon.opus.ClassesPai.CompletarProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva2;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan 09/10/2016.
 */
public class Questao2 extends CompletarProva {

    private EditText
                    linha2Palavra1,
                    linha2Palavra2,
                    linha4Palavra1,
                    linha5Palavra1;

    private String[] respostasCertas ;
    private String[] respostasCertasAcentuadas;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo2_prova_licao2, container, false);

        // DECLARANDO O MODULO E A ETAPA ATUAL A QUAL PERTENCE ESSA LIÇÃO
        // SERVE PARA FINS DE DEFINIR PROGRESSO NO BANCO DE DADOS
        super.moduloAtual = 1;
        super.etapaAtual  = 6;

        //TRAZENDO AS VIEWS
        accessViews();

        super.listeners();
        // TRAZENDO OS LISTENERS
        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva2)getActivity()).getPager();
        tabStrip   = ((ContainerProva2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva2)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva2)getActivity()).getVida01();
        vida02 = ((ContainerProva2)getActivity()).getVida02();
        vida03 = ((ContainerProva2)getActivity()).getVida03();
        vida04 = ((ContainerProva2)getActivity()).getVida04();
        vida05 = ((ContainerProva2)getActivity()).getVida05();

        // RESGATANDO A REFERENCIA DOS EDIT TEXTS QUE TERAO AS RESPOSTAS
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo2Pergunta2Linha2Palavra1);
        linha2Palavra2 = (EditText) rootView.findViewById(R.id.ProvaModulo2Pergunta2Linha2Palavra2);
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo2Pergunta2Linha4Palavra1);
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo2Pergunta2Linha4Palavra2);
        linha5Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo2Pergunta2Linha5Palavra1);

        // CRIANDO UMA LISTA QUE VAI GUARDAR OS EDIT TEXTS
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[]   {linha2Palavra1,
                                            linha2Palavra2,
                                            linha4Palavra1,
                                            linha4Palavra1,
                                            linha5Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // CHAMANDO AS VIEWS DA SUPER CLASSE
        super.accessViews();

        // DEFININDO AS RESPOSTAS DO EXERCICIO, NA ORDEM EM QUE DEVEM SER ESCRITAS
        respostasCertas = new String[]{"b", "entao", "se", "b", "escreva"};

        respostasCertasAcentuadas = new String[]{"b", "entao", "se", "b", "escreva"};
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