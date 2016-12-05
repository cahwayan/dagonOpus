package com.tcc.dagon.opus.FragmentosLicoes.fragmentosProvas.Prova4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tcc.dagon.opus.ClassesPai.QuestaoProva;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Provas.ContainerProva4;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Questao4 extends QuestaoProva {

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DE OBJETOS / BANCO / JANELA ALERTA / SONS
        super.instanciaObjetos();
        // MÓDULO A QUAL A PERGUNTA PERTENCE
        super.moduloAtual = 4;
        // ETAPA A QUAL A PERGUNTA PERTENCE
        super.etapaAtual = 6;
        // NÚMERO DA PERGUNTA
        super.questaoAtual = 2;

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo4_prova_questao4, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

        // Carregando os listeners
        super.listeners();

        return this.rootView;
    }


    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerProva4)getActivity()).getPager();
        tabStrip   = ((ContainerProva4)getActivity()).getTabStrip();
        mTabLayout = ((ContainerProva4)getActivity()).getmTabLayout();

        vida01 = ((ContainerProva4)getActivity()).getVida01();
        vida02 = ((ContainerProva4)getActivity()).getVida02();
        vida03 = ((ContainerProva4)getActivity()).getVida03();
        vida04 = ((ContainerProva4)getActivity()).getVida04();
        vida05 = ((ContainerProva4)getActivity()).getVida05();

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        alternativa1 = (RadioButton) rootView.findViewById(R.id.ProvaModulo4Pergunta4Alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.ProvaModulo4Pergunta4Alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.ProvaModulo4Pergunta4Alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.ProvaModulo4Pergunta4Alternativa4);

        // PEGANDO O RADIOGROUP DO LAYOUT
        containerRadioButtons = (RadioGroup) rootView.findViewById(R.id.radioGroupProvaModulo4Pergunta4);

        super.accessViews();
    }

}