package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 10/01/2017.
 */

public class QuestaoMultipla extends Questao {

    private CheckBox alternativa1;
    private CheckBox alternativa2;
    private CheckBox alternativa3;
    private CheckBox alternativa4;

    public static QuestaoMultipla novaQuestaoMultipla(int moduloAtual, int etapaAtual, int questaoAtual) {
        QuestaoMultipla questao = new QuestaoMultipla();
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
        super.getConstructorArgs();
        this.setRootView(inflater, container, savedInstanceState);
        super.instanciaObjetos();
        this.accessCheckBoxes(this.rootView);
        super.accessViews(this.rootView);
        this.fetchQuestionFromDatabase();

        // CARREGANDO A LÓGICA DOS LISTENERS DA CLASSE PAI
        this.listeners();

        return this.rootView;
    }

    @Override
    protected void setRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.activity_questao_multipla_escolha, container, false);
    }

    protected void accessCheckBoxes(View rootView) {
        this.alternativa1 = (CheckBox) rootView.findViewById(R.id.alternativa1);
        this.alternativa2 = (CheckBox) rootView.findViewById(R.id.alternativa2);
        this.alternativa3 = (CheckBox) rootView.findViewById(R.id.alternativa3);
        this.alternativa4 = (CheckBox) rootView.findViewById(R.id.alternativa4);
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
    protected void fetchQuestionFromDatabase() {
        this.pergunta.setText(DB_PROGRESSO.puxarPergunta(moduloAtual, etapaAtual, questaoAtual));

        this.alternativa1.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA1));
        this.alternativa2.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA2));
        this.alternativa3.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA3));
        this.alternativa4.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, ALTERNATIVA4));
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
