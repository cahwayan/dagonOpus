package com.tcc.dagon.opus.databases;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.tcc.dagon.opus.databases.ProgressoUsuario.Progresso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.tcc.dagon.opus.databases.ProgressoUsuario.Progresso.COLUNA_MODULO;


/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE ABRIGA O GERENCIADOR DO BANCO SQLITE
 */


public class GerenciadorBanco extends SQLiteOpenHelper {


    // VERSÃO DO BANCO QUE ESTÁ NO APLICATIVO. A CADA NOVA BUILD QUE LANÇARMOS, TEMOS QUE
    // ATUALIZAR ESSE NÚMERO
    private static final int VERSAO_BANCO = 2;
    // declarando o nome e o caminho do banco
    private static final String DB_NAME = "DB_PROGRESSO";
    private final String DB_PATH;
    ProgressoUsuario progresso = new ProgressoUsuario();
    private Context context;
    // objeto banco
    private SQLiteDatabase DB_PROGRESSO;


    public GerenciadorBanco(Context context) {
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
        DB_PROGRESSO.execSQL("DROP TABLE IF EXISTS" + Progresso.TABELA_PROGRESSO);
        try {
            criarBanco();
        } catch (IOException e) {
            throw new Error("Erro ao criar banco!");
        }

    }

    public void abrirBanco() throws SQLException {
        String caminho = DB_PATH;

        if (DB_PROGRESSO != null && DB_PROGRESSO.isOpen()) {
            // nothing
        } else {
            DB_PROGRESSO = SQLiteDatabase.openDatabase(caminho, null, SQLiteDatabase.OPEN_READWRITE);
        }

    }

    public void fecharBanco() {
        if (DB_PROGRESSO != null) {
            DB_PROGRESSO.close();
        }

        super.close();
    }

    public void criarBanco() throws IOException {
        boolean bancoExiste = checarBanco();
        if (bancoExiste) {
            // banco existe
        } else {
            this.getReadableDatabase();
            try {
                copiarBanco();
            } catch (IOException e) {
                throw new Error("Não foi possível copiar o banco de dado s" + e);
            }
        }
    }

    // checa se o banco existe ou não

    private boolean checarBanco() {
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

    // Copia o banco via bytestream (?)
    public void copiarBanco() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(DB_NAME + ".sqlite");

        // Path to the just created empty db
        String outFileName = DB_PATH;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // método que verifica o progresso do usuário
    public int verificaProgressoModulo() throws SQLException {
        // a coluna que o metodo vai verificar
        String tabela = Progresso.TABELA_PROGRESSO;
        String colunas[] = {
                Progresso.COLUNA_MODULO
        };
        String limit = "1";
        abrirBanco();
        Cursor cursor = DB_PROGRESSO.query(
                tabela,
                colunas,        // Coluna a retornar
                null,      // coluna para a clausula WHERE
                null,  // valores para a clausula WHERE
                null,           // não agrupar as tabelas
                null,           // não filtrar as tabelas
                null,           // não ordenar as tabelas
                limit           // limitar os resultados para 1

        );

        cursor.moveToFirst();
        int progressoModulo = cursor.getInt(
                cursor.getColumnIndexOrThrow(Progresso.COLUNA_MODULO)
        );
        fecharBanco();
        cursor.close();

        return progressoModulo;
    }

    public int verificaProgressoEtapa(int progressoEtapa) {
        String tabela = Progresso.TABELA_PROGRESSO;
        String limit = "1";
        String colunasEtapa[];
        Cursor cursor;
        switch (progressoEtapa) {
            case 1:
                colunasEtapa = new String[] {
                        Progresso.COLUNA_ETAPA1
                };
                abrirBanco();
                cursor = DB_PROGRESSO.query(
                        tabela,
                        colunasEtapa,
                        null,
                        null,
                        null,
                        null,
                        null,
                        limit
                );
                cursor.moveToFirst();
                progressoEtapa = cursor.getInt(
                        cursor.getColumnIndexOrThrow(Progresso.COLUNA_ETAPA1)
                );
                fecharBanco();
                cursor.close();
                break;
            case 2:
                colunasEtapa = new String[] {
                        Progresso.COLUNA_ETAPA2
                };
                abrirBanco();
                cursor = DB_PROGRESSO.query(
                        tabela,
                        colunasEtapa,
                        null,
                        null,
                        null,
                        null,
                        null,
                        limit
                );
                cursor.moveToFirst();
                progressoEtapa = cursor.getInt(
                        cursor.getColumnIndexOrThrow(Progresso.COLUNA_ETAPA2)
                );
                fecharBanco();
                cursor.close();
                break;
            case 3:
                colunasEtapa = new String[] {
                        Progresso.COLUNA_ETAPA3
                };
                abrirBanco();
                cursor = DB_PROGRESSO.query(
                        tabela,
                        colunasEtapa,
                        null,
                        null,
                        null,
                        null,
                        null,
                        limit
                );
                cursor.moveToFirst();
                progressoEtapa = cursor.getInt(
                        cursor.getColumnIndexOrThrow(Progresso.COLUNA_ETAPA3)
                );
                fecharBanco();
                cursor.close();
                break;
        }

        return progressoEtapa;
    }


}


