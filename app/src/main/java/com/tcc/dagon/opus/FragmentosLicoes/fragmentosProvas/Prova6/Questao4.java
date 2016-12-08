package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.tcc.dagon.opus.ClassesPai.CompletarProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva6;
import com.tcc.dagon.opus.R;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan 09/10/2016.
 */
public class Questao4 extends CompletarProva {

    private EditText palavra1, palavra2;

    private static final int tamanhoPalavra1 = 6;
    private static final int tamanhoPalavra2 = 9;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo6_prova_questao4, container, false);

        // DECLARANDO O MODULO E A ETAPA ATUAL A QUAL PERTENCE ESSA LIÇÃO
        // SERVE PARA FINS DE DEFINIR PROGRESSO NO BANCO DE DADOS
        super.moduloAtual = 6;
        super.etapaAtual  = 10;

        //TRAZENDO AS VIEWS
        accessViews();

        // TRAZENDO OS LISTENERS
        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva6)getActivity()).getPager();
        tabStrip   = ((ContainerProva6)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva6)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva6)getActivity()).getVida01();
        vida02 = ((ContainerProva6)getActivity()).getVida02();
        vida03 = ((ContainerProva6)getActivity()).getVida03();
        vida04 = ((ContainerProva6)getActivity()).getVida04();
        vida05 = ((ContainerProva6)getActivity()).getVida05();

        // RESGATANDO A REFERENCIA DOS EDIT TEXTS QUE TERAO AS RESPOSTAS
        palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
        palavra2 = (EditText) rootView.findViewById(R.id.palavra2);

        // CRIANDO UMA LISTA QUE VAI GUARDAR OS EDIT TEXTS
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[]    {palavra1, palavra2};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        // DEFININDO AS RESPOSTAS DO EXERCICIO, NA ORDEM EM QUE DEVEM SER ESCRITAS
        respostasCertas = new String[]{"classe", "bicicleta"};

        respostasCertasAcentuadas = new String[]{"classe", "bicicleta"};

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

        adicionarClickListenerEditText(tamanhoPalavra1, palavra1, palavra2);
        adicionarClickListenerEditText(tamanhoPalavra2, palavra2, null);


    }


}