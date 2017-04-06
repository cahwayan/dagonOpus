package com.tcc.dagon.opus.ui.usuario;

import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.utils.ProgressDialogHelper;
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.ToastManager;
import com.tcc.dagon.opus.utils.ValidarEmail;
import com.tcc.dagon.opus.utils.VerificarConexao;
import com.tcc.dagon.opus.utils.volley.CadastroRequests;
import com.tcc.dagon.opus.utils.volley.CallbackCadastro;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_cadastro)
public class CadastroActivity extends AppCompatActivity implements CallbackCadastro {

    private final String TAG = this.getClass().getSimpleName();
    /* VIEWS */ /**/
    @ViewById protected Button btnCadastra;
    @ViewById protected TextView textNome;
    @ViewById protected TextView textSenha;
    @ViewById protected TextView textCSenha;
    @ViewById protected TextView textEmail;
    ProgressDialog progressDialog;

    /* OBJETOS */
    protected GerenciadorSharedPreferences preferenceManager;


    /* VARIÁVEIS */
    protected String sNome,
                     sSenha,
                     sCsenha,
                     sEmail;

    private CadastroRequests volleyRequest;
    private ToastManager toastManager;

    private int COR_MAIN;
    private int COR_VERMELHO;
    private int COR_VERDE;

    @Override
    public void callbackUsuarioExiste(String resultado) {

        Log.d(TAG, "Callback Email Existe: " + resultado);

        // Se usuário existe
        if(resultado.equals("sim")) {
            configureEditTextUnavailable();
        } else if(resultado.equals("nao")) {
            configureEditTextAvailable();
        }

    }

    @Override
    public void callbackCadastro(String resultado) {

        hideProgressDialog();

        if(resultado.equals("certo")) {
            Log.d(TAG, "Usuário cadastrado com sucesso!");
            toastManager.toastShort("Você foi cadastrado com sucesso!");
            this.finish();
        } else if(resultado.equals("erroExiste")) {
            toastManager.toastShort("Email já cadastrado");
        } else {
            toastManager.toastLong("Erro desconhecido. Tente novamente.");
        }


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void init() {
        volleyRequest = new CadastroRequests(this);
        preferenceManager = new GerenciadorSharedPreferences(this);
        toastManager = new ToastManager(this);

        COR_MAIN = ContextCompat.getColor(this, R.color.colorPrimary);
        COR_VERMELHO = ContextCompat.getColor(this, R.color.corDicaVermelha);
        COR_VERDE = ContextCompat.getColor(this, R.color.corDicaVerde);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
        /* VERIFICAÇÃO DE SE O USUÁRIO ESTÁ CONECTADO */
        if(VerificarConexao.verificarConexao()) {
            cadastrar();
        } else {
            Toast.makeText(getApplicationContext(), "Sem conexão", Toast.LENGTH_LONG).show();
        }
    }

    @TextChange
    protected void textEmail() {

        sEmail = textEmail.getText().toString();

        if(!ValidarEmail.validarEmail(sEmail)) {
            configureEditTextReset();
            return;
        }

        verificarUsuarioExiste();
    }

    protected void cadastrar() {

            sNome   = textNome.getText().toString().trim();
            sSenha  = textSenha.getText().toString().trim();
            sCsenha = textCSenha.getText().toString().trim();
            sEmail  = textEmail.getText().toString().trim();

            /* SE OS DADOS ESTIVEREM OK E A FUNÇÃO RETORNAR VERDADEIRO... */
            if(verificarDados(sNome, sSenha, sCsenha, sEmail)) {
                /* MÉTODO QUE FAZ O REQUEST PARA GRAVAR OS DADOS NO BANCO */
                showProgressDialog(R.string.cadastrando);
                volleyRequest.cadastrarUsuario(StringsBanco.USUARIO_INTERNO, sEmail, sSenha, sNome);
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

    protected void verificarUsuarioExiste()  {

        volleyRequest.usuarioExiste(StringsBanco.USUARIO_INTERNO, sEmail);

    }

    private void configureEditTextUnavailable() {
        if(VerificarConexao.verificarConexao()) {
            textEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textEmail.setTextColor(COR_VERMELHO);
            toastManager.toastShort("Email já cadastrado");
        }

    }

    private void configureEditTextAvailable() {
        if(VerificarConexao.verificarConexao()) {
            textEmail.setTextColor(COR_VERDE);
            textEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_email_valido, 0);
        }
    }

    private void configureEditTextReset() {
        textEmail.setTextColor(COR_MAIN);
        textEmail.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    public void showProgressDialog(int resId) {
        showProgressDialog(getString(resId));
    }

    public void showProgressDialog(String msg) {
        this.progressDialog = ProgressDialogHelper.buildDialog(this, msg);
        this.progressDialog.show();
    }

    public void hideProgressDialog() {
        if(this.progressDialog != null) {
            this.progressDialog.dismiss();
        }
    }

}
