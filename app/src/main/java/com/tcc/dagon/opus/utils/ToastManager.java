package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by cahwayan on 05/02/2017.
 */

public class ToastManager {

    private Context context;

    public ToastManager(Context context) {
        this.context = context;
    }

    public void toastLong(String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }

    public void toastShort(String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }
}
