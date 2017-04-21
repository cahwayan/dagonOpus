package com.tcc.dagon.opus.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by cahwayan on 19/04/2017.
 */

public class DBQuestoes extends DB {

    private final String tabela = Colunas.Questoes.TABELA_QUESTOES;
    private final String moduloAtual;
    private final String etapaAtual;

    public DBQuestoes(int moduloAtual, int etapaAtual, Context context) {
        super(context);
        this.moduloAtual = String.valueOf(moduloAtual);
        this.etapaAtual = String.valueOf(etapaAtual);
    }

    /**
     * Faz uma busca pela resposta de uma pergunta no banco de dados
     * @param numQuestao: o número da questão dentro de uma determinada etapa
     * @param alternativa: qual alternativa deve ser verificada
     * @return 0 para uma resposta errada, 1 para uma resposta certa
     */
    public int verificarResposta(int numQuestao, int alternativa) {

        String colunaAlternativa[] = {getColunaRespostaAlternativa(alternativa)};
        String limit = "1";
        int respostaPergunta = 0;

        // SELECIONAR A COLUNA BASEADO NO COL_MODULO_QUESTAO A QUAL A COL_ETAPA_QUESTAO PERTENCE
        String select = Colunas.Questoes.COL_MODULO_QUESTAO + " lIKE ? AND " +
                Colunas.Questoes.COL_ETAPA_QUESTAO + " LIKE ? AND " +
                Colunas.Questoes.COL_NUM_QUESTAO + " LIKE ? ";

        // FAZER O SELECT BASEADO NO COL_MODULO_QUESTAO PERTENCENTE
        String selectArgs[] = {moduloAtual, etapaAtual, String.valueOf(numQuestao)};

        abrirBanco();
        Cursor cursor = DB_PROGRESSO.query(
                tabela,
                colunaAlternativa,
                select,
                selectArgs,
                null,
                null,
                limit
        );

        if( cursor != null && cursor.moveToFirst() ){
            respostaPergunta = cursor.getInt(
                    cursor.getColumnIndexOrThrow(colunaAlternativa[0])
            );
            cursor.close();
        }

        fecharBanco();

        Log.i(TAG, "Resposta pergunta: " + String.valueOf(respostaPergunta));

        return respostaPergunta;
    }

    /**
     * Faz uma query no banco de dados em busca do texto da pergunta
     * @param numQuestao: o número da questão dentro de uma determinada etapa
     */
    public String buscarPergunta(int numQuestao) {

        String coluna[] = {Colunas.Questoes.COL_TEXTO_QUESTAO};
        String pergunta = "";
        String limit = "1";

        String select = Colunas.Questoes.COL_MODULO_QUESTAO + " LIKE ? AND " +
                Colunas.Questoes.COL_ETAPA_QUESTAO + " LIKE ? AND " +
                Colunas.Questoes.COL_NUM_QUESTAO + " LIKE ? " ;

        String selectArgs[] = {moduloAtual, etapaAtual, String.valueOf(numQuestao)};

        Log.d("Mód:Etapa:NumQuestao: ", moduloAtual + " " + etapaAtual + " " + String.valueOf(numQuestao));

        abrirBanco();
        Cursor cursor = DB_PROGRESSO.query(
                tabela,
                coluna,
                select,
                selectArgs,
                null,
                null,
                limit
        );

        if( cursor != null && cursor.moveToFirst() ){
            pergunta = cursor.getString(
                    cursor.getColumnIndexOrThrow(coluna[0])
            );
            cursor.close();
        } else {
            Log.d("QUERY FALHOU: ", "CURSOR PROVAVELMENTE NULL!!");
        }

        fecharBanco();

        return pergunta;
    }

    /**
     * Faz uma query no banco de dados em busca do texto de uma alternativa de uma questão
     * @param numQuestao: o número da questão dentro de uma determinada etapa
     * @param alternativa: o número da alternativa a buscar
     */
    public String lerAlternativa(int numQuestao, int alternativa) {

        String textoAlternativa = "";
        String limit = "1";
        String coluna[] = new String[]{getColunaAlternativa(alternativa)};

        // SELECIONAR A COLUNA BASEADO NO COL_MODULO_QUESTAO A QUAL A COL_ETAPA_QUESTAO PERTENCE
        String select = Colunas.Questoes.COL_MODULO_QUESTAO + " LIKE ? AND " +
                Colunas.Questoes.COL_ETAPA_QUESTAO + " LIKE ? AND " +
                Colunas.Questoes.COL_NUM_QUESTAO + " LIKE ? " ;

        // FAZER O SELECT BASEADO NO COL_MODULO_QUESTAO PERTENCENTE
        String selectArgs[] = {moduloAtual, etapaAtual, String.valueOf(numQuestao)};

        abrirBanco();
        Cursor cursor = DB_PROGRESSO.query(
                tabela,
                coluna,
                select,
                selectArgs,
                null,
                null,
                limit
        );

        if( cursor != null && cursor.moveToFirst() ){
            textoAlternativa = cursor.getString(
                    cursor.getColumnIndexOrThrow(coluna[0])
            );
            cursor.close();
        }


        fecharBanco();

        return textoAlternativa;
    }

    public String getColunaAlternativa(int numAlternativa) {

        switch(numAlternativa) {
            case 0:
                return Colunas.Questoes.COL_ALTERNATIVA0;
            case 1:
                return Colunas.Questoes.COL_ALTERNATIVA1;
            case 2:
                return Colunas.Questoes.COL_ALTERNATIVA2;
            case 3:
                return Colunas.Questoes.COL_ALTERNATIVA3;
            default:
                return "COLUNA_INVALIDA";
        }

    }

    public String getColunaRespostaAlternativa(int numAlternativa) {
        switch (numAlternativa) {
            case 0:
                return Colunas.Questoes.COL_RESPOSTA_ALTERNATIVA0;
            case 1:
                return Colunas.Questoes.COL_RESPOSTA_ALTERNATIVA1;
            case 2:
                return Colunas.Questoes.COL_RESPOSTA_ALTERNATIVA2;
            case 3:
                return Colunas.Questoes.COL_RESPOSTA_ALTERNATIVA3;
            default:
                return "COLUNA_INVALIDA";
        }
    }
}
