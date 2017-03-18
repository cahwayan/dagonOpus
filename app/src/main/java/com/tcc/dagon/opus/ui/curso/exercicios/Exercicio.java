package com.tcc.dagon.opus.ui.curso.exercicios;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.curso.container.RefreshListener;
import com.tcc.dagon.opus.ui.etapas.subclasses.EtapasModulo0;
import com.tcc.dagon.opus.utils.OnOffClickListener;
import com.tcc.dagon.opus.utils.ViewController;

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
    protected RefreshListener refreshListener;

    /* VIEWS */
    protected View rootView;
    protected TextView pergunta;
    protected ImageView imgRespostaCerta;
    protected ImageView imgRespostaErrada;
    protected Button btnChecarResposta;
    protected Button btnAvancarQuestao;
    protected Button btnTentarNovamente;
    protected TextView txtPontos;

    /* VARIÁVEIS */
    private int qtdErros;
    private int pontuacao;
    protected int questaoAtual;


    public int getPontuacao() {
        return this.pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public int getQuestaoAtual() {
        return questaoAtual;
    }

    public void setQuestaoAtual(int questaoAtual) {
        this.questaoAtual = questaoAtual;
    }

    public int getQtdErros() {
        return qtdErros;
    }

    protected void setQtdErros(int qtdErros) {
        this.qtdErros = qtdErros;
    }

    /* ABSTRATOS */
    protected abstract void findViewsExclusivas(View rootView);
    protected abstract void validarRespostaUsuario();
    protected abstract void calcularPontuacao();

    /* CICLO DE VIDA */

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            refreshListener = (RefreshListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " precisa implementar onRefreshListener");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected void accessViews(View rootView) {
        txtPontos =          (TextView) rootView.findViewById(R.id.txtPontos);
        imgRespostaCerta  =  (ImageView) rootView.findViewById(R.id.imgRespostaCerta);
        imgRespostaErrada =  (ImageView) rootView.findViewById(R.id.imgRespostaErrada);
        btnChecarResposta =  (Button) rootView.findViewById(R.id.btnChecar);
        btnAvancarQuestao =  (Button) rootView.findViewById(R.id.btnAvancar);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);

        pergunta = (TextView)    rootView.findViewById(R.id.pergunta);

        String questao = refreshListener.fetchQuestionFromDatabase(questaoAtual);
        Log.d("QUESTÃO: ", String.valueOf(questaoAtual) + " " + questao);
        pergunta.setText(questao);
    }

    protected void setListeners() {

        setOnClickListeners();
        setTabListeners();

    }

    private void setOnClickListeners() {

        OnOffClickListener listenerChecarResposta = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                validarRespostaUsuario();
            }
        };
        btnChecarResposta.setOnClickListener(listenerChecarResposta);

        OnOffClickListener listenerConcluirQuestao = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                concluirQuestao();
            }
        };
        btnAvancarQuestao.setOnClickListener(listenerConcluirQuestao);
        imgRespostaCerta.setOnClickListener(listenerConcluirQuestao);

        OnOffClickListener listenerTentarNovamente = new OnOffClickListener() {
            @Override
            public void onOneClick(View v) {
                tentarNovamente();
            }
        };
        btnTentarNovamente.setOnClickListener(listenerTentarNovamente);
        imgRespostaErrada.setOnClickListener(listenerTentarNovamente);

    }

    private void setTabListeners() {
        // LISTENER QUE VERIFICA QUANDO A ABA SELECIONADA É MUDADA, SELECIONADA ou RE-SELECIONADA
        final ViewPager pager = refreshListener.getViewPager();
        final TabLayout tabLayout = refreshListener.getTabLayout();

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tentarNovamente();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    protected void respostaCerta() {
        refreshListener.tocarSomRespostaCerta();
        ViewController.setVisible(imgRespostaCerta);
        ViewController.initPulseAnimation(imgRespostaCerta);
        ViewController.setInvisible(btnChecarResposta);
        ViewController.setVisible(btnAvancarQuestao);
        calcularPontuacao();
    }

    /*
      * Ao errar a resposta, o usuário recebe um som de resposta errada, o botão de verificar resposta
       * é escondido, e o botão de tentar novamente é mostrado na tela para que ele possa fazer uma
       * nova tentativa. Também é incrementada a quantidade de erros para medir a pontuação mais tarde.
    */
    protected void respostaErrada() {
        qtdErros++;
        refreshListener.tocarSomRespostaErrada();
        ViewController.setVisible(imgRespostaErrada);
        ViewController.initPulseAnimation(imgRespostaErrada);
        ViewController.setInvisible(btnChecarResposta);
        ViewController.setVisible(btnTentarNovamente);
    }

    /*
      * Ao clicar no botão tentar novamente, que aparece após uma resposta errada, o usuário volta
      * ao estado inicial: o botão tentar novamente é escondido, as imagens são escondidas, e o
      * botão de checar resposta é mostrado novamente. O texto de pontuação é resetado
    */
    protected void tentarNovamente() {

        resetUIExercicio();

        /*setQtdErros(0);
        zerarPontuacao(); TODO: REMOVER*/
    }

    protected void concluirQuestao() {
        // Se a lição atual for igual à contagem total de lições, o usuário está na lição final da etapa
        if(refreshListener.isLastExercise()) {
            questaoFinal();
        } else {
            avancarLicao();
        }
    }

    protected void avancarLicao() {

        resetUIExercicio();
        zerarPontuacao();
        zerarErros();

        if(!usuarioJaCompletouEssaLicaoAntes()) {
            avancarProgresso();
            atualizarPontuacao();
        }

        // Ir para o próximo fragmento
        refreshListener.moveNext();
    }

    protected void questaoFinal() {

        if(!usuarioJaCompletouEssaEtapaAntes()) {
            avancarProgressoEtapa();
            atualizarPontuacao();
        }

        this.getActivity().finish();
    }

    /*
     * Cada exercício terá que sobrescrever esse método, chamar o super e adicionar as peculiaridades
      * necessárias para resetar cada tipo de exercício
    */
    protected void resetUIExercicio() {
        // Configurando a visibilidade dos botões
        ViewController.setInvisible(btnTentarNovamente);
        ViewController.setInvisible(btnAvancarQuestao);
        ViewController.setVisible(btnChecarResposta);

        // Escondendo as imagens de certo e errado
        ViewController.setInvisible(imgRespostaCerta);
        ViewController.setInvisible(imgRespostaErrada);

        // Resetando a TextView que mostra a pontuação
        txtPontos.setText(getActivity().getResources().getString(R.string.pontuacaoDefault));
    }

    protected void avancarProgresso() {

        if(!usuarioJaCompletouEssaLicaoAntes()) {
            refreshListener.setProgressoLicao(/* aumento em */ 2);
            atualizarPontuacao();
        }

        refreshListener.refreshUI();

    }

    protected void zerarPontuacao() {
        this.pontuacao = 0;
    }

    protected void zerarErros() {
        this.qtdErros = 0;
    }

    protected boolean usuarioJaCompletouEssaLicaoAntes() {
        int licaoAtual = refreshListener.getViewPager().getCurrentItem();
        int progressoSalvo = refreshListener.getProgressoLicao();

        return progressoSalvo > licaoAtual;
    }

    protected boolean usuarioJaCompletouEsseModuloAntes() {
        int progressoSalvo = refreshListener.getProgressoModulo();

        return progressoSalvo > refreshListener.getModuloAtual();
    }

    protected boolean usuarioJaCompletouEssaEtapaAntes() {
        int progressoSalvo = refreshListener.getProgressoEtapa();

        return progressoSalvo > refreshListener.getEtapaAtual();
    }

    protected void avancarProgressoModulo() {
        refreshListener.avancarProgressoModulo(/*AVANCAR EM */ 1);
    }

    protected void avancarProgressoEtapa() {
        Log.d("PROG ANTES: ", String.valueOf(refreshListener.getProgressoEtapa()));
        refreshListener.setProgressoEtapa(/*AUMENTO EM */ 1);
        Log.d("PROG DEPOIS: ", String.valueOf(refreshListener.getProgressoEtapa()));
    }

    protected void liberarPrimeiraEtapaDoProximoModulo() {
        refreshListener.setProgressoEtapa(refreshListener.getModuloAtual() + 1, /*AUMENTO EM */ 1);
    }

    protected void atualizarPontuacao() {
        refreshListener.somarPontos(this.pontuacao);
    }

    protected Class retornarTelaEtapas(int numeroModulo) {
        switch(numeroModulo) {
            case 0: return EtapasModulo0.class;
            /*case 2: return EtapasModulo2Activity.class;
            case 3: return EtapasModulo3Activity.class;
            case 4: return EtapasModulo4Activity.class;
            case 5: return EtapasModulo5Activity.class;
            case 6: return EtapasModulo6Activity.class;*/
            default: return null;
        }
    }



}
