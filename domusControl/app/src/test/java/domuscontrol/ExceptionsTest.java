package domuscontrol;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import exceptions.CasaJaExisteException;
import exceptions.CasaNaoExisteException;
import exceptions.CasaNaoSelecionadaException;
import exceptions.CredenciaisInvalidasException;
import exceptions.DispositivoNaoExisteException;
import exceptions.DivisaoNaoExisteException;
import exceptions.DivisaoNaoSelecionadaException;
import exceptions.SessaoNaoIniciadaException;
import exceptions.UtilizadorJaExisteException;
import exceptions.UtilizadorNaoExisteException;

class ExceptionsTest {

	@Test
	void testeCasaJaExisteException() {
		CasaJaExisteException ex = new CasaJaExisteException("casa1");
		assertEquals("Casa 'casa1' já existe.", ex.getMessage());
	}

	@Test
	void testeCasaNaoExisteException() {
		CasaNaoExisteException ex = new CasaNaoExisteException("casa1");
		assertEquals("Casa 'casa1' não existe.", ex.getMessage());
	}

	@Test
	void testeCasaNaoSelecionadaException() {
		CasaNaoSelecionadaException ex = new CasaNaoSelecionadaException();
		assertEquals("Nenhuma casa selecionada.", ex.getMessage());
	}

	@Test
	void testeCredenciaisInvalidasException() {
		CredenciaisInvalidasException ex = new CredenciaisInvalidasException();
		assertEquals("Username ou password incorretos.", ex.getMessage());
	}

	@Test
	void testeDispositivoNaoExisteException() {
		DispositivoNaoExisteException ex = new DispositivoNaoExisteException("disp1");
		assertEquals("Dispositivo 'disp1' não existe.", ex.getMessage());
	}

	@Test
	void testeDivisaoNaoExisteException() {
		DivisaoNaoExisteException ex = new DivisaoNaoExisteException("div1");
		assertEquals("Divisão 'div1' não existe.", ex.getMessage());
	}

	@Test
	void testeDivisaoNaoSelecionadaException() {
		DivisaoNaoSelecionadaException ex = new DivisaoNaoSelecionadaException();
		assertEquals("Nenhuma divisão selecionada.", ex.getMessage());
	}

	@Test
	void testeSessaoNaoIniciadaException() {
		SessaoNaoIniciadaException ex = new SessaoNaoIniciadaException();
		assertEquals("Sessão não iniciada. Faça login primeiro.", ex.getMessage());
	}

	@Test
	void testeUtilizadorJaExisteException() {
		UtilizadorJaExisteException ex = new UtilizadorJaExisteException("user1");
		assertEquals("Utilizador 'user1' já existe.", ex.getMessage());
	}

	@Test
	void testeUtilizadorNaoExisteException() {
		UtilizadorNaoExisteException ex = new UtilizadorNaoExisteException("user1");
		assertEquals("Utilizador 'user1' não existe.", ex.getMessage());
	}

	@Test
	void todasAsExceptionsSaoExceptions() {
		assertTrue(new CasaJaExisteException("x") instanceof Exception);
		assertTrue(new CasaNaoExisteException("x") instanceof Exception);
		assertTrue(new CasaNaoSelecionadaException() instanceof Exception);
		assertTrue(new CredenciaisInvalidasException() instanceof Exception);
		assertTrue(new DispositivoNaoExisteException("x") instanceof Exception);
		assertTrue(new DivisaoNaoExisteException("x") instanceof Exception);
		assertTrue(new DivisaoNaoSelecionadaException() instanceof Exception);
		assertTrue(new SessaoNaoIniciadaException() instanceof Exception);
		assertTrue(new UtilizadorJaExisteException("x") instanceof Exception);
		assertTrue(new UtilizadorNaoExisteException("x") instanceof Exception);
	}
}
