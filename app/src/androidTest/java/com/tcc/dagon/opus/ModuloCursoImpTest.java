package com.tcc.dagon.opus;

import android.support.test.runner.AndroidJUnit4;

import com.tcc.dagon.opus.ui.aprender.ModuloCurso;
import com.tcc.dagon.opus.ui.aprender.ModuloCursoImp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Created by cahwayan on 04/04/2017.
 */

@RunWith(AndroidJUnit4.class)
public class ModuloCursoImpTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        ModuloCursoImp modulo = new ModuloCursoImp(-1, "A++");
    }

    @Test
    public void moduloCompletoEReconhecidoCorretamente() {
        ModuloCursoImp modulo = new ModuloCursoImp(ModuloCurso.MODULO1, "A++");
        modulo.atualizarEstadoConformeProgressoAtual( /* Prog Módulo */2);

        Assert.assertEquals(modulo.getEstadoAtual(), ModuloCurso.IS_COMPLETO);
    }

    @Test
    public void moduloCertificadoEReconhecidoCorretamente() {
        ModuloCursoImp modulo = new ModuloCursoImp(ModuloCurso.MODULO_CERTIFICADO, "A++");
        modulo.atualizarEstadoConformeProgressoAtual(6);
        Assert.assertEquals(modulo.getEstadoAtual(), ModuloCurso.IS_CERTIFICADO);
    }
}
