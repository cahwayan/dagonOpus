package com.tcc.dagon.opus.telas.fragments.exercicios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.telas.fragments.container.ContainerProvaActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.AnimacaoResposta;

/**
 * Created by cahwayan on 11/11/2016.
 */

public final class CompletarProva extends Completar {

    private ImageView vida01, vida02, vida03, vida04, vida05;

    public OnHeadlineSelectedListener mCallback;

    /**/
    /* MÉTODO ESTÁTICO DE INSTÂNCIA. COMO FRAGMENTOS NÃO POSSUEM SUPORTE DECENTE PARA O USO DE MÉTODOS CONSTRUTORES
    * (NA VERDADE NÃO É RECOMENDADO NEM SOBRESCREVER O CONSTRUTOR DE UM FRAGMENT)
    * CRIAMOS UM MÉTODO ESTÁTICO, QUE PODE SER ACESSADO DE QUALQUER LUGAR, QUE SERVE PARA INSTANCIAR A CLASSE COMO UM MÉTODO CONSTRUTOR.
    * ESSE MÉTODO RECEBE OS PARÂMETROS, E PASSA PARA O ONCREATE ATRAVÉS DE UM BUNDLE. LÁ ENTÃO, PODEMOS PEGAR ESSES VALORES E TRABALHAR COM ELES.
    * É IMPORTANTE SABER QUE AS MODIFICAÇÕES FEITAS NESSE MÉTODO, SÃO REALIZADAS ANTES DO MÉTODO ONCREATE SER EXECUTADO, POR ISSO, SERVE PERFEITAMENTE
    * COMO UM CONSTRUTOR*/

    public static CompletarProva novoCompletarProva(int layoutID, int moduloAtual, int etapaAtual, int quantidadePalavras, String[] respostasCertas, String[] respostasCertasAcentuadas) {
        CompletarProva completar = new CompletarProva();
        completar.setModuloAtual(moduloAtual);
        completar.setEtapaAtual(etapaAtual);
        completar.setQuantidadePalavras(quantidadePalavras);
        completar.setLayoutID(layoutID);
        completar.setRespostasCertas(respostasCertas);
        completar.setRespostasCertasAcentuadas(respostasCertasAcentuadas);
        return completar;
    }

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
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
    protected void inflateRootView(int layoutID, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(inflater.inflate(layoutID, container, false));
    }

    /* libera apenas um exercício de cada vez, ao invés de dois, como no completar normal*/
    @Override
    public void avancarQuestao() {
        final int ICONE_EXERCICIO = 1;

        hideUnnecessaryView(getBtnAvancarQuestao());
        unhideView(getBtnChecarResposta());

        changeUpperBarIcon(ICONE_EXERCICIO, R.drawable.icon_pergunta);

        setUpperBarIconClickable(ICONE_EXERCICIO);

        hideUnnecessaryView(getImgRespostaCerta());
        hideUnnecessaryView(getImgRespostaErrada());

        limparEditTexts();
        habilitarEditTexts();

        avancarProgresso();

        // TROCANDO O FRAGMENTO
        moveNext(getView_pager());
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
    protected void questaoFinal() {
        //getPreferencias().setCompletouProva(getModuloAtual(), true);

        if (!usuarioJaCompletouEsseModuloAntes()) {
            atualizarPontuacao();
            liberarProximoModulo();
            liberarPrimeiraEtapaDoProximoModulo();
        }

        this.getActivity().finish();
    }

    @Override
    protected void accessViews(View rootView) {
        super.setView_pager( (( (ContainerProvaActivity)this.getActivity() ).getPager()) );
        //super.setTabStrip (( (ContainerProvaActivity)this.getActivity() ).getTabStrip());
        super.setTab_layout (( (ContainerProvaActivity)this.getActivity() ).getTab_layout() );
        this.vida01 = ((ContainerProvaActivity)getActivity()).getVida01();
        this.vida02 = ((ContainerProvaActivity)getActivity()).getVida02();
        this.vida03 = ((ContainerProvaActivity)getActivity()).getVida03();
        this.vida04 = ((ContainerProvaActivity)getActivity()).getVida04();
        this.vida05 = ((ContainerProvaActivity)getActivity()).getVida05();
        super.accessViews(rootView);
    }

    @Override
    protected void avancarProgresso() {
        if(!usuarioJaCompletouEssaLicaoAntes()) {
            atualizarPontuacao();
            atualizarProgressoLicao(/* AUMENTO EM*/ 1);
        }
    }


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

}
