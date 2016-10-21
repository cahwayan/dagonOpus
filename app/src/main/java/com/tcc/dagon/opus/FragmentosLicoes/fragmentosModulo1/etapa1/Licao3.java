package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao3 extends Fragment {
    View alternativa1,
         alternativa2,
         alternativa3,
         alternativa4;
    GerenciadorBanco DB_PROGRESSO;
    View btnChecar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        alternativa1 = getActivity().findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa1);
        alternativa2 = getActivity().findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa2);
        alternativa3 = getActivity().findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa3);
        alternativa4 = getActivity().findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa4);
        btnChecar = getActivity().findViewById(R.id.btnChecarResposta);

        listeners();
        return inflater.inflate(R.layout.fragment_modulo1_etapa1_licao3, container, false);

    }

    private void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // SE A ALTERNATIVA 1 ESTIVER SELECIONADA AO CLICAR
                if(alternativa1.isSelected()) {
                    // VERIFICAR CORRESPONDENCIA DA ALTERNATIVA 1 NO BANCO
                    // SE ESTIVER CERTA .................
                    if(DB_PROGRESSO.verificaPergunta(1,1,1,1) == 1) {
                        Toast.makeText(getContext(), "CERTO", Toast.LENGTH_SHORT).show();
                    // SE NÃO ESTIVER CERTA............
                    } else {
                        Toast.makeText(getContext(), "ERRADO", Toast.LENGTH_SHORT).show();
                    }
                } else if(alternativa2.isSelected()) {
                    if(DB_PROGRESSO.verificaPergunta(1,1,1,2) == 1) {
                        Toast.makeText(getContext(), "CERTO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "ERRADO", Toast.LENGTH_SHORT).show();
                    }
                } else if (alternativa3.isSelected()){
                    if(DB_PROGRESSO.verificaPergunta(1,1,1,3) == 1) {
                        Toast.makeText(getContext(), "CERTO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "ERRADO", Toast.LENGTH_SHORT).show();
                    }

                } else if (alternativa4.isSelected()) {
                    if(DB_PROGRESSO.verificaPergunta(1,1,1,4) == 1) {
                        Toast.makeText(getContext(), "CERTO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "ERRADO", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}