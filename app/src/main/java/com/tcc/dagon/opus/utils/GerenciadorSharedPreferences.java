package com.tcc.dagon.opus.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by cahwayan on 07/12/2016.
 */

public class GerenciadorSharedPreferences {

    private final SharedPreferences sharedPreferences;

    public GerenciadorSharedPreferences(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    private abstract static class Preferencias {
        /*
        * FLAGS DA TELA DE CONFIGURAÇÕES
        */
        private static final String flagSwitchConfigSom = "botaoSonsChecked";
        private static final String desativarSons = "desativarSons";

        /*
        FLAG DE LOGIN
        */
        private static final String isLogin = "isLogin";

        /*
        * FLAG NOME USER
        */
        private static final String nomeUsuario = "nomeUsuario";

        /*
        *FLAG CAMINHO FOTO
        */
        private static final String caminhoFoto = "caminhoFoto";

        /*
        *Flag que guarda o email do usuário
        */
        private static final String emailUsuario = "emailUsuario";

        /*
        * FLAGS QUE VERIFICAM SE O ALUNO JÁ COMPLETOU A PROVA AO MENOS UMA VEZ
        */
        private static final String flagProva1 = "completouTeste1";
        private static final String flagProva2 = "completouTeste2";
        private static final String flagProva3 = "completouTeste3";
        private static final String flagProva4 = "completouTeste4";
        private static final String flagProva5 = "completouTeste5";
        private static final String flagProva6 = "completouTeste6";
        private static final String flagCertificadoGerado = "flagCertificadoGerado";


    }

    /*MÉTODOS QUE LEEM FLAGS*/
    public String lerFlagString(String nomeFlag) {
        return sharedPreferences.getString(nomeFlag, "default");
    }

    public boolean lerFlagBoolean(String nomeFlag) {
        return sharedPreferences.getBoolean(nomeFlag, false);
    }

    /*MÉTODOS QUE ALTERAM FLAGS*/
    public void escreverFlagString(String nomeFlag, String valorFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nomeFlag, valorFlag);
        editor.apply();
    }

    public void escreverFlagBoolean(String nomeFlag, Boolean valorFlag ) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(nomeFlag, valorFlag);
        editor.apply();
    }

    public String lerFlagProva(int moduloProva) {
        switch(moduloProva) {
            case 1: return getFlagProva1();
            case 2: return getFlagProva2();
            case 3: return getFlagProva3();
            case 4: return getFlagProva4();
            case 5: return getFlagProva5();
            case 6: return getFlagProva6();
            default: return "default";
        }
    }

    public void escreverFlagProva(int moduloAtual) {
        escreverFlagBoolean(lerFlagProva(moduloAtual), true);
    }

    public boolean somEstaAtivado() {
        return lerFlagBoolean(getDesativarSons());
    }


    public static String getCaminhoFoto() {
        return Preferencias.caminhoFoto;
    }

    public static String getDesativarSons() {
        return Preferencias.desativarSons;
    }

    public static String getEmailUsuario() {
        return Preferencias.emailUsuario;
    }

    public static String getFlagCertificadoGerado() {
        return Preferencias.flagCertificadoGerado;
    }

    public static String getFlagProva1() {
        return Preferencias.flagProva1;
    }

    public static String getFlagProva2() {
        return Preferencias.flagProva2;
    }

    public static String getFlagProva3() {
        return Preferencias.flagProva3;
    }

    public static String getFlagProva4() {
        return Preferencias.flagProva4;
    }

    public static String getFlagProva5() {
        return Preferencias.flagProva5;
    }

    public static String getFlagProva6() {
        return Preferencias.flagProva6;
    }

    public static String getFlagSwitchConfigSom() {
        return Preferencias.flagSwitchConfigSom;
    }

    public static String getIsLogin() {
        return Preferencias.isLogin;
    }

    public static String getNomeUsuario() {
        return Preferencias.nomeUsuario;
    }
}
