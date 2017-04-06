package com.tcc.dagon.opus.network.volleyrequests.usuario;

import org.json.JSONObject;

/**
 * Created by cahwayan on 05/04/2017.
 */

public interface CallbackUsuario {

    void callbackGetId(String tipoUsuario, String id);
    void callbackGetNome(String nome);
    void callbackGetTempoEstudo(String tempoEstudo);
    void callbackGetEnderecoFoto(String endereco);
    void callbackGetEstadoCertificado(String estadoCertificado);
    void callbackGetProgresso(JSONObject progresso);
    void callbackGetPontuacao(JSONObject pontuacao);
    void callbackGetConquistas(JSONObject conquistas);

}
