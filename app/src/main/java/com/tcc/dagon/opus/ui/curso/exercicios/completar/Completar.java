package com.tcc.dagon.opus.ui.curso.exercicios.completar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.curso.exercicios.Exercicio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cahwayan on 04/11/2016.
 * EXERCÍCIO DE COMPLETAR DO CURSO
 */
/**/

public class Completar extends Exercicio {

    /* VARIÁVEIS */
    // GUARDA O ID DO LAYOUT QUE SERÁ USADO NA INSTÂNCIA
    private int layoutID;

    /* LISTAS, COLEÇÕES */
    // GUARDA AS EDIT TEXTS EM UMA LISTA DE OBJETOS PARA PODER TRABALHAR COM ELAS DE MANEIRA DINÂMICA
    private List<EditText> listEditTexts;

    private int quantidadePalavras;

    /* VETOR QUE PEGA AS RESPOSTAS DIGITADAS PELO USUÁRIO. ESSE VETOR PEGA OS VALORES DA LISTA DE EDIT TEXTS*/
    private String   respostasUsuario[];

    /* VETORES QUE GUARDAM AS RESPOSTAS CERTAS PARA DETERMINADO EXERCÍCIO */
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
    public static Completar novoCompletar(int layoutID, int questaoAtual, int quantidadePalavras, String[] respostasCertas, String[] respostasCertasAcentuadas) {
        Completar completar = new Completar();
        completar.setLayoutID(layoutID);
        completar.setQuestaoAtual(questaoAtual);
        completar.setQuantidadePalavras(quantidadePalavras);
        completar.setRespostasCertas(respostasCertas);
        completar.setRespostasCertasAcentuadas(respostasCertasAcentuadas);
        return completar;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void setQuantidadePalavras(int quantidadePalavras) {
        this.quantidadePalavras = quantidadePalavras;
    }

    public void setRespostasCertas(String[] respostasCertas) {
        this.respostasCertas = respostasCertas;
    }

    public void setRespostasCertasAcentuadas(String[] respostasCertasAcentuadas) {
        this.respostasCertasAcentuadas = respostasCertasAcentuadas;
    }

    /* CICLO DE VIDA DO APP */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inflateRootView(this.layoutID, inflater, container, savedInstanceState);
        accessViews(this.rootView);
        findViewsExclusivas(this.rootView);
        initListasRespostasUsuario();
        setListeners();
        resetUIExercicio();
        return this.rootView;
    }

    protected void inflateRootView(int layoutID, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(layoutID, container, false);
    }

