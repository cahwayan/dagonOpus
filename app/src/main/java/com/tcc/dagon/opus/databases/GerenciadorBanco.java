package com.tcc.dagon.opus.databases;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import com.tcc.dagon.opus.databases.ProgressoUsuario.Progresso;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE ABRIGA O GERENCIADOR DO BANCO SQLITE
 * TODAS AS OPERAÇÕES REALIZADAS COM O BANCO DE DADOS PRECISA SER FEITA NESSA CLASSE
 */

public class GerenciadorBanco extends SQLiteOpenHelper {

    // VERSÃO DO BANCO QUE ESTÁ NO APLICATIVO. A CADA NOVA BUILD QUE LANÇARMOS, TEMOS QUE
    // ATUALIZAR ESSE NÚMERO
    private static final int VERSAO_BANCO = 2;

    // DECLARANDO O NOME DO BANCO
    private static final String DB_NAME = "DB_PROGRESSO";

    // DECLARANDO A VARIÁVEL QUE GUARDARÁ O CAMINHO DO BANCO
    private final String DB_PATH;

    // SUPER VARIÁVEL CONTEXT
    private Context context;

    // OBJETO BANCO
    private SQLiteDatabase DB_PROGRESSO;

    private static final String TAG = "VALOR RESPOSTA BANCO: ";

    // MÉTODO CONSTRUTOR DO BANCO
    public GerenciadorBanco(Context context) {
        super(context, DB_NAME, null, VERSAO_BANCO);
        this.context = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

    // MÉTODO QUE PEGA O NOME DO BANCO
    public String getDbName() {
        return DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //ESSE MÉTODO È CHAMADO TODA VEZ QUE ATUALIZAMOS A ESTRUTURA DO BANCO
    // APAGA A TABELA ANTERIOR E CRIA UMA NOVA
    // (É MELHOR NAO PRECISARMOS ALTERAR ESSA ESTRUTURA.... HEHHEHE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.i(TAG, "Estrutura alterada, recriando banco!!!");
            criarBanco();
        } catch (IOException e) {
            throw new Error("Erro ao criar banco!");
        }

    }

    private void abrirBanco() throws SQLException {
        String caminho = DB_PATH;

        if (DB_PROGRESSO != null && DB_PROGRESSO.isOpen()) {
            // SE O BANCO ESTIVER ABERTO, NÃO REABRIR
        } else { // SE NÃO ESTIVER ABERTO, ABRIR
            DB_PROGRESSO = SQLiteDatabase.openDatabase(caminho, null, SQLiteDatabase.OPEN_READWRITE);
        }

    }

    // FECHA A CONEXÃO COM O BANCO
    private void fecharBanco() {
        if (DB_PROGRESSO != null) {
            DB_PROGRESSO.close();
        }

        super.close();
    }

    // MÉTODO QUE CRIA O BANCO CASO NÃO EXISTA
    public void criarBanco() throws IOException {
        // VARIÁVEL QUE GUARDA O RETORNO DO MÉTODO QUE CHECA SE O BANCO EXISTE
        // SE RETORNAR TRUE, O BANCO EXISTE, E ELE NÃO FAZ NADA.
        // SE RETORNAR FALSE, O BANCO NÃO EXISTE, E É EXECUTADO O MÉTODO DE COPIAR O BANCO
        boolean bancoExiste = checarSeBancoExiste();
        if (bancoExiste) {
            // BANCO EXISTE
        } else {
            // CRIANDO O BANCO VAZIO QUE RECEBERÁ AS COLUNAS DO BANCO PRINCIPAL
            this.getReadableDatabase();
            // COPIANDO O BANCO DA PASTA ASSETS PARA O DISPOSITIVO
            try {
                copiarBanco();
            } catch (IOException e) {
                throw new Error("Não foi possível copiar o banco de dado s" + e);
            }
        }
    }

    // CHECA SE O BANCO EXISTE. CASO NÃO EXISTA, ELE CRIA UM NOVO (GERA UMA EXCEÇÃO QUE É TRATADA NA PRIMEIRA VEZ)
    private boolean checarSeBancoExiste() {
        SQLiteDatabase checarBanco = null;

        try {
            checarBanco = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            System.out.println("Banco não existe! Criando . . . " + e);
        }

        if (checarBanco != null) {
            checarBanco.close();
        }

        return checarBanco != null;
    }

