package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa4;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa2;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText linha2Palavra1,
            linha2Palavra2;

    private String sLinha2Palavra1,
            sLinha2Palavra2;

    private static final String respostaLinha1Palavra1 = "ola",
                                respostaLinha1Palavra1Acentuada = "olá",
                                respostaLinha1Palavra2 = "mundo";

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo1_etapa4_licao4, container, false);
        super.moduloAtual = 1;
        super.etapaAtual  = 4;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo1Etapa2)getActivity()).getPager();
        tabStrip   = ((ContainerModulo1Etapa2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo1Etapa2)getActivity()).getmTabLayout();

        /* LINHA 2 AUTO COMPLETAR*/
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa4Pergunta2Linha2Palavra1);
        linha2Palavra2 = (EditText) rootView.findViewById(R.id.Modulo1Etapa4Pergunta2Linha2Palavra2);

        super.accessViews();

    }


    protected void listeners() {
        super.listeners();

        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sLinha2Palavra1 = linha2Palavra1.getText().toString();
                sLinha2Palavra2 = linha2Palavra2.getText().toString();

                if(sLinha2Palavra1.isEmpty() ||
                        sLinha2Palavra2.isEmpty())
                {

                    Toast.makeText(getActivity(), "Há campos em branco!", Toast.LENGTH_SHORT).show();

                } else if( (sLinha1Palavra1.equalsIgnoreCase(respostaLinha1Palavra1Acentuada) ||
                        sLinha4Palavra2.equalsIgnoreCase(respostaLinha1Palavra1) ) &&
                        sLinha1Palavra2.equalsIgnoreCase(respostaLinha1Palavra2))
                {

                    respostaCerta();

                } else {
                    respostaErrada();
                }

            }
        });

        /*LISTENERS DAS EDIT TEXTS PARA AVANÇAREM QUANDO FOR PREENCHIDA A PALAVRA*/
        // LINHA 2

        linha1Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha2Palavra1.getText().length() == 5) {
                    linha2Palavra2.requestFocus();
                }
            }
        });

        linha1Palavra2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha2Palavra2.getText().length() == 4) {
                    linha2Palavra3.requestFocus();
                }
            }
        });

    }

    // MÉTODO QUE DESABILITA OS RADIO BUTTONS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    @Override
    protected void desabilitarEditTexts() {
        linha1Palavra1.setInputType(InputType.TYPE_NULL);
        linha1Palavra2.setInputType(InputType.TYPE_NULL);


        linha1Palavra1.setFocusable(false);
        linha1Palavra2.setFocusable(false);


        linha1Palavra1.setFocusableInTouchMode(false);
        linha1Palavra2.setFocusableInTouchMode(false);


    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    @Override
    protected void habilitarEditTexts() {
        linha1Palavra1.setInputType(InputType.TYPE_CLASS_TEXT);
        linha1Palavra2.setInputType(InputType.TYPE_CLASS_TEXT);


        linha1Palavra1.setFocusable(true);
        linha1Palavra2.setFocusable(true);


        linha1Palavra1.setFocusableInTouchMode(true);
        linha1Palavra2.setFocusableInTouchMode(true);

    }

    // MÉTODO DE DESMARCAR OS RADIO BUTTONS
    @Override
    protected void limparEditTexts() {
        linha1Palavra1.setText("");
        linha1Palavra2.setText("");
    }

    @Override
    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    protected void respostaCerta() {
        // TOCAR SOM DE RESPOSTA CERTA
        somRespostaCerta.start();

        // ANIMAÇÃO RESPOSTA CERTA
        imgRespostaCerta.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaCerta)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        // DESABILITAR RADIO BUTTONS
        desabilitarEditTexts();

        //TRAZENDO O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.VISIBLE);
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    @Override
    protected void respostaErrada() {
        // TOCAR SOM DE RESPOSTA ERRADA
        somRespostaErrada.start();
        // ANIMAÇÃO RESPOSTA ERRADA
        imgRespostaErrada.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaErrada)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        if(!sLinha1Palavra1.equalsIgnoreCase(respostaLinha1Palavra1)) {
            linha1Palavra1.setTextColor(Color.RED);
        }
        if (!sLinha1Palavra2.equalsIgnoreCase(respostaLinha1Palavra2)) {
            linha1Palavra2.setTextColor(Color.RED);
        }

        // DESABILITAR RADIO BUTTONS
        desabilitarEditTexts();

        // SUMINDO COM O BOTAO CHECAR
        btnChecar.setVisibility(View.GONE);

        // TRAZENDO BOTÃO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.VISIBLE);
    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    @Override
    protected void tentarNovamente() {
        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);


        linha1Palavra1.setTextColor(Color.BLACK);
        linha1Palavra2.setTextColor(Color.BLACK);

        // DESMARCANDO OS RADIO BUTTONS
        limparEditTexts();

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarEditTexts();
    }
}