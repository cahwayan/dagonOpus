package com.tcc.dagon.opus.network.volleyrequests.usuario;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tcc.dagon.opus.application.AppController;
import com.tcc.dagon.opus.common.gerenciadorsharedpreferences.GerenciadorSharedPreferences;

import org.json.JSONObject;

/**
 * Created by cahwayan on 21/04/2017.
 */

public class UsuarioListenerImp implements UsuarioListener {

    private boolean houveramErrosAoRestaurarUsuario = false;

    private final String TAG = this.getClass().getSimpleName();
    private Activity activity;
    private GerenciadorSharedPreferences preferencias;
    private ProgressDialog progressDialog;

    public UsuarioListenerImp(Activity activity) {
        this.activity = activity;
        this.preferencias = new GerenciadorSharedPreferences(activity);
    }

    public UsuarioListenerImp(Activity activity, ProgressDialog dialog) {
        this.activity = activity;
        this.preferencias = new GerenciadorSharedPreferences(activity);
        this.progressDialog = dialog;
    }

    public boolean getHouveramErrosAoRestaurarUsuario() {
        return this.houveramErrosAoRestaurarUsuario;
    }

    public void setHouveramErrosAoRestaurarUsuario(boolean b) {
        this.houveramErrosAoRestaurarUsuario = b;
    }

    @Override
    public void onGetId(String id) {
        preferencias.setIdUsuario(id);
        AppController.decreaseRequestCount();
    }

    @Override
    public void onGetNome(String nome) {
        preferencias.setNomeUsuario(nome);
        AppController.decreaseRequestCount();
    }

    @Override
    public void onGetTempoEstudo(String tempoEstudo) {
        preferencias.setTempoEstudo(tempoEstudo);
        AppController.decreaseRequestCount();
    }

    @Override
    public void onGetEnderecoFoto(String endereco) {
        preferencias.setCaminhoFoto(endereco);
        AppController.decreaseRequestCount();
    }

    @Override
    public void onGetEstadoCertificado(String estadoCertificado) {
        if(estadoCertificado.equals("1")) {
            preferencias.setIsCertificadoGerado(true);
        } else if(estadoCertificado.equals("0")) {
            preferencias.setIsCertificadoGerado(false);
        }

        AppController.decreaseRequestCount();
    }

    @Override
    public void onGetProgresso(JSONObject progresso) {
        int progressoModulo = progresso.optInt("PROGRESSO_MODULO");
        int prog_etapas_modulo0 = progresso.optInt("PROGRESSO_ETAPAS_MODULO0");
        int prog_etapas_modulo1 = progresso.optInt("PROGRESSO_ETAPAS_MODULO1");
        int prog_etapas_modulo2 = progresso.optInt("PROGRESSO_ETAPAS_MODULO2");
        int prog_etapas_modulo3 = progresso.optInt("PROGRESSO_ETAPAS_MODULO3");
        int prog_etapas_modulo4 = progresso.optInt("PROGRESSO_ETAPAS_MODULO4");
        int prog_etapas_modulo5 = progresso.optInt("PROGRESSO_ETAPAS_MODULO5");

        preferencias.setProgressoModulo(progressoModulo);
        preferencias.setProgressoEtapa(0, prog_etapas_modulo0);
        preferencias.setProgressoEtapa(1, prog_etapas_modulo1);
        preferencias.setProgressoEtapa(2, prog_etapas_modulo2);
        preferencias.setProgressoEtapa(3, prog_etapas_modulo3);
        preferencias.setProgressoEtapa(4, prog_etapas_modulo4);
        preferencias.setProgressoEtapa(5, prog_etapas_modulo5);

        AppController.decreaseRequestCount();
    }

    @Override
    public void onGetPontuacao(JSONObject pontuacaoGeral) {

        int[] pontuacao = new int[]
                {
                        pontuacaoGeral.optInt("PONTOS_MODULO0"),
                        pontuacaoGeral.optInt("PONTOS_MODULO1"),
                        pontuacaoGeral.optInt("PONTOS_MODULO2"),
                        pontuacaoGeral.optInt("PONTOS_MODULO3"),
                        pontuacaoGeral.optInt("PONTOS_MODULO4"),
                        pontuacaoGeral.optInt("PONTOS_MODULO5")
                };

        for(int i = 0; i < pontuacao.length; i++) {
            preferencias.setPontos(i, pontuacao[i]);
        }

        AppController.decreaseRequestCount();
    }

    @Override
    public void onGetConquistas(JSONObject conquistas) {
        String[] idConquistas = new String[]
                {
                        "CONQ0",
                        "CONQ1",
                        "CONQ2",
                        "CONQ3",
                        "CONQ4",
                        "CONQ5",
                        "CONQ6",
                        "CONQ7",
                        "CONQ8",
                        "CONQ9",
                        "CONQ10",
                        "CONQ11",
                        "CONQ12",
                        "CONQ13",
                        "CONQ14",
                };

        int[] valores = new int[]
                {
                        conquistas.optInt("CONQ0"),
                        conquistas.optInt("CONQ1"),
                        conquistas.optInt("CONQ2"),
                        conquistas.optInt("CONQ3"),
                        conquistas.optInt("CONQ4"),
                        conquistas.optInt("CONQ5"),
                        conquistas.optInt("CONQ6"),
                        conquistas.optInt("CONQ7"),
                        conquistas.optInt("CONQ8"),
                        conquistas.optInt("CONQ9"),
                        conquistas.optInt("CONQ10"),
                        conquistas.optInt("CONQ11"),
                        conquistas.optInt("CONQ12"),
                        conquistas.optInt("CONQ13"),
                        conquistas.optInt("CONQ14")
                };


        for(int i = 0; i < valores.length; i++) {
            preferencias.setConquista(idConquistas[i], valores[i]);
        }


        AppController.decreaseRequestCount();
    }

    @Override
    public void onErrorResponse(String tag, String response) {
        Log.d(TAG, "Erro no request " + tag + "Cancelando requests . . . resposta: " + response);
        AppController.getInstance().cancelPendingRequests("Erro de resposta servidor");

        if(!houveramErrosAoRestaurarUsuario) {
            houveramErrosAoRestaurarUsuario = true;
        }

        hideProgressDialog();
        Toast.makeText(activity, "Ocorreu um erro ao se conectar com a base de dados. Você está conectado?", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpdate(String response) {

    }

    public void hideProgressDialog() {
        if(this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }
}
