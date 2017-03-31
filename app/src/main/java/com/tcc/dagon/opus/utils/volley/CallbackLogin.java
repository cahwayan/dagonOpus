package com.tcc.dagon.opus.utils.volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;

/**
 * Created by cahwayan on 29/03/2017.
 */

public interface CallbackLogin {

    void callbackLoginInterno(String response);
    void callbackGetNome(String nome);
    void callbackGetId(String tipoUsuario, String id);
    void callbackGetTempoEstudo(String tempoEstudo);
    void callbackGetEnderecoFoto(String endereco);
    void callbackGetEstadoCertificado(String estadoCertificado);

    void callbackGetProgresso(JSONObject progresso);
    void callbackGetPontuacao(JSONObject endereco);
    void callbackGetConquistas(JSONObject estadoCertificado);

}
