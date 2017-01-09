package com.tcc.dagon.opus.ClassesPai;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.PulseAnimation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by cahwayan on 04/11/2016.
 * EXERCÍCIO DE COMPLETAR DO CURSO
 */


public class Completar extends Fragment {

    /* OBJETOS */
    protected GerenciadorBanco DB_PROGRESSO = null;
    private MediaPlayer somRespostaCerta = null;
    private MediaPlayer somRespostaErrada = null;
    protected GerenciadorSharedPreferences preferencias = null;
    protected View rootView;

    /* VIEWS */
    protected Button btnChecar;
    protected Button btnAvancar;
    protected Button btnTentarNovamente;

    protected ViewPager view_pager;
    protected TabLayout tab_layout;

    protected ImageView imgRespostaCerta;
    protected ImageView imgRespostaErrada;

    protected LinearLayout tabStrip;
    private TextView txtPontos;

    private int qtdErros;
    private int pontuacao;

    /* VARIÁVEIS */
    // GUARDA O ID DO LAYOUT QUE SERÁ USADO NA INSTÂNCIA
    protected int layoutID;
    protected int moduloAtual, etapaAtual;

    /* LISTAS, COLEÇÕES */
    // GUARDA AS EDIT TEXTS EM UMA LISTA DE OBJETOS PARA PODER TRABALHAR COM ELAS DE MANEIRA DINÂMICA
    protected List<EditText> listaEditTexts;

    /* VETORES */
    /* VETOR QUE GUARDA AS EDIT TEXTS. ELA É NECESSÁRIA APENAS PARA PASSAR AS EDIT TEXTS PARA UMA LISTA
    *  QUE É MELHOR DE SER TRABALHADA*/
    private EditText linhasCompletar[];

    /* VETOR QUE PEGA AS RESPOSTAS DIGITADAS PELO USUÁRIO. ESSE VETOR PEGA OS VALORES DA LISTA DE EDIT TEXTS*/
    private String   respostasUsuario[];

    /* VETORES QUE GUARDAM AS RESPOSTAS CERTAS PARA DETERMINADO EXERCÍCIO*/
    private String   respostasCertas[];
    private String   respostasCertasAcentuadas[];

    /* VETOR QUE GUARDA O TAMANHO DAS PALAVRAS DAS RESPOSTAS CERTAS
    *  ESSE VETOR SERVE PARA ADICIONAR LISTENERS DE MANEIRA PROGRAMÁTICA DE ACORDO COM O
    *  TAMANHO DA RESPOSTA. O LISTENER EM QUESTÃO É O TEXT WATCHER, E FAZ PULAR DE UMA
    *  EDIT TEXT PARA OUTRA ASSIM QUE O TAMANHO DA PALAVRA CORRETA FOR ATINGIDO*/
    private int      tamanhoPalavras[];

    /* MÉTODO ESTÁTICO DE INSTÂNCIA. COMO FRAGMENTOS NÃO POSSUEM SUPORTE DECENTE PARA O USO DE MÉTODOS CONSTRUTORES
    * (NA VERDADE NÃO É RECOMENDADO NEM SOBRESCREVER O CONSTRUTOR DE UM FRAGMENT)
    * CRIAMOS UM MÉTODO ESTÁTICO, QUE PODE SER ACESSADO DE QUALQUER LUGAR, QUE SERVE PARA INSTANCIAR A CLASSE COMO UM MÉTODO CONSTRUTOR.
    * ESSE MÉTODO RECEBE OS PARÂMETROS, E PASSA PARA O ONCREATE ATRAVÉS DE UM BUNDLE. LÁ ENTÃO, PODEMOS PEGAR ESSES VALORES E TRABALHAR COM ELES.
    * É IMPORTANTE SABER QUE AS MODIFICAÇÕES FEITAS NESSE MÉTODO, SÃO REALIZADAS ANTES DO MÉTODO ONCREATE SER EXECUTADO, POR ISSO, SERVE PERFEITAMENTE
    * COMO UM CONSTRUTOR*/
    public static Completar newInstance(int layoutID, int moduloAtual, int etapaAtual, int quantidadePalavras, String[] respostasCertas, String[] respostasCertasAcentuadas) {
        Completar completar = new Completar();
        completar.setContentView(layoutID);
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
        super.onCreateView(inflater, container, savedInstanceState);
        /* PEGANDO OS ARGUMENTOS DO MÉTODO DE INSTÂNCIAÇÃO*/
        this.moduloAtual               = getArguments().getInt("moduloAtual", 0);
        this.etapaAtual                = getArguments().getInt("etapaAtual" , 0);
        int quantidadePalavras         = getArguments().getInt("quantidadePalavras", 0);
        this.respostasCertas           = getArguments().getStringArray("respostasCertas");
        this.respostasCertasAcentuadas = getArguments().getStringArray("respostasCertasAcentuadas");
        this.layoutID                  = getArguments().getInt("layoutID", (R.layout.fragment_modulo1_etapa1_licao1));

        /* MÉTODO QUE INSTANCIA OS OBJETOS PRINCIPAIS DA CLASSE*/
        this.instanciaObjetos();

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        this.rootView = inflater.inflate( setContentView(this.layoutID) , container, false);

        // INSTANCIANDO A LISTA
        this.listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        this.verificaQuantidadePalavras(quantidadePalavras);

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        this.listaEditTexts.addAll(Arrays.asList(this.linhasCompletar));

        this.accessViews();

        this.listeners();

        return this.rootView;
    }

