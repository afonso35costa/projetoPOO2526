package domuscontrol;

import model.utilizadores.Utilizador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UtilizadoresTest {
    
    private Utilizador utilizador;
    private Utilizador utilizador2;

    @BeforeEach
    public void setUp() {
        utilizador = new Utilizador("joao", "João Silva", "senha123");
        utilizador2 = new Utilizador("maria", "Maria Santos", "senha456");
    }

    @Test
    public void testConstrutorVazio() {
        Utilizador u = new Utilizador();
        assertAll(
            () -> assertEquals("", u.getUsername()),
            () -> assertEquals("", u.getNome()),
            () -> assertEquals("", u.getPassword())
        );
    }

    @Test
    public void testConstrutorComArgumentos() {
        assertAll(
            () -> assertEquals("joao", utilizador.getUsername()),
            () -> assertEquals("João Silva", utilizador.getNome()),
            () -> assertEquals("senha123", utilizador.getPassword())
        );
    }

    @Test
    public void testConstrutorCopia() {
        Utilizador copia = new Utilizador(utilizador);
        assertAll(
            () -> assertEquals(utilizador.getUsername(), copia.getUsername()),
            () -> assertEquals(utilizador.getNome(), copia.getNome()),
            () -> assertEquals(utilizador.getPassword(), copia.getPassword()),
            () -> assertNotSame(utilizador, copia)
        );
    }

    @Test
    public void testGettersRetornamValoresCorretos() {
        assertAll(
            () -> assertEquals("joao", utilizador.getUsername()),
            () -> assertEquals("João Silva", utilizador.getNome()),
            () -> assertEquals("senha123", utilizador.getPassword())
        );
    }

    @Test
    public void testSetUsername() {
        utilizador.setUsername("novoUsername");
        assertEquals("novoUsername", utilizador.getUsername());
        assertEquals("João Silva", utilizador.getNome());
    }

    @Test
    public void testSetNome() {
        utilizador.setNome("Novo Nome");
        assertEquals("joao", utilizador.getUsername());
        assertEquals("Novo Nome", utilizador.getNome());
    }

    @Test
    public void testSetPassword() {
        utilizador.setPassword("novaSenha");
        assertEquals("novaSenha", utilizador.getPassword());
        assertEquals("joao", utilizador.getUsername());
    }

    @Test
    public void testCloneFunciona() {
        Utilizador clon = utilizador.clone();
        assertAll(
            () -> assertEquals(utilizador.getUsername(), clon.getUsername()),
            () -> assertEquals(utilizador.getNome(), clon.getNome()),
            () -> assertEquals(utilizador.getPassword(), clon.getPassword()),
            () -> assertNotSame(utilizador, clon)
        );
    }

    @Test
    public void testToString() {
        String expected = "Utilizador{username='joao', nome='João Silva', password='senha123'}";
        assertEquals(expected, utilizador.toString());
    }

    @Test
    public void testEqualsComMesmoObjeto() {
        assertEquals(utilizador, utilizador);
    }

    @Test
    public void testEqualsComObjetoIgual() {
        Utilizador outro = new Utilizador("joao", "João Silva", "senha123");
        assertEquals(utilizador, outro);
        assertEquals(outro, utilizador);
    }

    @Test
    public void testEqualsComObjetosDiferentes() {
        assertNotEquals(utilizador, utilizador2);
        assertNotEquals(utilizador2, utilizador);
    }

    @Test
    public void testEqualsComNull() {
        assertNotEquals(utilizador, null);
    }

    @Test
    public void testEqualsComOutroTipo() {
        assertNotEquals(utilizador, "joao");
    }

    @Test
    public void testEqualsApenasUsernameIgual() {
        Utilizador outro = new Utilizador("joao", "Outro Nome", "senha123");
        assertNotEquals(utilizador, outro);
    }

    @Test
    public void testEqualsApenasNomeIgual() {
        Utilizador outro = new Utilizador("outro", "João Silva", "senha123");
        assertNotEquals(utilizador, outro);
    }

    @Test
    public void testEqualsApenasPasswordIgual() {
        Utilizador outro = new Utilizador("joao", "João Silva", "outraSenha");
        assertNotEquals(utilizador, outro);
    }

    @Test
    public void testHashCodeIguaisParaObjetosIguais() {
        Utilizador outro = new Utilizador("joao", "João Silva", "senha123");
        assertEquals(utilizador.hashCode(), outro.hashCode());
    }

    @Test
    public void testHashCodeDiferenteParaObjetosDiferentes() {
        assertNotEquals(utilizador.hashCode(), utilizador2.hashCode());
    }

}
