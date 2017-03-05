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
import com.tcc.dagon.opus.telas.fragments.container.ContainerLicoes;
import com.tcc.dagon.opus.telas.fragments.container.ContainerLicoes_;
import com.tcc.dagon.opus.ui.aprender.ModuloCurso;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;

import org.androidannotations.annotations.Background;
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

    private String[] stringQtdQuestoes;

    private int moduloAtual;
    private int qtdEtapas;
    private int progressoAtual;
    private String tituloModulo;

    // OBJETO QUE INVOCA JANELA DE ALERTAS

    private GerenciadorSharedPreferences preferenceManager;
    private NovaJanelaAlerta alerta;


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

    /* */
    protected abstract int getLayout();

    private void setLayout() {
        this.setContentView(getLayout());
    }

    private void getIntents() {
        this.moduloAtual = getIntent().getIntExtra("numModulo", 0);
        this.qtdEtapas = getIntent().getIntExtra("qtdEtapas", 0);
        this.tituloModulo = getIntent().getStringExtra("tituloModulo");
    }

    @UiThread
    protected void mainUIThread() {
        this.configurarUIEtapas();
    }

    /* CICLO DE VIDA */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntents();
        init();
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

    protected void init() {
        setLayout();
        initActionBar();

        preferenceManager = new GerenciadorSharedPreferences(this);
        stringQtdQuestoes = getStringQtdQuestoes();
        this.progressoAtual = preferenceManager.getProgressoEtapa(moduloAtual);

        inicializarListasEtapas();

        configurarUIEtapas();
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

    private void initActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(tituloModulo);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        mainUIThread();
        super.onRestart();
    }

    private String[] getStringQtdQuestoes() {
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

    protected void configurarUIEtapas() {

        progressoAtual = preferenceManager.getProgressoEtapa(moduloAtual);

        for(Etapa etapa : listEtapas) {
            etapa.atualizarUI();
        }

    }

    private void alertaEtapaBloqueada() {
        if(alerta == null) {
            alerta = new NovaJanelaAlerta(this);
        }
        alerta.alertDialogBloqueado("Etapa bloqueada", "Complete as etapas anteriores para desbloquear esta");
    }

    class EtapaImp implements Etapa {

        private final int numEtapa;
        private int estadoEtapa;

        EtapaImp(int numEtapa) {
            this.numEtapa = numEtapa;
            this.estadoEtapa = getEstadoEtapa();
            this.setClickListener();
        }

        private int getEstadoEtapa() {
            if(progressoAtual == numEtapa) {
                return CURSANDO;
            } else if(progressoAtual < numEtapa) {
                return BLOQUEADA;
            } else if(progressoAtual > numEtapa) {
                return COMPLETA;
            }

            return 0;
        }

        private void setClickListener() {
            listBtnEtapas.get(numEtapa).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    initAnimation(listBtnEtapas.get(numEtapa));

                    estadoEtapa = getEstadoEtapa();

                    if(estadoEtapa == BLOQUEADA) {
                        clickBloqueado();
                    } else if(estadoEtapa == CURSANDO || estadoEtapa == COMPLETA) {
                        clickLiberado();
                    }

                    if(isEtapaProva()) {
                        finish();
                    }
                }
            });
        }

        private void clickBloqueado() {
            alertaEtapaBloqueada();
        }

        private void clickLiberado() {
            Intent i = new Intent(context, ContainerLicoes_.class);
            i.putExtra("tituloEtapa", listTxtTituloEtapas.get(numEtapa).getText().toString());
            i.putExtra("moduloAtual", moduloAtual);
            i.putExtra("etapaAtual", numEtapa);
            Log.d("NUM ETAPA: ", String.valueOf(numEtapa));
            startActivity(i);
        }

        private boolean isEtapaProva() {
            return numEtapa == (listEtapas.size() - 1);
        }

        @Override
        public void atualizarUI() {

            this.estadoEtapa = getEstadoEtapa();

            switch(estadoEtapa) {
                case BLOQUEADA:
                    carregarUIBloqueada();
                    break;
                case CURSANDO:
                    carregarUICursando();
                    break;
                case COMPLETA:
                    carregarUICompleta();
                    break;
            }
        }

        private void carregarUIBloqueada() {

        }

        private void carregarUICursando() {
            LinearLayout botao = listBtnEtapas.get(numEtapa);
            TextView barraInferior = listBarraInferiorEtapas.get(numEtapa);
            TextView tituloEtapa = listTxtTituloEtapas.get(numEtapa);

            botao.setBackgroundColor(Color.WHITE);
            botao.setBackground(ContextCompat.getDrawable(context, R.drawable.borda_etapa_desbloqueada));

            barraInferior.setCompoundDrawables(null, null, null, null);
            barraInferior.setTextColor(Color.WHITE);
            barraInferior.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
            barraInferior.setText(stringQtdQuestoes[numEtapa]);

            tituloEtapa.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        private void carregarUICompleta() {

            LinearLayout botao = listBtnEtapas.get(numEtapa);
            TextView barraInferior = listBarraInferiorEtapas.get(numEtapa);
            TextView tituloEtapa = listTxtTituloEtapas.get(numEtapa);

            botao.setBackground(ContextCompat.getDrawable(context, R.drawable.borda_etapa_desbloqueada));

            barraInferior.setCompoundDrawables(null, null, null, null);
            barraInferior.setTextColor(Color.WHITE);
            barraInferior.setBackgroundColor(ContextCompat.getColor(context, R.color.corDicaAzul));
            barraInferior.setText(getResources().getString(R.string.etapaCompleta));

            tituloEtapa.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        protected void initAnimation(View view) {
            view.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
        }
    }
}
