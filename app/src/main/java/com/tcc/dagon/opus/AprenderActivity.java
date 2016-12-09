package com.tcc.dagon.opus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva1;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerPular1;
import com.tcc.dagon.opus.Glossario.ContainerComandosGlossario;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo1Activity;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo2Activity;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo3Activity;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo4Activity;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo5Activity;
import com.tcc.dagon.opus.telasEtapas.EtapasModulo6Activity;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences.NomePreferencia;
import com.tcc.dagon.opus.utils.NovaJanelaAlerta;
import java.io.File;
import java.io.IOException;
import static java.lang.String.valueOf;


/**
 * Created by Andrade on 23/09/2016.
 * ESSA CLASSE ABRIGA AS FUNÇÕES DA TELA DE MÓDULOS
 *
 */

public class AprenderActivity extends AppCompatActivity {
    // BANCO DE DADOS
    GerenciadorBanco DB_PROGRESSO;

    // BOTÕES DOS MÓDULOS
    private FrameLayout btnModulo1,
                        btnModulo2,
                        btnModulo3,
                        btnModulo4,
                        btnModulo5,
                        btnModulo6,
                        btnModulo7,
                        btnCertificado;

    // IMAGENS DOS MÓDULOS
    private ImageView   imgModulo1,
                        imgModulo2,
                        imgModulo3,
                        imgModulo4,
                        imgModulo5,
                        imgModulo6,
                        imgModulo7,
                        imgModulo8;

    // BOTÕES PULAR
    private ImageView btnPular1;

    // TEXTVIEWS QUE MOSTRA O TITULO DOS MÓDULOS
    protected TextView  txtTitulo1,
                        txtTitulo2,
                        txtTitulo3,
                        txtTitulo4,
                        txtTitulo5,
                        txtTitulo6,
                        txtTitulo7,
                        txtTitulo8,
                        txtTitulo9;

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
                                    barraModulo7;
    // SUPER VARIÁVEL CONTEXT
    private Context context = this;

    // Variáveis do menu puxável
    private ListView              mListView;
    private ActionBarDrawerToggle mAlterna;
    private DrawerLayout          drawer_layout;
    private String                mTitulo;

    // VARIÁVEL DE LOGOUT
    boolean isLogin;

    //VARIAVEL DO EMAIL AMARRAR COM A TELA DE PERFIL

    private String email;


    // OBJETO DE JANELA DE ALERTA
    NovaJanelaAlerta alerta = new NovaJanelaAlerta(this);
    NovaJanelaAlerta alertaSair = new NovaJanelaAlerta(this);
    GerenciadorSharedPreferences preferencias = new GerenciadorSharedPreferences(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprender);

