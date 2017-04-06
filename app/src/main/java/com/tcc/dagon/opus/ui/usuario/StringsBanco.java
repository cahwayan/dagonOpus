package com.tcc.dagon.opus.ui.usuario;

/**
 * Created by charlinho on 04/09/2016.
 */
public abstract class StringsBanco {

    public static final String USUARIO_INTERNO = "0";
    public static final String USUARIO_GOOGLE = "1";

    private static final String scriptGetId                = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindUserId.php";
    private static final String scriptCadastro             = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptCadastro.php";
    private static final String scriptLogin                = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptLogin.php";
    private static final String scriptUsuarioExiste        = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptVerificarUsuarioExiste.php";
    private static final String scriptGetNome              = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindUserNome.php";
    private static final String scriptGetTempoEstudo       = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindTempoEstudo.php";
    private static final String scriptGetCaminhoFoto       = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindEnderecoFoto.php";
    private static final String scriptGetEstadoCertificado = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindEstadoCertificado.php";

    private static final String scriptGetProgresso = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindProgresso.php";

    private static final String certificadoUrl = "http://dagonopus.esy.es/phpAndroid/certificado.php";
    private static final String recuperarSenha = "http://dagonopus.esy.es/phpAndroid/recupera1.php";

    public static String getScriptGetCaminhoFoto() {
        return scriptGetCaminhoFoto;
    }

    public static String getScriptGetProgresso() {
        return scriptGetProgresso;
    }

    public static String getScriptGetEstadoCertificado() {
        return scriptGetEstadoCertificado;
    }

    public static String getScriptGetTempoEstudo() {
        return scriptGetTempoEstudo;
    }

    public static String getScriptGetNome() {
        return scriptGetNome;
    }

    public static String getScriptGetId() {
        return scriptGetId;
    }

    public static String getScriptUsuarioExiste() {
        return scriptUsuarioExiste;
    }

    public static String getScriptCadastro() {
        return scriptCadastro;
    }

    public static String getScriptLogin() {
        return scriptLogin;
    }

    public static String getRecuperarSenha() {
        return recuperarSenha;
    }

    public static String getCertificadoUrl() {
        return certificadoUrl;
    }
}
