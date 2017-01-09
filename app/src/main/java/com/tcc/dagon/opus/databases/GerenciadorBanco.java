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

    private static final String TAG = "Atualização: ";

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
        System.out.println("Banco Criado!");
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

    public int verificarPontuacao(int moduloPertencente) {
        String tabela = Progresso.TABELA_PONTOS;
        String colunas[] = {
            Progresso.COLUNA_PONTOS
        };

        String select = Progresso.MODULO + " LIKE ? ";
        String selectArgs[] = {String.valueOf(moduloPertencente)};

        String limit = "1";

        abrirBanco();
        Cursor cursor = DB_PROGRESSO.query(
                tabela,
                colunas,
                select,
                selectArgs,
                null,
                null,
                limit
        );

        cursor.moveToFirst();

        int pontuacao = cursor.getInt(
                cursor.getColumnIndexOrThrow(Progresso.COLUNA_PONTOS)
        );

        fecharBanco();
        cursor.close();
        return pontuacao;
    }

    public void alterarPontuacao(int moduloPertencente, int pontos) {
        int pontuacaoAtual = this.verificarPontuacao(moduloPertencente);
        String tabela = Progresso.TABELA_PONTOS;
        ContentValues values = new ContentValues();
        values.put(Progresso.COLUNA_PONTOS, pontuacaoAtual + pontos);

        String select = Progresso.MODULO + " LIKE ? ";
        String[] selectArgs = {String.valueOf(moduloPertencente)};

        abrirBanco();
        DB_PROGRESSO.update(
                tabela,
                values,
                select,
                selectArgs
        );
        fecharBanco();
    }

    public void alterarPontuacaoTotal(int moduloPertencente, int pontos) {
        String tabela = Progresso.TABELA_PONTOS;
        ContentValues values = new ContentValues();
        values.put(Progresso.COLUNA_PONTOS, pontos);

        String select = Progresso.MODULO + " LIKE ? ";
        String[] selectArgs = {String.valueOf(moduloPertencente)};

        abrirBanco();
        DB_PROGRESSO.update(
                tabela,
                values,
                select,
                selectArgs
        );
        fecharBanco();
    }

    // MÉTODO QUE VERIFICA O PROGRESSO DA ETAPA
    // AO CHAMAR O MÉTODO COM O PARÂMETRO REFERENTE AO MÓDULO, O MÉTODO BUSCA O PROGRESSO ATUAL
    // DAS ETAPAS DO USUÁRIO PARA AQUELE MÓDULO
    // EX: verificaProgressoEtapa(3) busca o progresso das etapas do módulo 3
    public int verificaProgressoEtapa(int progressoEtapa) {
        String tabela = Progresso.TABELA_PROGRESSO;
        String colunasEtapa[] = {""};
        String limit = "1";
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

        Cursor cursor = DB_PROGRESSO.query(
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

    // VERIFICA PROGRESSO DA LICAO
    public int verificaProgressoLicao(int moduloPertencente, int etapaPertencente) {
        String tabela  = Progresso.TABELA_PROGRESSO_LICOES;
        String colunaEtapa[] = {""};
        String limit = "1";
        int progressoLicao = 0;

        // SELECIONAR A COLUNA BASEADO NO MODULO A QUAL A ETAPA PERTENCE
        String select       = Progresso.COLUNA_PROG_LICOES_MODULO + " LIKE ? ";

        // FAZER O SELECT BASEADO NO MODULO PERTENCENTE
        String selectArgs[] = {String.valueOf(moduloPertencente)};

        switch (etapaPertencente) {
            case 1:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA1;
                break;
            case 2:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA2;
                break;
            case 3:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA3;
                break;
            case 4:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA4;
                break;
            case 5:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA5;
                break;
            case 6:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA6;
                break;
            case 7:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA7;
                break;
            case 8:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA8;
                break;
            case 9:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA9;
                break;
            case 10:
                colunaEtapa[0] = Progresso.COLUNA_PROG_LICOES_ETAPA10;
                break;
        }


        abrirBanco();
        Cursor cursor = DB_PROGRESSO.query(
                tabela,
                colunaEtapa,
                select,
                selectArgs,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        progressoLicao = cursor.getInt(
                cursor.getColumnIndexOrThrow(colunaEtapa[0])
        );
        fecharBanco();
        cursor.close();

        // RETORNA O VALOR REQUERIDO
        return progressoLicao;
    }

    // VERIFICA SE A PERGUNTA ESTÁ CERTA
    public int verificaPergunta(int moduloPertencente, int etapaPertencente, int numQuestao, int alternativa) {
        String tabela = Progresso.RESPOSTAS_QUESTOES;
        String colunaAlternativa[] = {""};

        String colunaEtapa[] = {""};
        String limit = "1";
        int respostaPergunta = 10;

        // SELECIONAR A COLUNA BASEADO NO MODULO A QUAL A ETAPA PERTENCE
        String select       = Progresso.MODULO + " lIKE ? AND " + Progresso.ETAPA + " LIKE ? AND " + Progresso.NUM_QUESTAO + " LIKE ? " ;

        // FAZER O SELECT BASEADO NO MODULO PERTENCENTE
        String selectArgs[] = {String.valueOf(moduloPertencente), String.valueOf(etapaPertencente), String.valueOf(numQuestao)};

        switch (alternativa) {
            case 1:
                colunaAlternativa[0] = Progresso.ALTERNATIVA1;
                break;
            case 2:
                colunaAlternativa[0] = Progresso.ALTERNATIVA2;
                break;
            case 3:
                colunaAlternativa[0] = Progresso.ALTERNATIVA3;
                break;
            case 4:
                colunaAlternativa[0] = Progresso.ALTERNATIVA4;
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

    // ATUALIZA PROGRESSO MÓDULO
    public void atualizaProgressoModulo(int progresso) {

        String tabela = Progresso.TABELA_PROGRESSO;

        // OBJETO QUE GUARDARÁ OS VALORES
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


    // ATUALIZA PROGRESSO DA ETAPA
    public void atualizaProgressoEtapa(int moduloPertencente, int progressoEtapa) {
        // TABELA QUE IRÁ SER PESQUISADA
        String tabela = Progresso.TABELA_PROGRESSO;
        String coluna[] = {""};
        // SELECIONANDO O MÓDULO DA ETAPA A ATUALIZAR
        switch(moduloPertencente) {
            case 1: coluna[0] = Progresso.COLUNA_ETAPA1;
                break;
            case 2: coluna[0] = Progresso.COLUNA_ETAPA2;
                break;
            case 3: coluna[0] = Progresso.COLUNA_ETAPA3;
                break;
            case 4: coluna[0] = Progresso.COLUNA_ETAPA4;
                break;
            case 5: coluna[0] = Progresso.COLUNA_ETAPA5;
                break;
            case 6: coluna[0] = Progresso.COLUNA_ETAPA6;
                break;
            case 7: coluna[0] = Progresso.COLUNA_ETAPA7;
                break;
            case 8: coluna[0] = Progresso.COLUNA_ETAPA8;
                break;
        }
        // OBJETO QUE GUARDARÁ OS DADOS A SEREM COLOCADOS
        ContentValues valor = new ContentValues();
        // NOME DA TABELA E OS VALORES QUE SERÃO COLOCADOS
        valor.put(coluna[0], progressoEtapa);
        // SELECIONAR A COLUNA BASEADO NO ID
        String select       = Progresso.COLUNA_ID + " LIKE ? ";
        // QUAL LINHA QUE POSSUUI O ID FAZER O UPDATE
        String selectArgs[] = {String.valueOf(1)};
        abrirBanco();
        DB_PROGRESSO.update(
                tabela,
                valor,
                select,
                selectArgs
        );
        fecharBanco();
    }

    // ATUALIZA O PROGRESSO DA LIÇÃO
    public void atualizaProgressoLicao(int moduloPertencente, int etapaPertencente, int progressoLicao) {
        // TABELA QUE IRÁ SER PESQUISADA
        String tabela = Progresso.TABELA_PROGRESSO_LICOES;
        String coluna[] = {""};

        // SELECIONANDO O MÓDULO DA LICAO A ATUALIZAR
        switch (etapaPertencente) {
            case 1:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA1;
                break;
            case 2:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA2;
                break;
            case 3:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA3;
                break;
            case 4:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA4;
                break;
            case 5:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA5;
                break;
            case 6:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA6;
                break;
            case 7:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA7;
                break;
            case 8:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA8;
                break;
            case 9:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA9;
                break;
            case 10:
                coluna[0] = Progresso.COLUNA_PROG_LICOES_ETAPA10;
                break;
        }

        // OBJETO QUE GUARDARÁ OS DADOS A SEREM COLOCADOS
        ContentValues valor = new ContentValues();

        // NOME DA TABELA E OS VALORES QUE SERÃO COLOCADOS
        valor.put(coluna[0], progressoLicao);

        // SELECIONAR A COLUNA BASEADO NO ID
        String select       = Progresso.COLUNA_PROG_LICOES_MODULO + " LIKE ? ";

        // QUAL LINHA QUE POSSUUI O ID FAZER O UPDATE
        String selectArgs[] = {String.valueOf(moduloPertencente)};
        abrirBanco();
        DB_PROGRESSO.update(
                tabela,
                valor,
                select,
                selectArgs
        );
        fecharBanco();
    }

    public String puxarPergunta(int moduloPertencente, int etapaPertencente, int numQuestao) {

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
        }


        fecharBanco();

        return pergunta;
    }

    public String puxarAlternativa(int moduloPertencente, int etapaPertencente, int numQuestao, int alternativa) {

        String tabela = Progresso.QUESTOES;
        String coluna[] = {""};
        String textoAlternativa = "";

        switch(alternativa) {
            case 1:
                coluna[0] = Progresso.ALTERNATIVA1;
                break;
            case 2:
                coluna[0] = Progresso.ALTERNATIVA2;
                break;
            case 3:
                coluna[0] = Progresso.ALTERNATIVA3;
                break;
            case 4:
                coluna[0] = Progresso.ALTERNATIVA4;
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


