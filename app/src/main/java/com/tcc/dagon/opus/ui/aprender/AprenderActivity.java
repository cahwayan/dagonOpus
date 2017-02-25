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
import com.tcc.dagon.opus.telas.aprender.menulateral.GerenciarPerfilActivity;
import com.tcc.dagon.opus.telas.certificado.CertificadoActivity;
import com.tcc.dagon.opus.telas.certificado.CertificadoIncompleto;
import com.tcc.dagon.opus.telas.aprender.menulateral.glossario.ContainerComandosGlossario;
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
import java.util.ArrayList;
import java.util.List;

import com.tcc.dagon.opus.core.aprender.GerenciadorProgressoModulos;

import static java.lang.String.valueOf;


/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE ABRIGA AS FUNÇÕES DA TELA DE MÓDULOS
 *
 */

@EActivity(R.layout.activity_aprender)
public class AprenderActivity
        extends AppCompatActivity {

    @ViewById protected TextView txtProgresso1;
    @ViewById protected TextView txtProgresso2;
    @ViewById protected TextView txtProgresso3;
    @ViewById protected TextView txtProgresso4;
    @ViewById protected TextView txtProgresso5;
    @ViewById protected TextView txtProgresso6;

    private List<Modulo> listModulos;
    private List<LinearLayout> listBtnModulos;
    private List<TextView> listTxtNotas;
    private List<ImageView> listImgModulos;
    private List<TextView> listTitulosModulos;
    private List<TextView> listTxtProgressoModulos;
    private List<RoundCornerProgressBar> listBarrasProgresso;

    private int[] idStringsTxtProgresso;
    private int[] idImagensCursando;
    private int[] idImagensBloqueado;
    private int[] idImagensCompleto;

    /* MENU PUXÁVEL */
    @ViewById AppBarLayout appBar;
    @ViewById Toolbar toolbar;
    @ViewById protected DrawerLayout drawer_layout;
    @ViewById protected ListView     mListView;
    protected ActionBarDrawerToggle  mAlterna;
    protected String                 mTitulo;

    /*FIM VIEWS*/

    // SUPER VARIÁVEL CONTEXT
    protected Context context = this;

    /*BANCO DE DADOS*/
    GerenciadorBanco DB_PROGRESSO;

    private int contadorListeners;

    // OBJETOS
    protected NovaJanelaAlerta janelaAlerta;
    protected GerenciadorSharedPreferences preferenceManager;
    private GerenciadorProgressoModulos gerenciadorProgresso;

    private int progressoAtual;

    /* MÉTODOS DE CICLO DE VIDA DO APP */

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
        janelaAlerta.alertDialogSair("Tem certeza que deseja sair?");
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

        janelaAlerta = new NovaJanelaAlerta(this);
        preferenceManager = new GerenciadorSharedPreferences(this);
        gerenciadorProgresso = new GerenciadorProgressoModulos();
        this.progressoAtual = preferenceManager.lerFlagInt(preferenceManager.getPrefProgressoModulo());

        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
            criarBancoCasoNaoExista();
        }

        if(!preferenceManager.isUserLogged()) {
            String isLogin = GerenciadorSharedPreferences.getIsLogin();
            preferenceManager.escreverFlagBoolean(isLogin, true);
        }

        listModulos = new ArrayList<>();
        listTxtNotas = new ArrayList<>();
        listBtnModulos = new ArrayList<>();
        listImgModulos = new ArrayList<>();
        listTitulosModulos = new ArrayList<>();
        listTxtProgressoModulos = new ArrayList<>();
        listBarrasProgresso = new ArrayList<>();

        listModulos.add(new Modulo(1));
        listModulos.add(new Modulo(2));
        listModulos.add(new Modulo(3));
        listModulos.add(new Modulo(4));
        listModulos.add(new Modulo(5));
        listModulos.add(new Modulo(6));
        listModulos.add(new Modulo(7));

        listTxtNotas.add((TextView) findViewById(R.id.txtNota1));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota2));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota3));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota4));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota5));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota6));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota6 /* 7 */));

        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo1));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo2));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo3));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo4));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo5));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo6));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnCertificado));

        listImgModulos.add((ImageView) findViewById(R.id.imgModulo1));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo2));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo3));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo4));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo5));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo6));
        listImgModulos.add((ImageView) findViewById(R.id.imgCertificado));

        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo1));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo2));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo3));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo4));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo5));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo6));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTituloCertificado));

        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso1));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso2));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso3));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso4));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso5));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso6));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso6 /* 7 */ ));

        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo1));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo2));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo3));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo4));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo5));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo6));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo6 /*  7 */));

        idStringsTxtProgresso = new int[] {R.string.txtProgressoModulo1, R.string.txtProgressoModulo2,
                                           R.string.txtProgressoModulo3, R.string.txtProgressoModulo4,
                                           R.string.txtProgressoModulo5, R.string.txtProgressoModulo6,
                                           R.string.txtProgressoModulo7 /* 7 */};

        idImagensBloqueado = new int[] {R.drawable.btnmodulo1bloqueado, R.drawable.btnmodulo2bloqueado,
                                        R.drawable.btnmodulo3bloqueado, R.drawable.btnmodulo4bloqueado,
                                        R.drawable.btnmodulo5bloqueado, R.drawable.btnmodulo6bloqueado,
                                        R.drawable.btncertificadobloqueado};

        idImagensCursando = new int[] {R.drawable.btnmodulo1cursando, R.drawable.btnmodulo2cursando,
                                        R.drawable.btnmodulo3cursando, R.drawable.btnmodulo4cursando,
                                        R.drawable.btnmodulo5cursando, R.drawable.btnmodulo6cursando,
                                        R.drawable.btncertificadocursando};

        idImagensCompleto = new int[] {R.drawable.btnmodulo1completo, R.drawable.btnmodulo2completo,
                                        R.drawable.btnmodulo3completo, R.drawable.btnmodulo4completo,
                                        R.drawable.btnmodulo5completo, R.drawable.btnmodulo6completo,
                                        R.drawable.btncertificadocompleto};

        addClickListeners();

        fontes();

        adicionarItensMenu();

        configurarMenu();

        loadProgressBars();

        carregarProgresso();
    }

    /*
    * Esse método é responsável por carregar o progresso e ajustar a UI de acordo com ele.
    * Os módulos são guardados em uma lista, e eles carregam informações a respeito do progresso atual.
    * Então, esse método percorre essa lista, e decide para cada caso como configurar a UI do módulo
    */

    @UiThread
    private void carregarProgresso() {

        for(int i = 0; i < listModulos.size(); i++) {

            Modulo modulo = listModulos.get(i);
            String situacaoModulo = modulo.getSituacao();
            TextView nota = listTxtNotas.get(i);
            ImageView iconeModulo = listImgModulos.get(i);
            TextView txtProgressoModulo = listTxtProgressoModulos.get(i);
            RoundCornerProgressBar barraProgressoModulo = listBarrasProgresso.get(i);

            switch(situacaoModulo) {
                case Modulo.CURSANDO:
                    /*
                    * Um módulo que está na situação cursando deve mostrar o ícone do módulo em um azul mais claro,
                    *  o título do módulo, a textView de progresso, e a barra de progresso. A nota deve ser escondida
                    */
                    int numProgresso = DB_PROGRESSO.getProgressoEtapa(i + 1);
                    String stringTxtProgresso = String.valueOf(numProgresso) + idStringsTxtProgresso[i];

                    nota.setVisibility(View.GONE);
                    iconeModulo.setImageResource(idImagensCursando[i]);
                    txtProgressoModulo.setText(stringTxtProgresso);
                    txtProgressoModulo.setVisibility(View.VISIBLE);
                    float progresso = DB_PROGRESSO.getProgressoEtapa(i + 1);
                    barraProgressoModulo.setProgress(progresso);



                    barraProgressoModulo.setVisibility(View.VISIBLE);
                    break;

                case Modulo.COMPLETO:
                    /*
                    * Um módulo completo deve mostrar somente o ícone do módulo, em azul normal,
                    * a nota, o título do módulo, e a textView de progresso, com um texto "completo"
                    */
                    nota.setText(modulo.getStringNota());
                    nota.setVisibility(View.VISIBLE);
                    iconeModulo.setImageResource(idImagensCompleto[i]);
                    barraProgressoModulo.setVisibility(View.GONE);
                    txtProgressoModulo.setText(R.string.moduloCompleto);
                    break;

                case Modulo.BLOQUEADO:
                    /*
                    * Um módulo bloqueado deve mostrar somente o ícone do módulo, em cinza, e o
                    * título daquele módulo. A barra de progresso, a textView de progresso e a nota
                    * precisam ser escondidos
                    */
                    nota.setVisibility(View.GONE);
                    iconeModulo.setImageResource(idImagensBloqueado[i]);
                    barraProgressoModulo.setVisibility(View.GONE);
                    txtProgressoModulo.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

    }

    // MÉTODO QUE INSTANCIA O BANCO CASO ELE NÃO EXISTA
    protected void criarBancoCasoNaoExista() {
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
                //txtNota1.setText(stringNota);
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


    // CONFIGURA O PROGRESSO DAS PROGRESS BARS
    @UiThread
    protected void loadProgressBars() {
        gerenciadorProgresso.loadProgressBars(listBarrasProgresso, DB_PROGRESSO);
    }

    /* LISTENERS */

    private void addClickListeners() {

        final List<Class> listClassesEtapas = new ArrayList<>();
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);

        for(contadorListeners = 0; contadorListeners < listModulos.size() - 1; contadorListeners++) {

            final int numModulo = listModulos.get(contadorListeners).getNumModulo();

            if(numModulo == 7 /*NUMERO DO MÒDULO CERTIFICADO */) {

                if(progressoAtual >= 7) {
                    listBtnModulos.get(contadorListeners).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                            listImgModulos.get(contadorListeners).startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                            startActivity(new Intent(getApplicationContext(), CertificadoActivity.class));
                        }
                    });
                } else {
                    listBtnModulos.get(contadorListeners).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                            listImgModulos.get(contadorListeners).startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                            startActivity(new Intent(getApplicationContext(), CertificadoIncompleto.class));
                        }
                    });
                }

                continue;

            }

            /* LIBERADO */
            if(progressoAtual >= numModulo) {
                listBtnModulos.get(contadorListeners).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listImgModulos.get(contadorListeners).startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                        startActivity(new Intent(getApplicationContext(), listClassesEtapas.get(contadorListeners)));
                    }
                });
                /* BLOQUEADO */
            } else if(progressoAtual < numModulo) {
                listBtnModulos.get(contadorListeners).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertaModuloBloqueado();
                    }
                });
            }

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
        carregarProgresso();
        loadProgressBars();
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
        janelaAlerta.alertDialogBloqueado("Módulo Bloqueado", "Complete os módulos anteriores para desbloquear este.");
    }

    /*FONTES*/
    protected void fontes() {

        Typeface notosans = Typeface.createFromAsset(getAssets(), "fonts/notosans/regular.ttf");

        for(TextView titulo : listTitulosModulos) {
            titulo.setTypeface(notosans);
        }

        for(TextView txtProgresso : listTxtProgressoModulos) {
            txtProgresso.setTypeface(notosans);
        }

    }

    class Modulo {
        private int numModulo;
        private String stringNota;
        private String situacao;
        final static String CURSANDO = "cursando";
        final static String COMPLETO = "completo";
        final static String BLOQUEADO = "bloqueado";


        Modulo(int numModulo) {
            this.numModulo = numModulo;
            this.stringNota = getNota();
            this.setSituacao();
        }

        String getNota() {
            return preferenceManager.lerFlagString(preferenceManager.getPrefNota(this.numModulo));
        }

        String getStringNota() {
            return this.stringNota;
        }

        int getNumModulo() {
            return this.numModulo;
        }

        private String getSituacao() {
            return this.situacao;
        }

        private void setSituacao() {
            if(this.numModulo == progressoAtual) {
                this.situacao = CURSANDO;
            } else if (this.numModulo < progressoAtual) {
                this.situacao = COMPLETO;
            } else {
                this.situacao = BLOQUEADO;
            }
        }


    }
}

