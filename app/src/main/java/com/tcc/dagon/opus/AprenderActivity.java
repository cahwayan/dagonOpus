package com.tcc.dagon.opus;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

import java.io.File;
import java.io.IOException;

import static java.lang.String.valueOf;


public class AprenderActivity extends AppCompatActivity {
    // comentário de teste
    // teste
    // BANCO DE DADOS
    GerenciadorBanco DB_PROGRESSO;

    String mostraTexto;

    // BOTÕES DOS MÓDULOS
    public ImageView btnModulo1, btnModulo2, btnModulo3, btnModulo4, btnModulo5, btnModulo6,
                      btnModulo7, btnModulo8;
    // TEXTVIEWS TITULO MODULOS
    protected TextView txtTitulo1, txtTitulo2, txtTitulo3, txtTitulo4, txtTitulo5, txtTitulo6,
                     txtTitulo7, txtTitulo8;

    protected TextView txtProgresso1, txtProgresso2, txtProgresso3, txtProgresso4, txtProgresso5,
                     txtProgresso6, txtProgresso7, txtProgresso8;

    // BARRA DE PROGRESSO DOS MÓDULOS
    private RoundCornerProgressBar barraModulo1, barraModulo2, barraModulo3, barraModulo4, barraModulo5,
                                   barraModulo6, barraModulo7, barraModulo8;

    private Context     context = this;

    // Variáveis do menu puxável
    private ListView              mListView;
    private ActionBarDrawerToggle mAlterna;
    private DrawerLayout          drawer_layout;
    private String                mTitulo;

