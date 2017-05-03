package com.tcc.dagon.opus.network.volleyrequests.usuario;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.tcc.dagon.opus.application.AppController;
import com.tcc.dagon.opus.common.ConexaoChecker;
import com.tcc.dagon.opus.data.sharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.network.volleyrequests.BancoRemoto;

/**
 * Created by cahwayan on 28/04/2017.
 */

/* TODO: Encontrar um lugar para verificar a conexão ANTES de tentar mandar um request para evitar a tentativa de requests desnecessários*/

public class SyncUser extends IntentService implements SyncUserListener {

    private final String TAG = this.getClass().getSimpleName();

    private RequestsUsuario bancoRemoto;

    private final Handler retryRequestHandler = new Handler();

    private int qtdTentativasSync = 0;

    private final int QTD_MAXIMAS_ERRO_RESPOSTA = 3;

    public SyncUser() {
        super("sync_user");
    }

    @Override
    public void onCreate() {

        super.onCreate();

        GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);
        String tipoUsuario = preferencias.getTipoUsuario();
        String idUsuario   = preferencias.getIdUsuario();

        bancoRemoto = new RequestsUsuario(this, tipoUsuario, idUsuario);
        bancoRemoto.setSyncUserListener(this);
    }

    /**
     * Faz um request ao servidor para sincronizar o usuário.
     * Caso não haja conexão, o método aguarda um tempo antes de tentar novamente.
     * Caso o request venha de um cenário de erro de resposta, ele aguarda um tempo antes de tentar novamente.
     * Esse método faz um request simples caso não hajam erros no intent passado por parâmetro e haja internet.
     * e
     * @param intent: Um intent que pode vir com flags de informações
     * */
    @Override
    protected void onHandleIntent(Intent intent) {

        qtdTentativasSync++;

        if(this.qtdTentativasSync > QTD_MAXIMAS_ERRO_RESPOSTA) {
            this.stopSelf();
            return;
        }

        if(ConexaoChecker.verificarSeHaConexaoDisponivel(this)) {

            boolean houveramErros = intent.getBooleanExtra(BancoRemoto.ERRO_RESPOSTA, false);

            Log.d(TAG, "Houveram Erros: " + String.valueOf(houveramErros));

            if(!houveramErros) {
                Log.d(TAG, "Realizando request sem erros . . .");
                bancoRemoto.syncUser();
            } else {
                Log.d(TAG, "Request from error! Retrying  in " + String.valueOf(getTempoTentarNovamente()) + " milis. . . quantidade de tentativas feitas: " + String.valueOf(qtdTentativasSync));
                postNewDelayedSyncRequest();
            }

        } else {
            // Se não houver conexão
            Log.d(TAG, "Sem conexão! Tentando novamente em . . ." + String.valueOf(getTempoTentarNovamente()) + "milis. . .");
            postNewDelayedSyncRequest();
        }
    }

    /**
     * Lida com o sucesso do request de sincronizar o usuário com o banco remoto
     * @param response: a resposta do servidor
     * */
    @Override
    public void onSyncSuccess(String response) {
        if(response.equals("certo")) {
            Log.d(this.getClass().getSimpleName(), "Request sync sucesso: " + response);
            this.stopSelf();
        } else {
            Log.d(this.getClass().getSimpleName(), "Request sync ERRO: " + response);
            onSyncError("retrying ", response);
        }

        AppController.decreaseRequestCount();
    }

    /**
     * Lida com erro de resposta do servidor do request de sincronizar o usuário
     * Caso o request falhe, esse método vai tentar iniciar outra vez o serviço utilizando um intent,
     * mas com uma flag de erro.
     * @param requestTag: o nome do request
     * @param errorResponse: Stack trace do erro
     */
    @Override
    public void onSyncError(String requestTag, String errorResponse) {
        Log.d(this.getClass().getSimpleName(), "Erro de resposta: " + errorResponse);

        final Intent intent = new Intent(this, SyncUser.class);
        intent.putExtra(BancoRemoto.ERRO_RESPOSTA, true);
        onHandleIntent(intent);

        AppController.decreaseRequestCount();
    }

    private void postNewDelayedSyncRequest() {
        // Tenta executar o serviço novamente após trinta segundos
        retryRequestHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bancoRemoto.syncUser();
            }
        }, getTempoTentarNovamente());


    }

    private long getTempoTentarNovamente() {
        // Número base pela qual o Thread multiplica pela quantidade de erros antes de tentar novamente
        final int BASE_ERRO_TEMPO_RESPOSTA = 30000;
        return BASE_ERRO_TEMPO_RESPOSTA * qtdTentativasSync;
    }
}
