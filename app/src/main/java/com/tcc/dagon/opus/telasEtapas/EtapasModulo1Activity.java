package com.tcc.dagon.opus.telasEtapas;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.MainActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;

import org.w3c.dom.Text;

public class EtapasModulo1Activity extends AppCompatActivity {
    /*LAYOUTS*/
    private LinearLayout etapa1,
                         etapa2,
                         etapa3,
                         etapa4;

    private TextView txtEtapa1,
                     txtEtapa2,
                     txtEtapa3,
                     txtEtapa4;

    // OBJETO QUE FARÁ CONSULTA NO BANCO
    GerenciadorBanco DB_PROGRESSO;
    // OBJETO QUE INVOCA JANELA DE ALERTAS
    NovaJanelaAlerta alerta = new NovaJanelaAlerta(this);

    /*SUPER VARIÁVEL CONTEXT*/
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapas_modulo_1);

        //ACESSANDO AS VIEWS
        accessViews();

        //ADICIONANDO OS CLICK LISTENERS
        ClickListenersEtapas();

        // SETA VOLTAR NA BARRA DE MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Instanciando o objeto banco
        DB_PROGRESSO = new GerenciadorBanco(this);

        // DESBLOQUEANDO ETAPAS
        desbloquearEtapas();


    }


    private void accessViews() {
        /*RECUPEERANDO ITENS LAYOUT*/
        etapa1 = (LinearLayout) findViewById(R.id.Etapa1);
        etapa2 = (LinearLayout) findViewById(R.id.Etapa2);
        etapa3 = (LinearLayout) findViewById(R.id.Etapa3);
        etapa4 = (LinearLayout) findViewById(R.id.Etapa4);

        txtEtapa1 = (TextView)findViewById(R.id.txtQuestoesModulo1Etapa1);
        txtEtapa2 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa2);
        txtEtapa3 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa3);
        txtEtapa4 = (TextView) findViewById(R.id.txtQuestoesModulo1Etapa4);
    }

    private void desbloquearEtapas() {
        switch(DB_PROGRESSO.verificaProgressoEtapa(1)) {
            case 1:
                desbloquearEtapa1();
                break;
            case 2:
                desbloquearEtapa1();
                desbloquearEtapa2();
                break;
            case 3:
                desbloquearEtapa1();
                desbloquearEtapa2();
                desbloquearEtapa3();
                break;
            case 4:
                desbloquearEtapa1();
                desbloquearEtapa2();
                desbloquearEtapa3();
                desbloquearEtapa4();
                break;

            case 9:
                desbloquearEtapa1();
                desbloquearEtapa2();
                desbloquearEtapa3();
                desbloquearEtapa4();
                break;
        }
    }

    private void desbloquearEtapa1() {
        // SUMINDO COM O CADEADO
        txtEtapa1.setCompoundDrawables(null, null, null, null);
        // TRAZENDO DE VOLTA A COR DA ETAPA DESBLOQUEADA
        txtEtapa1.setBackgroundColor(ContextCompat.getColor(context, R.color.corEtapaDesbloqueada));
        // TROCANDO A BORDA
        etapa1.setBackgroundResource(R.drawable.borda_etapa_desbloqueada);
    }

    private void desbloquearEtapa2() {
        // SUMINDO COM O CADEADO
        txtEtapa2.setCompoundDrawables(null, null, null, null);
        // TRAZENDO DE VOLTA A COR DA ETAPA DESBLOQUEADA
        txtEtapa2.setBackgroundColor(ContextCompat.getColor(context, R.color.corEtapaDesbloqueada));
        // TROCANDO A BORDA
        etapa2.setBackgroundResource(R.drawable.borda_etapa_desbloqueada);
    }

    private void desbloquearEtapa3() {
        // SUMINDO COM O CADEADO
        txtEtapa3.setCompoundDrawables(null, null, null, null);
        // TRAZENDO DE VOLTA A COR DA ETAPA DESBLOQUEADA
        txtEtapa3.setBackgroundColor(ContextCompat.getColor(context, R.color.corEtapaDesbloqueada));
        // TROCANDO A BORDA
        etapa3.setBackgroundResource(R.drawable.borda_etapa_desbloqueada);
    }

    private void desbloquearEtapa4() {
        // SUMINDO COM O CADEADO
        txtEtapa4.setCompoundDrawables(null, null, null, null);
        // TRAZENDO DE VOLTA A COR DA ETAPA DESBLOQUEADA
        txtEtapa4.setBackgroundColor(ContextCompat.getColor(context, R.color.corEtapaDesbloqueada));
        // TROCANDO A BORDA
        etapa4.setBackgroundResource(R.drawable.borda_etapa_desbloqueada);
    }

    private void ClickListenersEtapas() {
        etapa1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // VERIFICANDO PROGRESSO DO USUARIO PARA LIBERAR ETAPAS
                if(DB_PROGRESSO.verificaProgressoEtapa(1) >= 1) {
                    // ANIMAÇÃO
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    // ABRIR LICOES
                    startActivity(new Intent(getApplicationContext(), ContainerModulo1Etapa1.class));
                } else {
                    // ALERTA CASO ESTEJA BLOQUEADO
                    alertaEtapaBloqueada();
                }

            }
        });

        etapa2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(1) >= 2) {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    //startActivity(new Intent(getApplicationContext(), EtapasModulo1Activity.class));
                } else {
                    alertaEtapaBloqueada();
                }
            }
        });

        etapa3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(1) >= 3) {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    //startActivity(new Intent(getApplicationContext(), EtapasModulo1Activity.class));
                } else {
                    alertaEtapaBloqueada();
                }
            }
        });

        etapa4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoEtapa(1) >= 4) {
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    //startActivity(new Intent(getApplicationContext(), EtapasModulo1Activity.class));
                } else {
                    alertaEtapaBloqueada();
                }
            }
        });
    }

    // MÉTODO QUE VOLTA PRA TELA APRENDER QUANDO CLICAR NA SETA LA EM CIMA
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, AprenderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // MÉTODO QUE INVOCA A JANELA DE ETAPA BLOQUEADA
    private void alertaEtapaBloqueada() {
        alerta.alertDialogBloqueado("Etapa Bloqueada", "Termine as etapas anteriores para desbloquear essa");
    }
}
