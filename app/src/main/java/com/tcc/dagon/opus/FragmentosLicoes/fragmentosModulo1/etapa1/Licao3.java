package com.tcc.dagon.opus.FragmentosLicoes.fragmentosModulo1.etapa1;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.view.View.OnClickListener;
import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

/**
 * Created by charlinho on 09/10/2016.
 */
public class Licao3 extends Fragment {
    RadioButton alternativa1,
         alternativa2,
         alternativa3,
         alternativa4;
    GerenciadorBanco DB_PROGRESSO;
    Button btnChecar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DB_PROGRESSO = new GerenciadorBanco(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_modulo1_etapa1_licao3, container, false);
        alternativa1 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa1);
        alternativa2 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa2);
        alternativa3 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa3);
        alternativa4 = (RadioButton) rootView.findViewById(R.id.Modulo1Etapa1Pergunta1Alternativa4);

        btnChecar = (Button) rootView.findViewById(R.id.btnChecarResposta);

        listeners();

        return rootView;

    }

    private void listeners() {
        // LISTENER BOTÃO CHECAR RESPOSTA
        btnChecar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.anim_botaoimageview));
                // SE A ALTERNATIVA 1 ESTIVER SELECIONADA AO CLICAR
                if(alternativa1.isChecked()) {
                    // VERIFICAR CORRESPONDENCIA DA ALTERNATIVA 1 NO BANCO
                    // SE ESTIVER CERTA .................
                    if(DB_PROGRESSO.verificaPergunta(1,1,1,1) == 1) {
                        Toast.makeText(getContext(), "CERTO", Toast.LENGTH_SHORT).show();
                    // SE NÃO ESTIVER CERTA............
                    } else {
                        Toast.makeText(getContext(), "ERRADO", Toast.LENGTH_SHORT).show();
                    }
                } else if(alternativa2.isChecked()) {
                    if(DB_PROGRESSO.verificaPergunta(1,1,1,2) == 1) {
                        Toast.makeText(getContext(), "CERTO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "ERRADO", Toast.LENGTH_SHORT).show();
                    }
                } else if (alternativa3.isChecked()){
                    if(DB_PROGRESSO.verificaPergunta(1,1,1,3) == 1) {
                        Toast.makeText(getContext(), "CERTO", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "ERRADO", Toast.LENGTH_SHORT).show();
                    }

                } else if (alternativa4.isChecked()) {
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