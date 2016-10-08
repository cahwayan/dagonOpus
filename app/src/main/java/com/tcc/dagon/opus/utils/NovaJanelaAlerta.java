package com.tcc.dagon.opus.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.R;

/**
 * Created by Otávio Paulino on 07/10/2016.
 */



public class NovaJanelaAlerta extends AppCompatActivity {
    private Context context;
    public NovaJanelaAlerta() {

    }
    /*JANELA DE ALERTA*/
    public void alertDialogBloqueado(String titulo, String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("OK", null);
        // Icone
        alerta.setIcon(R.drawable.icon_lock);
        alerta.create().show();
    }
}
