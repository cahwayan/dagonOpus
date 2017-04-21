package com.tcc.dagon.opus.data;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE ABRIGA O GERENCIADOR DO BANCO SQLITE
 * TODAS AS OPERAÇÕES REALIZADAS COM O BANCO DE DADOS PRECISA SER FEITA NESSA CLASSE
 */

public class DB extends SQLiteOpenHelper {

    protected final String TAG = this.getClass().getSimpleName();

    // A cada alteração na estrutura do banco, é necessário incrementar essa variável
    private static final int VERSAO_BANCO = 2;

    // Nome do arquivo do banco que será armazenado no dispositivo
    private static final String DB_NAME = "DB_PROGRESSO";

    // Caminho do arquivo do banco no dispositivo
    private final String DB_PATH;

    private Context context;
    protected SQLiteDatabase DB_PROGRESSO;

    public DB(Context context) {
        super(context, DB_NAME, null, VERSAO_BANCO);
        this.context = context;
        DB_PATH = context.getDatabasePath(DB_NAME).getPath();
    }

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

    protected void abrirBanco() throws SQLException {

        if (DB_PROGRESSO != null && DB_PROGRESSO.isOpen()) {

            // SE O BANCO ESTIVER ABERTO, NÃO REABRIR

        } else { // SE NÃO ESTIVER ABERTO, ABRIR
            DB_PROGRESSO = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        }

    }

    protected void fecharBanco() {
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

}