    // Transfere o arquivo do banco da pasta Assets para o armazenamento do celular
    private void copiarBanco() throws IOException {

        // ABRE O BANCO COMO INPUT STREAM
        InputStream myInput = context.getAssets().open(DB_NAME + ".sqlite");

        // CAMINHO PARA O BANCO RECÉM CRIADO
        String outFileName = DB_PATH;

        // ABRE O BANCO VAZIO QUE VAI RECEBER OS DADOS DO BANCO
        OutputStream myOutput = new FileOutputStream(outFileName);

        // TRANSFERE OS DADOS DO BANCO DO APLICATIVO PARA O BANCO VAZIO
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // FECHA AS VIAS DE TRANSFERÊNCIA DE DADOS
        myOutput.flush();
        myOutput.close();
        myInput.close();
        System.out.println("Banco Criado!");
    }


    public int verificarResposta(int moduloPertencente, int etapaPertencente, int numQuestao, int alternativa) {

        String tabela = Progresso.QUESTOES;

        String colunaAlternativa[] = {""};

        String limit = "1";
        int respostaPergunta = 0;

        // SELECIONAR A COLUNA BASEADO NO MODULO A QUAL A ETAPA PERTENCE
        String select = Progresso.MODULO + " lIKE ? AND " +
                        Progresso.ETAPA + " LIKE ? AND " +
                        Progresso.NUM_QUESTAO + " LIKE ? " ;

        // FAZER O SELECT BASEADO NO MODULO PERTENCENTE
        String selectArgs[] = {String.valueOf(moduloPertencente),
                                String.valueOf(etapaPertencente),
                                String.valueOf(numQuestao)};

        switch (alternativa) {
            case 0:
                colunaAlternativa[0] = Progresso.RESPOSTA_ALTERNATIVA0;
                break;
            case 1:
                colunaAlternativa[0] = Progresso.RESPOSTA_ALTERNATIVA1;
                break;
            case 2:

                colunaAlternativa[0] = Progresso.RESPOSTA_ALTERNATIVA2;
                break;
            case 3:
                colunaAlternativa[0] = Progresso.RESPOSTA_ALTERNATIVA3;
                break;
        }

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

        // RETORNA O VALOR REQUERIDO
        Log.i(TAG, String.valueOf(respostaPergunta));

        return respostaPergunta;
    }

    public String buscarPergunta(int moduloPertencente, int etapaPertencente, int numQuestao) {

        String tabela = Progresso.QUESTOES;
        String coluna[] = {Progresso.TEXTO_QUESTAO};
        String pergunta = "";
        String limit = "1";

        // SELECIONAR A COLUNA BASEADO NO MODULO A QUAL A ETAPA PERTENCE
        String select = Progresso.MODULO + " LIKE ? AND " +
                        Progresso.ETAPA + " LIKE ? AND " +
                        Progresso.NUM_QUESTAO + " LIKE ? " ;



        // FAZER O SELECT BASEADO NO MODULO PERTENCENTE
        String selectArgs[] = {String.valueOf(moduloPertencente), String.valueOf(etapaPertencente), String.valueOf(numQuestao)};

        Log.d("Mód:Etapa:NumQuestao: ", String.valueOf(moduloPertencente) + " " + String.valueOf(etapaPertencente) + " " + String.valueOf(numQuestao));

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
            Log.d("QUERY FAILED: ", "CURSOR IS PROBABLY NULL!!");
        }


        fecharBanco();

        return pergunta;
    }

    public String lerAlternativa(int moduloPertencente, int etapaPertencente, int numQuestao, int alternativa) {

        String tabela = Progresso.QUESTOES;
        String coluna[] = {""};
        String textoAlternativa = "";

        switch(alternativa) {
            case 1:
                coluna[0] = Progresso.TEXTO_ALTERNATIVA0;
                break;
            case 2:
                coluna[0] = Progresso.TEXTO_ALTERNATIVA1;
                break;
            case 3:
                coluna[0] = Progresso.TEXTO_ALTERNATIVA2;
                break;
            case 4:
                coluna[0] = Progresso.TEXTO_ALTERNATIVA3;
                break;
        }

        String limit = "1";

        // SELECIONAR A COLUNA BASEADO NO MODULO A QUAL A ETAPA PERTENCE
        String select = Progresso.MODULO + " LIKE ? AND " +
                Progresso.ETAPA + " LIKE ? AND " +
                Progresso.NUM_QUESTAO + " LIKE ? " ;

        // FAZER O SELECT BASEADO NO MODULO PERTENCENTE
        String selectArgs[] = {String.valueOf(moduloPertencente), String.valueOf(etapaPertencente), String.valueOf(numQuestao)};

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
}


