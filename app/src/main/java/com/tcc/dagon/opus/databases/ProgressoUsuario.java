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

    // COLUNAS DO BANCO DE DADOS
        static abstract class Progresso implements BaseColumns {
        static final String TABELA_PROGRESSO = "TD_PROG_USUARIO";
        static final String COLUNA_ID = "_id";
        static final String COLUNA_MODULO = "PROG_MODULO";
        static final String COLUNA_ETAPA1 = "PROG_ETAPA_MODULO1";
        static final String COLUNA_ETAPA2 = "PROG_ETAPA_MODULO2";
        static final String COLUNA_ETAPA3 = "PROG_ETAPA_MODULO3";
        static final String COLUNA_ETAPA4 = "PROG_ETAPA_MODULO4";
        static final String COLUNA_ETAPA5 = "PROG_ETAPA_MODULO5";
        static final String COLUNA_ETAPA6 = "PROG_ETAPA_MODULO6";
        static final String COLUNA_ETAPA7 = "PROG_ETAPA_MODULO7";
        static final String COLUNA_ETAPA8 = "PROG_ETAPA_MODULO8";
    }
}