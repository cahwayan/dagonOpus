package com.tcc.dagon.opus.ui.curso.exercicios.questao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.curso.exercicios.ExercicioFragment;
import com.tcc.dagon.opus.common.NovaJanelaAlerta;
import com.tcc.dagon.opus.common.ViewController;

/**
 * Created by cahwayan on 04/11/2016.
 */ /**/

public class QuestaoUnicaEscolha extends ExercicioFragment {

    protected final int ALTERNATIVA0 = 0;
    protected final int ALTERNATIVA1 = 1;
    protected final int ALTERNATIVA2 = 2;
    protected final int ALTERNATIVA3 = 3;

    /* COMPONENTES VISUAIS */
    private RadioGroup radioGroupQuestao;
    private RadioButton alternativa0,
                        alternativa1,
                        alternativa2,
                        alternativa3;

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inflateRootView(inflater, container, savedInstanceState);
        accessViews(this.rootView);
        findViewsExclusivas(this.rootView);
        carregarTextoQuestao();
        setListeners();

        resetUIExercicio();

        return this.rootView;
    }

    protected void inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Todas as questões desse tipo usam o mesmo layout
        rootView = inflater.inflate(R.layout.fragment_questao, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    // Essas views são exclusivas desse tipo de exercício
    protected void findViewsExclusivas(View rootView) {
        radioGroupQuestao = (RadioGroup)  rootView.findViewById(R.id.radioGroupQuestao);
        alternativa0 = (RadioButton) rootView.findViewById(R.id.alternativa0);
        alternativa1 = (RadioButton) rootView.findViewById(R.id.alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.alternativa3);
    }

    protected void carregarTextoQuestao() {

        String[] alternativas = refreshListener.fetchAlternativesFromDatabase(questaoAtual);

        alternativa0.setText(alternativas[ALTERNATIVA0]);
        alternativa1.setText(alternativas[ALTERNATIVA1]);
        alternativa2.setText(alternativas[ALTERNATIVA2]);
        alternativa3.setText(alternativas[ALTERNATIVA3]);

    }

    @Override
    protected void setListeners() {
        super.setListeners();

        imgRespostaErrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirQuestao();
            }
        });
    }

    @Override
    protected void validarRespostaUsuario() {

        final int ALTERNATIVA0 = alternativa0.getId();
        final int ALTERNATIVA1 = alternativa1.getId();
        final int ALTERNATIVA2 = alternativa2.getId();
        final int ALTERNATIVA3 = alternativa3.getId();

        int alternativaMarcada = radioGroupQuestao.getCheckedRadioButtonId();
        int resposta;

        if(alternativaMarcada == ALTERNATIVA0)
        {
            resposta = verificarResposta(this.ALTERNATIVA0);
        }
        else if(alternativaMarcada == ALTERNATIVA1)
        {
            resposta = verificarResposta(this.ALTERNATIVA1);
        }
        else if(alternativaMarcada == ALTERNATIVA2)
        {
            resposta = verificarResposta(this.ALTERNATIVA2);
        }
        else if(alternativaMarcada == ALTERNATIVA3)
        {
            resposta = verificarResposta(this.ALTERNATIVA3);
        }
        else
        {
            alertaOpcaoVazia();
            return;
        }

        final int RESPOSTA_CERTA  = 1;

        Log.d("VAR resposta: ", String.valueOf(resposta));

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
        refreshListener.tocarSomRespostaErrada();

        ViewController.setVisible(imgRespostaErrada);
        ViewController.initPulseAnimation(imgRespostaErrada);
        ViewController.setInvisible(btnChecarResposta);
        ViewController.setVisible(btnAvancarQuestao);

        calcularPontuacao();
        showCorrectAnswer();
    }

    private void showCorrectAnswer() {

        RadioButton correctAnswerButton = getRightAnswerButton();
        RadioButton checkedButton = getCheckedButton();

        if(correctAnswerButton != null && checkedButton != null) {
            correctAnswerButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_alternativa_correta, 0);
            checkedButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_alternativa_errada, 0);
        }

    }

    private RadioButton getCheckedButton() {
        int buttonId = radioGroupQuestao.getCheckedRadioButtonId();

        return (RadioButton) rootView.findViewById(buttonId);
    }

    private RadioButton getRightAnswerButton() {

        final int RESPOSTA_CERTA = 1;

        for(int i = 0; i < radioGroupQuestao.getChildCount(); i++) {

            int alternativa = verificarResposta(i);

            if( alternativa == RESPOSTA_CERTA) {

                return (RadioButton) this.radioGroupQuestao.getChildAt(i);

            }
        }

        return null;

    }

    @Override
    // MÉTODO QUE CONFIGURA A TELA PARA O USUÁRIO RESPONDER NOVAMENTE
    protected void tentarNovamente() {

        super.tentarNovamente();

        resetButtonsStyle();
        // DESMARCANDO OS RADIO BUTTONS
        uncheckAllButtons();

        // HABILITANDO RADIO BUTTONS DE NOVO
        setAllButtonsClickable();
    }

    @Override
    protected void avancarLicao() {
        setAllButtonsClickable();
        uncheckAllButtons();
        super.avancarLicao();
    }

    @Override
    protected void calcularPontuacao() {

        if(getQtdErros() <= 0) {
            setPontuacao(1500);
        } else {
            setPontuacao(0);
        }

        String pontuacao = "Pontos: " + String.valueOf(getPontuacao());

        txtPontos.setText(pontuacao);
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

    protected void resetButtonsStyle() {
        for(int i = 0; i < radioGroupQuestao.getChildCount(); i++ ) {
            RadioButton button = (RadioButton) radioGroupQuestao.getChildAt(i);
            button.setCompoundDrawables(null, null, null, null);
        }
    }

    protected int verificarResposta(int alternativa) {

        return refreshListener.getManager().verificaAlternativa(questaoAtual, alternativa);

    }

    protected void alertaOpcaoVazia() {

        NovaJanelaAlerta.alertDialogBloqueado(getActivity(), "Selecione uma opção", "Selecione uma resposta!");

    }
}
