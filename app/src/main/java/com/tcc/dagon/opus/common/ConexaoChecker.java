package com.tcc.dagon.opus.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * Created by cahwayan on 14/12/2016.
 */

public class ConexaoChecker {

    public static final String WIFI = "WIFI";
    public static final String MOBILE = "MOBILE";

    public static Boolean verificarSeHaConexaoDisponivel(Context context) {

        boolean haveMobileConnection = false;
        boolean haveWifiConnection = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo != null) {
            if(networkInfo.getTypeName().equals("WIFI"))
                if(networkInfo.isConnected())
                    haveWifiConnection = true;

            if(networkInfo.getTypeName().equals("MOBILE"))
                if(networkInfo.isConnected())
                    haveMobileConnection = true;
        }


        return haveWifiConnection || haveMobileConnection;

    }

    public static String getTipoConexaoAtiva(Activity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo.getTypeName();

    }
}
