package com.tcc.dagon.opus.Activities.Fragments.Exercicios;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.Activities.AppCompatActivity.Containers.ContainerProva;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by cahwayan on 11/11/2016.
 */

public class QuestaoProva extends Questao {

    private ImageView vida01, vida02, vida03, vida04, vida05;

    public OnHeadlineSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    public static QuestaoProva newInstance(int moduloAtual, int etapaAtual, int questaoAtual) {
        QuestaoProva questao = new QuestaoProva();
        Bundle args = new Bundle();
        args.putInt("moduloAtual", moduloAtual);
        args.putInt("etapaAtual", etapaAtual);
        args.putInt("questaoAtual", questaoAtual);

        questao.setArguments(args);
        return questao;
    }

    @Override
    protected void setRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.activity_questao_prova, container, false);
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
    protected void changeUpperBarIcon(int passo, int drawableID) {
        passo = 1;
        super.changeUpperBarIcon(passo, drawableID);
    }

    @Override
    protected void setUpperBarIconClickable(int passo) {
        passo = 1;
        super.setUpperBarIconClickable(passo);
    }

    @Override
    protected void updateUserProgress() {
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


    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    @Override
    protected void respostaErrada() {
        super.respostaErrada();

        ContainerProva container = (ContainerProva) getActivity();
        int contagemVidas = container.getContagemVidas();

        switch(contagemVidas) {
            case 5:
                PulseAnimation.create().with(vida05)
                        .setDuration(310)
                        .setRepeatCount(3)
                        .setRepeatMode(PulseAnimation.REVERSE)
                        .start();

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        vida05.setBackgroundResource(R.drawable.iconevidaprovavazio);
                    }
                }, 1000);
                break;

            case 4:
                PulseAnimation.create().with(vida04)
                        .setDuration(310)
                        .setRepeatCount(3)
                        .setRepeatMode(PulseAnimation.REVERSE)
                        .start();

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        vida04.setBackgroundResource(R.drawable.iconevidaprovavazio);
                    }
                }, 1000);
                break;
            case 3:
                PulseAnimation.create().with(vida03)
                        .setDuration(310)
                        .setRepeatCount(3)
                        .setRepeatMode(PulseAnimation.REVERSE)
                        .start();

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        vida03.setBackgroundResource(R.drawable.iconevidaprovavazio);
                    }
                }, 1000);
                break;
            case 2:
                PulseAnimation.create().with(vida02)
                        .setDuration(310)
                        .setRepeatCount(3)
                        .setRepeatMode(PulseAnimation.REVERSE)
                        .start();

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        vida02.setBackgroundResource(R.drawable.iconevidaprovavazio);
                    }
                }, 1000);
                break;
            case 1:
                PulseAnimation.create().with(vida01)
                        .setDuration(310)
                        .setRepeatCount(3)
                        .setRepeatMode(PulseAnimation.REVERSE)
                        .start();

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        vida01.setBackgroundResource(R.drawable.iconevidaprovavazio);
                    }
                }, 1000);
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
    protected void tentarNovamente() {
        super.tentarNovamente();

        ContainerProva container = (ContainerProva) getActivity();
        if(container.getContagemVidas() == 0) {
            Toast.makeText(getActivity(), "Você perdeu todas as vidas! Tente de novo.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), retornarTelaEtapas(getModuloAtual())));
            this.getActivity().finish();
        }
    }

    @Override
    protected void avancarQuestao() {
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
    protected void questaoFinal() {
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
    protected void accessViews(View rootView) {
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

    protected Class retornarTelaEtapas(int numeroModulo) {
        switch(numeroModulo) {
            case 1: return EtapasModulo1Activity.class;
            /*case 2: return EtapasModulo2Activity.class;
            case 3: return EtapasModulo3Activity.class;
            case 4: return EtapasModulo4Activity.class;
            case 5: return EtapasModulo5Activity.class;
            case 6: return EtapasModulo6Activity.class;*/
            default: return null;
        }
    }
}
