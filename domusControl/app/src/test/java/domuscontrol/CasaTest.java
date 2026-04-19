package domuscontrol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domus.DomusControl;
import casas.Casa;

class CasaTest {

    @Test
    void casaTemDivisoes() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        assertNotNull(casa);
        assertFalse(casa.getDivisoes().isEmpty());
    }

    @Test
    void deveCriarCasasNoSistema() {
        DomusControl dc = Testes.criarSistemaTeste();

        assertEquals(2, dc.getCasas().size());
    }

    @Test
    void casaDeveTerDivisoes() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        assertTrue(casa.getDivisoes().size() >= 1);
    }

    @Test
    void casaDeveTerUtilizadores() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        assertFalse(casa.getUtilizadores().isEmpty());
    }
}