package com.tcc.dagon.opus.ui.curso.exercicios.questao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.ViewController;

/**
 * Created by cahwayan on 10/01/2017.
 */ /**/

public class QuestaoMultiplaEscolha extends QuestaoUnicaEscolha {

    private CheckBox alternativa0;
    private CheckBox alternativa1;
    private CheckBox alternativa2;
    private CheckBox alternativa3;

    public static QuestaoMultiplaEscolha novaQuestaoMultipla(int questaoAtual) {
        QuestaoMultiplaEscolha questao = new QuestaoMultiplaEscolha();
        questao.setQuestaoAtual(questaoAtual);
        return questao;
    }

    @Override
    protected void inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(inflater.inflate(R.layout.activity_questao_multipla_escolha, container, false));
    }

    @Override
    protected void findViewsExclusivas(View rootView) {
        this.alternativa0 = (CheckBox) rootView.findViewById(R.id.alternativa0);
        this.alternativa1 = (CheckBox) rootView.findViewById(R.id.alternativa1);
        this.alternativa2 = (CheckBox) rootView.findViewById(R.id.alternativa2);
        this.alternativa3 = (CheckBox) rootView.findViewById(R.id.alternativa3);
    }

    @Override
    public void validarRespostaUsuario() {
        if(!alternativa0.isChecked() && !alternativa1.isChecked() &&
                !alternativa2.isChecked() && !alternativa3.isChecked()) {
            alertaOpcaoVazia();
            return;
        }

        Boolean[] estadoAlternativas = {alternativa0.isChecked(),
                                          alternativa1.isChecked(),
                                          alternativa2.isChecked(),
                                          alternativa3.isChecked()};

        int respostas[] = new int[estadoAlternativas.length];
        boolean algumErro = false;

        // de 0 até o tamanho do vetor que guarda o estado das checkboxes
        for(int i = 0; i < estadoAlternativas.length; i++) {

            // se a alternativa estiver marcada ...
            if(estadoAlternativas[i]) {
                // guardar o retorno da busca no banco de dados em um vetor int
                respostas[i] = verificaResposta(i);
                //Log.i("ALTERNATIVA " + String.valueOf(i), String.valueOf(respostas[contadorAlternativa]));
                // se a resposta retornar algum 0, o usuário errou alguma alternativa
                if(respostas[i] == 0) {
                    // marcar a flag de erro
                    algumErro = true;
                    // estourar o loop
                    respostaErrada();
                    break;
                }
            } else { // Se o usuário não marcou alguma alternativa que por acaso esteja correta, ele também cometeu um erro
                respostas[i] = verificaResposta(i);
                if(respostas[i] == 1) {
                    algumErro = true;
                    respostaErrada();
                    break;
                }
            }

        }

        // se as consultas retornaram apenas números 1, e a variável algumErro ainda está 0
        // significa que o usuário marcou todas corretamente
        if(!algumErro) {
            respostaCerta();
        }
    }

    @Override
    public void respostaErrada() {
        setQtdErros(getQtdErros() + 1);
        Log.d("ERROS: ", String.valueOf(getQtdErros()));
        refreshListener.tocarSomRespostaErrada();

        ViewController.initPulseAnimation(imgRespostaErrada);
        ViewController.setInvisible(btnChecarResposta);
        ViewController.setVisible(btnTentarNovamente);


        setAllButtonsUnclickable();

    }

    @Override
    public void calcularPontuacao() {

        int pontos = 0;
        pontos += 1000;

        switch (getQtdErros()) {
            case 0: pontos += 500;
                break;
            case 1: pontos -= 100;
                break;
            case 2: pontos -= 200;
                break;
            case 3: pontos -= 300;
                break;
            case 4: pontos -= 400;
                break;
            case 5: pontos -= 500;
                break;
            default: pontos = 0;
                break;
        }

        setPontuacao(pontos);

        String mensagemPontos = "Pontos: " + String.valueOf(pontos);
        txtPontos.setText(mensagemPontos);
    }

    @Override
    protected void carregarTextoQuestao() {

        String[] alternativas = refreshListener.fetchAlternativesFromDatabase(questaoAtual);

        this.alternativa0.setText(alternativas[ALTERNATIVA0]);
        this.alternativa1.setText(alternativas[ALTERNATIVA1]);
        this.alternativa2.setText(alternativas[ALTERNATIVA2]);
        this.alternativa3.setText(alternativas[ALTERNATIVA3]);

    }

    @Override
    protected void uncheckAllButtons() {
        this.alternativa0.setChecked(false);
        this.alternativa1.setChecked(false);
        this.alternativa2.setChecked(false);
        this.alternativa3.setChecked(false);
    }

    @Override
    protected void setAllButtonsClickable() {
        this.alternativa0.setClickable(true);
        this.alternativa1.setClickable(true);
        this.alternativa2.setClickable(true);
        this.alternativa3.setClickable(true);
    }

    @Override
    protected void setAllButtonsUnclickable() {
        this.alternativa0.setClickable(false);
        this.alternativa1.setClickable(false);
        this.alternativa2.setClickable(false);
        this.alternativa3.setClickable(false);
    }

    @Override
    protected void resetButtonsStyle() {

    }

}
