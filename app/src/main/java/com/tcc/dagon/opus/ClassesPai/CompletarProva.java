package com.tcc.dagon.opus.ClassesPai;

import android.view.View;

import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 11/11/2016.
 */

public class CompletarProva extends Completar {

    @Override
    protected void avancarCompletar() {

        limparEditTexts(listaEditTexts);
        habilitarEditTexts(listaEditTexts);

        // SUMINDO COM O BOTAO TENTAR NOVAMENTE
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTAO CHECAR
        btnChecar.setVisibility(View.VISIBLE);

        // TROCANDO O ICONE DO CADEADO
        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 1).setIcon(R.drawable.icon_pergunta);

        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setEnabled(true);

        // SUMINDO COM AS IMAGENS DE CERTO OU ERRADO
        imgRespostaCerta.setVisibility(View.GONE);
        imgRespostaErrada.setVisibility(View.GONE);

        // TROCANDO O FRAGMENTO
        moveNext(mViewPager);

        if(DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual) <= mViewPager.getCurrentItem()) {
            // AVANÇAR O PROGRESSO EM DOIS
            DB_PROGRESSO.atualizaProgressoLicao(moduloAtual, etapaAtual, (mViewPager.getCurrentItem() + 1) );
        }

    }
}
