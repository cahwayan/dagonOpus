package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo3.etapa1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tcc.dagon.opus.ClassesPai.Questao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo3.ContainerModulo3Etapa1;
import com.tcc.dagon.opus.R;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Questao1 extends Questao {

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DE OBJETOS / BANCO / JANELA ALERTA / SONS
        super.instanciaObjetos();
        // MÓDULO A QUAL A PERGUNTA PERTENCE
        super.moduloAtual = 3;
        // ETAPA A QUAL A PERGUNTA PERTENCE
        super.etapaAtual = 1;
        // NÚMERO DA PERGUNTA
        super.questaoAtual = 1;

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo3_etapa1_licao2, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

        // Carregando os listeners
        super.listeners();

        return this.rootView;
    }


    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo3Etapa1)getActivity()).getPager();
        tabStrip   = ((ContainerModulo3Etapa1)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo3Etapa1)getActivity()).getmTabLayout();

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        alternativa1 = (RadioButton) rootView.findViewById(R.id.Modulo3Etapa1Pergunta1Alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.Modulo3Etapa1Pergunta1Alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.Modulo3Etapa1Pergunta1Alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.Modulo3Etapa1Pergunta1Alternativa4);

        // PEGANDO O RADIOGROUP DO LAYOUT
        containerRadioButtons = (RadioGroup) rootView.findViewById(R.id.radioGroupModulo3Etapa1Licao2);

        super.accessViews();
    }
}