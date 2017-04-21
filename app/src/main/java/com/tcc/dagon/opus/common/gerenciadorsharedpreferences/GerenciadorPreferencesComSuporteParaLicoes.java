package com.tcc.dagon.opus.common.gerenciadorsharedpreferences;

import android.content.Context;

import static com.tcc.dagon.opus.ui.curso.constantes.ModuloConstants.*;

/**
 * Created by cahwayan on 26/02/2017.
 */

public class GerenciadorPreferencesComSuporteParaLicoes extends GerenciadorSharedPreferences implements PreferenciasComSuporteParaLicoes {

    private final String[][] PROGRESSO_LICOES;

    public GerenciadorPreferencesComSuporteParaLicoes(Context context) {
        super(context);
        PROGRESSO_LICOES = inicializarVetorDeProgressoLicoes();
    }

    @Override
    public int lerFlagInt(String nomeFlag) {
        return sharedPreferences.getInt(nomeFlag, 1);
    }

    public String[][] inicializarVetorDeProgressoLicoes() {

        String[][] progressoLicoes = new String[6][10];

        String[] flagsLicoes = new String[]
                {PROGRESSO_LICOES_MODULO0_ETAPA0, PROGRESSO_LICOES_MODULO0_ETAPA1, PROGRESSO_LICOES_MODULO0_ETAPA2, PROGRESSO_LICOES_MODULO0_ETAPA3, PROGRESSO_LICOES_MODULO0_ETAPA4, PROGRESSO_LICOES_MODULO0_ETAPA5, PROGRESSO_LICOES_MODULO0_ETAPA6, PROGRESSO_LICOES_MODULO0_ETAPA7, PROGRESSO_LICOES_MODULO0_ETAPA8, PROGRESSO_LICOES_MODULO0_ETAPA9,
                 PROGRESSO_LICOES_MODULO1_ETAPA0, PROGRESSO_LICOES_MODULO1_ETAPA1, PROGRESSO_LICOES_MODULO1_ETAPA2, PROGRESSO_LICOES_MODULO1_ETAPA3, PROGRESSO_LICOES_MODULO1_ETAPA4, PROGRESSO_LICOES_MODULO1_ETAPA5, PROGRESSO_LICOES_MODULO1_ETAPA6, PROGRESSO_LICOES_MODULO1_ETAPA7, PROGRESSO_LICOES_MODULO1_ETAPA8, PROGRESSO_LICOES_MODULO1_ETAPA9,
                 PROGRESSO_LICOES_MODULO2_ETAPA0, PROGRESSO_LICOES_MODULO2_ETAPA1, PROGRESSO_LICOES_MODULO2_ETAPA2, PROGRESSO_LICOES_MODULO2_ETAPA3, PROGRESSO_LICOES_MODULO2_ETAPA4, PROGRESSO_LICOES_MODULO2_ETAPA5, PROGRESSO_LICOES_MODULO2_ETAPA6, PROGRESSO_LICOES_MODULO2_ETAPA7, PROGRESSO_LICOES_MODULO2_ETAPA8, PROGRESSO_LICOES_MODULO2_ETAPA9,
                 PROGRESSO_LICOES_MODULO3_ETAPA0, PROGRESSO_LICOES_MODULO3_ETAPA1, PROGRESSO_LICOES_MODULO3_ETAPA2, PROGRESSO_LICOES_MODULO3_ETAPA3, PROGRESSO_LICOES_MODULO3_ETAPA4, PROGRESSO_LICOES_MODULO3_ETAPA5, PROGRESSO_LICOES_MODULO3_ETAPA6, PROGRESSO_LICOES_MODULO3_ETAPA7, PROGRESSO_LICOES_MODULO3_ETAPA8, PROGRESSO_LICOES_MODULO3_ETAPA9,
                 PROGRESSO_LICOES_MODULO4_ETAPA0, PROGRESSO_LICOES_MODULO4_ETAPA1, PROGRESSO_LICOES_MODULO4_ETAPA2, PROGRESSO_LICOES_MODULO4_ETAPA3, PROGRESSO_LICOES_MODULO4_ETAPA4, PROGRESSO_LICOES_MODULO4_ETAPA5, PROGRESSO_LICOES_MODULO4_ETAPA6, PROGRESSO_LICOES_MODULO4_ETAPA7, PROGRESSO_LICOES_MODULO4_ETAPA8, PROGRESSO_LICOES_MODULO4_ETAPA9,
                 PROGRESSO_LICOES_MODULO5_ETAPA0, PROGRESSO_LICOES_MODULO5_ETAPA1, PROGRESSO_LICOES_MODULO5_ETAPA2, PROGRESSO_LICOES_MODULO5_ETAPA3, PROGRESSO_LICOES_MODULO5_ETAPA4, PROGRESSO_LICOES_MODULO5_ETAPA5, PROGRESSO_LICOES_MODULO5_ETAPA6, PROGRESSO_LICOES_MODULO5_ETAPA7, PROGRESSO_LICOES_MODULO5_ETAPA8, PROGRESSO_LICOES_MODULO5_ETAPA9};

        for(int x = 0; x < progressoLicoes.length; x++) {
            for(int y = 0; y < progressoLicoes[x].length; y++) {
                progressoLicoes[x][y] = flagsLicoes[y];
            }
        }

        return progressoLicoes;

    }

    public int getProgressoLicao(int numModuloReferente, int numEtapaReferente) {
        return lerFlagInt(PROGRESSO_LICOES[numModuloReferente][numEtapaReferente]);
    }

    public void setProgressoLicao(int numModuloReferente, int numEtapaReferente, int valor) {
        modFlag(PROGRESSO_LICOES[numModuloReferente][numEtapaReferente], valor);
    }

    public void somarPontos(int moduloAtual, int pontos) {

        String flag;

        switch (moduloAtual) {
            case MODULO0:
                flag = PONTOS_MODULO0;
                break;

            case MODULO1:
                flag = PONTOS_MODULO1;
                break;

            case MODULO2:
                flag = PONTOS_MODULO2;
                break;

            case MODULO3:
                flag = PONTOS_MODULO3;
                break;

            case MODULO4:
                flag = PONTOS_MODULO4;
                break;

            case MODULO5:
                flag = PONTOS_MODULO5;
                break;

            default:
                flag = "defaultPontos";
                break;
        }

        int pontuacaoAnterior = getPontos(moduloAtual);
        modFlag(flag, (pontuacaoAnterior + pontos) );


    }


}
