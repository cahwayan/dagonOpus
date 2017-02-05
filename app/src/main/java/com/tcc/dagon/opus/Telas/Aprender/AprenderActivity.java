package com.tcc.dagon.opus.telas.aprender;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tcc.dagon.opus.telas.aprender.menulateral.ActivityConfig_;
import com.tcc.dagon.opus.telas.certificado.CertificadoActivity;
import com.tcc.dagon.opus.telas.certificado.CertificadoIncompleto;
import com.tcc.dagon.opus.telas.aprender.menulateral.glossario.ContainerComandosGlossario;
import com.tcc.dagon.opus.telas.aprender.menulateral.GerenciarPerfilActivity;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.telas.etapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import java.io.File;
import java.io.IOException;
import static java.lang.String.valueOf;


/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE ABRIGA AS FUNÇÕES DA TELA DE MÓDULOS
 *
 */

@EActivity(R.layout.activity_aprender)
public class AprenderActivity
        extends AppCompatActivity{

    /* VIEWS */

    /*BOTÕES DOS MÓDULOS*/
    @ViewById protected LinearLayout btnModulo1;
    @ViewById protected LinearLayout btnModulo2;
    @ViewById protected LinearLayout btnModulo3;
    @ViewById protected LinearLayout btnModulo4;
    @ViewById protected LinearLayout btnModulo5;
    @ViewById protected LinearLayout btnModulo6;
    @ViewById protected LinearLayout btnCertificado;

    /* TXT NOTAS */
    @ViewById protected TextView txtNota1;
    @ViewById protected TextView txtNota2;
    @ViewById protected TextView txtNota3;
    @ViewById protected TextView txtNota4;
    @ViewById protected TextView txtNota5;
    @ViewById protected TextView txtNota6;

    /*IMAGENS DOS MÓDULOS*/
    @ViewById protected ImageView imgModulo1;
    @ViewById protected ImageView imgModulo2;
    @ViewById protected ImageView imgModulo3;
    @ViewById protected ImageView imgModulo4;
    @ViewById protected ImageView imgModulo5;
    @ViewById protected ImageView imgModulo6;
    @ViewById protected ImageView imgCertificado;

    /*TEXTVIEWS TÍTULOS MÓDULOS*/
    @ViewById protected TextView txtTitulo1;
    @ViewById protected TextView txtTitulo2;
    @ViewById protected TextView txtTitulo3;
    @ViewById protected TextView txtTitulo4;
    @ViewById protected TextView txtTitulo5;
    @ViewById protected TextView txtTitulo6;
    @ViewById protected TextView txtTituloCertificado;

    /*TEXT VIEW PROGRESSO DO USUÁRIO*/
    @ViewById protected TextView txtProgresso1;
    @ViewById protected TextView txtProgresso2;
    @ViewById protected TextView txtProgresso3;
    @ViewById protected TextView txtProgresso4;
    @ViewById protected TextView txtProgresso5;
    @ViewById protected TextView txtProgresso6;
//w
    /* BARRA DE PROGRESSO DOS MÓDULOS */
    @ViewById protected RoundCornerProgressBar barraModulo1;
    @ViewById protected RoundCornerProgressBar barraModulo2;
    @ViewById protected RoundCornerProgressBar barraModulo3;
    @ViewById protected RoundCornerProgressBar barraModulo4;
    @ViewById protected RoundCornerProgressBar barraModulo5;
    @ViewById protected RoundCornerProgressBar barraModulo6;

    /* MENU PUXÁVEL */
    @ViewById AppBarLayout appBar;
    @ViewById Toolbar toolbar;
    @ViewById protected DrawerLayout drawer_layout;
    @ViewById protected ListView     mListView;
    protected ActionBarDrawerToggle  mAlterna;
    protected String                 mTitulo;

    protected int pontuacaoTotalModulo = 0;

    /*FIM VIEWS*/

    // SUPER VARIÁVEL CONTEXT
    protected Context context = this;

    /*BANCO DE DADOS*/
    GerenciadorBanco DB_PROGRESSO;

    // OBJETOS
    protected NovaJanelaAlerta alerta;
    protected GerenciadorSharedPreferences preferenceManager;

    /* MÉTODOS DE CICLO DE VIDA DO APP */

    public AprenderActivity() {
        alerta = new NovaJanelaAlerta(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DON'T DO THIS !! It will throw a NullPointerException, myTextView is not set yet.
        // myTextView.setText("Date: " + new Date());
    }

    @Override
    protected void onResume() {

        atualizaProgressoUI();

        if(drawer_layout.isDrawerVisible(mListView)) {
            drawer_layout.closeDrawers();
        }

        super.onResume();

    }

    @Override
    public void onBackPressed() {
        alerta.alertDialogSair("Tem certeza que deseja sair?");
    }

    /* FIM CICLO DE VIDA APP*/

    /* CICLO DE VIDA DO MENU PUXÁVEL */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mAlterna.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mAlterna.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mAlterna.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* FIM CICLO DE VIDA MENU PUXÁVEL*/

    /* MÉTODO INICIALIZADOR */
    @AfterViews
    protected void init() {

        preferenceManager = new GerenciadorSharedPreferences(this);

        // BOTÃO SUPERIOR MENU PUXÁVEL
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.appBar.setElevation(0);
            this.toolbar.setElevation(0);
        }

        this.setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mTitulo = getTitle().toString();
        }

        if(!preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getIsLogin())) {
            preferenceManager.escreverFlagBoolean(GerenciadorSharedPreferences.getIsLogin(), true);
        }

        fontes();

        // INSTANCIANDO E CRIANDO O BANCO CASO ELE NÃO EXISTA
        instanciaBanco();

        // MÉTODO QUE INVOCA OS ITENS DO MENU PUXÁVEL
        adicionarItensMenu();

        // MÉTODO DE CONFIGURAÇÃO DO MENU PUXÁVEL
        configurarMenu();

        // PROGRESSO DAS TEXT VIEWS
        progressoTextView();

        // PROGRESSO DAS PROGRESS BARS
        progressBars();

        // BLOQUEANDO OS MÓDULOS AO INICIAR O APP
        bloquearModulos();

        // VERIFICAÇÕES E DESBLOQUEIO DE PROGRESSO DOS MÓDULOS
        desbloquearModulos();


    }

    // MÉTODO QUE INSTANCIA O BANCO CASO ELE NÃO EXISTA
    protected void instanciaBanco() {
        // CRIANDO O OBJETO DO GERENCIADOR DO BANCO
        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
        }


        // TENTANDO PEGAR O ARQUIVO DO BANCO PARA VER SE ELE EXISTE
        File banco = context.getApplicationContext().getDatabasePath(DB_PROGRESSO.getDbName());

        // CASO NÃO EXISTA
        if(!banco.exists()) {
            try {
                // CRIE O BANCO
                DB_PROGRESSO.criarBanco();
            } catch (IOException ioe) {
                throw new Error("Erro de cópia");
            }
        }
    }

    // BLOQUEIA OS MÓDULOS
    protected void bloquearModulos() {

        // SUMINDO COM AS TEXTVIEWS DE PROGRESSO
        txtProgresso1.setVisibility(View.GONE);
        txtProgresso2.setVisibility(View.GONE);
        txtProgresso3.setVisibility(View.GONE);
        txtProgresso4.setVisibility(View.GONE);
        txtProgresso5.setVisibility(View.GONE);
        txtProgresso6.setVisibility(View.GONE);

        // TROCANDO A IMAGEM DO MÓDULO PARA BLOQUEADO
        imgModulo1.setImageResource(R.drawable.btnmodulo1bloqueado);
        imgModulo2.setImageResource(R.drawable.btnmodulo2bloqueado);
        imgModulo3.setImageResource(R.drawable.btnmodulo3bloqueado);
        imgModulo4.setImageResource(R.drawable.btnmodulo4bloqueado);
        imgModulo5.setImageResource(R.drawable.btnmodulo5bloqueado);
        imgModulo6.setImageResource(R.drawable.btnmodulo6bloqueado);
        imgCertificado.setImageResource(R.drawable.btncertificadobloqueado);

        // sumindo com as barras de progresso
        barraModulo1.setVisibility(View.GONE);
        barraModulo2.setVisibility(View.GONE);
        barraModulo3.setVisibility(View.GONE);
        barraModulo4.setVisibility(View.GONE);
        barraModulo5.setVisibility(View.GONE);
        barraModulo6.setVisibility(View.GONE);

        txtNota1.setVisibility(View.GONE);
        txtNota2.setVisibility(View.GONE);
        txtNota3.setVisibility(View.GONE);
        txtNota4.setVisibility(View.GONE);
        txtNota5.setVisibility(View.GONE);
        txtNota6.setVisibility(View.GONE);

    }

    // DESBLOQUEIA OS MÓDULOS
    protected void desbloquearModulo1() {

        if(preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagProva1()) ) {
            imgModulo1.setImageResource(R.drawable.btnmodulo1completo);
            definirNota(1);
            txtNota1.setVisibility(View.VISIBLE);

        } else {
            imgModulo1.setImageResource(R.drawable.btnmodulo1cursando);
            // trazendo a barra de progresso
            barraModulo1.setVisibility(View.VISIBLE);
            txtProgresso1.setVisibility(View.VISIBLE);
        }

    }

    protected void desbloquearModulo2() {
        if( preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagProva2()) ) {
            imgModulo2.setImageResource(R.drawable.btnmodulo2completo);
            txtNota2.setVisibility(View.VISIBLE);
        } else {
            imgModulo2.setImageResource(R.drawable.btnmodulo2cursando);
            // trazendo a barra de progresso
            barraModulo2.setVisibility(View.VISIBLE);

            txtProgresso2.setVisibility(View.VISIBLE);
        }


    }

    protected void desbloquearModulo3() {
        if( preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagProva3()) ) {
            imgModulo3.setImageResource(R.drawable.btnmodulo3completo);
            txtNota3.setVisibility(View.VISIBLE);
        } else {
            imgModulo3.setImageResource(R.drawable.btnmodulo3cursando);
            // trazendo a barra de progresso
            barraModulo3.setVisibility(View.VISIBLE);

            txtProgresso3.setVisibility(View.VISIBLE);
        }


    }

    protected void desbloquearModulo4() {
        if( preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagProva4()) ) {
            imgModulo4.setImageResource(R.drawable.btnmodulo4completo);
            txtNota4.setVisibility(View.VISIBLE);
        } else {
            imgModulo4.setImageResource(R.drawable.btnmodulo4cursando);
            // trazendo a barra de progresso
            barraModulo4.setVisibility(View.VISIBLE);

            txtProgresso4.setVisibility(View.VISIBLE);
        }


    }

    protected void desbloquearModulo5() {
        if( preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagProva5()) ) {
            imgModulo5.setImageResource(R.drawable.btnmodulo5completo);
            txtNota5.setVisibility(View.VISIBLE);
        } else {
            imgModulo5.setImageResource(R.drawable.btnmodulo5cursando);
            // trazendo a barra de progresso
            barraModulo5.setVisibility(View.VISIBLE);

            txtProgresso5.setVisibility(View.VISIBLE);
        }


    }

    protected void desbloquearModulo6() {
        if( preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagProva6()) ) {
            imgModulo6.setImageResource(R.drawable.btnmodulo6completo);
            txtNota6.setVisibility(View.VISIBLE);
        } else {
            imgModulo6.setImageResource(R.drawable.btnmodulo6cursando);
            // trazendo a barra de progresso
            barraModulo6.setVisibility(View.VISIBLE);

            txtProgresso6.setVisibility(View.VISIBLE);
        }


    }

    protected void desbloquearCertificado() {
        if( preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagProva6()) ) {
            imgCertificado.setImageResource(R.drawable.btncertificadocursando);
        } else if( preferenceManager.lerFlagBoolean(GerenciadorSharedPreferences.getFlagCertificadoGerado()) ) {
            imgCertificado.setImageResource(R.drawable.btncertificadocompleto);
        }

    }

    protected void definirNota(int modulo) {
        String stringNota = "";
        int pontuacaoModulo = DB_PROGRESSO.verificarPontuacao(modulo);

        switch (modulo) {
            case 1:
                if(pontuacaoModulo <= 3000) {
                    stringNota = "C";
                } else if(pontuacaoModulo <= 4000) {
                    stringNota = "C++";
                } else if(pontuacaoModulo <= 5000) {
                    stringNota = "B";
                } else if(pontuacaoModulo <= 6000) {
                    stringNota = "B+";
                } else if(pontuacaoModulo <= 7000) {
                    stringNota = "A";
                } else if(pontuacaoModulo <= 8000) {
                    stringNota = "A++";
                }
                txtNota1.setText(stringNota);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }

    }


    // VERIFICANDO E MUDANDO O PROGRESSO DAS TEXTVIEWS
    protected void desbloquearModulos() {
        // SWITCH VAI ATÉ O BANCO E VERIFICA EM QUAL MÓDULO O USUÁRIO ESTÁ
        switch(DB_PROGRESSO.verificaProgressoModulo()) {
            // DE ACORDO COM O PROGRESSO (1 A 8), OS MÓDULOS SÃO LIBERADOS
            case 1:
                desbloquearModulo1();
                break;
            case 2:
                desbloquearModulo1();
                desbloquearModulo2();
                break;
            case 3:
                desbloquearModulo1();
                desbloquearModulo2();
                desbloquearModulo3();
                break;
            case 4:
                desbloquearModulo1();
                desbloquearModulo2();
                desbloquearModulo3();
                desbloquearModulo4();
                break;
            case 5:
                desbloquearModulo1();
                desbloquearModulo2();
                desbloquearModulo3();
                desbloquearModulo4();
                desbloquearModulo5();
                break;
            case 6:
                desbloquearModulo1();
                desbloquearModulo2();
                desbloquearModulo3();
                desbloquearModulo4();
                desbloquearModulo5();
                desbloquearModulo6();
                break;
            case 7:
                desbloquearModulo1();
                desbloquearModulo2();
                desbloquearModulo3();
                desbloquearModulo4();
                desbloquearModulo5();
                desbloquearModulo6();
                desbloquearCertificado();
        }
    }

    // VERIFICA O PROGRESSO DO USUÁRIO REFERENTE AS ETAPAS DE CADA MÓDULO E ATRIBUI A TEXTVIEW
    protected void progressoTextView() {
        txtProgresso1.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(1)) + "/9 etapas concluídas");
        txtProgresso2.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(2)) + "/6 etapas concluídas");
        txtProgresso3.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(3)) + "/3 etapas concluídas");
        txtProgresso4.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(4)) + "/6 etapas concluídas");
        txtProgresso5.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(5)) + "/1 etapas concluídas");
        txtProgresso6.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(6)) + "/10 etapas concluídas");
    }

    // CONFIGURA O PROGRESSO DAS PROGRESS BARS
    protected void progressBars() {
        barraModulo1.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(1)) ) );
        barraModulo2.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(2)) ) );
        barraModulo3.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(3)) ) );
        barraModulo4.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(4)) ) );
        barraModulo5.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(5)) ) );
        barraModulo6.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(6)) ) );
    }

    /* LISTENERS */

    @Click
    protected void btnModulo1() {
        // SE O MÓDULO ESTIVER DESBLOQUEADO, INICIAR A ACTIVITY
        if(DB_PROGRESSO.verificaProgressoModulo() >= 1) {
            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
            imgModulo1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), EtapasModulo1Activity.class));
            //finish();
        } else { // SENÃO EXIBIR ALERTDIALOG
            alertaModuloBloqueado();
        }
    }

    /*@Click
    protected void btnModulo2() {
        if(DB_PROGRESSO.verificaProgressoModulo() >= 2) {
            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
            imgModulo2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), EtapasModulo2Activity.class));
            //finish();
        } else {
            alertaModuloBloqueado();
        }
    }

    @Click
    protected void btnModulo3() {

        if(DB_PROGRESSO.verificaProgressoModulo() >= 3) {
            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
            imgModulo3.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), EtapasModulo3Activity.class));
            //finish();
        } else {
            alertaModuloBloqueado();
        }

    }

    @Click
    protected void btnModulo4() {

        if(DB_PROGRESSO.verificaProgressoModulo() >= 4) {
            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
            imgModulo4.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), EtapasModulo4Activity.class));
            //finish();
        } else {
            alertaModuloBloqueado();
        }

    }

    @Click
    protected void btnModulo5() {
        if(DB_PROGRESSO.verificaProgressoModulo() >= 5) {
            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
            imgModulo5.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), EtapasModulo5Activity.class));
            //finish();
        } else {
            alertaModuloBloqueado();
        }
    }

    @Click
    protected void btnModulo6() {
        if(DB_PROGRESSO.verificaProgressoModulo() >= 6) {
            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
            imgModulo6.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), EtapasModulo6Activity.class));
            //finish();
        } else {
            alertaModuloBloqueado();
        }
    }*/

    @Click
    protected void btnCertificado() {
        if(DB_PROGRESSO.verificaProgressoModulo() >= 7) {
            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
            imgCertificado.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), CertificadoActivity.class));
        } else {
            imgCertificado.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            startActivity(new Intent(getApplicationContext(), CertificadoIncompleto.class));
        }
    }

    @ItemClick
    protected void mListView(int position) {
        switch(position) {
            case 0: // MÓDULOS
                drawer_layout.closeDrawers();
                break;
            case 1: // PERFIL
                Intent i = new Intent(AprenderActivity.this, GerenciarPerfilActivity.class);
                startActivity(i);
                finish();
                break;
            case 2: // GLOSSARIO
                startActivity(new Intent(getApplicationContext(), ContainerComandosGlossario.class));
                break;
            case 3: // CONFIGURAÇÕES
                startActivity(new Intent(getApplicationContext(), ActivityConfig_.class));
                break;
            case 4:
                break;
        }
    }

    /* FIM LISTENERS */

    /* UI THREADS */

    @UiThread(delay = 100)
    protected void atualizaProgressoUI() {
        bloquearModulos();
        desbloquearModulos();
        progressoTextView();
        progressBars();
    }

    /*FIM UI THREADS*/

    // MÉTODO QUE RECEBE OS ITENS QUE VÃO PRO MENU PUXÁVEL, ADAPTA, E OS COLOCA LÁ
    protected void adicionarItensMenu() {
        // Adapter String <mais em https://teamtreehouse.com/library/android-lists-and-adapters>
        ArrayAdapter<String> mAdapter;
        // ITENS DO MENU
        String[] itensMenu = {"Módulos", "Perfil", "Glossário", "Configurações", "Avalie-nos!"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itensMenu);
        mListView.setAdapter(mAdapter);
    }

    // MÉTODO DE CONFIGURAÇÃO DO MENU
    protected void configurarMenu() {
        // CONFIGURANDO O OBJETO QUE ABRE O MENU QUANDO O BOTÃO SUPERIOR É APERTADO
        mAlterna = new ActionBarDrawerToggle(this, drawer_layout, R.string.mAbrir,
                R.string.mFechar){
            // Método chamado quando o menu abre
            public void aoAbrirMenu(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // CRIA CHAMADA PARA O MÉTODO onPrepareOptionsMenu()
            }
            // Método chamado quando o menu fecha
            public void aoFecharMenu(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitulo);
                invalidateOptionsMenu(); // CRIA CHAMADA PARA O MÉTODO onPrepareOptionsMenu()
            }
        };

        mAlterna.setDrawerIndicatorEnabled(true);
        drawer_layout.addDrawerListener(mAlterna);
    }

    // MÉTODO DE CONFIGURAÇÃO DA JANELA DE ALERTA PARA OS MODULOS
    protected  void alertaModuloBloqueado() {
        alerta.alertDialogBloqueado("Módulo Bloqueado", "Complete os módulos anteriores para desbloquear este.");
    }

    /*FONTES*/
    protected void fontes() {
        Typeface notosans = Typeface.createFromAsset(getAssets(), "fonts/notosans/regular.ttf");
        txtTitulo1.setTypeface(notosans);
        txtTitulo2.setTypeface(notosans);
        txtTitulo3.setTypeface(notosans);
        txtTitulo4.setTypeface(notosans);
        txtTitulo5.setTypeface(notosans);
        txtTitulo6.setTypeface(notosans);
        txtTituloCertificado.setTypeface(notosans);

        txtProgresso1.setTypeface(notosans);
        txtProgresso2.setTypeface(notosans);
        txtProgresso3.setTypeface(notosans);
        txtProgresso4.setTypeface(notosans);
        txtProgresso5.setTypeface(notosans);
        txtProgresso6.setTypeface(notosans);
    }
}

