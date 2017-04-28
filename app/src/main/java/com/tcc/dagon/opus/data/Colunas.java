package com.tcc.dagon.opus.data;

import android.provider.BaseColumns;

/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE GUARDA OS NOMES DAS COLUNAS E DAS TABELAS DO BANCO
 */
final class Colunas {

    // CONSTRUTOR VAZIO PARA EVITAR QUE ALGUÉM ACIDENTALMENTE INSTANCIE ESSA CLASSE
    private Colunas() {
    }

    static abstract class Questoes implements BaseColumns {

        static final String COLUNA_ID = "_id";

        /**
         * Tabela que guarda as questões
         */
        static final String TABELA_QUESTOES = "TD_QUESTOES";

        /**
         * Informações de seleção de questões
         */
        static final String COL_MODULO_QUESTAO = "MODULO";
        static final String COL_ETAPA_QUESTAO = "ETAPA";
        static final String COL_NUM_QUESTAO = "NUM_QUESTAO";

        /**
         * Coluna que guarda o texto da questão
         */
        static final String COL_TEXTO_QUESTAO = "QUESTAO";

        /**
         * Colunas das alternativas das questões
         */
        static final String COL_ALTERNATIVA0 = "ALTERNATIVA0";
        static final String COL_ALTERNATIVA1 = "ALTERNATIVA1";
        static final String COL_ALTERNATIVA2 = "ALTERNATIVA2";
        static final String COL_ALTERNATIVA3 = "ALTERNATIVA3";

        /**
         * Colunas das respostas das questões
         */
        static final String COL_RESPOSTA_ALTERNATIVA0 = "RESPOSTA_ALTERNATIVA0";
        static final String COL_RESPOSTA_ALTERNATIVA1 = "RESPOSTA_ALTERNATIVA1";
        static final String COL_RESPOSTA_ALTERNATIVA2 = "RESPOSTA_ALTERNATIVA2";
        static final String COL_RESPOSTA_ALTERNATIVA3 = "RESPOSTA_ALTERNATIVA3";
    }
}