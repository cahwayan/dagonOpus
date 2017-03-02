package com.tcc.dagon.opus.ui.usuario;

/**
 * Created by charlinho on 04/09/2016.
 */
public abstract class StringsBanco {

    private static final String insereUrl = "http://dagonopus.esy.es/phpAndroid/phpTeste/scriptCadastro.php";
    private static final String loginUrl = "http://dagonopus.esy.es/phpAndroid/phpTeste/ScriptLogin.php";
    private static final String insereGoogle = "http://dagonopus.esy.es/phpAndroid/insereGoogle.php";
    private static final String nomeUrl = "http://dagonopus.esy.es/phpAndroid/lerNome.php";
    private static final String usuarioExiste = "http://dagonopus.esy.es/phpAndroid/phpTeste/scriptVerificarUsuarioExiste.php";
    private static final String recuperarSenha = "http://dagonopus.esy.es/phpAndroid/recupera1.php";
    private static final String certificadoUrl = "http://dagonopus.esy.es/phpAndroid/certificado.php";

    public static String getUsuarioExiste() {
        return usuarioExiste;
    }

    public static String getInsereGoogle() {
        return insereGoogle;
    }

    public static String getInsereUrl() {
        return insereUrl;
    }

    public static String getLoginUrl() {
        return loginUrl;
    }

    public static String getNomeUrl() {
        return nomeUrl;
    }

    public static String getRecuperarSenha() {
        return recuperarSenha;
    }

    public static String getCertificadoUrl() {
        return certificadoUrl;
    }
}
