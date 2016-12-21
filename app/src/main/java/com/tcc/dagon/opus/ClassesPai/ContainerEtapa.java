package com.tcc.dagon.opus.ClassesPai;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

/**
 * Created by cahwayan on 04/11/2016.
 */

public class ContainerEtapa extends AppCompatActivity {

    protected TabLayout mTabLayout;
    protected ViewPager mViewPager;
    protected GerenciadorBanco DB_PROGRESSO = null;
    protected LinearLayout tabStrip;
    protected int moduloAtual, etapaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.container_etapa);
        super.onCreate(savedInstanceState);
        instanciaObjetos();
        accessViews();
        bloquearLicoes();
        desbloquearLicoes();
    }


    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 1){
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 2) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 3) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 4) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 5) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 6) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 7) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 8) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 9) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 10) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 11) {
            movePrevious(mViewPager);
        } else if (mViewPager.getCurrentItem() == 12) {
            super.onBackPressed();
        } else if (mViewPager.getCurrentItem() == 13) {
            movePrevious(mViewPager);
        }else {
            super.onBackPressed();
        }
    }

    protected void accessViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout)findViewById(R.id.tab_layout);
        mViewPager = (ViewPager)findViewById(R.id.view_pager);

        // Setando o adapter que vai retornar os fragmentos
        setAdapter();

        // Setudo do Viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        tabStrip = ((LinearLayout)mTabLayout.getChildAt(0));
    }

    protected void bloquearLicoes() {
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            mTabLayout.getTabAt(i).setIcon(R.drawable.icon_lock_licao);
            tabStrip.getChildAt(i).setClickable(false);
            tabStrip.getChildAt(i).setEnabled(false);
        }
    }

    protected void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                for(int i = 0; i <= progresso; i += 1) {
                    if(mTabLayout.getTabAt(i) != null) {
                        if( i % 2 == 0) {
                           mTabLayout.getTabAt(i).setIcon(R.drawable.icon_licao);
                        } else {
                           mTabLayout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                        }

                    }

                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
                break;
        }
    }

    protected void instanciaObjetos() {
        if(DB_PROGRESSO == null) {
            DB_PROGRESSO = new GerenciadorBanco(this);
        }

    }

    public ViewPager getPager(){
        return mViewPager;
    }

    public LinearLayout getTabStrip() {
        return tabStrip;
    }

    public TabLayout getmTabLayout() {
        return mTabLayout;
    }

    protected void movePrevious(View view) {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    // MÉTODO QUE SETA O ADAPTER DE ACORDO COM O ESTADO ATUAL
    protected void setAdapter() {
        // VERIFICAR O MÓDULO ATUAL
        switch(moduloAtual) {
            // CASO MÓDULO 1
            case 1:
                // DENTRO DO MÓDULO ATUAL, VERIFICAR A ETAPA ATUAL E SETAR O ADAPTER ESPECÍFICO
                switch(etapaAtual) {
                    // CASO ETAPA 1
                    case 1:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa1(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa1)));
                        break;
                    case 2:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa2(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa2)));
                        break;
                    case 3:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa3(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa3)));
                        break;
                    case 4:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa4(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa4)));
                        break;
                    case 5:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa5(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa5)));
                        break;
                    case 6:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa6(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa6)));
                        break;
                    case 7:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa7(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa7)));
                        break;
                    case 8:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo1.AdapterEtapa8(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_etapa8)));
                        break;
                    case 9:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Provas.AdapterProva1(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo1_prova)));
                        break;
                }
            break;

            // CASO MÓDULO 2 ....
            case 2:
                switch(etapaAtual) {
                    case 1:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa1(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo2_etapa1)));
                        break;
                    case 2:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa2(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo2_etapa2)));
                        break;
                    case 3:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa3(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo2_etapa3)));
                        break;
                    case 4:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa4(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo2_etapa4)));
                        break;
                    case 5:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo2.AdapterEtapa5(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo2_etapa5)));
                        break;
                    case 6:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Provas.AdapterProva2(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo2_prova)));
                        break;
                }

            break;

            case 3:
                switch(etapaAtual) {
                    case 1:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo3.AdapterEtapa1(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo3_etapa1)));
                        break;
                    case 2:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo3.AdapterEtapa2(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo3_etapa2)));
                        break;
                    case 3:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Provas.AdapterProva3(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo3_prova)));
                        break;
                }
            break;

            case 4:
                switch(etapaAtual) {
                    case 1:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo4.AdapterEtapa1(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo4_etapa1)));
                        break;
                    case 2:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo4.AdapterEtapa2(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo4_etapa2)));
                        break;
                    case 3:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo4.AdapterEtapa3(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo4_etapa3)));
                        break;
                    case 4:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo4.AdapterEtapa4(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo4_etapa4)));
                        break;
                    case 5:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo4.AdapterEtapa5(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo4_etapa5)));
                        break;
                    case 6:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Provas.AdapterProva4(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo4_prova)));
                        break;
                }
            break;

            case 5:
                switch(etapaAtual) {
                    case 1:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo5.AdapterEtapa1(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo5_etapa1)));
                        break;
                }
            break;

            case 6:
                switch(etapaAtual) {
                    case 1:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa1(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa1)));
                        break;
                    case 2:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa2(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa2)));
                        break;
                    case 3:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa3(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa3)));
                        break;
                    case 4:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa4(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa4)));
                        break;
                    case 5:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa5(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa5)));
                        break;
                    case 6:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa6(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa6)));
                        break;
                    case 7:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa7(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa7)));
                        break;
                    case 8:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa8(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa8)));
                        break;
                    case 9:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Modulo6.AdapterEtapa9(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_etapa9)));
                        break;
                    case 10:
                        mViewPager.setAdapter(new com.tcc.dagon.opus.Adapters.Provas.AdapterProva6(getSupportFragmentManager(),
                                getResources().getStringArray(R.array.tab_modulo6_prova)));
                        break;

                }
            break;

        }
    }



}
