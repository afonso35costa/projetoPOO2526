package domuscontrol;

import model.automatismos.*;
import model.dispositivos.*;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class AutomatismosTest {

    // AutomacaoChuva tests
    @Test
    public void testAutomacaoChuvaVazio() {
        AutomacaoChuva automacao = new AutomacaoChuva();
        assertNull(automacao.getSensor());
        assertNotNull(automacao.getAtuadores());
        assertTrue(automacao.getAtuadores().isEmpty());
    }

    @Test
    public void testAutomacaoChuvaComArgumentos() {
        SensorChuva sensor = new SensorChuva();
        sensor.setPluviosidade(10);
        List<Dispositivo> atuadores = new ArrayList<>();
        Cortina cortina = new Cortina();
        atuadores.add(cortina);
        
        AutomacaoChuva automacao = new AutomacaoChuva(sensor, atuadores);
        assertEquals(sensor, automacao.getSensor());
        assertEquals(1, automacao.getAtuadores().size());
    }

    @Test
    public void testAutomacaoChuvaConstrutorCopia() {
        SensorChuva sensor = new SensorChuva();
        sensor.setPluviosidade(10);
        List<Dispositivo> atuadores = new ArrayList<>();
        atuadores.add(new Cortina());
        AutomacaoChuva automacao = new AutomacaoChuva(sensor, atuadores);
        
        AutomacaoChuva copia = new AutomacaoChuva(automacao);
        assertEquals(automacao.getSensor(), copia.getSensor());
        assertEquals(automacao.getAtuadores().size(), copia.getAtuadores().size());
        assertNotSame(automacao, copia);
    }

    @Test
    public void testAutomacaoChuvaSettersGetter() {
        AutomacaoChuva automacao = new AutomacaoChuva();
        SensorChuva sensor = new SensorChuva();
        sensor.setPluviosidade(20);
        List<Dispositivo> atuadores = new ArrayList<>();
        atuadores.add(new Cortina());
        
        automacao.setSensor(sensor);
        automacao.setAtuadores(atuadores);
        
        assertEquals(sensor, automacao.getSensor());
        assertEquals(1, automacao.getAtuadores().size());
    }

    @Test
    public void testAutomacaoChuvaVerificarRetornaFalseSeNullOuVazio() {
        AutomacaoChuva automacao = new AutomacaoChuva();
        assertFalse(automacao.verificar());
        
        automacao.setSensor(new SensorChuva());
        assertFalse(automacao.verificar());
    }

    @Test
    public void testAutomacaoChuvaVerificarRetornaEstadoChuva() {
        SensorChuva sensor = new SensorChuva();
        sensor.setPluviosidade(15);
        List<Dispositivo> atuadores = new ArrayList<>();
        atuadores.add(new Cortina());
        
        AutomacaoChuva automacao = new AutomacaoChuva(sensor, atuadores);
        assertTrue(automacao.verificar());
        
        SensorChuva sensorSemChuva = new SensorChuva();
        sensorSemChuva.setPluviosidade(0);
        automacao.setSensor(sensorSemChuva);
        assertFalse(automacao.verificar());
    }

    @Test
    public void testAutomacaoChuvaExecutarFechaCortinaAberta() {
        SensorChuva sensor = new SensorChuva();
        sensor.setPluviosidade(15);
        List<Dispositivo> atuadores = new ArrayList<>();
        Cortina cortina = new Cortina();
        cortina.abrir(100);
        atuadores.add(cortina);
        
        AutomacaoChuva automacao = new AutomacaoChuva(sensor, atuadores);
        automacao.executar();
        
        assertEquals(Dispositivo.Estado.FECHADO, cortina.getEstado());
        assertEquals(0, cortina.getAbertura());
    }

    @Test
    public void testAutomacaoChuvaClone() {
        SensorChuva sensor = new SensorChuva();
        sensor.setPluviosidade(15);
        List<Dispositivo> atuadores = new ArrayList<>();
        atuadores.add(new Cortina());
        
        AutomacaoChuva automacao = new AutomacaoChuva(sensor, atuadores);
        AutomacaoChuva clon = automacao.clone();
        
        assertEquals(automacao, clon);
        assertNotSame(automacao, clon);
    }

    // AutomacaoEscalonamentoAnimais tests
    @Test
    public void testAutomacaoAnimaisVazio() {
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais();
        assertNull(automacao.getDispensadorRacao());
    }

    @Test
    public void testAutomacaoAnimaisComArgumentos() {
        DispensadorRacao dispensador = new DispensadorRacao();
        dispensador.setNivelRacao(50);
        dispensador.setNivelAgua(50);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(dispensador);
        Predicate<Dispositivo> condicao = d -> d instanceof DispensadorRacao;
        LocalTime hora = LocalTime.of(8, 0);
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais("automacao", hora, dispositivos, condicao, d -> {((DispensadorRacao) d).encherAguaSeVazia(); ((DispensadorRacao) d).encherRacaoSeVazia();});
        assertEquals(dispensador, automacao.getDispensadorRacao());
    }

    @Test
    public void testAutomacaoAnimaisConstrutorCopia() {
        DispensadorRacao dispensador = new DispensadorRacao();
        dispensador.setNivelRacao(50);
        dispensador.setNivelAgua(50);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(dispensador);
        Predicate<Dispositivo> condicao = d -> d instanceof DispensadorRacao;
        LocalTime hora = LocalTime.of(8, 0);
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais("automacao1", hora, dispositivos, condicao, d -> {((DispensadorRacao) d).encherAguaSeVazia(); ((DispensadorRacao) d).encherRacaoSeVazia();});
        
        AutomacaoEscalonamentoAnimais copia = new AutomacaoEscalonamentoAnimais(automacao);
        assertEquals(automacao.getDispensadorRacao(), copia.getDispensadorRacao());
        assertNotSame(automacao, copia);
    }

    @Test
    public void testAutomacaoAnimaisVerificarRacaoVazia() {
        DispensadorRacao dispensador = new DispensadorRacao();
        dispensador.setNivelRacao(0);
        dispensador.setNivelAgua(50);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(dispensador);
        Predicate<Dispositivo> condicao = d -> d instanceof DispensadorRacao;
        LocalTime hora = LocalTime.of(8, 0);
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais("automacao2", hora, dispositivos, condicao, d -> {((DispensadorRacao) d).encherAguaSeVazia(); ((DispensadorRacao) d).encherRacaoSeVazia();});
        assertTrue(automacao.verificar());
    }

    @Test
    public void testAutomacaoAnimaisVerificarAguaVazia() {
        DispensadorRacao dispensador = new DispensadorRacao();
        dispensador.setNivelRacao(50);
        dispensador.setNivelAgua(0);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(dispensador);
        Predicate<Dispositivo> condicao = d -> d instanceof DispensadorRacao;
        LocalTime hora = LocalTime.of(8, 0);
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais("automacao3", hora, dispositivos, condicao, d -> {((DispensadorRacao) d).encherAguaSeVazia(); ((DispensadorRacao) d).encherRacaoSeVazia();});
        assertTrue(automacao.verificar());
    }

    @Test
    public void testAutomacaoAnimaisVerificarAmbosCheios() {
        DispensadorRacao dispensador = new DispensadorRacao();
        dispensador.setNivelRacao(50);
        dispensador.setNivelAgua(50);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(dispensador);
        Predicate<Dispositivo> condicao = d -> d instanceof DispensadorRacao;
        LocalTime hora = LocalTime.of(8, 0);
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais("automacao4", hora, dispositivos, condicao, d -> {((DispensadorRacao) d).encherAguaSeVazia(); ((DispensadorRacao) d).encherRacaoSeVazia();});
        assertFalse(automacao.verificar());
    }

    @Test
    public void testAutomacaoAnimaisExecutarEncheRacao() {
        DispensadorRacao dispensador = new DispensadorRacao();
        dispensador.setNivelRacao(0);
        dispensador.setNivelAgua(50);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(dispensador);
        Predicate<Dispositivo> condicao = d -> d instanceof DispensadorRacao;
        LocalTime hora = LocalTime.of(8, 0);
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais("automacao5", hora, dispositivos, condicao, d -> {((DispensadorRacao) d).encherAguaSeVazia(); ((DispensadorRacao) d).encherRacaoSeVazia();});
        automacao.executar();
        assertEquals(100, dispensador.getNivelRacao());
    }

    @Test
    public void testAutomacaoAnimaisExecutarEncheAgua() {
        DispensadorRacao dispensador = new DispensadorRacao();
        dispensador.setNivelRacao(50);
        dispensador.setNivelAgua(0);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(dispensador);
        Predicate<Dispositivo> condicao = d -> d instanceof DispensadorRacao;
        LocalTime hora = LocalTime.of(8, 0);
        AutomacaoEscalonamentoAnimais automacao = new AutomacaoEscalonamentoAnimais("automacao6", hora, dispositivos, condicao, d -> {((DispensadorRacao) d).encherAguaSeVazia(); ((DispensadorRacao) d).encherRacaoSeVazia();});
        automacao.executar();
        assertEquals(100, dispensador.getNivelAgua());
    }

    // AutomacaoLuzExterior tests
    @Test
    public void testAutomacaoLuzExteriorVazio() {
        AutomacaoLuzExterior automacao = new AutomacaoLuzExterior();
        assertNull(automacao.getSensorLuminosidade());
        assertNotNull(automacao.getLampadas());
        assertTrue(automacao.getLampadas().isEmpty());
    }

    @Test
    public void testAutomacaoLuzExteriorComArgumentos() {
        SensorLuminosidade sensor = new SensorLuminosidade();
        sensor.setLuminosidade(5);
        List<Lampada> lampadas = new ArrayList<>();
        lampadas.add(new Lampada());
        
        AutomacaoLuzExterior automacao = new AutomacaoLuzExterior(sensor, lampadas);
        assertEquals(sensor, automacao.getSensorLuminosidade());
        assertEquals(1, automacao.getLampadas().size());
    }

    @Test
    public void testAutomacaoLuzExteriorConstrutorCopia() {
        SensorLuminosidade sensor = new SensorLuminosidade();
        sensor.setLuminosidade(5);
        List<Lampada> lampadas = new ArrayList<>();
        lampadas.add(new Lampada());
        AutomacaoLuzExterior automacao = new AutomacaoLuzExterior(sensor, lampadas);
        
        AutomacaoLuzExterior copia = new AutomacaoLuzExterior(automacao);
        assertEquals(automacao.getSensorLuminosidade(), copia.getSensorLuminosidade());
        assertEquals(automacao.getLampadas().size(), copia.getLampadas().size());
        assertNotSame(automacao, copia);
    }

    @Test
    public void testAutomacaoLuzExteriorVerificarRetornaFalseSeNull() {
        AutomacaoLuzExterior automacao = new AutomacaoLuzExterior();
        assertFalse(automacao.verificar());
    }

    @Test
    public void testAutomacaoLuzExteriorVerificarRetornaDeveAcenderLuzes() {
        SensorLuminosidade sensorNoite = new SensorLuminosidade();
        sensorNoite.setLuminosidade(5);
        List<Lampada> lampadas = new ArrayList<>();
        lampadas.add(new Lampada());
        
        AutomacaoLuzExterior automacao = new AutomacaoLuzExterior(sensorNoite, lampadas);
        assertTrue(automacao.verificar());
        
        SensorLuminosidade sensorDia = new SensorLuminosidade();
        sensorDia.setLuminosidade(2000);
        automacao.setSensorLuminosidade(sensorDia);
        assertFalse(automacao.verificar());
    }

    @Test
    public void testAutomacaoLuzExteriorExecutarLigaLampadas() {
        SensorLuminosidade sensor = new SensorLuminosidade();
        sensor.setLuminosidade(5);
        List<Lampada> lampadas = new ArrayList<>();
        Lampada lamp = new Lampada();
        lamp.desligar();
        lampadas.add(lamp);
        
        AutomacaoLuzExterior automacao = new AutomacaoLuzExterior(sensor, lampadas);
        automacao.executar();
        
        assertEquals(Dispositivo.Estado.LIGADO, lamp.getEstado());
    }

    // AutomacaoRelva tests
    @Test
    public void testAutomacaoRelvaVazio() {
        AutomacaoRelva automacao = new AutomacaoRelva();
        assertNull(automacao.getIrrigacao());
        assertNull(automacao.getSensorChuva());
        assertNull(automacao.getSensorHumidade());
    }

    @Test
    public void testAutomacaoRelvaComArgumentos() {
        Irrigacao irrigacao = new Irrigacao();
        SensorChuva sensorChuva = new SensorChuva();
        SensorHumidadeSolo sensorHumidade = new SensorHumidadeSolo();
        
        AutomacaoRelva automacao = new AutomacaoRelva(irrigacao, sensorChuva, sensorHumidade);
        assertEquals(irrigacao, automacao.getIrrigacao());
        assertEquals(sensorChuva, automacao.getSensorChuva());
        assertEquals(sensorHumidade, automacao.getSensorHumidade());
    }

    @Test
    public void testAutomacaoRelvaConstrutorCopia() {
        Irrigacao irrigacao = new Irrigacao();
        SensorChuva sensorChuva = new SensorChuva();
        SensorHumidadeSolo sensorHumidade = new SensorHumidadeSolo();
        
        AutomacaoRelva automacao = new AutomacaoRelva(irrigacao, sensorChuva, sensorHumidade);
        AutomacaoRelva copia = new AutomacaoRelva(automacao);
        
        assertEquals(automacao.getIrrigacao(), copia.getIrrigacao());
        assertEquals(automacao.getSensorChuva(), copia.getSensorChuva());
        assertEquals(automacao.getSensorHumidade(), copia.getSensorHumidade());
        assertNotSame(automacao, copia);
    }

    @Test
    public void testAutomacaoRelvaVerificarSemChuvaEComSoloSeco() {
        Irrigacao irrigacao = new Irrigacao();
        SensorChuva sensorChuva = new SensorChuva();
        sensorChuva.setPluviosidade(0);
        SensorHumidadeSolo sensorHumidade = new SensorHumidadeSolo();
        sensorHumidade.setHumidade(0);
        
        AutomacaoRelva automacao = new AutomacaoRelva(irrigacao, sensorChuva, sensorHumidade);
        assertTrue(automacao.verificar());
    }

    @Test
    public void testAutomacaoRelvaVerificarComChuva() {
        Irrigacao irrigacao = new Irrigacao();
        SensorChuva sensorChuva = new SensorChuva();
        sensorChuva.setPluviosidade(50);
        SensorHumidadeSolo sensorHumidade = new SensorHumidadeSolo();
        sensorHumidade.setHumidade(30);
        
        AutomacaoRelva automacao = new AutomacaoRelva(irrigacao, sensorChuva, sensorHumidade);
        assertFalse(automacao.verificar());
    }

    @Test
    public void testAutomacaoRelvaExecutarLigaIrrigacao() {
        Irrigacao irrigacao = new Irrigacao();
        SensorChuva sensorChuva = new SensorChuva();
        sensorChuva.setPluviosidade(0);
        SensorHumidadeSolo sensorHumidade = new SensorHumidadeSolo();
        sensorHumidade.setHumidade(0);
        
        AutomacaoRelva automacao = new AutomacaoRelva(irrigacao, sensorChuva, sensorHumidade);
        automacao.executar();
        
        assertEquals(Dispositivo.Estado.LIGADO, irrigacao.getEstado());
        assertEquals(100, sensorHumidade.getHumidade());
    }

    // AutomacaoTemperatura tests
    @Test
    public void testAutomacaoTemperaturaVazio() {
        AutomacaoTemperatura automacao = new AutomacaoTemperatura();
        assertNull(automacao.getTermostato());
    }

    @Test
    public void testAutomacaoTemperaturaComArgumentos() {
        Termostato termostato = new Termostato();
        termostato.setTemperaturaAtual(20);
        termostato.setTemperaturaAlvo(22);
        AutomacaoTemperatura automacao = new AutomacaoTemperatura(termostato);
        assertEquals(termostato, automacao.getTermostato());
    }

    @Test
    public void testAutomacaoTemperaturaConstrutorCopia() {
        Termostato termostato = new Termostato();
        termostato.setTemperaturaAtual(20);
        termostato.setTemperaturaAlvo(22);
        AutomacaoTemperatura automacao = new AutomacaoTemperatura(termostato);
        
        AutomacaoTemperatura copia = new AutomacaoTemperatura(automacao);
        assertEquals(automacao.getTermostato(), copia.getTermostato());
        assertNotSame(automacao, copia);
    }

    @Test
    public void testAutomacaoTemperaturaSetTermostato() {
        AutomacaoTemperatura automacao = new AutomacaoTemperatura();
        Termostato termostato = new Termostato();
        termostato.setTemperaturaAtual(20);
        termostato.setTemperaturaAlvo(22);
        
        automacao.setTermostato(termostato);
        assertEquals(termostato, automacao.getTermostato());
    }

    @Test
    public void testAutomacaoTemperaturaClone() {
        Termostato termostato = new Termostato();
        termostato.setTemperaturaAtual(20);
        termostato.setTemperaturaAlvo(22);
        AutomacaoTemperatura automacao = new AutomacaoTemperatura(termostato);
        
        AutomacaoTemperatura clon = automacao.clone();
        assertEquals(automacao, clon);
        assertNotSame(automacao, clon);
    }

    // Escalonamento tests
    @Test
    public void testEscalonamentoVazio() {
        Escalonamento escalonamento = new Escalonamento();
        assertEquals("", escalonamento.getId());
        assertNotNull(escalonamento.getHora());
        assertNotNull(escalonamento.getDispositivos());
        assertTrue(escalonamento.getDispositivos().isEmpty());
        assertNotNull(escalonamento.getCondicao());
        assertNotNull(escalonamento.getAcao());
    }

    @Test
    public void testEscalonamentoComArgumentos() {
        LocalTime hora = LocalTime.of(14, 30);
        List<Dispositivo> dispositivos = new ArrayList<>();
        Lampada lampada = new Lampada();
        dispositivos.add(lampada);
        
        Escalonamento escalonamento = new Escalonamento(
            "esc1", 
            hora, 
            dispositivos, 
            d -> d instanceof Lampada, 
            d -> ((Lampada) d).ligar()
        );
        
        assertEquals("esc1", escalonamento.getId());
        assertEquals(hora, escalonamento.getHora());
        assertEquals(1, escalonamento.getDispositivos().size());
    }

    @Test
    public void testEscalonamentoConstrutorCopia() {
        LocalTime hora = LocalTime.of(14, 30);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(new Lampada());
        
        Escalonamento escalonamento = new Escalonamento(
            "esc1",
            hora,
            dispositivos,
            d -> true,
            d -> {}
        );
        
        Escalonamento copia = new Escalonamento(escalonamento);
        assertEquals(escalonamento.getId(), copia.getId());
        assertEquals(escalonamento.getHora(), copia.getHora());
        assertEquals(escalonamento.getDispositivos().size(), copia.getDispositivos().size());
        assertNotSame(escalonamento, copia);
    }

    @Test
    public void testEscalonamentoSetId() {
        Escalonamento escalonamento = new Escalonamento();
        escalonamento.setId("novo_id");
        assertEquals("novo_id", escalonamento.getId());
    }

    @Test
    public void testEscalonamentoSetHora() {
        Escalonamento escalonamento = new Escalonamento();
        LocalTime novaHora = LocalTime.of(10, 0);
        escalonamento.setHora(novaHora);
        assertEquals(novaHora, escalonamento.getHora());
    }

    @Test
    public void testEscalonamentoSetDispositivos() {
        Escalonamento escalonamento = new Escalonamento();
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(new Lampada());
        
        escalonamento.setDispositivos(dispositivos);
        assertEquals(1, escalonamento.getDispositivos().size());
    }

    @Test
    public void testEscalonamentoVerificaHoraIgual() {
        LocalTime hora = LocalTime.of(14, 30);
        Escalonamento escalonamento = new Escalonamento(
            "esc1",
            hora,
            new ArrayList<>(),
            d -> true,
            d -> {}
        );

        LocalTime agora = LocalTime.of(14, 30);
        assertTrue(escalonamento.verificaHora(agora));
    }

    @Test
    public void testEscalonamentoVerificaHoraDiferente() {
        LocalTime hora = LocalTime.of(14, 30);
        Escalonamento escalonamento = new Escalonamento(
            "esc1",
            hora,
            new ArrayList<>(),
            d -> true,
            d -> {}
        );

        LocalTime agora = LocalTime.of(15, 30);
        assertFalse(escalonamento.verificaHora(agora));
    }

    @Test
    public void testEscalonamentoExecutarComCondicao() {
        LocalTime hora = LocalTime.of(14, 30);
        List<Dispositivo> dispositivos = new ArrayList<>();
        Lampada lampada = new Lampada();
        lampada.desligar();
        dispositivos.add(lampada);
        
        AtomicBoolean foiExecutada = new AtomicBoolean(false);
        
        Escalonamento escalonamento = new Escalonamento(
            "esc1",
            hora,
            dispositivos,
            d -> d instanceof Lampada,
            d -> {
                ((Lampada) d).ligar();
                foiExecutada.set(true);
            }
        );
        
        escalonamento.executar();
        assertTrue(foiExecutada.get());
        assertEquals(Dispositivo.Estado.LIGADO, lampada.getEstado());
    }

    @Test
    public void testEscalonamentoClone() {
        LocalTime hora = LocalTime.of(14, 30);
        List<Dispositivo> dispositivos = new ArrayList<>();
        dispositivos.add(new Lampada());
        
        Escalonamento escalonamento = new Escalonamento(
            "esc1",
            hora,
            dispositivos,
            d -> true,
            d -> {}
        );
        
        Escalonamento clon = escalonamento.clone();
        assertEquals(escalonamento, clon);
        assertNotSame(escalonamento, clon);
    }

    // AutomacaoFactory tests
    @Test
    public void testFactoryCriarAutomacaoChuva() {
        Automacao automacao = AutomacaoFactory.criarAutomacao("chuva");
        assertNotNull(automacao);
        assertTrue(automacao instanceof AutomacaoChuva);
    }

    @Test
    public void testFactoryCriarAutomacaoAnimais() {
        Automacao automacao = AutomacaoFactory.criarAutomacao("animais");
        assertNotNull(automacao);
        assertTrue(automacao instanceof AutomacaoEscalonamentoAnimais);
    }

    @Test
    public void testFactoryCriarAutomacaoLuzExterior() {
        Automacao automacao = AutomacaoFactory.criarAutomacao("luz_exterior");
        assertNotNull(automacao);
        assertTrue(automacao instanceof AutomacaoLuzExterior);
    }

    @Test
    public void testFactoryCriarAutomacaoRelva() {
        Automacao automacao = AutomacaoFactory.criarAutomacao("relva");
        assertNotNull(automacao);
        assertTrue(automacao instanceof AutomacaoRelva);
    }

    @Test
    public void testFactoryCriarAutomacaoTemperatura() {
        Automacao automacao = AutomacaoFactory.criarAutomacao("temperatura");
        assertNotNull(automacao);
        assertTrue(automacao instanceof AutomacaoTemperatura);
    }

    @Test
    public void testFactoryCriarAutomacaoDesconhecida() {
        assertThrows(IllegalArgumentException.class, () -> {
            AutomacaoFactory.criarAutomacao("desconhecida");
        });
    }

    @Test
    public void testFactoryCaseInsensitive() {
        Automacao automacao1 = AutomacaoFactory.criarAutomacao("CHUVA");
        Automacao automacao2 = AutomacaoFactory.criarAutomacao("Chuva");
        
        assertTrue(automacao1 instanceof AutomacaoChuva);
        assertTrue(automacao2 instanceof AutomacaoChuva);
    }

}
