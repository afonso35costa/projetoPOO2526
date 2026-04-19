package domuscontrol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domus.DomusControl;
import casas.Casa;
import casas.Divisao;

class DivisaoTest {

    @Test
    void divisoesNaoDevemEstarVazias() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        assertFalse(casa.getDivisoes().isEmpty());
    }

    @Test
    void divisaoTemDispositivos() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        Divisao sala = casa.getDivisoes().get("Sala");

        assertNotNull(sala);
        assertFalse(sala.getDispositivos().isEmpty());
    }

    @Test
    void todasDivisoesTemDispositivos() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        for (Divisao d : casa.getDivisoes().values()) {
            assertFalse(d.getDispositivos().isEmpty());
        }
    }
}