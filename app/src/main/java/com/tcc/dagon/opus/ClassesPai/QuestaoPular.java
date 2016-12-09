package com.tcc.dagon.opus.ClassesPai;

import android.content.Intent;
import android.widget.Toast;

import com.tcc.dagon.opus.AprenderActivity;
import com.tcc.dagon.opus.utils.GerenciadorSharedPreferences;

/**
 * Created by cahwayan on 11/11/2016.
 */

public class QuestaoPular extends QuestaoProva {

    @Override
    protected void questaoFinal() {

        // ATUALIZANDO O PROGRESSO SE FOR A PRIMEIRA VEZ
        // SE O PROGRESSO DA ETAPA 1 DO MÓDULO 1 FOR MENOR OU IGUAL A TRÊS, É A PRIMEIRA VEZ QUE O USUÁRIO ESTÁ FAZENDO

        // AVANÇAR O PROGRESSO EM DOIS
        this.DB_PROGRESSO.atualizaProgressoModulo(moduloAtual + 2);

        this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual, 9);
        this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual + 1, 6);
        this.DB_PROGRESSO.atualizaProgressoEtapa(moduloAtual + 2, 1);

        DB_PROGRESSO.atualizaProgressoLicao(1,1,2);
        DB_PROGRESSO.atualizaProgressoLicao(1,2,3);
        DB_PROGRESSO.atualizaProgressoLicao(1,3,3);
        DB_PROGRESSO.atualizaProgressoLicao(1,4,3);
        DB_PROGRESSO.atualizaProgressoLicao(1,5,3);
        DB_PROGRESSO.atualizaProgressoLicao(1,6,4);
        DB_PROGRESSO.atualizaProgressoLicao(1,7,7);
        DB_PROGRESSO.atualizaProgressoLicao(1,8,1);
        DB_PROGRESSO.atualizaProgressoLicao(1,9,8);

        DB_PROGRESSO.atualizaProgressoLicao(2,1,3);
        DB_PROGRESSO.atualizaProgressoLicao(2,2,5);
        DB_PROGRESSO.atualizaProgressoLicao(2,3,7);
        DB_PROGRESSO.atualizaProgressoLicao(2,4,3);
        DB_PROGRESSO.atualizaProgressoLicao(2,5,1);
        DB_PROGRESSO.atualizaProgressoLicao(2,6,4);

        preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.NomePreferencia.flagProva1, true);
        preferencias.escreverFlagBoolean(GerenciadorSharedPreferences.NomePreferencia.flagProva2, true);


        // INICIANDO ATIVIDADE DOS MODULOS
        startActivity(new Intent(getActivity(), AprenderActivity.class));
        // TERMINANDO COM ESSA ATIVIDADE
        this.getActivity().finish();
    }

    // MÉTODO DISPARADO NO BOTÃO TENTAR NOVAMENTE
    @Override
    protected void tentarNovamente() {

        super.tentarNovamente();
        ContainerProva container = (ContainerProva) getActivity();

        if(container.getContagemVidas() == 0) {
            Toast.makeText(getActivity(), "Você perdeu todas as vidas! Tente de novo.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), AprenderActivity.class));
            this.getActivity().finish();
        }

    }
}
