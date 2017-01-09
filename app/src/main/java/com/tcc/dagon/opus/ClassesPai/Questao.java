package com.tcc.dagon.opus.ClassesPai;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.text.Text;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import com.tcc.dagon.opus.utils.PulseAnimation;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class Questao extends Fragment {

    private final String TAG = "DEBUG!";

    /* COMPONENTES VISUAIS */

    private RadioGroup radioGroupQuestao;
    private RadioButton alternativa1,
                        alternativa2,
                        alternativa3,
                        alternativa4;

    private TextView pergunta;
    private Button btnChecarResposta;
    private Button btnAvancarQuestao;
    private Button btnTentarNovamente;
    private ViewPager mViewPager;
    private LinearLayout tabStrip;
    private TabLayout mTabLayout;
    private ImageView imgRespostaCerta, imgRespostaErrada;
    private TextView txtPontos;

    private int qtdErros = 0;


    private int pontuacao = 0;

    /* OBJETOS */
    private GerenciadorBanco DB_PROGRESSO = null;
    private MediaPlayer somRespostaCerta = null;
    private MediaPlayer somRespostaErrada = null;
    private NovaJanelaAlerta alertaOpcaoVazia = null;

    protected View rootView;

    private int moduloAtual, etapaAtual, questaoAtual;

    protected GerenciadorSharedPreferences preferencias;

    public static Questao newInstance(int moduloAtual, int etapaAtual, int questaoAtual) {

        Questao questao = new Questao();

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

        this.moduloAtual  = getArguments().getInt("moduloAtual", 0);
        this.etapaAtual   = getArguments().getInt("etapaAtual", 0);
        this.questaoAtual = getArguments().getInt("questaoAtual", 0);

        configurarQuestao(moduloAtual, etapaAtual, questaoAtual);

        rootView = inflater.inflate(R.layout.activity_questao, container, false);

        this.instanciaObjetos();

        //TRAZENDO AS VIEWS
        this.accessViews();

        // CARREGANDO A LÓGICA DOS LISTENERS DA CLASSE PAI
        this.listeners();

        return rootView;
    }

    protected void instanciaObjetos() {
        // INSTANCIANDO OS OBJETOS DA CLASSE PAI
        // OBJETO BANCO DE DADOS
        if(DB_PROGRESSO == null) {
            this.DB_PROGRESSO = new GerenciadorBanco(getActivity());
        }

        // OBJETO JANELA DE ALERTA
        if(alertaOpcaoVazia == null) {
            alertaOpcaoVazia = new NovaJanelaAlerta(getActivity());
        }

        // CARREGANDO OS SONS DAS RESPOSTAS
        if(somRespostaCerta == null || somRespostaErrada == null) {
            somRespostaCerta = MediaPlayer.create(getActivity(), R.raw.resposta_certa);
            somRespostaErrada = MediaPlayer.create(getActivity(), R.raw.resposta_errada);
        }

        preferencias = new GerenciadorSharedPreferences(getActivity());
    }

    public void configurarQuestao(int moduloAtual, int etapaAtual, int questaoAtual) {
        this.moduloAtual  = moduloAtual;
        this.etapaAtual   = etapaAtual;
        this.questaoAtual = questaoAtual;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        desmarcarRadioButtons();
        btnChecarResposta.setVisibility(View.VISIBLE);
        btnAvancarQuestao.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(somRespostaCerta != null) {
            somRespostaCerta.release();
            somRespostaCerta = null;
        }

        if(somRespostaErrada != null) {
            somRespostaErrada.release();
            somRespostaErrada = null;
        }

        super.onDestroy();
    }

    protected void accessViews() {

        mViewPager = ((ContainerEtapa)this.getActivity()).getPager();
        tabStrip   = ((ContainerEtapa)this.getActivity()).getTabStrip();
        mTabLayout = ((ContainerEtapa)this.getActivity()).getmTabLayout();

        // PEGANDO O RADIOGROUP DO LAYOUT
        radioGroupQuestao = (RadioGroup) rootView.findViewById(R.id.radioGroupQuestao);

        pergunta = (TextView) rootView.findViewById(R.id.pergunta);

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        alternativa1 = (RadioButton) rootView.findViewById(R.id.alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.alternativa4);

        txtPontos = (TextView) rootView.findViewById(R.id.txtPontos);

        carregarPergunta();

        // IMAGENS CERTO E ERRADO
        imgRespostaCerta  = (ImageView) rootView.findViewById(R.id.imgRespostaCerta);
        imgRespostaErrada = (ImageView) rootView.findViewById(R.id.imgRespostaErrada);

        // PEGANDO OS BOTÕES AVANÇAR, CHECAR E TENTAR DE NOVO
        btnChecarResposta = (Button) rootView.findViewById(R.id.btnChecar);
        btnAvancarQuestao = (Button) rootView.findViewById(R.id.btnAvancar);
        btnTentarNovamente = (Button)rootView.findViewById(R.id.btnTentarNovamente);

        // SUMINDO COM OS BOTÕES DESNECESSARIOS NO INICIO DA ATIVIDADE
        btnAvancarQuestao.setVisibility(View.GONE);
        btnTentarNovamente.setVisibility(View.GONE);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);
    }

    protected void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecarResposta.setOnClickListener(new View.OnClickListener() {
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
        btnAvancarQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluirQuestao();
            }
        });

        // BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente();
            }
        });

        // LISTENER QUE VERIFICA QUANDO A ABA SELECIONADA É MUDADA, SELECIONADA ou RE-SELECIONADA
        // ELE É IMPORTANTE PARA ESVAZIAR OS RADIO BUTTONS AO SAIR DA ATIVIDADE ENQUANTO ESTÃO CHECADOS
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tentarNovamente();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tentarNovamente();
            }
        });

        imgRespostaCerta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                concluirQuestao();
            }
        });

        imgRespostaErrada.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tentarNovamente();
            }
        });


    }

    // MÉTODO QUE DESABILITA OS RADIO BUTTONS
    // PARA QUE O USUÁRIO NÃO POSSA TROCAR DE RESPOSTA DEPOIS DE CLICAR EM CHECAR
    protected void desabilitarRadioButtons() {
        alternativa1.setClickable(false);
        alternativa2.setClickable(false);
        alternativa3.setClickable(false);
        alternativa4.setClickable(false);
    }

    //MÉTODO QUE HABILITA NOVAMENTE OS RADIO BUTTONS
    //PARA TRAZER DE VOLTA OS BOTÔES DEPOIS DE CLICAR EM TENTAR NOVAMENTE OU RETORNAR A ATIVIDADE
    protected void habilitarRadioButtons() {
        alternativa1.setClickable(true);
        alternativa2.setClickable(true);
        alternativa3.setClickable(true);
        alternativa4.setClickable(true);
    }

    // MÉTODO DE DESMARCAR OS RADIO BUTTONS
    protected void desmarcarRadioButtons() {
        radioGroupQuestao.clearCheck();
    }

    private void setPontuacao() {
        this.pontuacao += 1000;
        switch (qtdErros) {
            case 0: this.pontuacao += 500;
                break;
            case 1: this.pontuacao -= 100;
                break;
            case 2: this.pontuacao -= 200;
                break;
            case 3: this.pontuacao -= 300;
                break;
            case 4: this.pontuacao -= 400;
                break;
            default: this.pontuacao = 0;
                break;
        }

        txtPontos.setText("Pontos: " + String.valueOf(pontuacao));
    }

    // MÉTODO EXECUTADO QUANDO A RESPOSTA ESTÁ CORRETA
    protected void respostaCerta() {

        setPontuacao();
        Log.i("pontuação geral: ", String.valueOf(DB_PROGRESSO.verificarPontuacao(moduloAtual)));
        Log.i("pontuação: ", String.valueOf(this.pontuacao));
        Log.i("qtd erros: ", String.valueOf(this.qtdErros));

        // TOCAR SOM DE RESPOSTA CERTA
        if(!preferencias.lerFlagBoolean(NomePreferencia.desativarSons)) {
            somRespostaCerta.start();
        }

        // ANIMAÇÃO RESPOSTA CERTA
        imgRespostaCerta.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaCerta)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();

        //TRAZENDO O BOTÃO AVANÇAR
        btnAvancarQuestao.setVisibility(View.VISIBLE);
    }

    //MÉTODO DISPARADO QUANDO A RESPOSTA ESTÁ ERRADA
    protected void respostaErrada() {

        this.qtdErros++;

        // TOCAR SOM DE RESPOSTA ERRADA
        if(!preferencias.lerFlagBoolean(NomePreferencia.desativarSons)) {
            somRespostaErrada.start();
        }

        // ANIMAÇÃO RESPOSTA ERRADA
        imgRespostaErrada.setVisibility(View.VISIBLE);
        PulseAnimation.create().with(imgRespostaErrada)
                .setDuration(310)
                .setRepeatCount(PulseAnimation.INFINITE)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();


        // SUMINDO COM O BOTAO CHECAR
        btnChecarResposta.setVisibility(View.GONE);

        // TRAZENDO BOTÃO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.VISIBLE);

        // DESABILITAR RADIO BUTTONS
        desabilitarRadioButtons();
    }


    // MÉTODO DE AVANÇAR LIÇÃO CASO A RESPOSTA ESTEJA CERTA E TALS
    protected void concluirQuestao() {
        if(mViewPager.getCurrentItem() ==  (mTabLayout.getTabCount() - 1 )  ) {
            questaoFinal();
        } else {
            avancarQuestao();
        }
    }

    protected void questaoFinal() {
        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO
        if(this.DB_PROGRESSO.verificaProgressoEtapa(moduloAtual) <= etapaAtual) {
            // AVANÇAR O PROGRESSO EM DOIS
            this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual, (etapaAtual + 1) );
            this.DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);
        }

        this.getActivity().finish();
    }

    protected void avancarQuestao() {
        // SUMINDO COM O BOTÃO AVANÇAR
        btnAvancarQuestao.setVisibility(View.GONE);

        // TRAZENDO O BOTÃO CHECAR NOVAMENTE
        btnChecarResposta.setVisibility(View.VISIBLE);

        // REABILITANDO OS RADIO BUTTONS
        habilitarRadioButtons();

        // TROCANDO O ICONE DO CADEADO

        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 1).setIcon(R.drawable.icon_licao);
        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 2).setIcon(R.drawable.icon_pergunta);


        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setEnabled(true);

        tabStrip.getChildAt(mViewPager.getCurrentItem() + 2).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 2).setEnabled(true);

        //DESMARCANDO RADIO BUTTON
        desmarcarRadioButtons();

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        if(DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual) <= mViewPager.getCurrentItem()) {
            DB_PROGRESSO.alterarPontuacao(moduloAtual, this.pontuacao);

            // AVANÇAR O PROGRESSO EM DOIS
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, (mViewPager.getCurrentItem() + 1) );
        }

        // TROCANDO O FRAGMENTO
        moveNext(mViewPager);
    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    protected void tentarNovamente() {
        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnTentarNovamente.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecarResposta.setVisibility(View.VISIBLE);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        txtPontos.setText("Pontos: 0");
        this.pontuacao = 0;

        // DESMARCANDO OS RADIO BUTTONS
        desmarcarRadioButtons();

        // HABILITANDO RADIO BUTTONS DE NOVO
        habilitarRadioButtons();

    }

    // JANELA DE ALERTA CASO AS OPÇÕES ESTEJAM VAZIAS
    protected void alertaOpcaoVazia() {
        alertaOpcaoVazia.alertDialogBloqueadoLicao("Selecione uma opção", "Selecione uma resposta!");
    }

    protected void moveNext(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    protected void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    protected void carregarPergunta() {
        pergunta.setText(DB_PROGRESSO.puxarPergunta(moduloAtual, etapaAtual, questaoAtual));

        alternativa1.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, 1));
        alternativa2.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, 2));
        alternativa3.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, 3));
        alternativa4.setText(DB_PROGRESSO.puxarAlternativa(moduloAtual, etapaAtual, questaoAtual, 4));
    }

    // MÉTODOS DE VERIFICAÇÃO DAS ALTERNATIVAS

    protected int verificaAlternativa(int alternativa) {
        switch(alternativa) {
            case 1: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 1);
            case 2: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 2);
            case 3: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 3);
            case 4: return this.DB_PROGRESSO.verificaPergunta(moduloAtual, etapaAtual, questaoAtual, 4);
        }
        return 0;
    }

    /* MÉTODOS ESPECIAIS */

    public void setModuloAtual(int moduloAtual) {
        this.moduloAtual = moduloAtual;
    }

    public void setEtapaAtual(int etapaAtual) {
        this.etapaAtual = etapaAtual;
    }

    public void setQuestaoAtual(int questaoAtual) {
        this.questaoAtual = questaoAtual;
    }

    public int getModuloAtual() {
        return moduloAtual;
    }

    public int getEtapaAtual() {
        return etapaAtual;
    }

    public int getQuestaoAtual() {
        return questaoAtual;
    }

    public GerenciadorBanco getDB_PROGRESSO() {
        return DB_PROGRESSO;
    }

    public Button getBtnChecarResposta() {
        return btnChecarResposta;
    }

    public Button getBtnAvancarQuestao() {
        return btnAvancarQuestao;
    }

    public Button getBtnTentarNovamente() {
        return btnTentarNovamente;
    }

    public LinearLayout getTabStrip() {
        return tabStrip;
    }

    public TabLayout getmTabLayout() {
        return mTabLayout;
    }

    public ViewPager getmViewPager() {
        return mViewPager;
    }

    public ImageView getImgRespostaCerta() {
        return imgRespostaCerta;
    }

    public ImageView getImgRespostaErrada() {
        return imgRespostaErrada;
    }

    public void setmViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public void setTabStrip(LinearLayout tabStrip) {
        this.tabStrip = tabStrip;
    }

    public void setmTabLayout(TabLayout mTabLayout) {
        this.mTabLayout = mTabLayout;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

}
