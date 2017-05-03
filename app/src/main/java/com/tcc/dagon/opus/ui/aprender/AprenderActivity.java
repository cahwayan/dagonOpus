package com.tcc.dagon.opus.ui.aprender;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
//import com.tcc.dagon.opus.telas.aprender.menulateral.ActivityConfig_;
import com.tcc.dagon.opus.network.volleyrequests.usuario.SyncUser;
import com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants;
import com.tcc.dagon.opus.ui.telasatransferir.aprender.menulateral.ActivityConfig_;
import com.tcc.dagon.opus.ui.telasatransferir.aprender.menulateral.GerenciarPerfilActivity_;
import com.tcc.dagon.opus.ui.telasatransferir.aprender.menulateral.glossario.ContainerComandosGlossarioActivity;
import com.tcc.dagon.opus.ui.telasatransferir.certificado.CertificadoActivity;
import com.tcc.dagon.opus.ui.telasatransferir.certificado.CertificadoIncompleto;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.data.DB;
//import com.tcc.dagon.opus.ui.etapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.ui.etapas.subclasses.EtapasModulo0;
import com.tcc.dagon.opus.common.OnOffClickListener;
import com.tcc.dagon.opus.data.sharedpreferences.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.common.NovaJanelaAlerta;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.*;

/******************************************************
 * Created by Andrade on 23/09/2016.
 * Essa classe representa a tela de aprender do aplicativo. Ela mostra módulos disponibilizados
 * em vários ícones ao longo da tela. Cada módulo possui um título, um número representando sua posição,
 * uma nota (que vem da pontuação do usuário), uma TextView e uma barra mostrando o progresso do módulo.
 *
 * Cada módulo possui três estados: Completo, Cursando e Bloqueado. Dependendo do progresso atual do usuário,
 * os módulos são dispostos em configurações diferentes. Mais infos sobre o estado dos módulos na subclasse
 * ModuloCursoImp no fim dessa classe.
 *
 * O último módulo sempre será o referente ao certificado. Ele possui uma configuração diferente dos demais.
 * Apesar disso, a classe módulo consegue reconhecer qual é o último módulo, e atribui a ele o comportamento e
 * configuração necessária. A classe reconhece através da constante MODULO_CERTIFICADO que está na interface ModuloCurso.
 * Caso o número de módulos seja alterado (i.e um novo módulo seja adicionado, ou removido), é necessário alterar o
 * valor da constante para o número correspondente ao módulo na última posição da tela (começando a contar do 0).
 *
 * As referências das views são guardadas em listas, e são acessadas automaticamente pelos módulos no índice de suas variáveis que
 * guardam a informação da posição daquele módulo. Também são utilizados arrays para guardar e acessar certos valores da mesma forma.
 *
 * Essa classe utiliza Android Annotations e a que roda no App é uma subclasse dessa, gerada automaticamente chamada AprenderActivity_
 *
 * Essa classe também possui um menu lateral simples, que permite acessar cerca de cinco outras activities
 ********************************************************/

