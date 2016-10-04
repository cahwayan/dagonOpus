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

//TESTE OTÁVIO
//SEERA QUE FUNCIONA?

public class AprenderActivity extends AppCompatActivity {
    // BANCO DE DADOS
    GerenciadorBanco DB_PROGRESSO;

    // BOTÕES DOS MÓDULOS
    public ImageView btnModulo1,
                     btnModulo2,
                     btnModulo3,
                     btnModulo4,
                     btnModulo5,
                     btnModulo6,
                     btnModulo7,
                     btnModulo8;

    // TEXTVIEWS QUE MOSTRA O TITULO DOS MÓDULOS
    protected TextView  txtTitulo1,
                        txtTitulo2,
                        txtTitulo3,
                        txtTitulo4,
                        txtTitulo5,
                        txtTitulo6,
                        txtTitulo7,
                        txtTitulo8;

    // TEXT VIEW QUE MOSTRA O PROGRESSO DO USUÁRIO
    protected TextView  txtProgresso1,
                        txtProgresso2,
                        txtProgresso3,
                        txtProgresso4,
                        txtProgresso5,
                        txtProgresso6,
                        txtProgresso7,
                        txtProgresso8;

    // BARRA DE PROGRESSO DOS MÓDULOS
    private RoundCornerProgressBar  barraModulo1,
                                    barraModulo2,
                                    barraModulo3,
                                    barraModulo4,
                                    barraModulo5,
                                    barraModulo6,
                                    barraModulo7,
                                    barraModulo8;
    // SUPER VARIÁVEL CONTEXT
    private Context context = this;

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

        // INSTANCIANDO E CRIANDO O BANCO CASO ELE NÃO EXISTA
        instanciaBanco();

        // INVOCANDO OS COMPONENTES
        accessViews();

        // MÉTODO QUE INVOCA OS ITENS DO MENU PUXÁVEL
        adicionarItensMenu();

        // MÉTODO DE CONFIGURAÇÃO DO MENU PUXÁVEL
        configurarMenu();

        // BOTÃO SUPERIOR MENU PUXÁVEL
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // CHAMANDO OS CLICK LISTENERS DOS BOTÕES
        listenersBtnModulos();

        // CLICK LISTENERS DO MENU PUXÁVEL
        listenersMenuPrincipal();

        // BLOQUEANDO OS MÓDULOS AO INICIAR O APP
        bloquearModulos();

        // VERIFICAÇÕES E DESBLOQUEIO DE PROGRESSO DOS MÓDULOS
        progressoModulos();

        // SETANDO O PROGRESSO DAS TEXT VIEWS
        progressoTextView();

