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

        // COLUNA DO ID
        static final String COLUNA_ID = "_id";

        // TABELA QUE GUARDA O TEXTO DAS QUESTÕES
        static final String QUESTOES = "TD_QUESTOES";

        /*COLUNAS QUE AJUDAM NA SELEÇÃO*/
        static final String MODULO = "MODULO";
        static final String ETAPA = "ETAPA";
        static final String NUM_QUESTAO = "NUM_QUESTAO";

        /* A PERGUNTA EM SI*/
        static final String TEXTO_QUESTAO = "QUESTAO";

        // COLUNAS DA TABELA DAS RESPOSTAS DAS PERGUNTAS
        // SERVE TANTO PARA AS RESPOSTAS QUANDO PARA O TEXTO
        static final String TEXTO_ALTERNATIVA0 = "ALTERNATIVA0";
        static final String TEXTO_ALTERNATIVA1 = "ALTERNATIVA1";
        static final String TEXTO_ALTERNATIVA2 = "ALTERNATIVA2";
        static final String TEXTO_ALTERNATIVA3 = "ALTERNATIVA3";

        static final String RESPOSTA_ALTERNATIVA0 = "RESPOSTA_ALTERNATIVA0";
        static final String RESPOSTA_ALTERNATIVA1 = "RESPOSTA_ALTERNATIVA1";
        static final String RESPOSTA_ALTERNATIVA2 = "RESPOSTA_ALTERNATIVA2";
        static final String RESPOSTA_ALTERNATIVA3 = "RESPOSTA_ALTERNATIVA3";
    }
}