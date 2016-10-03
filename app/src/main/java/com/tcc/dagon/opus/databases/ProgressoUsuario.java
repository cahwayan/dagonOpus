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
        public static final String COLUNA_ETAPA = "PROG_ETAPA";
    }
}