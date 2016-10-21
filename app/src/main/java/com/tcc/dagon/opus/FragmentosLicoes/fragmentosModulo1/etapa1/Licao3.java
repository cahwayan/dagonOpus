package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1;

import android.content.Context;
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
    Button btnChecar;
    ViewPager mViewPager;
    LinearLayout tabStrip;
    TabLayout mTabLayout;
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

        listeners();

        return rootView;

    }

    private void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new OnClickListener() {
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

    private void respostaCerta() {
        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();
        // TROCANDO O TEXTO
        btnChecar.setText(R.string.txtAvancar);
        // ATUALIZAR PROGRESSO
        DB_PROGRESSO.atualizaProgressoLicao(1,1,3);
        mViewPager.setCurrentItem(3);
        mTabLayout.getTabAt(3).setIcon(R.drawable.icon_licao);
        tabStrip.getChildAt(3).setClickable(true);
        tabStrip.getChildAt(3).setEnabled(true);
    }

    private void respostaErrada() {
        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();
        // TROCAR O TEXTO
        btnChecar.setText(R.string.txtTentarNovamente);
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