package com.tcc.dagon.opus.etapas;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.tcc.dagon.opus.R;

public class EtapasModulo1Activity extends AppCompatActivity {
    /*LAYOUTS*/
    private LinearLayout etapa1,
                         etapa2,
                         etapa3,
                         etapa4;

    /*SUPER VARIÁVEL CONTEXT*/
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etapas_modulo_1);

        /*ACESSANDO AS VIEWS*/
        accessViews();

        /*ADICIONANDO OS CLICK LISTENERS*/
        ClickListenersEtapas();
    }


    private void accessViews() {
        /*RECUPEERANDO ITENS LAYOUT*/
        etapa1 = (LinearLayout) findViewById(R.id.Etapa1);
        etapa2 = (LinearLayout) findViewById(R.id.Etapa2);
        etapa3 = (LinearLayout) findViewById(R.id.Etapa3);
        etapa4 = (LinearLayout) findViewById(R.id.Etapa4);
    }

    private void ClickListenersEtapas() {
        etapa1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        etapa2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        etapa3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });

        etapa4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_botaoimageview));
            }
        });
    }
}
