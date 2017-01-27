package com.tcc.dagon.opus.telas.etapas;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Etapas extends AppCompatActivity {

    protected LinearLayout etapas[];
    protected List<LinearLayout> listaEtapas;

    protected TextView barraInferiorEtapas[];
    protected List<TextView> listaBarraInferiorEtapas;

    protected TextView tituloEtapas[];
    protected List<TextView> listaTituloEtapas;

    protected int moduloAtual;

    // OBJETO QUE FARÁ CONSULTA NO BANCO
    protected GerenciadorBanco DB_PROGRESSO;

    // OBJETO QUE INVOCA JANELA DE ALERTAS
    NovaJanelaAlerta alerta = new NovaJanelaAlerta(this);

    /*SUPER VARIÁVEL CONTEXT*/
    protected Context context = this;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void instanciaObjetos() {

        // Instanciando o objeto banco
        DB_PROGRESSO = new GerenciadorBanco(this);

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }


    @Override
    protected void onRestart() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                desbloquearEtapas(listaEtapas);
            }
        }, 1);

        super.onRestart();
    }

    protected void accessViews() {
        // ADICIONANDO TUDO O QUE HÁ NO NO VETOR ETAPAS NA LISTA
        listaEtapas = new ArrayList<LinearLayout>();
        listaEtapas.addAll(Arrays.asList(etapas));

        listaBarraInferiorEtapas = new ArrayList<TextView>();
        listaBarraInferiorEtapas.addAll(Arrays.asList(barraInferiorEtapas));

        listaTituloEtapas = new ArrayList<TextView>();
        listaTituloEtapas.addAll(Arrays.asList(tituloEtapas) );
    }

    protected void desbloquearEtapas(List<LinearLayout> listaEtapas) {
        for(int i = 0 ; i < DB_PROGRESSO.verificaProgressoEtapa(moduloAtual); i++) {


            // ENCHENDO OS VETORES COM OS OBJETOS
            // A CADA LOOP ELE VAI LIBERAR UMA ETAPA. ATÉ QUE I SEJA IGUAL O PROGRESSO
            etapas[i] = listaEtapas.get(i);
            barraInferiorEtapas[i] = listaBarraInferiorEtapas.get(i);
            tituloEtapas[i] = listaTituloEtapas.get(i);

            tituloEtapas[i].setTextColor(Color.BLACK);

            // TROCAR A COR DA BORDA
            etapas[i].setBackgroundResource(R.drawable.borda_etapa_desbloqueada);

            // SUMIR COM O CADEADO
            barraInferiorEtapas[i].setCompoundDrawables(null, null, null, null);
            barraInferiorEtapas[i].setTextColor(Color.WHITE);

            // TROCAR A COR DE FUNDO
            barraInferiorEtapas[i].setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

    }
    

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // MÉTODO QUE INVOCA A JANELA DE ETAPA BLOQUEADA
    protected void alertaEtapaBloqueada() {
        alerta.alertDialogBloqueado("Etapa Bloqueada", "Termine as etapas anteriores para desbloquear essa");
    }
}
