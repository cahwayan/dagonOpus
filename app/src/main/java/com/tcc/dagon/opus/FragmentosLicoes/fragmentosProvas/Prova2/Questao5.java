package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tcc.dagon.opus.ClassesPai.QuestaoProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva2;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan 09/10/2016.
 */
public class Questao5 extends QuestaoProva {

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DE OBJETOS / BANCO / JANELA ALERTA / SONS
        super.instanciaObjetos();
        // MÓDULO A QUAL A PERGUNTA PERTENCE
        super.moduloAtual = 2;
        // ETAPA A QUAL A PERGUNTA PERTENCE
        super.etapaAtual = 6;
        // NÚMERO DA PERGUNTA
        super.questaoAtual = 5;

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo2_prova_questao5, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

        // Carregando os listeners
        super.listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva2)getActivity()).getPager();
        tabStrip   = ((ContainerProva2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva2)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva2)getActivity()).getVida01();
        vida02 = ((ContainerProva2)getActivity()).getVida02();
        vida03 = ((ContainerProva2)getActivity()).getVida03();
        vida04 = ((ContainerProva2)getActivity()).getVida04();
        vida05 = ((ContainerProva2)getActivity()).getVida05();

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        alternativa1 = (RadioButton) rootView.findViewById(R.id.ProvaModulo2Pergunta5Alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.ProvaModulo2Pergunta5Alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.ProvaModulo2Pergunta5Alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.ProvaModulo2Pergunta5Alternativa4);

        // PEGANDO O RADIOGROUP DO LAYOUT
        containerRadioButtons = (RadioGroup) rootView.findViewById(R.id.radioGroupProvaModulo2Pergunta5);

        super.accessViews();

    }

    @Override
    // MODIFICAR FLAG PARA LOGOUT
    public void writeFlag(boolean flag) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("completouTeste2", flag);
        editor.apply();
    }

}