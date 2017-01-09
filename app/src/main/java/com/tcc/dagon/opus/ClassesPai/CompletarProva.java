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
import com.tcc.dagon.opus.utils.PulseAnimation;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;

/**
 * Created by cahwayan on 11/11/2016.
 */

public class CompletarProva extends Completar {

    protected ImageView vida01, vida02, vida03, vida04, vida05;

    public OnHeadlineSelectedListener mCallback;


    /* MÉTODO ESTÁTICO DE INSTÂNCIA. COMO FRAGMENTOS NÃO POSSUEM SUPORTE DECENTE PARA O USO DE MÉTODOS CONSTRUTORES
    * (NA VERDADE NÃO É RECOMENDADO NEM SOBRESCREVER O CONSTRUTOR DE UM FRAGMENT)
    * CRIAMOS UM MÉTODO ESTÁTICO, QUE PODE SER ACESSADO DE QUALQUER LUGAR, QUE SERVE PARA INSTANCIAR A CLASSE COMO UM MÉTODO CONSTRUTOR.
    * ESSE MÉTODO RECEBE OS PARÂMETROS, E PASSA PARA O ONCREATE ATRAVÉS DE UM BUNDLE. LÁ ENTÃO, PODEMOS PEGAR ESSES VALORES E TRABALHAR COM ELES.
    * É IMPORTANTE SABER QUE AS MODIFICAÇÕES FEITAS NESSE MÉTODO, SÃO REALIZADAS ANTES DO MÉTODO ONCREATE SER EXECUTADO, POR ISSO, SERVE PERFEITAMENTE
    * COMO UM CONSTRUTOR*/

    public static CompletarProva newInstance(int layoutID, int moduloAtual, int etapaAtual, int quantidadePalavras, String[] respostasCertas, String[] respostasCertasAcentuadas) {
        CompletarProva completar = new CompletarProva();
        completar.setContentView(layoutID);
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

    /* libera apenas um exercício de cada vez, ao invés de dois, como no completar normal*/
    @Override
    protected void avancarCompletar() {

        limparEditTexts(listaEditTexts);
        habilitarEditTexts(listaEditTexts);

        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // TROCANDO O ICONE DO CADEADO
        tab_layout.getTabAt(view_pager.getCurrentItem() + 1).setIcon(R.drawable.icon_pergunta);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // TROCANDO O FRAGMENTO
        moveNext(view_pager);

        if(DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual) <= view_pager.getCurrentItem()) {
            // AVANÇAR O PROGRESSO EM DOIS
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, (view_pager.getCurrentItem() + 1) );
        }

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


    protected Class retornarTelaEtapas(int numeroModulo) {
        switch(numeroModulo) {
            case 1: return EtapasModulo1Activity.class;
          /*  case 2: return EtapasModulo2Activity.class;
            case 3: return EtapasModulo3Activity.class;
            case 4: return EtapasModulo4Activity.class;
            case 5: return EtapasModulo5Activity.class;
            case 6: return EtapasModulo6Activity.class;*/
            default: return null;
        }
    }

    @Override
    protected void completarFinal() {
        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO

        // ESCREVENDO A FLAG PARA O USUARIO NAO PRECISAR REFAZER AS PROVAS APÓS TERMINAR UMA VEZ
        preferencias.escreverFlagBoolean(NomePreferencia.lerFlagProva(moduloAtual), true);

        if(this.DB_PROGRESSO.verificaProgressoModulo() <= moduloAtual) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.DB_PROGRESSO.atualizaProgressoModulo(moduloAtual + 1);
            // atualizar progresso do módulo 2 para 1
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual + 1, 1);
        }

        // TERMINANDO COM ESSA ATIVIDADE
        this.getActivity().finish();
    }

    @Override
    protected void accessViews() {
        super.accessViews();
        view_pager = ((ContainerEtapa)getActivity()).getPager();
        tabStrip   = ((ContainerEtapa)getActivity()).getTabStrip();
        tab_layout = ((ContainerEtapa)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva)getActivity()).getVida01();
        vida02 = ((ContainerProva)getActivity()).getVida02();
        vida03 = ((ContainerProva)getActivity()).getVida03();
        vida04 = ((ContainerProva)getActivity()).getVida04();
        vida05 = ((ContainerProva)getActivity()).getVida05();
    }


    @Override
    protected void respostaErrada(String[] respostasCertas, String[] respostasCertasAcentuadas) {
        super.respostaErrada(respostasCertas, respostasCertasAcentuadas);

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

}