@EActivity(R.layout.activity_aprender)
public class AprenderActivity
        extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    // Listas de views
    private List<ModuloCurso> listModuloCurso;
    private List<LinearLayout> listBtnModulos;
    private List<TextView> listTxtNotas;
    private List<ImageView> listImgModulos;
    private List<TextView> listTitulosModulos;
    private List<TextView> listTxtProgressoModulos;
    private List<RoundCornerProgressBar> listBarrasProgresso;
    List<Class> listClassesEtapas;
    // Fim listas de views

    // Arrays com dados
    private String[] stringsTxtProgresso;
    private int[] idImagensCursando;
    private int[] idImagensBloqueado;
    private int[] idImagensCompleto;
    // Fim arrays com dados

    // Componentes do menu lateral
    @ViewById AppBarLayout appBar;
    @ViewById Toolbar toolbar;
    @ViewById DrawerLayout drawer_layout;
    @ViewById ListView     mListView;
    ActionBarDrawerToggle  mAlterna;
    String                 mTitulo;
    // Fim componentes menu lateral

    // Context
    private Context context = this;

    // Objeto capaz de ler e modificar Shared Preferences
    private GerenciadorSharedPreferences preferenceManager;

    // Variável que guarda o progresso atual do usuário
    private int progressoAtual;

    /*
      * Ciclo de vida
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DON'T DO THIS !! It will throw a NullPointerException, myTextView is not set yet.
        // myTextView.setText("Date: " + new Date());

        Log.d(TAG, "INICIANDO SERVIÇO SYNC USUARIO . . .");
        startSyncUserService();

    }

    @Override
    protected void onResume() {

        if(drawer_layout.isDrawerVisible(mListView)) {
            drawer_layout.closeDrawers();
        }

        super.onResume();
    }

    @Override
    protected  void onStart() {
        super.onStart();
        atualizarUIGeralConformeProgressoAtual();

    }

    private void startSyncUserService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Intent i = new Intent(getApplicationContext(), SyncUser.class);
                        startService(i);
                        Log.d(TAG, "SERVIÇO DE SINCRONIZACAO INICIADO!");
                        final int TRES_MINUTOS = 180000;
                        Log.d(TAG, "Thread indo dormir por 3 minutos! . . .");
                        Thread.sleep(TRES_MINUTOS);
                    } catch(InterruptedException e) {
                        Log.d(TAG, e.toString());
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        NovaJanelaAlerta alerta = new NovaJanelaAlerta(this);
        alerta.alertDialogSair("Tem certeza que deseja sair?");
    }

    /*
      * Fim ciclo de vida do app
    */

    @AfterViews
    protected void init() {

        initSupportActionBar();
        adicionarItensMenuLateral();
        configurarMenuLateral();

        preferenceManager = new GerenciadorSharedPreferences(this);
        carregarListasModulos();

        loadClickListeners();

        // Feito no background
        carregarFontes();
        criarBancoCasoNaoExista();
    }

    /*
    * Inicializa as listas que controlam os objetos módulos
    */
    protected void carregarListasModulos() {

        listClassesEtapas = new ArrayList<>();
        listClassesEtapas.add(EtapasModulo0.class);
        listClassesEtapas.add(EtapasModulo0.class);
        listClassesEtapas.add(EtapasModulo0.class);
        listClassesEtapas.add(EtapasModulo0.class);
        listClassesEtapas.add(EtapasModulo0.class);
        listClassesEtapas.add(EtapasModulo0.class);

        listTxtNotas = new ArrayList<>();
        listTxtNotas.add((TextView) findViewById(R.id.txtNota0));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota1));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota2));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota3));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota4));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota5));

        listBtnModulos = new ArrayList<>();
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo0));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo1));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo2));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo3));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo4));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo5));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnCertificado));

        listImgModulos = new ArrayList<>();
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo0));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo1));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo2));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo3));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo4));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo5));
        listImgModulos.add((ImageView) findViewById(R.id.imgCertificado));

        listTitulosModulos = new ArrayList<>();
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo0));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo1));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo2));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo3));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo4));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo5));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTituloCertificado));

        listTxtProgressoModulos = new ArrayList<>();
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso0));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso1));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso2));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso3));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso4));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso5));

        listBarrasProgresso = new ArrayList<>();
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo0));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo1));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo2));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo3));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo4));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo5));

        stringsTxtProgresso = new String[] {context.getString(R.string.txtProgressoModulo0),
                                            context.getString(R.string.txtProgressoModulo1),
                                            context.getString(R.string.txtProgressoModulo2),
                                            context.getString(R.string.txtProgressoModulo3),
                                            context.getString(R.string.txtProgressoModulo4),
                                            context.getString(R.string.txtProgressoModulo5)};

        idImagensBloqueado = new int[] {R.drawable.btnmodulo0bloqueado, R.drawable.btnmodulo1bloqueado,
                                        R.drawable.btnmodulo2bloqueado, R.drawable.btnmodulo3bloqueado,
                                        R.drawable.btnmodulo4bloqueado, R.drawable.btnmodulo5bloqueado,
                                        R.drawable.btncertificadobloqueado};

        idImagensCursando = new int[] {R.drawable.btnmodulo0cursando, R.drawable.btnmodulo1cursando,
                                        R.drawable.btnmodulo2cursando, R.drawable.btnmodulo3cursando,
                                        R.drawable.btnmodulo4cursando, R.drawable.btnmodulo5cursando,
                                        R.drawable.btncertificadocursando};

        idImagensCompleto = new int[] {R.drawable.btnmodulo0completo, R.drawable.btnmodulo1completo,
                                        R.drawable.btnmodulo2completo, R.drawable.btnmodulo3completo,
                                        R.drawable.btnmodulo4completo, R.drawable.btnmodulo5completo,
                                        R.drawable.btncertificadocompleto};

        listModuloCurso = new ArrayList<>();

        for(int numModulo = 0; numModulo < listBtnModulos.size(); numModulo++) {
            String nota = preferenceManager.getNota(numModulo);
            listModuloCurso.add(new ModuloCursoImp(numModulo, nota));
        }

    }

    protected void initSupportActionBar() {

        // Botão superior do menu lateral
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
    }



    /* Instancia o banco caso ele não exista */
    @Background
    protected void criarBancoCasoNaoExista() {

        DB DB_PROGRESSO = new DB(this);

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

    @UiThread
    protected void atualizarUIGeralConformeProgressoAtual() {

        progressoAtual = preferenceManager.getProgressoModulo();

        for(int i = 0; i < listModuloCurso.size(); i++) {
            String nota = preferenceManager.getNota(i);
            listModuloCurso.get(i).setNota(nota);
        }

        for(ModuloCurso modulo : listModuloCurso) {
            modulo.atualizarEstadoConformeProgressoAtual(progressoAtual);
            this.configurarUIModuloConformeProgressoAtual(modulo);
        }

    }

    /* Invoca a janela de alerta */
    private  void alertaModuloBloqueado() {
        NovaJanelaAlerta.alertDialogBloqueado(this, "Módulo Bloqueado", "Complete os módulos anteriores para desbloquear este.");
    }

    /* Carrega fontes customizadas */
    @UiThread
    protected void carregarFontes() {

        Typeface notosans = Typeface.createFromAsset(getAssets(), "fonts/notosans/regular.ttf");

        for(TextView titulo : listTitulosModulos) {
            titulo.setTypeface(notosans);
        }

        for(TextView txtProgresso : listTxtProgressoModulos) {
            txtProgresso.setTypeface(notosans);
        }
    }

    /*
      * Aqui começa as configurações referentes a UI dos módulos e Click Listeners
    */

    private void configurarUIModuloConformeProgressoAtual(ModuloCurso modulo) {

        int estadoAtual = modulo.getEstadoAtual();
        int numModulo = modulo.getNumModulo();

        if(estadoAtual == ModuloConstants.IS_CERTIFICADO) {
            configurarModuloCertificado(modulo);
            return;
        }

        switch(estadoAtual) {
            case ModuloConstants.IS_BLOQUEADO:
                configurarModuloBloqueado(numModulo);
                break;
            case ModuloConstants.IS_CURSANDO:
                configurarModuloCursando(numModulo);
                break;
            case IS_COMPLETO:
                String nota = modulo.getNota();
                configurarModuloCompleto(numModulo, nota);
                break;
        }

    }

    /*
     * Um módulo completo deve mostrar somente o ícone do módulo, em azul normal,
     * a nota, o título do módulo, e a textView de progresso, com um texto "completo"
    */
    protected void configurarModuloCompleto(int numModulo, String nota) {
        listTxtNotas.get(numModulo).setText(nota);
        listTxtNotas.get(numModulo).setVisibility(View.VISIBLE);
        listImgModulos.get(numModulo).setImageResource(idImagensCompleto[numModulo]);
        listBarrasProgresso.get(numModulo).setVisibility(View.GONE);
        listTxtProgressoModulos.get(numModulo).setText(R.string.moduloCompleto);
    }

    /*
     * Um módulo que está na situação cursando deve mostrar o ícone do módulo em um azul mais claro,
     *  o título do módulo, a textView de progresso, e a barra de progresso. A nota deve ser escondida
    */
    protected void configurarModuloCursando(int numModulo) {
        int progressoEtapasModulo = preferenceManager.getProgressoEtapa(numModulo);

        String stringProgressoModulo = String.valueOf(progressoEtapasModulo) + stringsTxtProgresso[numModulo];

        listTxtNotas.get(numModulo).setText("");
        listImgModulos.get(numModulo).setImageResource(idImagensCursando[numModulo]);
        listTxtProgressoModulos.get(numModulo).setText(stringProgressoModulo);
        listTxtProgressoModulos.get(numModulo).setVisibility(View.VISIBLE);
        listBarrasProgresso.get(numModulo).setProgress((float)progressoEtapasModulo);
        listBarrasProgresso.get(numModulo).setVisibility(View.VISIBLE);
    }

    /*
      * Um módulo bloqueado deve mostrar somente o ícone cinza, e o título do módulo
    */
    protected void configurarModuloBloqueado(int numModulo) {
        listTxtNotas.get(numModulo).setText("");
        listImgModulos.get(numModulo).setImageResource(idImagensBloqueado[numModulo]);
        listBarrasProgresso.get(numModulo).setVisibility(View.GONE);
        listTxtProgressoModulos.get(numModulo).setVisibility(View.GONE);
    }

    /*
      * O módulo certificado deve mostrar sempre somente o ícone e o título. Ele não possui
      * barras nem TextView de progresso, nem nota. Basicamente, deve-se alterar somente o ícone.
    */
    protected void configurarModuloCertificado(ModuloCurso modulo) {

        int numModulo = modulo.getNumModulo();

        if(modulo.isCertificadoBloqueado(progressoAtual)) {
            listImgModulos.get(numModulo).setImageResource(idImagensBloqueado[numModulo]);
        } else if(modulo.isCertificadoLiberado(progressoAtual)) {
            listImgModulos.get(numModulo).setImageResource(idImagensCursando[numModulo]);
        } else if(modulo.isCertificadoGerado(progressoAtual)) {
            listImgModulos.get(numModulo).setImageResource(idImagensCompleto[numModulo]);
        }

    }

    /* CLICK LISTENER */
    protected void loadClickListeners() {

        for(int i = 0; i < listBtnModulos.size(); i++) {

            final ModuloCurso modulo = listModuloCurso.get(i);
            final LinearLayout botaoModulo = listBtnModulos.get(i);

            OnOffClickListener clickListener = new OnOffClickListener() {
                @Override
                public void onOneClick(View v) {
                    botaoModulo.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));

                    switch(modulo.getEstadoAtual()) {
                        case IS_BLOQUEADO:
                            clickBloqueado();
                            break;
                        case IS_CURSANDO:
                            clickLiberado(modulo);
                            break;
                        case IS_COMPLETO:
                            clickLiberado(modulo);
                            break;
                        case IS_CERTIFICADO:
                            clickCertificado(modulo);
                            break;
                        default:
                            break;
                    }
                }
            };

            botaoModulo.setOnClickListener(clickListener);
        }
    }


    private void clickBloqueado() {
        alertaModuloBloqueado();
    }

    private void clickLiberado(ModuloCurso modulo) {
        int numModulo = modulo.getNumModulo();
        int qtdEtapas = modulo.getQtdEtapasModulo();
        String tituloModulo = listTitulosModulos.get(numModulo).getText().toString();
        Intent i = new Intent(context, listClassesEtapas.get(numModulo));
        i.putExtra("numModulo", numModulo);
        i.putExtra("qtdEtapas", qtdEtapas);
        i.putExtra("tituloModulo", tituloModulo);
        startActivity(i);
    }

    private void clickCertificado(ModuloCurso modulo) {
        if(modulo.isCertificadoBloqueado(progressoAtual)) {
            clickCertificadoBloqueado();
        } else if (modulo.isCertificadoLiberado(progressoAtual)) {
            clickCertificadoLiberado();
        } else if(modulo.isCertificadoGerado(progressoAtual)) {
            clickCertificadoGerado();
        }
    }

    private void clickCertificadoBloqueado() {
        // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
        startActivity(new Intent(getApplicationContext(), CertificadoIncompleto.class));
    }

    private void clickCertificadoLiberado() {
        // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
        startActivity(new Intent(getApplicationContext(), CertificadoActivity.class));
    }

    private void clickCertificadoGerado() {
        // comportamento de tela que nao permite mais gerar novos certificados
    }

    /*
      * Ciclo de vida do menu lateral
    */

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
        return mAlterna.onOptionsItemSelected(item);
    }

    @ItemClick
    protected void mListView(int position) {
        switch(position) {
            case 0: // MÓDULOS
                drawer_layout.closeDrawers();
                break;
            case 1: // PERFIL
                Intent i = new Intent(AprenderActivity.this, GerenciarPerfilActivity_.class);
                startActivity(i);
                finish();
                break;
            case 2: // GLOSSARIO
                startActivity(new Intent(getApplicationContext(), ContainerComandosGlossarioActivity.class));
                break;
            case 3: // CONFIGURAÇÕES
                startActivity(new Intent(getApplicationContext(), ActivityConfig_.class));
                break;
            case 4:
                break;
        }
    }

    /* Configuração do menu lateral */
    protected void adicionarItensMenuLateral() {
        // Adapter String <mais em https://teamtreehouse.com/library/android-lists-and-adapters>
        ArrayAdapter<String> mAdapter;
        // ITENS DO MENU
        String[] itensMenu = {"Módulos", "Perfil", "Glossário", "Configurações", "Avalie-nos!"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itensMenu);
        mListView.setAdapter(mAdapter);
    }


    protected void configurarMenuLateral() {
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

    /* Fim configuração do menu lateral */

    /*
      * Fim ciclo de vida do menu lateral
    */


}



