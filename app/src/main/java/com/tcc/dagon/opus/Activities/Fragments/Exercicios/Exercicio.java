package com.tcc.dagon.opus.Activities.Fragments.Exercicios;

import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.Activities.AppCompatActivity.Containers.ContainerEtapa;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by Felipe on 07/01/2017.
 * ESTA CLASSE É A RAÍZ DE TODOS AS CLASSES DE EXERCÍCIO DO APLICATIVO
 */


public class Exercicio extends Fragment {

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
    private TextView txtPontos;

    /* VARIÁVEIS */
    private int qtdErros;
    protected int pontuacao;
    protected int moduloAtual, etapaAtual;



    @Override
    public void onPause() {
        this.btnChecarResposta.setVisibility(View.VISIBLE);
        this.btnAvancarQuestao.setVisibility(View.GONE);
        this.btnTentarNovamente.setVisibility(View.GONE);
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

    protected void validarRespostaUsuario() {}

    protected void respostaCerta() {
        // SETAR PONTUAÇÃO
        this.setPontuacao();

        // TOCAR SOM
        this.playSound(this.somRespostaCerta);

        // INICIAR ANIMAÇÃO
        this.initAnimationAnswer(this.imgRespostaCerta);

        // MOSTRAR BOTÃO DE AVANÇAR QUESTÃO
        this.btnAvancarQuestao.setVisibility(View.VISIBLE);
    }

    protected void respostaErrada() {
        this.qtdErros++;
        this.playSound(this.somRespostaErrada);
        this.initAnimationAnswer(this.imgRespostaErrada);
        this.hideUnnecessaryView(btnChecarResposta);
        this.unhideView(btnTentarNovamente);

    }

    protected void tentarNovamente() {
        hideUnnecessaryView(btnTentarNovamente);
        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);
        unhideView(btnChecarResposta);

        txtPontos.setText("Pontos: 0");

        this.pontuacao = 0;
    }

    protected void concluirQuestao() {
        final int CONTAGEM_TOTAL_LICOES = tab_layout.getTabCount() - 1;
        final int LICAO_ATUAL = view_pager.getCurrentItem();

        // Se a lição atual for igual à contagem total de lições, o usuário está na lição final da etapa
        if(LICAO_ATUAL ==  CONTAGEM_TOTAL_LICOES) {
            questaoFinal();
        } else {
            avancarQuestao();
        }
    }

    protected void avancarQuestao() {
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

        updateUserProgress();

        // TROCANDO O FRAGMENTO
        moveNext(view_pager);
    }

    protected void questaoFinal() {
        if(this.DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) <= etapaAtual) {
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual, (etapaAtual + 1) );
            this.DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);
        }
        this.getActivity().finish();
    }

    protected void setPontuacao() {

        this.pontuacao += 1000;

        switch (qtdErros) {
            case 0: this.pontuacao += 500;
                break;
            case 1: this.pontuacao -= 100;
                break;
            case 2: this.pontuacao -= 200;
                break;
            case 3: this.pontuacao -= 300;
                break;
            case 4: this.pontuacao -= 400;
                break;
            default: this.pontuacao = 0;
                break;
        }

        txtPontos.setText("Pontos: " + String.valueOf(pontuacao));
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
        if(!preferencias.lerFlagBoolean(GerenciadorSharedPreferences.NomePreferencia.desativarSons)) {
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

    private void changeUpperBarIcon(int passo, int drawableID) {
        tab_layout.getTabAt(view_pager.getCurrentItem() + passo).setIcon(drawableID);
    }

    private void setUpperBarIconClickable(int passo) {
        this.tabStrip.getChildAt(view_pager.getCurrentItem() + passo).setClickable(true);
        this.tabStrip.getChildAt(view_pager.getCurrentItem() + passo).setEnabled(true);
    }

    private void updateUserProgress() {
        if(DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual) <= view_pager.getCurrentItem()) {
            DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);

            // AVANÇAR O PROGRESSO EM UM
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, (view_pager.getCurrentItem() + 1) );
        }
    }

    protected void moveNext(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
    }

    protected void movePrevious(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }

}
