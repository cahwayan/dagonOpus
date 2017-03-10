package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.telas.fragments.container.ContainerProvaActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.AnimacaoResposta;

/**
 * Created by cahwayan on 16/01/2017.
 */ /**/

public final class QuestaoMultiplaProva extends QuestaoMultipla {

    private ImageView vida01, vida02, vida03, vida04, vida05;

    public OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    public static QuestaoMultiplaProva novaQuestaoMultiplaProva(int moduloAtual, int etapaAtual, int questaoAtual) {
        QuestaoMultiplaProva questao = new QuestaoMultiplaProva();
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

        // CARREGANDO A LÓGICA DOS LISTENERS DA CLASSE PAI
        super.listeners();

        return getRootView();
    }

    @Override
    protected void inflateRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(inflater.inflate(R.layout.activity_questao_multipla_escolha_prova, container, false));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnHeadlineSelectedListener) context;
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
    public void avancarProgresso() {
        if(!usuarioJaCompletouEssaLicaoAntes()) {
            atualizarPontuacao();
            atualizarProgressoLicao(/* AUMENTO EM */ 1);
        }
    }


    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    @Override
    public void respostaErrada() {
        super.respostaErrada();

        ContainerProvaActivity container = (ContainerProvaActivity) getActivity();
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

        ContainerProvaActivity container = (ContainerProvaActivity) getActivity();
        if(container.getContagemVidas() == 0) {
            Toast.makeText(getActivity(), "Você perdeu todas as vidas! Tente de novo.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), retornarTelaEtapas(getModuloAtual())));
            this.getActivity().finish();
        }
    }

    @Override
    public void avancarQuestao() {
        final int ICONE_EXERCICIO = 1;

        hideUnnecessaryView(getBtnAvancarQuestao());
        unhideView(getBtnChecarResposta());

        changeUpperBarIcon(ICONE_EXERCICIO, R.drawable.icon_pergunta);

        setUpperBarIconClickable(ICONE_EXERCICIO);

        hideUnnecessaryView(getImgRespostaCerta());
        hideUnnecessaryView(getImgRespostaErrada());

        avancarProgresso();

        // TROCANDO O FRAGMENTO
        moveNext(getView_pager());
    }

    @Override
    public void questaoFinal() {
        //getPreferencias().setCompletouProva(getModuloAtual(), true);

        if (!usuarioJaCompletouEsseModuloAntes()) {
            atualizarPontuacao();
            liberarProximoModulo();
            liberarPrimeiraEtapaDoProximoModulo();
        }

        this.getActivity().finish();
    }

    @Override
    public void accessViews(View rootView) {
        super.setView_pager( (( (ContainerProvaActivity)this.getActivity() ).getPager()) );
        //super.setTabStrip (( (ContainerProvaActivity)this.getActivity() ).getTabStrip());
        super.setTab_layout( (( (ContainerProvaActivity)this.getActivity() ).getTab_layout() ) );
        this.vida01 = ((ContainerProvaActivity)getActivity()).getVida01();
        this.vida02 = ((ContainerProvaActivity)getActivity()).getVida02();
        this.vida03 = ((ContainerProvaActivity)getActivity()).getVida03();
        this.vida04 = ((ContainerProvaActivity)getActivity()).getVida04();
        this.vida05 = ((ContainerProvaActivity)getActivity()).getVida05();
        super.accessViews(rootView);
    }
}
