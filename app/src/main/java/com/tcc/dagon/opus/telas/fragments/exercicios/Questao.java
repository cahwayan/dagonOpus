package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

/**
 * Created by cahwayan on 04/11/2016.
 */ /**/

public class Questao extends Exercicio {



    private final int ALTERNATIVA0 = 0;
    private final int ALTERNATIVA1 = 1;
    private final int ALTERNATIVA2 = 2;
    private final int ALTERNATIVA3 = 3;

    /* COMPONENTES VISUAIS */
    private RadioGroup radioGroupQuestao;
    private RadioButton alternativa0,
                        alternativa1,
                        alternativa2,
                        alternativa3;

    private TextView pergunta;
    private View rootView;
    private NovaJanelaAlerta alertaOpcaoVazia;

    public int getALTERNATIVA0() {
        return ALTERNATIVA0;
    }

    public int getALTERNATIVA1() {
        return ALTERNATIVA1;
    }

    public int getALTERNATIVA2() {
        return ALTERNATIVA2;
    }

    public int getALTERNATIVA3() {
        return ALTERNATIVA3;
    }

    public TextView getPergunta() {
        return pergunta;
    }

    public void setPergunta(TextView pergunta) {
        this.pergunta = pergunta;
    }

    public int getQuestaoAtual() {
        return questaoAtual;
    }

    public void setQuestaoAtual(int questaoAtual) {
        this.questaoAtual = questaoAtual;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public static Questao novaQuestao(int moduloAtual, int etapaAtual, int questaoAtual) {
        Questao questao = new Questao();
        questao.setModuloAtual(moduloAtual);
        questao.setEtapaAtual(etapaAtual);
        questao.setQuestaoAtual(questaoAtual);
        return questao;
    }

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.inflateRootView(inflater, container, savedInstanceState);
        this.instanciaObjetos();
        this.accessViews(this.rootView);
        this.accessRadioButtons(this.rootView);
        this.setupQuestion();
        this.listeners();
        return this.rootView;
    }

    protected void inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void accessViews(View rootView) {
        super.accessViews(rootView);
        pergunta = (TextView)    rootView.findViewById(R.id.pergunta);
    }

    protected void accessRadioButtons(View rootView) {
        radioGroupQuestao = (RadioGroup)  rootView.findViewById(R.id.radioGroupQuestao);
        alternativa0 = (RadioButton) rootView.findViewById(R.id.alternativa0);
        alternativa1 = (RadioButton) rootView.findViewById(R.id.alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.alternativa3);
    }

    protected void setupQuestion() {
        String question = refreshListener.fetchQuestionFromDatabase(questaoAtual);
        String[] alternatives = refreshListener.fetchAlternativesFromDatabase(questaoAtual);

        pergunta.setText(question);

        alternativa0.setText(alternatives[ALTERNATIVA0]);
        alternativa1.setText(alternatives[ALTERNATIVA1]);
        alternativa2.setText(alternatives[ALTERNATIVA2]);
        alternativa3.setText(alternatives[ALTERNATIVA3]);

    }

    private RadioButton getCheckedButton() {
        for(int i = 0; i < radioGroupQuestao.getChildCount(); i++) {
            RadioButton checkedButton = (RadioButton) radioGroupQuestao.getChildAt(i);

            if(checkedButton.isChecked()) {
                return checkedButton;
            }
        }
        return null;
    }

