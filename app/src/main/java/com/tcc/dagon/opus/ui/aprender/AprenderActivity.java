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
import org.androidannotations.annotations.Background;
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

    private List<Modulo> listModulos;
    private List<LinearLayout> listBtnModulos;
    private List<TextView> listTxtNotas;
    private List<ImageView> listImgModulos;
    private List<TextView> listTitulosModulos;
    private List<TextView> listTxtProgressoModulos;
    private List<RoundCornerProgressBar> listBarrasProgresso;
    List<Class> listClassesEtapas;

    private String[] stringsTxtProgresso;
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

    // OBJETOS
    protected NovaJanelaAlerta janelaAlerta;
    protected GerenciadorSharedPreferences preferenceManager;
    private GerenciadorProgressoModulos gerenciadorProgresso;

    private static int progressoAtual;

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
        progressoAtual = preferenceManager.lerFlagInt(preferenceManager.getPrefProgressoModulo());

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
        listClassesEtapas = new ArrayList<>();

        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);
        listClassesEtapas.add(EtapasModulo1Activity.class);

        listTxtNotas.add((TextView) findViewById(R.id.txtNota1));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota2));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota3));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota4));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota5));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota6));

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

        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo1));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo2));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo3));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo4));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo5));
        listBarrasProgresso.add((RoundCornerProgressBar) findViewById(R.id.barraModulo6));

        stringsTxtProgresso = new String[] {context.getString(R.string.txtProgressoModulo1),
                                                context.getString(R.string.txtProgressoModulo2),
                                                context.getString(R.string.txtProgressoModulo3),
                                                context.getString(R.string.txtProgressoModulo4),
                                                context.getString(R.string.txtProgressoModulo5),
                                                context.getString(R.string.txtProgressoModulo6)};

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

        carregarModulos();

        fontes();

        adicionarItensMenu();

        configurarMenu();
    }

    /*
    * Esse método é responsável por carregar o progresso e ajustar a UI de acordo com ele.
    * Os módulos são guardados em uma lista, e eles carregam informações a respeito do progresso atual.
    * Então, esse método guarda os módulos que são conscientes de seu estado atual
    */

    @UiThread
    protected void carregarModulos() {
        listModulos.add(addModulo(0));
        listModulos.add(addModulo(1));
        listModulos.add(addModulo(2));
        listModulos.add(addModulo(3));
        listModulos.add(addModulo(4));
        listModulos.add(addModulo(5));
        listModulos.add(addModulo(6));
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

    @UiThread
    protected void atualizaProgressoUI() {
        progressoAtual = preferenceManager.lerFlagInt(preferenceManager.getPrefProgressoModulo());
        for(Modulo modulo : listModulos) {
            modulo.configurarModulo();
        }
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

    private Modulo addModulo(int numModulo) {

        Modulo modulo;

        // novo objeto modulo
        if(numModulo == 6 /* CERTIFICADO */ ) {
            modulo = new ModuloCertificado();
        } else if(numModulo == progressoAtual) {
            modulo  = new ModuloCursando();
        } else if (numModulo < progressoAtual) {
            modulo = new ModuloCompleto();
        } else {
            modulo = new ModuloBloqueado();
        }

        modulo.setStringNota(preferenceManager.getPrefNota(numModulo));
        modulo.setNumModulo(numModulo);
        modulo.setClickListener();
        return modulo;
    }

    interface Modulo {
        void setStringNota(String nota);
        void setNumModulo(int numModulo);
        void configurarModulo();
        void setClickListener();
    }

    abstract class ModuloImp implements Modulo {

        int numModulo;
        private String stringNota;

        public abstract void configurarModulo();

        @Override
        public void setNumModulo(int numModulo) {
            this.numModulo = numModulo;
        }

        @Override
        public void setStringNota(String nota) {
            this.stringNota = nota;
        }

        protected String getStringNota() {
            return this.stringNota;
        }

        @Override
        public void setClickListener() {
            listBtnModulos.get(numModulo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listImgModulos.get(numModulo).startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));

                    if(numModulo == 6) {
                        if(progressoAtual == 6) {
                            clickCertificadoLiberado();
                        } else if (progressoAtual < 6){
                            clickCertificadoBloqueado();
                        } else if(progressoAtual > 6) {
                            clickCertificadoGerado();
                        }
                        return;
                    }

                    if(numModulo <= progressoAtual) {
                        clickLiberado();
                    } else {
                        clickBloqueado();
                    }
                }
            });
        }

        private void clickBloqueado() {
            alertaModuloBloqueado();
        }

        private void clickLiberado() {
            startActivity(new Intent(getApplicationContext(), listClassesEtapas.get(numModulo)));
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

        }

    }

    class ModuloCursando extends ModuloImp implements Modulo {

        @Override
        public void configurarModulo() {
            /*
             * Um módulo que está na situação cursando deve mostrar o ícone do módulo em um azul mais claro,
             *  o título do módulo, a textView de progresso, e a barra de progresso. A nota deve ser escondida
            */

            int progresso = DB_PROGRESSO.getProgressoEtapa(numModulo + 1);
            String stringTxtProgresso = String.valueOf(progresso) + stringsTxtProgresso[numModulo];
            listTxtNotas.get(numModulo).setVisibility(View.GONE);
            listImgModulos.get(numModulo).setImageResource(idImagensCursando[numModulo]);
            listTxtProgressoModulos.get(numModulo).setText(stringTxtProgresso);
            listTxtProgressoModulos.get(numModulo).setVisibility(View.VISIBLE);

            listBarrasProgresso.get(numModulo).setProgress((float)progresso);
            listBarrasProgresso.get(numModulo).setVisibility(View.VISIBLE);
        }
    }

    class ModuloCompleto extends ModuloImp implements Modulo {

        @Override
        public void configurarModulo() {
            /*
             * Um módulo completo deve mostrar somente o ícone do módulo, em azul normal,
             * a nota, o título do módulo, e a textView de progresso, com um texto "completo"
            */

            listTxtNotas.get(numModulo).setText(getStringNota());
            listTxtNotas.get(numModulo).setVisibility(View.VISIBLE);
            listImgModulos.get(numModulo).setImageResource(idImagensCompleto[numModulo]);
            listBarrasProgresso.get(numModulo).setVisibility(View.GONE);
            listTxtProgressoModulos.get(numModulo).setText(R.string.moduloCompleto);
        }
    }

    class ModuloBloqueado extends ModuloImp implements Modulo{

        @Override
        public void configurarModulo() {
            listTxtNotas.get(numModulo).setVisibility(View.GONE);
            listImgModulos.get(numModulo).setImageResource(idImagensBloqueado[numModulo]);
            listBarrasProgresso.get(numModulo).setVisibility(View.GONE);
            listTxtProgressoModulos.get(numModulo).setVisibility(View.GONE);
        }
    }

    class ModuloCertificado extends ModuloImp implements Modulo {

        @Override
        public void configurarModulo() {
            /*
             * Um módulo completo deve mostrar somente o ícone do módulo, em azul normal,
             * a nota, o título do módulo, e a textView de progresso, com um texto "completo"
            */
            if(progressoAtual < 6) {
                listImgModulos.get(numModulo).setImageResource(idImagensBloqueado[numModulo]);
            } else if(progressoAtual == 6) {
                listImgModulos.get(numModulo).setImageResource(idImagensCursando[numModulo]);
            } else if(progressoAtual > 6) {
                listImgModulos.get(numModulo).setImageResource(idImagensCompleto[numModulo]);
            }
        }

    }

}



