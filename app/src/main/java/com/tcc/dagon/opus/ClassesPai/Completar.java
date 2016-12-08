package com.tcc.dagon.opus.ClassesPai;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Toast;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.PulseAnimation;
import java.util.List;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class Completar extends Fragment {

    // OBJETO BANCO
    protected GerenciadorBanco DB_PROGRESSO = null;

    // BOTÕES DE CHECAR RESPOSTA, AVANÇAR E TENTAR DE NOVO
    protected Button btnChecar,
                     btnAvancar,
                     btnTentarNovamente;

    // REFERENCIA DO VIEWPAGER DO CONTAINER
    protected ViewPager mViewPager;

    // REFERENCIA DO LAYOUT DO CONTAINER
    protected LinearLayout tabStrip;

    // REFERENCIA DO TABLAYOUT DO CONTAINER
    protected TabLayout mTabLayout;

    protected View rootView;

    // SONS DO APP
    protected MediaPlayer somRespostaCerta = null;
    protected MediaPlayer somRespostaErrada = null;

    // VARIÁVEL QUE VÊ SE O LIMITE DA EDIT TEXT FOI ATINGIDO
    protected boolean isReached = false;

    protected List<EditText> listaEditTexts;
    protected EditText linhasCompletar[];

    protected String respostasUsuario[];
    protected String respostasCertas[];

    
    // IMAGENS DE CERTO E ERRADO
    protected ImageView imgRespostaCerta, imgRespostaErrada;

    protected int moduloAtual, etapaAtual;

    protected GerenciadorSharedPreferences preferencias;

    protected int[] tamanhoPalavras;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    protected void instanciaObjetos() {
        // BANCO DE DADOS
        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(getActivity());
        }

        // SONS DO APP
        if (somRespostaCerta == null || somRespostaErrada == null) {
            somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
            somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);
        }

        preferencias = new GerenciadorSharedPreferences(getActivity());
    }

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

    protected void accessViews() {
        // PEGANDO OS BOTÕES AVANÇAR, CHECAR E TENTAR DE NOVO
        btnChecar          = (Button) rootView.findViewById(R.id.btnChecarResposta);
        btnAvancar         = (Button) rootView.findViewById(R.id.btnAvancarQuestao);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);

        // IMAGENS CERTO E ERRADO
        imgRespostaCerta  = (ImageView) rootView.findViewById(R.id.imgRespostaCerta);
        imgRespostaErrada = (ImageView) rootView.findViewById(R.id.imgRespostaErrada);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        if(imgRespostaCerta != null) {
            imgRespostaCerta.setVisibility(View.GONE);
        }

        if(imgRespostaErrada != null) {
            imgRespostaErrada.setVisibility(View.GONE);
        }


        // SUMINDO COM OS BOTÕES DESNECESSARIOS NO INICIO DA ATIVIDADE
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);

        linhasCompletar = new EditText[listaEditTexts.size()];
        respostasUsuario = new String[listaEditTexts.size()];

        tamanhoPalavras = new int[listaEditTexts.size()];



    }


    protected void listeners() {

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
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                resetarEditTexts();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                resetarEditTexts();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                resetarEditTexts();
            }
        });


    }

    // MÉTODO QUE CHECA RESPOSTAS DO COMPLETAR
    protected void checarRespostasCompletar(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        int i;
        int qtdRespostasCorretas = 0;
        // ESSE LOOP ENCHE OS VETORES COM OS DADOS A SEREM CHECADOS
        for(i = 0; i <= (listaEditTexts.size() - 1); i++) {
            // PASSANDO AS STRINGS QUE ESTÃO NAS EDIT TEXTS PARA UM VETOR PARA PODER COMPARAR
            linhasCompletar[i] = listaEditTexts.get(i);
            respostasUsuario[i] = linhasCompletar[i].getText().toString();
        }

        // DE 0 ATÉ O TAMANHO DA LISTA QUE SERÁ DEFINIDA NA CLASSE FILHA, INCREMENTE
        for(i = 0; i <= (listaEditTexts.size() - 1); i++) {
            // SE TIVER CAMPO EM BRANCO, EMITA UM AVISO
            if(respostasUsuario[i].isEmpty() )
            {
                    Toast.makeText(getActivity(), "Há campos em branco!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        // ESSE LOOP VERIFICA A QUANTIDADE DE RESPOSTAS CORRETAS
        for (i = 0; i <= (listaEditTexts.size()) - 1; i++) {
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

    // MÉTODO QUE DESABILITA OS RADIO BUTTONS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    protected void desabilitarEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            linhasCompletar[i] = listaEditTexts.get(i);
            linhasCompletar[i].setInputType(InputType.TYPE_NULL);
            linhasCompletar[i].setFocusable(false);
            linhasCompletar[i].setFocusableInTouchMode(false);
        }
    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    protected void habilitarEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            linhasCompletar[i] = listaEditTexts.get(i);
            linhasCompletar[i].setInputType(InputType.TYPE_CLASS_TEXT);
            linhasCompletar[i].setFocusable(true);
            linhasCompletar[i].setFocusableInTouchMode(true);
        }
    }

    // MÉTODO DE LIMPAR AS EDIT TEXTS
    protected void limparEditTexts(List<EditText> listaEditTexts) {
        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            linhasCompletar[i] = listaEditTexts.get(i);
            linhasCompletar[i].setText("");
        }
    }

    protected void resetarEditTexts() {
        limparEditTexts(listaEditTexts);
        habilitarEditTexts(listaEditTexts);

        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            linhasCompletar[i] = listaEditTexts.get(i);
            linhasCompletar[i].setTextColor(Color.BLACK);
        }


    }

    protected void limparEditTextsVermelhas(String[] respostasCertas, String[] respostasCertasAcentuadas) {

        for(int i = 0; i <= (listaEditTexts.size() - 1); i++) {
            respostasUsuario[i] = linhasCompletar[i].getText().toString();
            if(!respostasUsuario[i].equalsIgnoreCase(respostasCertas[i]) && !respostasUsuario[i].equalsIgnoreCase(respostasCertasAcentuadas[i]) ){
                linhasCompletar[i] = listaEditTexts.get(i);
                linhasCompletar[i].setText("");
            }
        }
    }

    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    protected void respostaCerta() {
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

    protected void concluirCompletar() {
        if(mViewPager.getCurrentItem() ==  (mTabLayout.getTabCount() -1 )  ) {
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
        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 1).setIcon(R.drawable.icon_licao);
        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 2).setIcon(R.drawable.icon_pergunta);

        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setEnabled(true);

        tabStrip.getChildAt(mViewPager.getCurrentItem() + 2).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 2).setEnabled(true);

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


        // LIMPANDO AS EDIT TEXTS VERMELHAS
        limparEditTextsVermelhas(respostasCertas, respostasCertasAcentuadas);

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarEditTexts(listaEditTexts);

    }

    protected void moveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    protected void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    protected void escondeTeclado() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    protected void invocaTeclado() {
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


}