package com.tcc.dagon.opus.databases;

import android.provider.BaseColumns;

/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE GUARDA OS NOMES DAS COLUNAS E DAS TABELAS DO BANCO
 */
final class ProgressoUsuario {

    // CONSTRUTOR VAZIO PARA EVITAR QUE ALGUÉM ACIDENTALMENTE INSTANCIE ESSA CLASSE
    public ProgressoUsuario() {
    }


    static abstract class Progresso implements BaseColumns {
        // TABELAS DO BANCO DE DADOS
        static final String TABELA_PROGRESSO = "TD_PROG_USUARIO";
        static final String TABELA_PROGRESSO_LICOES = "TD_PROG_LICOES_MODULO";
        static final String TABELA_PERGUNTAS_MODULO1 = "TD_QUESTOES_MODULO1";
        static final String TABELA_PERGUNTAS_MODULO2 = "TD_QUESTOES_MODULO2";
        static final String TABELA_PERGUNTAS_MODULO3 = "TD_QUESTOES_MODULO3";
        static final String TABELA_PERGUNTAS_MODULO4 = "TD_QUESTOES_MODULO4";
        static final String TABELA_PERGUNTAS_MODULO5 = "TD_QUESTOES_MODULO5";
        static final String TABELA_PERGUNTAS_MODULO6 = "TD_QUESTOES_MODULO6";
        static final String TABELA_PERGUNTAS_MODULO7 = "TD_QUESTOES_MODULO7";
        static final String TABELA_PERGUNTAS_MODULO8 = "TD_QUESTOES_MODULO8";

        // COLUNA DO ID
        static final String COLUNA_ID = "_id";
        // COLUNA DA TABELA DO PROGRESSO DOS MÓDULOS
        static final String COLUNA_MODULO = "PROG_MODULO";

        // COLUNAS DO PROGRESSO DAS ETAPAS DOS MÓDULOS
        static final String COLUNA_ETAPA1 = "PROG_ETAPA_MODULO1";
        static final String COLUNA_ETAPA2 = "PROG_ETAPA_MODULO2";
        static final String COLUNA_ETAPA3 = "PROG_ETAPA_MODULO3";
        static final String COLUNA_ETAPA4 = "PROG_ETAPA_MODULO4";
        static final String COLUNA_ETAPA5 = "PROG_ETAPA_MODULO5";
        static final String COLUNA_ETAPA6 = "PROG_ETAPA_MODULO6";
        static final String COLUNA_ETAPA7 = "PROG_ETAPA_MODULO7";
        static final String COLUNA_ETAPA8 = "PROG_ETAPA_MODULO8";

        // COLUNAS DAS LIÇÕES DAS ETAPAS DO MÓDULO 1
        static final String COLUNA_PROG_LICOES_MODULO = "MODULO";
        static final String COLUNA_PROG_LICOES_ETAPA1 = "PROG_LICOES_ETAPA1";
        static final String COLUNA_PROG_LICOES_ETAPA2 = "PROG_LICOES_ETAPA2";
        static final String COLUNA_PROG_LICOES_ETAPA3 = "PROG_LICOES_ETAPA3";
        static final String COLUNA_PROG_LICOES_ETAPA4 = "PROG_LICOES_ETAPA4";
        static final String COLUNA_PROG_LICOES_ETAPA5 = "PROG_LICOES_ETAPA5";
        static final String COLUNA_PROG_LICOES_ETAPA6 = "PROG_LICOES_ETAPA6";
        static final String COLUNA_PROG_LICOES_ETAPA7 = "PROG_LICOES_ETAPA7";
        static final String COLUNA_PROG_LICOES_ETAPA8 = "PROG_LICOES_ETAPA8";
        static final String COLUNA_PROG_LICOES_ETAPA9 = "PROG_LICOES_ETAPA9";
        static final String COLUNA_PROG_LICOES_ETAPA10 = "PROG_LICOES_ETAPA10";

        // COLUNAS DA TABELA DE PERGUNTAS
        static final String ETAPA_PERGUNTA = "ETAPA";
        static final String NUM_PERGUNTA   = "NUM_PERGUNTA";
        static final String ALTERNATIVA1   = "ALTERNATIVA1";
        static final String ALTERNATIVA2   = "ALTERNATIVA2";
        static final String ALTERNATIVA3   = "ALTERNATIVA3";
        static final String ALTERNATIVA4   = "ALTERNATIVA4";


    }
}