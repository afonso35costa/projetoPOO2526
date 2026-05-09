package domuscontrol;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import exceptions.CasaJaExisteException;
import exceptions.CasaNaoExisteException;
import exceptions.DispositivoNaoExisteException;
import exceptions.DivisaoNaoExisteException;
import exceptions.UtilizadorJaExisteException;
import exceptions.UtilizadorNaoExisteException;
import exceptions.AutomacaoJaExisteException;
import exceptions.AutomacaoNaoExisteException;
import exceptions.EscalonamentoJaExisteException;
import exceptions.EscalonamentoNaoExisteException;
import exceptions.CenarioJaExisteException;
import exceptions.CenarioNaoExisteException;
import model.casas.Casa;
import model.casas.Divisao;
import model.casas.Cenario;
import model.automatismos.Automacao;
import model.automatismos.Escalonamento;
import model.automatismos.AutomacaoChuva;
import model.dispositivos.Lampada;
import model.dispositivos.SensorChuva;
import model.dispositivos.Dispositivo;
import model.dispositivos.Dispositivo.Estado;
import model.domus.DomusControl;
import model.utilizadores.Utilizador;

class DomusControlTest {

    private DomusControl domusControl;
    private Utilizador utilizador;
    private Utilizador utilizador2;
    private Casa casa;
    private Casa casa2;
    private Divisao divisao;
    private Lampada dispositivo;

    @BeforeEach
    public void setUp() {
        domusControl = new DomusControl();
        utilizador = new Utilizador("user1", "nome1", "password");
        utilizador2 = new Utilizador("user2", "nome2", "pass2");
        casa = new Casa("casaId", "morada1", new HashMap<>(), new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        casa2 = new Casa("casaId2", "morada2", new HashMap<>(), new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashSet<>());
        divisao = new Divisao("divisaoId", new HashMap<>());
        dispositivo = new Lampada("lampId", "Philips", "Hue", 0.9, Estado.DESLIGADO, 0, 5, 50);
    }

    // ===== Testes para Construtores =====
    
    @Test
    public void testConstrutorVazio() {
        DomusControl novoSistema = new DomusControl();
        assertNotNull(novoSistema);
        assertTrue(novoSistema.getCasas().isEmpty());
        assertTrue(novoSistema.getUtilizadores().isEmpty());
    }

    @Test
    public void testConstrutorComMapas() {
        Map<String, Casa> casas = new HashMap<>();
        casas.put(casa.getId(), casa);
        Map<String, Utilizador> utilizadores = new HashMap<>();
        utilizadores.put(utilizador.getUsername(), utilizador);
        
        DomusControl sistema = new DomusControl(casas, utilizadores);
        
        assertNotNull(sistema);
        assertEquals(1, sistema.getCasas().size());
        assertEquals(1, sistema.getUtilizadores().size());
    }

    @Test
    public void testConstrutorCopia() {
        Map<String, Casa> casas = new HashMap<>();
        casas.put(casa.getId(), casa);
        Map<String, Utilizador> utilizadores = new HashMap<>();
        utilizadores.put(utilizador.getUsername(), utilizador);
        
        DomusControl sistema1 = new DomusControl(casas, utilizadores);
        DomusControl sistema2 = new DomusControl(sistema1);
        
        assertEquals(sistema1.getCasas().size(), sistema2.getCasas().size());
        assertEquals(sistema1.getUtilizadores().size(), sistema2.getUtilizadores().size());
    }

    // ===== Testes para Getters e Setters =====

    @Test
    public void testGetCasas() {
        Map<String, Casa> casas = new HashMap<>();
        casas.put(casa.getId(), casa);
        Map<String, Utilizador> utilizadores = new HashMap<>();
        DomusControl sistema = new DomusControl(casas, utilizadores);
        
        Map<String, Casa> casasRetrieved = sistema.getCasas();
        assertEquals(1, casasRetrieved.size());
        assertTrue(casasRetrieved.containsKey(casa.getId()));
    }

    @Test
    public void testSetCasas() {
        Map<String, Casa> casas = new HashMap<>();
        casas.put(casa.getId(), casa);
        casas.put(casa2.getId(), casa2);
        
        domusControl.setCasas(casas);
        
        assertEquals(2, domusControl.getCasas().size());
    }

    @Test
    public void testGetUtilizadores() {
        domusControl.getUtilizadores();
        assert domusControl.getUtilizadores() instanceof Map;
    }

    @Test
    public void testSetUtilizadores() {
        Map<String, Utilizador> utilizadores = new HashMap<>();
        utilizadores.put(utilizador.getUsername(), utilizador);
        utilizadores.put(utilizador2.getUsername(), utilizador2);
        
        domusControl.setUtilizadores(utilizadores);
        
        assertEquals(2, domusControl.getUtilizadores().size());
    }

    // ===== Testes para Clone =====

    @Test
    public void testClone() throws UtilizadorJaExisteException, CasaJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        DomusControl clone = domusControl.clone();
        
        assertEquals(domusControl.getCasas().size(), clone.getCasas().size());
        assertEquals(domusControl.getUtilizadores().size(), clone.getUtilizadores().size());
    }