        // SETANDO O PROGRESSO DAS PROGRESS BARS
        progressBars();
    }

    private void accessViews() {
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
        Typeface harabara = Typeface.createFromAsset(getAssets(), "fonts/harabara.ttf");
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
    }

    private void instanciaBanco() {
        DB_PROGRESSO = new GerenciadorBanco(this);
        File banco = context.getApplicationContext().getDatabasePath(DB_PROGRESSO.getDbName());
        if(!banco.exists()) {
            try {
                DB_PROGRESSO.criarBanco();
            } catch (IOException ioe) {
                throw new Error("Erro de cópia");
            }
        }
    }

    // BLOQUEIA OS MÓDULOS
    private void bloquearModulos() {
        // MUDANDO IMAGEM PARA A BLOQUEADA
        btnModulo1.setImageResource(R.drawable.modulo_bloqueado);
        // BLOQUEANDO O CLICK
        btnModulo1.setClickable(false);
        // SUMINDO COM AS TEXTVIEWS
        txtTitulo1.setVisibility(View.GONE);
        txtProgresso1.setVisibility(View.GONE);
        // SUMINDO COM A BARRA DE PROGRESSO
        barraModulo1.setVisibility(View.GONE);

        btnModulo2.setImageResource(R.drawable.modulo_bloqueado);
        btnModulo2.setClickable(false);
        txtTitulo2.setVisibility(View.GONE);
        txtProgresso2.setVisibility(View.GONE);
        barraModulo2.setVisibility(View.GONE);

        btnModulo3.setImageResource(R.drawable.modulo_bloqueado);
        btnModulo3.setClickable(false);
        txtTitulo3.setVisibility(View.GONE);
        txtProgresso3.setVisibility(View.GONE);
        barraModulo3.setVisibility(View.GONE);

        btnModulo4.setImageResource(R.drawable.modulo_bloqueado);
        btnModulo4.setClickable(false);
        txtTitulo4.setVisibility(View.GONE);
        txtProgresso4.setVisibility(View.GONE);
        barraModulo4.setVisibility(View.GONE);

        btnModulo5.setImageResource(R.drawable.modulo_bloqueado);
        btnModulo5.setClickable(false);
        txtTitulo5.setVisibility(View.GONE);
        txtProgresso5.setVisibility(View.GONE);
        barraModulo5.setVisibility(View.GONE);

        btnModulo6.setImageResource(R.drawable.modulo_bloqueado);
        btnModulo6.setClickable(false);
        txtTitulo6.setVisibility(View.GONE);
        txtProgresso6.setVisibility(View.GONE);
        barraModulo6.setVisibility(View.GONE);

        btnModulo7.setImageResource(R.drawable.modulo_bloqueado);
        btnModulo7.setClickable(false);
        txtTitulo7.setVisibility(View.GONE);
        txtProgresso7.setVisibility(View.GONE);
        barraModulo7.setVisibility(View.GONE);

        btnModulo8.setImageResource(R.drawable.modulo_bloqueado);
        btnModulo8.setClickable(false);
        txtTitulo8.setVisibility(View.GONE);
        txtProgresso8.setVisibility(View.GONE);
        barraModulo8.setVisibility(View.GONE);
    }


    // DESBLOQUEIA OS MÓDULOS
    private void desbloquearModulo1() {
        // MUDANDO A IMAGEM PARA A ORIGINAL DO MÓDULO
        btnModulo1.setImageResource(R.drawable.btnmodulo1);
        // RELIGANDO O CLICK
        btnModulo1.setClickable(true);
        // TRAZENDO DE VOLTA O TÍTULO DO MÓDULO
        txtTitulo1.setVisibility(View.VISIBLE);
        // TRAZENDO DE VOLTA A TEXTVIEW DO PROGRESSO DO MÓDULO
        txtProgresso1.setVisibility(View.VISIBLE);
        // TRAZENDO DE VOLTA A BARRA DE PROGRESSO DO MÓDULO
        barraModulo1.setVisibility(View.VISIBLE);
    }

    private void desbloquearModulo2() {
        desbloquearModulo1();
        btnModulo2.setImageResource(R.drawable.btnmodulo2);
        btnModulo2.setClickable(true);
        txtTitulo2.setVisibility(View.VISIBLE);
        txtProgresso2.setVisibility(View.VISIBLE);
        barraModulo2.setVisibility(View.VISIBLE);
    }

    private void desbloquearModulo3() {
        btnModulo3.setImageResource(R.drawable.btnmodulo3);
        btnModulo3.setClickable(true);
        txtTitulo3.setVisibility(View.VISIBLE);
        txtProgresso3.setVisibility(View.VISIBLE);
        barraModulo3.setVisibility(View.VISIBLE);
    }

    private void desbloquearModulo4() {
        btnModulo4.setImageResource(R.drawable.btnmodulo4);
        btnModulo4.setClickable(true);
        txtTitulo4.setVisibility(View.VISIBLE);
        txtProgresso4.setVisibility(View.VISIBLE);
        barraModulo4.setVisibility(View.VISIBLE);
    }

    private void desbloquearModulo5() {
        btnModulo5.setImageResource(R.drawable.btnmodulo5);
        btnModulo5.setClickable(true);
        txtTitulo5.setVisibility(View.VISIBLE);
        txtProgresso5.setVisibility(View.VISIBLE);
        barraModulo5.setVisibility(View.VISIBLE);
    }

    private void desbloquearModulo6() {
        btnModulo6.setImageResource(R.drawable.btnmodulo6);
        btnModulo6.setClickable(true);
        txtTitulo6.setVisibility(View.VISIBLE);
        txtProgresso6.setVisibility(View.VISIBLE);
        barraModulo6.setVisibility(View.VISIBLE);
    }

    private void desbloquearModulo7() {
        btnModulo7.setImageResource(R.drawable.btnmodulo7);
        btnModulo7.setClickable(true);
        txtTitulo7.setVisibility(View.VISIBLE);
        txtProgresso7.setVisibility(View.VISIBLE);
        barraModulo7.setVisibility(View.VISIBLE);
    }

    private void desbloquearModulo8() {
        btnModulo8.setImageResource(R.drawable.btnmodulo8);
        btnModulo8.setClickable(true);
        txtTitulo8.setVisibility(View.VISIBLE);
        txtProgresso8.setVisibility(View.VISIBLE);
        barraModulo8.setVisibility(View.VISIBLE);
    }


    // VERIFICANDO E MUDANDO O PROGRESSO DAS TEXTVIEWS
    private void progressoModulos() {
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
                desbloquearModulo7();
                break;
            case 8:
                desbloquearModulo1();
                desbloquearModulo2();
                desbloquearModulo3();
                desbloquearModulo4();
                desbloquearModulo5();
                desbloquearModulo6();
                desbloquearModulo7();
                desbloquearModulo8();
                break;
        }
    }

    // VERIFICA O PROGRESSO DO USUÁRIO REFERENTE AS ETAPAS DE CADA MÓDULO E ATRIBUI A TEXTVIEW
    private void progressoTextView() {
        txtProgresso1.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(1)) + "/9");
        txtProgresso2.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(2)) + "/10");
        txtProgresso3.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(3)) + "/7");
        txtProgresso4.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(4)) + "/5");
        txtProgresso5.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(5)) + "/6");
        txtProgresso6.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(6)) + "/7");
        txtProgresso7.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(7)) + "/X");
        txtProgresso8.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(8)) + "/X");
    }

    // CONFIGURA O PROGRESSO DAS PROGRESS BARS
    private void progressBars() {
        barraModulo1.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(1)) ) );
        barraModulo2.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(2)) ) );
        barraModulo3.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(3)) ) );
        barraModulo4.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(4)) ) );
        barraModulo5.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(5)) ) );
        barraModulo6.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(6)) ) );
        barraModulo7.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(7)) ) );
        barraModulo8.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(8)) ) );
    }

    // Método que invoca os listeners dos botões
    private void listenersBtnModulos() {
        btnModulo1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
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

    // LISTENERS DO MENU PUXÁVEL
    private void listenersMenuPrincipal() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // CLICK LISTENERS PARA CADA POSIÇÃO DO MENU
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position ,long id) {
                if (position==0){
                    Intent gerenciarPerfil = new Intent(AprenderActivity.this, GerenciarPerfilActivity.class);
                    startActivity(gerenciarPerfil);
                }
                else if(position==1){

                }
            }
        });
    }

    // MÉTODO QUE RECEBE OS ITENS QUE VÃO PRO MENU PUXÁVEL, ADAPTA, E OS COLOCA LÁ
    private void adicionarItensMenu() {
        // ITENS DO MENU
        String[] itensMenu = {"Perfil", "Logout", "Item3", "Item4", "Item5", "Item6", "Item7", "Item8"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itensMenu);
        mListView.setAdapter(mAdapter);
    }

    // MÉTODO DE CONFIGURAÇÃO DO MENU (ALGUNS MÉTODOS SÃO NATIVOS, NÃO RENOMEAR)
    private void configurarMenu() {
        // CONFIGURANDO O OBJETO QUE ABRE O MENU QUANDO O BOTÃO SUPERIOR É APERTADO
        mAlterna = new ActionBarDrawerToggle(this, drawer_layout, R.string.mAbrir,
                                             R.string.mFechar){
            // Método chamado quando o menu abre
            public void aoAbrirMenu(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // CRIA CHAMADA PARA O MÉTODO onPrepareOptionsMenu()

            }
            // Método chamado quando o menu fecha
            public void aoFecharMenu(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mTitulo);
                invalidateOptionsMenu(); // CRIA CHAMADA PARA O MÉTODO onPrepareOptionsMenu()
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

