package com.tcc.dagon.opus.databases;

import android.provider.BaseColumns;

/**
 * Created by Andrade on 23/09/2016.
 */
public final class ProgressoUsuario {
    // para evitar que alguém acidentalmente instancie essa classe
    public ProgressoUsuario() {
    }

    // COLUNAS DO BANCO DE DADOS
    public static abstract class Progresso implements BaseColumns {
        public static final String TABELA_PROGRESSO = "TD_PROG_USUARIO";
        public static final String COLUNA_ID = "_id";
        public static final String COLUNA_MODULO = "PROG_MODULO";
        public static final String COLUNA_ETAPA1 = "PROG_ETAPA_MODULO1";
        public static final String COLUNA_ETAPA2 = "PROG_ETAPA_MODULO2";
        public static final String COLUNA_ETAPA3 = "PROG_ETAPA_MODULO3";
        public static final String COLUNA_ETAPA4 = "PROG_ETAPA_MODULO4";
        public static final String COLUNA_ETAPA5 = "PROG_ETAPA_MODULO5";
        public static final String COLUNA_ETAPA6 = "PROG_ETAPA_MODULO6";
        public static final String COLUNA_ETAPA7 = "PROG_ETAPA_MODULO7";
        public static final String COLUNA_ETAPA8 = "PROG_ETAPA_MODULO8";
    }
}