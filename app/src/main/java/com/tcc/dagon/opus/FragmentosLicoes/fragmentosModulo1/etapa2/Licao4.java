package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa2;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao4 extends Fragment {

    // OBJETO BANCO
    private GerenciadorBanco DB_PROGRESSO;

    // BOTÕES DE CHECAR RESPOSTA, AVANÇAR E TENTAR DE NOVO
    private Button  btnChecar,
            btnAvancar,
            btnTentarNovamente;

    // REFERENCIA DO VIEWPAGER DO CONTAINER
    private ViewPager mViewPager;

    // REFERENCIA DO LAYOUT DO CONTAINER
    private LinearLayout tabStrip;

    // REFERENCIA DO TABLAYOUT DO CONTAINER
    private TabLayout mTabLayout;

    private View rootView;

    // SONS DO APP
    private MediaPlayer somRespostaCerta;
    private MediaPlayer somRespostaErrada;

    // VARIÁVEL QUE VÊ SE O LIMITE DA EDIT TEXT FOI ATINGIDO
    private boolean isReached = false;

    private EditText linha2Palavra1,
                     linha2Palavra2,
                     linha2Palavra3,
                     linha3Palavra1,
                     linha4Palavra1,
                     linha4Palavra2;

    private String sLinha2Palavra1,
                    sLinha2Palavra2,
                    sLinha2Palavra3,
                    sLinha3Palavra1,
                    sLinha4Palavra1,
                    sLinha4Palavra2;

    private static final String respostaLinha2Palavra1           = "olhar",
                                respostaLinha2Palavra2           = "para",
                                respostaLinha2Palavra3           = "direita",
                                respostaLinha3Palavra1           = "atravesse",
                                respostaLinha4Palavra1           = "nao",
                                respostaLinha4Palavra1Acentuada  = "não",
                                respostaLinha4Palavra2           = "atravesse";

    // IMAGENS DE CERTO E ERRADO
    private ImageView imgRespostaCerta, imgRespostaErrada;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DO OBJETO BANCO
        DB_PROGRESSO = new GerenciadorBanco(getActivity());

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        rootView = inflater.inflate(R.layout.fragment_modulo1_etapa2_licao4, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

        somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
        somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);

        listeners();

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo1Etapa2)getActivity()).getPager();
        tabStrip   = ((ContainerModulo1Etapa2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo1Etapa2)getActivity()).getmTabLayout();

        // PEGANDO OS BOTÕES AVANÇAR, CHECAR E TENTAR DE NOVO
        btnChecar          = (Button) rootView.findViewById(R.id.btnChecarResposta);
        btnAvancar         = (Button) rootView.findViewById(R.id.btnAvancar);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);

        /* LINHA 2 AUTO COMPLETAR*/
        linha2Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha2Palavra1);
        linha2Palavra2 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha2Palavra2);
        linha2Palavra3 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha2Palavra3);

        /* LINHA 3 AUTO COMPLETAR */
        linha3Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha3Palavra1);

        /* LINHA 4 AUTO COMPLETAR */
        linha4Palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha4Palavra1);
        linha4Palavra2 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha4Palavra2);

        // IMAGENS CERTO E ERRADO
        imgRespostaCerta  = (ImageView) rootView.findViewById(R.id.imgRespostaCerta);
        imgRespostaErrada = (ImageView) rootView.findViewById(R.id.imgRespostaErrada);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // SUMINDO COM OS BOTÕES DESNECESSARIOS NO INICIO DA ATIVIDADE
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);

    }


    private void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                       sLinha2Palavra1 = linha2Palavra1.getText().toString();
                       sLinha2Palavra2 = linha2Palavra2.getText().toString();
                       sLinha2Palavra3 = linha2Palavra3.getText().toString();
                       sLinha3Palavra1 = linha3Palavra1.getText().toString();
                       sLinha4Palavra1 = linha4Palavra1.getText().toString();
                       sLinha4Palavra2 = linha4Palavra2.getText().toString();
                if(sLinha2Palavra1.isEmpty() ||
                   sLinha2Palavra2.isEmpty() ||
                   sLinha2Palavra3.isEmpty() ||
                   sLinha3Palavra1.isEmpty() ||
                   sLinha4Palavra1.isEmpty() ||
                   sLinha4Palavra2.isEmpty() )
                {

                    Toast.makeText(getActivity(), "Há campos em branco!", Toast.LENGTH_SHORT).show();

                } else if(sLinha2Palavra1.equalsIgnoreCase(respostaLinha2Palavra1) &&
                          sLinha2Palavra2.equalsIgnoreCase(respostaLinha2Palavra2) &&
                          sLinha2Palavra3.equalsIgnoreCase(respostaLinha2Palavra3) &&
                          sLinha3Palavra1.equalsIgnoreCase(respostaLinha3Palavra1) &&
                          (sLinha4Palavra1.equalsIgnoreCase(respostaLinha4Palavra1) ||
                          sLinha4Palavra1.equalsIgnoreCase(respostaLinha4Palavra1Acentuada)) &&
                          sLinha4Palavra2.equalsIgnoreCase(respostaLinha4Palavra2))
                {

                        respostaCerta();

                } else {
                    respostaErrada();
                }
            }
        });

        // BOTAO AVANÇAR LICAO
        btnAvancar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirLicao();
            }
        });

        // BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente();
                linha2Palavra1.requestFocus();
                invocaTeclado();
            }
        });

        // LISTENER QUE VERIFICA QUANDO A ABA SELECIONADA É MUDADA, SELECIONADA ou RE-SELECIONADA
        // ELE É IMPORTANTE PARA ESVAZIAR OS RADIO BUTTONS AO SAIR DA ATIVIDADE ENQUANTO ESTÃO CHECADOS
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                limparEditTexts();
                habilitarEditTexts();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                btnAvancar.setVisibility(View.GONE);
                btnTentarNovamente.setVisibility(View.GONE);
                btnChecar.setVisibility(View.VISIBLE);

                // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
                imgRespostaCerta.setVisibility(View.GONE);
                imgRespostaErrada.setVisibility(View.GONE);

                // VOLTANDO O TEXTO PARA PRETO AO SAIR DA TAB PARA NÃO FICAR VERMELHO CASO O USUÁRIO SAIA ANTES DE CLICAR EM TENTAR NOVAMENTE
                linha2Palavra1.setTextColor(Color.BLACK);
                linha2Palavra2.setTextColor(Color.BLACK);
                linha2Palavra3.setTextColor(Color.BLACK);
                linha3Palavra1.setTextColor(Color.BLACK);
                linha4Palavra1.setTextColor(Color.BLACK);
                linha4Palavra2.setTextColor(Color.BLACK);
                limparEditTexts();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        /*LISTENERS DAS EDIT TEXTS PARA AVANÇAREM QUANDO FOR PREENCHIDA A PALAVRA*/
        // LINHA 2

        linha2Palavra1.addTextChangedListener(new TextWatcher() {
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

        linha2Palavra2.addTextChangedListener(new TextWatcher() {
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

        linha2Palavra3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha2Palavra3.getText().length() == 7) {
                    linha3Palavra1.requestFocus();
                }
            }
        });

        // LINHA 3
        linha3Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha3Palavra1.getText().length() == 9) {
                    linha4Palavra1.requestFocus();
                }
            }
        });

        // LINHA 4

        linha4Palavra1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha4Palavra1.getText().length() == 3) {
                    linha4Palavra2.requestFocus();
                }
            }
        });

        linha4Palavra2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(linha4Palavra2.getText().length() == 9) {
                    escondeTeclado();
                }
            }
        });



    }

    // MÉTODO QUE DESABILITA OS RADIO BUTTONS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    private void desabilitarEditTexts() {
        linha2Palavra1.setInputType(InputType.TYPE_NULL);
        linha2Palavra2.setInputType(InputType.TYPE_NULL);
        linha2Palavra3.setInputType(InputType.TYPE_NULL);
        linha3Palavra1.setInputType(InputType.TYPE_NULL);
        linha4Palavra1.setInputType(InputType.TYPE_NULL);
        linha4Palavra2.setInputType(InputType.TYPE_NULL);

        linha2Palavra1.setFocusable(false);
        linha2Palavra2.setFocusable(false);
        linha2Palavra3.setFocusable(false);
        linha3Palavra1.setFocusable(false);
        linha4Palavra1.setFocusable(false);
        linha4Palavra2.setFocusable(false);

        linha2Palavra1.setFocusableInTouchMode(false);
        linha2Palavra2.setFocusableInTouchMode(false);
        linha2Palavra3.setFocusableInTouchMode(false);
        linha3Palavra1.setFocusableInTouchMode(false);
        linha4Palavra1.setFocusableInTouchMode(false);
        linha4Palavra2.setFocusableInTouchMode(false);

    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    private void habilitarEditTexts() {
        linha2Palavra1.setInputType(InputType.TYPE_CLASS_TEXT);
        linha2Palavra2.setInputType(InputType.TYPE_CLASS_TEXT);
        linha2Palavra3.setInputType(InputType.TYPE_CLASS_TEXT);
        linha3Palavra1.setInputType(InputType.TYPE_CLASS_TEXT);
        linha4Palavra1.setInputType(InputType.TYPE_CLASS_TEXT);
        linha4Palavra2.setInputType(InputType.TYPE_CLASS_TEXT);

        linha2Palavra1.setFocusable(true);
        linha2Palavra2.setFocusable(true);
        linha2Palavra3.setFocusable(true);
        linha3Palavra1.setFocusable(true);
        linha4Palavra1.setFocusable(true);
        linha4Palavra2.setFocusable(true);

        linha2Palavra1.setFocusableInTouchMode(true);
        linha2Palavra2.setFocusableInTouchMode(true);
        linha2Palavra3.setFocusableInTouchMode(true);
        linha3Palavra1.setFocusableInTouchMode(true);
        linha4Palavra1.setFocusableInTouchMode(true);
        linha4Palavra2.setFocusableInTouchMode(true);

    }

    // MÉTODO DE DESMARCAR OS RADIO BUTTONS
    private void limparEditTexts() {
        linha2Palavra1.setText("");
        linha2Palavra2.setText("");
        linha2Palavra3.setText("");

        linha3Palavra1.setText("");

        linha4Palavra1.setText("");
        linha4Palavra2.setText("");

    }

    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    private void respostaCerta() {
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
    private void respostaErrada() {
        // TOCAR SOM DE RESPOSTA ERRADA
        somRespostaErrada.start();
        // ANIMAÇÃO RESPOSTA ERRADA
        imgRespostaErrada.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaErrada)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        if(!sLinha2Palavra1.equalsIgnoreCase(respostaLinha2Palavra1)) {
            linha2Palavra1.setTextColor(Color.RED);
        }
        if (!sLinha2Palavra2.equalsIgnoreCase(respostaLinha2Palavra2)) {
            linha2Palavra2.setTextColor(Color.RED);
        }

        if (!sLinha2Palavra3.equalsIgnoreCase(respostaLinha2Palavra3)) {
            linha2Palavra3.setTextColor(Color.RED);
        }

        if(!sLinha3Palavra1.equalsIgnoreCase(respostaLinha3Palavra1)) {
            linha3Palavra1.setTextColor(Color.RED);
        }

        if(!sLinha4Palavra1.equalsIgnoreCase(respostaLinha4Palavra1) &&
           !sLinha4Palavra1.equalsIgnoreCase(respostaLinha4Palavra1Acentuada) ) {
            linha4Palavra1.setTextColor(Color.RED);
        }

        if(!sLinha4Palavra2.equalsIgnoreCase(respostaLinha4Palavra2)) {
            linha4Palavra2.setTextColor(Color.RED);
        }

        // DESABILITAR RADIO BUTTONS
        desabilitarEditTexts();

        // SUMINDO COM O BOTAO CHECAR
        btnChecar.setVisibility(View.GONE);

        // TRAZENDO BOTÃO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.VISIBLE);
    }

    // MÉTODO DE AVANÇAR LIÇÃO CASO A RESPOSTA ESTEJA CERTA E TALS
    // MÉTODO DE AVANÇAR LIÇÃO CASO A RESPOSTA ESTEJA CERTA E TALS
    private void concluirLicao() {
        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO
        if(DB_PROGRESSO.verificaProgressoEtapa(1) <= 2) {
            // AVANÇAR O PROGRESSO EM DOIS
            DB_PROGRESSO.atualizaProgressoEtapa(1,3);
        }
        getActivity().finish();

    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    private void tentarNovamente() {
        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // DESMARCANDO OS RADIO BUTTONS
        limparEditTexts();

        linha2Palavra1.setTextColor(Color.BLACK);
        linha2Palavra2.setTextColor(Color.BLACK);
        linha2Palavra3.setTextColor(Color.BLACK);
        linha3Palavra1.setTextColor(Color.BLACK);
        linha4Palavra1.setTextColor(Color.BLACK);
        linha4Palavra2.setTextColor(Color.BLACK);

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarEditTexts();
    }


    public void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }
    public void escondeTeclado() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    public void invocaTeclado() {
        linha2Palavra1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}