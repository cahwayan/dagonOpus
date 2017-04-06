package com.tcc.dagon.opus.network.volleyrequests.cadastro;

/**
 * Created by cahwayan on 29/03/2017.
 */

public interface CallbackCadastro {
    void onCadastro(String resultado);
    void onUsuarioExiste(String resultado);

}
