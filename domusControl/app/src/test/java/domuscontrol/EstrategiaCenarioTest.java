package domuscontrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

import model.casas.EstrategiaCenario;
import model.dispositivos.Lampada;
import model.dispositivos.Dispositivo;
import model.dispositivos.Dispositivo.Estado;

class EstrategiaCenarioTest {

	// No topo do ficheiro de teste, dentro da classe
	static class CondicaoLamp1 implements Predicate<Dispositivo>, Serializable {
		@Override public boolean test(Dispositivo d) { 
			return d.getIdentificador().equals("lamp1"); 
		}
		@Override public boolean equals(Object o) { return o instanceof CondicaoLamp1; }
		@Override public int hashCode() { return getClass().hashCode(); }
	}

	static class AcaoVazia implements Consumer<Dispositivo>, Serializable {
		@Override public void accept(Dispositivo d) {}
		@Override public boolean equals(Object o) { return o instanceof AcaoVazia; }
		@Override public int hashCode() { return getClass().hashCode(); }
	}

	private EstrategiaCenario estrategia;
	private Lampada lampada;

	@BeforeEach
	void setUp() {
		lampada = new Lampada("lamp1", "Philips", "Hue", 1.0, Estado.LIGADO, 60, 10, 75);
		estrategia = new EstrategiaCenario(new CondicaoLamp1(), new AcaoVazia());
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
		EstrategiaCenario outra = new EstrategiaCenario(new CondicaoLamp1(), new AcaoVazia());
		// estrategia também tem de ser criada com new CondicaoLamp1(), new AcaoVazia()
		
		assertNotNull(estrategia.toString());
		assertEquals(estrategia, outra);
		assertEquals(estrategia.hashCode(), outra.hashCode());
	}
}
