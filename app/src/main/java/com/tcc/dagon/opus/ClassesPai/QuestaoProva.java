package com.tcc.dagon.opus.ClassesPai;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.PulseAnimation;

import java.util.List;

/**
 * Created by cahwayan on 11/11/2016.
 */

public class QuestaoProva extends Questao {

    protected ImageView vida01, vida02, vida03, vida04, vida05;

    public OnHeadlineSelectedListener mCallback;


    // Container Activity must implement this interface
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    @Override
    public void onResume() {
        super.onResume();
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
    protected void avancarQuestao() {
        // SUMINDO COM O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTÃO CHECAR NOVAMENTE
        btnChecar.setVisibility(View.VISIBLE);

        // REABILITANDO OS RADIO BUTTONS
        habilitarRadioButtons();

        // TROCANDO O ICONE DO CADEADO

        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 1).setIcon(R.drawable.icon_pergunta);

        //DESMARCANDO RADIO BUTTON
        desmarcarRadioButtons();

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // TROCANDO O FRAGMENTO
        moveNext(mViewPager);

        if(DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual) <= mViewPager.getCurrentItem()) {
            // AVANÇAR O PROGRESSO EM DOIS
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, (mViewPager.getCurrentItem() + 1) );
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
            this.getActivity().finish();
        }

    }

    @Override
    protected void questaoFinal() {
        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO

        if (this.DB_PROGRESSO.verificaProgressoModulo() <= moduloAtual) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.DB_PROGRESSO.atualizaProgressoModulo(moduloAtual + 1);
            // atualizar progresso do módulo 2 para 1
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual + 1, 1);
        }

        // ESCREVENDO A FLAG PARA O USUARIO NAO PRECISAR REFAZER AS PROVAS APÓS TERMINAR UMA VEZ
        writeFlag(true);

        // INICIANDO ATIVIDADE DOS MODULOS

        startActivity(new Intent(getActivity(), AprenderActivity.class));
        // TERMINANDO COM ESSA ATIVIDADE
        this.getActivity().finish();
    }

    // MODIFICAR FLAG PARA LOGOUT
    public void writeFlag(boolean flag) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("completouTeste1", flag);
        editor.apply();
    }

}