    protected void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }

    protected void initListasRespostasUsuario() {

        this.listEditTexts = new ArrayList<>();

        this.listEditTexts.addAll(Arrays.asList(findViewsExclusivas()));

        // VETORES QUE TOMARÃO O TAMANHO DA LISTA DE EDIT TEXTS
        respostasUsuario  = new String[listEditTexts.size()];
        lengthRespostas   = getLengthRespostas();

    }

    protected int[] getLengthRespostas() {

        int[] lengthRespostas = new int[listEditTexts.size()];

        for(int i = 0; i < listEditTexts.size(); i++) {
            // O vetor lengthRespostas guarda o tamanho das strings que guardam as respostas certas.
            // as respostas certas são passadas por parâmetro no construtor, na ordem em que devem
            // ser preenchidas
            lengthRespostas[i] = respostasCertas[i].length();
        }

        return lengthRespostas;

    }

    @Override
    public void setListeners() {

        super.setListeners();

        /*LISTENERS DAS EDIT TEXTS PARA AVANÇAREM QUANDO FOR PREENCHIDA A PALAVRA*/
        // de 0 até o tamanho do vetor que guarda o tamanho de cada palavra
        for(int i = 0; i < listEditTexts.size(); i++) {
            // pegue a coleção que está na lista, jogue no vetor
            // adicione um click listener que tenha:
            // o tamanho da palavra na posição i,
            // na edit text da posição i
            // e sete o focus para a próxima edit text

            try {

                adicionarClickListenerEditText(lengthRespostas[i], listEditTexts.get(i), listEditTexts.get(i + 1));

            } catch(IndexOutOfBoundsException erroLista) {
                erroLista.printStackTrace();
                adicionarClickListenerEditText(lengthRespostas[i], listEditTexts.get(i), null);
            }

        }
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

    /* VALIDA AS RESPOSTAS DO USUÁRIO
    *
    * */
    @Override
    public void validarRespostaUsuario() {

        int qtdRespostasCorretas = 0;

        // ESSE LOOP ENCHE OS VETORES COM OS DADOS A SEREM CHECADOS
        for(int i = 0; i < listEditTexts.size(); i++) {

            // PASSANDO AS STRINGS QUE ESTÃO NAS EDIT TEXTS PARA UM VETOR PARA PODER COMPARAR
            respostasUsuario[i] = listEditTexts.get(i).getText().toString();

            // SE A RESPOSTA DO USUARIO FORNECIDA NA POSIÇÃO I FOR IGUAL A RESPOSTA CERTA DEFINIDA NA CLASSE FILHA, OU IGUAL À VERSÃO ACENTUADA,
            // INCREMENTAR 1 NA QUANTIDADE DE RESPOSTAS CORRETAS
            String respostaUsuario = respostasUsuario[i];
            String respostaCerta   = respostasCertas[i];
            String respostaCertaAcentuada = respostasCertasAcentuadas[i];

            if(respostaUsuario.equalsIgnoreCase(respostaCerta)
                    || respostaUsuario.equalsIgnoreCase(respostaCertaAcentuada)) {
                qtdRespostasCorretas++;
            }
        }

        // SE A QUANTIDADE DE RESPOSTAS CORRETAS FOR IGUAL AO TAMANHO DO VETOR DE EDIT TEXTS, SIGNIFICA QUE O USUÁRIO ACERTOU TODAS AS PALAVRAS
        int totalQuantidadeRespostas = listEditTexts.size();

        if(qtdRespostasCorretas == totalQuantidadeRespostas) {
            respostaCerta();
        } else {
            // SE NÃO FOR IGUAL, ELE ERROU, CHAMAR MÉTODO DE RESPOSTA ERRADA
            respostaErrada();
        }
    }

    // MÉTODO QUE DESABILITA AS EDIT TEXTS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    private void desabilitarEditTexts() {
        for(EditText resposta : listEditTexts) {
            resposta.setInputType(InputType.TYPE_NULL);
            resposta.setFocusable(false);
            resposta.setFocusableInTouchMode(false);
        }
    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    protected void habilitarEditTexts() {
        for(EditText resposta : listEditTexts) {
            resposta.setInputType(InputType.TYPE_CLASS_TEXT);
            resposta.setFocusable(true);
            resposta.setFocusableInTouchMode(true);
        }
    }

    // MÉTODO DE LIMPAR AS EDIT TEXTS
    protected void limparEditTexts() {
        for(EditText resposta : listEditTexts) {
            resposta.setText("");
        }
    }

    private void limparEditTextsComRespostaErrada() {
        for(int i = 0; i < listEditTexts.size(); i++) {

            respostasUsuario[i] = listEditTexts.get(i).getText().toString();

            if(!respostasUsuario[i].equalsIgnoreCase(respostasCertas[i])
                    &&
                    !respostasUsuario[i].equalsIgnoreCase(respostasCertasAcentuadas[i]) ){

                listEditTexts.get(i).setText("");
            }
        }
    }

    @Override
    public void calcularPontuacao() {

        int pontos = 0;
        pontos += 1000;

        switch (getQtdErros()) {
            case 0: pontos += 500;
                break;
            case 1: pontos -= 50;
                break;
            case 2: pontos -= 100;
                break;
            case 3: pontos -= 150;
                break;
            case 4: pontos -= 200;
                break;
            case 5: pontos -= 250;
                break;
            case 6: pontos -= 300;
                break;
            case 7: pontos -= 350;
                break;
            case 8: pontos -= 400;
                break;
            case 9: pontos -= 450;
                break;
            case 10: pontos -= 500;
                break;
            default: pontos = 0;
                break;
        }

        setPontuacao(pontos);

        String stringPontuacao = "Pontos: " + String.valueOf(pontos);

        txtPontos.setText(stringPontuacao);
    }

    @Override
    public void respostaCerta() {
        super.respostaCerta();

        // BLOQUEAR EDIT TEXTS
        desabilitarEditTexts();
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    public void respostaErrada() {
        super.respostaErrada();
        changeColorWrongAnswers(Color.RED);

        // DESABILITAR RADIO BUTTONS
        desabilitarEditTexts();
    }

    @Override
    public void avancarLicao() {
        limparEditTexts();
        habilitarEditTexts();
        super.avancarLicao();
    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    @Override
    public void tentarNovamente() {
        super.tentarNovamente();

        changeColorAllEditTexts(Color.BLACK);

        // LIMPANDO AS EDIT TEXTS VERMELHAS
        limparEditTextsComRespostaErrada();

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarEditTexts();
    }

    private void escondeTeclado() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    private void changeColorAllEditTexts(int cor) {
        for(EditText resposta : listEditTexts) {
            resposta.setTextColor(cor);
        }
    }

    private void changeColorWrongAnswers(int cor) {
        for(int i = 0; i < listEditTexts.size(); i++) {

            String respostaUsuario = respostasUsuario[i];
            String respostaCerta = respostasCertas[i];
            String respostaCertaAcentuada = respostasCertasAcentuadas[i];

            if(!respostaUsuario.equalsIgnoreCase(respostaCerta)
                    && !respostaUsuario.equalsIgnoreCase(respostaCertaAcentuada) ){
                listEditTexts.get(i).setTextColor(cor);
            }
        }
    }

    @Override
    protected void findViewsExclusivas(View rootView) {

    }


    protected EditText[] findViewsExclusivas() {
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
                return new EditText[] {palavra1};
            case 2:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                return new EditText[] {palavra1, palavra2};
            case 3:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                return new EditText[] {palavra1, palavra2, palavra3};

            case 4:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                return new EditText[] {palavra1, palavra2, palavra3, palavra4};

            case 5:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5};

            case 6:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6};

            case 7:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7};
            case 8:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                palavra7 = (EditText) rootView.findViewById(R.id.palavra7);
                palavra8 = (EditText) rootView.findViewById(R.id.palavra8);
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17, palavra18};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17, palavra18, palavra19};

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
                return new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17, palavra18, palavra19, palavra20};
            default:
                return null;
        }


    }

}