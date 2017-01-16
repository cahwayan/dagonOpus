package com.tcc.dagon.opus.Activities.Fragments.Exercicios;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tcc.dagon.opus.Interfaces.Completar;
import com.tcc.dagon.opus.Interfaces.Exercicio;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cahwayan on 04/11/2016.
 * EXERCÍCIO DE COMPLETAR DO CURSO
 */


public class CCompletar extends CExercicio implements Exercicio, Completar {

    protected View rootView;

    /* VARIÁVEIS */
    // GUARDA O ID DO LAYOUT QUE SERÁ USADO NA INSTÂNCIA
    protected int layoutID;
    protected int moduloAtual, etapaAtual;

    /* LISTAS, COLEÇÕES */
    // GUARDA AS EDIT TEXTS EM UMA LISTA DE OBJETOS PARA PODER TRABALHAR COM ELAS DE MANEIRA DINÂMICA
    protected List<EditText> listRespostasUsuario;

    private int quantidadePalavras;

    /* VETORES */
    /* VETOR QUE GUARDA AS EDIT TEXTS. ELA É NECESSÁRIA APENAS PARA PASSAR AS EDIT TEXTS PARA UMA LISTA
    *  QUE É MELHOR DE SER TRABALHADA*/
    private EditText totalDeRespostas[];

    /* VETOR QUE PEGA AS RESPOSTAS DIGITADAS PELO USUÁRIO. ESSE VETOR PEGA OS VALORES DA LISTA DE EDIT TEXTS*/
    private String   respostasUsuario[];

    /* VETORES QUE GUARDAM AS RESPOSTAS CERTAS PARA DETERMINADO EXERCÍCIO*/
    private String   respostasCertas[];
    private String   respostasCertasAcentuadas[];

    /* VETOR QUE GUARDA O TAMANHO DAS PALAVRAS DAS RESPOSTAS CERTAS
    *  ESSE VETOR SERVE PARA ADICIONAR LISTENERS DE MANEIRA PROGRAMÁTICA DE ACORDO COM O
    *  TAMANHO DA RESPOSTA. O LISTENER EM QUESTÃO É O TEXT WATCHER, E FAZ PULAR DE UMA
    *  EDIT TEXT PARA OUTRA ASSIM QUE O TAMANHO DA PALAVRA CORRETA FOR ATINGIDO*/
    private int lengthRespostas[];

    /* MÉTODO ESTÁTICO DE INSTÂNCIA. COMO FRAGMENTOS NÃO POSSUEM SUPORTE DECENTE PARA O USO DE MÉTODOS CONSTRUTORES
    * (NA VERDADE NÃO É RECOMENDADO NEM SOBRESCREVER O CONSTRUTOR DE UM FRAGMENT)
    * CRIAMOS UM MÉTODO ESTÁTICO, QUE PODE SER ACESSADO DE QUALQUER LUGAR, QUE SERVE PARA INSTANCIAR A CLASSE COMO UM MÉTODO CONSTRUTOR.
    * ESSE MÉTODO RECEBE OS PARÂMETROS, E PASSA PARA O ONCREATE ATRAVÉS DE UM BUNDLE. LÁ ENTÃO, PODEMOS PEGAR ESSES VALORES E TRABALHAR COM ELES.
    * É IMPORTANTE SABER QUE AS MODIFICAÇÕES FEITAS NESSE MÉTODO, SÃO REALIZADAS ANTES DO MÉTODO ONCREATE SER EXECUTADO, POR ISSO, SERVE PERFEITAMENTE
    * COMO UM CONSTRUTOR*/
    public static CCompletar novoCompletar(int layoutID, int moduloAtual, int etapaAtual, int quantidadePalavras, String[] respostasCertas, String[] respostasCertasAcentuadas) {
        CCompletar completar = new CCompletar();

        //completar.setLayoutID(layoutID);
        Bundle args = new Bundle();
        args.putInt("moduloAtual", moduloAtual);
        args.putInt("etapaAtual", etapaAtual);
        args.putInt("quantidadePalavras", quantidadePalavras);
        args.putInt("layoutID", layoutID);
        args.putStringArray("respostasCertas", respostasCertas);
        args.putStringArray("respostasCertasAcentuadas", respostasCertasAcentuadas);

        completar.setArguments(args);
        return completar;
    }

