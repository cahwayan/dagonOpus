package com.tcc.dagon.opus;

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

public class ActivityConfig extends AppCompatActivity {
    private Switch switchSons;
    private Button btnApagarProgresso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

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

            }
        });
    }

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
