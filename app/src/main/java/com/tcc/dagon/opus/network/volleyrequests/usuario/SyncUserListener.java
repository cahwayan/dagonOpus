package com.tcc.dagon.opus.network.volleyrequests.usuario;

/**
 * Created by cahwayan on 28/04/2017.
 */

public interface SyncUserListener {

    void onSyncSuccess(String response);
    void onSyncError(String requestTag, String errorResponse);
}
