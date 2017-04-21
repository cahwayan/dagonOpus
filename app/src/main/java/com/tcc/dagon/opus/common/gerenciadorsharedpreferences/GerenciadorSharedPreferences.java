package com.tcc.dagon.opus.common.gerenciadorsharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.*;

/**
 * Created by cahwayan on 07/12/2016.
 */

public class GerenciadorSharedPreferences implements Preferencias {

    private final String TAG = this.getClass().getSimpleName();

    final SharedPreferences sharedPreferences;

    public GerenciadorSharedPreferences(Context context) {
        this.sharedPreferences = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
    }

    /*MÉTODOS QUE LEEM FLAGS*/
    @Override
    public String lerFlagString(String nomeFlag) {
        String valorFlag = sharedPreferences.getString(nomeFlag, "default");
        Log.d(TAG, " Flag lida: " + nomeFlag + " Valor: " + valorFlag);
        return valorFlag;
    }

    @Override
    public boolean lerFlagBoolean(String nomeFlag) {
        boolean valorFlag = sharedPreferences.getBoolean(nomeFlag, false);
        Log.d(TAG, " Flag lida: " + nomeFlag + " Valor: " + String.valueOf(valorFlag));
        return valorFlag;
    }

    @Override
    public int lerFlagInt(String nomeFlag) {
        int valorFlag = sharedPreferences.getInt(nomeFlag, 0);
        Log.d(TAG, " Flag lida: " + nomeFlag +  " Valor: " + String.valueOf(valorFlag));
        return valorFlag;
    }

    @Override
    public void modFlag(String nomeFlag, String valorFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(nomeFlag, valorFlag);
        editor.apply();
        Log.d(TAG, nomeFlag + " Modificada. Valor: " + valorFlag);
    }

    @Override
    public void modFlag(String nomeFlag, boolean valorFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(nomeFlag, valorFlag);
        editor.apply();
        Log.d(TAG, nomeFlag + " Modificada. Valor: " + valorFlag);
    }

    @Override
    public void modFlag(String nomeFlag, int valorFlag) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(nomeFlag, valorFlag);
        editor.apply();
        Log.d(TAG, nomeFlag + " Modificada. Valor: " + valorFlag);
    }

    @Override
    public String getIdUsuario() {
        return lerFlagString(ID_USUARIO);
    }

    @Override
    public void setIdUsuario(String id) {
        modFlag(ID_USUARIO, id);
    }

    @Override
    public void setTempoEstudo(String tempoEstudo) {
        this.modFlag(TEMPO_ESTUDO, tempoEstudo);
    }

    @Override
    public String getTempoEstudo() {
        return lerFlagString(TEMPO_ESTUDO);
    }

    @Override
    public void setTipoUsuario(String tipoUsuario) {
        modFlag(TIPO_USUARIO, tipoUsuario);
    }

    @Override
    public String getTipoUsuario() {
        return lerFlagString(TIPO_USUARIO);
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
            case MODULO0:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO0);

            case MODULO1:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO1);

            case MODULO2:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO2);

            case MODULO3:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO3);

            case MODULO4:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO4);

            case MODULO5:
                return lerFlagInt(PROGRESSO_ETAPAS_MODULO5);

            default:
                return 0;
        }
    }

    @Override
    public boolean getCompletouProva(int numModuloReferente) {
        switch(numModuloReferente) {
            case MODULO0:
                return lerFlagBoolean(flagProva0);

            case MODULO1:
                return lerFlagBoolean(flagProva1);

            case MODULO2:
                return lerFlagBoolean(flagProva2);

            case MODULO3:
                return lerFlagBoolean(flagProva3);

            case MODULO4:
                return lerFlagBoolean(flagProva4);

            case MODULO5:
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
            case MODULO0:
                return lerFlagString(notaModulo0);
            case MODULO1:
                return lerFlagString(notaModulo1);
            case MODULO2:
                return lerFlagString(notaModulo2);
            case MODULO3:
                return lerFlagString(notaModulo3);
            case MODULO4:
                return lerFlagString(notaModulo4);
            case MODULO5:
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
    public String getEstadoConquista(String idConquista) {
        return lerFlagString(idConquista);
    }

    @Override
    public void liberarConquista(String idConquista) {
        modFlag(idConquista, 1);
    }

    @Override
    public void bloquearConquista(String idConquista) {
        modFlag(idConquista, 0);
    }

    @Override
    public void setConquista(String idConquista, int valor) {
        modFlag(idConquista, valor);
    }

    @Override
    public void setProgressoEtapa(int numModuloReferente, int valor) {

        String flag;

        switch(numModuloReferente) {
            case MODULO0:
                flag = PROGRESSO_ETAPAS_MODULO0;
                break;
            case MODULO1:
                flag = PROGRESSO_ETAPAS_MODULO1;
                break;
            case MODULO2:
                flag = PROGRESSO_ETAPAS_MODULO2;
                break;
            case MODULO3:
                flag = PROGRESSO_ETAPAS_MODULO3;
                break;
            case MODULO4:
                flag = PROGRESSO_ETAPAS_MODULO4;
                break;
            case MODULO5:
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
            case MODULO0:
                flag = flagProva0;
                break;
            case MODULO1:
                flag = flagProva1;
                break;
            case MODULO2:
                flag = flagProva2;
                break;
            case MODULO3:
                flag = flagProva3;
                break;
            case MODULO4:
                flag = flagProva4;
                break;
            case MODULO5:
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
            case MODULO0:
                flag = notaModulo0;
                break;
            case MODULO1:
                flag = notaModulo1;
                break;
            case MODULO2:
                flag = notaModulo2;
                break;
            case MODULO3:
                flag = notaModulo3;
                break;
            case MODULO4:
                flag = notaModulo4;
                break;
            case MODULO5:
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
            case MODULO0:
                flag = PONTOS_MODULO0;
                break;
            case MODULO1:
                flag = PONTOS_MODULO1;
                break;
            case MODULO2:
                flag = PONTOS_MODULO2;
                break;
            case MODULO3:
                flag = PONTOS_MODULO3;
                break;
            case MODULO4:
                flag = PONTOS_MODULO4;
                break;
            case MODULO5:
                flag = PONTOS_MODULO5;
                break;

            default: flag = "defaultPontos";
        }

        return lerFlagInt(flag);
    }

    public void setPontos(int moduloReferente, int pontos) {

        String flag;

        switch (moduloReferente) {
            case MODULO0:
                flag = PONTOS_MODULO0;
                break;
            case MODULO1:
                flag = PONTOS_MODULO1;
                break;
            case MODULO2:
                flag = PONTOS_MODULO2;
                break;
            case MODULO3:
                flag = PONTOS_MODULO3;
                break;
            case MODULO4:
                flag = PONTOS_MODULO4;
                break;
            case MODULO5:
                flag = PONTOS_MODULO5;
                break;

            default: flag = "defaultPontos";
        }

        modFlag(flag, pontos);
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

    public boolean isFlagsSetup() {
        return lerFlagBoolean(Preferencias.isFlagsSetup);
    }

    public void setFlagsSetup(boolean value) {
        modFlag(isFlagsSetup, value);
    }
}
