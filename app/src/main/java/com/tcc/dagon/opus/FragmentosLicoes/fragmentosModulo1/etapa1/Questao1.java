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
        super.onCreateView(inflater, container, savedInstanceState);
        // INSTANCIA DE OBJETOS / BANCO / JANELA ALERTA / SONS
        super.instanciaObjetos();
        // MÓDULO A QUAL A PERGUNTA PERTENCE
        super.moduloAtual = 1;
        // ETAPA A QUAL A PERGUNTA PERTENCE
        super.etapaAtual = 1;
        // NÚMERO DA PERGUNTA
        super.questaoAtual = 1;

        //TRAZENDO AS VIEWS
        accessViews();

        // CARREGANDO A LÓGICA DOS LISTENERS DA CLASSE PAI
        super.listeners();

        return rootView;
    }

    protected void accessViews() {

        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        super.mViewPager = ((ContainerModulo1Etapa1)this.getActivity()).getPager();
        super.tabStrip   = ((ContainerModulo1Etapa1)this.getActivity()).getTabStrip();
        super.mTabLayout = ((ContainerModulo1Etapa1)this.getActivity()).getmTabLayout();

        super.accessViews();

    }


}