package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1.ContainerModulo1Etapa2;
import com.tcc.dagon.opus.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Completar1 extends Completar {

    private EditText palavra1,
                    palavra2,
                    palavra3,
                    palavra4,
                    palavra5,
                    palavra6;


    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo1_etapa2_licao4, container, false);
        super.moduloAtual = 1;
        super.etapaAtual  = 2;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo1Etapa2)getActivity()).getPager();
        tabStrip   = ((ContainerModulo1Etapa2)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo1Etapa2)getActivity()).getmTabLayout();

        palavra1 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha2Palavra1);
        palavra2 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha2Palavra2);
        palavra3 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha2Palavra3);

        palavra4 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha3Palavra1);

        palavra5 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha4Palavra1);
        palavra6 = (EditText) rootView.findViewById(R.id.Modulo1Etapa2Pergunta2Linha4Palavra2);

        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4, palavra5, palavra6};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));




        // CRIANDO OS VETORES DE RESPOSTAS
        super.respostasCertas = new String[]{"olhar", "para", "direita", "atravesse", "nao", "atravesse"};
        super.respostasCertasAcentuadas = new String[]{"olhar", "para", "direita", "atravesse", "não", "atravesse"};

        super.accessViews();

    }


    protected void listeners() {
        super.listeners();





    }
}