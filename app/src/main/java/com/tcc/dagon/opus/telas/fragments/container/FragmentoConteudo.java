package com.tcc.dagon.opus.telas.fragments.container;

/**
 * Created by Felipe on 04/03/2017.
 */

public class FragmentoConteudo {

    public static int BLOQUEADO = 0;
    public static int LIBERADO = 1;

    private int indexFragmento;

    FragmentoConteudo(int indexFragmento) {
        this.indexFragmento = indexFragmento;
    }

    public int getEstadoLicaoEmRelacaoAoProgresso(int progresso) {
        return progresso >= indexFragmento ? LIBERADO : BLOQUEADO;
    }

    public int getIndexFragmento() {
        return this.indexFragmento;
    }


}
