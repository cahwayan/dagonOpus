package com.tcc.dagon.opus.ui.etapas;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.curso.container.ContainerLicoesActivity_;
import com.tcc.dagon.opus.ui.aprender.ModuloCurso;
import com.tcc.dagon.opus.ui.curso.container.ContainerProvaActivity;
import com.tcc.dagon.opus.ui.curso.container.ContainerProvaActivity_;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import com.tcc.dagon.opus.utils.OnOffClickListener;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

@EActivity
public abstract class EtapasActivity extends AppCompatActivity {

    private List<Etapa> listEtapas;
    protected List<LinearLayout> listBtnEtapas;
    protected List<TextView> listTxtTituloEtapas;
    protected List<TextView> listBarraInferiorEtapas;

    private String[] questoesPorEtapa;

    private int moduloAtual;
    private int qtdEtapas;
    private int progressoAtual;
    private String tituloModulo;

    // OBJETO QUE INVOCA JANELA DE ALERTAS

    private GerenciadorSharedPreferences preferenceManager;

    /*SUPER VARIÁVEL CONTEXT*/
    private final Context context = this;

    /*
      * Precisa adicionar as views exclusivas de cada classe nesse método nas listas:
      * listBtnEtapas
      * listTxtTituloEtapas
      * listBarraInferiorEtapas
      * listClassesLicoes
    */
    protected abstract void findViews();

    /* Na implementação da classe filha precisa especificar qual layout retornar */
    protected abstract int getLayout();

