package com.tcc.dagon.opus.ClassesPai;

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

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        this.setModuloAtual( getArguments().getInt("moduloAtual", 0) );
        this.setEtapaAtual( getArguments().getInt("etapaAtual", 0) );
        this.setQuestaoAtual( getArguments().getInt("questaoAtual", 0) );

        configurarQuestao(getModuloAtual(), getEtapaAtual(), getQuestaoAtual());

        this.rootView = inflater.inflate(R.layout.activity_questao, container, false);

        this.instanciaObjetos();

        //TRAZENDO AS VIEWS
        this.accessViews();

        // CARREGANDO A LÓGICA DOS LISTENERS DA CLASSE PAI
        this.listeners();

        return this.rootView;
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
        getBtnAvancarQuestao().setVisibility(View.GONE);

        // TRAZENDO O BOTÃO CHECAR NOVAMENTE
        getBtnChecarResposta().setVisibility(View.VISIBLE);

        // REABILITANDO OS RADIO BUTTONS
        habilitarRadioButtons();

        // TROCANDO O ICONE DO CADEADO

        getmTabLayout().getTabAt(getmViewPager().getCurrentItem() + 1).setIcon(R.drawable.icon_pergunta);

        //DESMARCANDO RADIO BUTTON
        desmarcarRadioButtons();

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        getImgRespostaCerta().setVisibility(View.GONE);
        getImgRespostaErrada().setVisibility(View.GONE);

        // TROCANDO O FRAGMENTO
        moveNext(getmViewPager());

        if(getDB_PROGRESSO().verificaProgressoLicao(getModuloAtual(), getEtapaAtual()) <= getmViewPager().getCurrentItem()) {
            // AVANÇAR O PROGRESSO EM DOIS
            getDB_PROGRESSO().atualizaProgressoLicao(getModuloAtual(), getEtapaAtual(), (getmViewPager().getCurrentItem() + 1) );
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
    protected void questaoFinal() {

        // ESCREVENDO A FLAG PARA O USUARIO NAO PRECISAR REFAZER AS PROVAS APÓS TERMINAR UMA VEZ
        preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.NomePreferencia.lerFlagProva(getModuloAtual()), true);

        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO

        if (this.getDB_PROGRESSO().verificaProgressoModulo() <= getModuloAtual()) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.getDB_PROGRESSO().atualizaProgressoModulo(getModuloAtual() + 1);
            // atualizar progresso do módulo 2 para 1
            this.getDB_PROGRESSO().atualizaProgressoEtapa(getModuloAtual() + 1, 1);
        }

       /* // INICIANDO ATIVIDADE DOS MODULOS
        startActivity(new Intent(getActivity(), AprenderActivity.class));*/

        // TERMINANDO COM ESSA ATIVIDADE
        this.getActivity().finish();
    }

    @Override
    protected void accessViews() {
        super.accessViews();
        super.setmViewPager(( (ContainerProva)this.getActivity() ).getPager() );
        super.setTabStrip  (( (ContainerProva)this.getActivity() ).getTabStrip());
        super.setmTabLayout(( (ContainerProva)this.getActivity() ).getmTabLayout() );

        vida01 = ((ContainerProva)getActivity()).getVida01();
        vida02 = ((ContainerProva)getActivity()).getVida02();
        vida03 = ((ContainerProva)getActivity()).getVida03();
        vida04 = ((ContainerProva)getActivity()).getVida04();
        vida05 = ((ContainerProva)getActivity()).getVida05();
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
