package domuscontrol;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import model.dispositivos.Dispositivo;
import model.dispositivos.Dispositivo.Estado;
import model.dispositivos.ColunaDeSom;
import model.dispositivos.Cortina;
import model.dispositivos.DispensadorRacao;
import model.dispositivos.DispositivoFactory;
import model.dispositivos.Irrigacao;
import model.dispositivos.Lampada;
import model.dispositivos.LampadaCor;
import model.dispositivos.PortaoGaragem;
import model.dispositivos.Rele;
import model.dispositivos.SensorChuva;
import model.dispositivos.SensorHumidadeSolo;
import model.dispositivos.SensorLuminosidade;
import model.dispositivos.Televisao;
import model.dispositivos.Termostato;

class DispositivoTest {

    private Lampada lampada;
    private LampadaCor lampadaCor;
    private Cortina cortina;
    private PortaoGaragem portao;
    private ColunaDeSom coluna;
    private DispensadorRacao dispensador;
    private Rele rele;
    private SensorChuva sensorChuva;
    private SensorHumidadeSolo sensorHumidadeSolo;
    private SensorLuminosidade sensorLuminosidade;
    private Televisao televisao;
    private Termostato termostato;
    private Irrigacao irrigacao;

    @BeforeEach
    void setUp() {
        lampada = new Lampada("lamp1", "Philips", "Hue", 0.9, Estado.DESLIGADO, 0, 5, 50);
        lampadaCor = new LampadaCor("lampCor1", "Philips", "Hue", 1.1, Estado.DESLIGADO, 10, 2, 40, 3000);
        cortina = new Cortina("cort1", "Ikea", "Ritm", 0.2, Estado.FECHADO, 0, 0, 20);
        portao = new PortaoGaragem("port1", "Nice", "Garage", 0.4, Estado.FECHADO, 0, 0, 0);
        coluna = new ColunaDeSom("som1", "Sony", "Bass", 0.8, Estado.DESLIGADO, 0, 1, 20);
        dispensador = new DispensadorRacao("disp1", "Pets", "AutoFeed", 0.1, Estado.DESLIGADO, 0, 0, 80, 60);
        rele = new Rele("rele1", "Generic", "Relay", 0.05, Estado.DESLIGADO, 0, 0);
        sensorChuva = new SensorChuva("chuva1", "Sens", "Rain", 0.05, Estado.DESLIGADO, 0, 0, 2.0, true);
        sensorHumidadeSolo = new SensorHumidadeSolo("solo1", "Sens", "Soil", 0.05, Estado.DESLIGADO, 0, 0, 25.0, false);
        sensorLuminosidade = new SensorLuminosidade("lum1", "Sens", "Light", 0.05, Estado.DESLIGADO, 0, 0, 80);
        televisao = new Televisao("tv1", "Samsung", "Neo", 1.5, Estado.DESLIGADO, 0, 0, 10, 20);
        termostato = new Termostato("termo1", "Honeywell", "T1000", 0.3, Estado.DESLIGADO, 0, 0, 22.0, 18.0, "auto");
        irrigacao = new Irrigacao("irr1", "Gardena", "Smart", 0.2, Estado.DESLIGADO, 0, 0, 15, 50);
    }

    @Test
    void baseGettersSettersCloneEquals() {
        assertAll(
            () -> assertEquals("lamp1", lampada.getIdentificador()),
            () -> assertEquals("Philips", lampada.getMarca()),
            () -> assertEquals("Hue", lampada.getModelo()),
            () -> assertEquals(0.9, lampada.getConsumoPorHora(), 0.0001),
            () -> assertEquals(Estado.DESLIGADO, lampada.getEstado()),
            () -> assertEquals(0, lampada.getMinutosLigado()),
            () -> assertEquals(5, lampada.getNumActivacoes())
        );

        Lampada clone = lampada.clone();
        assertEquals(lampada, clone);
        assertNotSame(lampada, clone);
        assertNotNull(lampada.toString());

        lampada.setIdentificador("lampX");
        lampada.setMarca("MarcaX");
        lampada.setModelo("ModeloX");
        lampada.setConsumoPorHora(2.5);
        lampada.setEstado(Estado.LIGADO);
        lampada.setMinutosLigado(30);
        lampada.setNumActivacoes(9);

        assertEquals("lampX", lampada.getIdentificador());
        assertEquals("MarcaX", lampada.getMarca());
        assertEquals("ModeloX", lampada.getModelo());
        assertEquals(2.5, lampada.getConsumoPorHora(), 0.0001);
        assertEquals(Estado.LIGADO, lampada.getEstado());
        assertEquals(30, lampada.getMinutosLigado());
        assertEquals(9, lampada.getNumActivacoes());
    }

