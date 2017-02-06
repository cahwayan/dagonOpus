package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class Questao extends CExercicio {

    protected int questaoAtual;

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

    public void setQuestaoAtual(int questaoAtual) {
        this.questaoAtual = questaoAtual;
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

        imgRespostaErrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirQuestao();
            }
        });
    }

    @Override
    protected void validarRespostaUsuario() {

        final int ALTERNATIVA1 = alternativa1.getId();
        final int ALTERNATIVA2 = alternativa2.getId();
        final int ALTERNATIVA3 = alternativa3.getId();
        final int ALTERNATIVA4 = alternativa4.getId();

        int alternativaMarcada = radioGroupQuestao.getCheckedRadioButtonId();
        int resposta;

        if(alternativaMarcada == ALTERNATIVA1)
        {
            resposta = verificaResposta(this.ALTERNATIVA1);
        }
        else if(alternativaMarcada == ALTERNATIVA2)
        {
            resposta = verificaResposta(this.ALTERNATIVA2);
        }
        else if(alternativaMarcada == ALTERNATIVA3)
        {
            resposta = verificaResposta(this.ALTERNATIVA3);
        }
        else if(alternativaMarcada == ALTERNATIVA4)
        {
            resposta = verificaResposta(this.ALTERNATIVA4);
        }
        else
        {
            alertaOpcaoVazia();
            return;
        }

        Log.d("Retorno da alt:", String.valueOf(resposta));
        Log.d("Retorno do BANCO: ", String.valueOf(this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 3)));


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
        qtdErros++;
        playSound(somRespostaErrada);
        initAnimationAnswer(imgRespostaErrada);
        hideUnnecessaryView(btnChecarResposta);
        unhideView(btnAvancarQuestao);
        setPontuacao();
        showCorrectAnswer();
    }

    private void showCorrectAnswer() {

        for(int i = 1; i <= 4; i++) {
            RadioButton correctAnswerButton = (RadioButton) radioGroupQuestao.getChildAt(i - 1);
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

        hideUnnecessaryView(btnAvancarQuestao);

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
    protected void setPontuacao() {

        this.pontuacao += 1000;

        switch (qtdErros) {
            case 0: this.pontuacao += 500;
                break;
            default: this.pontuacao = 0;
                break;
        }

        txtPontos.setText("Pontos: " + String.valueOf(pontuacao));
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

    private void resetButtonsStyle() {
        for(int i = 0; i < radioGroupQuestao.getChildCount(); i++ ) {
            RadioButton button = (RadioButton) radioGroupQuestao.getChildAt(i);
            button.setCompoundDrawables(null, null, null, null);
        }
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





}
