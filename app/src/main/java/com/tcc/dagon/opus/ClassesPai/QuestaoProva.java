package com.tcc.dagon.opus.ClassesPai;

import android.view.View;

import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 11/11/2016.
 */

public class QuestaoProva extends Questao{

    @Override
    protected void avancarQuestao() {
        // SUMINDO COM O BOTÃO AVANÇAR
        btnAvancar.setVisibility(View.GONE);

        // TRAZENDO O BOTÃO CHECAR NOVAMENTE
        btnChecar.setVisibility(View.VISIBLE);

        // REABILITANDO OS RADIO BUTTONS
        habilitarRadioButtons();

        // TROCANDO O ICONE DO CADEADO

        mTabLayout.getTabAt(mViewPager.getCurrentItem() + 1).setIcon(R.drawable.icon_pergunta);

        // TORNANDO CLICAVEL A TAB QUE SERÁ DESBLOQUEADA
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setClickable(true);
        tabStrip.getChildAt(mViewPager.getCurrentItem() + 1).setEnabled(true);


        //DESMARCANDO RADIO BUTTON
        desmarcarRadioButtons();

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
