package com.tcc.dagon.opus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo2Activity;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

public class ActivityConfig extends AppCompatActivity {
    private Switch switchSons;
    private Button btnApagarProgresso;
    private GerenciadorBanco DB_PROGRESSO;
    private NovaJanelaAlerta alertaApagar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        alertaApagar = new NovaJanelaAlerta(this);
        DB_PROGRESSO = new GerenciadorBanco(this);

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        switchSons =         (Switch) findViewById(R.id.switchSons);
        btnApagarProgresso = (Button) findViewById(R.id.btnResetarProgresso);

        if(verificarBotaoSonsChecked()) {
            switchSons.setChecked(true);
        }
        listeners();
    }

    private void listeners() {
        switchSons.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchSons.isChecked()) {
                    desativarSons(true);
                    botaoSonsChecked(true);
                } else {
                    desativarSons(false);
                    botaoSonsChecked(false);
                }
            }
        });

        btnApagarProgresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaApagarProgresso("Deseja mesmo sair da prova? Se não tiver completado ela ainda, seu progresso será reiniciado!",
                        listenerDialogClickProva);

            }
        });
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
                    DB_PROGRESSO.atualizaProgressoEtapa(1,1);
                    DB_PROGRESSO.atualizaProgressoEtapa(2,0);
                    DB_PROGRESSO.atualizaProgressoEtapa(3,0);
                    DB_PROGRESSO.atualizaProgressoEtapa(4,0);
                    DB_PROGRESSO.atualizaProgressoEtapa(5,0);
                    DB_PROGRESSO.atualizaProgressoEtapa(6,0);
                    DB_PROGRESSO.atualizaProgressoEtapa(7,0);
                    DB_PROGRESSO.atualizaProgressoEtapa(8,0);

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 1
                    DB_PROGRESSO.atualizaProgressoLicao(1,1,2);
                    DB_PROGRESSO.atualizaProgressoLicao(1,2,1);
                    DB_PROGRESSO.atualizaProgressoLicao(1,3,1);
                    DB_PROGRESSO.atualizaProgressoLicao(1,4,1);
                    DB_PROGRESSO.atualizaProgressoLicao(1,5,1);
                    DB_PROGRESSO.atualizaProgressoLicao(1,6,1);
                    DB_PROGRESSO.atualizaProgressoLicao(1,7,1);
                    DB_PROGRESSO.atualizaProgressoLicao(1,8,1);
                    DB_PROGRESSO.atualizaProgressoLicao(1,9,1);

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 2
                    DB_PROGRESSO.atualizaProgressoLicao(2,1,1);
                    DB_PROGRESSO.atualizaProgressoLicao(2,2,1);
                    DB_PROGRESSO.atualizaProgressoLicao(2,3,1);
                    DB_PROGRESSO.atualizaProgressoLicao(2,4,1);
                    DB_PROGRESSO.atualizaProgressoLicao(2,5,1);
                    DB_PROGRESSO.atualizaProgressoLicao(2,6,1);

                    // RESETAR PROGRESSO LIÇÕES MÓDULO 2
                    DB_PROGRESSO.atualizaProgressoLicao(3,1,1);
                    DB_PROGRESSO.atualizaProgressoLicao(3,2,1);
                    DB_PROGRESSO.atualizaProgressoLicao(3,3,1);

                    Toast.makeText(getApplicationContext(), "Progresso resetado!", Toast.LENGTH_LONG).show();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };


    public void desativarSons(boolean flag) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("desativarSons", flag);
        editor.apply();
    }

    public void botaoSonsChecked(boolean flag) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("botaoSonsChecked", flag);
        editor.apply();
    }

    // LER FLAG PARA VER SE O DESATIVOU SONS
    public boolean verificarBotaoSonsChecked() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("botaoSonsChecked", false);
    }

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
