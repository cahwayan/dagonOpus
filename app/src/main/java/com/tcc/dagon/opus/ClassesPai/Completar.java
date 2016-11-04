package com.tcc.dagon.opus.ClassesPai;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.PulseAnimation;

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

    // EDIT TEXTS DO COMPLETAR
    protected EditText linha1Palavra1, linha1Palavra2, linha1Palavra3, linha1Palavra4,
                       linha2Palavra1, linha2Palavra2, linha2Palavra3, linha2Palavra4,
                       linha3Palavra1, linha3Palavra2, linha3Palavra3, linha3Palavra4,
                       linha4Palavra1, linha4Palavra2, linha4Palavra3, linha4Palavra4;

    protected String  sLinha1Palavra1, sLinha1Palavra2, sLinha1Palavra3, sLinha1Palavra4,
                      sLinha2Palavra1, sLinha2Palavra2, sLinha2Palavra3, sLinha2Palavra4,
                      sLinha3Palavra1, sLinha3Palavra2, sLinha3Palavra3, sLinha3Palavra4,
                      sLinha4Palavra1, sLinha4Palavra2, sLinha4Palavra3, sLinha4Palavra4;

    protected String respostaLinha1Palavra1Acentuada, respostaLinha1Palavra2Acentuada, respostaLinha1Palavra3Acentuada, respostaLinha1Palavra4Acentuada,
                     respostaLinha2Palavra1Acentuada, respostaLinha2Palavra2Acentuada, respostaLinha2Palavra3Acentuada, respostaLinha2Palavra4Acentuada,
                     respostaLinha3Palavra1Acentuada, respostaLinha3Palavra2Acentuada, respostaLinha3Palavra3Acentuada, respostaLinha3Palavra4Acentuada,
                     respostaLinha4Palavra1Acentuada, respostaLinha4Palavra2Acentuada, respostaLinha4Palavra3Acentuada, respostaLinha4Palavra4Acentuada;

    protected String    respostaLinha1Palavra1, respostaLinha1Palavra2, respostaLinha1Palavra3, respostaLinha1Palavra4,
                        respostaLinha2Palavra1, respostaLinha2Palavra2, respostaLinha2Palavra3, respostaLinha2Palavra4,
                        respostaLinha3Palavra1, respostaLinha3Palavra2, respostaLinha3Palavra3, respostaLinha3Palavra4,
                        respostaLinha4Palavra1, respostaLinha4Palavra2, respostaLinha4Palavra3, respostaLinha4Palavra4;
    
    // IMAGENS DE CERTO E ERRADO
    protected ImageView imgRespostaCerta, imgRespostaErrada;

    protected int moduloAtual, etapaAtual;


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
    }

    @Override
    public void onPause() {
        super.onPause();
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
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // SUMINDO COM OS BOTÕES DESNECESSARIOS NO INICIO DA ATIVIDADE
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);
    }


    protected void listeners() {

        // BOTAO AVANÇAR LICAO
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirCompletar();
            }
        });

        btnTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente();
            }
        });
    }

    // MÉTODO QUE DESABILITA OS RADIO BUTTONS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    protected void desabilitarEditTexts() {

    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    protected void habilitarEditTexts() {

    }

    // MÉTODO DE LIMPAR AS EDIT TEXTS
    protected void limparEditTexts() {

    }

    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    protected void respostaCerta() {

    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    protected void respostaErrada() {

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
        if(this.DB_PROGRESSO.verificaProgressoEtapa(etapaAtual) <= etapaAtual) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual, (etapaAtual + 1) );
        }
        this.getActivity().finish();
    }

    protected void avancarCompletar() {
        limparEditTexts();
        habilitarEditTexts();
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
    protected void tentarNovamente() {

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
        linha2Palavra1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}