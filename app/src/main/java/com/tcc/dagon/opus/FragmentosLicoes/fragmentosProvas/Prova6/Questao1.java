package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova6;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tcc.dagon.opus.ClassesPai.QuestaoProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva6;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Questao1 extends QuestaoProva {

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DE OBJETOS / BANCO / JANELA ALERTA / SONS
        super.instanciaObjetos();
        // MÓDULO A QUAL A PERGUNTA PERTENCE
        super.moduloAtual = 6;
        // ETAPA A QUAL A PERGUNTA PERTENCE
        super.etapaAtual = 10;
        // NÚMERO DA PERGUNTA
        super.questaoAtual = 1;


        // TODA PRIMEIRA QUESTAO DA PROVA TEM QUE VIR COM 5 VIDAS
        super.mCallback.onArticleSelected(5);

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo6_prova_questao1, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

        // Carregando os listeners
        super.listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva6)getActivity()).getPager();
        tabStrip   = ((ContainerProva6)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva6)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva6)getActivity()).getVida01();
        vida02 = ((ContainerProva6)getActivity()).getVida02();
        vida03 = ((ContainerProva6)getActivity()).getVida03();
        vida04 = ((ContainerProva6)getActivity()).getVida04();
        vida05 = ((ContainerProva6)getActivity()).getVida05();

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        alternativa1 = (RadioButton) rootView.findViewById(R.id.alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.alternativa4);

        // PEGANDO O RADIOGROUP DO LAYOUT
        radioGroupQuestao = (RadioGroup) rootView.findViewById(R.id.radioGroupAlternativas);

        super.accessViews();

    }

}