package com.tcc.dagon.opus.ContainerLicoes.Modulos.Modulo1;
import android.os.Bundle;
import com.tcc.dagon.opus.ClassesPai.ContainerEtapa;
import com.tcc.dagon.opus.R;

public class ContainerModulo1Etapa1 extends ContainerEtapa {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.moduloAtual = 1;
        super.etapaAtual = 1;

        // LAYOUT QUE O CONTAINER VAI PUXAR
        super.onCreate(savedInstanceState);
        this.desbloquearLicoes();

    }


    @Override
    protected void desbloquearLicoes() {

        int progresso = DB_PROGRESSO.verificaProgressoLicao(moduloAtual, etapaAtual);
        switch(progresso) {
            default:
                mTabLayout.getTabAt(2).setIcon(R.drawable.icon_pergunta);
                tabStrip.getChildAt(2).setClickable(true);
                tabStrip.getChildAt(2).setEnabled(true);

                for(int i = 0; i < progresso; i++) {
                    if(mTabLayout.getTabAt(i) != null) {
                            mTabLayout.getTabAt(i).setIcon(R.drawable.icon_licao);
                    }
                    tabStrip.getChildAt(i).setClickable(true);
                    tabStrip.getChildAt(i).setEnabled(true);
                }
                break;
        }
    }

}
