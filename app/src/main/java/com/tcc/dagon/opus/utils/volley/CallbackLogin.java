package com.tcc.dagon.opus.utils.volley;

import java.sql.Time;

/**
 * Created by cahwayan on 29/03/2017.
 */

public interface CallbackLogin {

    void callbackLogin(String response);
    void callbackNome(String nome);
    void callbackId(String id);
    void callbackTempoEstudo(Time tempoEstudo);
    void callbackEnderecoFoto(String endereco);
    void callbackEstadoCertificado(String estadoCertificado);

}
