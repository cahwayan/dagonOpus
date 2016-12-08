package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tcc.dagon.opus.ClassesPai.CompletarProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva1;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 11/11/2016.
 */

public class Questao7 extends CompletarProva {

    protected EditText linha1Palavra1,
            linha2Palavra1,
            linha3Palavra1,
            linha4Palavra1,
            linha5Palavra1,
            linha6Palavra1;

    protected String[] respostasCertas ;
    protected String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo1_prova_questao7, container, false);
        // DECLARANDO O MODULO E A ETAPA ATUAL A QUAL PERTENCE ESSA LIÇÃO
        // SERVE PARA FINS DE DEFINIR PROGRESSO NO BANCO DE DADOS
        super.moduloAtual = 1;
        super.etapaAtual  = 9;

        //TRAZENDO AS VIEWS
        this.accessViews();

        // TRAZENDO OS LISTENERS
        listeners();

        return this.rootView;
    }

    @Override
    protected void avancarCompletar() {

        limparEditTexts(listaEditTexts);
        habilitarEditTexts(listaEditTexts);

        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // TROCANDO O ICONE DO CADEADO
        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 1).setIcon(R.drawable.icon_pergunta);

        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setEnabled(true);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // TROCANDO O FRAGMENTO
        moveNext(mViewPager);

        if(DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual) <= mViewPager.getCurrentItem()) {
            // AVANÇAR O PROGRESSO EM DOIS
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, (mViewPager.getCurrentItem() + 1) );
        }

    }

    @Override
    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva1)getActivity()).getPager();
        tabStrip   = ((ContainerProva1)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva1)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva1)getActivity()).getVida01();
        vida02 = ((ContainerProva1)getActivity()).getVida02();
        vida03 = ((ContainerProva1)getActivity()).getVida03();
        vida04 = ((ContainerProva1)getActivity()).getVida04();
        vida05 = ((ContainerProva1)getActivity()).getVida05();

        // RESGATANDO A REFERENCIA DOS EDIT TEXTS QUE TERAO AS RESPOSTAS
        linha1Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa7Pergunta4Linha1Palavra1);
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa7Pergunta4Linha2Palavra1);
        linha3Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa7Pergunta4Linha3Palavra1);
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa7Pergunta4Linha4Palavra1);
        linha5Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa7Pergunta4Linha5Palavra1);
        linha6Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa7Pergunta4Linha6Palavra1);

        // CRIANDO UMA LISTA QUE VAI GUARDAR OS EDIT TEXTS
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {linha1Palavra1, linha2Palavra1, linha3Palavra1,
                linha4Palavra1, linha5Palavra1, linha6Palavra1};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // DEFININDO AS RESPOSTAS DO EXERCICIO, NA ORDEM EM QUE DEVEM SER ESCRITAS
        respostasCertas = new String[]{"verdadeiro", "falso", "falso",
                "verdadeiro", "verdadeiro", "falso"};

        respostasCertasAcentuadas = new String[]{"verdadeiro", "falso", "falso",
                "verdadeiro", "verdadeiro", "falso"};

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
