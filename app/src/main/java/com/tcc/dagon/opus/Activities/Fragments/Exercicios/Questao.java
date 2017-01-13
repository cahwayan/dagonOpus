package com.tcc.dagon.opus.Activities.Fragments.Exercicios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class Questao extends Exercicio {

    private int questaoAtual;

    protected final int ALTERNATIVA1 = 1;
    protected final int ALTERNATIVA2 = 2;
    protected final int ALTERNATIVA3 = 3;
    protected final int ALTERNATIVA4 = 4;

    /* COMPONENTES VISUAIS */
    private RadioGroup radioGroupQuestao;
    private RadioButton alternativa1,
                        alternativa2,
                        alternativa3,
                        alternativa4;

    protected TextView pergunta;
    protected View rootView;
    private NovaJanelaAlerta alertaOpcaoVazia;

    public static Questao novaQuestao(int moduloAtual, int etapaAtual, int questaoAtual) {
        Questao questao = new Questao();
        Bundle args = new Bundle();
        args.putInt("moduloAtual", moduloAtual);
        args.putInt("etapaAtual", etapaAtual);
        args.putInt("questaoAtual", questaoAtual);
        questao.setArguments(args);
        return questao;
    }

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.getConstructorArgs();
        super.onCreateView(inflater, container, savedInstanceState);
        this.setRootView(inflater, container, savedInstanceState);
        this.instanciaObjetos();
        this.accessViews(this.rootView);
        this.accessRadioButtons(this.rootView);
        this.fetchQuestionFromDatabase();
        this.listeners();
        return this.rootView;
    }

    protected void setRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.activity_questao, container, false);
    }

    protected void instanciaObjetos() {
        super.instanciaObjetos();
        // OBJETO JANELA DE ALERTA
        if(alertaOpcaoVazia == null) {
            alertaOpcaoVazia = new NovaJanelaAlerta(getActivity());
        }
    }

    @Override
    public void getConstructorArgs() {
        super.getConstructorArgs();
        this.questaoAtual = getArguments().getInt("questaoAtual", 0);
    }

    @Override
    public void onPause() {
        uncheckAllButtons();
        super.onPause();
    }

    @Override
    protected void accessViews(View rootView) {
        super.accessViews(rootView);

        pergunta = (TextView)    rootView.findViewById(R.id.pergunta);

    }

    protected void accessRadioButtons(View rootView) {
        radioGroupQuestao = (RadioGroup)  rootView.findViewById(R.id.radioGroupQuestao);
        alternativa1      = (RadioButton) rootView.findViewById(R.id.alternativa1);
        alternativa2      = (RadioButton) rootView.findViewById(R.id.alternativa2);
        alternativa3      = (RadioButton) rootView.findViewById(R.id.alternativa3);
        alternativa4      = (RadioButton) rootView.findViewById(R.id.alternativa4);
    }

    @Override
    protected void validarRespostaUsuario() {
        final int ALTERNATIVA1 = 0x7f0f0146;
        final int ALTERNATIVA2 = 0x7f0f0147;
        final int ALTERNATIVA3 = 0x7f0f0148;
        final int ALTERNATIVA4 = 0x7f0f0149;
        final int OPCAO_VAZIA = -1;

        int alternativaMarcada = radioGroupQuestao.getCheckedRadioButtonId();
        int resposta = 0;
        final int RESPOSTA_CERTA  = 1;

        switch (alternativaMarcada) {
            case ALTERNATIVA1: resposta = verificaResposta(this.ALTERNATIVA1);
                break;
            case ALTERNATIVA2: resposta = verificaResposta(this.ALTERNATIVA2);
                break;
            case ALTERNATIVA3: resposta = verificaResposta(this.ALTERNATIVA3);
                break;
            case ALTERNATIVA4: resposta = verificaResposta(this.ALTERNATIVA4);
                break;
            case OPCAO_VAZIA: alertaOpcaoVazia();
                return;
        }

        if(resposta == RESPOSTA_CERTA) {
            respostaCerta();
        } else {
            respostaErrada();
        }
    }

    @Override
    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    protected void respostaCerta() {
        super.respostaCerta();

        // DESABILITAR RADIO BUTTONS
        setAllButtonsUnclickable();
    }

    @Override
    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    protected void respostaErrada() {
        super.respostaErrada();

        // DESABILITAR RADIO BUTTONS
        setAllButtonsUnclickable();
    }

    @Override
    // MÉTODO QUE CONFIGURA A TELA PARA O USUÁRIO RESPONDER NOVAMENTE
    protected void tentarNovamente() {
        super.tentarNovamente();

        // DESMARCANDO OS RADIO BUTTONS
        uncheckAllButtons();

        // HABILITANDO RADIO BUTTONS DE NOVO
        setAllButtonsClickable();
    }

    @Override
    // MÉTODO QUE AVANÇA A TELA
    protected void avancarQuestao() {

        // REABILITANDO OS RADIO BUTTONS
        setAllButtonsClickable();

        //DESMARCANDO RADIO BUTTON
        uncheckAllButtons();

        super.avancarQuestao();
    }

    protected void setAllButtonsUnclickable() {
        alternativa1.setClickable(false);
        alternativa2.setClickable(false);
        alternativa3.setClickable(false);
        alternativa4.setClickable(false);
    }

    protected void setAllButtonsClickable() {
        alternativa1.setClickable(true);
        alternativa2.setClickable(true);
        alternativa3.setClickable(true);
        alternativa4.setClickable(true);
    }

    protected void uncheckAllButtons() {
        radioGroupQuestao.clearCheck();
    }

    protected void fetchQuestionFromDatabase() {
        this.pergunta.setText(DB_PROGRESSO.puxarPergunta(moduloAtual, etapaAtual, questaoAtual));

        this.alternativa1.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, this.ALTERNATIVA1));
        this.alternativa2.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, this.ALTERNATIVA2));
        this.alternativa3.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, this.ALTERNATIVA3));
        this.alternativa4.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, this.ALTERNATIVA4));
    }

    protected int verificaResposta(int alternativa) {

        final int RESPOSTA_ERRADA = 0;

        switch(alternativa) {
            case 1:
                return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA1);
            case 2:
                return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA2);
            case 3:
                return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA3);
            case 4:
                return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA4);
            default:
                return RESPOSTA_ERRADA;
        }
    }

    protected void alertaOpcaoVazia() {
        alertaOpcaoVazia.alertDialogBloqueadoLicao("Selecione uma opção", "Selecione uma resposta!");
    }

    /* MÉTODOS ESPECIAIS */

    public void setModuloAtual(int moduloAtual) {
        this.moduloAtual = moduloAtual;
    }

    public void setEtapaAtual(int etapaAtual) {
        this.etapaAtual = etapaAtual;
    }

    public void setQuestaoAtual(int questaoAtual) {
        this.questaoAtual = questaoAtual;
    }

    public int getModuloAtual() {
        return moduloAtual;
    }

    public int getEtapaAtual() {
        return etapaAtual;
    }

    public int getQuestaoAtual() {
        return questaoAtual;
    }

    public GerenciadorBanco getDB_PROGRESSO() {
        return DB_PROGRESSO;
    }



}
