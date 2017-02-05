package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.telas.fragments.container.ContainerEtapa;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.telas.etapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by Felipe on 07/01/2017.
 * ESTA CLASSE É A RAÍZ DE TODOS AS CLASSES DE EXERCÍCIO DO APLICATIVO
 */


public abstract class CExercicio extends Fragment{

    /* OBJETOS */
    protected GerenciadorBanco             DB_PROGRESSO = null;
    protected GerenciadorSharedPreferences preferencias = null;
    protected MediaPlayer                  somRespostaCerta = null;
    protected MediaPlayer                  somRespostaErrada = null;

    /* VIEWS */
    protected ImageView imgRespostaCerta;
    protected ImageView imgRespostaErrada;
    protected Button btnChecarResposta;
    protected Button btnAvancarQuestao;
    protected Button btnTentarNovamente;
    protected ViewPager view_pager;
    protected TabLayout tab_layout;
    protected LinearLayout tabStrip;
    protected TextView txtPontos;

    /* VARIÁVEIS */
    protected int qtdErros;
    protected int pontuacao;
    protected int moduloAtual, etapaAtual;

    /* ABSTRATOS */
    public abstract void validarRespostaUsuario();
    public abstract void setPontuacao();

    /* CICLO DE VIDA */

    @Override
    public void onPause() {
//        this.btnChecarResposta.setVisibility(View.VISIBLE);
//        this.btnAvancarQuestao.setVisibility(View.GONE);
//        this.btnTentarNovamente.setVisibility(View.GONE);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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

    protected void instanciaObjetos() {
        if(this.DB_PROGRESSO == null) {
            this.DB_PROGRESSO = new GerenciadorBanco(getActivity());
        }

        if(this.somRespostaCerta == null || this.somRespostaErrada == null) {
            this.somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
            this.somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);
        }

        if(this.preferencias == null) {
            this.preferencias = new GerenciadorSharedPreferences(getActivity());
        }
    }

    protected void getConstructorArgs() {
        this.moduloAtual  = getArguments().getInt("moduloAtual", 0);
        this.etapaAtual   = getArguments().getInt("etapaAtual", 0);
    }

    protected void accessViews(View rootView) {
        view_pager = ((ContainerEtapa)this.getActivity()).getPager();
        tabStrip   = ((ContainerEtapa)this.getActivity()).getTabStrip();
        tab_layout = ((ContainerEtapa)this.getActivity()).getmTabLayout();
        txtPontos =          (TextView) rootView.findViewById(R.id.txtPontos);
        imgRespostaCerta  =  (ImageView) rootView.findViewById(R.id.imgRespostaCerta);
        imgRespostaErrada =  (ImageView) rootView.findViewById(R.id.imgRespostaErrada);
        btnChecarResposta =  (Button) rootView.findViewById(R.id.btnChecar);
        btnAvancarQuestao =  (Button) rootView.findViewById(R.id.btnAvancar);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);

