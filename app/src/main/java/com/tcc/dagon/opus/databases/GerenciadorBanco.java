package com.tcc.dagon.opus.databases;
import android.content.ContentValues;
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
        DB_PROGRESSO.execSQL("DROP TABLE IF EXISTS" + Progresso.TABELA_PROGRESSO);
        try {
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
        boolean bancoExiste = checarBanco();
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

    // TRANSFERE O BANCO DA PASTA ASSETS PARA O DIRETÓRIO NO CELULAR
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
    }

    // MÉTODO QUE VERIFICA O PROGRESSO DOS MÓDULOS
    public int verificaProgressoModulo() throws SQLException {
        // A COLUNA QUE O MÉTODO VAI VERIFICAR
        String tabela = Progresso.TABELA_PROGRESSO;
        // VETOR COM O NOME DA COLUNA (PRECISA SER UM VETOR ATÉ PARA PESQUIZAR UMA ÚNICA COLUNA)
        String colunas[] = {
                Progresso.COLUNA_MODULO
        };
        // LIMITE DE LINHAS QUE O BANCO VAI TRAZER
        String limit = "1";
        // ABRE A CONEXÃO COM O BANCO
        abrirBanco();
        // OBJETO CURSOR QUE VAI EFETIVAMENTE GUARDAR A QUERY
        Cursor cursor = DB_PROGRESSO.query(
                tabela,
                colunas,        // Coluna a retornar
                null,           // coluna para a clausula WHERE
                null,           // valores para a clausula WHERE
                null,           // não agrupar as tabelas
                null,           // não filtrar as tabelas
                null,           // não ordenar as tabelas
                limit           // limitar os resultados para 1

        );
        // MOVENDO O CURSOR PARA O PRIMEIRO RESULTADO ENCONTRADO
        cursor.moveToFirst();
        // TRANSFERINDO O RESULTADO DO CURSOR PARA UMA VARIÁVEL
        int progressoModulo = cursor.getInt(
                cursor.getColumnIndexOrThrow(Progresso.COLUNA_MODULO)
        );
        // FECHANDO A CONEXÃO COM O BANCO
        fecharBanco();
        // FECHANDO O CURSOR
        cursor.close();
        // RETORNANDO O DADO BUSCADO NO BANCO
        return progressoModulo;
    }

    // MÉTODO QUE VERIFICA O PROGRESSO DA ETAPA
    // AO CHAMAR O MÉTODO COM O PARÂMETRO REFERENTE AO MÓDULO, O MÉTODO BUSCA O PROGRESSO ATUAL
    // DAS ETAPAS DO USUÁRIO PARA AQUELE MÓDULO
    // EX: verificaProgressoEtapa(3) busca o progresso das etapas do módulo 3
    public int verificaProgressoEtapa(int progressoEtapa) {
        String tabela = Progresso.TABELA_PROGRESSO;
        String limit = "1";
        String colunasEtapa[] = {""};
        Cursor cursor;

        switch (progressoEtapa) {
            case 1:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA1;
                break;
            case 2:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA2;
                break;
            case 3:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA3;
                break;
            case 4:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA4;
                break;
            case 5:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA5;
                break;
            case 6:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA6;
                break;
            case 7:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA7;
                break;
            case 8:
                colunasEtapa[0] = Progresso.COLUNA_ETAPA8;
                break;
        }

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
                cursor.getColumnIndexOrThrow(colunasEtapa[0])
        );
        fecharBanco();
        cursor.close();

        // RETORNA O VALOR REQUERIDO
        return progressoEtapa;
    }

    public void atualizaProgressoModulo(int progresso) {

        String tabela = Progresso.TABELA_PROGRESSO;

        switch(progresso) {
            case 1:
                progresso = 1;
                break;
            case 2:
                progresso = 2;
                break;
            case 3:
                progresso = 3;
                break;
            case 4:
                progresso = 4;
                break;
            case 5:
                progresso = 5;
                break;
            case 6:
                progresso = 6;
                break;
            case 7:
                progresso = 7;
                break;
            case 8:
                progresso = 8;
                break;
        }
        // Qual coluna da tabela fazer o update
        ContentValues valor = new ContentValues();
        // Coluna da tabela e o valor
        valor.put(Progresso.COLUNA_MODULO, progresso);

        // Qual coluna fazer o update baseado no id
        String select       = Progresso.COLUNA_ID + " LIKE ?";
        // qual linha atualizar baseado no ID
        String[] selectArgs = {String.valueOf(1)};
        abrirBanco();
        DB_PROGRESSO.update(
                tabela,
                valor,
                select,
                selectArgs
        );
        fecharBanco();
    }


}


