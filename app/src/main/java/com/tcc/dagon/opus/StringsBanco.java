package com.tcc.dagon.opus;

/**
 * Created by charlinho on 04/09/2016.
 */
public class StringsBanco {

    public String getInsereUrl() {
        return insereUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getNomeUrl() {
        return nomeUrl;
    }

    public String getInsereGoogle() {
        return insereGoogle;
    }

    private final String insereUrl;
    private final String loginUrl;
    private final String insereGoogle;
    private final String nomeUrl;
    private final String mostrarUrl;
    public  final String recuperarSenha;
    final String certificadoUrl;


    public StringsBanco(){
        mostrarUrl = "http://dagonopus.esy.es/phpAndroid/mostrarEstudante.php";
        insereUrl = "http://dagonopus.esy.es/phpAndroid/insere.php";
        insereGoogle = "http://dagonopus.esy.es/phpAndroid/insereGoogle.php";
        loginUrl = "http://dagonopus.esy.es/phpAndroid/login.php";
        recuperarSenha = "http://dagonopus.esy.es/phpAndroid/recupera1.php";
        nomeUrl = "http://dagonopus.esy.es/phpAndroid/lerNome.php";
        certificadoUrl = "http://dagonopus.esy.es/phpAndroid/certificado.php";

    }


}
