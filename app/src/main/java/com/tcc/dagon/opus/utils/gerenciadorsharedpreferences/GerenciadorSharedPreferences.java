package com.tcc.dagon.opus.utils.gerenciadorsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.tcc.dagon.opus.ui.aprender.ModuloCurso;

/**
 * Created by cahwayan on 07/12/2016.
 */

public class GerenciadorSharedPreferences implements Preferencias {

    final SharedPreferences sharedPreferences;

    public GerenciadorSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    /*MÉTODOS QUE LEEM FLAGS*/
    @Override
    public String lerFlagString(String nomeFlag) {
        return sharedPreferences.getString(nomeFlag, "default");
    }

    @Override
    public boolean lerFlagBoolean(String nomeFlag) {
        return sharedPreferences.getBoolean(nomeFlag, false);
    }

    @Override
    public int lerFlagInt(String nomeFlag) {
        return sharedPreferences.getInt(nomeFlag, 0);
    }

    @Override
    public void modFlag(String nomeFlag, String valorFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nomeFlag, valorFlag);
        editor.apply();
    }

    @Override
    public void modFlag(String nomeFlag, boolean valorFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(nomeFlag, valorFlag);
        editor.apply();
    }

    @Override
    public void modFlag(String nomeFlag, int valorFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(nomeFlag, valorFlag);
        editor.apply();
    }

    @Override
    public String getCaminhoFoto() {
        return lerFlagString(caminhoFoto);
    }

    @Override
    public int getProgressoModulo() {
        return lerFlagInt(PROGRESSO_MODULO);
    }

    @Override
    public int getProgressoEtapa(int numModuloReferente) {
        switch(numModuloReferente) {
            case ModuloCurso.MODULO0:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO0);

            case ModuloCurso.MODULO1:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO1);

            case ModuloCurso.MODULO2:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO2);

            case ModuloCurso.MODULO3:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO3);

            case ModuloCurso.MODULO4:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO4);

            case ModuloCurso.MODULO5:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO5);

            default:
                return 0;
        }
    }

    @Override
    public boolean getCompletouProva(int numModuloReferente) {
        switch(numModuloReferente) {
            case ModuloCurso.MODULO0:
                return lerFlagBoolean(flagProva0);

            case ModuloCurso.MODULO1:
                return lerFlagBoolean(flagProva1);

            case ModuloCurso.MODULO2:
                return lerFlagBoolean(flagProva2);

            case ModuloCurso.MODULO3:
                return lerFlagBoolean(flagProva3);

            case ModuloCurso.MODULO4:
                return lerFlagBoolean(flagProva4);

            case ModuloCurso.MODULO5:
                return lerFlagBoolean(flagProva5);

            default:
                return false;
        }
    }

    @Override
    public boolean getSwitchSons() {
        return lerFlagBoolean(flagSwitchConfigSom);
    }

    @Override
    public boolean getDesativarSons() {
        return lerFlagBoolean(desativarSons);
    }

    @Override
    public boolean getIsLogin() {
        return lerFlagBoolean(isLogin);
    }

    @Override
    public String getNomeUsuario() {
        return lerFlagString(nomeUsuario);
    }

    @Override
    public String getNota(int numModuloReferente) {
        switch(numModuloReferente) {
            case ModuloCurso.MODULO0:
                return lerFlagString(notaModulo0);
            case ModuloCurso.MODULO1:
                return lerFlagString(notaModulo1);
            case ModuloCurso.MODULO2:
                return lerFlagString(notaModulo2);
            case ModuloCurso.MODULO3:
                return lerFlagString(notaModulo3);
            case ModuloCurso.MODULO4:
                return lerFlagString(notaModulo4);
            case ModuloCurso.MODULO5:
                return lerFlagString(notaModulo5);
            default:
                return "numModuloInvalido";
        }
    }

    @Override
    public String getEmailUsuario() {
        return lerFlagString(emailUsuario);
    }

    @Override
    public boolean getIsCertificadoGerado() {
        return lerFlagBoolean(flagCertificadoGerado);
    }

    @Override
    public void setProgressoModulo(int valor) {
        modFlag(PROGRESSO_MODULO, valor);
    }

    @Override
    public void setProgressoEtapa(int numModuloReferente, int valor) {

        String flag;

        switch(numModuloReferente) {
            case ModuloCurso.MODULO0:
                flag = PROGRESSO_ETAPAS_MODULO0;
                break;
            case ModuloCurso.MODULO1:
                flag = PROGRESSO_ETAPAS_MODULO1;
                break;
            case ModuloCurso.MODULO2:
                flag = PROGRESSO_ETAPAS_MODULO2;
                break;
            case ModuloCurso.MODULO3:
                flag = PROGRESSO_ETAPAS_MODULO3;
                break;
            case ModuloCurso.MODULO4:
                flag = PROGRESSO_ETAPAS_MODULO4;
                break;
            case ModuloCurso.MODULO5:
                flag = PROGRESSO_ETAPAS_MODULO5;
                break;
            default:
                flag = "defaultProgEtapa";
                break;
        }

        modFlag(flag, valor);
    }

    @Override
    public void setCompletouProva(int numModuloReferente, boolean valor) {

        String flag;

        switch(numModuloReferente) {
            case ModuloCurso.MODULO0:
                flag = flagProva0;
                break;
            case ModuloCurso.MODULO1:
                flag = flagProva1;
                break;
            case ModuloCurso.MODULO2:
                flag = flagProva2;
                break;
            case ModuloCurso.MODULO3:
                flag = flagProva3;
                break;
            case ModuloCurso.MODULO4:
                flag = flagProva4;
                break;
            case ModuloCurso.MODULO5:
                flag = flagProva5;
                break;
            default:
                flag = "defaultProva";
                break;
        }

        modFlag(flag, valor);
    }

    @Override
    public void setSwitchSons(boolean valor) {
        modFlag(flagSwitchConfigSom, valor);
    }

    @Override
    public void setDesativarSons(boolean valor) {
        modFlag(desativarSons, valor);
    }

    @Override
    public void setIsLogin(boolean valor) {
        modFlag(isLogin, valor);
    }

    @Override
    public void setNomeUsuario(String nome) {
        modFlag(nomeUsuario, nome);
    }

    @Override
    public void setNota(int numModuloReferente, String nota) {

        String flag;

        switch (numModuloReferente) {
            case ModuloCurso.MODULO0:
                flag = notaModulo0;
                break;
            case ModuloCurso.MODULO1:
                flag = notaModulo1;
                break;
            case ModuloCurso.MODULO2:
                flag = notaModulo2;
                break;
            case ModuloCurso.MODULO3:
                flag = notaModulo3;
                break;
            case ModuloCurso.MODULO4:
                flag = notaModulo4;
                break;
            case ModuloCurso.MODULO5:
                flag = notaModulo5;
                break;
            default:
                flag = "defaultNota";
                break;
        }

        modFlag(flag, nota);
    }

    public int getPontos(int moduloReferente) {

        String flag;

        switch (moduloReferente) {
            case ModuloCurso.MODULO0:
                flag = PONTOS_MODULO0;
                break;
            case ModuloCurso.MODULO1:
                flag = PONTOS_MODULO1;
                break;
            case ModuloCurso.MODULO2:
                flag = PONTOS_MODULO2;
                break;
            case ModuloCurso.MODULO3:
                flag = PONTOS_MODULO3;
                break;
            case ModuloCurso.MODULO4:
                flag = PONTOS_MODULO4;
                break;
            case ModuloCurso.MODULO5:
                flag = PONTOS_MODULO5;
                break;

            default: flag = "defaultPontos";
        }

        return lerFlagInt(flag);
    }

    @Override
    public void setCaminhoFoto(String caminho) {
        modFlag(caminhoFoto, caminho);
    }

    @Override
    public void setEmailUsuario(String email) {
        modFlag(emailUsuario, email);
    }

    @Override
    public void setIsCertificadoGerado(boolean isCertificadoGerado) {
        modFlag(flagCertificadoGerado, isCertificadoGerado);
    }
}
