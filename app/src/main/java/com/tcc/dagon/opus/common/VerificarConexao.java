package com.tcc.dagon.opus.common;

/**
 * Created by cahwayan on 14/12/2016.
 */

public class VerificarConexao {

    public static Boolean verificarConexao() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
