package com.tcc.dagon.opus;

/**
 * Created by charlinho on 04/09/2016.
 */
public class StringsBanco {
    final String insereUrl;
    private final String mostrarUrl;
    final String loginUrl;
    final String insereGoogle;

    StringsBanco(){
        mostrarUrl = "http://dagonopus.esy.es/phpAndroid/mostrarEstudante.php";
        insereUrl = "http://dagonopus.esy.es/phpAndroid/insere.php";
        insereGoogle = "http://dagonopus.esy.es/phpAndroid/insereGoogle.php";
        loginUrl = "http://dagonopus.esy.es/phpAndroid/login.php";
    }


}
