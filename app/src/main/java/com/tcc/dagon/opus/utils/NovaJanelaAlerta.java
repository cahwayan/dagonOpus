package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import com.tcc.dagon.opus.R;

/**
 * Created by Otávio Paulino on 07/10/2016.
 */



public class NovaJanelaAlerta extends AppCompatActivity {

    Activity activity;

    public NovaJanelaAlerta(Activity activity) {
        this.activity = activity;
    }

    /* Janela alerta que pode ser invocada de maneira estática, passando um context */
    public static void alertDialogBloqueado(Context context, String titulo, String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("OK", null);
        // Icone
        alerta.setIcon(R.drawable.icon_lock);
        alerta.create().show();
    }

    /* Para invocar essa janela de alerta, é necessário instanciar um objeto do tipo NovaJanelaAlerta, e passar uma Activity por parâmetro no construtor */
    public void alertDialogSair(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(mensagem).setPositiveButton("Sim", listenerDialogClick)
                .setNegativeButton("Não", listenerDialogClick).show();
    }

    DialogInterface.OnClickListener listenerDialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    activity.finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };




}