    // Adapter String <mais em https://teamtreehouse.com/library/android-lists-and-adapters>
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);

        // INSTANCIANDO E CRIANDO O BANCO
        DB_PROGRESSO = new GerenciadorBanco(this);
        File banco = context.getApplicationContext().getDatabasePath(DB_PROGRESSO.getDbName());
        if(!banco.exists()) {
            try {
                DB_PROGRESSO.criarBanco();
            } catch (IOException ioe) {
                throw new Error("Erro de cópia");
            }
        }


        // LAYOUTS
        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);

        // BOTÕES DOS MÓDULOS
        btnModulo1 = (ImageView)findViewById(R.id.btnModulo1);
        btnModulo2 = (ImageView)findViewById(R.id.btnModulo2);
        btnModulo3 = (ImageView)findViewById(R.id.btnModulo3);
        btnModulo4 = (ImageView)findViewById(R.id.btnModulo4);
        btnModulo5 = (ImageView)findViewById(R.id.btnModulo5);
        btnModulo6 = (ImageView)findViewById(R.id.btnModulo6);
        btnModulo7 = (ImageView)findViewById(R.id.btnModulo7);
        btnModulo8 = (ImageView)findViewById(R.id.btnModulo8);

        // TEXT VIEWS DOS TITULOS DOS MODULOS

        txtTitulo1 = (TextView)findViewById(R.id.txtTitulo1);
        txtTitulo2 = (TextView)findViewById(R.id.txtTitulo2);
        txtTitulo3 = (TextView)findViewById(R.id.txtTitulo3);
        txtTitulo4 = (TextView)findViewById(R.id.txtTitulo4);
        txtTitulo5 = (TextView)findViewById(R.id.txtTitulo5);
        txtTitulo6 = (TextView)findViewById(R.id.txtTitulo6);
        txtTitulo7 = (TextView)findViewById(R.id.txtTitulo7);
        txtTitulo8 = (TextView)findViewById(R.id.txtTitulo8);

        // TEXT VIEWS DOS PROGRESSOS DOS MÓDULOS

        txtProgresso1 = (TextView)findViewById(R.id.txtProgresso1);
        txtProgresso2 = (TextView)findViewById(R.id.txtProgresso2);
        txtProgresso3 = (TextView)findViewById(R.id.txtProgresso3);
        txtProgresso4 = (TextView)findViewById(R.id.txtProgresso4);
        txtProgresso5 = (TextView)findViewById(R.id.txtProgresso5);
        txtProgresso6 = (TextView)findViewById(R.id.txtProgresso6);
        txtProgresso7 = (TextView)findViewById(R.id.txtProgresso7);
        txtProgresso8 = (TextView)findViewById(R.id.txtProgresso8);

        // SETANDO A FONTE DOS TITULOS
        Typeface harabara = Typeface.createFromAsset(getAssets(), "fonts/harabara.otf");
        txtTitulo1.setTypeface(harabara);
        txtTitulo2.setTypeface(harabara);
        txtTitulo3.setTypeface(harabara);
        txtTitulo4.setTypeface(harabara);
        txtTitulo5.setTypeface(harabara);
        txtTitulo6.setTypeface(harabara);
        txtTitulo7.setTypeface(harabara);
        txtTitulo8.setTypeface(harabara);

        // SETANDO A FONTE DO PROGRESSO
        txtProgresso1.setTypeface(harabara);
        txtProgresso2.setTypeface(harabara);
        txtProgresso3.setTypeface(harabara);
        txtProgresso4.setTypeface(harabara);
        txtProgresso5.setTypeface(harabara);
        txtProgresso6.setTypeface(harabara);
        txtProgresso7.setTypeface(harabara);
        txtProgresso8.setTypeface(harabara);

        // BARRA DE PROGRESSO DOS MÓDULOS

        barraModulo1 = (RoundCornerProgressBar)findViewById(R.id.barraModulo1);
        barraModulo2 = (RoundCornerProgressBar)findViewById(R.id.barraModulo2);
        barraModulo3 = (RoundCornerProgressBar)findViewById(R.id.barraModulo3);
        barraModulo4 = (RoundCornerProgressBar)findViewById(R.id.barraModulo4);
        barraModulo5 = (RoundCornerProgressBar)findViewById(R.id.barraModulo5);
        barraModulo6 = (RoundCornerProgressBar)findViewById(R.id.barraModulo6);
        barraModulo7 = (RoundCornerProgressBar)findViewById(R.id.barraModulo7);
        barraModulo8 = (RoundCornerProgressBar)findViewById(R.id.barraModulo8);

        // MENU PUXÁVEL
        mListView = (ListView)findViewById(R.id.listMenuPrincipal);
        mTitulo   = getTitle().toString();

        // MÉTODO QUE INVOCA OS ITENS DO MENU PUXÁVEL
        adicionarItensMenu();
        // MÉTODO DE CONFIGURAÇÃO DO MENU PUXÁVEL
        configurarMenu();

        // BOTÃO SUPERIOR MENU PUXÁVEL
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // CHAMANDO OS CLICK LISTENERS DOS BOTÕES AO EXECUTAR A CLASSE
        listenersBtnModulos();

        // CLICK LISTENERS DO MENU PUXÁVEL
        // sim
        listenersMenuPrincipal();

        // Bloqueando módulos ao iniciar o app
        bloquearModulos();
        // VERIFICAÇÕES DE PROGRESSO DOS MÓDULOS
        switch(DB_PROGRESSO.verificaProgressoModulo()) {
            case 1: desbloquearModulo1();
                break;
            case 2: desbloquearModulo2();
                break;
            case 3: desbloquearModulo3();
                break;
            case 4: desbloquearModulo4();
                break;
            case 5: desbloquearModulo5();
                break;
            case 6: desbloquearModulo6();
                break;
            case 7: desbloquearModulo7();
                break;
            case 8: desbloquearModulo8();
                break;
        }
    }

    // métodos de desbloquear os módulos
    public void desbloquearModulo1() {
        btnModulo1.setClickable(true);
        txtTitulo1.setVisibility(View.VISIBLE);
        txtProgresso1.setVisibility(View.VISIBLE);
        barraModulo1.setVisibility(View.VISIBLE);
    }

    public void desbloquearModulo2() {
        btnModulo2.setClickable(true);
        txtTitulo2.setVisibility(View.VISIBLE);
        txtProgresso2.setVisibility(View.VISIBLE);
        barraModulo2.setVisibility(View.VISIBLE);
    }

    public void desbloquearModulo3() {
        btnModulo3.setClickable(true);
        txtTitulo3.setVisibility(View.VISIBLE);
        txtProgresso3.setVisibility(View.VISIBLE);
        barraModulo3.setVisibility(View.VISIBLE);
    }

    public void desbloquearModulo4() {
        btnModulo4.setClickable(true);
        txtTitulo4.setVisibility(View.VISIBLE);
        txtProgresso4.setVisibility(View.VISIBLE);
        barraModulo4.setVisibility(View.VISIBLE);
    }

    public void desbloquearModulo5() {
        btnModulo5.setClickable(true);
        txtTitulo5.setVisibility(View.VISIBLE);
        txtProgresso5.setVisibility(View.VISIBLE);
        barraModulo5.setVisibility(View.VISIBLE);
    }

    public void desbloquearModulo6() {
        btnModulo6.setClickable(true);
        txtTitulo6.setVisibility(View.VISIBLE);
        txtProgresso6.setVisibility(View.VISIBLE);
        barraModulo6.setVisibility(View.VISIBLE);
    }

    public void desbloquearModulo7() {
        btnModulo7.setClickable(true);
        txtTitulo7.setVisibility(View.VISIBLE);
        txtProgresso7.setVisibility(View.VISIBLE);
        barraModulo7.setVisibility(View.VISIBLE);
    }

    public void desbloquearModulo8() {
        btnModulo8.setClickable(true);
        txtTitulo8.setVisibility(View.VISIBLE);
        txtProgresso8.setVisibility(View.VISIBLE);
        barraModulo8.setVisibility(View.VISIBLE);
    }

    // método que bloqueia os módulos
    public void bloquearModulos() {
        btnModulo1.setClickable(false);
        txtTitulo1.setVisibility(View.GONE);
        txtProgresso1.setVisibility(View.GONE);
        barraModulo1.setVisibility(View.GONE);

        btnModulo2.setClickable(false);
        txtTitulo2.setVisibility(View.GONE);
        txtProgresso2.setVisibility(View.GONE);
        barraModulo2.setVisibility(View.GONE);

        btnModulo3.setClickable(false);
        txtTitulo3.setVisibility(View.GONE);
        txtProgresso3.setVisibility(View.GONE);
        barraModulo3.setVisibility(View.GONE);

        btnModulo4.setClickable(false);
        txtTitulo4.setVisibility(View.GONE);
        txtProgresso4.setVisibility(View.GONE);
        barraModulo4.setVisibility(View.GONE);

        btnModulo5.setClickable(false);
        txtTitulo5.setVisibility(View.GONE);
        txtProgresso5.setVisibility(View.GONE);
        barraModulo5.setVisibility(View.GONE);

        btnModulo6.setClickable(false);
        txtTitulo6.setVisibility(View.GONE);
        txtProgresso6.setVisibility(View.GONE);
        barraModulo6.setVisibility(View.GONE);

        btnModulo7.setClickable(false);
        txtTitulo7.setVisibility(View.GONE);
        txtProgresso7.setVisibility(View.GONE);
        barraModulo7.setVisibility(View.GONE);

        btnModulo8.setClickable(false);
        txtTitulo8.setVisibility(View.GONE);
        txtProgresso8.setVisibility(View.GONE);
        barraModulo8.setVisibility(View.GONE);
    }

    // Método que invoca os listeners dos botões
    private void listenersBtnModulos() {
        btnModulo1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        btnModulo2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        btnModulo3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        btnModulo4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        btnModulo5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        btnModulo6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        btnModulo7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        btnModulo8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });
    }

    private void listenersMenuPrincipal() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position ,long id) {
                if (position==0){

                }
                else if(position==1){
                finish();

                }
            }
        });
    }

    // Método que adiciona os itens no menu puxável, adapta e os coloca no componente
    private void adicionarItensMenu() {
        /*Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        String txt = bundle.getString("txt");
*/
        String[] itensMenu = {"item1", "Logout", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itensMenu);
        mListView.setAdapter(mAdapter);
    }

    // Método de configuração do menu puxável (MÉTODOS A SEGUIR SAO NATIVOS)
    private void configurarMenu() {
        // Configurando o objeto que abre o menu através do ícone superior
        mAlterna = new ActionBarDrawerToggle(this, drawer_layout, R.string.mAbrir,
                                             R.string.mFechar){
            // Método chamado quando o menu abre
            public void aoAbrirMenu(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // Cria chamada para o método onPrepareOptionsMenu()

            }
            // Método chamado quando o menu fecha
            public void aoFecharMenu(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitulo);
                invalidateOptionsMenu(); // Cria chamada para o método onPrepareOptionsMenu()
            }
        };

        mAlterna.setDrawerIndicatorEnabled(true);
        drawer_layout.addDrawerListener(mAlterna);
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (mAlterna.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    // FIM MÉTODOS NATIVOS DO MENU




}

