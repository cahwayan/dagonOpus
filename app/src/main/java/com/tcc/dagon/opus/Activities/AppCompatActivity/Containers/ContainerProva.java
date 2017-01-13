package com.tcc.dagon.opus.Activities.AppCompatActivity.Containers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.Activities.Fragments.Exercicios.CompletarProva;
import com.tcc.dagon.opus.Activities.Fragments.Exercicios.QuestaoProva;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;


/**
 * Created by cahwayan on 14/11/2016.
 */


public class ContainerProva extends ContainerEtapa implements QuestaoProva.OnHeadlineSelectedListener, CompletarProva.OnHeadlineSelectedListener {

    private ImageView vida01, vida02, vida03, vida04, vida05;

    private int contagemVidas;

    Context context = this;

    GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_prova);

        this.tituloEtapa = getIntent().getStringExtra("tituloEtapa");
        this.moduloAtual = getIntent().getIntExtra("moduloAtual", 0);
        this.etapaAtual  = getIntent().getIntExtra("etapaAtual", 0);

        super.instanciaObjetos();
        this.accessViews();
        this.setContagemVidas(5);
        super.bloquearLicoes();
        this.desbloquearLicoes();
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        switch(position) {
            case 5:
                contagemVidas = 5;
                break;
            case 4:
                contagemVidas = 4;
                break;
            case 3:
                contagemVidas = 3;
                break;
            case 2:
                contagemVidas = 2;
                break;
            case 1:
                contagemVidas = 1;
                break;
            case 0:
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Toast.makeText(context, "Você perdeu todas as vidas! Tente de novo.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), retornarTelaEtapas(moduloAtual)));
                        finish();
                    }
                }, 1500);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if(!preferencias.lerFlagBoolean(GerenciadorSharedPreferences.NomePreferencia.lerFlagProva(moduloAtual))) {
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, 1);
        }

        super.onDestroy();
    }

    // método que constrói a janela de alerta ao apertar o back button
    public void alertDialogSairProva(String mensagem, DialogInterface.OnClickListener listenerOnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensagem).setPositiveButton("Sim", listenerOnClick)
                .setNegativeButton("Não", listenerOnClick).show();
    }



    @Override
    protected void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                for(int i = 0; i <= progresso - 1; i += 1) {
                    if(mTabLayout.getTabAt(i) != null) {
                        mTabLayout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                    }
                }
                break;
        }

        if(preferencias.lerFlagBoolean(NomePreferencia.lerFlagProva(moduloAtual))) {
            for(int i = 0; i <= progresso - 1; i += 1) {
                if(mTabLayout.getTabAt(i) != null) {
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
            }
        }
    }

    public ImageView getVida01() {
        return this.vida01;
    }

    public ImageView getVida02() {
        return this.vida02;
    }

    public ImageView getVida03() {
        return this.vida03;
    }

    public ImageView getVida04() {
        return this.vida04;
    }

    public ImageView getVida05() {
        return this.vida05;
    }

    public int getContagemVidas() {
        return this.contagemVidas;
    }

    public void setContagemVidas(int contagemVidas) {
        this.contagemVidas = contagemVidas;
    }

    @Override
    public void onBackPressed() {
        alertDialogSairProva("Deseja mesmo sair da prova? Se não tiver completado ela ainda, seu progresso será reiniciado!",
                listenerDialogClickProva);
    }

    // MENSAGEM DE ALERTA AO CLICAR NO BACK BUTTON
    DialogInterface.OnClickListener listenerDialogClickProva = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    startActivity(new Intent(getApplicationContext(), retornarTelaEtapas(moduloAtual)));
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

    protected Class retornarTelaEtapas(int numeroModulo) {
        switch(numeroModulo) {
            case 1: return EtapasModulo1Activity.class;
          /*  case 2: return EtapasModulo2Activity.class;
            case 3: return EtapasModulo3Activity.class;
            case 4: return EtapasModulo4Activity.class;
            case 5: return EtapasModulo5Activity.class;
            case 6: return EtapasModulo6Activity.class;*/
            default: return null;
        }
    }

    protected void accessViews() {
        super.accessViews();
        this.vida01 = (ImageView) findViewById(R.id.vida01);
        this.vida02 = (ImageView) findViewById(R.id.vida02);
        this.vida03 = (ImageView) findViewById(R.id.vida03);
        this.vida04 = (ImageView) findViewById(R.id.vida04);
        this.vida05 = (ImageView) findViewById(R.id.vida05);
    }



}
