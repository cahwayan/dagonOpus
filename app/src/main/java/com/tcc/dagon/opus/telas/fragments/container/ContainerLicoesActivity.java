package com.tcc.dagon.opus.telas.fragments.container;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telas.fragments.adapter.Adapter;
import com.tcc.dagon.opus.telas.fragments.adapter.GerenciadorListaExercicios;
import com.tcc.dagon.opus.telas.fragments.exercicios.Exercicio;
import com.tcc.dagon.opus.telas.fragments.exercicios.RefreshListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by cahwayan on 04/11/2016.
 */

@EActivity(R.layout.container_licoes)
public class ContainerLicoesActivity extends AppCompatActivity implements RefreshListener {

    @ViewById TabLayout tab_layout;
    @ViewById ViewPager view_pager;
    @ViewById Toolbar toolbar;

    private GerenciadorLicoes gerenciadorProgresso;
    protected LinearLayout tabStrip;
    protected String tituloEtapa;

    protected int moduloAtual, etapaAtual;

    private int qtdFragmentos;

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

        gerenciadorProgresso = new GerenciadorLicoes(/* Context */ this, qtdFragmentos, moduloAtual, etapaAtual);
        refreshUI();
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
        qtdFragmentos = (GerenciadorListaExercicios.getInstanciasExercicios(moduloAtual, etapaAtual).length) - 1;
        String[] tabTitulos = new String[qtdFragmentos];

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

    /*
     * Esse método busca o estado atual das lições no gerenciador de progresso, e as avalia.
     * A verificação é feita uma por vez, de forma sequencial, através de um vetor. Caso a lição
     * esteja liberada, ele chama o método de configuração da lição liberada, e vice-versa. Isso é
     * feito até que não tenha mais lições para avaliar
    */

    private void configurarEstadoLicoes() {

        int[] estadoLicoes = gerenciadorProgresso.getEstadoLicoes();

        for(int i = 0; i < estadoLicoes.length; i++) {
            if(estadoLicoes[i] == FragmentoLicao.LIBERADO) {
                configurarLicaoLiberada(i);
            } else {
                configurarLicaoBloqueada(i);
            }
        }
    }

    /*
     * Esse método configura uma lição dada com o estado Liberado.
      * @param indexLicao: o método precisa saber qual o index da lição para que ele possa saber qual
      * index alterar a funcionalidade
    */
    private void configurarLicaoLiberada(int indexLicao) {

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

    public boolean isLastExercise() {
        final int contagemTotalLicoes = tab_layout.getTabCount() - 1;
        final int licaoAtual = view_pager.getCurrentItem();

        return licaoAtual == contagemTotalLicoes;
    }

    public boolean isSomDesativado() {
        return gerenciadorProgresso.getPreferences().getDesativarSons();
    }

    public int getProgressoModulo() {
        return gerenciadorProgresso.getProgressoModulo();
    }

    public void avancarProgressoModulo(int aumento) {
        gerenciadorProgresso.setProgressoModulo(getProgressoModulo() + aumento);
    }

    public int getProgressoEtapa() {
        return gerenciadorProgresso.getProgressoEtapa();
    }

    public void avancarProgressoEtapa(int aumento) {
        gerenciadorProgresso.setProgressoEtapa(getProgressoEtapa() + aumento);
        Log.d("AUMENTO REALIZADO: ", String.valueOf(getProgressoEtapa() + aumento) );
    }

    public int getProgressoLicao() {
        return gerenciadorProgresso.getProgressoLicao();
    }

    public void avancarProgressoLicao(int aumento) {
        gerenciadorProgresso.setProgressoLicao(gerenciadorProgresso.getProgressoLicao() + aumento);
    }

    public int getModuloAtual() {
        return this.moduloAtual;
    }

    public int getEtapaAtual() {
        return this.etapaAtual;
    }

    public ViewPager getPager(){
        return view_pager;
    }

    public LinearLayout getTabStrip() {
        return tabStrip;
    }

    public TabLayout getTab_layout() {
        return tab_layout;
    }

    protected void movePrevious() {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }
}
