package domuscontrol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domus.DomusControl;
import casas.Casa;
import dispositivos.Dispositivo;
import dispositivos.Dispositivo.Estado;

class DispositivoTest {

    @Test
    void divisaoTemDispositivos() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        Dispositivo d = casa.getDivisoes()
                            .values()
                            .iterator()
                            .next()
                            .getDispositivos()
                            .iterator()
                            .next();

        assertNotNull(d);
    }

    @Test
    void deveExistirPeloMenosUmDispositivo() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        boolean temDispositivos = casa.getDivisoes()
                .values()
                .stream()
                .anyMatch(d -> !d.getDispositivos().isEmpty());

        assertTrue(temDispositivos);
    }

    @Test
    void dispositivosDevemTerEstadoValido() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        for (var divisao : casa.getDivisoes().values()) {
            for (Dispositivo d : divisao.getDispositivos()) {
                assertNotNull(d.getEstado());
                assertTrue(
                    d.getEstado() == Estado.LIGADO ||
                    d.getEstado() == Estado.DESLIGADO ||
                    d.getEstado() == Estado.ABERTO ||
                    d.getEstado() == Estado.FECHADO
                );
            }
        }
    }

    @Test
    void consumoDispositivosDeveSerPositivo() {
        DomusControl dc = Testes.criarSistemaTeste();

        Casa casa = dc.getCasas().iterator().next();

        for (var divisao : casa.getDivisoes().values()) {
            for (Dispositivo d : divisao.getDispositivos()) {
                assertTrue(d.getConsumoPorHora() > 0);
            }
        }
    }
}