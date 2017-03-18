package com.tcc.dagon.opus.ui.telasatransferir.aprender.menulateral;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import com.tcc.dagon.opus.utils.ToastManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_config)
public class ActivityConfig extends AppCompatActivity {

    /* VIEWS */
    @ViewById protected Switch switchSons;
    @ViewById protected Button btnResetarProgresso;

    /* OBJETOS */
    protected GerenciadorBanco DB_PROGRESSO;
    protected NovaJanelaAlerta alertaApagar;
    protected GerenciadorSharedPreferences preferencias;
    protected boolean flagDesativarSom;
    protected ToastManager toastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void init() {
        preferencias = new GerenciadorSharedPreferences(this);
        toastManager = new ToastManager(this);
        flagDesativarSom = preferencias.getDesativarSons();

        if(flagDesativarSom) {
            switchSons.setChecked(true);
        } else {
            switchSons.setChecked(false);
        }

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(alertaApagar == null) {
            alertaApagar = new NovaJanelaAlerta(this);
        }

        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
        }

        listeners();

    }

    private void listeners() {
        switchSons.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchSons.isChecked()) {
                    preferencias.setDesativarSons(true);
                } else {
                    preferencias.setDesativarSons(false);
                }
            }
        });
    }

    @Click
    void btnResetarProgresso() {
        alertaApagarProgresso("Todo seu progresso será apagado. Tem certeza que deseja fazer isso?",
                listenerDialogClickProva);
    }

    // método que constrói a janela de janelaAlerta ao apertar o botão de apagar dados
    public void alertaApagarProgresso(String mensagem, DialogInterface.OnClickListener listenerOnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensagem).setPositiveButton("Sim", listenerOnClick)
                .setNegativeButton("Não", listenerOnClick).show();
    }

    // MENSAGEM DE ALERTA AO CLICAR NO BOTÃO APAGAR
    DialogInterface.OnClickListener listenerDialogClickProva = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:



                    preferencias.setCompletouProva(0, false);
                    preferencias.setCompletouProva(1, false);
                    preferencias.setCompletouProva(2, false);
                    preferencias.setCompletouProva(3, false);
                    preferencias.setCompletouProva(4, false);
                    preferencias.setCompletouProva(5, false);

                    toastManager.toastLong("Progresso resetado!");
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };


    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
