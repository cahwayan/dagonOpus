package com.tcc.dagon.opus.telas.fragments.container;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telas.fragments.adapter.Adapter;
import com.tcc.dagon.opus.telas.fragments.adapter.GerenciadorListaExercicios;
import com.tcc.dagon.opus.telas.fragments.exercicios.CExercicio;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by cahwayan on 04/11/2016.
 */

@EActivity(R.layout.container_etapa)
public class ContainerLicoes extends AppCompatActivity implements CExercicio.RefreshListener {

    @ViewById TabLayout tab_layout;
    @ViewById ViewPager view_pager;
    @ViewById Toolbar toolbar;

    private GerenciadorFragmentosConteudo gerenciadorProgresso;
    protected LinearLayout tabStrip;
    protected String tituloEtapa;

    public void setModuloAtual(int moduloAtual) {
        this.moduloAtual = moduloAtual;
    }

    public void setEtapaAtual(int etapaAtual) {
        this.etapaAtual = etapaAtual;
    }

    protected int moduloAtual, etapaAtual;

    int quantidadeFragmentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /* Início ciclo de vida */

    /* Fim ciclo de vida*/

    @Override
    public void onBackPressed() {
        /* Se a condição for verdadeira, o fragmento atual é uma lição*/
        if (view_pager.getCurrentItem() % 2 == 0 ) {
            super.onBackPressed();
        } else { // Senão, é um exercício
            movePrevious(view_pager);
        }
    }

    private void getIntents() {
        this.tituloEtapa = getIntent().getStringExtra("tituloEtapa");
        this.moduloAtual = getIntent().getIntExtra("moduloAtual", 0);
        this.etapaAtual  = getIntent().getIntExtra("etapaAtual", 0);
        Log.d("NUM ETAP DENTRO LICAO: ", String.valueOf(etapaAtual));
    }

    @AfterViews
    protected void init() {
        getIntents();
        // Corta o layout em blocos
        tabStrip = ((LinearLayout) tab_layout.getChildAt(0));

        initActionBar();
        initUI();
        initGerenciadorConteudo();
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);

        if(this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(tituloEtapa);
        }
    }

    private void initGerenciadorConteudo() {
        gerenciadorProgresso = new GerenciadorFragmentosConteudo(this, quantidadeFragmentos, moduloAtual, etapaAtual);
        refreshUI();
    }

    private void initUI() {
        // Set o adapter que mostra os fragmentos na tela
        view_pager.setAdapter(getInstanciasConteudo());

        // Com o pager configurado, ele passa as infos para o layout e ele dispõe os fragmentos
        tab_layout.setupWithViewPager(view_pager);
    }

    @UiThread
    public void refreshUI() {
        configurarEstadoLicoes();
    }

    private PagerAdapter getInstanciasConteudo() {
        String[] tabTitulos = new String[GerenciadorListaExercicios.getArrayInstanciasExercicios(moduloAtual, etapaAtual).length];
        quantidadeFragmentos = tabTitulos.length - 1;
        // Setando o adapter que vai retornar os fragmentos
        return new Adapter(getSupportFragmentManager(),
                tabTitulos,
                moduloAtual,
                etapaAtual);
    }

    // Método que deve ser chamado para atualizar a UI
    private void configurarEstadoLicoes() {

        int[] progressoFragmentos = gerenciadorProgresso.getEstadoLicoes();

        for(int i = 0; i < progressoFragmentos.length; i++) {
            if(progressoFragmentos[i] == FragmentoConteudo.LIBERADO) {
                configurarLicaoLiberada(i);
            } else {
                configurarLicaoBloqueada(i);
            }
        }
    }

    private void configurarLicaoLiberada(int indexLicao) {

        if(indexLicao % 2 == 0) {
            tab_layout.getTabAt(indexLicao).setIcon(R.drawable.icon_licao);
        } else {
            tab_layout.getTabAt(indexLicao).setIcon(R.drawable.icon_pergunta);
        }

        tabStrip.getChildAt(indexLicao).setClickable(true);
        tabStrip.getChildAt(indexLicao).setEnabled(true);
    }

    private void configurarLicaoBloqueada(int indexLicao) {
        tab_layout.getTabAt(indexLicao).setIcon(R.drawable.icon_lock_licao);
        tabStrip.getChildAt(indexLicao).setClickable(false);
        tabStrip.getChildAt(indexLicao).setEnabled(false);
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

    protected void movePrevious(View view) {
        view_pager.setCurrentItem(view_pager.getCurrentItem() - 1);
    }

    public boolean isLastExercise() {
        final int contagemTotalLicoes = tab_layout.getTabCount() - 1;
        final int licaoAtual = view_pager.getCurrentItem();

        return licaoAtual == contagemTotalLicoes;
    }

    public void setProgressoLicao(int aumento) {
        gerenciadorProgresso.setProgressoLicao(gerenciadorProgresso.getProgressoLicao() + aumento);
    }

    public int getProgressoLicao() {
        return gerenciadorProgresso.getProgressoLicao();
    }

    public void setProgressoEtapa(int aumento) {
        gerenciadorProgresso.setProgressoEtapa(getProgressoEtapa() + aumento);
        Log.d("AUMENTO REALIZADO: ", String.valueOf(getProgressoEtapa() + aumento) );
    }

    public int getProgressoEtapa() {
        return gerenciadorProgresso.getProgressoEtapa();
    }

    public int getEtapaAtual() {
        return this.etapaAtual;
    }
}
