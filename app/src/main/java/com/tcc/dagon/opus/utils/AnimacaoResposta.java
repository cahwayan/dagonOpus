package com.tcc.dagon.opus.utils;

import android.os.Handler;
import android.widget.ImageView;

import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 16/01/2017.
 */

public class AnimacaoResposta {

    public static void criarAnimacaoCom(final ImageView imagem) {
        PulseAnimation.create().with(imagem)
                .setDuration(310)
                .setRepeatCount(3)
                .setRepeatMode(PulseAnimation.REVERSE)
                .start();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                imagem.setBackgroundResource(R.drawable.iconevidaprovavazio);
            }
        }, 1000);
    }
}
