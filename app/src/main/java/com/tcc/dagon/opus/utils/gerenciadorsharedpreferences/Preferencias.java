package com.tcc.dagon.opus.utils.gerenciadorsharedpreferences;

/**
 * Created by cahwayan on 26/02/2017.
 */

public interface Preferencias {

    /*
      * Início das preferências que controlam o progresso do usuário no aplicativo
      * TIPO: int
    */

    String ID_USUARIO = "idUsuario";
    String TIPO_USUARIO = "tipoUsuario";
    String TEMPO_ESTUDO = "tempoEstudo";

    // Módulos
    String PROGRESSO_MODULO = "progressoModulo";

    // EtapasActivity
    String PROGRESSO_ETAPAS_MODULO0 = "progressoEtapasModulo0";
    String PROGRESSO_ETAPAS_MODULO1 = "progressoEtapasModulo1";
    String PROGRESSO_ETAPAS_MODULO2 = "progressoEtapasModulo2";
    String PROGRESSO_ETAPAS_MODULO3 = "progressoEtapasModulo3";
    String PROGRESSO_ETAPAS_MODULO4 = "progressoEtapasModulo4";
    String PROGRESSO_ETAPAS_MODULO5 = "progressoEtapasModulo5";

    // Flags pontuação
    String PONTOS_MODULO0 = "pontuacaoModulo0";
    String PONTOS_MODULO1 = "pontuacaoModulo1";
    String PONTOS_MODULO2 = "pontuacaoModulo2";
    String PONTOS_MODULO3 = "pontuacaoModulo3";
    String PONTOS_MODULO4 = "pontuacaoModulo4";
    String PONTOS_MODULO5 = "pontuacaoModulo5";
    String PONTOS_MODULO6 = "pontuacaoModulo6";

    // Flag que verifica se as flags foram inicializadas
    String isFlagsSetup = "isFlagsSetup";

    /*
      * Flags da tela de configurações
      * TIPO: boolean
    */
    String flagSwitchConfigSom = "botaoSonsChecked";
    String desativarSons = "desativarSons";

    /*
      * Flag de autenticação
      * TIPO: boolean
    */
    String isLogin = "isLogin";

    /*
      * Guarda o nome do usuário
      * TIPO: String
    */
    String nomeUsuario = "nomeUsuario";

    /*
      * Guarda o caminho da foto do usuário no cartão SD ou Telefone
      * TIPO: String
    */
    String caminhoFoto = "caminhoFoto";

    /*
      * Guarda o email do usuário logado
      * TIPO: String
    */
    String emailUsuario = "emailUsuario";

    /*
      * Guardam se o usuário já completou determinada prova alguma vez
      * TIPO: boolean
    */
    String flagProva0 = "completouTeste0";
    String flagProva1 = "completouTeste1";
    String flagProva2 = "completouTeste2";
    String flagProva3 = "completouTeste3";
    String flagProva4 = "completouTeste4";
    String flagProva5 = "completouTeste5";

    String flagCertificadoGerado = "flagCertificadoGerado";

    /*
      * Guardam a nota do usuário de acordo com a pontuação dele no módulo
       * TIPO: String
    */

    String notaModulo0 = "notaModulo0";
    String notaModulo1 = "notaModulo1";
    String notaModulo2 = "notaModulo2";
    String notaModulo3 = "notaModulo3";
    String notaModulo4 = "notaModulo4";
    String notaModulo5 = "notaModulo5";

    String lerFlagString(String nomeFlag);
    boolean lerFlagBoolean(String nomeFlag);
    int lerFlagInt(String nomeFlag);

    void modFlag(String nomeFlag, String valorFlag);
    void modFlag(String nomeFlag, boolean valorFlag);
    void modFlag(String nomeFlag, int valorFlag);

    int getProgressoModulo();
    int getProgressoEtapa(int numModuloReferente);
    boolean getCompletouProva(int numModuloReferente);
    boolean getSwitchSons();
    boolean getDesativarSons();
    boolean getIsLogin();
    String getNomeUsuario();
    String getNota(int numModuloReferente);
    String getCaminhoFoto();
    String getEmailUsuario();
    boolean getIsCertificadoGerado();

    void setTipoUsuario(String tipoUsuario);
    String getTipoUsuario();

    void setTempoEstudo(String tempoEstudo);
    String getTempoEstudo();

    String getIdUsuario();
    void setIdUsuario(String id);

    void setProgressoModulo(int valor);
    void setProgressoEtapa(int numModuloReferente, int valor);
    void setCompletouProva(int numModuloReferente, boolean valor);
    void setSwitchSons(boolean valor);
    void setDesativarSons(boolean valor);
    void setIsLogin(boolean valor);
    void setNomeUsuario(String nome);
    void setNota(int numModuloReferente, String nota);
    void setCaminhoFoto(String caminho);
    void setEmailUsuario(String email);
    void setIsCertificadoGerado(boolean isCertificadoGerado);
}
