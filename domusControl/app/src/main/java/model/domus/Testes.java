package model.domus;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.automatismos.*;
import model.casas.*;
import model.dispositivos.*;
import model.dispositivos.Dispositivo.Estado;
import model.utilizadores.Utilizador;

public class Testes {

    public static DomusControl criarSistemaTeste() {
        DomusControl sistema = new DomusControl();

        try {
            // ===== UTILIZADORES =====
            Utilizador admin = new Utilizador("admin", "Administrador", true, "admin123");
            Utilizador joao = new Utilizador("joao", "João Silva", false, "joao123");
            Utilizador maria = new Utilizador("maria", "Maria Santos", false, "maria123");

            sistema.adicionarUtilizador(admin);
            sistema.adicionarUtilizador(joao);
            sistema.adicionarUtilizador(maria);

            // ===== CASA 1 — Casa do João =====
            Casa casa1 = new Casa("casa_joao", "Rua das Flores, 10",
                    new HashMap<>(), new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            // Divisões da casa 1
            Divisao sala = new Divisao("sala", new HashMap<>());
            Divisao cozinha = new Divisao("cozinha", new HashMap<>());
            Divisao quarto = new Divisao("quarto", new HashMap<>());
            Divisao garagem = new Divisao("garagem", new HashMap<>());

            // Dispositivos da sala
            Lampada lampSala = new Lampada("lamp_sala", "Philips", "Hue White", 0.9,
                    Estado.DESLIGADO, 120, 15, 80);
            LampadaCor lampCorSala = new LampadaCor("lampcor_sala", "IKEA", "Tradfri",
                    0.8, Estado.DESLIGADO, 90, 10, 70, 3000);
            ColunaDeSom colunaSala = new ColunaDeSom("coluna_sala", "Sonos", "One",
                    5.0, Estado.DESLIGADO, 60, 8, 50);
            Televisao tvSala = new Televisao("tv_sala", "Samsung", "QLED 55",
                    80.0, Estado.DESLIGADO, 45, 12, 5, 30);
            Cortina cortinaSala = new Cortina("cortina_sala", "Somfy", "RS100",
                    2.0, Estado.FECHADO, 30, 6, 0);
            SensorLuminosidade sensorLuz = new SensorLuminosidade("sensor_luz_sala",
                    "Aqara", "LGXA01", 0.1, Estado.LIGADO, 0, 0, 450.0);
            Rele releSala = new Rele("rele_sala", "Sonoff", "Basic R2",
                    0.5, Estado.LIGADO, 10, 2);

            sala.adicionarDispositivo(lampSala);
            sala.adicionarDispositivo(lampCorSala);
            sala.adicionarDispositivo(colunaSala);
            sala.adicionarDispositivo(tvSala);
            sala.adicionarDispositivo(cortinaSala);
            sala.adicionarDispositivo(sensorLuz);
            sala.adicionarDispositivo(releSala);

            // Dispositivos da cozinha
            Lampada lampCozinha = new Lampada("lamp_cozinha", "Philips", "Hue White",
                    0.9, Estado.LIGADO, 200, 20, 100);
            Irrigacao irrigacao = new Irrigacao("irrigacao_cozinha", "Gardena",
                    "Smart", 1.0, Estado.DESLIGADO, 0, 0, 15, 60);
            Rele tomadaCozinha = new Rele("tomada_cozinha", "Sonoff", "S26",
                    0.5, Estado.LIGADO, 500, 1);

            cozinha.adicionarDispositivo(lampCozinha);
            cozinha.adicionarDispositivo(irrigacao);
            cozinha.adicionarDispositivo(tomadaCozinha);

            // Dispositivos do quarto
            Lampada lampQuarto = new Lampada("lamp_quarto", "IKEA", "Tradfri",
                    0.6, Estado.DESLIGADO, 80, 9, 50);
            LampadaCor lampCorQuarto = new LampadaCor("lampcor_quarto", "Philips",
                    "Hue Color", 0.9, Estado.DESLIGADO, 70, 8, 30, 2700);
            Cortina cortinaQuarto = new Cortina("cortina_quarto", "Somfy", "RS100",
                    2.0, Estado.FECHADO, 20, 4, 0);
            ColunaDeSom colunaQuarto = new ColunaDeSom("coluna_quarto", "JBL",
                    "Flip 5", 3.0, Estado.DESLIGADO, 40, 5, 30);

            quarto.adicionarDispositivo(lampQuarto);
            quarto.adicionarDispositivo(lampCorQuarto);
            quarto.adicionarDispositivo(cortinaQuarto);
            quarto.adicionarDispositivo(colunaQuarto);

            // Dispositivos da garagem
            PortaoGaragem portao = new PortaoGaragem("portao_garagem", "Nice",
                    "Run400", 150.0, Estado.FECHADO, 50, 25, 0);
            SensorChuva sensorChuva = new SensorChuva("sensor_chuva", "Aqara",
                    "WS-EUK03", 0.1, Estado.LIGADO, 0, 0, 0.0, false);

            garagem.adicionarDispositivo(portao);
            garagem.adicionarDispositivo(sensorChuva);

            // Adiciona divisões à casa
            casa1.adicionarDivisao(sala);
            casa1.adicionarDivisao(cozinha);
            casa1.adicionarDivisao(quarto);
            casa1.adicionarDivisao(garagem);

            sistema.adicionarCasa(casa1, "joao");

            // Associa maria à casa do joão também
            sistema.associarUtilizadorACasa("casa_joao", "maria");

            // ===== CASA 2 — Casa da Maria =====
            Casa casa2 = new Casa("casa_maria", "Av. da República, 45",
                    new HashMap<>(), new HashSet<>(),
                    new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

            Divisao salaMaria = new Divisao("sala", new HashMap<>());
            Divisao quartoPrincipal = new Divisao("quarto_principal", new HashMap<>());

            Lampada lampSalaMaria = new Lampada("lamp_sala_m", "Philips", "Hue",
                    0.9, Estado.LIGADO, 300, 30, 100);
            Televisao tvMaria = new Televisao("tv_maria", "LG", "OLED 65",
                    90.0, Estado.LIGADO, 100, 20, 3, 40);
            Termostato termostato = new Termostato("termostato", "Nest",
                    "Learning", 2.0, Estado.LIGADO, 0, 0, 21.0, 19.5, "auto");

            salaMaria.adicionarDispositivo(lampSalaMaria);
            salaMaria.adicionarDispositivo(tvMaria);
            salaMaria.adicionarDispositivo(termostato);

            Lampada lampQuartoMaria = new Lampada("lamp_quarto_m", "IKEA",
                    "Tradfri", 0.6, Estado.DESLIGADO, 50, 6, 50);
            PortaoGaragem fechadura = new PortaoGaragem("fechadura", "Yale",
                    "Conexis L1", 1.0, Estado.FECHADO, 10, 5, 0);

            quartoPrincipal.adicionarDispositivo(lampQuartoMaria);
            quartoPrincipal.adicionarDispositivo(fechadura);

            casa2.adicionarDivisao(salaMaria);
            casa2.adicionarDivisao(quartoPrincipal);

            sistema.adicionarCasa(casa2, "maria");

            // ===== CENÁRIOS — Casa do João =====
            // criados via controller seria o ideal mas aqui fazemos direto

            // Cenário Sair de Casa
            List<Dispositivo> dispSair = new ArrayList<>();
            dispSair.add(lampSala); dispSair.add(lampCorSala);
            dispSair.add(lampCozinha); dispSair.add(lampQuarto);
            dispSair.add(cortinaSala); dispSair.add(cortinaQuarto);
            dispSair.add(colunaSala); dispSair.add(colunaQuarto);

            List<EstrategiaCenario> estrategiasSair = List.of(
                new EstrategiaCenario(d -> d instanceof Lampada,
                        d -> ((Lampada) d).desligar()),
                new EstrategiaCenario(d -> d instanceof Cortina,
                        d -> ((Cortina) d).fechar(100)),
                new EstrategiaCenario(d -> d instanceof ColunaDeSom,
                        d -> ((ColunaDeSom) d).desligar())
            );
            Cenario cenarioSair = new Cenario("sair_de_casa", false,
                    dispSair, estrategiasSair);
            sistema.adicionarCenario("casa_joao", cenarioSair);

            // Cenário Deitar
            List<Dispositivo> dispDeitar = new ArrayList<>(dispSair);
            dispDeitar.add(tvSala);
            List<EstrategiaCenario> estrategiasDeitar = List.of(
                new EstrategiaCenario(d -> d instanceof Lampada,
                        d -> ((Lampada) d).desligar()),
                new EstrategiaCenario(d -> d instanceof Cortina,
                        d -> ((Cortina) d).fechar(100)),
                new EstrategiaCenario(d -> d instanceof ColunaDeSom,
                        d -> ((ColunaDeSom) d).desligar()),
                new EstrategiaCenario(d -> d instanceof Televisao,
                        d -> ((Televisao) d).desligar())
            );
            Cenario cenarioDeitar = new Cenario("deitar", false,
                    dispDeitar, estrategiasDeitar);
            sistema.adicionarCenario("casa_joao", cenarioDeitar);

            // Cenário Acordar
            List<Dispositivo> dispAcordar = new ArrayList<>();
            dispAcordar.add(lampQuarto); dispAcordar.add(lampCorQuarto);
            dispAcordar.add(cortinaQuarto); dispAcordar.add(colunaQuarto);
            List<EstrategiaCenario> estrategiasAcordar = List.of(
                new EstrategiaCenario(d -> d instanceof Lampada,
                        d -> ((Lampada) d).ligar()),
                new EstrategiaCenario(d -> d instanceof Cortina,
                        d -> ((Cortina) d).abrir(50)),
                new EstrategiaCenario(d -> d instanceof ColunaDeSom,
                        d -> ((ColunaDeSom) d).setVolume(40))
            );
            Cenario cenarioAcordar = new Cenario("acordar", false,
                    dispAcordar, estrategiasAcordar);
            sistema.adicionarCenario("casa_joao", cenarioAcordar);

            // Cenário Jantar com Amigos
            List<Dispositivo> dispJantar = new ArrayList<>();
            dispJantar.add(lampCorSala); dispJantar.add(cortinaSala);
            dispJantar.add(colunaSala);
            List<EstrategiaCenario> estrategiasJantar = List.of(
                new EstrategiaCenario(d -> d instanceof LampadaCor,
                        d -> ((LampadaCor) d).maisQuente()),
                new EstrategiaCenario(d -> d instanceof Cortina,
                        d -> ((Cortina) d).fechar(75)),
                new EstrategiaCenario(d -> d instanceof ColunaDeSom,
                        d -> ((ColunaDeSom) d).setVolume(50))
            );
            Cenario cenarioJantar = new Cenario("jantar_com_amigos", false,
                    dispJantar, estrategiasJantar);
            sistema.adicionarCenario("casa_joao", cenarioJantar);

            // ===== ESCALONAMENTOS — Casa do João =====
            List<Dispositivo> cortinasTodas = List.of(cortinaSala, cortinaQuarto);
            List<Dispositivo> lampadasTodas = List.of(lampSala, lampCorSala,
                    lampCozinha, lampQuarto);

            Escalonamento abrirCortinasManh = new Escalonamento(
                    "abre_cortinas_manha", LocalTime.of(7, 30),
                    cortinasTodas, d -> d instanceof Cortina,
                    d -> ((Cortina) d).abrir(100));

            Escalonamento fecharCortinasNoite = new Escalonamento(
                    "fecha_cortinas_noite", LocalTime.of(21, 0),
                    cortinasTodas, d -> d instanceof Cortina,
                    d -> ((Cortina) d).fechar(100));

            Escalonamento acenderLampadasNoite = new Escalonamento(
                    "acende_lampadas_noite", LocalTime.of(20, 0),
                    lampadasTodas, d -> d instanceof Lampada,
                    d -> ((Lampada) d).ligar());

            Escalonamento desligarLampadasManh = new Escalonamento(
                    "desliga_lampadas_manha", LocalTime.of(8, 0),
                    lampadasTodas, d -> d instanceof Lampada,
                    d -> ((Lampada) d).desligar());

            sistema.adicionarEscalonamento("casa_joao", abrirCortinasManh);
            sistema.adicionarEscalonamento("casa_joao", fecharCortinasNoite);
            sistema.adicionarEscalonamento("casa_joao", acenderLampadasNoite);
            sistema.adicionarEscalonamento("casa_joao", desligarLampadasManh);

            // ===== AUTOMAÇÕES — Casa do João =====
            Automacao automacaoChuva = new AutomacaoChuva(
                    sensorChuva, List.of(cortinaSala, cortinaQuarto));
            Automacao automacaoLuz = new AutomacaoLuzExterior(
                    sensorLuz, List.of(lampSala, lampCorSala));

            sistema.adicionarAutomacao("casa_joao", automacaoChuva);
            sistema.adicionarAutomacao("casa_joao", automacaoLuz);

        } catch (Exception e) {
            System.out.println("Erro ao criar dados de teste: " + e.getMessage());
        }

        return sistema;
    }
}

