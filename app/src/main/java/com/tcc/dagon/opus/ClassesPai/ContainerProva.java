package com.tcc.dagon.opus.ClassesPai;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo2Activity;


/**
 * Created by cahwayan on 14/11/2016.
 */


public class ContainerProva extends ContainerEtapa implements QuestaoProva.OnHeadlineSelectedListener, CompletarProva.OnHeadlineSelectedListener {

    protected ImageView vida01, vida02, vida03, vida04, vida05;

    private int contagemVidas;

    Context context = this;

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
                        finish();
                    }
                }, 1500);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {

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
    }

    public ImageView getVida01() {
        return vida01;
    }

    public ImageView getVida02() {
        return vida02;
    }

    public ImageView getVida03() {
        return vida03;
    }

    public ImageView getVida04() {
        return vida04;
    }

    public ImageView getVida05() {
        return vida05;
    }

    public int getContagemVidas() {
        return this.contagemVidas;
    }

    public void setContagemVidas(int contagemVidas) {
        this.contagemVidas = contagemVidas;
    }

}
