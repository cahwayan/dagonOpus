package com.tcc.dagon.opus;

/**
 * Created by charlinho on 04/09/2016.
 */
public class StringsBanco {
    final String insereUrl;
    private final String mostrarUrl;
    final String loginUrl;
    final String insereGoogle;
    final String recuperarSenha;
    final String certificadoUrl;
    final String nomeUrl;
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public static final String FILE_UPLOAD_URL = "http://dagonopus.esy.es/phpAndroid/fileUpload.php";

    StringsBanco(){
        mostrarUrl = "http://dagonopus.esy.es/phpAndroid/mostrarEstudante.php";
        insereUrl = "http://dagonopus.esy.es/phpAndroid/insere.php";
        insereGoogle = "http://dagonopus.esy.es/phpAndroid/insereGoogle.php";
        loginUrl = "http://dagonopus.esy.es/phpAndroid/login.php";
        recuperarSenha = "http://dagonopus.esy.es/phpAndroid/recupera1.php";
        nomeUrl = "http://dagonopus.esy.es/phpAndroid/lerNome.php";
        certificadoUrl = "http://dagonopus.esy.es/phpAndroid/certificado.php";

    }


}
