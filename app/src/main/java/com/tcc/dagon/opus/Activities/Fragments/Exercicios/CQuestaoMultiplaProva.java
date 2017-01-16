package com.tcc.dagon.opus.Activities.Fragments.Exercicios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.Activities.AppCompatActivity.Containers.ContainerProva;
import com.tcc.dagon.opus.Interfaces.Exercicio;
import com.tcc.dagon.opus.Interfaces.Questao;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.AnimacaoResposta;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;

/**
 * Created by cahwayan on 16/01/2017.
 */

public final class CQuestaoMultiplaProva extends CQuestaoMultipla implements Questao, Exercicio {

    private ImageView vida01, vida02, vida03, vida04, vida05;

    public CQuestaoProva.OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    public static CQuestaoMultiplaProva novaQuestaoMultiplaProva(int moduloAtual, int etapaAtual, int questaoAtual) {
        CQuestaoMultiplaProva questao = new CQuestaoMultiplaProva();
        Bundle args = new Bundle();
        args.putInt("moduloAtual", moduloAtual);
        args.putInt("etapaAtual", etapaAtual);
        args.putInt("questaoAtual", questaoAtual);
        questao.setArguments(args);
        return questao;
    }

    @Override
    protected void setRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.activity_questao_multipla_escolha, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (CQuestaoProva.OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void changeUpperBarIcon(int passo, int drawableID) {
        passo = 1;
        super.changeUpperBarIcon(passo, drawableID);
    }

    @Override
    public void setUpperBarIconClickable(int passo) {
        passo = 1;
        super.setUpperBarIconClickable(passo);
    }

    @Override
    public void updateUserProgress() {
        final int VALOR_AUMENTO_PROGRESSO = 1;
        int progressoAtual = view_pager.getCurrentItem();
        int novoProgresso = progressoAtual + VALOR_AUMENTO_PROGRESSO;
        int progressoBanco = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);

        if(progressoBanco <= progressoAtual) {
            DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);

            // AVANÇAR O PROGRESSO EM UM
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, novoProgresso);
        }
    }


    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    @Override
    public void respostaErrada() {
        super.respostaErrada();

        ContainerProva container = (ContainerProva) getActivity();
        int contagemVidas = container.getContagemVidas();

        switch(contagemVidas) {
            case 5:
                AnimacaoResposta.criarAnimacaoCom(vida05);
                break;
            case 4:
                AnimacaoResposta.criarAnimacaoCom(vida04);
                break;
            case 3:
                AnimacaoResposta.criarAnimacaoCom(vida03);
                break;
            case 2:
                AnimacaoResposta.criarAnimacaoCom(vida02);
                break;
            case 1:
                AnimacaoResposta.criarAnimacaoCom(vida01);
                break;
            case 0:
                break;
            default:
                break;
        }

        contagemVidas -= 1;

        mCallback.onArticleSelected(contagemVidas);
    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    @Override
    public void tentarNovamente() {
        super.tentarNovamente();

        ContainerProva container = (ContainerProva) getActivity();
        if(container.getContagemVidas() == 0) {
            Toast.makeText(getActivity(), "Você perdeu todas as vidas! Tente de novo.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), retornarTelaEtapas(moduloAtual)));
            this.getActivity().finish();
        }
    }

    @Override
    public void avancarQuestao() {
        final int ICONE_EXERCICIO = 1;

        hideUnnecessaryView(btnAvancarQuestao);
        unhideView(btnChecarResposta);

        changeUpperBarIcon(ICONE_EXERCICIO, R.drawable.icon_pergunta);

        setUpperBarIconClickable(ICONE_EXERCICIO);

        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);

        updateUserProgress();

        // TROCANDO O FRAGMENTO
        moveNext(view_pager);
    }

    @Override
    public void questaoFinal() {
        // ESCREVENDO A FLAG PARA O USUARIO NAO PRECISAR REFAZER AS PROVAS APÓS TERMINAR UMA VEZ
        final String COMPLETOU_PROVA = GerenciadorSharedPreferences.NomePreferencia.lerFlagProva(moduloAtual);
        preferencias.escreverFlagBoolean(COMPLETOU_PROVA, true);

        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO
        final int PROGRESSO_ATUAL = DB_PROGRESSO.verificaProgressoModulo();
        final int PROXIMO_MODULO = moduloAtual + 1;
        if (PROGRESSO_ATUAL <= moduloAtual) {
            DB_PROGRESSO.alterarPontuacao(moduloAtual, pontuacao);
            DB_PROGRESSO.atualizaProgressoModulo(moduloAtual + 1);
            DB_PROGRESSO.atualizaProgressoEtapa(PROXIMO_MODULO, 1);
        }

        this.getActivity().finish();
    }

    @Override
    public void accessViews(View rootView) {
        super.view_pager = (( (ContainerProva)this.getActivity() ).getPager() );
        super.tabStrip   = (( (ContainerProva)this.getActivity() ).getTabStrip());
        super.tab_layout = (( (ContainerProva)this.getActivity() ).getmTabLayout() );
        this.vida01 = ((ContainerProva)getActivity()).getVida01();
        this.vida02 = ((ContainerProva)getActivity()).getVida02();
        this.vida03 = ((ContainerProva)getActivity()).getVida03();
        this.vida04 = ((ContainerProva)getActivity()).getVida04();
        this.vida05 = ((ContainerProva)getActivity()).getVida05();
        super.accessViews(rootView);
    }
}