    /* CICLO DE VIDA DO APP */

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(somRespostaCerta != null) {
            somRespostaCerta.release();
            somRespostaCerta = null;
        }

        if(somRespostaErrada != null) {
            somRespostaErrada.release();
            somRespostaErrada = null;
        }

        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.getConstructorArgs();
        super.onCreateView(inflater, container, savedInstanceState);
        this.setRootView(this.layoutID, inflater, container, savedInstanceState);
        super.instanciaObjetos();
        this.configurarVetoresListas();
        this.accessViews(this.rootView);
        this.listeners();
        return this.rootView;
    }

    @Override
    public void getConstructorArgs() {
        super.getConstructorArgs();
        this.quantidadePalavras        = getArguments().getInt("quantidadePalavras", 0);
        this.respostasCertas           = getArguments().getStringArray("respostasCertas");
        this.respostasCertasAcentuadas = getArguments().getStringArray("respostasCertasAcentuadas");
        this.layoutID                  = getArguments().getInt("layoutID", 0);
    }

    protected void setRootView(int layoutID, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(layoutID, container, false);
    }

    protected void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    protected void configurarVetoresListas() {
        this.listRespostasUsuario = new ArrayList<>();

        this.verificaQuantidadePalavras(this.quantidadePalavras);

        this.listRespostasUsuario.addAll(Arrays.asList(this.totalDeRespostas));

        // VETORES QUE TOMARÃO O TAMANHO DA LISTA DE EDIT TEXTS
        final int TAMANHO_LISTA_RESPOSTAS = this.listRespostasUsuario.size();
        this.totalDeRespostas = new EditText[TAMANHO_LISTA_RESPOSTAS];
        this.respostasUsuario  = new String[TAMANHO_LISTA_RESPOSTAS];
        this.lengthRespostas   = new int[TAMANHO_LISTA_RESPOSTAS];

        this.getLengthRespostas();

    }

    protected void getLengthRespostas() {
        final int RESPOSTAS_USUARIO = this.listRespostasUsuario.size() - 1;
        for(int i = 0; i <= RESPOSTAS_USUARIO; i++) {
            // O vetor lengthRespostas guarda o tamanho das strings que guardam as respostas certas.
            // as respostas certas são passadas por parâmetro no construtor, na ordem em que devem
            // ser preenchidas
            this.lengthRespostas[i] = this.respostasCertas[i].length();
        }
    }

    @Override
    public void listeners() {

        super.listeners();

        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecarResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarRespostaUsuario(respostasCertas, respostasCertasAcentuadas);
            }
        });

        btnTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente(respostasCertas, respostasCertasAcentuadas);
            }
        });

        /*LISTENERS DAS EDIT TEXTS PARA AVANÇAREM QUANDO FOR PREENCHIDA A PALAVRA*/
        // de 0 até o tamanho do vetor que guarda o tamanho de cada palavra
        for(int i = 0; i <= (listRespostasUsuario.size() - 1) ; i++) {
            // pegue a coleção que está na lista, jogue no vetor
            // adicione um click listener que tenha:
            // o tamanho da palavra na posição i,
            // na edit text da posição i
            // e sete o focus para a próxima edit text
            totalDeRespostas[i] = listRespostasUsuario.get(i);

            try {

                adicionarClickListenerEditText(lengthRespostas[i], totalDeRespostas[i], listRespostasUsuario.get(i + 1));

            } catch(IndexOutOfBoundsException erroLista) {
                erroLista.printStackTrace();
                adicionarClickListenerEditText(lengthRespostas[i], totalDeRespostas[i], null);
            }

        }

        // LISTENER QUE VERIFICA QUANDO A ABA SELECIONADA É MUDADA, SELECIONADA ou RE-SELECIONADA
        // ELE É IMPORTANTE PARA ESVAZIAR AS EDIT TEXTS AO SAIR DA ATIVIDADE ENQUANTO ESTÃO PREENCHIDAS
        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                resetarEstadoExercicio();
                setQtdErros(0);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                resetarEstadoExercicio();
                setQtdErros(0);
            }
        });

        this.imgRespostaErrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente(respostasCertas, respostasCertasAcentuadas);
            }
        });
    }

    public void validarRespostaUsuario(){}

    // MÉTODO QUE CHECA RESPOSTAS DO COMPLETAR
    private void validarRespostaUsuario(String[] respostasCertas, String[] respostasCertasAcentuadas) {

        int qtdRespostasCorretas = 0;

        final int TAMANHO_LISTA_RESPOSTAS = listRespostasUsuario.size() - 1;

        // ESSE LOOP ENCHE OS VETORES COM OS DADOS A SEREM CHECADOS
        for(int i = 0; i <= TAMANHO_LISTA_RESPOSTAS; i++) {

            // PASSANDO AS STRINGS QUE ESTÃO NAS EDIT TEXTS PARA UM VETOR PARA PODER COMPARAR
            totalDeRespostas[i] = listRespostasUsuario.get(i);
            respostasUsuario[i] = totalDeRespostas[i].getText().toString();

            // SE A RESPOSTA DO USUARIO FORNECIDA NA POSIÇÃO I FOR IGUAL A RESPOSTA CERTA DEFINIDA NA CLASSE FILHA, OU IGUAL À VERSÃO ACENTUADA,
            // INCREMENTAR 1 NA QUANTIDADE DE RESPOSTAS CORRETAS
            String RESPOSTA_USUARIO = respostasUsuario[i];
            String RESPOSTA_CERTA   = respostasCertas[i];
            String RESPOSTA_CERTA_ACENTUADA = respostasCertasAcentuadas[i];

            if(RESPOSTA_USUARIO.equalsIgnoreCase(RESPOSTA_CERTA) || RESPOSTA_USUARIO.equalsIgnoreCase(RESPOSTA_CERTA_ACENTUADA)) {
                qtdRespostasCorretas++;
            }
        }

        // SE A QUANTIDADE DE RESPOSTAS CORRETAS FOR IGUAL AO TAMANHO DO VETOR DE EDIT TEXTS, SIGNIFICA QUE O USUÁRIO ACERTOU TODAS AS PALAVRAS
        int TOTAL_QUANTIDADE_RESPOSTAS = totalDeRespostas.length;

        if(qtdRespostasCorretas == TOTAL_QUANTIDADE_RESPOSTAS) {
            respostaCerta();
        } else {
            // SE NÃO FOR IGUAL, ELE ERROU, CHAMAR MÉTODO DE RESPOSTA ERRADA COM OS PARÂMETROS
            respostaErrada(respostasCertas, respostasCertasAcentuadas);
        }
    }

    // MÉTODO QUE DESABILITA AS EDIT TEXTS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    private void desabilitarEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            this.totalDeRespostas[i] = listaEditTexts.get(i);
            this.totalDeRespostas[i].setInputType(InputType.TYPE_NULL);
            this.totalDeRespostas[i].setFocusable(false);
            this.totalDeRespostas[i].setFocusableInTouchMode(false);
        }
    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    protected void habilitarEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            this.totalDeRespostas[i] = listaEditTexts.get(i);
            this.totalDeRespostas[i].setInputType(InputType.TYPE_CLASS_TEXT);
            this.totalDeRespostas[i].setFocusable(true);
            this.totalDeRespostas[i].setFocusableInTouchMode(true);
        }
    }

    // MÉTODO DE LIMPAR AS EDIT TEXTS
    protected void limparEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            this.totalDeRespostas[i] = listaEditTexts.get(i);
            this.totalDeRespostas[i].setText("");
        }
    }

    private void resetarEstadoExercicio() {
        zerarPontuacao();
        this.limparEditTexts(this.listRespostasUsuario);
        this.habilitarEditTexts(this.listRespostasUsuario);

        for(int i = 0; i <= (this.listRespostasUsuario.size() - 1); i++) {
            mudarCorEditTexts(Color.BLACK, i);
        }
    }

    private void mudarCorEditTexts(int color, int contador) {
        totalDeRespostas[contador] = listRespostasUsuario.get(contador);
        this.totalDeRespostas[contador].setTextColor(color);
    }

    private void limparEditTextsComRespostaErrada(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        for(int i = 0; i <= (this.listRespostasUsuario.size() - 1); i++) {
            this.respostasUsuario[i] = this.totalDeRespostas[i].getText().toString();
            if(!this.respostasUsuario[i].equalsIgnoreCase(respostasCertas[i]) && !this.respostasUsuario[i].equalsIgnoreCase(respostasCertasAcentuadas[i]) ){
                this.totalDeRespostas[i] = this.listRespostasUsuario.get(i);
                this.totalDeRespostas[i].setText("");
            }
        }
    }

    @Override
    public void setPontuacao() {
        this.pontuacao += 1000;
        switch (qtdErros) {
            case 0: this.pontuacao += 500;
                break;
            case 1: this.pontuacao -= 50;
                break;
            case 2: this.pontuacao -= 100;
                break;
            case 3: this.pontuacao -= 150;
                break;
            case 4: this.pontuacao -= 200;
                break;
            case 5: this.pontuacao -= 250;
                break;
            case 6: this.pontuacao -= 300;
                break;
            case 7: this.pontuacao -= 350;
                break;
            case 8: this.pontuacao -= 400;
                break;
            case 9: this.pontuacao -= 450;
                break;
            case 10: this.pontuacao -= 500;
                break;
            default: this.pontuacao = 0;
                break;
        }

        txtPontos.setText("Pontos: " + String.valueOf(this.pontuacao) );
    }

    @Override
    public void respostaCerta() {
        super.respostaCerta();

        // BLOQUEAR EDIT TEXTS
        desabilitarEditTexts(listRespostasUsuario);
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    protected void respostaErrada(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        super.respostaErrada();

        for(int i = 0; i <= (listRespostasUsuario.size() - 1); i++) {
            String RESPOSTA_USUARIO = respostasUsuario[i];
            String RESPOSTA_CERTA = respostasCertas[i];
            String RESPOSTA_CERTA_ACENTUADA = respostasCertasAcentuadas[i];

            if(!RESPOSTA_USUARIO.equalsIgnoreCase(RESPOSTA_CERTA) && !RESPOSTA_USUARIO.equalsIgnoreCase(RESPOSTA_CERTA_ACENTUADA) ){
                mudarCorEditTexts(Color.RED, i);
            }
        }

        // DESABILITAR RADIO BUTTONS
        desabilitarEditTexts(listRespostasUsuario);
    }

    @Override
    public void avancarQuestao() {
        limparEditTexts(listRespostasUsuario);
        habilitarEditTexts(listRespostasUsuario);
        super.avancarQuestao();
    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    protected void tentarNovamente(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        super.tentarNovamente();

        for(int i = 0; i <= (listRespostasUsuario.size() - 1); i++) {
            mudarCorEditTexts(Color.BLACK, i);
        }

        // LIMPANDO AS EDIT TEXTS VERMELHAS
        limparEditTextsComRespostaErrada(respostasCertas, respostasCertasAcentuadas);

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarEditTexts(listRespostasUsuario);
    }

    private void escondeTeclado() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    protected void adicionarClickListenerEditText(final int tamanhoMaximo, final EditText editText1, final EditText editText2) {

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editText2 == null && editText1.length() == tamanhoMaximo
                        && !editText1.getText().toString().equals("")) {
                    try {
                        escondeTeclado();
                    } catch(NullPointerException e) {
                        e.printStackTrace();
                    }

                } else if(editText1.getText().length() == tamanhoMaximo) {
                    if(editText2 != null
                            && !editText1.getText().toString().equals("") ) {
                        editText2.requestFocus();
                    }

                }
            }
        });
    }

    private void verificaQuantidadePalavras(int quantidadePalavras) {

        EditText palavra1;
        EditText palavra2;
        EditText palavra3;
        EditText palavra4;
        EditText palavra5;
        EditText palavra6;
        EditText palavra7;
        EditText palavra8;
        EditText palavra9;
        EditText palavra10;
        EditText palavra11;
        EditText palavra12;
        EditText palavra13;
        EditText palavra14;
        EditText palavra15;
        EditText palavra16;
        EditText palavra17;
        EditText palavra18;
        EditText palavra19;
        EditText palavra20;


        switch(quantidadePalavras) {
            case 1:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                this.totalDeRespostas = new EditText[] {palavra1};
                break;
            case 2:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2};
                break;
            case 3:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3};
                break;
            case 4:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4};
                break;
            case 5:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5};
                break;
            case 6:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6};
                break;
            case 7:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7};
                break;
            case 8:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8};
                break;
            case 9:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9};
                break;
            case 10:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10};
                break;
            case 11:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11};
                break;
            case 12:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12};
                break;
            case 13:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13};
                break;
            case 14:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                palavra14 = (EditText) rootView.findViewById(R.id.palavra14);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14};
                break;
            case 15:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                palavra14 = (EditText) rootView.findViewById(R.id.palavra14);
                palavra15 = (EditText) rootView.findViewById(R.id.palavra15);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15};
                break;
            case 16:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                palavra14 = (EditText) rootView.findViewById(R.id.palavra14);
                palavra15 = (EditText) rootView.findViewById(R.id.palavra15);
                palavra16 = (EditText) rootView.findViewById(R.id.palavra16);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16};
                break;
            case 17:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                palavra14 = (EditText) rootView.findViewById(R.id.palavra14);
                palavra15 = (EditText) rootView.findViewById(R.id.palavra15);
                palavra16 = (EditText) rootView.findViewById(R.id.palavra16);
                palavra17 = (EditText) rootView.findViewById(R.id.palavra17);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17};
                break;
            case 18:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                palavra14 = (EditText) rootView.findViewById(R.id.palavra14);
                palavra15 = (EditText) rootView.findViewById(R.id.palavra15);
                palavra16 = (EditText) rootView.findViewById(R.id.palavra16);
                palavra17 = (EditText) rootView.findViewById(R.id.palavra17);
                palavra18 = (EditText) rootView.findViewById(R.id.palavra18);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17, palavra18};
                break;
            case 19:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                palavra14 = (EditText) rootView.findViewById(R.id.palavra14);
                palavra15 = (EditText) rootView.findViewById(R.id.palavra15);
                palavra16 = (EditText) rootView.findViewById(R.id.palavra16);
                palavra17 = (EditText) rootView.findViewById(R.id.palavra17);
                palavra18 = (EditText) rootView.findViewById(R.id.palavra18);
                palavra19 = (EditText) rootView.findViewById(R.id.palavra19);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17, palavra18, palavra19};
                break;
            case 20:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                palavra9 = (EditText) rootView.findViewById(R.id.palavra9);
                palavra10 = (EditText) rootView.findViewById(R.id.palavra10);
                palavra11 = (EditText) rootView.findViewById(R.id.palavra11);
                palavra12 = (EditText) rootView.findViewById(R.id.palavra12);
                palavra13 = (EditText) rootView.findViewById(R.id.palavra13);
                palavra14 = (EditText) rootView.findViewById(R.id.palavra14);
                palavra15 = (EditText) rootView.findViewById(R.id.palavra15);
                palavra16 = (EditText) rootView.findViewById(R.id.palavra16);
                palavra17 = (EditText) rootView.findViewById(R.id.palavra17);
                palavra18 = (EditText) rootView.findViewById(R.id.palavra18);
                palavra19 = (EditText) rootView.findViewById(R.id.palavra19);
                palavra20 = (EditText) rootView.findViewById(R.id.palavra20);
                this.totalDeRespostas = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17, palavra18, palavra19, palavra20};
                break;
        }
    }




}