    @Override
    protected void listeners() {
        super.listeners();

        getImgRespostaErrada().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirQuestao();
            }
        });
    }

    @Override
    protected void validarRespostaUsuario() {

        final int ALTERNATIVA1 = alternativa0.getId();
        final int ALTERNATIVA2 = alternativa1.getId();
        final int ALTERNATIVA3 = alternativa2.getId();
        final int ALTERNATIVA4 = alternativa3.getId();

        int alternativaMarcada = radioGroupQuestao.getCheckedRadioButtonId();
        int resposta;

        if(alternativaMarcada == ALTERNATIVA1)
        {
            resposta = verificaResposta(this.ALTERNATIVA0);
        }
        else if(alternativaMarcada == ALTERNATIVA2)
        {
            resposta = verificaResposta(this.ALTERNATIVA1);
        }
        else if(alternativaMarcada == ALTERNATIVA3)
        {
            resposta = verificaResposta(this.ALTERNATIVA2);
        }
        else if(alternativaMarcada == ALTERNATIVA4)
        {
            resposta = verificaResposta(this.ALTERNATIVA3);
        }
        else
        {
            alertaOpcaoVazia();
            return;
        }

        final int RESPOSTA_CERTA  = 1;

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
        setAllButtonsUnclickable();
        setQtdErros(getQtdErros() + 1);
        refreshListener.playSoundWrongAnswer();
        initAnimationAnswer(getImgRespostaErrada());
        hideUnnecessaryView(getBtnChecarResposta());
        unhideView(getBtnAvancarQuestao());
        calcularPontuacao();
        showCorrectAnswer();
    }

    private void showCorrectAnswer() {

        for(int i = 1; i <= 4; i++) {
            RadioButton correctAnswerButton = (RadioButton) this.radioGroupQuestao.getChildAt(i - 1);
            RadioButton checkedButton = getCheckedButton();
            int alternativa = verificaResposta(i);
            final int RESPOSTA_CERTA = 1;

            if( alternativa == RESPOSTA_CERTA) {

                correctAnswerButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_alternativa_correta, 0);

                if(checkedButton != null) {
                    checkedButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_alternativa_errada, 0);
                }

                break;
            }
        }
    }

    @Override
    // MÉTODO QUE CONFIGURA A TELA PARA O USUÁRIO RESPONDER NOVAMENTE
    protected void tentarNovamente() {
        super.tentarNovamente();

        hideUnnecessaryView(getBtnAvancarQuestao());

        resetButtonsStyle();
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

    @Override
    protected void calcularPontuacao() {

        int pontos = getPontuacao();

        pontos += 1000;

        switch (getQtdErros()) {
            case 0: setPontuacao(pontos + 500);
                break;
            default: pontos = 0;
                break;
        }

        setPontuacao(pontos);

        getTxtPontos().setText("Pontos: " + String.valueOf(getPontuacao()));
    }

    protected void setAllButtonsUnclickable() {
        alternativa0.setClickable(false);
        alternativa1.setClickable(false);
        alternativa2.setClickable(false);
        alternativa3.setClickable(false);
    }

    protected void setAllButtonsClickable() {
        alternativa0.setClickable(true);
        alternativa1.setClickable(true);
        alternativa2.setClickable(true);
        alternativa3.setClickable(true);
    }

    protected void uncheckAllButtons() {
        radioGroupQuestao.clearCheck();
    }

    private void resetButtonsStyle() {
        for(int i = 0; i < radioGroupQuestao.getChildCount(); i++ ) {
            RadioButton button = (RadioButton) radioGroupQuestao.getChildAt(i);
            button.setCompoundDrawables(null, null, null, null);
        }
    }

    protected int verificaResposta(int alternativa) {

        final int RESPOSTA_ERRADA = 0;

        GerenciadorBanco DB = getDB_PROGRESSO();
        int moduloAtual = getModuloAtual();
        int etapaAtual = getEtapaAtual();

        switch(alternativa) {
            case 1:
                return DB.verificaPergunta(refreshListener.getModuloAtual(), refreshListener.getEtapaAtual(), questaoAtual, 1);
            case 2:
                return DB.verificaPergunta(refreshListener.getModuloAtual(), refreshListener.getEtapaAtual(), questaoAtual, 2);
            case 3:
                return DB.verificaPergunta(refreshListener.getModuloAtual(), refreshListener.getEtapaAtual(), questaoAtual, 3);
            case 4:
                return DB.verificaPergunta(refreshListener.getModuloAtual(), refreshListener.getEtapaAtual(), questaoAtual, 4);
            default:
                return RESPOSTA_ERRADA;
        }
    }


    protected void alertaOpcaoVazia() {
        alertaOpcaoVazia.alertDialogBloqueadoLicao("Selecione uma opção", "Selecione uma resposta!");
    }
}
