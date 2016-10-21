package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import android.view.View.OnClickListener;

import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;


/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao3 extends Fragment {
    RadioButton alternativa1,
         alternativa2,
         alternativa3,
         alternativa4;
    GerenciadorBanco DB_PROGRESSO;
    Button btnChecar, btnAvancar, btnTentarNovamente;
    ViewPager mViewPager;
    LinearLayout tabStrip;
    TabLayout mTabLayout;

    private MediaPlayer somRespostaCerta;
    private MediaPlayer somRespostaErrada;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DB_PROGRESSO = new GerenciadorBanco(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_modulo1_etapa1_licao3, container, false);
        mViewPager = ((ContainerModulo1Etapa1)getActivity()).getPager();
        tabStrip   = ((ContainerModulo1Etapa1)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo1Etapa1)getActivity()).getmTabLayout();
        alternativa1 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa4);


        btnChecar = (Button) rootView.findViewById(R.id.btnChecarResposta);
        btnAvancar = (Button) rootView.findViewById(R.id.btnAvancar);
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);
        btnTentarNovamente.setVisibility(View.GONE);

        somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
        somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);

        listeners();



        return rootView;

    }

    @Override
    public void onPause() {
        desmarcarRadioButtons();
        btnChecar.setVisibility(View.VISIBLE);
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);
        super.onPause();
    }


    private void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_botaoimageview));
                // SE A ALTERNATIVA 1 ESTIVER SELECIONADA AO CLICAR
                if(alternativa1.isChecked()) {
                    // VERIFICAR CORRESPONDENCIA DA ALTERNATIVA 1 NO BANCO
                    // SE ESTIVER CERTA .................
                    if(verificaAlternativa1() == 1) {
                        respostaCerta();
                    // SE NÃO ESTIVER CERTA............
                    } else {
                        respostaErrada();
                    }
                } else if(alternativa2.isChecked()) {
                    if(verificaAlternativa2() == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else if (alternativa3.isChecked()){
                    if(verificaAlternativa3() == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else if (alternativa4.isChecked()) {
                    if(verificaAlternativa4() == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                }
            }
        });

        btnAvancar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_botaoimageview));
                avancarLicao();
            }
        });

        btnTentarNovamente.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_botaoimageview));
                tentarNovamente();
            }
        });
    }

    private void desabilitarRadioButtons() {
        alternativa1.setClickable(false);
        alternativa2.setClickable(false);
        alternativa3.setClickable(false);
        alternativa4.setClickable(false);
    }

    private void habilitarRadioButtons() {
        alternativa1.setClickable(true);
        alternativa2.setClickable(true);
        alternativa3.setClickable(true);
        alternativa4.setClickable(true);
    }

    private void desmarcarRadioButtons() {
        alternativa1.setChecked(false);
        alternativa2.setChecked(false);
        alternativa3.setChecked(false);
        alternativa4.setChecked(false);
    }

    private void respostaCerta() {
        somRespostaCerta.start();
        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();
        // TROCANDO O TEXTO
        btnAvancar.setVisibility(View.VISIBLE);
        //btnChecar.setVisibility(View.GONE);
        // ATUALIZAR PROGRESSO
    }

    private void respostaErrada() {
        somRespostaErrada.start();
        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();
        // TRAZENDO BOTAO DE TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.VISIBLE);
        // SUMINDO COM O BOTAO CHECAR
        btnChecar.setVisibility(View.GONE);
    }

    private void avancarLicao() {
        btnAvancar.setVisibility(View.GONE);
        btnChecar.setVisibility(View.VISIBLE);
        // HABILITANDO OS RADIO BUTTONS
        habilitarRadioButtons();

        // TROCANDO O ICONE DO CADEADO
        mTabLayout.getTabAt(3).setIcon(R.drawable.icon_licao);

        //DESMARCANDO RADIO BUTTON
        desmarcarRadioButtons();

        // TORNANDO CLICAVEL
        tabStrip.getChildAt(3).setClickable(true);
        tabStrip.getChildAt(3).setEnabled(true);

        // TROCANDO O FRAGMENTO
        mViewPager.setCurrentItem(3);
        // ATUALIZANDO O PROGRESSO
        DB_PROGRESSO.atualizaProgressoLicao(1,1,3);
    }



    private void tentarNovamente() {
        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.GONE);
        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);
        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarRadioButtons();
    }

    private int verificaAlternativa1() {
        return DB_PROGRESSO.verificaPergunta(1,1,1,1);
    }

    private int verificaAlternativa2() {
        return DB_PROGRESSO.verificaPergunta(1,1,1,2);
    }

    private int verificaAlternativa3() {
        return DB_PROGRESSO.verificaPergunta(1,1,1,3);
    }

    private int verificaAlternativa4() {
        return DB_PROGRESSO.verificaPergunta(1,1,1,4);
    }

}