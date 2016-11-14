package com.tcc.dagon.opus.ClassesPai;

import com.tcc.dagon.opus.R;

/**
 * Created by cahwayan on 14/11/2016.
 */


public class ContainerProva extends ContainerEtapa {

    @Override
    protected void desbloquearLicoes() {
        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                for(int i = 0; i <= progresso - 1; i += 1) {
                    if(mTabLayout.getTabAt(i) != null) {
                        mTabLayout.getTabAt(i).setIcon(R.drawable.icon_pergunta);
                        tabStrip.getChildAt(i).setClickable(true);
                        tabStrip.getChildAt(i).setEnabled(true);
                    }
                }
                break;
        }
    }


}
