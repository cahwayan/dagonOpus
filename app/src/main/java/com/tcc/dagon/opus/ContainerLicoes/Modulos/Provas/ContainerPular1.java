package com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.tcc.dagon.opus.Aprender.AprenderActivity;
import com.tcc.dagon.opus.ClassesPai.ContainerProva;

/**
 * Created by cahwayan on 01/12/2016.
 */

public class ContainerPular1 extends ContainerProva {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 1;
        super.etapaAtual = 9;
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBackPressed() {
        alertDialogSairProva("Deseja mesmo sair da prova?",
                listenerDialogClickProva);
    }

    // MENSAGEM DE ALERTA AO CLICAR NO BACK BUTTON
    DialogInterface.OnClickListener listenerDialogClickProva = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    startActivity(new Intent(getApplicationContext(), AprenderActivity.class));
                    finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

}
