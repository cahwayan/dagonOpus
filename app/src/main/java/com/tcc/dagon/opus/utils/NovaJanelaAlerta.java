package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.R;

/**
 * Created by Otávio Paulino on 07/10/2016.
 */



public class NovaJanelaAlerta extends AppCompatActivity {
    private Context context;
    Activity activity;
    public NovaJanelaAlerta(Activity activity) {
        this.activity = activity;
    }
    /*JANELA DE ALERTA*/
    public void alertDialogBloqueado(String titulo, String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("OK", null);
        // Icone
        alerta.setIcon(R.drawable.icon_lock);
        alerta.create().show();
    }

    public void alertDialogBloqueadoLicao(String titulo, String mensagem) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(titulo);
        alerta.setMessage(mensagem);
        alerta.setPositiveButton("OK", null);
        // Icone
        alerta.create().show();
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

    public void alertDialogSairProva(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(mensagem).setPositiveButton("Sim", listenerDialogClick)
                .setNegativeButton("Não", listenerDialogClick).show();
    }

    DialogInterface.OnClickListener listenerDialogClickProva = new DialogInterface.OnClickListener() {
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

    public void alertDialogSair(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(mensagem).setPositiveButton("Sim", listenerDialogClick)
                .setNegativeButton("Não", listenerDialogClick).show();
    }


}
