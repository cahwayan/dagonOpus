package com.tcc.dagon.opus.ClassesPai;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class Questao extends Fragment {

    // RADIO BUTTONS DAS ALTERNATIVAS
    protected RadioButton alternativa1,
                          alternativa2,
                          alternativa3,
                          alternativa4;


    // OBJETO BANCO PARA VERIFICAR AS RESPOSTAS
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

    // RADIOGROUP
    protected RadioGroup containerRadioButtons;

    // SONS DO APP
    protected MediaPlayer somRespostaCerta = null;
    protected MediaPlayer somRespostaErrada = null;

    // OBJETO DE JANELA DE ALERTA
    protected NovaJanelaAlerta alertaOpcaoVazia = null;

    // IMAGENS CERTO E ERRADO
    protected ImageView imgRespostaCerta, imgRespostaErrada;

    protected int moduloAtual, etapaAtual, questaoAtual;



    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    protected void instanciaObjetos() {
        // INSTANCIANDO OS OBJETOS DA CLASSE PAI
        // OBJETO BANCO DE DADOS
        if(DB_PROGRESSO == null) {
            this.DB_PROGRESSO = new GerenciadorBanco(getActivity());
        }

        // OBJETO JANELA DE ALERTA
        if(alertaOpcaoVazia == null) {
            alertaOpcaoVazia = new NovaJanelaAlerta(getActivity());
        }

        // CARREGANDO OS SONS DAS RESPOSTAS
        if(somRespostaCerta == null || somRespostaErrada == null) {
            somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
            somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);
        }
    }


    @Override
    public void onPause() {
        desmarcarRadioButtons();
        btnChecar.setVisibility(View.VISIBLE);
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);
        super.onPause();
    }

    protected void accessViews() {

        // IMAGENS CERTO E ERRADO
        imgRespostaCerta  = (ImageView) rootView.findViewById(R.id.imgRespostaCerta);
        imgRespostaErrada = (ImageView) rootView.findViewById(R.id.imgRespostaErrada);

        // PEGANDO OS BOTÕES AVANÇAR, CHECAR E TENTAR DE NOVO
        btnChecar = (Button) rootView.findViewById(R.id.btnChecarResposta);
        btnAvancar = (Button) rootView.findViewById(R.id.btnAvancarQuestao);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);

        // SUMINDO COM OS BOTÕES DESNECESSARIOS NO INICIO DA ATIVIDADE
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

    }

    protected void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SE A ALTERNATIVA 1 ESTIVER SELECIONADA AO CLICAR
                if(alternativa1.isChecked()) {
                    // VERIFICAR CORRESPONDENCIA DA ALTERNATIVA 1 NO BANCO
                    // SE ESTIVER CERTA .................
                    if(verificaAlternativa(1) == 1) {
                        respostaCerta(); // MÉTODO QUE MOSTRA O BOTÃO DE AVANÇAR
                        // SE NÃO ESTIVER CERTA............
                    } else {
                        respostaErrada(); // MÉTODO QUE MOSTRA O BOTÃO TENTAR NOVAMENTE
                    }
                } else if(alternativa2.isChecked()) {
                    if(verificaAlternativa(2) == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else if (alternativa3.isChecked()){
                    if(verificaAlternativa(3) == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else if (alternativa4.isChecked()) {
                    if(verificaAlternativa(4) == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else { // SE TODAS ESTIVEREM VAZIAS, MOSTRAR ALERTA
                    alertaOpcaoVazia();
                }
            }
        });

        // BOTAO AVANÇAR LICAO
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirQuestao();
            }
        });

        // BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente();
            }
        });

        // LISTENER QUE VERIFICA QUANDO A ABA SELECIONADA É MUDADA, SELECIONADA ou RE-SELECIONADA
        // ELE É IMPORTANTE PARA ESVAZIAR OS RADIO BUTTONS AO SAIR DA ATIVIDADE ENQUANTO ESTÃO CHECADOS
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                tentarNovamente();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tentarNovamente();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tentarNovamente();
            }
        });

    }

    // MÉTODO QUE DESABILITA OS RADIO BUTTONS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    protected void desabilitarRadioButtons() {
        alternativa1.setClickable(false);
        alternativa2.setClickable(false);
        alternativa3.setClickable(false);
        alternativa4.setClickable(false);
    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    protected void habilitarRadioButtons() {
        alternativa1.setClickable(true);
        alternativa2.setClickable(true);
        alternativa3.setClickable(true);
        alternativa4.setClickable(true);
    }

    // MÉTODO DE DESMARCAR OS RADIO BUTTONS
    protected void desmarcarRadioButtons() {
        containerRadioButtons.clearCheck();
    }

    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    protected void respostaCerta() {
        // TOCAR SOM DE RESPOSTA CERTA
        somRespostaCerta.start();

        // ANIMAÇÃO RESPOSTA CERTA
        imgRespostaCerta.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaCerta)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();

        //TRAZENDO O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.VISIBLE);
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    protected void respostaErrada() {
        // TOCAR SOM DE RESPOSTA ERRADA
        somRespostaErrada.start();

        // ANIMAÇÃO RESPOSTA ERRADA
        imgRespostaErrada.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaErrada)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();


        // SUMINDO COM O BOTAO CHECAR
        btnChecar.setVisibility(View.GONE);

        // TRAZENDO BOTÃO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.VISIBLE);

        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();
    }


    // MÉTODO DE AVANÇAR LIÇÃO CASO A RESPOSTA ESTEJA CERTA E TALS
    protected void concluirQuestao() {
        if(mViewPager.getCurrentItem() ==  (mTabLayout.getTabCount() - 1 )  ) {
            questaoFinal();
        } else {
            avancarQuestao();
        }
    }

    protected void questaoFinal() {
        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO
        if(this.DB_PROGRESSO.verificaProgressoEtapa(etapaAtual) <= etapaAtual) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual, (etapaAtual + 1) );
        }
        this.getActivity().finish();
    }

    protected void avancarQuestao() {
        // SUMINDO COM O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTÃO CHECAR NOVAMENTE
        btnChecar.setVisibility(View.VISIBLE);

        // REABILITANDO OS RADIO BUTTONS
        habilitarRadioButtons();

        // TROCANDO O ICONE DO CADEADO

        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 1).setIcon(R.drawable.icon_licao);
        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 2).setIcon(R.drawable.icon_pergunta);


        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setEnabled(true);

        tabStrip.getChildAt(mViewPager.getCurrentItem() + 2).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 2).setEnabled(true);

        //DESMARCANDO RADIO BUTTON
        desmarcarRadioButtons();

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
        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // DESMARCANDO OS RADIO BUTTONS
        desmarcarRadioButtons();

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarRadioButtons();

    }

    // JANELA DE ALERTA CASO AS OPÇÕES ESTEJAM VAZIAS
    protected void alertaOpcaoVazia() {
        alertaOpcaoVazia.alertDialogBloqueadoLicao("Selecione uma opção", "Selecione uma resposta!");
    }

    protected void moveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    protected void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    // MÉTODOS DE VERIFICAÇÃO DAS ALTERNATIVAS

    protected int verificaAlternativa(int alternativa) {
        switch(alternativa) {
            case 1: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 1);
            case 2: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 2);
            case 3: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 3);
            case 4: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 4);
        }
        return 0;
    }

}
