package com.tcc.dagon.opus.telas.login;

/**
 * Created by charlinho on 04/09/2016.
 */
public class StringsBanco {

    public String getInsereUrl() {
        return insereUrl;
    }

    public static String getLoginUrl() {
        return loginUrl;
    }

    public String getNomeUrl() {
        return nomeUrl;
    }

    public String getInsereGoogle() {

        return insereGoogle; //y //y
    }

    private final String insereUrl;
    private static final String loginUrl = "http://dagonopus.esy.es/phpAndroid/phpTeste/ScriptLogin.php";;
    private final String insereGoogle;
    private final String nomeUrl;
    private final String mostrarUrl;
    public final String usuarioExiste;
    public final String recuperarSenha;
    public final String certificadoUrl;


    public StringsBanco(){
        mostrarUrl = "http://dagonopus.esy.es/phpAndroid/mostrarEstudante.php";
        insereUrl = "http://dagonopus.esy.es/phpAndroid/phpTeste/scriptCadastro.php";
        usuarioExiste = "http://dagonopus.esy.es/phpAndroid/phpTeste/scriptVerificarUsuarioExiste";

        insereGoogle = "http://dagonopus.esy.es/phpAndroid/insereGoogle.php";

        recuperarSenha = "http://dagonopus.esy.es/phpAndroid/recupera1.php";
        nomeUrl = "http://dagonopus.esy.es/phpAndroid/lerNome.php";
        certificadoUrl = "http://dagonopus.esy.es/phpAndroid/certificado.php";

    }


}
