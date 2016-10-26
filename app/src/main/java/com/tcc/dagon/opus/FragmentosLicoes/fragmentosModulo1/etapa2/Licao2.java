package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2;

import android.media.MediaPlayer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa2;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;


/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao2 extends Fragment {

    // RADIO BUTTONS DAS ALTERNATIVAS
    private RadioButton  alternativa1,
                         alternativa2,
                         alternativa3,
                         alternativa4;

    // OBJETO BANCO PARA VERIFICAR AS RESPOSTAS
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

    // RADIOGROUP
    private RadioGroup containerRadioButtons;

    // SONS DO APP
    private MediaPlayer somRespostaCerta;
    private MediaPlayer somRespostaErrada;

    // OBJETO DE JANELA DE ALERTA
    private NovaJanelaAlerta alertaOpcaoVazia;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DO OBJETO BANCO
        DB_PROGRESSO = new GerenciadorBanco(getActivity());
        // INSTANCIA DO OBJETO JANELA ALERTA
        alertaOpcaoVazia = new NovaJanelaAlerta(getActivity());

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        rootView = inflater.inflate(R.layout.fragment_modulo1_etapa2_licao2, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

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

    private void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo1Etapa2)getActivity()).getPager();
        tabStrip   = ((ContainerModulo1Etapa2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo1Etapa2)getActivity()).getmTabLayout();

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        alternativa1 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa2Pergunta1Alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa2Pergunta1Alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa2Pergunta1Alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa2Pergunta1Alternativa4);
        // PEGANDO O RADIOGROUP DO LAYOUT
        containerRadioButtons = (RadioGroup) rootView.findViewById(R.id.radioGroupModulo1Etapa2Licao2);

        // PEGANDO OS BOTÕES AVANÇAR, CHECAR E TENTAR DE NOVO
        btnChecar = (Button) rootView.findViewById(R.id.btnChecarResposta);
        btnAvancar = (Button) rootView.findViewById(R.id.btnAvancar);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);

        // SUMINDO COM OS BOTÕES DESNECESSARIOS NO INICIO DA ATIVIDADE
        btnAvancar.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);
    }


    private void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // SE A ALTERNATIVA 1 ESTIVER SELECIONADA AO CLICAR
                if(alternativa1.isChecked()) {
                    // VERIFICAR CORRESPONDENCIA DA ALTERNATIVA 1 NO BANCO
                    // SE ESTIVER CERTA .................
                    if(verificaAlternativa(1) == 1) {
                        respostaCerta(); // MÉTODO QUE MOSTRA O BOTÃO DE AVANÇAR
                        // SE NÃO ESTIVER CERTA............
                    } else {
                        respostaErrada(); // MÉTODO QUE MOSTRA O BOTÃO TENTAR NOVAMENTE
                    }
                } else if(alternativa2.isChecked()) {
                    if(verificaAlternativa(2) == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else if (alternativa3.isChecked()){
                    if(verificaAlternativa(3) == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else if (alternativa4.isChecked()) {
                    if(verificaAlternativa(4) == 1) {
                        respostaCerta();
                    } else {
                        respostaErrada();
                    }
                } else { // SE TODAS ESTIVEREM VAZIAS, MOSTRAR ALERTA
                    alertaOpcaoVazia();
                }
            }
        });

        // BOTAO AVANÇAR LICAO
        btnAvancar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                avancarLicao();
            }
        });

        // BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente();
            }
        });

        // LISTENER QUE VERIFICA QUANDO A ABA SELECIONADA É MUDADA, SELECIONADA ou RE-SELECIONADA
        // ELE É IMPORTANTE PARA ESVAZIAR OS RADIO BUTTONS AO SAIR DA ATIVIDADE ENQUANTO ESTÃO CHECADOS
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                habilitarRadioButtons();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                desmarcarRadioButtons();
                btnAvancar.setVisibility(View.GONE);
                btnTentarNovamente.setVisibility(View.GONE);
                btnChecar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    // MÉTODO QUE DESABILITA OS RADIO BUTTONS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    private void desabilitarRadioButtons() {
        alternativa1.setClickable(false);
        alternativa2.setClickable(false);
        alternativa3.setClickable(false);
        alternativa4.setClickable(false);
    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    private void habilitarRadioButtons() {
        alternativa1.setClickable(true);
        alternativa2.setClickable(true);
        alternativa3.setClickable(true);
        alternativa4.setClickable(true);
    }

    // MÉTODO DE DESMARCAR OS RADIO BUTTONS
    private void desmarcarRadioButtons() {
        containerRadioButtons.clearCheck();
    }

    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    private void respostaCerta() {
        // TOCAR SOM DE RESPOSTA CERTA
        somRespostaCerta.start();

        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();

        //TRAZENDO O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.VISIBLE);
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    private void respostaErrada() {
        // TOCAR SOM DE RESPOSTA ERRADA
        somRespostaErrada.start();

        // SUMINDO COM O BOTAO CHECAR
        btnChecar.setVisibility(View.GONE);

        // TRAZENDO BOTÃO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.VISIBLE);

        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();
    }

    // MÉTODO DE AVANÇAR LIÇÃO CASO A RESPOSTA ESTEJA CERTA E TALS
    private void avancarLicao() {
        // SUMINDO COM O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTÃO CHECAR NOVAMENTE
        btnChecar.setVisibility(View.VISIBLE);

        // REABILITANDO OS RADIO BUTTONS
        habilitarRadioButtons();

        // TROCANDO O ICONE DO CADEADO
        mTabLayout.getTabAt(2).setIcon(R.drawable.icon_licao);
        mTabLayout.getTabAt(3).setIcon(R.drawable.icon_pergunta);



        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(2).setClickable(true);
        tabStrip.getChildAt(2).setEnabled(true);

        tabStrip.getChildAt(3).setClickable(true);
        tabStrip.getChildAt(3).setEnabled(true);

        //DESMARCANDO RADIO BUTTON
        desmarcarRadioButtons();

        // TROCANDO O FRAGMENTO
        mViewPager.setCurrentItem(2);

        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO
        if(DB_PROGRESSO.verificaProgressoLicao(1,2) <= 1) {
            // AVANÇAR O PROGRESSO EM DOIS
            DB_PROGRESSO.atualizaProgressoLicao(1,2,3);
        }

    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    private void tentarNovamente() {
        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // DESMARCANDO OS RADIO BUTTONS
        desmarcarRadioButtons();

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarRadioButtons();
    }

    // JANELA DE ALERTA CASO AS OPÇÕES ESTEJAM VAZIAS
    private void alertaOpcaoVazia() {
        alertaOpcaoVazia.alertDialogBloqueadoLicao("Selecione uma opção", "Selecione uma resposta!");
    }

    // MÉTODOS DE VERIFICAÇÃO DAS ALTERNATIVAS
    private int verificaAlternativa(int alternativa) {
        switch(alternativa) {
            case 1: return DB_PROGRESSO.verificaPergunta(1,2,1,1);
            case 2: return DB_PROGRESSO.verificaPergunta(1,2,1,2);
            case 3: return DB_PROGRESSO.verificaPergunta(1,2,1,3);
            case 4: return DB_PROGRESSO.verificaPergunta(1,2,1,4);
        }
        return 0;
    }

}