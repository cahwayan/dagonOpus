package com.tcc.dagon.opus.ui.telasatransferir.aprender.menulateral;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.application.AppController;
import com.tcc.dagon.opus.common.ProgressDialogHelper;
import com.tcc.dagon.opus.data.sharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.common.NovaJanelaAlerta;
import com.tcc.dagon.opus.common.ToastManager;
import com.tcc.dagon.opus.network.volleyrequests.usuario.RequestsUsuario;
import com.tcc.dagon.opus.network.volleyrequests.usuario.UsuarioListener;
import com.tcc.dagon.opus.network.volleyrequests.usuario.UsuarioListenerImp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static com.tcc.dagon.opus.ui.curso.constantes.EtapaConstants.ETAPA0;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO0;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO1;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO2;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO3;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO4;
import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.MODULO5;

@EActivity(R.layout.activity_config)
public class ActivityConfig extends AppCompatActivity {

    /* VIEWS */
    @ViewById protected Switch switchSons;
    @ViewById protected Button btnResetarProgresso;

    protected NovaJanelaAlerta alertaApagar;
    protected GerenciadorSharedPreferences preferencias;
    protected boolean flagDesativarSom;
    protected ToastManager toastManager;

    private RequestsUsuario bancoRemoto;
    private UsuarioListenerImp callbacksRequestsUsuario;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void init() {
        preferencias = new GerenciadorSharedPreferences(this);
        toastManager = new ToastManager(this);
        flagDesativarSom = preferencias.getDesativarSons();

        progressDialog = ProgressDialogHelper.buildDialog(this, "Resetando progresso...");
        callbacksRequestsUsuario = new UsuarioListenerImp(this, progressDialog);

        String tipoUsuario = preferencias.getTipoUsuario();
        String idUsuario = preferencias.getIdUsuario();
        bancoRemoto = new RequestsUsuario(tipoUsuario, idUsuario, callbacksRequestsUsuario);

        if(flagDesativarSom) {
            switchSons.setChecked(true);
        } else {
            switchSons.setChecked(false);
        }

        // SETA VOLTAR NA BARRA DE MENU
        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if(alertaApagar == null) {
            alertaApagar = new NovaJanelaAlerta(this);
        }

        listeners();

    }

    private void listeners() {
        switchSons.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchSons.isChecked()) {
                    preferencias.setDesativarSons(true);
                } else {
                    preferencias.setDesativarSons(false);
                }
            }
        });
    }

    @Click
    void btnResetarProgresso() {
        alertaApagarProgresso("Todo seu progresso será apagado. Tem certeza que deseja fazer isso?",
                listenerDialogClickProva);
    }

    // método que constrói a janela de janelaAlerta ao apertar o botão de apagar dados
    public void alertaApagarProgresso(String mensagem, DialogInterface.OnClickListener listenerOnClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensagem).setPositiveButton("Sim", listenerOnClick)
                .setNegativeButton("Não", listenerOnClick).show();
    }

    // MENSAGEM DE ALERTA AO CLICAR NO BOTÃO APAGAR
    DialogInterface.OnClickListener listenerDialogClickProva = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which) {
                case DialogInterface.BUTTON_POSITIVE:
                    showProgressDialog("Resetando progresso...");
                    resetarProgressoRemoto();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

    // Reseta todo o progresso do usuário no banco local e no remoto
    public void resetarProgressoLocal() {
        // Reset progresso módulos
        preferencias.setProgressoModulo(MODULO0);

        // Reset progresso etapas dos módulos
        preferencias.setProgressoEtapa(MODULO0, ETAPA0);
        preferencias.setProgressoEtapa(MODULO1, ETAPA0);
        preferencias.setProgressoEtapa(MODULO2, ETAPA0);
        preferencias.setProgressoEtapa(MODULO3, ETAPA0);
        preferencias.setProgressoEtapa(MODULO4, ETAPA0);
        preferencias.setProgressoEtapa(MODULO5, ETAPA0);

        // Reset tempo estudo
        preferencias.setTempoEstudo("0");

        // Reset pontuação
        preferencias.setPontos(MODULO0, 0);
        preferencias.setPontos(MODULO1, 0);
        preferencias.setPontos(MODULO2, 0);
        preferencias.setPontos(MODULO3, 0);
        preferencias.setPontos(MODULO4, 0);
        preferencias.setPontos(MODULO5, 0);

        // Reset das flags das provas
        preferencias.setCompletouProva(0, false);
        preferencias.setCompletouProva(1, false);
        preferencias.setCompletouProva(2, false);
        preferencias.setCompletouProva(3, false);
        preferencias.setCompletouProva(4, false);
        preferencias.setCompletouProva(5, false);
    }

    public void resetarProgressoRemoto() {

        bancoRemoto.resetDadosUsuario();

        AppController.setRequestCountdown(AppController.getRequestCount());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    AppController.getCountdownLatch().await();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {

                            if(!callbacksRequestsUsuario.getHouveramErrosAoRestaurarUsuario()) {
                                resetarProgressoLocal();
                                hideProgressDialog();
                                toastManager.toastLong("Progresso resetado!");
                            } else {
                                Toast.makeText(getApplicationContext(), "Ocorreu um erro. Você está conectado?", Toast.LENGTH_LONG).show();
                                hideProgressDialog();
                                callbacksRequestsUsuario.setHouveramErrosAoRestaurarUsuario(false);
                            }


                        }
                    });

                } catch(InterruptedException e) {
                    Log.d(getClass().getSimpleName(), e.toString());
                }

            }
        }).start();

    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
