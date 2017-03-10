package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.telas.fragments.container.ContainerLicoes_Activity_;
import com.tcc.dagon.opus.ui.etapas.subclasses.EtapasModulo0;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by Caíque on 07/01/2017.
 * ESTA CLASSE É A RAÍZ DE TODOS AS CLASSES DE EXERCÍCIO DO APLICATIVO
 */


public abstract class Exercicio extends ConteudoWrapper {

    /*
     * Através dessa interface, o fragmento pode se comunicar com o container para ler informações
     * a respeito do progresso atual, mandar mensagens para o container atualizar a interface dele,
     * também pode mandar mensagens para que o container altere o progresso atual de acordo com o desempenho
     * do usuário através do aplicativo.
    */
    private RefreshListener refreshListener;

    /* OBJETOS */
    private GerenciadorBanco DB_PROGRESSO;
    private MediaPlayer somRespostaCerta;
    private MediaPlayer somRespostaErrada;

    /* VIEWS */
    private ImageView imgRespostaCerta;
    private ImageView imgRespostaErrada;
    private Button btnChecarResposta;
    private Button btnAvancarQuestao;
    private Button btnTentarNovamente;
    private TextView txtPontos;
    private ViewPager view_pager;
    private TabLayout tab_layout;

    /* VARIÁVEIS */
    private int qtdErros;
    private int pontuacao;
    private int moduloAtual, etapaAtual;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            refreshListener = (RefreshListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " precisa implementar onRefreshListener");
        }

    }

    public Button getBtnAvancarQuestao() {
        return btnAvancarQuestao;
    }

    public Button getBtnChecarResposta() {
        return btnChecarResposta;
    }


    public Button getBtnTentarNovamente() {
        return btnTentarNovamente;
    }

    public GerenciadorBanco getDB_PROGRESSO() {
        return DB_PROGRESSO;
    }

    public int getEtapaAtual() {
        return etapaAtual;
    }

    public void setEtapaAtual(int etapaAtual) {
        this.etapaAtual = etapaAtual;
    }

    public ImageView getImgRespostaCerta() {
        return imgRespostaCerta;
    }

    public ImageView getImgRespostaErrada() {
        return imgRespostaErrada;
    }

    public int getModuloAtual() {
        return moduloAtual;
    }

    public void setModuloAtual(int moduloAtual) {
        this.moduloAtual = moduloAtual;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getQtdErros() {
        return qtdErros;
    }

    public MediaPlayer getSomRespostaErrada() {
        return somRespostaErrada;
    }

    public void setTab_layout(TabLayout tab_layout) {
        this.tab_layout = tab_layout;
    }

    public TextView getTxtPontos() {
        return txtPontos;
    }

    public ViewPager getView_pager() {
        return view_pager;
    }

    public void setView_pager(ViewPager view_pager) {
        this.view_pager = view_pager;
    }

    /* ABSTRATOS */
    protected abstract void validarRespostaUsuario();
    protected abstract void calcularPontuacao();

    /* CICLO DE VIDA */

    @Override
    public void onPause() {
        releaseSons();
        super.onPause();
    }

    @Override
    public void onResume() {
        instanciaSons();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        releaseSons();
        super.onDestroy();
    }

    protected void instanciaObjetos() {
        if(this.DB_PROGRESSO == null) {
            this.DB_PROGRESSO = new GerenciadorBanco(getActivity());
        }

        instanciaSons();
    }

    private void instanciaSons() {
        if(this.somRespostaCerta == null || this.somRespostaErrada == null) {
            this.somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
            this.somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);
        }
    }

    private void releaseSons() {
        if(this.somRespostaCerta != null) {
            this.somRespostaCerta.release();
            this.somRespostaCerta = null;
        }

        if(this.somRespostaErrada != null) {
            this.somRespostaErrada.release();
            this.somRespostaErrada = null;
        }

    }

    protected void accessViews(View rootView) {
        view_pager = ((ContainerLicoes_Activity_)this.getActivity()).getPager();
        tab_layout = ((ContainerLicoes_Activity_)this.getActivity()).getTab_layout();
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

    protected void respostaCerta() {
        playSound(somRespostaCerta);
        calcularPontuacao();
        initAnimationAnswer(this.imgRespostaCerta);
        unhideView(btnAvancarQuestao);
    }

    protected void respostaErrada() {
        this.qtdErros++;
        this.playSound(somRespostaErrada);
        this.initAnimationAnswer(imgRespostaErrada);
        this.hideUnnecessaryView(btnChecarResposta);
        this.unhideView(btnTentarNovamente);
    }

    protected void tentarNovamente() {
        hideUnnecessaryView(btnTentarNovamente);
        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);
        unhideView(btnChecarResposta);

        txtPontos.setText("Pontos: 0");
        setQtdErros(0);
        zerarPontuacao();
    }

    protected void concluirQuestao() {
        final int contagemTotalLicoes = tab_layout.getTabCount() - 1;
        final int licaoAtual = view_pager.getCurrentItem();

        // Se a lição atual for igual à contagem total de lições, o usuário está na lição final da etapa
        if(licaoAtual ==  contagemTotalLicoes) {
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

        /*changeUpperBarIcon(ICONE_LICAO, R.drawable.icon_licao);
        changeUpperBarIcon(ICONE_EXERCICIO, R.drawable.icon_pergunta);

        setUpperBarIconClickable(ICONE_LICAO);
        setUpperBarIconClickable(ICONE_EXERCICIO);*/

        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);

        zerarPontuacao();
        this.setQtdErros(0);

        avancarProgresso();

        // TROCANDO O FRAGMENTO
        moveNext(view_pager);
    }

    protected void questaoFinal() {
        if(!usuarioJaCompletouEssaEtapaAntes()) {
            atualizarProgressoEtapa();
            //atualizarPontuacao();
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

    // Método genérico que controla a função de tocar som
    protected void playSound(MediaPlayer sound) {
        // Se o som não estiver desativado
        if(!refreshListener.isSomDesativado()) {
            // Tocar som
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
        /*tab_layout.getTabAt(view_pager.getCurrentItem() + passo).setIcon(drawableID);*/
    }

    protected void setUpperBarIconClickable(int passo) {
        /*this.tabStrip.getChildAt(view_pager.getCurrentItem() + passo).setClickable(true);
        this.tabStrip.getChildAt(view_pager.getCurrentItem() + passo).setEnabled(true);*/
    }

    protected void avancarProgresso() {
        if(!usuarioJaCompletouEssaLicaoAntes()) {
            refreshListener.avancarProgressoLicao(/* aumento em */2);
            //atualizarPontuacao();
        }

        refreshListener.refreshUI();
    }

    protected void moveNext(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
    }

    protected void movePrevious(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }

    protected void setQtdErros(int qtdErros) {
        this.qtdErros = qtdErros;
    }

    protected void zerarPontuacao() {
        this.pontuacao = 0;
    }

    protected Class retornarTelaEtapas(int numeroModulo) {
        switch(numeroModulo) {
            case 1: return EtapasModulo0.class;
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
        int progressoSalvo = refreshListener.getProgressoLicao();

        return progressoSalvo > licaoAtual;
    }

    protected boolean usuarioJaCompletouEsseModuloAntes() {
        int progressoSalvo = this.DB_PROGRESSO.verificaProgressoModulo();

        return progressoSalvo > this.moduloAtual;
    }

    protected boolean usuarioJaCompletouEssaEtapaAntes() {
        int progressoSalvo = refreshListener.getProgressoEtapa();
        Log.d("PROG ETAPA:^", String.valueOf(progressoSalvo));
        Log.d("NUM ETAPA ATUAL:^", String.valueOf(etapaAtual));
        return progressoSalvo > refreshListener.getEtapaAtual();
    }

    protected void liberarProximoModulo() {
        this.DB_PROGRESSO.atualizaProgressoModulo(this.moduloAtual + 1);
    }

    protected void atualizarProgressoEtapa() {
        refreshListener.avancarProgressoEtapa(/*AUMENTO EM */ 1);
    }

    protected void liberarPrimeiraEtapaDoProximoModulo() {
       // refreshListener.avancarProgressoEtapa(/*AUMENTO EM */ 2);
    }



    protected void atualizarProgressoLicao(int aumento) {
        refreshListener.avancarProgressoLicao(aumento);
    }

    protected void atualizarPontuacao() {
        DB_PROGRESSO.atualizarPontuacao(this.moduloAtual, this.pontuacao);
    }



}
