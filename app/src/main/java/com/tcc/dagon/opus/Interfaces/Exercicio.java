package com.tcc.dagon.opus.Interfaces;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.Activities.AppCompatActivity.Containers.ContainerEtapa;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;
import com.tcc.dagon.opus.utils.PulseAnimation;

/**
 * Created by cahwayan on 16/01/2017.
 */

public interface Exercicio {
    void onPause();
    void onResume();
    void onDestroy() ;
    void instanciaObjetos();
    void getConstructorArgs();
    void accessViews(View rootView);
    void listeners();
    void validarRespostaUsuario();
    void respostaCerta();
    void respostaErrada() ;
    void tentarNovamente() ;
    void concluirQuestao() ;
    void avancarQuestao();
    void questaoFinal() ;
    void setPontuacao() ;
    void initAnimationAnswer(ImageView image) ;
    void playSound(MediaPlayer sound);
    void hideUnnecessaryView(View view);
    void unhideView(View view);
    void changeUpperBarIcon(int passo, int drawableID) ;
    void setUpperBarIconClickable(int passo);
    void updateUserProgress() ;
    void moveNext(View view) ;
    void movePrevious(View view) ;
    void setQtdErros(int qtdErros);
    void zerarPontuacao();
    Class retornarTelaEtapas(int numeroModulo);
}
