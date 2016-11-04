package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.tcc.dagon.opus.ClassesPai.Questao;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa1;
import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 09/10/2016.
 * ESSA CLASSE É UMA EXTENSÃO DA CLASSE QUESTÃO
 * TODA A LÓGICA FICA LÁ, AQUI APENAS INSTANCIAMOS ALGUNS OBJETOS, ACERTAMOS
 * ALGUNS VALORES SOBRE O MODULO, ETAPA E QUESTAO ATUAL, E RESGATAMOS AS VIEWS
 * PARA QUE ELAS POSSAM SER TRABALHADAS
 */
public class Questao1 extends Questao {

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // INSTANCIA DE OBJETOS / BANCO / JANELA ALERTA / SONS
        super.instanciaObjetos();
        // MÓDULO A QUAL A PERGUNTA PERTENCE
        super.moduloAtual = 1;
        // ETAPA A QUAL A PERGUNTA PERTENCE
        super.etapaAtual = 1;
        // NÚMERO DA PERGUNTA
        super.questaoAtual = 1;

        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo1_etapa1_licao3, container, false);

        //TRAZENDO AS VIEWS
        accessViews();

        // CARREGANDO A LÓGICA DOS LISTENERS DA CLASSE PAI
        super.listeners();

        return this.rootView;
    }

    protected void accessViews() {

        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        super.mViewPager = ((ContainerModulo1Etapa1)this.getActivity()).getPager();
        super.tabStrip   = ((ContainerModulo1Etapa1)this.getActivity()).getTabStrip();
        super.mTabLayout = ((ContainerModulo1Etapa1)this.getActivity()).getmTabLayout();

        // PEGANDO OS RADIO BUTTONS DO LAYOUT
        super.alternativa1 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa1);
        super.alternativa2 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa2);
        super.alternativa3 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa3);
        super.alternativa4 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa4);

        // PEGANDO O RADIOGROUP DO LAYOUT
        super.containerRadioButtons = (RadioGroup) rootView.findViewById(R.id.radioGroupModulo1Etapa1Licao3);

        super.accessViews();

    }


}