    /* CICLO DE VIDA */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntents();
        setLayout();
        initActionBar();
        preferenceManager = new GerenciadorSharedPreferences(this);
        questoesPorEtapa = getStringArrayQuantidadeQuestoesPorEtapa();
        inicializarListasEtapas();
        loadClickListeners();
        Log.d("OnCREATE: ", "Executado");
    }

    private void getIntents() {
        this.moduloAtual = getIntent().getIntExtra("numModulo", 0);
        this.qtdEtapas = getIntent().getIntExtra("qtdEtapas", 0);
        this.tituloModulo = getIntent().getStringExtra("tituloModulo");
    }

    private void setLayout() {
        this.setContentView(getLayout());
    }

    private void initActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(tituloModulo);
        }
    }

    private String[] getStringArrayQuantidadeQuestoesPorEtapa() {
        switch(moduloAtual) {
            case ModuloCurso.MODULO0:
                return getResources().getStringArray(R.array.qtdQuestoesModulo0);
            case ModuloCurso.MODULO1:
                return getResources().getStringArray(R.array.qtdQuestoesModulo1);
            case ModuloCurso.MODULO2:
                return getResources().getStringArray(R.array.qtdQuestoesModulo2);
            case ModuloCurso.MODULO3:
                return getResources().getStringArray(R.array.qtdQuestoesModulo3);
            case ModuloCurso.MODULO4:
                return getResources().getStringArray(R.array.qtdQuestoesModulo4);
            case ModuloCurso.MODULO5:
                return getResources().getStringArray(R.array.qtdQuestoesModulo5);
            default:
                return null;
        }
    }

    private void inicializarListasEtapas() {

        listEtapas = new ArrayList<>();

        listBtnEtapas = new ArrayList<>();

        listTxtTituloEtapas = new ArrayList<>();

        listBarraInferiorEtapas = new ArrayList<>();

        findViews();

        // Criando os objetos etapa
        for(int i = 0; i <= qtdEtapas; i++) {
            listEtapas.add(new EtapaImp(i));
        }

    }

    private void loadClickListeners() {

        for(int i = 0; i < listEtapas.size(); i++) {

            final LinearLayout btnEtapa = listBtnEtapas.get(i);
            final Etapa etapa = listEtapas.get(i);
            final int numEtapa = i;

            OnOffClickListener clickListener = new OnOffClickListener() {
                @Override
                public void onOneClick(View v) {
                    initAnimation(btnEtapa);
                    int estadoEtapa = etapa.getEstadoAtual();

                    if(estadoEtapa == Etapa.BLOQUEADA) {
                        alertaEtapaBloqueada();
                    } else if(estadoEtapa == Etapa.CURSANDO || estadoEtapa == Etapa.COMPLETA) {
                        clickLiberado(numEtapa);
                    }

                    if(isEtapaProva(numEtapa) && (estadoEtapa == Etapa.COMPLETA || estadoEtapa == Etapa.CURSANDO) ) {
                        finish();
                    }
                }
            };

            btnEtapa.setOnClickListener(clickListener);
        }

    }

    private void clickLiberado(int numEtapa) {

        Intent i;
        boolean isProva = false;
        if(numEtapa == 2 /*(listEtapas.size() - 1)*/ ) { // É uma prova
            i = new Intent(context, ContainerProvaActivity_.class);
            isProva = true;
        } else {
            i = new Intent(context, ContainerLicoesActivity_.class);
        }

        i.putExtra("tituloEtapa", listTxtTituloEtapas.get(numEtapa).getText().toString());
        i.putExtra("tituloModulo", tituloModulo);
        i.putExtra("qtdEtapas", qtdEtapas);
        i.putExtra("moduloAtual", moduloAtual);
        i.putExtra("etapaAtual", numEtapa);

        Log.d("NUM ETAPA: ", String.valueOf(numEtapa));
        startActivity(i);

        if(isProva) {
            finish();
        }
    }

    private boolean isEtapaProva(int numEtapa) {
        return numEtapa == (listEtapas.size() - 1);
    }

    @UiThread
    public void carregarUIConformeProgressoAtual() {

        atualizarProgressoAtual();

        for(Etapa etapa : listEtapas) {
            etapa.atualizarEstadoConformeProgressoAtual(progressoAtual);
            int estadoAtual = etapa.getEstadoAtual();
            int numEtapa = etapa.getNumEtapa();

            switch (estadoAtual) {
                case Etapa.BLOQUEADA:
                    carregarUIBloqueada(numEtapa);
                    break;
                case Etapa.CURSANDO:
                    carregarUICursando(numEtapa);
                    break;
                case Etapa.COMPLETA:
                    carregarUICompleta(numEtapa);
                    break;
            }
        }
    }

    private void carregarUIBloqueada(int numEtapa) {

    }

    private void carregarUICursando(int numEtapa) {

        final LinearLayout botao = listBtnEtapas.get(numEtapa);
        final TextView barraInferior = listBarraInferiorEtapas.get(numEtapa);
        final TextView tituloEtapa = listTxtTituloEtapas.get(numEtapa);

        botao.setBackgroundColor(Color.WHITE);
        botao.setBackground(ContextCompat.getDrawable(context, R.drawable.borda_etapa_desbloqueada));

        barraInferior.setCompoundDrawables(null, null, null, null);
        barraInferior.setTextColor(Color.WHITE);
        barraInferior.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        barraInferior.setText(questoesPorEtapa[numEtapa]);

        tituloEtapa.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    private void carregarUICompleta(int numEtapa) {

        final LinearLayout botao = listBtnEtapas.get(numEtapa);
        final TextView barraInferior = listBarraInferiorEtapas.get(numEtapa);
        final TextView tituloEtapa = listTxtTituloEtapas.get(numEtapa);

        botao.setBackground(ContextCompat.getDrawable(context, R.drawable.borda_etapa_desbloqueada));

        barraInferior.setCompoundDrawables(null, null, null, null);
        barraInferior.setTextColor(Color.WHITE);
        barraInferior.setBackgroundColor(ContextCompat.getColor(context, R.color.corDicaAzul));
        barraInferior.setText(getResources().getString(R.string.etapaCompleta));

        tituloEtapa.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
    }

    private void atualizarProgressoAtual() {
        this.progressoAtual = preferenceManager.getProgressoEtapa(moduloAtual);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarUIConformeProgressoAtual();
        Log.d("OnStart: ", "Executado");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        carregarUIConformeProgressoAtual();
        Log.d("OnREStart: ", "Executado");
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void initAnimation(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
    }

    private void alertaEtapaBloqueada() {
        NovaJanelaAlerta.alertDialogBloqueado(this, "Etapa bloqueada", "Complete as etapas anteriores para desbloquear esta");
    }


}