    private void instanciaObjetos() {
        // BANCO DE DADOS
        if(this.DB_PROGRESSO == null) {
            this.DB_PROGRESSO = new GerenciadorBanco(getActivity());
        }

        // SONS DO APP
        if (this.somRespostaCerta == null || this.somRespostaErrada == null) {
            this.somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
            this.somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);
        }

        if(this.preferencias == null) {
            this.preferencias = new GerenciadorSharedPreferences(getActivity());
        }

    }



    protected void accessViews() {

        // IMAGENS CERTO E ERRADO
        imgRespostaCerta  = (ImageView) rootView.findViewById(R.id.imgRespostaCerta);
        imgRespostaErrada = (ImageView) rootView.findViewById(R.id.imgRespostaErrada);

        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        view_pager = ((ContainerEtapa)getActivity()).getPager();
        tabStrip   = ((ContainerEtapa)getActivity()).getTabStrip();
        tab_layout = ((ContainerEtapa)getActivity()).getmTabLayout();

        txtPontos = (TextView) rootView.findViewById(R.id.txtPontos);

        // PEGANDO OS BOTÕES AVANÇAR, CHECAR E TENTAR DE NOVO
        btnChecar          = (Button) rootView.findViewById(R.id.btnChecar);
        btnAvancar         = (Button) rootView.findViewById(R.id.btnAvancar);
        btnTentarNovamente = (Button) rootView.findViewById(R.id.btnTentarNovamente);

        // COMPONENTES ESCONDIDOS
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);

        // VETORES QUE TOMARÃO O TAMANHO DA LISTA DE EDIT TEXTS
        linhasCompletar = new EditText[listaEditTexts.size()];
        respostasUsuario = new String[listaEditTexts.size()];
        tamanhoPalavras = new int[listaEditTexts.size()];

        // ESSE LOOP PEGA A RESPOSTA NO INDICE I E ATRIBUI AO VETOR
        // QUE GUARDA O TAMANHO DESSA PALAVRA NO VETOR DE TAMANHO
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            tamanhoPalavras[i] = respostasCertas[i].length();
        }
    }

    private void listeners() {

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

        // BOTAO AVANÇAR LICAO
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirCompletar();
            }
        });


        /*LISTENERS DAS EDIT TEXTS PARA AVANÇAREM QUANDO FOR PREENCHIDA A PALAVRA*/
        // de 0 até o tamanho do vetor que guarda o tamanho de cada palavra
        for(int i = 0; i <= (listaEditTexts.size() - 1) ; i++) {
            // pegue a coleção que está na lista, jogue no vetor
            // adicione um click listener que tenha:
            // o tamanho da palavra na posição i,
            // na edit text da posição i
            // e sete o focus para a próxima edit text
            linhasCompletar[i] = listaEditTexts.get(i);

            try {

                adicionarClickListenerEditText(tamanhoPalavras[i], linhasCompletar[i], listaEditTexts.get(i + 1));

            } catch(IndexOutOfBoundsException erroLista) {
                erroLista.printStackTrace();
                adicionarClickListenerEditText(tamanhoPalavras[i], linhasCompletar[i], null);
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
                resetarEditTexts();
                pontuacao = 0;
                qtdErros = 0;
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                resetarEditTexts();
                pontuacao = 0;
                qtdErros = 0;
            }
        });

        this.imgRespostaCerta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                concluirCompletar();
            }
        });

        this.imgRespostaErrada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tentarNovamente(respostasCertas, respostasCertasAcentuadas);
            }
        });


    }

    // MÉTODO QUE CHECA RESPOSTAS DO COMPLETAR
    private void checarRespostasCompletar(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        int i;
        int qtdRespostasCorretas = 0;
        // ESSE LOOP ENCHE OS VETORES COM OS DADOS A SEREM CHECADOS
        for(i = 0; i <= (listaEditTexts.size() - 1); i++) {
            // PASSANDO AS STRINGS QUE ESTÃO NAS EDIT TEXTS PARA UM VETOR PARA PODER COMPARAR
            linhasCompletar[i] = listaEditTexts.get(i);
            respostasUsuario[i] = linhasCompletar[i].getText().toString();

            // SE A RESPOSTA DO USUARIO FORNECIDA NA POSIÇÃO I FOR IGUAL A RESPOSTA CERTA DEFINIDA NA CLASSE FILHA, OU IGUAL À VERSÃO ACENTUADA,
            // INCREMENTAR 1 NA QUANTIDADE DE RESPOSTAS CORRETAS
            if(respostasUsuario[i].equalsIgnoreCase(respostasCertas[i]) || respostasUsuario[i].equalsIgnoreCase(respostasCertasAcentuadas[i])) {
                qtdRespostasCorretas++;
            }
        }

        // SE A QUANTIDADE DE RESPOSTAS CORRETAS FOR IGUAL AO TAMANHO DO VETOR DE EDIT TEXTS, SIGNIFICA QUE O USUÁRIO ACERTOU TODAS AS PALAVRAS
        if(qtdRespostasCorretas == linhasCompletar.length) {
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
            this.linhasCompletar[i] = listaEditTexts.get(i);
            this.linhasCompletar[i].setInputType(InputType.TYPE_NULL);
            this.linhasCompletar[i].setFocusable(false);
            this.linhasCompletar[i].setFocusableInTouchMode(false);
        }
    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    protected void habilitarEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            this.linhasCompletar[i] = listaEditTexts.get(i);
            this.linhasCompletar[i].setInputType(InputType.TYPE_CLASS_TEXT);
            this.linhasCompletar[i].setFocusable(true);
            this.linhasCompletar[i].setFocusableInTouchMode(true);
        }
    }

    // MÉTODO DE LIMPAR AS EDIT TEXTS
    protected void limparEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            this.linhasCompletar[i] = listaEditTexts.get(i);
            this.linhasCompletar[i].setText("");
        }
    }

    private void resetarEditTexts() {
        this.limparEditTexts(this.listaEditTexts);
        this.habilitarEditTexts(this.listaEditTexts);

        for(int i = 0; i <= (this.listaEditTexts.size() - 1); i++) {
            this.linhasCompletar[i] = this.listaEditTexts.get(i);
            this.linhasCompletar[i].setTextColor(Color.BLACK);
        }


    }

    private void limparEditTextsVermelhas(String[] respostasCertas, String[] respostasCertasAcentuadas) {

        for(int i = 0; i <= (this.listaEditTexts.size() - 1); i++) {
            this.respostasUsuario[i] = this.linhasCompletar[i].getText().toString();
            if(!this.respostasUsuario[i].equalsIgnoreCase(respostasCertas[i]) && !this.respostasUsuario[i].equalsIgnoreCase(respostasCertasAcentuadas[i]) ){
                this.linhasCompletar[i] = this.listaEditTexts.get(i);
                this.linhasCompletar[i].setText("");
            }
        }
    }

    private void setPontuacao() {
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

    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    private void respostaCerta() {

        setPontuacao();
        Toast.makeText(getActivity(), String.valueOf(DB_PROGRESSO.verificarPontuacao(moduloAtual)), Toast.LENGTH_LONG).show();

        // TOCAR SOM DE RESPOSTA CERTA
        if(!preferencias.lerFlagBoolean(NomePreferencia.desativarSons)) {
            somRespostaCerta.start();
        }

        // ANIMAÇÃO RESPOSTA CERTA
        imgRespostaCerta.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaCerta)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        // DESABILITAR RADIO BUTTONS
        desabilitarEditTexts(listaEditTexts);

        //TRAZENDO O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.VISIBLE);
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    protected void respostaErrada(String[] respostasCertas, String[] respostasCertasAcentuadas) {

        this.qtdErros++;

        // TOCAR SOM DE RESPOSTA ERRADA
        if(!preferencias.lerFlagBoolean(NomePreferencia.desativarSons)) {
            somRespostaErrada.start();
        }


        // ANIMAÇÃO RESPOSTA ERRADA
        imgRespostaErrada.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaErrada)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            if(!respostasUsuario[i].equalsIgnoreCase(respostasCertas[i]) && !respostasUsuario[i].equalsIgnoreCase(respostasCertasAcentuadas[i]) ){
                linhasCompletar[i] = listaEditTexts.get(i);
                linhasCompletar[i].setTextColor(Color.RED);
            }
        }

        // DESABILITAR RADIO BUTTONS
        desabilitarEditTexts(listaEditTexts);

        // SUMINDO COM O BOTAO CHECAR
        btnChecar.setVisibility(View.GONE);

        // TRAZENDO BOTÃO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.VISIBLE);
    }

    private void concluirCompletar() {
        if(view_pager.getCurrentItem() ==  (tab_layout.getTabCount() -1 )  ) {
            completarFinal();
        } else {
            avancarCompletar();
        }
    }

    protected void completarFinal() {
        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO
        if(this.DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) <= etapaAtual) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual, (etapaAtual + 1) );
            this.DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);
        }
        this.getActivity().finish();
    }

    protected void avancarCompletar() {

        limparEditTexts(listaEditTexts);
        habilitarEditTexts(listaEditTexts);

        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // TROCANDO O ICONE DO CADEADO
        tab_layout.getTabAt(view_pager.getCurrentItem() + 1).setIcon(R.drawable.icon_licao);
        tab_layout.getTabAt(view_pager.getCurrentItem() + 2).setIcon(R.drawable.icon_pergunta);

        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(view_pager.getCurrentItem() + 1).setClickable(true);
        tabStrip.getChildAt(view_pager.getCurrentItem() + 1).setEnabled(true);

        tabStrip.getChildAt(view_pager.getCurrentItem() + 2).setClickable(true);
        tabStrip.getChildAt(view_pager.getCurrentItem() + 2).setEnabled(true);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        if(DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual) <= view_pager.getCurrentItem()) {
            this.DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, (view_pager.getCurrentItem() + 1) );
            this.DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);
        }

        // TROCANDO O FRAGMENTO
        moveNext(view_pager);

    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    protected void tentarNovamente(String[] respostasCertas, String[] respostasCertasAcentuadas) {

        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            linhasCompletar[i] = listaEditTexts.get(i);
            linhasCompletar[i].setTextColor(Color.BLACK);
        }

        txtPontos.setText("Pontos: 0");
        this.pontuacao = 0;

        // LIMPANDO AS EDIT TEXTS VERMELHAS
        limparEditTextsVermelhas(respostasCertas, respostasCertasAcentuadas);

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarEditTexts(listaEditTexts);

    }

    protected void moveNext(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
    }

    protected void movePrevious(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }

    private void escondeTeclado() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    private void invocaTeclado() {
        //linha2Palavra1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                if(editText2 == null && editText1.length() == tamanhoMaximo & !editText1.getText().toString().equals("")) {
                    try {
                        escondeTeclado();
                    } catch(NullPointerException e) {
                        e.printStackTrace();
                    }

                } else if(editText1.getText().length() == tamanhoMaximo) {
                    if(editText2 != null && !editText1.getText().toString().equals("") ) {
                        editText2.requestFocus();
                    }

                }
            }
        });
    }

    /* MÉTODOS ESPECIAIS */
    /* MÉTODO QUE SETA O LAYOUT QUE SERÁ UTILIZADO NA INSTÂNCIA DO FRAGMENTO*/
    int setContentView(int layoutID) {
        return this.layoutID = layoutID;
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
                this.linhasCompletar = new EditText[] {palavra1};
                break;
            case 2:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                this.linhasCompletar = new EditText[] {palavra1, palavra2};
                break;
            case 3:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3};
                break;
            case 4:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4};
                break;
            case 5:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5};
                break;
            case 6:
                palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
                palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
                palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
                palavra4 = (EditText) rootView.findViewById(R.id.palavra4);
                palavra5 = (EditText) rootView.findViewById(R.id.palavra5);
                palavra6 = (EditText) rootView.findViewById(R.id.palavra6);
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
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
                this.linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5,
                        palavra6, palavra7, palavra8, palavra9, palavra10,
                        palavra11, palavra12, palavra13, palavra14, palavra15,
                        palavra16, palavra17, palavra18, palavra19, palavra20};
                break;
        }
    }



}