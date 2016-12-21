package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tcc.dagon.opus.ClassesPai.QuestaoProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva3;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Questao3 extends QuestaoProva {

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DE OBJETOS / BANCO / JANELA ALERTA / SONS
        super.instanciaObjetos();
        // MÓDULO A QUAL A PERGUNTA PERTENCE
        super.moduloAtual = 3;
        // ETAPA A QUAL A PERGUNTA PERTENCE
        super.etapaAtual = 3;
        // NÚMERO DA PERGUNTA
        super.questaoAtual = 3;

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo3_prova_questao3, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

        // Carregando os listeners
        super.listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva3)getActivity()).getPager();
        tabStrip   = ((ContainerProva3)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva3)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva3)getActivity()).getVida01();
        vida02 = ((ContainerProva3)getActivity()).getVida02();
        vida03 = ((ContainerProva3)getActivity()).getVida03();
        vida04 = ((ContainerProva3)getActivity()).getVida04();
        vida05 = ((ContainerProva3)getActivity()).getVida05();

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        alternativa1 = (RadioButton) rootView.findViewById(R.id.ProvaModulo3Pergunta3Alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.ProvaModulo3Pergunta3Alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.ProvaModulo3Pergunta3Alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.ProvaModulo3Pergunta3Alternativa4);

        // PEGANDO O RADIOGROUP DO LAYOUT
        radioGroupQuestao = (RadioGroup) rootView.findViewById(R.id.radioGroupProvaModulo3Pergunta3);

        super.accessViews();

    }

}