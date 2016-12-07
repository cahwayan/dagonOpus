package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by cahwayan on 07/12/2016.
 */

public class GerenciadorSharedPreferences extends Activity {

    Context context;

    public GerenciadorSharedPreferences(Context context) {
        this.context = context;

    }

    public static abstract class NomePreferencia {

        /*FLAGS QUE VERIFICAM SE O ALUNO JÁ COMPLETOU A PROVA AO MENOS UMA VEZ*/
        public static final String flagProva1 = "completouTeste1";
        public static final String flagProva2 = "completouTeste2";
        public static final String flagProva3 = "completouTeste3";
        public static final String flagProva4 = "completouTeste4";
        public static final String flagProva5 = "completouTeste5";
        public static final String flagProva6 = "completouTeste6";

        /*FLAGS DA TELA DE CONFIGURAÇÕES*/
        public static final String flagSwitchConfigSom = "botaoSonsChecked";
        public static final String desativarSons = "desativarSons";

        /*FLAG LOGIN*/
        public static final String isLogin = "isLogin";

        /*FLAG NOME USER*/
        public static final String nomeUsuario = "nomeUsuario";

        /*FLAG CAMINHO FOTO*/
        public static final String caminhoFoto = "caminhoFoto";

        public static final String emailUsuario = "emailUsuario";



    }

    /*MÉTODOS QUE ALTERAM FLAGS*/

    public void escreverFlagString(String nomeFlag, String valorFlag ) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nomeFlag, valorFlag);
        editor.apply();
    }

    public void escreverFlagBoolean(String nomeFlag, Boolean valorFlag ) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(nomeFlag, valorFlag);
        editor.apply();
    }

    /*MÉTODOS QUE LEEM FLAGS*/

    public String lerFlagString(String nomeFlag) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(nomeFlag, "default");
    }

    public boolean lerFlagBoolean(String nomeFlag) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(nomeFlag, false);
    }


}
