package domuscontrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import model.automatismos.Automacao;
import model.automatismos.AutomacaoChuva;
import model.automatismos.Escalonamento;
import model.casas.Casa;
import model.casas.Cenario;
import model.casas.Divisao;
import model.casas.EstrategiaCenario;
import model.dispositivos.Dispositivo;
import model.dispositivos.Lampada;
import model.dispositivos.SensorChuva;
import model.dispositivos.Dispositivo.Estado;
import model.utilizadores.Utilizador;

class CasaTest {

    private Casa casa;
    private Casa outraCasa;
    private Divisao divisao;
    private Lampada lampada;
    private Utilizador utilizador;
    private Cenario cenario;
    private Automacao automacao;
    private Escalonamento escalonamento;

    @BeforeEach
    void setUp() {
        casa = new Casa("casa1", "morada1", new HashMap<>(), new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        outraCasa = new Casa("casa2", "morada2", new HashMap<>(), new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        divisao = new Divisao("div1", new HashMap<>());
        lampada = new Lampada("lamp1", "Philips", "Hue", 0.9, Estado.LIGADO, 120, 10, 75);
        utilizador = new Utilizador("user1", "Nome", false, "pass");
        cenario = new Cenario("cenario1", false, new ArrayList<>(), new ArrayList<>());
        SensorChuva sensor = new SensorChuva("sensor1", "Marca", "Modelo", 0.5, Estado.LIGADO, 0, 0, 1.5, true);
        automacao = new AutomacaoChuva(sensor, new ArrayList<>());
        escalonamento = new Escalonamento("esc1", LocalDateTime.of(2026, 5, 1, 10, 30), new ArrayList<>(), d -> true, d -> {});
    }

    @Test
    void construtorVazio() {
        Casa casaVazia = new Casa();
        assertNotNull(casaVazia);
        assertEquals("", casaVazia.getId());
        assertEquals("", casaVazia.getMorada());
        assertTrue(casaVazia.getDivisoes().isEmpty());
        assertTrue(casaVazia.getUtilizadores().isEmpty());
        assertTrue(casaVazia.getCenarios().isEmpty());
        assertTrue(casaVazia.getAutomacoes().isEmpty());
        assertTrue(casaVazia.getEscalonamentos().isEmpty());
    }

    @Test
    void construtorCopia() {
        casa.adicionarDivisao(divisao);
        casa.adicionarUtilizador(utilizador);
        casa.adicionarCenario(cenario);
        casa.adicionarAutomacao(automacao);
        casa.adicionarEscalonamento(escalonamento);

        Casa copia = new Casa(casa);

        assertEquals(casa, copia);
        assertNotSame(casa, copia);
    }

    @Test
    void gettersESetters() {
        casa.setId("novaCasa");
        casa.setMorada("novaMorada");

        Map<String, Divisao> divisoes = new HashMap<>();
        divisoes.put(divisao.getId(), divisao);
        casa.setDivisoes(divisoes);

        Set<Utilizador> utilizadores = new HashSet<>();
        utilizadores.add(utilizador);
        casa.setUtilizadores(utilizadores);

        List<Cenario> cenarios = new ArrayList<>();
        cenarios.add(cenario);
        casa.setCenarios(cenarios);

        List<Automacao> automacoes = new ArrayList<>();
        automacoes.add(automacao);
        casa.setAutomacoes(automacoes);

        List<Escalonamento> escalonamentos = new ArrayList<>();
        escalonamentos.add(escalonamento);
        casa.setEscalonamentos(escalonamentos);

        assertEquals("novaCasa", casa.getId());
        assertEquals("novaMorada", casa.getMorada());
        assertEquals(1, casa.getDivisoes().size());
        assertEquals(1, casa.getUtilizadores().size());
        assertEquals(1, casa.getCenarios().size());
        assertEquals(1, casa.getAutomacoes().size());
        assertEquals(1, casa.getEscalonamentos().size());
    }

    @Test
    void toStringEEqualsEHashCode() {
        Casa casaIgual = new Casa("casa1", "morada1", new HashMap<>(), new HashSet<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        assertNotNull(casa.toString());
        assertEquals(casa, casaIgual);
        assertEquals(casa.hashCode(), casaIgual.hashCode());
    }

    @Test
    void calcularConsumoTotal() {
        Divisao d = new Divisao("div1", new HashMap<>());
        d.adicionarDispositivo(lampada);
        casa.adicionarDivisao(d);

        assertEquals((120 / 60.0) * 0.9, casa.calcularConsumoTotal(), 0.0001);
    }

    @Test
    void adicionarERemoverUtilizador() {
        casa.adicionarUtilizador(utilizador);
        assertEquals(1, casa.getUtilizadores().size());

        casa.removerUtilizador(utilizador);
        assertTrue(casa.getUtilizadores().isEmpty());
    }

    @Test
    void adicionarERemoverCenario() {
        casa.adicionarCenario(cenario);
        assertEquals(1, casa.getCenarios().size());

        casa.removerCenario(cenario);
        assertTrue(casa.getCenarios().isEmpty());
    }

    @Test
    void adicionarERemoverAutomacao() {
        casa.adicionarAutomacao(automacao);
        assertEquals(1, casa.getAutomacoes().size());

        casa.removerAutomacao(automacao);
        assertTrue(casa.getAutomacoes().isEmpty());
    }

    @Test
    void adicionarERemoverDivisao() {
        casa.adicionarDivisao(divisao);
        assertEquals(1, casa.getDivisoes().size());

        casa.removerDivisao(divisao.getId());
        assertTrue(casa.getDivisoes().isEmpty());
    }

    @Test
    void adicionarERemoverEscalonamento() {
        casa.adicionarEscalonamento(escalonamento);
        assertEquals(1, casa.getEscalonamentos().size());

        casa.removerEscalonamento(escalonamento);
        assertTrue(casa.getEscalonamentos().isEmpty());
    }

    @Test
    void dispositivosMaisUtilizados() {
        Divisao d1 = new Divisao("div1", new HashMap<>());
        Lampada lampada2 = new Lampada("lamp2", "Philips", "Hue", 1.0, Estado.DESLIGADO, 30, 20, 50);
        d1.adicionarDispositivo(lampada);
        d1.adicionarDispositivo(lampada2);
        casa.adicionarDivisao(d1);

        List<Dispositivo> ordenados = casa.dispositivosMaisUtilizados(Comparator.comparingInt(Dispositivo::getNumActivacoes));

        assertEquals(2, ordenados.size());
        assertEquals("lamp2", ordenados.get(0).getIdentificador());
    }

    @Test
    void getDivisoesComCasaEgetDivisaoById() {
        casa.adicionarDivisao(divisao);

        List<Map.Entry<Casa, Divisao>> lista = casa.getDivisoesComCasa();

        assertEquals(1, lista.size());
        assertSame(casa, lista.get(0).getKey());
        assertEquals(divisao.getId(), lista.get(0).getValue().getId());
        assertNotNull(casa.getDivisaoById(divisao.getId()));
        assertNull(casa.getDivisaoById("inexistente"));
    }

    @Test
    void removerPorId() {
        casa.adicionarAutomacao(automacao);
        casa.adicionarEscalonamento(escalonamento);
        casa.adicionarCenario(cenario);

        casa.removerAutomacaoPorId("AutomacaoChuva");
        casa.removerEscalonamentoPorId("esc1");
        casa.removerCenarioPorNome("cenario1");

        assertTrue(casa.getAutomacoes().isEmpty());
        assertTrue(casa.getEscalonamentos().isEmpty());
        assertTrue(casa.getCenarios().isEmpty());
    }

    @Test
    void ativarCenarioPorFiltro() {
        Cenario c = new Cenario("cenarioX", false, new ArrayList<>(), new ArrayList<>());
        AtomicBoolean executado = new AtomicBoolean(false);
        c.adicionarEstrategia(d -> true, d -> executado.set(true));
        casa.adicionarCenario(c);

        casa.ativarCenarioPorFiltro(cen -> cen.getNome().equals("cenarioX"));

        assertTrue(casa.getCenarios().get(0).isAtivo());
        assertTrue(executado.get());
    }

    @Test
    void executarFiltros() {
        AtomicBoolean automacaoExecutada = new AtomicBoolean(false);
        AtomicBoolean escalonamentoExecutado = new AtomicBoolean(false);

        SensorChuva sensor = new SensorChuva("sensor2", "Marca", "Modelo", 0.5, Estado.LIGADO, 0, 0, 1.0, true);
        Automacao auto = new AutomacaoChuva(sensor, new ArrayList<>()) {
            @Override
            public void executar() {
                automacaoExecutada.set(true);
            }
        };

        Escalonamento esc = new Escalonamento("esc2", LocalDateTime.of(2026, 5, 1, 11, 0), new ArrayList<>(), d -> true, d -> escalonamentoExecutado.set(true));

        casa.adicionarAutomacao(auto);
        casa.adicionarEscalonamento(esc);

        casa.executarAutomacoesPorFiltro(a -> true);
        casa.executarEscalonamentosPorFiltro(e -> true);

        assertTrue(automacaoExecutada.get());
        assertTrue(escalonamentoExecutado.get());
    }

    @Test
    void removerUtilizadorPorUsername() {
        casa.adicionarUtilizador(utilizador);
        casa.removerUtilizadorPorUsername("user1");

        assertTrue(casa.getUtilizadores().isEmpty());
    }
}