package com.tcc.dagon.opus.telas.fragments.exercicios.som;

import android.content.Context;
import android.media.MediaPlayer;

import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 15/03/2017.
 */

public class GerenciadorSons {

    private MediaPlayer somRespostaCerta;
    private MediaPlayer somRespostaErrada;

    public GerenciadorSons(Context context) {
        somRespostaCerta = MediaPlayer.create(context, R.raw.resposta_certa);
        somRespostaErrada = MediaPlayer.create(context, R.raw.resposta_errada);
    }

    public void playSomRespostaCerta() {
        somRespostaCerta.start();
    }

    public void playSomRespostaErrada() {
        somRespostaErrada.start();
    }
}