    // ===== Testes para toString e equals =====

    @Test
    public void testToString() {
        String resultado = domusControl.toString();
        assertNotNull(resultado);
        assertTrue(resultado.contains("Sistema"));
    }

    @Test
    public void testEquals() {
        DomusControl sistema1 = new DomusControl();
        DomusControl sistema2 = new DomusControl();
        
        assertEquals(sistema1, sistema2);
    }

    @Test
    public void testEqualsComDados() throws UtilizadorJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        
        DomusControl sistema2 = new DomusControl();
        sistema2.adicionarUtilizador(utilizador);
        
        assertEquals(domusControl, sistema2);
    }

    @Test
    public void testHashCode() {
        DomusControl sistema1 = new DomusControl();
        DomusControl sistema2 = new DomusControl();
        
        assertEquals(sistema1.hashCode(), sistema2.hashCode());
    }

    // ===== Testes para Gestão de Casas =====

    @Test
    public void testAdicionarCasa() throws UtilizadorJaExisteException, CasaJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        assertTrue(domusControl.casaExiste(casa.getId()));
        assertEquals(1, domusControl.getCasas().size());
    }

    @Test
    public void testAdicionarCasaDuplicada() throws UtilizadorJaExisteException, CasaJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        assertThrows(CasaJaExisteException.class, () -> {
            domusControl.adicionarCasa(casa, utilizador.getUsername());
        });
    }

    @Test
    public void testRemoverCasa() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        domusControl.removerCasa(casa.getId());
        
        assertFalse(domusControl.casaExiste(casa.getId()));
    }

    @Test
    public void testRemoverCasaNaoExistente() {
        assertThrows(CasaNaoExisteException.class, () -> {
            domusControl.removerCasa("naoExiste");
        });
    }

    @Test
    public void testCasaExiste() throws UtilizadorJaExisteException, CasaJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        assertTrue(domusControl.casaExiste(casa.getId()));
        assertFalse(domusControl.casaExiste("naoExiste"));
    }

    // ===== Testes para Gestão de Utilizadores =====

    @Test
    public void testAdicionarUtilizador() throws UtilizadorJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        
        assertEquals(1, domusControl.getUtilizadores().size());
    }

    @Test
    public void testAdicionarUtilizadorDuplicado() throws UtilizadorJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        
        assertThrows(UtilizadorJaExisteException.class, () -> {
            domusControl.adicionarUtilizador(utilizador);
        });
    }

    @Test
    public void testRemoverUtilizador() throws UtilizadorJaExisteException, CasaNaoExisteException, UtilizadorNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.removerUtilizador(utilizador.getUsername());
        
        assertEquals(0, domusControl.getUtilizadores().size());
    }

    @Test
    public void testExisteUtilizador() throws UtilizadorJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        
        assertTrue(domusControl.existeUtilizador("user1", "password"));
        assertFalse(domusControl.existeUtilizador("user1", "senhaErrada"));
        assertFalse(domusControl.existeUtilizador("naoExiste", "password"));
    }

    // ===== Testes para Gestão de Divisões =====

    @Test
    public void testAdicionarDivisao() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        
        assertTrue(domusControl.divisaoExiste(casa.getId(), divisao.getId()));
    }

    @Test
    public void testAdicionarDivisaoEmCasaNaoExistente() {
        assertThrows(CasaNaoExisteException.class, () -> {
            domusControl.adicionarDivisao("casaNaoExiste", divisao);
        });
    }

    @Test
    public void testRemoverDivisao() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        
        domusControl.removerDivisao(casa.getId(), divisao.getId());
        
        assertFalse(domusControl.divisaoExiste(casa.getId(), divisao.getId()));
    }

    @Test
    public void testRemoverDivisaoNaoExistente() {
        assertThrows(CasaNaoExisteException.class, () -> {
            domusControl.removerDivisao("casaNaoExiste", "divisaoNaoExiste");
        });
    }

    @Test
    public void testDivisaoExiste() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        
        assertTrue(domusControl.divisaoExiste(casa.getId(), divisao.getId()));
        assertFalse(domusControl.divisaoExiste(casa.getId(), "divisaoNaoExiste"));
    }

    // ===== Testes para Gestão de Dispositivos =====

    @Test
    public void testAdicionarDispositivo() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), dispositivo);
        
        assertTrue(domusControl.dispositivoExiste(casa.getId(), divisao.getId(), dispositivo.getIdentificador()));
    }

    @Test
    public void testAdicionarDispositivoEmCasaNaoExistente() {
        assertThrows(CasaNaoExisteException.class, () -> {
            domusControl.adicionarDispositivo("casaNaoExiste", "divisaoId", dispositivo);
        });
    }

    @Test
    public void testAdicionarDispositivoEmDivisaoNaoExistente() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        assertThrows(DivisaoNaoExisteException.class, () -> {
            domusControl.adicionarDispositivo(casa.getId(), "divisaoNaoExiste", dispositivo);
        });
    }

    @Test
    public void testRemoverDispositivo() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException, DispositivoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), dispositivo);
        
        domusControl.removerDispositivo(casa.getId(), divisao.getId(), dispositivo.getIdentificador());
        
        assertFalse(domusControl.dispositivoExiste(casa.getId(), divisao.getId(), dispositivo.getIdentificador()));
    }

    @Test
    public void testRemoverDispositivoNaoExistente() {
        assertThrows(CasaNaoExisteException.class, () -> {
            domusControl.removerDispositivo("casaNaoExiste", "divisaoId", "dispId");
        });
    }

    @Test
    public void testDispositivoExiste() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), dispositivo);
        
        assertTrue(domusControl.dispositivoExiste(casa.getId(), divisao.getId(), dispositivo.getIdentificador()));
    }

    // ===== Testes para Análises =====

    @Test
    public void testCasaQueMaisConsome() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());

        Casa resultados = domusControl.casaQueMaisConsome(utilizador.getUsername());
        assertNotNull(resultados);
    }

    @Test
    public void testCasaQueMaisConsomeVazio() {
        Casa resultado = domusControl.casaQueMaisConsome("");
        assertNull(resultado);
    }

    @Test
    public void testTresDispositivosMaisUtilizados() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        
        Lampada lamp1 = new Lampada("lamp1", "Philips", "Hue", 0.9, Estado.LIGADO, 100, 10, 75);
        Lampada lamp2 = new Lampada("lamp2", "Philips", "Hue", 0.9, Estado.LIGADO, 50, 5, 50);
        
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), lamp1);
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), lamp2);
        
        Comparator<Dispositivo> comparator = Comparator.comparingInt(d -> d.getNumActivacoes());
        List<Dispositivo> resultado = domusControl.tresDispositivosMaisUtilizados(casa, comparator);
        
        assertNotNull(resultado);
    }

    @Test
    public void testTresDivisoesComMaisDispositivos() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), dispositivo);
        
        List<Map.Entry<Casa, Divisao>> resultado = domusControl.tresDivisoesComMaisDispositivos(utilizador.getUsername());
        
        assertNotNull(resultado);
    }

    // ===== Testes para Automatismos (Cenários, Automações, Escalonamentos) =====

    @Test
    public void testRemoverAutomacao() throws UtilizadorJaExisteException, CasaJaExisteException, AutomacaoJaExisteException, CasaNaoExisteException, AutomacaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        SensorChuva sensor = new SensorChuva("sensor1", "marca", "modelo", 0.5, Estado.LIGADO, 0, 0, 2.5, true);
        Automacao automacao = new AutomacaoChuva(sensor, new ArrayList<>());
        domusControl.adicionarAutomacao(casa.getId(), automacao);
        domusControl.removerAutomacao(casa.getId(), "automacaoChuva");
        
        assertEquals(0, domusControl.getCasas().get(casa.getId()).getAutomacoes().size());
    }

    @Test
    public void testAdicionarEscalonamento() throws UtilizadorJaExisteException, CasaJaExisteException, EscalonamentoJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());

        Escalonamento escalonamento = new Escalonamento("escId", LocalTime.of(10, 30), new ArrayList<>(), d -> true, d -> {});
        domusControl.adicionarEscalonamento(casa.getId(), escalonamento);
        
        assertEquals(1, domusControl.getCasas().get(casa.getId()).getEscalonamentos().size());
    }

    @Test
    public void testRemoverEscalonamento() throws UtilizadorJaExisteException, CasaJaExisteException, EscalonamentoJaExisteException, CasaNaoExisteException, EscalonamentoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());

        Escalonamento escalonamento = new Escalonamento("escId", LocalTime.of(10, 30), new ArrayList<>(), d -> true, d -> {});
        domusControl.adicionarEscalonamento(casa.getId(), escalonamento);
        domusControl.removerEscalonamento(casa.getId(), "escId");
        
        assertEquals(0, domusControl.getCasas().get(casa.getId()).getEscalonamentos().size());
    }

    @Test
    public void testAdicionarCenario() throws UtilizadorJaExisteException, CasaJaExisteException, CenarioJaExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        Cenario cenario = new Cenario("cenarioId", true, new ArrayList<>(), new ArrayList<>());
        domusControl.adicionarCenario(casa.getId(), cenario);
        
        assertEquals(1, domusControl.getCasas().get(casa.getId()).getCenarios().size());
    }

    @Test
    public void testRemoverCenario() throws UtilizadorJaExisteException, CasaJaExisteException, CenarioJaExisteException, CasaNaoExisteException, CenarioNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        Cenario cenario = new Cenario("cenarioId", true, new ArrayList<>(), new ArrayList<>());
        domusControl.adicionarCenario(casa.getId(), cenario);
        domusControl.removerCenario(casa.getId(), "cenarioId");
        
        assertEquals(0, domusControl.getCasas().get(casa.getId()).getCenarios().size());
    }

    @Test
    public void testAtivarCenario() throws UtilizadorJaExisteException, CasaJaExisteException, CenarioJaExisteException, CasaNaoExisteException, CenarioNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        Cenario cenario = new Cenario("cenarioId", true, new ArrayList<>(), new ArrayList<>());
        domusControl.adicionarCenario(casa.getId(), cenario);
        
        domusControl.ativarCenario(casa.getId(), c -> c.getNome().equals("cenarioId"));
        
        assertNotNull(domusControl.getCasas().get(casa.getId()).getCenarios());
    }

    @Test
    public void testExecutarAutomacoes() throws UtilizadorJaExisteException, CasaJaExisteException, AutomacaoJaExisteException, CasaNaoExisteException, AutomacaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        SensorChuva sensor = new SensorChuva("sensor1", "marca", "modelo", 0.5, Estado.LIGADO, 0, 0, 2.5, true);
        Automacao automacao = new AutomacaoChuva(sensor, new ArrayList<>());
        domusControl.adicionarAutomacao(casa.getId(), automacao);
        
        domusControl.executarAutomacoes(casa.getId(), a -> automacao.equals(a));
        
        assertNotNull(domusControl.getCasas().get(casa.getId()).getAutomacoes());
    }

    @Test
    public void testExecutarEscalonamentos() throws UtilizadorJaExisteException, CasaJaExisteException, EscalonamentoJaExisteException, CasaNaoExisteException, EscalonamentoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());

        Escalonamento escalonamento = new Escalonamento("escId", LocalTime.of(10, 30), new ArrayList<>(), d -> true, d -> {});
        domusControl.adicionarEscalonamento(casa.getId(), escalonamento);
        
        domusControl.executarEscalonamentos(casa.getId(), e -> e.getId().equals("escId"));
        
        assertNotNull(domusControl.getCasas().get(casa.getId()).getEscalonamentos());
    }

    // ===== Testes para Filtragem de Dispositivos =====

    @Test
    public void testPercorreDivisoesEfiltraDispositivos() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), dispositivo);
        
        List<Dispositivo> resultado = domusControl.percorreDivisoesEfiltraDispositivos(casa.getId(), null, "Lampada");
        
        assertNotNull(resultado);
    }

    @Test
    public void testPercorreDivisoesEfiltraDispositivosComDivisoes() throws UtilizadorJaExisteException, CasaJaExisteException, CasaNaoExisteException, DivisaoNaoExisteException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        domusControl.adicionarDivisao(casa.getId(), divisao);
        domusControl.adicionarDispositivo(casa.getId(), divisao.getId(), dispositivo);
        
        List<String> divisoesIds = new ArrayList<>();
        divisoesIds.add(divisao.getId());
        
        List<Dispositivo> resultado = domusControl.percorreDivisoesEfiltraDispositivos(casa.getId(), divisoesIds, "Lampada");
        
        assertNotNull(resultado);
    }

    // ===== Testes para Persistência (Serialização) =====

    @Test
    public void testWriteToFile() throws UtilizadorJaExisteException, CasaJaExisteException, IOException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        String filename = "test_domus.dat";
        domusControl.guardarEstado(filename);
        
        File file = new File(filename);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testReadFromFile() throws UtilizadorJaExisteException, CasaJaExisteException, IOException, ClassNotFoundException {
        domusControl.adicionarUtilizador(utilizador);
        domusControl.adicionarCasa(casa, utilizador.getUsername());
        
        String filename = "test_domus_read.dat";
        domusControl.guardarEstado(filename);
        
        DomusControl domusLido = domusControl.carregarEstado(filename);
        
        assertNotNull(domusLido);
        assertEquals(domusControl.getCasas().size(), domusLido.getCasas().size());
        assertEquals(domusControl.getUtilizadores().size(), domusLido.getUtilizadores().size());
        
        File file = new File(filename);
        file.delete();
    }
}
