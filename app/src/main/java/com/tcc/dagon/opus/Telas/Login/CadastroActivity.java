package com.tcc.dagon.opus.telas.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.ValidarEmail;
import com.tcc.dagon.opus.utils.VerificarConexao;
import com.tcc.dagon.opus.utils.VolleyRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_cadastro)
public class CadastroActivity extends AppCompatActivity {

    /* VIEWS */
    @ViewById protected Button btnCadastra;
    @ViewById protected TextView textNome;
    @ViewById protected TextView textSenha;
    @ViewById protected TextView textCSenha;
    @ViewById protected TextView textEmail;

    /* OBJETOS */
    protected GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);
    protected VolleyRequest volleyRequest;

    /* VARIÁVEIS */
    protected String sNome,
                     sSenha,
                     sCsenha,
                     sEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void init() {

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        volleyRequest = new VolleyRequest(this);

    }

    /* LISTENER DO BOTÃO DA ACTION BAR*/
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Click
    protected void btnCadastra() {
        cadastrar();
    }

    protected void cadastrar() {

        /* TRABALHAR COM VARIÁVEIS É MELHOR DO QUE TRABALHAR COM OBJETOS DIRETAMENTE */
        /* PEGANDO OS DADOS DO USUÁRIO */
        sNome   = textNome.getText().toString().trim();
        sSenha  = textSenha.getText().toString().trim();
        sCsenha = textCSenha.getText().toString().trim();
        sEmail  = textEmail.getText().toString().trim();

        /* VERIFICAÇÃO DE SE O USUÁRIO ESTÁ CONECTADO */
        if(VerificarConexao.verificarConexao()) {

            /* SE OS DADOS ESTIVEREM OK E A FUNÇÃO RETORNAR VERDADEIRO... */
            if(verificarDados(sNome, sSenha, sCsenha, sEmail)) {

                /* MÉTODO QUE FAZ O REQUEST PARA GRAVAR OS DADOS NO BANCO */
                volleyRequest.requestCadastrarDados(sEmail, sSenha, sNome);

            }

        /* RESPOSTA CASO O USUÁRIO NÃO ESTEJA CONECTADO */
        } else {
            Toast.makeText(getApplicationContext(), "Sem conexão", Toast.LENGTH_LONG).show();
        }
    }

    protected boolean verificarDados(String sNome, String sSenha, String sCsenha, String sEmail) {
        /* VERIFICAÇÃO CAMPOS VAZIOS */
        if ((sNome.matches("")) ||
                sSenha.matches("") ||
                sCsenha.matches("") ||
                sEmail.matches("")) {
            Toast.makeText(getApplicationContext(), "Há campos em branco", Toast.LENGTH_LONG).show();
            return false;

            /* VERIFICAÇÃO DO TAMANHO DO NOME */
        } else if (sNome.length() < 2 ) {
            Toast.makeText(getApplicationContext(), "Informe um nome maior que 2 caracteres", Toast.LENGTH_LONG).show();
            textNome.requestFocus();
            return false;

            /* VERIFICAÇÃO DO EMAIL*/
        } else if (!ValidarEmail.validarEmail(sEmail)) {
            Toast.makeText(getApplicationContext(), "Por favor informe um email válido!", Toast.LENGTH_LONG).show();
            textEmail.requestFocus();
            return false;

            /* VERIFICAÇÃO DA SENHA */
        } else if (sSenha.length() < 6) {
            Toast.makeText(getApplicationContext(), "A senha precisa conter no mínimo 6 caracteres", Toast.LENGTH_LONG).show();
            textSenha.requestFocus();
            return false;

            /* CONFIRMAÇÃO DE SENHA */
        } else if (!sSenha.equals(sCsenha)) {
            Toast.makeText(getApplicationContext(), "A senha e a confirmação de senha estão diferentes!", Toast.LENGTH_LONG).show();
            textSenha.requestFocus();
            return false;

        /* TENTATIVA DE GRAVAR OS DADOS NO BANCO */
        } else {

            return true;

        }


    }

}
