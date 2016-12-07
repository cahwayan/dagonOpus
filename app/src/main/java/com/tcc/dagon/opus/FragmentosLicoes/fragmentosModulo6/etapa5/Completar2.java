package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo6.etapa5;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.tcc.dagon.opus.ClassesPai.Completar;
import com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo6.ContainerModulo6Etapa5;
import com.tcc.dagon.opus.R;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by cahwayan on 09/10/2016.
 */
public class Completar2 extends Completar {

    private EditText palavra1, palavra2, palavra3, palavra4;

    private String[] respostasCertas;
    private String[] respostasCertasAcentuadas;

    // MÉTODO ON CREATE DO FRAGMENTO
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.instanciaObjetos();
        // GUARDANDO O LAYOUT EM UMA VARIÁVEL PARA RETORNAR NO FIM DO MÉTODO
        super.rootView = inflater.inflate(R.layout.fragment_modulo6_etapa5_licao6, container, false);
        super.moduloAtual = 6;
        super.etapaAtual  = 5;
        //TRAZENDO AS VIEWS
        accessViews();

        listeners();

        return this.rootView;
    }

    protected void accessViews() {
        // PEGANDO A REFERENCIA DOS LAYOUTS DA ATIVIDADE CONTAINER
        mViewPager = ((ContainerModulo6Etapa5)getActivity()).getPager();
        tabStrip   = ((ContainerModulo6Etapa5)getActivity()).getTabStrip();
        mTabLayout = ((ContainerModulo6Etapa5)getActivity()).getmTabLayout();


        palavra1 = (EditText) rootView.findViewById(R.id.palavra1);
        palavra2 = (EditText) rootView.findViewById(R.id.palavra2);
        palavra3 = (EditText) rootView.findViewById(R.id.palavra3);
        palavra4 = (EditText) rootView.findViewById(R.id.palavra4);


        // INSTANCIANDO A LISTA
        listaEditTexts = new ArrayList<>();

        // ENCHENDO O ARRAY DE EDIT TEXTS COM AS EDIT TEXTS
        linhasCompletar = new EditText[] {palavra1, palavra2, palavra3, palavra4};

        // ENCHENDO A LISTA COM O ARRAY DE EDIT TEXTS
        listaEditTexts.addAll(Arrays.asList(linhasCompletar));

        super.accessViews();

        // CRIANDO OS VETORES DE RESPOSTAS
        respostasCertas = new String[]{"nova", "caneta", "cor", "destampar()"};
        respostasCertasAcentuadas = new String[]{"nova", "caneta", "cor", "destampar()"};
    }


    protected void listeners() {
        super.listeners();

        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checarRespostasCompletar(respostasCertas, respostasCertasAcentuadas);
            }
        });

        btnTentarNovamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tentarNovamente(respostasCertas, respostasCertasAcentuadas);
            }
        });
    }
}