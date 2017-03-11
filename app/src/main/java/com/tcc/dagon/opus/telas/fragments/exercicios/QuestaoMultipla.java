package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

/**
 * Created by cahwayan on 10/01/2017.
 */ /**/

public class QuestaoMultipla extends Questao {

    private CheckBox alternativa1;
    private CheckBox alternativa2;
    private CheckBox alternativa3;
    private CheckBox alternativa4;

    public static QuestaoMultipla novaQuestaoMultipla(int moduloAtual, int etapaAtual, int questaoAtual) {
        QuestaoMultipla questao = new QuestaoMultipla();
        questao.setModuloAtual(moduloAtual);
        questao.setEtapaAtual(etapaAtual);
        questao.setQuestaoAtual(questaoAtual);
        return questao;
    }

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflateRootView(inflater, container, savedInstanceState);
        instanciaObjetos();
        accessCheckBoxes(getRootView());
        accessViews(getRootView());
        fetchQuestionFromDatabase();
        listeners();
        return getRootView();
    }

    @Override
    protected void inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(inflater.inflate(R.layout.activity_questao_multipla_escolha, container, false));
    }

    protected void accessCheckBoxes(View rootView) {
        this.alternativa1 = (CheckBox) rootView.findViewById(R.id.alternativa0);
        this.alternativa2 = (CheckBox) rootView.findViewById(R.id.alternativa1);
        this.alternativa3 = (CheckBox) rootView.findViewById(R.id.alternativa2);
        this.alternativa4 = (CheckBox) rootView.findViewById(R.id.alternativa3);
    }

    @Override
    public void validarRespostaUsuario() {
        if(!alternativa1.isChecked() && !alternativa2.isChecked() &&
                !alternativa3.isChecked() && !alternativa4.isChecked()) {
            alertaOpcaoVazia();
            return;
        }

        Boolean[] isAlternativaChecked = {alternativa1.isChecked(),
                                          alternativa2.isChecked(),
                                          alternativa3.isChecked(),
                                          alternativa4.isChecked()};

        int respostas[] = new int[isAlternativaChecked.length];
        int algumErro = 0;
        int contadorAlternativa = 1;

        // de 0 até o tamanho do vetor que guarda o estado das checkboxes
        for(int i = 0; i <= 3; i++) {

            // se a alternativa estiver marcada ...
            if(isAlternativaChecked[i]) {
                // guardar o retorno da busca no banco de dados em um vetor int
                respostas[i] = verificaResposta(contadorAlternativa);
                //Log.i("ALTERNATIVA " + String.valueOf(i), String.valueOf(respostas[contadorAlternativa]));
                contadorAlternativa++;
                // se a resposta retornar algum 0, o usuário errou alguma alternativa
                if(respostas[i] == 0) {
                    // marcar a flag de erro
                    algumErro++;
                    // estourar o loop
                    respostaErrada();
                    break;
                }
            } else {
                respostas[i] = verificaResposta(contadorAlternativa);
                contadorAlternativa++;
                if(respostas[i] == 1) {
                    algumErro++;
                    respostaErrada();
                    break;
                }
            }

        }

        // se as consultas retornaram apenas números 1, e a variável algumErro ainda está falsa
        // significa que o usuário marcou todas corretamente
        if(algumErro <= 0) {
            respostaCerta();
        }
    }

    @Override
    public void tentarNovamente() {
        hideUnnecessaryView(getBtnTentarNovamente());
        hideUnnecessaryView(getImgRespostaCerta());
        hideUnnecessaryView(getImgRespostaErrada());
        unhideView(getBtnChecarResposta());
        getTxtPontos().setText("Pontos: 0");
        zerarPontuacao();

        this.uncheckAllButtons();
        this.setAllButtonsClickable();
    }

    @Override
    public void respostaErrada() {
        setQtdErros(getQtdErros() + 1);
        Log.d("ERROS: ", String.valueOf(getQtdErros()));
        refreshListener.playSoundWrongAnswer();
        this.initAnimationAnswer(getImgRespostaErrada());
        this.hideUnnecessaryView(getBtnChecarResposta());
        this.unhideView(getBtnTentarNovamente());

        setAllButtonsUnclickable();

    }

    @Override
    public void calcularPontuacao() {

        int pontos = getPontuacao();
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

        getTxtPontos().setText("Pontos: " + String.valueOf(pontos));
    }

    protected void fetchQuestionFromDatabase() {

        GerenciadorBanco DB = getDB_PROGRESSO();
        int moduloAtual = getModuloAtual();
        int etapaAtual = getEtapaAtual();
        int questaoAtual = getQuestaoAtual();

        getPergunta().setText(DB.puxarPergunta(moduloAtual, etapaAtual, questaoAtual));

        this.alternativa1.setText(DB.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, getALTERNATIVA0()));
        this.alternativa2.setText(DB.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, getALTERNATIVA1()));
        this.alternativa3.setText(DB.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, getALTERNATIVA2()));
        this.alternativa4.setText(DB.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, getALTERNATIVA3()));
    }

    @Override
    protected void uncheckAllButtons() {
        this.alternativa1.setChecked(false);
        this.alternativa2.setChecked(false);
        this.alternativa3.setChecked(false);
        this.alternativa4.setChecked(false);
    }

    @Override
    protected void setAllButtonsClickable() {
        this.alternativa1.setClickable(true);
        this.alternativa2.setClickable(true);
        this.alternativa3.setClickable(true);
        this.alternativa4.setClickable(true);
    }

    @Override
    protected void setAllButtonsUnclickable() {
        this.alternativa1.setClickable(false);
        this.alternativa2.setClickable(false);
        this.alternativa3.setClickable(false);
        this.alternativa4.setClickable(false);
    }

}
