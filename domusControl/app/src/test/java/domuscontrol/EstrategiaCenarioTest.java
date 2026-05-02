package domuscontrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicBoolean;

import model.casas.EstrategiaCenario;
import model.dispositivos.Dispositivo;
import model.dispositivos.Lampada;
import model.dispositivos.Dispositivo.Estado;

class EstrategiaCenarioTest {

	private EstrategiaCenario estrategia;
	private Lampada lampada;

	@BeforeEach
	void setUp() {
		lampada = new Lampada("lamp1", "Philips", "Hue", 1.0, Estado.LIGADO, 60, 10, 75);
		estrategia = new EstrategiaCenario(d -> d.getIdentificador().equals("lamp1"), d -> {});
	}

	@Test
	void construtorComArgumentos() {
		assertNotNull(estrategia);
		assertTrue(estrategia.getCondicao().test(lampada));
	}

	@Test
	void construtorVazio() {
		EstrategiaCenario vazia = new EstrategiaCenario();

		assertNotNull(vazia);
		assertTrue(vazia.getCondicao().test(lampada));
		assertDoesNotThrow(() -> vazia.getAcao().accept(lampada));
	}

	@Test
	void construtorCopia() {
		EstrategiaCenario copia = new EstrategiaCenario(estrategia);

		assertEquals(estrategia, copia);
		assertNotSame(estrategia, copia);
	}

	@Test
	void gettersESetters() {
		AtomicBoolean executado = new AtomicBoolean(false);

		estrategia.setCondicao(d -> d.getEstado() == Estado.LIGADO);
		estrategia.setAcao(d -> executado.set(true));

		assertTrue(estrategia.getCondicao().test(lampada));
		estrategia.getAcao().accept(lampada);
		assertTrue(executado.get());
	}

	@Test
	void cloneFunciona() {
		EstrategiaCenario copia = estrategia.clone();

		assertEquals(estrategia, copia);
		assertNotSame(estrategia, copia);
	}

	@Test
	void toStringEqualsEHashCode() {
		EstrategiaCenario outra = new EstrategiaCenario(d -> d.getIdentificador().equals("lamp1"), d -> {});

		assertNotNull(estrategia.toString());
		assertEquals(estrategia, outra);
		assertEquals(estrategia.hashCode(), outra.hashCode());
	}
}
