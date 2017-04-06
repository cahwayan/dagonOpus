package com.tcc.dagon.opus.utils.volley;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;

/**
 * Created by cahwayan on 29/03/2017.
 */

public interface CallbackLogin {

    void callbackLoginInterno(String response);

    void callbackGetId(String tipoUsuario, String id);
    void callbackGetNome(String nome);
    void callbackGetTempoEstudo(String tempoEstudo);
    void callbackGetEnderecoFoto(String endereco);
    void callbackGetEstadoCertificado(String estadoCertificado);
    void callbackGetProgresso(JSONObject progresso);
    void callbackGetPontuacao(JSONObject endereco);
    void callbackGetConquistas(JSONObject estadoCertificado);

}
