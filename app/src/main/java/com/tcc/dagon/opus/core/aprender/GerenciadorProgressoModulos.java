package com.tcc.dagon.opus.core.aprender;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.tcc.dagon.opus.databases.GerenciadorBanco;

import java.util.List;

import static java.lang.String.valueOf;

/**
 * Created by cahwayan on 09/02/2017.
 */

public class GerenciadorProgressoModulos{

    public void loadProgressBars(List<RoundCornerProgressBar> listaBarras, GerenciadorBanco db) {
        for(RoundCornerProgressBar barra : listaBarras) {
            int numEtapa = (listaBarras.indexOf(barra) + 1);
            float progresso = db.getProgressoEtapa(numEtapa);
            barra.setProgress(progresso);
        }
    }


}