    @Test
    void lampadaTestes() {
        assertEquals(50, lampada.getIntensidade());
        lampada.setIntensidade(120);
        assertEquals(100, lampada.getIntensidade());
        lampada.setIntensidade(-10);
        assertEquals(0, lampada.getIntensidade());

        lampada.aumentarIntensidade(25);
        assertEquals(25, lampada.getIntensidade());
        lampada.diminuirIntensidade(10);
        assertEquals(15, lampada.getIntensidade());

        lampada.ligar();
        assertEquals(Estado.LIGADO, lampada.getEstado());
        assertEquals(100, lampada.getIntensidade());

        lampada.desligar();
        assertEquals(Estado.DESLIGADO, lampada.getEstado());
        assertEquals(0, lampada.getIntensidade());
    }

    @Test
    void lampadaCorTestes() {
        assertEquals(3000, lampadaCor.getTemperaturaCor());
        lampadaCor.setTemperaturaCor(2500);
        assertEquals(2700, lampadaCor.getTemperaturaCor());
        lampadaCor.setTemperaturaCor(4500);
        assertEquals(4000, lampadaCor.getTemperaturaCor());

        lampadaCor.maisFria();
        assertEquals(4000, lampadaCor.getTemperaturaCor());
        lampadaCor.maisQuente();
        assertEquals(3900, lampadaCor.getTemperaturaCor());

        LampadaCor clone = lampadaCor.clone();
        assertEquals(lampadaCor, clone);
        assertNotSame(lampadaCor, clone);
        assertNotNull(lampadaCor.toString());
    }

    @Test
    void cortinaTestes() {
        assertEquals(20, cortina.getAbertura());
        cortina.setAbertura(150);
        assertEquals(100, cortina.getAbertura());
        cortina.setAbertura(-5);
        assertEquals(0, cortina.getAbertura());

        cortina.abrir(60);
        assertEquals(Estado.ABERTO, cortina.getEstado());
        assertEquals(60, cortina.getAbertura());

        cortina.fechar(25);
        assertEquals(Estado.FECHADO, cortina.getEstado());
        assertEquals(75, cortina.getAbertura());

        Cortina clone = cortina.clone();
        assertEquals(cortina, clone);
        assertNotSame(cortina, clone);
        assertNotNull(cortina.toString());
    }

    @Test
    void portaoTestes() {
        assertEquals(0, portao.getAbertura());
        portao.setAbertura(70);
        assertEquals(70, portao.getAbertura());

        portao.abrir(55);
        assertEquals(Estado.ABERTO, portao.getEstado());
        assertEquals(55, portao.getAbertura());

        portao.fechar(40);
        assertEquals(Estado.FECHADO, portao.getEstado());
        assertEquals(60, portao.getAbertura());

        PortaoGaragem clone = portao.clone();
        assertEquals(portao, clone);
        assertNotSame(portao, clone);
        assertNotNull(portao.toString());
    }

    @Test
    void colunaDeSomTestes() {
        assertEquals(20, coluna.getVolume());
        coluna.setVolume(150);
        assertEquals(100, coluna.getVolume());
        coluna.setVolume(-20);
        assertEquals(0, coluna.getVolume());

        coluna.aumentarVolume(35);
        assertEquals(35, coluna.getVolume());
        coluna.diminuirVolume(10);
        assertEquals(25, coluna.getVolume());
        coluna.maxVolume();
        assertEquals(100, coluna.getVolume());
        coluna.minVolume();
        assertEquals(0, coluna.getVolume());

        coluna.ligar();
        assertEquals(Estado.LIGADO, coluna.getEstado());
        assertEquals(100, coluna.getVolume());

        coluna.desligar();
        assertEquals(Estado.DESLIGADO, coluna.getEstado());
        assertEquals(0, coluna.getVolume());

        ColunaDeSom clone = coluna.clone();
        assertEquals(coluna, clone);
        assertNotSame(coluna, clone);
        assertNotNull(coluna.toString());
    }

    @Test
    void dispensadorRacaoTestes() {
        assertEquals(80, dispensador.getNivelAgua());
        assertEquals(60, dispensador.getNivelRacao());

        dispensador.setNivelAgua(120);
        dispensador.setNivelRacao(-5);
        assertEquals(100, dispensador.getNivelAgua());
        assertEquals(0, dispensador.getNivelRacao());

        assertTrue(dispensador.racaoVazia());
        assertFalse(dispensador.aguaVazia());
        dispensador.encherRacaoSeVazia();
        assertEquals(100, dispensador.getNivelRacao());

        dispensador.setNivelAgua(0);
        assertTrue(dispensador.aguaVazia());
        dispensador.encherAguaSeVazia();
        assertEquals(100, dispensador.getNivelAgua());

        assertNotNull(dispensador.toString());
        DispensadorRacao clone = dispensador.clone();
        assertEquals(dispensador, clone);
        assertNotSame(dispensador, clone);
    }

