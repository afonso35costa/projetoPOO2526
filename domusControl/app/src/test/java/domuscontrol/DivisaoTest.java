package domuscontrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import model.casas.Divisao;
import model.dispositivos.Dispositivo.Estado;
import model.dispositivos.Lampada;

class DivisaoTest {

    private Divisao divisao;
    private Lampada lampada1;
    private Lampada lampada2;

    @BeforeEach
    void setUp() {
        divisao = new Divisao("div1", new HashMap<>());
        lampada1 = new Lampada("lamp1", "Philips", "Hue", 1.2, Estado.LIGADO, 60, 10, 80);
        lampada2 = new Lampada("lamp2", "Osram", "Smart", 2.0, Estado.DESLIGADO, 30, 4, 40);
    }

    @Test
    void construtorVazio() {
        Divisao divisaoVazia = new Divisao();
        assertNotNull(divisaoVazia);
        assertEquals("", divisaoVazia.getId());
        assertTrue(divisaoVazia.getDispositivos().isEmpty());
    }

    @Test
    void construtorComMapa() {
        Map<String, model.dispositivos.Dispositivo> dispositivos = new HashMap<>();
        dispositivos.put(lampada1.getIdentificador(), lampada1);

        Divisao d = new Divisao("divX", dispositivos);

        assertEquals("divX", d.getId());
        assertEquals(1, d.getDispositivos().size());
        assertTrue(d.getDispositivos().containsKey("lamp1"));
    }

    @Test
    void construtorCopia() {
        divisao.adicionarDispositivo(lampada1);
        divisao.adicionarDispositivo(lampada2);

        Divisao copia = new Divisao(divisao);

        assertEquals(divisao, copia);
        assertNotSame(divisao, copia);
        assertEquals(2, copia.getDispositivos().size());
    }

    @Test
    void gettersESetters() {
        divisao.setId("novaDiv");

        Map<String, model.dispositivos.Dispositivo> dispositivos = new HashMap<>();
        dispositivos.put(lampada1.getIdentificador(), lampada1);
        divisao.setDispositivos(dispositivos);

        assertEquals("novaDiv", divisao.getId());
        assertEquals(1, divisao.getDispositivos().size());
        assertTrue(divisao.getDispositivos().containsKey("lamp1"));
    }

    @Test
    void toStringEqualsEHashCode() {
        Divisao outra = new Divisao("div1", new HashMap<>());

        assertNotNull(divisao.toString());
        assertEquals(divisao, outra);
        assertEquals(divisao.hashCode(), outra.hashCode());
    }

    @Test
    void getNumDispositivos() {
        assertEquals(0, divisao.getNumDispositivos());

        divisao.adicionarDispositivo(lampada1);
        divisao.adicionarDispositivo(lampada2);

        assertEquals(2, divisao.getNumDispositivos());
    }

    @Test
    void adicionarERemoverDispositivo() {
        divisao.adicionarDispositivo(lampada1);
        assertEquals(1, divisao.getDispositivos().size());
        assertTrue(divisao.getDispositivos().containsKey("lamp1"));

        divisao.removerDispositivo("lamp1");
        assertTrue(divisao.getDispositivos().isEmpty());
    }

    @Test
    void calcularConsumoDivisao() {
        divisao.adicionarDispositivo(lampada1);
        divisao.adicionarDispositivo(lampada2);

        double esperado = (60 / 60.0) * 1.2 + (30 / 60.0) * 2.0;
        assertEquals(esperado, divisao.calcularConsumoDivisao(), 0.0001);
    }
}
