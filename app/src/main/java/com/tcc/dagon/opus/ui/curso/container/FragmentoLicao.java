package com.tcc.dagon.opus.ui.curso.container;

/**
 * Created by Felipe on 04/03/2017.
 */

class FragmentoLicao {

    static int BLOQUEADO = 0;
    static int LIBERADO = 1;

    private int indexFragmento;

    FragmentoLicao(int indexFragmento) {
        this.indexFragmento = indexFragmento;
    }

    int getEstadoLicaoEmRelacaoAoProgresso(int progresso) {
        return progresso >= indexFragmento ? LIBERADO : BLOQUEADO;
    }

    public int getIndexFragmento() {
        return this.indexFragmento;
    }


}