    @Test
    void releTestes() {
        assertEquals(Estado.DESLIGADO, rele.getEstado());
        rele.ligar();
        assertEquals(Estado.LIGADO, rele.getEstado());
        rele.desligar();
        assertEquals(Estado.DESLIGADO, rele.getEstado());

        Rele clone = rele.clone();
        assertEquals(rele, clone);
        assertNotSame(rele, clone);
        assertNotNull(rele.toString());
    }

    @Test
    void sensorChuvaTestes() {
        assertEquals(2.0, sensorChuva.getPluviosidade(), 0.0001);
        assertTrue(sensorChuva.estaAChover());
        sensorChuva.setPluviosidade(0);
        assertEquals(0.0, sensorChuva.getPluviosidade(), 0.0001);
        assertFalse(sensorChuva.estaAChover());
        sensorChuva.setEstaAChover(true);
        assertTrue(sensorChuva.estaAChover());

        sensorChuva.ligar();
        assertEquals(Estado.LIGADO, sensorChuva.getEstado());
        sensorChuva.desligar();
        assertEquals(Estado.DESLIGADO, sensorChuva.getEstado());

        SensorChuva clone = sensorChuva.clone();
        assertEquals(sensorChuva, clone);
        assertNotSame(sensorChuva, clone);
        assertNotNull(sensorChuva.toString());
    }

    @Test
    void sensorHumidadeSoloTestes() {
        assertEquals(25.0, sensorHumidadeSolo.getHumidade(), 0.0001);
        assertFalse(sensorHumidadeSolo.estaSeco());
        sensorHumidadeSolo.setHumidade(0);
        assertEquals(0.0, sensorHumidadeSolo.getHumidade(), 0.0001);
        assertTrue(sensorHumidadeSolo.estaSeco());
        sensorHumidadeSolo.setEstaSeco(false);
        assertEquals(0.0, sensorHumidadeSolo.getHumidade(), 0.0001);

        sensorHumidadeSolo.ligar();
        assertEquals(Estado.LIGADO, sensorHumidadeSolo.getEstado());
        sensorHumidadeSolo.desligar();
        assertEquals(Estado.DESLIGADO, sensorHumidadeSolo.getEstado());

        SensorHumidadeSolo clone = sensorHumidadeSolo.clone();
        assertEquals(sensorHumidadeSolo, clone);
        assertNotSame(sensorHumidadeSolo, clone);
        assertNotNull(sensorHumidadeSolo.toString());
    }

    @Test
    void sensorLuminosidadeTestes() {
        assertEquals(80, sensorLuminosidade.getLuminosidade(), 0.0001);
        assertTrue(sensorLuminosidade.isPordosol());
        assertTrue(sensorLuminosidade.deveAcenderLuzes());
        assertFalse(sensorLuminosidade.isNoite());
        assertFalse(sensorLuminosidade.isDia());

        sensorLuminosidade.setLuminosidade(-1);
        assertEquals(0, sensorLuminosidade.getLuminosidade(), 0.0001);
        assertTrue(sensorLuminosidade.isNoite());

        sensorLuminosidade.setLuminosidade(1200);
        assertTrue(sensorLuminosidade.isDia());
        assertFalse(sensorLuminosidade.deveAcenderLuzes());

        sensorLuminosidade.ligar();
        assertEquals(Estado.LIGADO, sensorLuminosidade.getEstado());
        sensorLuminosidade.desligar();
        assertEquals(Estado.DESLIGADO, sensorLuminosidade.getEstado());

        SensorLuminosidade clone = sensorLuminosidade.clone();
        assertEquals(sensorLuminosidade, clone);
        assertNotSame(sensorLuminosidade, clone);
        assertNotNull(sensorLuminosidade.toString());
    }

    @Test
    void televisaoTestes() {
        assertEquals(10, televisao.getCanal());
        assertEquals(20, televisao.getVolume());

        televisao.setCanal(0);
        televisao.setVolume(120);
        assertEquals(1, televisao.getCanal());
        assertEquals(100, televisao.getVolume());

        televisao.aumentarVolume();
        assertEquals(100, televisao.getVolume());
        televisao.diminuirVolume();
        assertEquals(95, televisao.getVolume());
        televisao.mudarCanal(250);
        assertEquals(250, televisao.getCanal());
        televisao.proximoCanal();
        assertEquals(251, televisao.getCanal());
        televisao.canalAnterior();
        assertEquals(250, televisao.getCanal());

        televisao.ligar();
        assertEquals(Estado.LIGADO, televisao.getEstado());
        televisao.desligar();
        assertEquals(Estado.DESLIGADO, televisao.getEstado());

        Televisao clone = televisao.clone();
        assertEquals(televisao, clone);
        assertNotSame(televisao, clone);
        assertNotNull(televisao.toString());
    }

