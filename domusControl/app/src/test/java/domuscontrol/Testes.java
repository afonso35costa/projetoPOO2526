package domuscontrol;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import automatismos.Automacao;
import casas.Casa;
import casas.Cenario;
import casas.Divisao;
import dispositivos.ColunaDeSom;
import dispositivos.Cortina;
import dispositivos.Irrigacao;
import dispositivos.Lampada;
import dispositivos.LampadaCor;
import dispositivos.PortaoGaragem;
import dispositivos.Rele;
import dispositivos.SensorChuva;
import dispositivos.SensorLuminosidade;
import dispositivos.Televisao;
import dispositivos.Termostato;
import domus.Sistema;
import utilizadores.Admin;
import utilizadores.Utilizador;

public final class Testes {
    private Testes() {
    }

    public static Sistema criarSistemaTeste() {
        Set<Casa> casas = new LinkedHashSet<>();
        casas.add(criarCasaCentro());
        casas.add(criarCasaPraia());
        return new Sistema(casas);
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
        Set<dispositivos.Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new Televisao(1001, "LG", "OLED-C1", 0.18, true, 320, 25, 23, 35));
        dispositivos.add(new ColunaDeSom(1002, "Sony", "SRS-XB43", 0.06, true, 540, 42, 45));
        dispositivos.add(new LampadaCor(1003, "Philips", "Hue A60", 0.01, true, 890, 150, 70, 3200));
        dispositivos.add(new Cortina(1004, "Somfy", "Curtain X", 0.03, false, 120, 18, 20));
        return new Divisao(101, dispositivos);
    }

    private static Divisao criarCozinhaCentro() {
        Set<dispositivos.Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new Lampada(1101, "Ikea", "Tradfri", 0.012, true, 780, 130, 85));
        dispositivos.add(new Rele(1102, "Shelly", "1PM", 0.01, false, 430, 70));
        dispositivos.add(new SensorLuminosidade(1103, "Aqara", "T1", 0.004, true, 1440, 5, 280));
        dispositivos.add(new Termostato(1104, "Nest", "v3", 0.05, true, 600, 66, 21.0, 20.3, "auto"));
        return new Divisao(102, dispositivos);
    }

    private static Divisao criarQuartoCentro() {
        Set<dispositivos.Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new LampadaCor(1201, "Xiaomi", "MiBulb", 0.009, false, 340, 54, 40, 2900));
        dispositivos.add(new Termostato(1202, "Tado", "Smart", 0.04, true, 920, 81, 19.5, 19.0, "aquecimento"));
        dispositivos.add(new SensorChuva(1203, "Bosch", "RainMini", 0.003, true, 1300, 9, 0.0, false));
        return new Divisao(103, dispositivos);
    }

    private static Divisao criarSalaPraia() {
        Set<dispositivos.Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new Televisao(2001, "Samsung", "Q80", 0.2, true, 410, 37, 12, 28));
        dispositivos.add(new ColunaDeSom(2002, "JBL", "Charge 5", 0.05, true, 300, 21, 55));
        dispositivos.add(new Lampada(2003, "Osram", "Smart+", 0.011, true, 500, 88, 75));
        return new Divisao(201, dispositivos);
    }

    private static Divisao criarExteriorPraia() {
        Set<dispositivos.Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new PortaoGaragem(2101, "FAAC", "D600", 0.08, false, 180, 27, 0));
        dispositivos.add(new Irrigacao(2102, "RainBird", "ESP", 0.09, false, 200, 11, 30, 250));
        dispositivos.add(new SensorChuva(2103, "Netatmo", "RainGauge", 0.004, true, 1440, 7, 1.2, true));
        dispositivos.add(new SensorLuminosidade(2104, "Eve", "Light", 0.004, true, 1440, 7, 85));
        return new Divisao(202, dispositivos);
    }

    private static Divisao criarSuitePraia() {
        Set<dispositivos.Dispositivo> dispositivos = new LinkedHashSet<>();
        dispositivos.add(new LampadaCor(2201, "Philips", "Hue E14", 0.008, false, 250, 33, 20, 2700));
        dispositivos.add(new Termostato(2202, "Daikin", "SmartTherm", 0.06, true, 700, 52, 22.0, 23.1, "arrefecimento"));
        dispositivos.add(new Rele(2203, "Sonoff", "MiniR4", 0.01, false, 340, 40));
        return new Divisao(203, dispositivos);
    }
}
