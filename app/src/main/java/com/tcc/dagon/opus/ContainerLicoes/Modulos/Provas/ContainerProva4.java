package com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.Adapters.Provas.AdapterProva4;
import com.tcc.dagon.opus.ClassesPai.ContainerProva;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo4Activity;

/**
 * Created by cahwayan on 01/12/2016.
 */

public class ContainerProva4 extends ContainerProva {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LAYOUT QUE O CONTAINER VAI PUXAR
        setContentView(R.layout.container_modulo4_prova);
        super.onCreate(savedInstanceState);
        super.instanciaObjetos();
        super.moduloAtual = 4;
        super.etapaAtual = 6;
        accessViews();
        super.bloquearLicoes();
        this.desbloquearLicoes();

        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);

        if(readFlag()) {
            for(int i = 0; i <= progresso - 1; i += 1) {
                if(mTabLayout.getTabAt(i) != null) {
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.alertDialogSairProva("Deseja mesmo sair da prova? Se não tiver completado ela ainda, seu progresso será reiniciado!",
                listenerDialogClickProva);
    }

    // MENSAGEM DE ALERTA AO CLICAR NO BACK BUTTON
    DialogInterface.OnClickListener listenerDialogClickProva = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    finish();
                    startActivity(new Intent(getApplicationContext(), EtapasModulo4Activity.class));
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };



    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarModulo4Prova);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout_modulo4_prova);
        mViewPager = (ViewPager)findViewById(R.id.pager_modulo4_prova);
        mViewPager.setAdapter(new AdapterProva4(getSupportFragmentManager(),
                getResources().getStringArray(R.array.tab_modulo4_prova)));
        mTabLayout.setupWithViewPager(mViewPager);

        vida01 = (ImageView) findViewById(R.id.vida01);
        vida02 = (ImageView) findViewById(R.id.vida02);
        vida03 = (ImageView) findViewById(R.id.vida03);
        vida04 = (ImageView) findViewById(R.id.vida04);
        vida05 = (ImageView) findViewById(R.id.vida05);

        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));

    }

    @Override
    protected void onDestroy() {
        // AO DESTRUIR O OBJETO, O APP VERIFICA SE O USUÁRIO TERMINOU A PROVA ALGUMA VEZ.
        // SE SIM, ELE LIBERA TODAS AS PERGUNTAS, SE NÃO, ELE RESETA
        if(!readFlag()) {
            DB_PROGRESSO.atualizaProgressoLicao(4,6,1);
        }

        super.onDestroy();
    }

    // LER FLAG PARA VER SE O USUARIO JA TERMINOU O TESTE ALGUMA VEZ
    public boolean readFlag() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("completouTeste4", false);
    }
}