    @Test
    void termostatoTestes() {
        assertEquals(22.0, termostato.getTemperaturaAlvo(), 0.0001);
        assertEquals(18.0, termostato.getTemperaturaAtual(), 0.0001);
        assertEquals("auto", termostato.getModo());

        termostato.setTemperaturaAlvo(100);
        assertEquals(35.0, termostato.getTemperaturaAlvo(), 0.0001);
        termostato.setTemperaturaAlvo(1);
        assertEquals(5.0, termostato.getTemperaturaAlvo(), 0.0001);

        termostato.setTemperaturaAtual(19.5);
        termostato.setModo("aquecimento");
        assertEquals(19.5, termostato.getTemperaturaAtual(), 0.0001);
        assertEquals("aquecimento", termostato.getModo());

        termostato.aumentarTemperatura(2.5);
        assertEquals(8.0, termostato.getTemperaturaAlvo(), 0.0001);
        termostato.diminuirTemperatura(1.0);
        assertEquals(7.0, termostato.getTemperaturaAlvo(), 0.0001);

        termostato.ligar();
        assertEquals(Estado.LIGADO, termostato.getEstado());
        assertEquals("auto", termostato.getModo());
        termostato.desligar();
        assertEquals(Estado.DESLIGADO, termostato.getEstado());

        Termostato clone = termostato.clone();
        assertEquals(termostato, clone);
        assertNotSame(termostato, clone);
        assertNotNull(termostato.toString());
    }

    @Test
    void irrigacaoTestes() {
        assertEquals(15, irrigacao.getDuracaoMinutos());
        assertEquals(50, irrigacao.getCaudal());
        assertEquals(0.0, irrigacao.getUltimaChuva(), 0.0001);

        irrigacao.setDuracaoMinutos(-1);
        irrigacao.setCaudal(-10);
        irrigacao.setUltimaChuva(8.0);
        assertEquals(0, irrigacao.getDuracaoMinutos());
        assertEquals(0, irrigacao.getCaudal());
        assertEquals(8.0, irrigacao.getUltimaChuva(), 0.0001);

        assertEquals((0 / 60.0) * 0, irrigacao.calcularAguaConsumida(), 0.0001);

        irrigacao.ligar();
        assertEquals(Estado.LIGADO, irrigacao.getEstado());
        assertEquals(100, irrigacao.getCaudal());
        irrigacao.desligar();
        assertEquals(Estado.DESLIGADO, irrigacao.getEstado());
        assertEquals(0, irrigacao.getCaudal());

        Irrigacao clone = irrigacao.clone();
        assertEquals(irrigacao, clone);
        assertNotSame(irrigacao, clone);
        assertNotNull(irrigacao.toString());

        SensorChuva chuva = new SensorChuva("sc1", "M", "M", 0.1, Estado.DESLIGADO, 0, 0, 10.0, false);
        assertTrue(irrigacao.iniciarAutomatico(chuva));
        chuva.setEstaAChover(true);
        assertFalse(irrigacao.iniciarAutomatico(chuva));
    }

    @Test
    void factoryCriaTodosOsTipos() {
        assertInstanceOf(Lampada.class, DispositivoFactory.criarDispositivo("lampada"));
        assertInstanceOf(Televisao.class, DispositivoFactory.criarDispositivo("televisao"));
        assertInstanceOf(Cortina.class, DispositivoFactory.criarDispositivo("cortina"));
        assertInstanceOf(SensorChuva.class, DispositivoFactory.criarDispositivo("sensor_chuva"));
        assertInstanceOf(SensorHumidadeSolo.class, DispositivoFactory.criarDispositivo("sensor_humidade_solo"));
        assertInstanceOf(SensorLuminosidade.class, DispositivoFactory.criarDispositivo("sensor_luminosidade"));
        assertInstanceOf(Termostato.class, DispositivoFactory.criarDispositivo("termostato"));
        assertInstanceOf(PortaoGaragem.class, DispositivoFactory.criarDispositivo("portao_garagem"));
        assertInstanceOf(Rele.class, DispositivoFactory.criarDispositivo("rele"));
        assertInstanceOf(LampadaCor.class, DispositivoFactory.criarDispositivo("lampada_cor"));
        assertInstanceOf(ColunaDeSom.class, DispositivoFactory.criarDispositivo("coluna_som"));
        assertInstanceOf(DispensadorRacao.class, DispositivoFactory.criarDispositivo("dispensador_racao"));
        assertInstanceOf(Irrigacao.class, DispositivoFactory.criarDispositivo("irrigacao"));
        assertThrows(IllegalArgumentException.class, () -> DispositivoFactory.criarDispositivo("desconhecido"));
    }
}
