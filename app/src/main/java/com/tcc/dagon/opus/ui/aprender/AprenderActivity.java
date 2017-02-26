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
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.GerenciadorSharedPreferences;
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
import com.tcc.dagon.opus.utils.gerenciadorsharedpreferences.Preferencias;

/******************************************************
 * Created by Andrade on 23/09/2016.
 * Essa classe representa a tela de aprender do aplicativo. Ela mostra módulos disponibilizados
 * em vários ícones ao longo da tela. Cada módulo possui um título, um número representando sua posição,
 * uma nota (que vem da pontuação do usuário), uma TextView e uma barra mostrando o progresso do módulo.
 *
 * Cada módulo possui três estados: Completo, Cursando e Bloqueado. Dependendo do progresso atual do usuário,
 * os módulos são dispostos em configurações diferentes. Mais infos sobre o estado dos módulos na subclasse
 * ModuloImp no fim dessa classe.
 *
 * O último módulo sempre será o referente ao certificado. Ele possui uma configuração diferente dos demais.
 * Apesar disso, a classe módulo consegue reconhecer qual é o último módulo, e atribui a ele o comportamento e
 * configuração necessária. A classe reconhece através da constante MODULO_CERTIFICADO que está na interface Modulo.
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

    // Listas de views
    private List<Modulo> listModulos;
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
    @ViewById protected DrawerLayout drawer_layout;
    @ViewById protected ListView     mListView;
    protected ActionBarDrawerToggle  mAlterna;
    protected String                 mTitulo;
    // Fim componentes menu lateral

    // Context
    protected Context context = this;

    // Objeto de acesso ao banco de dados
    GerenciadorBanco DB_PROGRESSO;

    // Objeto capaz de invocar janelas de alerta
    protected NovaJanelaAlerta janelaAlerta;

    // Objeto capaz de ler e modificar Shared Preferences
    protected Preferencias preferenceManager;

    // Variável estática que guarda o progresso atual do usuário
    private static int progressoAtual;

    /*
      * Métodos de ciclo de vida do app
    */

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
    protected  void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        janelaAlerta.alertDialogSair("Tem certeza que deseja sair?");
    }

    /*
      * Fim ciclo de vida do app
    */

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
        if (mAlterna.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
      * Fim ciclo de vida do menu lateral
    */

    /*
      * Métodos inicializadores
    */

    @AfterViews
    protected void init() {
        initSupportActionBar();
        initObjetos();
        carregarModulos();
        carregarFontes();
        adicionarItensMenuLateral();
        configurarMenuLateral();
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

    protected void initObjetos() {

        janelaAlerta = new NovaJanelaAlerta(this);
        preferenceManager = new GerenciadorSharedPreferences(this);
        progressoAtual = preferenceManager.getProgressoModulo();

        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
            criarBancoCasoNaoExista();
        }

        if(!preferenceManager.lerFlagBoolean(Preferencias.isLogin)) {
            preferenceManager.modFlag(Preferencias.isLogin, true);
        }
    }

    /*
      * Fim métodos inicializadores
    */

    /*
    * Esse método é responsável por carregar o progresso e ajustar a UI de acordo com ele.
    * Os módulos são guardados em uma lista, e eles carregam o seu número de identificação.
    * Então, esse método guarda os módulos, que sabem seu número, e respondem de acordo
    * com o progresso atual, alterando a interface e o clique.
    */
    @Background
    protected void carregarModulos() {

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

        listTxtNotas.add((TextView) findViewById(R.id.txtNota0));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota1));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota2));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota3));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota4));
        listTxtNotas.add((TextView) findViewById(R.id.txtNota5));

        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo0));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo1));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo2));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo3));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo4));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnModulo5));
        listBtnModulos.add((LinearLayout) findViewById(R.id.btnCertificado));

        listImgModulos.add((ImageView) findViewById(R.id.imgModulo0));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo1));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo2));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo3));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo4));
        listImgModulos.add((ImageView) findViewById(R.id.imgModulo5));
        listImgModulos.add((ImageView) findViewById(R.id.imgCertificado));

        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo0));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo1));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo2));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo3));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo4));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTitulo5));
        listTitulosModulos.add((TextView) findViewById(R.id.txtTituloCertificado));

        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso0));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso1));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso2));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso3));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso4));
        listTxtProgressoModulos.add((TextView) findViewById(R.id.txtProgresso5));

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

        listModulos.add(addModulo(0));
        listModulos.add(addModulo(1));
        listModulos.add(addModulo(2));
        listModulos.add(addModulo(3));
        listModulos.add(addModulo(4));
        listModulos.add(addModulo(5));
        listModulos.add(addModulo(6));
    }

    /* Instancia o banco daso ele não exista */
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


    /* Ui threads */
    @UiThread
    protected void atualizaProgressoUI() {

        progressoAtual = preferenceManager.getProgressoModulo();

        for(Modulo modulo : listModulos) {
            modulo.configurarModulo();
        }

    }
    /* Fim Ui threads*/

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

    /* Invoca a janela de alerta */
    protected  void alertaModuloBloqueado() {
        janelaAlerta.alertDialogBloqueado("Módulo Bloqueado", "Complete os módulos anteriores para desbloquear este.");
    }

    /* Carrega fontes customizadas */
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
      * Cria uma instância de um módulo, o configura de acordo com o seu número de identificação,
      * e o retorna para que possa ser colocado na lista de módulos.
      * @param numModulo: Representa o número do módulo de acordo com sua posição na tela, começando do 0.
    */
    private Modulo addModulo(int numModulo) {
        Modulo modulo = new ModuloImp();
        modulo.setStringNota(preferenceManager.getNota(numModulo));
        modulo.setNumModulo(numModulo);
        modulo.setClickListener();
        return modulo;
    }

    /* Interface que representa um módulo */
    interface Modulo {
        int MODULO_CERTIFICADO = 6;
        void setStringNota(String nota);
        void setNumModulo(int numModulo);
        void configurarModulo();
        void setClickListener();
    }

    /* Subclasse que implementa a interface Modulo, e permite a criação de objetos que representam módulos */
    class ModuloImp implements Modulo {

        int numModulo;
        private String stringNota;

        @Override
        public void setNumModulo(int numModulo) {
            this.numModulo = numModulo;
        }

        @Override
        public void setStringNota(String nota) {
            this.stringNota = nota;
        }

        String getStringNota() {
            return this.stringNota;
        }

        private boolean isModuloCursando() {
            return numModulo == progressoAtual;
        }

        private boolean isModuloCompleto() {
            return progressoAtual > numModulo;
        }

        private boolean isModuloLiberado() {
            return progressoAtual >= numModulo;
        }

        private boolean isModuloBloqueado() {
            return progressoAtual < numModulo;
        }

        private boolean isModuloCertificado() {
            return numModulo == MODULO_CERTIFICADO;
        }

        private boolean isCertificadoLiberado() {
            return progressoAtual == MODULO_CERTIFICADO;
        }

        private boolean isCertificadoBloqueado() {
            return progressoAtual < MODULO_CERTIFICADO;
        }

        private boolean isCertificadoGerado() {
            return progressoAtual > MODULO_CERTIFICADO;
        }

        /* CLICK LISTENER */
        @Override
        public void setClickListener() {
            listBtnModulos.get(numModulo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listImgModulos.get(numModulo).startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));

                    if(isModuloCertificado()) {

                        if(isCertificadoLiberado()) {
                            clickCertificadoLiberado();
                        } else if (isCertificadoBloqueado()){
                            clickCertificadoBloqueado();
                        } else if(isCertificadoGerado()) {
                            clickCertificadoGerado();
                        }

                        return;

                    }

                    if(isModuloLiberado()) {
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

        @Override
        public void configurarModulo() {

            if(isModuloCertificado())
            {
                configurarModuloCertificado();
                return;
            }

            if(isModuloCursando())
            {
                configurarModuloCursando();
            }

            else if(isModuloCompleto())
            {
                configurarModuloCompleto();
            }

            else if(isModuloBloqueado())
            {
                configurarModuloBloqueado();
            }
        }


        /*
         * Um módulo completo deve mostrar somente o ícone do módulo, em azul normal,
         * a nota, o título do módulo, e a textView de progresso, com um texto "completo"
        */
        private void configurarModuloCompleto() {
            listTxtNotas.get(numModulo).setText(getStringNota());
            listTxtNotas.get(numModulo).setVisibility(View.VISIBLE);
            listImgModulos.get(numModulo).setImageResource(idImagensCompleto[numModulo]);
            listBarrasProgresso.get(numModulo).setVisibility(View.GONE);
            listTxtProgressoModulos.get(numModulo).setText(R.string.moduloCompleto);
        }

        /*
         * Um módulo que está na situação cursando deve mostrar o ícone do módulo em um azul mais claro,
         *  o título do módulo, a textView de progresso, e a barra de progresso. A nota deve ser escondida
        */
        private void configurarModuloCursando() {
            int progresso = preferenceManager.getProgressoEtapa(numModulo);
            String stringTxtProgresso = String.valueOf(progresso) + stringsTxtProgresso[numModulo];

            listTxtNotas.get(numModulo).setVisibility(View.GONE);
            listImgModulos.get(numModulo).setImageResource(idImagensCursando[numModulo]);
            listTxtProgressoModulos.get(numModulo).setText(stringTxtProgresso);
            listTxtProgressoModulos.get(numModulo).setVisibility(View.VISIBLE);
            listBarrasProgresso.get(numModulo).setProgress((float)progresso);
            listBarrasProgresso.get(numModulo).setVisibility(View.VISIBLE);
        }

        /*
          * Um módulo bloqueado deve mostrar somente o ícone cinza, e o título do módulo
        */
        private void configurarModuloBloqueado() {
            listTxtNotas.get(numModulo).setVisibility(View.GONE);
            listImgModulos.get(numModulo).setImageResource(idImagensBloqueado[numModulo]);
            listBarrasProgresso.get(numModulo).setVisibility(View.GONE);
            listTxtProgressoModulos.get(numModulo).setVisibility(View.GONE);
        }

        /*
          * O módulo certificado deve mostrar sempre somente o ícone e o título. Ele não possui
          * barras nem TextView de progresso, nem nota. Basicamente, deve-se alterar somente o ícone.
        */
        private void configurarModuloCertificado() {

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



