package com.tcc.dagon.opus.ui.curso.container;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.tcc.dagon.opus.R;
//import com.tcc.dagon.opus.ui.etapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.ui.etapas.subclasses.EtapasModulo0;
import com.tcc.dagon.opus.common.AnimacaoVida;
import com.tcc.dagon.opus.data.sharedpreferences.GerenciadorPreferencesComSuporteParaLicoes;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.*;

/**
 * Created by cahwayan on 14/11/2016.
 */
/**/

@EActivity(R.layout.container_prova)
public class ContainerProvaActivity extends ContainerLicoesActivity
        implements ContagemDeVidasListener{

    private String tituloModulo;
    private int qtdEtapas;

    private ImageView[] vidas;

    private static final int VIDA05 = 4;
    private static final int VIDA04 = 3;
    private static final int VIDA03 = 2;
    private static final int VIDA02 = 1;
    private static final int VIDA01 = 0;

    private int contagemVidas;

    Context context = this;

    GerenciadorPreferencesComSuporteParaLicoes preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tituloModulo = getIntent().getStringExtra("tituloModulo");
        qtdEtapas = getIntent().getIntExtra("qtdEtapas", 0);
    }

    @Override
    @AfterViews
    protected void init() {
        super.init();

        preferenceManager = new GerenciadorPreferencesComSuporteParaLicoes(this);
        this.contagemVidas = 4 /* A contagem termina no 0, então são 5 vidas*/;

        vidas = new ImageView[5];

        this.vidas[0] = (ImageView) findViewById(R.id.vida00);
        this.vidas[1] = (ImageView) findViewById(R.id.vida01);
        this.vidas[2] = (ImageView) findViewById(R.id.vida02);
        this.vidas[3] = (ImageView) findViewById(R.id.vida03);
        this.vidas[4] = (ImageView) findViewById(R.id.vida04);
    }

    @Override
    public void setCompletouProva(boolean valor) {
        preferenceManager.setCompletouProva(moduloAtual, true);
    }

    @Override
    public void removerVida() {

        switch (contagemVidas) {
            case 4:
                AnimacaoVida.criarAnimacaoRetirarVida(vidas[VIDA05]);
                break;
            case 3:
                AnimacaoVida.criarAnimacaoRetirarVida(vidas[VIDA04]);
                break;
            case 2:
                AnimacaoVida.criarAnimacaoRetirarVida(vidas[VIDA03]);
                break;
            case 1:
                AnimacaoVida.criarAnimacaoRetirarVida(vidas[VIDA02]);
                break;
            case 0:
                AnimacaoVida.criarAnimacaoRetirarVida(vidas[VIDA01]);
                break;
            default:
                break;
        }

        contagemVidas -= 1;

        if(contagemVidas < 0) {
            gameOver();
        }

    }

    @Override
    public void adicionarVida() {

    }

    @Override
    protected void onDestroy() {
        if(!usuarioJaCompletouAProva()) {
            preferenceManager.setProgressoLicao(moduloAtual, etapaAtual, 0);
        }

        super.onDestroy();
    }


    @Override
    protected void configurarEstadoLicoes() {

        if(usuarioJaCompletouAProva()) {
            // Liberar todas as lições
            for(int i = 0; i <= qtdFragmentos; i++ ) {
                configurarLicaoLiberada(i);
            }

            return;
        }

        super.configurarEstadoLicoes();
    }

    @Override
    protected void configurarLicaoLiberada(int indexLicao) {
        tab_layout.getTabAt(indexLicao).setIcon(R.drawable.icon_pergunta);
        tabStrip.getChildAt(indexLicao).setClickable(true);
        tabStrip.getChildAt(indexLicao).setEnabled(true);
    }

    public ImageView[] getVidas() {
        return this.vidas;
    }

    public int getContagemVidas() {
        return this.contagemVidas;
    }

    protected boolean usuarioJaCompletouAProva() {
        return preferenceManager.getCompletouProva(moduloAtual);
    }

    private void gameOver() {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(context, "Você perdeu todas as vidas! Tente de novo.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), retornarTelaEtapas(moduloAtual));
                i.putExtra("tituloModulo", tituloModulo);
                i.putExtra("qtdEtapas", qtdEtapas);
                i.putExtra("numModulo", moduloAtual);
                startActivity(i);
                finish();
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        alertDialogSairProva("Deseja mesmo sair da prova? Se não tiver completado ela ainda, seu progresso será reiniciado!",
                listenerDialogClickProva);
    }

    // método que constrói a janela de janelaAlerta ao apertar o back button
    public void alertDialogSairProva(String mensagem, DialogInterface.OnClickListener listenerOnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensagem).setPositiveButton("Sim", listenerOnClick)
                .setNegativeButton("Não", listenerOnClick).show();
    }

    // MENSAGEM DE ALERTA AO CLICAR NO BACK BUTTON
    DialogInterface.OnClickListener listenerDialogClickProva = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Intent i = new Intent(getApplicationContext(), retornarTelaEtapas(moduloAtual));
                    i.putExtra("tituloModulo", tituloModulo);
                    i.putExtra("qtdEtapas", qtdEtapas);
                    i.putExtra("numModulo", moduloAtual);
                    startActivity(i);
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
            case 0: return EtapasModulo0.class;
          /*  case 2: return EtapasModulo2Activity.class;
            case 3: return EtapasModulo3Activity.class;
            case 4: return EtapasModulo4Activity.class;
            case 5: return EtapasModulo5Activity.class;
            case 6: return EtapasModulo6Activity.class;*/
            default: return null;
        }
    }

    @Override
    public void setNota(String nota) {
        preferenceManager.setNota(moduloAtual, nota);
    }

    public int getPontuacao() {
        return preferenceManager.getPontos(moduloAtual);
    }

    @Override
    public String calcularNota() {
        int pontuacaoTotal = getPontuacao();
        int qtdPerguntas = getQtdPerguntasModulo();
        Log.d("Pontuacao Total: ", String.valueOf(pontuacaoTotal));
        Log.d("qtdPerguntas: ", String.valueOf(qtdPerguntas));
        String nota;
        double mediaTotal = pontuacaoTotal / qtdPerguntas;
        Log.d("Media total: ", String.valueOf(mediaTotal));

        if(mediaTotal == 1500) {
            nota = "S";
        } else if(mediaTotal >= 1400) {
            nota = "A++";
        } else if(mediaTotal >= 1300) {
            nota = "A+";
        } else if(mediaTotal >= 1200) {
            nota = "A";
        } else if(mediaTotal >= 1100) {
            nota = "B+";
        } else if(mediaTotal >= 1000) {
            nota = "B";
        } else if(mediaTotal >= 900) {
            nota = "C+";
        } else if(mediaTotal >= 800) {
            nota = "C";
        } else if(mediaTotal >= 750) {
            nota = "C-";
        } else if(mediaTotal >= 600) {
            nota = "D";
        } else if(mediaTotal >= 500) {
            nota = "D-";
        } else if(mediaTotal >= 250) {
            nota = "E";
        } else {
            nota = "F";
        }

        Log.d("NOTA: ", nota);

        return nota;
    }

    private int getQtdPerguntasModulo() {

        int qtd = 0;
        
        switch (moduloAtual) {
            case MODULO0:
                qtd = getResources().getInteger(R.integer.qtdPerguntasModulo0);
                break;
            case MODULO1:
                qtd = getResources().getInteger(R.integer.qtdPerguntasModulo1);
                break;
            case MODULO2:
                qtd = getResources().getInteger(R.integer.qtdPerguntasModulo2);
                break;
            case MODULO3:
                qtd = getResources().getInteger(R.integer.qtdPerguntasModulo3);
                break;
            case MODULO4:
                qtd = getResources().getInteger(R.integer.qtdPerguntasModulo3);
                break;
            case MODULO5:
                qtd = getResources().getInteger(R.integer.qtdPerguntasModulo4);
                break;
            default:
                qtd = 0;
                break;
        }

        return qtd;
    }
}
