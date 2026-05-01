package domuscontrol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.dispositivos.Dispositivo;
import model.automatismos.Automacao;
import model.casas.Casa;
import model.casas.Cenario;
import model.casas.Divisao;
import model.dispositivos.ColunaDeSom;
import model.dispositivos.Cortina;
import model.dispositivos.Dispositivo.Estado;
import model.dispositivos.Irrigacao;
import model.dispositivos.Lampada;
import model.dispositivos.LampadaCor;
import model.dispositivos.PortaoGaragem;
import model.dispositivos.Rele;
import model.dispositivos.SensorChuva;
import model.dispositivos.SensorLuminosidade;
import model.dispositivos.Televisao;
import model.dispositivos.Termostato;
import model.domus.DomusControl;
import model.utilizadores.Utilizador;

public final class Testes {
    private Testes() {
    }

    public static DomusControl criarSistemaTeste() {
        Set<Casa> casas = new LinkedHashSet<>();
        casas.add(criarCasaCentro());
        casas.add(criarCasaPraia());
        return new DomusControl(casas);
    }

    private static Casa criarCasaCentro() {
        Set<Utilizador> utilizadores = new LinkedHashSet<>();
        utilizadores.add(new Admin(1, "Admin Centro"));
        utilizadores.add(new Utilizador(2, "Joao"));
        utilizadores.add(new Utilizador(3, "Marta"));

        List<Cenario> cenarios = new ArrayList<>();
        cenarios.add(new Cenario("Noite", new ArrayList<Automacao>()));
        cenarios.add(new Cenario("Ausente", new ArrayList<Automacao>()));

        Map<String, Divisao> divisoes = new LinkedHashMap<>();
        divisoes.put("Sala", criarSalaCentro());
        divisoes.put("Cozinha", criarCozinhaCentro());
        divisoes.put("Quarto", criarQuartoCentro());

        return new Casa(
            "CASA-001",
            "Rua do Centro, 10",
            divisoes,
            utilizadores,
            cenarios,
            new ArrayList<Automacao>()
        );
    }

    private static Casa criarCasaPraia() {
        Set<Utilizador> utilizadores = new LinkedHashSet<>();
        utilizadores.add(new Admin(10, "Admin Praia"));
        utilizadores.add(new Utilizador(11, "Carla"));
        utilizadores.add(new Utilizador(12, "Rui"));

        List<Cenario> cenarios = new ArrayList<>();
        cenarios.add(new Cenario("Ferias", new ArrayList<Automacao>()));
        cenarios.add(new Cenario("Tempestade", new ArrayList<Automacao>()));

        Map<String, Divisao> divisoes = new LinkedHashMap<>();
        divisoes.put("Sala", criarSalaPraia());
        divisoes.put("Exterior", criarExteriorPraia());
        divisoes.put("Suite", criarSuitePraia());

        return new Casa(
            "CASA-002",
            "Avenida da Praia, 88",
            divisoes,
            utilizadores,
            cenarios,
            new ArrayList<Automacao>()
        );
    }

    private static Divisao criarSalaCentro() {
        Set<Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new Televisao(1001, "LG", "OLED-C1", 0.18, Estado.LIGADO, 320, 25, 23, 35));
        dispositivos.add(new ColunaDeSom(1002, "Sony", "SRS-XB43", 0.06, Estado.LIGADO, 540, 42, 45));
        dispositivos.add(new LampadaCor(1003, "Philips", "Hue A60", 0.01, Estado.LIGADO, 890, 150, 70, 3200));
        dispositivos.add(new Cortina(1004, "Somfy", "Curtain X", 0.03, Estado.FECHADO, 120, 18, 20));
        return new Divisao(101, dispositivos);
    }

    private static Divisao criarCozinhaCentro() {
        Set<Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new Lampada(1101, "Ikea", "Tradfri", 0.012, Estado.LIGADO, 780, 130, 85));
        dispositivos.add(new Rele(1102, "Shelly", "1PM", 0.01, Estado.DESLIGADO, 430, 70));
        dispositivos.add(new SensorLuminosidade(1103, "Aqara", "T1", 0.004, Estado.LIGADO, 1440, 5, 280));
        dispositivos.add(new Termostato(1104, "Nest", "v3", 0.05, Estado.LIGADO, 600, 66, 21.0, 20.3, "auto"));
        return new Divisao(102, dispositivos);
    }

    private static Divisao criarQuartoCentro() {
        Set<Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new LampadaCor(1201, "Xiaomi", "MiBulb", 0.009, Estado.DESLIGADO, 340, 54, 40, 2900));
        dispositivos.add(new Termostato(1202, "Tado", "Smart", 0.04, Estado.LIGADO, 920, 81, 19.5, 19.0, "aquecimento"));
        dispositivos.add(new SensorChuva(1203, "Bosch", "RainMini", 0.003, Estado.LIGADO, 1300, 9, 0.0, false));
        return new Divisao(103, dispositivos);
    }

    private static Divisao criarSalaPraia() {
        Set<Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new Televisao(2001, "Samsung", "Q80", 0.2, Estado.LIGADO, 410, 37, 12, 28));
        dispositivos.add(new ColunaDeSom(2002, "JBL", "Charge 5", 0.05, Estado.LIGADO, 300, 21, 55));
        dispositivos.add(new Lampada(2003, "Osram", "Smart+", 0.011, Estado.LIGADO, 500, 88, 75));
        return new Divisao(201, dispositivos);
    }

    private static Divisao criarExteriorPraia() {
        Set<Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new PortaoGaragem(2101, "FAAC", "D600", 0.08, Estado.FECHADO, 180, 27, 0));
        dispositivos.add(new Irrigacao(2102, "RainBird", "ESP", 0.09, Estado.DESLIGADO, 200, 11, 30, 250));
        dispositivos.add(new SensorChuva(2103, "Netatmo", "RainGauge", 0.004, Estado.LIGADO, 1440, 7, 1.2, true));
        dispositivos.add(new SensorLuminosidade(2104, "Eve", "Light", 0.004, Estado.LIGADO, 1440, 7, 85));
        return new Divisao(202, dispositivos);
    }

    private static Divisao criarSuitePraia() {
        Set<dispositivos.Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new LampadaCor(2201, "Philips", "Hue E14", 0.008, Estado.DESLIGADO, 250, 33, 20, 2700));
        dispositivos.add(new Termostato(2202, "Daikin", "SmartTherm", 0.06, Estado.LIGADO, 700, 52, 22.0, 23.1, "arrefecimento"));
        dispositivos.add(new Rele(2203, "Sonoff", "MiniR4", 0.01, Estado.DESLIGADO, 340, 40));
        return new Divisao(203, dispositivos);
    }
}
