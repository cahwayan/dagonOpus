package com.tcc.dagon.opus.utils.volley;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tcc.dagon.opus.ui.usuario.StringsBanco;
import java.util.HashMap;
import java.util.Map;

import com.tcc.dagon.opus.ui.aprender.AprenderActivity_;
import com.tcc.dagon.opus.utils.ToastManager;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;

/**
 * Created by cahwayan on 22/12/2016.
 */ /**/

public class VolleyRequest {

    private final String TAG = this.getClass().getSimpleName();

    final GerenciadorSharedPreferences preferencesManager;
    RequestQueue requestQueue;
    ToastManager toastManager;

    public VolleyRequest(Context context) {
        preferencesManager = new GerenciadorSharedPreferences(context);
        requestQueue = Volley.newRequestQueue(context);
        toastManager = new ToastManager(context);
    }


}