        if(getSupportActionBar() != null) {
            // BOTÃO SUPERIOR MENU PUXÁVEL
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // INSTANCIANDO E CRIANDO O BANCO CASO ELE NÃO EXISTA
        instanciaBanco();

        if(!preferencias.lerFlagBoolean(NomePreferencia.isLogin)) {
            preferencias.escreverFlagBoolean(NomePreferencia.isLogin, true);
        }

        // INVOCANDO OS COMPONENTES
        accessViews();

        // MÉTODO QUE INVOCA OS ITENS DO MENU PUXÁVEL
        adicionarItensMenu();

        // MÉTODO DE CONFIGURAÇÃO DO MENU PUXÁVEL
        configurarMenu();

        // CLICK LISTENERS DOS BOTÕES
        listenersBtnModulos();

        // CLICK LISTENERS DO MENU PUXÁVEL
        listenersMenuPrincipal();

        // CLICK LISTENERS DOS BOTÕES DE PULAR
        listenersBtnPular();

        // BLOQUEANDO OS MÓDULOS AO INICIAR O APP
        bloquearModulos();

        // VERIFICAÇÕES E DESBLOQUEIO DE PROGRESSO DOS MÓDULOS
        desbloquearModulos();

        // SETANDO O PROGRESSO DAS TEXT VIEWS
        progressoTextView();

        // SETANDO O PROGRESSO DAS PROGRESS BARS
        progressBars();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                bloquearModulos();
                desbloquearModulos();
                progressoTextView();
                progressBars();

            }
        }, 1);

        if(drawer_layout.isDrawerVisible(mListView)) {
            drawer_layout.closeDrawers();
        }
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        alerta.alertDialogSair("Tem certeza que deseja sair?");
    }


    private void accessViews() {
        // LAYOUTS
        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);

        // BOTÕES DOS MÓDULOS
        btnModulo1 = (FrameLayout)findViewById(R.id.btnModulo1);
        btnModulo2 = (FrameLayout)findViewById(R.id.btnModulo2);
        btnModulo3 = (FrameLayout)findViewById(R.id.btnModulo3);
        btnModulo4 = (FrameLayout)findViewById(R.id.btnModulo4);
        btnModulo5 = (FrameLayout)findViewById(R.id.btnModulo5);
        btnModulo6 = (FrameLayout)findViewById(R.id.btnModulo6);
        /*btnModulo7 = (FrameLayout)findViewById(R.id.btnModulo7);*/
        btnCertificado = (FrameLayout)findViewById(R.id.btnModulo8);

        // IMAGENS DOS MÓDULOS
        imgModulo1 = (ImageView)findViewById(R.id.imgModulo1);
        imgModulo2 = (ImageView)findViewById(R.id.imgModulo2);
        imgModulo3 = (ImageView)findViewById(R.id.imgModulo3);
        imgModulo4 = (ImageView)findViewById(R.id.imgModulo4);
        imgModulo5 = (ImageView)findViewById(R.id.imgModulo5);
        imgModulo6 = (ImageView)findViewById(R.id.imgModulo6);
        /*imgModulo7 = (ImageView)findViewById(R.id.imgModulo7);*/
        imgModulo8 = (ImageView)findViewById(R.id.imgModulo8);

        // TEXT VIEWS DOS TITULOS DOS MODULOS
        txtTitulo1 = (TextView)findViewById(R.id.txtTitulo1);
        txtTitulo2 = (TextView)findViewById(R.id.txtTitulo2);
        txtTitulo3 = (TextView)findViewById(R.id.txtTitulo3);
        txtTitulo4 = (TextView)findViewById(R.id.txtTitulo4);
        txtTitulo5 = (TextView)findViewById(R.id.txtTitulo5);
        txtTitulo6 = (TextView)findViewById(R.id.txtTitulo6);
        /*txtTitulo7 = (TextView)findViewById(R.id.txtTitulo7);*/
        txtTitulo8 = (TextView)findViewById(R.id.txtTitulo8);


        // TEXT VIEWS DOS PROGRESSOS DOS MÓDULOS
        txtProgresso1 = (TextView)findViewById(R.id.txtProgresso1);
        txtProgresso2 = (TextView)findViewById(R.id.txtProgresso2);
        txtProgresso3 = (TextView)findViewById(R.id.txtProgresso3);
        txtProgresso4 = (TextView)findViewById(R.id.txtProgresso4);
        txtProgresso5 = (TextView)findViewById(R.id.txtProgresso5);
        txtProgresso6 = (TextView)findViewById(R.id.txtProgresso6);
        /*txtProgresso7 = (TextView)findViewById(R.id.txtProgresso7);*/

        // BOTÕES PULAR
        btnPular1 = (ImageView) findViewById(R.id.btnPular1);


        // SETANDO A FONTE DOS TITULOS
        Typeface harabara = Typeface.createFromAsset(getAssets(), "fonts/harabara.ttf");
        txtTitulo1.setTypeface(harabara);
        txtTitulo2.setTypeface(harabara);
        txtTitulo3.setTypeface(harabara);
        txtTitulo4.setTypeface(harabara);
        txtTitulo5.setTypeface(harabara);
        txtTitulo6.setTypeface(harabara);
        /*txtTitulo7.setTypeface(harabara);*/
        txtTitulo8.setTypeface(harabara);


        // SETANDO A FONTE DO PROGRESSO
        txtProgresso1.setTypeface(harabara);
        txtProgresso2.setTypeface(harabara);
        txtProgresso3.setTypeface(harabara);
        txtProgresso4.setTypeface(harabara);
        txtProgresso5.setTypeface(harabara);
        txtProgresso6.setTypeface(harabara);
        /*txtProgresso7.setTypeface(harabara);*/

        // BARRA DE PROGRESSO DOS MÓDULOS

        barraModulo1 = (RoundCornerProgressBar)findViewById(R.id.barraModulo1);
        barraModulo2 = (RoundCornerProgressBar)findViewById(R.id.barraModulo2);
        barraModulo3 = (RoundCornerProgressBar)findViewById(R.id.barraModulo3);
        barraModulo4 = (RoundCornerProgressBar)findViewById(R.id.barraModulo4);
        barraModulo5 = (RoundCornerProgressBar)findViewById(R.id.barraModulo5);
        barraModulo6 = (RoundCornerProgressBar)findViewById(R.id.barraModulo6);
        /*barraModulo7 = (RoundCornerProgressBar)findViewById(R.id.barraModulo7);*/

        // MENU PUXÁVEL
        mListView = (ListView)findViewById(R.id.listMenuPrincipal);
        mTitulo   = getTitle().toString();
    }

    // MÉTODO QUE INSTANCIA O BANCO CASO ELE NÃO EXISTA
    private void instanciaBanco() {
        // CRIANDO O OBJETO DO GERENCIADOR DO BANCO
        DB_PROGRESSO = new GerenciadorBanco(this);
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
    private void bloquearModulos() {
        // MUDANDO COR DO FUNDO PARA AS BLOQUEADAS
        btnModulo1.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));
        btnModulo2.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));
        btnModulo3.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));
        btnModulo4.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));
        btnModulo5.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));
        btnModulo6.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));
        /*btnModulo7.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));*/
        btnCertificado.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModuloBloqueado));

        // SUMINDO COM AS TEXTVIEWS DE PROGRESSO
        txtProgresso1.setVisibility(View.GONE);
        txtProgresso2.setVisibility(View.GONE);
        txtProgresso3.setVisibility(View.GONE);
        txtProgresso4.setVisibility(View.GONE);
        txtProgresso5.setVisibility(View.GONE);
        txtProgresso6.setVisibility(View.GONE);
        /*txtProgresso7.setVisibility(View.GONE);*/

        // TROCANDO A IMAGEM DO MÓDULO PARA BLOQUEADO
        imgModulo1.setImageResource(R.drawable.modulo_bloqueado);
        imgModulo2.setImageResource(R.drawable.modulo_bloqueado);
        imgModulo3.setImageResource(R.drawable.modulo_bloqueado);
        imgModulo4.setImageResource(R.drawable.modulo_bloqueado);
        imgModulo5.setImageResource(R.drawable.modulo_bloqueado);
        imgModulo6.setImageResource(R.drawable.modulo_bloqueado);
        /*imgModulo7.setImageResource(R.drawable.modulo_bloqueado);*/
    }


    // DESBLOQUEIA OS MÓDULOS
    private void desbloquearModulo1() {
        // MUDANDO A IMAGEM PARA A ORIGINAL DO MÓDULO
        imgModulo1.setImageResource(R.drawable.btnmodulo1);
        // TRAZENDO DE VOLTA O TÍTULO DO MÓDULO
        txtTitulo1.setText(R.string.txtTituloModulo1);
        // TRAZENDO DE VOLTA A TEXTVIEW DO PROGRESSO DO MÓDULO
        txtProgresso1.setVisibility(View.VISIBLE);
        // TROCANDO A COR DO BACKGROUND PARA A ORIGINAL DESBLOQUEADA
        btnModulo1.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModulo1));
    }

    private void desbloquearModulo2() {
        imgModulo2.setImageResource(R.drawable.btnmodulo2);
        txtTitulo2.setText(R.string.txtTituloModulo2);
        txtProgresso2.setVisibility(View.VISIBLE);
        btnPular1.setVisibility(View.GONE);
        btnModulo2.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModulo2));
    }

    private void desbloquearModulo3() {
        imgModulo3.setImageResource(R.drawable.btnmodulo3);
        txtTitulo3.setText(R.string.txtTituloModulo3);
        txtProgresso3.setVisibility(View.VISIBLE);
        btnModulo3.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModulo3));
    }

    private void desbloquearModulo4() {
        imgModulo4.setImageResource(R.drawable.btnmodulo4);
        txtTitulo4.setText(R.string.txtTituloModulo4);
        txtProgresso4.setVisibility(View.VISIBLE);
        btnModulo4.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModulo4));
    }

    private void desbloquearModulo5() {
        imgModulo5.setImageResource(R.drawable.btnmodulo5);
        txtTitulo5.setText(R.string.txtTituloModulo5);
        txtProgresso5.setVisibility(View.VISIBLE);
        btnModulo5.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModulo5));
    }

    private void desbloquearModulo6() {
        imgModulo6.setImageResource(R.drawable.btnmodulo6);
        txtTitulo6.setText(R.string.txtTituloModulo6);
        txtProgresso6.setVisibility(View.VISIBLE);
        btnModulo6.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModulo6));
    }

    private void desbloquearCertificado() {
        btnCertificado.setBackgroundColor(ContextCompat.getColor(context, R.color.corBtnModulo7));
    }


    // VERIFICANDO E MUDANDO O PROGRESSO DAS TEXTVIEWS
    public void desbloquearModulos() {
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
    private void progressoTextView() {
        txtProgresso1.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(1)) + "/9");
        txtProgresso2.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(2)) + "/6");
        txtProgresso3.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(3)) + "/3");
        txtProgresso4.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(4)) + "/6");
        txtProgresso5.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(5)) + "/1");
        txtProgresso6.setText(String.valueOf(DB_PROGRESSO.verificaProgressoEtapa(6)) + "/10");
    }

    // CONFIGURA O PROGRESSO DAS PROGRESS BARS
    private void progressBars() {
        barraModulo1.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(1)) ) );
        barraModulo2.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(2)) ) );
        barraModulo3.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(3)) ) );
        barraModulo4.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(4)) ) );
        barraModulo5.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(5)) ) );
        barraModulo6.setProgress( Float.parseFloat(valueOf(DB_PROGRESSO.verificaProgressoEtapa(6)) ) );
    }

    // Método que invoca os listeners dos botões
    private void listenersBtnModulos() {
        btnModulo1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                // SE O MÓDULO ESTIVER DESBLOQUEADO, INICIAR A ACTIVITY
                if(DB_PROGRESSO.verificaProgressoModulo() >= 1) {
                    // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    startActivity(new Intent(getApplicationContext(), EtapasModulo1Activity.class));
                    finish();
                } else { // SENÃO EXIBIR ALERTDIALOG
                    alertaModuloBloqueado();
                }
            }
        });

        btnModulo2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoModulo() >= 2) {
                    // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    startActivity(new Intent(getApplicationContext(), EtapasModulo2Activity.class));
                    finish();
                } else {
                    alertaModuloBloqueado();
                }
            }
        });

        btnModulo3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoModulo() >= 3) {
                    // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    startActivity(new Intent(getApplicationContext(), EtapasModulo3Activity.class));
                    finish();
                } else {
                    alertaModuloBloqueado();
                }
            }
        });

        btnModulo4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoModulo() >= 4) {
                    // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    startActivity(new Intent(getApplicationContext(), EtapasModulo4Activity.class));
                    finish();
                } else {
                    alertaModuloBloqueado();
                }
            }
        });

        btnModulo5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoModulo() >= 5) {
                    // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    startActivity(new Intent(getApplicationContext(), EtapasModulo5Activity.class));
                    finish();
                } else {
                    alertaModuloBloqueado();
                }
            }
        });

        btnModulo6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoModulo() >= 6) {
                    // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    startActivity(new Intent(getApplicationContext(), EtapasModulo6Activity.class));
                    finish();
                } else {
                    alertaModuloBloqueado();
                }
            }
        });

        btnCertificado.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(DB_PROGRESSO.verificaProgressoModulo() >= 7) {
                    // CARREGANDO A ANIMAÇÃO DO BOTÃO AO CLICAR
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
                    startActivity(new Intent(getApplicationContext(), CertificadoActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), CertificadoIncompleto.class));
                }
            }
        });

    }

    private void listenersBtnPular() {
        btnPular1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context,R.anim.anim_botaoimageview));
                startActivity(new Intent(getApplicationContext(), ContainerPular1.class));
                finish();
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
                    drawer_layout.closeDrawers();
                }
                else if(position==1){
                    Intent i = new Intent(AprenderActivity.this, GerenciarPerfilActivity.class);
                    startActivity(i);
                    finish();
                } else if (position ==2) {
                    startActivity(new Intent(getApplicationContext(), ContainerComandosGlossario.class));
                } else if(position ==3) {
                    startActivity(new Intent(getApplicationContext(), ActivityConfig.class));
                } else if(position == 4) {

                }
            }
        });
    }

    // MÉTODO QUE RECEBE OS ITENS QUE VÃO PRO MENU PUXÁVEL, ADAPTA, E OS COLOCA LÁ
    private void adicionarItensMenu() {
        // Adapter String <mais em https://teamtreehouse.com/library/android-lists-and-adapters>
        ArrayAdapter<String> mAdapter;
        // ITENS DO MENU
        String[] itensMenu = {"Módulos", "Perfil", "Glossário", "Configurações", "Avalie-nos!"};
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

    // FIM MÉTODOS NATIVOS DO MENU

    // MÉTODO DE CONFIGURAÇÃO DA JANELA DE ALERTA PARA OS MODULOS
    private void alertaModuloBloqueado() {
        alerta.alertDialogBloqueado("Módulo Bloqueado", "Complete os módulos anteriores para desbloquear este.");
    }

}