        hideUnnecessaryView(btnAvancarQuestao);
        hideUnnecessaryView(btnTentarNovamente);
        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);
    }

    protected void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecarResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarRespostaUsuario();
            }
        });

        // BOTAO AVANÇAR LICAO
        btnAvancarQuestao.setOnClickListener(new View.OnClickListener() {
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
        view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
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

        imgRespostaCerta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                concluirQuestao();
            }
        });

        imgRespostaErrada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tentarNovamente();
            }
        });
    }

    public void respostaCerta() {
        playSound(somRespostaCerta);
        setPontuacao();
        initAnimationAnswer(this.imgRespostaCerta);
        unhideView(btnAvancarQuestao);
    }

    public void respostaErrada() {
        this.qtdErros++;
        this.playSound(somRespostaErrada);
        this.initAnimationAnswer(imgRespostaErrada);
        this.hideUnnecessaryView(btnChecarResposta);
        this.unhideView(btnTentarNovamente);
    }

    public void tentarNovamente() {
        hideUnnecessaryView(btnTentarNovamente);
        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);
        unhideView(btnChecarResposta);

        txtPontos.setText("Pontos: 0");
        setQtdErros(0);
        zerarPontuacao();
    }

    public void concluirQuestao() {
        final int contagemTotalLicoes = tab_layout.getTabCount() - 1;
        final int licaoAtual = view_pager.getCurrentItem();

        // Se a lição atual for igual à contagem total de lições, o usuário está na lição final da etapa
        if(licaoAtual ==  contagemTotalLicoes) {
            questaoFinal();
        } else {
            avancarQuestao();
        }
    }

    public void avancarQuestao() {
        final int ICONE_LICAO = 1;
        final int ICONE_EXERCICIO = 2;

        hideUnnecessaryView(btnAvancarQuestao);
        unhideView(btnChecarResposta);

        changeUpperBarIcon(ICONE_LICAO, R.drawable.icon_licao);
        changeUpperBarIcon(ICONE_EXERCICIO, R.drawable.icon_pergunta);

        setUpperBarIconClickable(ICONE_LICAO);
        setUpperBarIconClickable(ICONE_EXERCICIO);

        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);

        zerarPontuacao();
        this.setQtdErros(0);

        atualizarProgresso();

        // TROCANDO O FRAGMENTO
        moveNext(view_pager);
    }

    protected void questaoFinal() {

        if(!usuarioJaCompletouEssaEtapaAntes()) {
            atualizarProgressoEtapa();
            atualizarPontuacao();
        }

        this.getActivity().finish();
    }

    protected void initAnimationAnswer(ImageView image) {
        // ANIMAÇÃO RESPOSTA CERTA
        image.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(image)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();
    }

    protected void playSound(MediaPlayer sound) {
        if(!preferencias.somEstaAtivado()) {
            sound.start();
        }
    }

    protected void hideUnnecessaryView(View view) {
        if(view != null) {
            view.setVisibility(View.GONE);
        }
    }

    protected void unhideView(View view) {
        if(view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    protected void changeUpperBarIcon(int passo, int drawableID) {
        tab_layout.getTabAt(view_pager.getCurrentItem() + passo).setIcon(drawableID);
    }

    protected void setUpperBarIconClickable(int passo) {
        this.tabStrip.getChildAt(view_pager.getCurrentItem() + passo).setClickable(true);
        this.tabStrip.getChildAt(view_pager.getCurrentItem() + passo).setEnabled(true);
    }

    protected void atualizarProgresso() {
        if(!usuarioJaCompletouEssaLicaoAntes()) {
            atualizarProgressoLicao(/* AUMENTO EM */ 2);
            atualizarPontuacao();
        }
    }

    public void moveNext(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
    }

    public void movePrevious(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }

    public void setQtdErros(int qtdErros) {
        this.qtdErros = qtdErros;
    }

    public void zerarPontuacao() {
        this.pontuacao = 0;
    }

    public Class retornarTelaEtapas(int numeroModulo) {
        switch(numeroModulo) {
            case 1: return EtapasModulo1Activity.class;
            /*case 2: return EtapasModulo2Activity.class;
            case 3: return EtapasModulo3Activity.class;
            case 4: return EtapasModulo4Activity.class;
            case 5: return EtapasModulo5Activity.class;
            case 6: return EtapasModulo6Activity.class;*/
            default: return null;
        }
    }

    protected boolean usuarioJaCompletouEssaLicaoAntes() {
        int licaoAtual = view_pager.getCurrentItem();
        int progressoSalvo = verificarProgressoLicao();

        return progressoSalvo > licaoAtual;
    }

    protected boolean usuarioJaCompletouEsseModuloAntes() {
        int progressoSalvo = this.DB_PROGRESSO.verificaProgressoModulo();

        return progressoSalvo > this.moduloAtual;
    }

    protected boolean usuarioJaCompletouEssaEtapaAntes() {
        int progressoSalvo = this.DB_PROGRESSO.verificaProgressoEtapa(this.moduloAtual);
        return progressoSalvo > this.etapaAtual;
    }

    protected void liberarProximoModulo() {
        this.DB_PROGRESSO.atualizaProgressoModulo(this.moduloAtual + 1);
    }

    protected void atualizarProgressoEtapa() {
        this.DB_PROGRESSO.atualizaProgressoEtapa(this.moduloAtual, this.etapaAtual + 1);
    }

    protected void liberarPrimeiraEtapaDoProximoModulo() {
        this.DB_PROGRESSO.atualizaProgressoEtapa(this.moduloAtual + 1, /* EM */ 1);
    }

    protected int verificarProgressoLicao() {
        return this.DB_PROGRESSO.verificaProgressoLicao(this.moduloAtual, this.etapaAtual);
    }

    protected void atualizarProgressoLicao(int aumento) {
        int progressoAtual = view_pager.getCurrentItem();
        this.DB_PROGRESSO.atualizaProgressoLicao(this.moduloAtual, this.etapaAtual, progressoAtual + aumento);
    }

    protected void atualizarPontuacao() {
        DB_PROGRESSO.atualizarPontuacao(this.moduloAtual, this.pontuacao);
    }



}
