package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ClassesPai.CompletarProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva3;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva4;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Questao2 extends CompletarProva {

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
        super.rootView = inflater.inflate(R.layout.fragment_modulo4_prova_questao2, container, false);
        super.moduloAtual = 4;
        super.etapaAtual  = 6;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva4)getActivity()).getPager();
        tabStrip   = ((ContainerProva4)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva4)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva4)getActivity()).getVida01();
        vida02 = ((ContainerProva4)getActivity()).getVida02();
        vida03 = ((ContainerProva4)getActivity()).getVida03();
        vida04 = ((ContainerProva4)getActivity()).getVida04();
        vida05 = ((ContainerProva4)getActivity()).getVida05();


        linha1Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo4Pergunta2Linha1Palavra1);
        linha1Palavra2 = (EditText) rootView.findViewById(R.id.ProvaModulo4Pergunta2Linha1Palavra2);
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo4Pergunta2Linha2Palavra1);
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.ProvaModulo4Pergunta2Linha4Palavra1);


        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha1Palavra1, linha1Palavra2, linha2Palavra1, linha4Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"procedimento", "fazerBolo", "inicio", "fimProcedimento"};
        respostasCertasAcentuadas = new String[]{"procedimento", "fazerBolo", "inicio", "fimProcedimento"};

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