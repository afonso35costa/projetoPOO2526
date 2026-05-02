package domuscontrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import model.casas.Cenario;
import model.casas.EstrategiaCenario;
import model.dispositivos.Dispositivo;
import model.dispositivos.Lampada;
import model.dispositivos.Dispositivo.Estado;

class CenarioTest {

	private Cenario cenario;
	private Lampada lampada1;
	private Lampada lampada2;

	@BeforeEach
	void setUp() {
		cenario = new Cenario("cenario1", false, new ArrayList<>(), new ArrayList<>());
		lampada1 = new Lampada("lamp1", "Philips", "Hue", 1.0, Estado.LIGADO, 60, 10, 75);
		lampada2 = new Lampada("lamp2", "Osram", "Smart", 2.0, Estado.DESLIGADO, 30, 4, 40);
	}

	@Test
	void construtorVazio() {
		Cenario vazio = new Cenario();
		assertNotNull(vazio);
		assertEquals("", vazio.getNome());
		assertFalse(vazio.isAtivo());
		assertTrue(vazio.getDispositivos().isEmpty());
		assertTrue(vazio.getEstrategias().isEmpty());
	}

	@Test
	void construtorComArgumentos() {
		List<Dispositivo> dispositivos = new ArrayList<>();
		dispositivos.add(lampada1);

		List<EstrategiaCenario> estrategias = new ArrayList<>();
		estrategias.add(new EstrategiaCenario(d -> true, d -> {}));

		Cenario c = new Cenario("cenarioX", true, dispositivos, estrategias);

		assertEquals("cenarioX", c.getNome());
		assertTrue(c.isAtivo());
		assertEquals(1, c.getDispositivos().size());
		assertEquals(1, c.getEstrategias().size());
	}

	@Test
	void construtorCopia() {
		cenario.setDispositivos(List.of(lampada1, lampada2));
		cenario.adicionarEstrategia(d -> true, d -> {});

		Cenario copia = new Cenario(cenario);

		assertEquals(cenario, copia);
		assertNotSame(cenario, copia);
		assertEquals(2, copia.getDispositivos().size());
		assertEquals(1, copia.getEstrategias().size());
	}

	@Test
	void gettersESetters() {
		cenario.setNome("novoNome");
		cenario.setAtivo(true);
		cenario.setDispositivos(List.of(lampada1));

		List<EstrategiaCenario> estrategias = new ArrayList<>();
		estrategias.add(new EstrategiaCenario(d -> true, d -> {}));
		cenario.setEstrategias(estrategias);

		assertEquals("novoNome", cenario.getNome());
		assertTrue(cenario.isAtivo());
		assertEquals(1, cenario.getDispositivos().size());
		assertEquals(1, cenario.getEstrategias().size());
	}

	@Test
	void toStringEqualsEHashCode() {
		Cenario outro = new Cenario("cenario1", false, new ArrayList<>(), new ArrayList<>());

		assertNotNull(cenario.toString());
		assertEquals(cenario, outro);
		assertEquals(cenario.hashCode(), outro.hashCode());
	}

	@Test
	void adicionarEstrategia() {
		cenario.adicionarEstrategia(d -> d.getIdentificador().equals("lamp1"), d -> {});

		assertEquals(1, cenario.getEstrategias().size());
	}

	@Test
	void executarNaoAtivoNaoFazNada() {
		AtomicBoolean executado = new AtomicBoolean(false);
		cenario.setDispositivos(List.of(lampada1));
		cenario.adicionarEstrategia(d -> true, d -> executado.set(true));

		cenario.executar();

		assertFalse(executado.get());
	}

	@Test
	void executarAtivoExecutaEstrategias() {
		AtomicBoolean executadoLamp1 = new AtomicBoolean(false);
		AtomicBoolean executadoLamp2 = new AtomicBoolean(false);

		cenario.setAtivo(true);
		cenario.setDispositivos(List.of(lampada1, lampada2));
		cenario.adicionarEstrategia(d -> d.getIdentificador().equals("lamp1"), d -> executadoLamp1.set(true));
		cenario.adicionarEstrategia(d -> d.getIdentificador().equals("lamp2"), d -> executadoLamp2.set(true));

		cenario.executar();

		assertTrue(executadoLamp1.get());
		assertTrue(executadoLamp2.get());
	}

	@Test
	void getEstrategiasDevolveCopia() {
		cenario.adicionarEstrategia(d -> true, d -> {});

		List<EstrategiaCenario> estrategias = cenario.getEstrategias();

		assertEquals(1, estrategias.size());
		assertNotSame(cenario.getEstrategias(), estrategias);
	}
}
