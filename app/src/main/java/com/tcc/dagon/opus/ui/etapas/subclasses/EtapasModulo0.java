package com.tcc.dagon.opus.ui.etapas.subclasses;


import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcc.dagon.opus.R;
import com.tcc.dagon.opus.ui.etapas.EtapasActivity;

/**
 * Created by cahwayan on 01/03/2017.
 */

public final class EtapasModulo0 extends EtapasActivity {

    @Override
    protected final int getLayout() {
        return R.layout.activity_etapas_modulo0;
    }

    @Override
    protected final void findViews() {

        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa0) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa1) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa2) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa3) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa4) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa5) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa6) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa7) );
        listBtnEtapas.add( (LinearLayout) findViewById(R.id.etapa8) );

        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa0) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa1) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa2) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa3) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa4) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa5) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa6) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa7) );
        listTxtTituloEtapas.add( (TextView) findViewById(R.id.tituloEtapa8) );

        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa0) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa1) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa2) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa3) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa4) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa5) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa6) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa7) );
        listBarraInferiorEtapas.add( (TextView) findViewById(R.id.questoesEtapa8) );
    }

}
