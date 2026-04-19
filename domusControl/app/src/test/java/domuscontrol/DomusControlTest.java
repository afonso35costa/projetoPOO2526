package domuscontrol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domus.DomusControl;

class DomusControlTest {

    @Test
    void sistemaTemCasas() {
        DomusControl dc = Testes.criarSistemaTeste();

        assertNotNull(dc);
        assertFalse(dc.getCasas().isEmpty());
    }

    @Test
    void sistemaNaoPodeSerNull() {
        DomusControl dc = Testes.criarSistemaTeste();

        assertNotNull(dc);
    }
}