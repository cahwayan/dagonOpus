package com.tcc.dagon.opus.utils;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by cahwayan on 15/03/2017.
 */

public class ViewController {

    public static void initPulseAnimation(ImageView image) {
        // ANIMAÇÃO RESPOSTA CERTA
        if(image != null) {
            image.setVisibility(View.VISIBLE);
            PulseAnimation.create().with(image)
                    .setDuration(310)
                    .setRepeatCount(5)
                    .setRepeatMode(PulseAnimation.REVERSE)
                    .start();
        }

    }

    public static void setInvisible(View view) {
        if(view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public static void setVisible(View view) {
        if(view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }
}
