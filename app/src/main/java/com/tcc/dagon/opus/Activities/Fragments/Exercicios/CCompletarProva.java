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
import com.tcc.dagon.opus.Interfaces.Completar;
import com.tcc.dagon.opus.Interfaces.Exercicio;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.AnimacaoResposta;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;

/**
 * Created by cahwayan on 11/11/2016.
 */

public final class CCompletarProva extends CCompletar implements Completar, Exercicio {

    private ImageView vida01, vida02, vida03, vida04, vida05;

    public OnHeadlineSelectedListener mCallback;


    /* MÉTODO ESTÁTICO DE INSTÂNCIA. COMO FRAGMENTOS NÃO POSSUEM SUPORTE DECENTE PARA O USO DE MÉTODOS CONSTRUTORES
    * (NA VERDADE NÃO É RECOMENDADO NEM SOBRESCREVER O CONSTRUTOR DE UM FRAGMENT)
    * CRIAMOS UM MÉTODO ESTÁTICO, QUE PODE SER ACESSADO DE QUALQUER LUGAR, QUE SERVE PARA INSTANCIAR A CLASSE COMO UM MÉTODO CONSTRUTOR.
    * ESSE MÉTODO RECEBE OS PARÂMETROS, E PASSA PARA O ONCREATE ATRAVÉS DE UM BUNDLE. LÁ ENTÃO, PODEMOS PEGAR ESSES VALORES E TRABALHAR COM ELES.
    * É IMPORTANTE SABER QUE AS MODIFICAÇÕES FEITAS NESSE MÉTODO, SÃO REALIZADAS ANTES DO MÉTODO ONCREATE SER EXECUTADO, POR ISSO, SERVE PERFEITAMENTE
    * COMO UM CONSTRUTOR*/

    public static CCompletarProva novoCompletarProva(int layoutID, int moduloAtual, int etapaAtual, int quantidadePalavras, String[] respostasCertas, String[] respostasCertasAcentuadas) {
        CCompletarProva completar = new CCompletarProva();
        Bundle args = new Bundle();
        args.putInt("moduloAtual", moduloAtual);
        args.putInt("etapaAtual", etapaAtual);
        args.putInt("quantidadePalavras", quantidadePalavras);
        args.putInt("layoutID", layoutID);
        args.putStringArray("respostasCertas", respostasCertas);
        args.putStringArray("respostasCertasAcentuadas", respostasCertasAcentuadas);
        completar.setArguments(args);
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
    protected void setRootView(int layoutID, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(layoutID, container, false);
    }

    /* libera apenas um exercício de cada vez, ao invés de dois, como no completar normal*/
    @Override
    public void avancarQuestao() {
        final int ICONE_EXERCICIO = 1;

        hideUnnecessaryView(btnAvancarQuestao);
        unhideView(btnChecarResposta);

        changeUpperBarIcon(ICONE_EXERCICIO, R.drawable.icon_pergunta);

        setUpperBarIconClickable(ICONE_EXERCICIO);

        hideUnnecessaryView(imgRespostaCerta);
        hideUnnecessaryView(imgRespostaErrada);

        limparEditTexts(listRespostasUsuario);
        habilitarEditTexts(listRespostasUsuario);

        updateUserProgress();

        // TROCANDO O FRAGMENTO
        moveNext(view_pager);
    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    @Override
    protected void tentarNovamente(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        super.tentarNovamente(respostasCertas, respostasCertasAcentuadas);

        ContainerProva container = (ContainerProva) getActivity();

        if(container.getContagemVidas() == 0) {
            Toast.makeText(getActivity(), "Você perdeu todas as vidas! Tente de novo.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), retornarTelaEtapas(moduloAtual)));
            this.getActivity().finish();
        }
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

    @Override
    public void updateUserProgress() {
        final int VALOR_AUMENTO_PROGRESSO = 1;
        final int PROGRESSO_ATUAL = view_pager.getCurrentItem();
        final int NOVO_PROGRESSO = PROGRESSO_ATUAL + VALOR_AUMENTO_PROGRESSO;
        final int PROGRESSO_BANCO = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);

        if(PROGRESSO_BANCO <= PROGRESSO_ATUAL) {
            DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);

            // AVANÇAR O PROGRESSO EM UM
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, NOVO_PROGRESSO);
        }
    }


    @Override
    protected void respostaErrada(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        super.respostaErrada(respostasCertas, respostasCertasAcentuadas);

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

}
