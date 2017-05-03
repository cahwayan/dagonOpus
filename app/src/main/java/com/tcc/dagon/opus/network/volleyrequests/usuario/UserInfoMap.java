package com.tcc.dagon.opus.network.volleyrequests.usuario;

import android.content.Context;

import com.tcc.dagon.opus.data.sharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants;

import java.util.HashMap;

import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO0;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO1;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO2;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO3;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO4;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO5;

/**
 * Created by cahwayan on 28/04/2017.
 */

public class UserInfoMap {

    private final HashMap<String, String> userInfoMap;
    private final GerenciadorSharedPreferences preferencias;

    public UserInfoMap(Context context) {
        preferencias = new GerenciadorSharedPreferences(context);
        userInfoMap = initUserInfoMap();
    }

    private HashMap<String, String> initUserInfoMap() {

        HashMap<String, String> userInfos = new HashMap<>();

        userInfos.put("ID_USUARIO", preferencias.getIdUsuario());
        userInfos.put("TIPO_USUARIO", preferencias.getTipoUsuario());
        userInfos.put("TEMPO_ESTUDO", preferencias.getTempoEstudo());
        userInfos.put("CAMINHO_FOTO", preferencias.getCaminhoFoto());


        //userInfos.put("ESTADO_CERTIFICADO", preferencias.getIsCertificadoGerado());

        userInfos.put("PROGRESSO_MODULO", String.valueOf(preferencias.getProgressoModulo()));
        userInfos.put("PROGRESSO_ETAPAS_MODULO0", String.valueOf(preferencias.getProgressoEtapa(MODULO0)));
        userInfos.put("PROGRESSO_ETAPAS_MODULO1", String.valueOf(preferencias.getProgressoEtapa(MODULO1)));
        userInfos.put("PROGRESSO_ETAPAS_MODULO2", String.valueOf(preferencias.getProgressoEtapa(MODULO2)));
        userInfos.put("PROGRESSO_ETAPAS_MODULO3", String.valueOf(preferencias.getProgressoEtapa(MODULO3)));
        userInfos.put("PROGRESSO_ETAPAS_MODULO4", String.valueOf(preferencias.getProgressoEtapa(MODULO4)));
        userInfos.put("PROGRESSO_ETAPAS_MODULO5", String.valueOf(preferencias.getProgressoEtapa(MODULO5)));

        userInfos.put("PONTOS_MODULO0", String.valueOf(preferencias.getPontos(MODULO0)));
        userInfos.put("PONTOS_MODULO1", String.valueOf(preferencias.getPontos(MODULO1)));
        userInfos.put("PONTOS_MODULO2", String.valueOf(preferencias.getPontos(MODULO2)));
        userInfos.put("PONTOS_MODULO3", String.valueOf(preferencias.getPontos(MODULO3)));
        userInfos.put("PONTOS_MODULO4", String.valueOf(preferencias.getPontos(MODULO4)));
        userInfos.put("PONTOS_MODULO5", String.valueOf(preferencias.getPontos(MODULO5)));

        return userInfos;

    }

    public HashMap<String, String> getUserInfoMap() {
        return userInfoMap;
    }
}
