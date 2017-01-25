package com.tcc.dagon.opus.telas.aprender.menulateral;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

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
    protected GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void init() {

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

        if(preferencias.lerFlagBoolean(GerenciadorSharedPreferences.NomePreferencia.flagSwitchConfigSom)) {
            switchSons.setChecked(true);
        }

        listeners();

    }

    private void listeners() {
        switchSons.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchSons.isChecked()) {
                    preferencias.escreverFlagBoolean(NomePreferencia.flagSwitchConfigSom, true);
                    preferencias.escreverFlagBoolean(NomePreferencia.desativarSons, true);
                } else {
                    preferencias.escreverFlagBoolean(NomePreferencia.flagSwitchConfigSom, false);
                    preferencias.escreverFlagBoolean(NomePreferencia.desativarSons, false);
                }
            }
        });
    }

    @Click
    void btnResetarProgresso() {
        alertaApagarProgresso("Todo seu progresso será apagado. Tem certeza que deseja fazer isso?",
                listenerDialogClickProva);
    }

    // método que constrói a janela de alerta ao apertar o botão de apagar dados
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

                    // RESETAR PROGRESSO DO MÓDULO
                    DB_PROGRESSO.atualizaProgressoModulo(1);

                    // RESETAR PROGRESSO ETAPAS
                    for(int i=1; i <= 8; ++i) {
                        DB_PROGRESSO.atualizaProgressoEtapa( i , 0);
                    }

                    DB_PROGRESSO.atualizaProgressoEtapa(1,1);

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 1
                    for(int i = 1; i <= 9; ++i) {
                        DB_PROGRESSO.atualizaProgressoLicao(1,i,1);
                    }

                    DB_PROGRESSO.atualizaProgressoLicao(1,1,2);

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 2
                    for(int i = 1; i <= 6; ++i) {
                        DB_PROGRESSO.atualizaProgressoLicao(2,i,1);
                    }

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 3
                    for(int i = 1; i <= 3; ++i) {
                        DB_PROGRESSO.atualizaProgressoLicao(3,i,1);
                    }

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 4

                    for(int i = 1; i <= 6; ++i) {
                        DB_PROGRESSO.atualizaProgressoLicao(4,i,1);
                    }

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 5

                    for(int i = 1; i <= 1; ++i) {
                        DB_PROGRESSO.atualizaProgressoLicao(5,i,1);
                    }


                    // RESETAR PROGRESSO LIÇÕES MÓDULO 6
                    for(int i = 1; i <= 10; ++i) {
                        DB_PROGRESSO.atualizaProgressoLicao(6,i,1);
                    }



                    preferencias.escreverFlagBoolean(NomePreferencia.flagProva1, false);
                    preferencias.escreverFlagBoolean(NomePreferencia.flagProva2, false);

                    preferencias.escreverFlagBoolean(NomePreferencia.flagProva3, false);
                    preferencias.escreverFlagBoolean(NomePreferencia.flagProva4, false);

                    preferencias.escreverFlagBoolean(NomePreferencia.flagProva5, false);
                    preferencias.escreverFlagBoolean(NomePreferencia.flagProva6, false);

                    Toast.makeText(getApplicationContext(), "Progresso resetado!", Toast.LENGTH_LONG).show();
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
