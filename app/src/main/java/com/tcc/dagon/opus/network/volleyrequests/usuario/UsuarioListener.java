package com.tcc.dagon.opus.network.volleyrequests.usuario;

import org.json.JSONObject;

/**
 * Created by cahwayan on 05/04/2017.
 */

public interface UsuarioListener {

    void onGetId(String id);
    void onGetNome(String nome);
    void onGetTempoEstudo(String tempoEstudo);
    void onGetEnderecoFoto(String endereco);
    void onGetEstadoCertificado(String estadoCertificado);
    void onGetProgresso(JSONObject progresso);
    void onGetPontuacao(JSONObject pontuacao);
    void onGetConquistas(JSONObject conquistas);

    void onErrorResponse(String tag, String error);
    void onUpdate(String response);
}
