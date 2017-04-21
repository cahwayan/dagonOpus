package com.tcc.dagon.opus.network.volleyrequests;

import com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants;

/**
 * Created by Caique on 04/09/2016.
 */
public abstract class BancoRemoto {

    public static final String USUARIO_INTERNO = "0";
    public static final String USUARIO_GOOGLE = "1";

    private static final String scriptGetId                = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindUserId.php";
    private static final String scriptCadastro             = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptCadastro.php";
    private static final String scriptLogin                = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptLogin.php";
    private static final String scriptUsuarioExiste        = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptVerificarUsuarioExiste.php";
    private static final String scriptGetNome              = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptFindUserNome.php";

    private static final String scriptTempoEstudo = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptTempoEstudo.php";
    private static final String scriptCaminhoFoto = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptEnderecoFoto.php";
    private static final String scriptEstadoCertificado = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptEstadoCertificado.php";
    private static final String scriptProgresso = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptProgresso.php";
    private static final String scriptPontuacao = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptPontuacao.php";
    private static final String scriptConquistas = "http://dagonopus.esy.es/phpAndroid/phpTeste/scripts/scriptConquistas.php";

    private static final String certificadoUrl = "http://dagonopus.esy.es/phpAndroid/certificado.php";
    private static final String recuperarSenha = "http://dagonopus.esy.es/phpAndroid/recupera1.php";

    public static String getScriptCaminhoFoto() {
        return scriptCaminhoFoto;
    }

    public static String getScriptPontuacao() {
        return scriptPontuacao;
    }

    public static String getScriptConquistas() {
        return scriptConquistas;
    }

    public static String getScriptProgresso() {
        return scriptProgresso;
    }

    public static String getScriptEstadoCertificado() {
        return scriptEstadoCertificado;
    }

    public static String getScriptTempoEstudo() {
        return scriptTempoEstudo;
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

    public static class Action {

        public static final String SELECT    = "SELECT";
        public static final String SELECTALL = "SELECTALL";

        public static final String UPDATE    = "UPDATE";
        public static final String UPDATEALL = "UPDATEALL";

        public static final String DELETE    = "DELETE";
        public static final String DELETEALL = "DELETEALL";

    }

    public static class Tabelas {

        public static final String COL_ID = "ID_USUARIO";

        public static class Pontuacao {
            private static final String COL_PONTUACAO_MODULO0 = "PONTOS_MODULO0";
            private static final String COL_PONTUACAO_MODULO1 = "PONTOS_MODULO1";
            private static final String COL_PONTUACAO_MODULO2 = "PONTOS_MODULO2";
            private static final String COL_PONTUACAO_MODULO3 = "PONTOS_MODULO3";
            private static final String COL_PONTUACAO_MODULO4 = "PONTOS_MODULO4";
            private static final String COL_PONTUACAO_MODULO5 = "PONTOS_MODULO5";

            public static String getColunaPontuacao(int numModulo) {
                if(numModulo >= ModuloConstants.MODULO0 && numModulo <= ModuloConstants.MODULO5) {
                    switch(numModulo) {
                        case ModuloConstants.MODULO0:
                            return COL_PONTUACAO_MODULO0;
                        case ModuloConstants.MODULO1:
                            return COL_PONTUACAO_MODULO1;
                        case ModuloConstants.MODULO2:
                            return COL_PONTUACAO_MODULO2;
                        case ModuloConstants.MODULO3:
                            return COL_PONTUACAO_MODULO3;
                        case ModuloConstants.MODULO4:
                            return COL_PONTUACAO_MODULO4;
                        case ModuloConstants.MODULO5:
                            return COL_PONTUACAO_MODULO5;
                    }
                }

                return "Erro ao selecionar coluna";

            }
        }

        public static class Progresso {

            public static final String COL_PROGRESSO_MODULOS = "PROGRESSO_MODULO";

            static final String COL_PROGRESSO_ETAPAS_MODULO0 = "PROGRESSO_ETAPAS_MODULO0";
            static final String COL_PROGRESSO_ETAPAS_MODULO1 = "PROGRESSO_ETAPAS_MODULO1";
            static final String COL_PROGRESSO_ETAPAS_MODULO2 = "PROGRESSO_ETAPAS_MODULO2";
            static final String COL_PROGRESSO_ETAPAS_MODULO3 = "PROGRESSO_ETAPAS_MODULO3";
            static final String COL_PROGRESSO_ETAPAS_MODULO4 = "PROGRESSO_ETAPAS_MODULO4";
            static final String COL_PROGRESSO_ETAPAS_MODULO5 = "PROGRESSO_ETAPAS_MODULO5";

            public static String getColunaProgressoEtapas(int numModulo) {
                if(numModulo >= ModuloConstants.MODULO0 && numModulo <= ModuloConstants.MODULO5) {
                    switch(numModulo) {
                        case ModuloConstants.MODULO0:
                            return COL_PROGRESSO_ETAPAS_MODULO0;
                        case ModuloConstants.MODULO1:
                            return COL_PROGRESSO_ETAPAS_MODULO1;
                        case ModuloConstants.MODULO2:
                            return COL_PROGRESSO_ETAPAS_MODULO2;
                        case ModuloConstants.MODULO3:
                            return COL_PROGRESSO_ETAPAS_MODULO3;
                        case ModuloConstants.MODULO4:
                            return COL_PROGRESSO_ETAPAS_MODULO4;
                        case ModuloConstants.MODULO5:
                            return COL_PROGRESSO_ETAPAS_MODULO5;
                    }
                }

                return "Erro ao selecionar coluna";

            }

        }

        public static class Conquistas {

            static final String COL_CONQ0 = "CONQ0";
            static final String COL_CONQ1 = "CONQ1";
            static final String COL_CONQ2 = "CONQ2";
            static final String COL_CONQ3 = "CONQ3";
            static final String COL_CONQ4 = "CONQ4";

            static final String COL_CONQ5 = "CONQ5";
            static final String COL_CONQ6 = "CONQ6";
            static final String COL_CONQ7 = "CONQ7";
            static final String COL_CONQ8 = "CONQ8";
            static final String COL_CONQ9 = "CONQ9";

            static final String COL_CONQ10 = "CONQ10";
            static final String COL_CONQ11 = "CONQ11";
            static final String COL_CONQ12 = "CONQ12";
            static final String COL_CONQ13 = "CONQ13";
            static final String COL_CONQ14 = "CONQ14";

            public static String getColunaConquista(int idConquista) {

                switch(idConquista) {
                    case 0:
                        return COL_CONQ0;
                    case 1:
                        return COL_CONQ1;
                    case 2:
                        return COL_CONQ2;
                    case 3:
                        return COL_CONQ3;
                    case 4:
                        return COL_CONQ4;
                    case 5:
                        return COL_CONQ5;
                    case 6:
                        return COL_CONQ6;
                    case 7:
                        return COL_CONQ7;
                    case 8:
                        return COL_CONQ8;
                    case 9:
                        return COL_CONQ9;
                    case 10:
                        return COL_CONQ10;
                    case 11:
                        return COL_CONQ11;
                    case 12:
                        return COL_CONQ12;
                    case 13:
                        return COL_CONQ13;
                    case 14:
                        return COL_CONQ14;
                }

                return "";
            }


        }

        public static class TempoEstudo {

            public static final String COL_TEMPO_ESTUDO = "TEMPO_ESTUDO";

        }

        public static class EndFoto {

            public static final String COL_END_FOTO = "END_FOTO";

        }

        public static class Certificado {

            public static final String COL_ESTADO_CERTIFICADO = "CERTIFICADO_GERADO";

        }
    }

}
