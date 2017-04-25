package com.tcc.dagon.opus.ui.curso.container;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.network.volleyrequests.usuario.RequestsUsuario;
import com.tcc.dagon.opus.network.volleyrequests.usuario.UsuarioListener;
import com.tcc.dagon.opus.network.volleyrequests.usuario.UsuarioListenerImp;
import com.tcc.dagon.opus.ui.curso.adapter.Adapter;
import com.tcc.dagon.opus.ui.curso.adapter.GerenciadorListaExercicios;
import com.tcc.dagon.opus.common.som.GerenciadorSons;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cahwayan on 04/11/2016.
 */

@EActivity(R.layout.container_licoes)
public class ContainerLicoesActivity extends AppCompatActivity implements RefreshListener {

    @ViewById TabLayout tab_layout;
    @ViewById ViewPager view_pager;
    @ViewById Toolbar toolbar;

    private GerenciadorLicoes gerenciadorLicoes;
    private GerenciadorSons sons;
    private RequestsUsuario bancoRemoto;
    private UsuarioListener callbackRequestsUsuario;

    protected LinearLayout tabStrip;
    protected String tituloEtapa;

    protected int moduloAtual, etapaAtual;

    protected int qtdFragmentos;

    private final Date STARTING_TIME = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshUI();
    }
    /* Início ciclo de vida */

    /* Fim ciclo de vida*/

    // Inicia um thread que vai rodar a cada 10 segundos salvando o tempo do usuário na sharedPreference
    public void startSalvarTempo() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gerenciadorLicoes.getPreferences().addTempoEstudo(String.valueOf(getTempoQueUsuarioEstaLogado()));
                handler.postDelayed(this, 10000);
            }

        }, 10000);
    }

    public long getTempoQueUsuarioEstaLogado() {
        final Date ENDING_TIME = Calendar.getInstance().getTime();
        return ENDING_TIME.getTime() - STARTING_TIME.getTime();
    }

    @Override
    public void atualizarTempoEstudoNoBancoRemoto() {
        String tempoEstudo = gerenciadorLicoes.getPreferences().getTempoEstudo();
        bancoRemoto.updateTempoEstudo(tempoEstudo);
    }

    @Override
    public void onBackPressed() {
        // A ordem de exibição é sempre a seguinte: Lição, exercício, lição, exercício . . .
        /* Se a condição for verdadeira, o fragmento atual é uma lição*/
        if (view_pager.getCurrentItem() % 2 == 0 ) {
            super.onBackPressed();
        } else { // Senão, é um exercício
            movePrevious();
        }
    }

    @AfterViews
    protected void init() {

        getIntents();

        // Corta o layout em blocos para podermos trabalhar com eles
        // Esse tabStrip serve apenas como referência, o que deve ser alterado
        // é o tab_layout
        tabStrip = ((LinearLayout) tab_layout.getChildAt(0));

        initActionBar();
        setupAdapter();

        sons = new GerenciadorSons(this);
        gerenciadorLicoes = new GerenciadorLicoes(/* Context */ this, qtdFragmentos, moduloAtual, etapaAtual);

        callbackRequestsUsuario = new UsuarioListenerImp(this);
        String tipoUsuario = gerenciadorLicoes.getPreferences().getTipoUsuario();
        String idUsuario = gerenciadorLicoes.getPreferences().getIdUsuario();
        bancoRemoto = new RequestsUsuario(tipoUsuario, idUsuario, callbackRequestsUsuario);

        startSalvarTempo();

    }

    private void getIntents() {
        this.tituloEtapa = getIntent().getStringExtra("tituloEtapa");
        this.moduloAtual = getIntent().getIntExtra("moduloAtual", 3600);
        this.etapaAtual  = getIntent().getIntExtra("etapaAtual", 3600);
    }


    private void initActionBar() {
        setSupportActionBar(toolbar);

        if(this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(tituloEtapa);
        }
    }

    private void setupAdapter() {
        // Set o adapter que mostra os fragmentos na tela
        view_pager.setAdapter(this.getAdapterWithContent());

        // Com o pager configurado, ele passa as infos para o layout e ele dispõe os fragmentos
        tab_layout.setupWithViewPager(view_pager);
    }

    /*
     * Retorna uma nova instância PagerAdapter com todas as instâncias dos fragmentos das lições da
      * etapa atual.
    */
    private PagerAdapter getAdapterWithContent() {
        String[] tabTitulos = new String[GerenciadorListaExercicios.getInstanciasExercicios(moduloAtual, etapaAtual).length];
        qtdFragmentos = tabTitulos.length - 1;

        // Setando o adapter que vai retornar os fragmentos
        return new Adapter(getSupportFragmentManager(),
                tabTitulos,
                moduloAtual,
                etapaAtual);
    }

    @UiThread
    public void refreshUI() {
        configurarEstadoLicoes();
    }

    /**
     * Esse método busca o estado atual das lições no gerenciador de progresso, e as avalia.
     * A verificação é feita uma por vez, de forma sequencial, através de um vetor. Caso a lição
     * esteja liberada, ele chama o método de configuração da lição liberada, e vice-versa. Isso é
     * feito até que não tenha mais lições para avaliar
    */
    protected void configurarEstadoLicoes() {

        int[] estadoLicoes = gerenciadorLicoes.getEstadoLicoes();

        for(int i = 0; i < estadoLicoes.length; i++) {
            if(estadoLicoes[i] == FragmentoConteudo.LIBERADO) {
                configurarLicaoLiberada(i);
            } else {
                configurarLicaoBloqueada(i);
            }
        }
    }

    /**
     * Esse método configura uma lição dada com o estado Liberado.
      * @param indexLicao: o método precisa saber qual o index da lição para que ele possa saber qual
      * index alterar a funcionalidade
    */
    protected void configurarLicaoLiberada(int indexLicao) {

        // Se o index em questão for par, é uma lição
        // Senao é um exercício
        // Esse modo de diferenciar limita um pouco a flexibilidade, seria ideal melhorar isso no futuro
        if(indexLicao % 2 == 0) {
            tab_layout.getTabAt(indexLicao).setIcon(R.drawable.icon_licao);
        } else {
            tab_layout.getTabAt(indexLicao).setIcon(R.drawable.icon_pergunta);
        }

        // Libera o click no index da lição
        tabStrip.getChildAt(indexLicao).setClickable(true);
        tabStrip.getChildAt(indexLicao).setEnabled(true);
    }

    // Tal como uma lição liberada, o método precisa saber o index que ele precisa alterar.
    // Essa configuração é mais simples, bastando bloquear o click da lição e alterar o ícone
    private void configurarLicaoBloqueada(int indexLicao) {
        tab_layout.getTabAt(indexLicao).setIcon(R.drawable.icon_lock_licao);
        tabStrip.getChildAt(indexLicao).setClickable(false);
        tabStrip.getChildAt(indexLicao).setEnabled(false);
    }

    @Override
    public boolean isLastExercise() {
        final int contagemTotalLicoes = tab_layout.getTabCount() - 1;
        final int licaoAtual = view_pager.getCurrentItem();

        return licaoAtual == contagemTotalLicoes;
    }

    @Override
    public boolean isSomDesativado() {
        return gerenciadorLicoes.getPreferences().getDesativarSons();
    }

    @Override
    public int getProgressoModulo() {
        return gerenciadorLicoes.getProgressoModulo();
    }

    @Override
    public void avancarProgressoModulo(int aumento) {
        gerenciadorLicoes.setProgressoModulo(aumento);
        atualizarProgressoModuloNoBancoRemoto();
    }

    @Override
    public int getProgressoEtapa() {
        return gerenciadorLicoes.getProgressoEtapa();
    }

    @Override
    public void setProgressoEtapa(int aumento) {
        gerenciadorLicoes.setProgressoEtapa(aumento);
        atualizarProgressoEtapaNoBancoRemoto();
    }

    @Override
    public void setProgressoEtapa(int moduloReferente, int aumento) {
        gerenciadorLicoes.setProgressoEtapa(moduloReferente, aumento);
    }

    @Override
    public int getProgressoLicao() {
        return gerenciadorLicoes.getProgressoLicao();
    }

    @Override
    public void setProgressoLicao(int aumento) {
        gerenciadorLicoes.setProgressoLicao(aumento);
    }

    @Override
    public void atualizarProgressoModuloNoBancoRemoto() {
        final int progressoAtual = gerenciadorLicoes.getProgressoModulo();
        bancoRemoto.updateProgressoModulo(moduloAtual, progressoAtual);
    }

    @Override
    public void atualizarProgressoEtapaNoBancoRemoto() {
        final int progressoAtual = gerenciadorLicoes.getProgressoEtapa();
        bancoRemoto.updateProgressoEtapa(moduloAtual, progressoAtual);
    }

    @Override
    public int getModuloAtual() {
        return this.moduloAtual;
    }

    @Override
    public int getEtapaAtual() {
        return this.etapaAtual;
    }

    @Override
    public String fetchQuestionFromDatabase(int questaoAtual) {
        return gerenciadorLicoes.fetchQuestionFromDatabase(questaoAtual);
    }

    @Override
    public String[] fetchAlternativesFromDatabase(int questaoAtual) {
        return gerenciadorLicoes.fetchAlternativesFromDatabase(questaoAtual);
    }

    @Override
    public void tocarSomRespostaCerta() {
        sons.playSomRespostaCerta();
    }

    @Override
    public void tocarSomRespostaErrada() {
        sons.playSomRespostaErrada();
    }

    @Override
    public void somarPontos(int pontos) {
        gerenciadorLicoes.somarPontos(moduloAtual, pontos);
        atualizarPontosNoBancoRemoto();
    }

    @Override
    public void atualizarPontosNoBancoRemoto() {
        int pontos = gerenciadorLicoes.getPreferences().getPontos(moduloAtual);
        bancoRemoto.updatePontuacao(moduloAtual, pontos);
    }

    @Override
    public GerenciadorLicoes getManager() {
        return this.gerenciadorLicoes;
    }

    @Override
    public ViewPager getViewPager(){
        return view_pager;
    }

    @Override
    public TabLayout getTabLayout() {
        return tab_layout;
    }

    @Override
    public void moveNext() {
        view_pager.setCurrentItem(view_pager.getCurrentItem() + 1);
    }

    @Override
    public void movePrevious() {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }

    @Override
    public String calcularNota() {
        return "";
    }

    @Override
    public void setNota(String nota) {

    }
}
