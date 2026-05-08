package view;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import controller.Controller;
import exceptions.*;

public class MenuAdmin extends TextUI {

    private final Scanner sc;
    private final Controller controller;
    private final Menu menu;

    public MenuAdmin(Scanner sc, Controller controller, String nomeCasa) {
        super(controller);
        this.sc = sc;
        this.controller = controller;
        this.menu = new Menu("Gerir Casa: " + nomeCasa, new String[]{
                "Gerir Utilizadores",
                "Gerir Divisões",
                "Gerir Dispositivos",
                "Gerir Cenários",
                "Gerir Automações",
                "Gerir Escalonamentos",
                "Ver consumo total",
                "Remover Casa"
        }, this.sc);

        this.menu.setPreCondition(2, () -> true);
        this.menu.setPreCondition(3, () -> this.controller.temCasasNoSistema());

        menu.setHandler(1, () -> menuUtilizadores(nomeCasa));
        menu.setHandler(2, () -> menuDivisoesAdmin(nomeCasa));
        menu.setHandler(3, () -> menuDispositivosAdmin(nomeCasa));
        menu.setHandler(4, () -> menuCenariosAdmin(nomeCasa));
        menu.setHandler(5, () -> menuAutomacoesAdmin(nomeCasa));
        menu.setHandler(6, () -> menuEscalonamentosAdmin(nomeCasa));
        menu.setHandler(7, () -> verConsumoCasa(nomeCasa));
        menu.setHandler(8, () -> removerCasa(nomeCasa));
    }

    public void run() {
        this.menu.run();
    }

    // ==================== UTILIZADORES ====================

    private void menuUtilizadores(String nomeCasa) {
        Menu menu = new Menu("Gerir Utilizadores", new String[]{
                "Criar Utilizador",
                "Listar Utilizadores",
                "Associar Utilizador a Casa",
                "Desassociar Utilizador de Casa",
        }, this.sc);

        menu.setPreCondition(1, () -> this.controller.temUtilizadoresNoSistema());

        menu.setHandler(1, () -> this.criarUtilizador(nomeCasa));
        menu.setHandler(2, () -> this.listarUtilizadores(nomeCasa));
        menu.setHandler(3, () -> associarUtilizadorACasa(nomeCasa));
        menu.setHandler(4, () -> this.removerUtilizador(nomeCasa));
        menu.run();
    }

    private void criarUtilizador(String nomeCasa) {
        System.out.print("Username: ");
        String username = this.sc.nextLine();
        System.out.print("Nome: ");
        String nome = this.sc.nextLine();
        System.out.print("Password: ");
        String pass = this.sc.nextLine();
        System.out.print("Este utilizador será admin da casa " + nomeCasa + "? (s/n): ");
        String resposta = this.sc.nextLine();
        try {
            this.controller.criaUtilizador(nome, username, pass);
            this.controller.associarUtilizadorACasa(nomeCasa, username);
            if (resposta.equalsIgnoreCase("s")) {
                this.controller.addAdminCasa(nomeCasa, username);
            }
            System.out.println("Utilizador '" + username + "' criado com sucesso.");
        } catch (UtilizadorJaExisteException e) {
            System.out.println("Erro: utilizador já existe.");
        } catch (CasaNaoExisteException e) {
            System.out.println("Erro: casa não existe.");
        } catch (UtilizadorNaoExisteException e) {
            System.out.println("Erro: utilizador não existe.");
        }

    }


    private void listarUtilizadores(String nomeCasa) {
        System.out.println("\nUtilizadores registados:");
        try{
            var utilizadores = this.controller.listarUtilizadoresCasa(nomeCasa);
            if (utilizadores.isEmpty()) {
                System.out.println("(sem utilizadores associados a esta casa)");
                return;
            }
            if (utilizadores.isEmpty()) {
            System.out.println("(sem utilizadores)");
            return;
            }
            utilizadores.forEach(u ->
            System.out.println("- " + u.getUsername() +
                            " | " + u.getNome()
                        ));
        } catch (CasaNaoExisteException e) {
            System.out.println("Casa não encontrada.");
        }
    }

    private void removerUtilizador(String nomeCasa) {
        listarUtilizadores(nomeCasa);
        System.out.print("\nUsername a remover (ENTER para voltar): ");
        String username = this.sc.nextLine();
        if (username.isBlank()) return;
        try {
            this.controller.desassociarUtilizador(username);
            System.out.println("Utilizador '" + username + "' removido.");
        } catch (SessaoNaoIniciadaException e) {
            System.out.println("Erro: sessão não iniciada.");
        } catch (UtilizadorNaoExisteException e) {
            System.out.println("Erro: utilizador não existe.");
        }
        catch (UtilizadorNaoRemovivelException e) {
            System.out.println("Erro: utilizador não pode ser removido.");
        }
        catch (CasaNaoExisteException e) {
            System.out.println("Erro: casa não existe.");
        }
    }


    //...............REMOVER CASA..................//

    private void removerCasa(String nome) {
        System.out.print("Confirmar remoção de '" + nome + "'? (s/n): ");
        String conf = this.sc.nextLine();
        if (!conf.equalsIgnoreCase("s")) return;
        try {
            this.controller.removeCasa(nome);
            System.out.println("Casa '" + nome + "' removida.");
        } catch (SessaoNaoIniciadaException e) {
            System.out.println("Erro: sessão não iniciada.");
        } catch (CasaNaoExisteException e) {
            System.out.println("Erro: casa não existe.");
        }
    }

    private void associarUtilizadorACasa(String nomeCasa) {
        System.out.print("\nUsername a associar (ENTER para voltar): ");
        String username = this.sc.nextLine();
        if (username.isBlank()) return;
        try {
            System.out.println("Quer que esse utilizador seja admin da casa? (s/n): ");
            String resp = this.sc.nextLine();
            this.controller.associarUtilizadorACasa(nomeCasa, username);
            if (resp.equalsIgnoreCase("s")) {
                this.controller.addAdminCasa(nomeCasa, username);
                System.out.println("Utilizador '" + username + "' adicionado como admin da casa '" + nomeCasa + "'.");
            }
            else System.out.println("Utilizador '" + username + "' associado à casa '" + nomeCasa + "'.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verConsumoCasa(String nomeCasa) {
        double consumo = this.controller.getConsumoCasa(nomeCasa);
        System.out.println("\nConsumo atual de '" + nomeCasa + "': " + consumo + " Wh");
    }

    // ==================== DIVISÕES (ADMIN) ====================

    private void menuDivisoesAdmin(String nomeCasa) {
        Menu menu = new Menu("Divisões - " + nomeCasa, new String[]{
                "Criar Divisão",
                "Listar Divisões",
                "Remover Divisão"
        }, this.sc);

        menu.setPreCondition(3, () -> this.controller.temDivisoes(nomeCasa));

        menu.setHandler(1, () -> criarDivisao(nomeCasa));
        menu.setHandler(2, () -> listarDivisoes(nomeCasa));
        menu.setHandler(3, () -> removerDivisao(nomeCasa));
        menu.run();
    }

    private void criarDivisao(String nomeCasa) {
        System.out.print("Nome/ID da divisão: ");
        String nomeDivisao = this.sc.nextLine();
        try {
            this.controller.selecionaCasa(nomeCasa);
            this.controller.criaDivisao(nomeDivisao);
            System.out.println("Divisão '" + nomeDivisao + "' criada.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarDivisoes(String nomeCasa) {
        System.out.println("\nDivisões de '" + nomeCasa + "':");
        var divisoes = this.controller.getSistema().getCasas().get(nomeCasa).getDivisoes();
        if (divisoes.isEmpty()) {
            System.out.println("(sem divisões)");
            return;
        }
        divisoes.values().forEach(d ->
            System.out.println("- " + d.getId() +
                               " | " + d.getNumDispositivos() + " dispositivos")
        );
    }

    private void removerDivisao(String nomeCasa) {
        listarDivisoes(nomeCasa);
        System.out.print("\nNome da divisão a remover (ENTER para voltar): ");
        String nomeDivisao = this.sc.nextLine();
        if (nomeDivisao.isBlank()) return;
        try {
            this.controller.selecionaCasa(nomeCasa);
            this.controller.removeDivisao(nomeDivisao);
            System.out.println("Divisão '" + nomeDivisao + "' removida.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ==================== DISPOSITIVOS (ADMIN) ====================

    private void menuDispositivosAdmin(String nomeCasa) {
        Menu menu = new Menu("Dispositivos - " + nomeCasa, new String[]{
                "Adicionar Dispositivo",
                "Ligar/Abrir Dispositivo",
                "Desligar/Fechar Dispositivo",
                "Ver Estado",
                "Consultar Info Dispositivo",
                "Remover Dispositivo"
        }, this.sc);

        menu.setPreCondition(2, () -> this.controller.temDispositivos(nomeCasa));
        menu.setPreCondition(3, () -> this.controller.temDispositivos(nomeCasa));
        menu.setPreCondition(4, () -> this.controller.temDispositivos(nomeCasa));

        menu.setHandler(1, () -> adicionarDispositivo(nomeCasa));
        menu.setHandler(2, () -> ligarOuAbrirDispositivoAdmin(nomeCasa));
        menu.setHandler(3, () -> desligarOuFecharDispositivoAdmin(nomeCasa));
        menu.setHandler(4, () -> verEstadoAdmin(nomeCasa));
        menu.setHandler(5, () -> consultarInfoDispositivoAdmin(nomeCasa));
        menu.setHandler(6, () -> removerDispositivoAdmin(nomeCasa));
        menu.run();
    }

    private void adicionarDispositivo(String nomeCasa) {
        listarDivisoes(nomeCasa);
        System.out.print("\nDivisão onde adicionar (ENTER para voltar): ");
        String nomeDivisao = this.sc.nextLine();
        if (nomeDivisao.isBlank()) return;
        try {
            if (!this.controller.divisaoExiste(nomeCasa, nomeDivisao)) {
                System.out.println("Divisão inexistente.");
                return;
            }
        } catch (DivisaoNaoExisteException e) {
            System.out.println("Divisão não encontrada.");
            return;
        }

        System.out.println("\nTipo de dispositivo:");
        System.out.println("1-rele  2-lampada  3-lampada_cor  4-coluna_som");
        System.out.println("5-televisao  6-cortina  7-portao_garagem  8-termostato");
        System.out.println("9-sensor_chuva  10-sensor_humidade_solo  11-sensor_luminosidade");
        System.out.println("12-dispensador_racao  13-irrigacao");
        System.out.print("Tipo: ");
        String tipo = normalizarTipoDispositivo(this.sc.nextLine());

        System.out.print("ID do dispositivo: ");
        String id = this.sc.nextLine();
        System.out.print("Marca: ");
        String marca = this.sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = this.sc.nextLine();
        System.out.print("Consumo por hora (Wh): ");
        String consumo = this.sc.nextLine();

        System.out.println("\nQueres adicionar o dispositivo a algum escalonamento? (s/n): ");
        String resposta = this.sc.nextLine();
        listarEscalonamentosAdmin(nomeCasa);
        if (resposta.equalsIgnoreCase("s")) {
            System.out.print("\nID do escalonamento: ");
            String idEscalonamento = this.sc.nextLine();
            try {
                this.controller.atualizaDispositivosEscalonamento(nomeCasa, idEscalonamento, tipo, List.of(nomeDivisao));
            } catch (CasaNaoExisteException e) {
                System.out.println("Casa não encontrada.");
            } catch (DivisaoNaoExisteException e) {
                System.out.println("Divisão não encontrada.");
            }

            try {
                this.controller.selecionaCasa(nomeCasa);
                this.controller.selecionaDivisao(nomeDivisao);
                this.controller.criaDispositivo(tipo, id, marca, modelo, consumo);
                System.out.println("Dispositivo adicionado com sucesso.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private String normalizarTipoDispositivo(String entrada) {
        return switch (entrada.trim().toLowerCase()) {
            case "1", "rele" -> "rele";
            case "2", "lampada" -> "lampada";
            case "3", "lampadacor", "lampada_cor" -> "lampada_cor";
            case "4", "colunadesom", "coluna_som" -> "coluna_som";
            case "5", "televisao" -> "televisao";
            case "6", "cortina" -> "cortina";
            case "7", "portaogaragem", "portao_garagem" -> "portao_garagem";
            case "8", "termostato" -> "termostato";
            case "9", "sensorchuva", "sensor_chuva" -> "sensor_chuva";
            case "10", "sensorhumidadesolo", "sensor_humidade_solo" -> "sensor_humidade_solo";
            case "11", "sensorluminosidade", "sensor_luminosidade" -> "sensor_luminosidade";
            case "12", "dispensadorracao", "dispensador_racao" -> "dispensador_racao";
            case "13", "irrigacao" -> "irrigacao";
            default -> entrada.trim().toLowerCase();
        };
    }

    private void ligarOuAbrirDispositivoAdmin(String nomeCasa) {
        pedirEOperarDispositivo(nomeCasa, "ligar/abrir");
    }

    private void desligarOuFecharDispositivoAdmin(String nomeCasa) {
        pedirEOperarDispositivo(nomeCasa, "desligar/fechar");
    }

    private void verEstadoAdmin(String nomeCasa) {
        pedirEOperarDispositivo(nomeCasa, "estado");
    }

    private void consultarInfoDispositivoAdmin(String nomeCasa) {
        pedirEOperarDispositivo(nomeCasa, "info");
    }

    private void removerDispositivoAdmin(String nomeCasa) {
        pedirEOperarDispositivo(nomeCasa, "remover");
    }

    private void pedirEOperarDispositivo(String nomeCasa, String operacao) {
        listarDivisoes(nomeCasa);
        System.out.print("\nDivisão (ENTER para voltar): ");
        String nomeDivisao = this.sc.nextLine();
        if (nomeDivisao.isBlank()) return;

        System.out.println("Dispositivos de '" + nomeDivisao + "':");
        this.controller.getSistema().getCasas().get(nomeCasa)
                .getDivisaoById(nomeDivisao).getDispositivos()
                .values().forEach(d -> System.out.println("- " + d.getIdentificador()));

        System.out.print("ID do dispositivo (ENTER para voltar): ");
        String idDisp = this.sc.nextLine();
        if (idDisp.isBlank()) return;

        try {
            switch (operacao) {
                case "ligar/abrir" -> {
                    this.controller.ligarOuAbrirDispositivo(nomeCasa, nomeDivisao, idDisp);
                    System.out.println("Dispositivo ligado/aberto.");
                }
                case "desligar/fechar" -> {
                    this.controller.desligarOuFecharDispositivo(nomeCasa, nomeDivisao, idDisp);
                    System.out.println("Dispositivo desligado/fechado.");
                }
                case "estado" -> System.out.println(
                        this.controller.getEstadoDispositivo(nomeCasa, nomeDivisao, idDisp));

                case "info" -> System.out.println(
                        this.controller.getInfoDispositivo(nomeCasa, nomeDivisao, idDisp));

                case "remover" -> {
                    this.controller.selecionaCasa(nomeCasa);
                    this.controller.selecionaDivisao(nomeDivisao);
                    this.controller.removeDispositivo(idDisp);
                    System.out.println("Dispositivo removido.");
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ==================== CENÁRIOS (ADMIN) ====================

    private void menuCenariosAdmin(String nomeCasa) {
        Menu menu = new Menu("Cenários - " + nomeCasa, new String[]{
                "Criar Cenário Predefinido",
                "Listar Cenários",
                "Ativar Cenário",
                "Remover Cenário"
        }, this.sc);

        menu.setPreCondition(3, () -> this.controller.getCenarios(nomeCasa).size() > 0);
        menu.setPreCondition(4, () -> this.controller.getCenarios(nomeCasa).size() > 0);

        menu.setHandler(1, () -> criarCenarioPredefinido(nomeCasa));
        menu.setHandler(2, () -> listarCenariosAdmin(nomeCasa));
        menu.setHandler(3, () -> ativarCenarioAdmin(nomeCasa));
        menu.setHandler(4, () -> removerCenarioAdmin(nomeCasa));
        menu.run();
    }

    private void criarCenarioPredefinido(String nomeCasa) {
        Menu menu = new Menu("Criar Cenário", new String[]{
                "Sair de Casa",
                "Jantar com Amigos",
                "Deitar",
                "Acordar"
        }, this.sc);

        menu.setHandler(1, () -> {
            try {
                this.controller.selecionaCasa(nomeCasa);
                this.controller.criaCenarioSairdeCasa();
                System.out.println("Cenário 'Sair de Casa' criado.");
            } catch (CasaNaoExisteException e) {
                System.out.println("Casa não encontrada.");
            } catch (DivisaoNaoExisteException e) {
                System.out.println("Divisão não encontrada.");
            }
        });
        menu.setHandler(2, () -> {
            try {
                this.controller.selecionaCasa(nomeCasa);
                this.controller.criaCenarioJantarComAmigos();
                System.out.println("Cenário 'Jantar com Amigos' criado.");
            } catch (CasaNaoExisteException e) {
                System.out.println("Casa não encontrada.");
            } catch (DivisaoNaoExisteException e) {
                System.out.println("Divisão não encontrada.");
            }
        });
        menu.setHandler(3, () -> {
            try {
                this.controller.selecionaCasa(nomeCasa);
                this.controller.criaCenarioDeitar();
                System.out.println("Cenário 'Deitar' criado.");
            } catch (CasaNaoExisteException e) {
                System.out.println("Casa não encontrada.");
            } catch (DivisaoNaoExisteException e) {
                System.out.println("Divisão não encontrada.");
            }
        });
        menu.setHandler(4, () -> {
            try {
                this.controller.selecionaCasa(nomeCasa);
                this.controller.criaCenarioAcordar();
                System.out.println("Cenário 'Acordar' criado.");
            } catch (CasaNaoExisteException e) {
                System.out.println("Casa não encontrada.");
            } catch (DivisaoNaoExisteException e) {
                System.out.println("Divisão não encontrada.");
            }
        });
        menu.run();
    }

    private void listarCenariosAdmin(String nomeCasa) {
        System.out.println("\nCenários de '" + nomeCasa + "':");
        var cenarios = this.controller.getCenarios(nomeCasa);
        if (cenarios.isEmpty()) {
            System.out.println("(sem cenários)");
            return;
        }
        cenarios.forEach(c -> System.out.println("- " + c.getNome() +
                " | " + (c.isAtivo() ? "Ativo" : "Inativo")));
    }

    private void ativarCenarioAdmin(String nomeCasa) {
        listarCenariosAdmin(nomeCasa);
        System.out.print("\nNome do cenário a ativar (ENTER para voltar): ");
        String nome = this.sc.nextLine();
        if (nome.isBlank()) return;
        this.controller.ativarCenario(nomeCasa, nome);
        System.out.println("Cenário '" + nome + "' ativado.");
    }

    private void removerCenarioAdmin(String nomeCasa) {
        listarCenariosAdmin(nomeCasa);
        System.out.print("\nNome do cenário a remover (ENTER para voltar): ");
        String nome = this.sc.nextLine();
        if (nome.isBlank()) return;
        this.controller.removeCenario(nomeCasa, nome);
        System.out.println("Cenário '" + nome + "' removido.");
    }

    // ==================== AUTOMAÇÕES (ADMIN) ====================

    private void menuAutomacoesAdmin(String nomeCasa) {
        Menu menu = new Menu("Automações - " + nomeCasa, new String[]{
                "Criar Automação",
                "Listar Automações",
                "Executar Automações",
                "Remover Automação"
        }, this.sc);

        menu.setPreCondition(3, () -> this.controller.getAutomacoes(nomeCasa).size() > 0);
        menu.setPreCondition(4, () -> this.controller.getAutomacoes(nomeCasa).size() > 0);

        menu.setHandler(1, () -> criarAutomacaoAdmin(nomeCasa));
        menu.setHandler(2, () -> listarAutomacoesAdmin(nomeCasa));
        menu.setHandler(3, () -> executarAutomacoesAdmin(nomeCasa));
        menu.setHandler(4, () -> removerAutomacaoAdmin(nomeCasa));
        menu.run();
    }

    private void criarAutomacaoAdmin(String nomeCasa) {
        System.out.println("\nTipos de automação disponíveis:");
        System.out.println("1 - Chuva");
        System.out.println("2 - Luz exterior");
        System.out.println("3 - Relva");
        System.out.println("4 - Temperatura");
        System.out.println("5 - Animais");
        System.out.print("Escolha (ENTER para voltar): ");
        String opcao = this.sc.nextLine();
        if (opcao.isBlank()) return;
        try {
            this.controller.selecionaCasa(nomeCasa);
            switch (opcao) {
                case "1" -> {
                    this.controller.criaAutomacao("chuva");
                    System.out.println("Automação 'chuva' criada.");
                }
                case "2" -> {
                    this.controller.criaAutomacao("luz_exterior");
                    System.out.println("Automação 'luz_exterior' criada.");
                }
                case "3" -> {
                    this.controller.criaAutomacao("relva");
                    System.out.println("Automação 'relva' criada.");
                }
                case "4" -> {
                    this.controller.criaAutomacao("temperatura");
                    System.out.println("Automação 'temperatura' criada.");
                }
                case "5" -> {
                    this.controller.criaAutomacao("animais");
                    System.out.println("Automação 'animais' criada.");
                }
                default -> System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarAutomacoesAdmin(String nomeCasa) {
        System.out.println("\nAutomações de '" + nomeCasa + "':");
        var automacoes = this.controller.getAutomacoes(nomeCasa);
        if (automacoes.isEmpty()) {
            System.out.println("(sem automações)");
            return;
        }
        automacoes.forEach(a -> System.out.println("- " + a));
    }

    private void executarAutomacoesAdmin(String nomeCasa) {
        try {
            this.controller.selecionaCasa(nomeCasa);
            this.controller.ativarTodasAutomacoes();
            System.out.println("Automações executadas.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void removerAutomacaoAdmin(String nomeCasa) {
        listarAutomacoesAdmin(nomeCasa);
        System.out.print("\nNome da automação a remover (ENTER para voltar): ");
        String nome = this.sc.nextLine();
        if (nome.isBlank()) return;
        try {
            this.controller.selecionaCasa(nomeCasa);
            this.controller.removeAutomacao(nome);
            System.out.println("Automação '" + nome + "' removida.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    // ==================== ESCALONAMENTOS (ADMIN) ====================

    private void menuEscalonamentosAdmin(String nomeCasa) {
        Menu menu = new Menu("Escalonamentos - " + nomeCasa, new String[]{
                "Criar Escalonamento",
                "Listar Escalonamentos",
                "Remover Escalonamento",
                "Simular Avanço de Tempo"
        }, this.sc);

        menu.setPreCondition(3, () -> this.controller.getEscalonamentos(nomeCasa).size() > 0);
        menu.setPreCondition(4, () -> this.controller.getEscalonamentos(nomeCasa).size() > 0);

        menu.setHandler(1, () -> criarEscalonamentoAdmin(nomeCasa));
        menu.setHandler(2, () -> listarEscalonamentosAdmin(nomeCasa));
        menu.setHandler(3, () -> removerEscalonamentoAdmin(nomeCasa));
        menu.setHandler(4, () -> simularHora(nomeCasa));
        menu.run();
    }

    private void criarEscalonamentoAdmin(String nomeCasa) {
        Menu menu = new Menu("Criar Escalonamento", new String[]{
                "Abrir cortinas a uma hora",
                "Fechar cortinas a uma hora",
                "Acender lâmpadas a uma hora",
                "Desligar lâmpadas a uma hora",
                "Ligar colunas a uma hora",
                "Desligar colunas a uma hora"
        }, this.sc);

        menu.setHandler(1, () -> criarEscalonamentoComHora(nomeCasa, "abrirCortinas"));
        menu.setHandler(2, () -> criarEscalonamentoComHora(nomeCasa, "fecharCortinas"));
        menu.setHandler(3, () -> criarEscalonamentoComHora(nomeCasa, "acenderLampadas"));
        menu.setHandler(4, () -> criarEscalonamentoComHora(nomeCasa, "desligarLampadas"));
        menu.setHandler(5, () -> criarEscalonamentoComHora(nomeCasa, "ligarColunas"));
        menu.setHandler(6, () -> criarEscalonamentoComHora(nomeCasa, "desligarColunas"));
        menu.run();
    }

    private void criarEscalonamentoComHora(String nomeCasa, String tipo) {
        System.out.print("Hora (HH:mm): ");
        String hora = this.sc.nextLine();
        System.out.print("IDs das divisões separados por vírgula (ENTER para todas): ");
        String input = this.sc.nextLine();
        List<String> divisoes = input.isBlank() ? null :
            List.of(input.split(",")).stream().map(String::trim).filter(s -> !s.isBlank()).toList();
        try {
            this.controller.selecionaCasa(nomeCasa);
            switch (tipo) {
                case "abrirCortinas"    -> this.controller.criaEscalonamentoAbrirCortinas(hora, divisoes);
                case "fecharCortinas"   -> this.controller.criaEscalonamentoFecharCortinas(hora, divisoes);
                case "acenderLampadas"  -> this.controller.criaEscalonamentoAcenderLampadas(hora, divisoes);
                case "desligarLampadas" -> this.controller.criaEscalonamentoDesligarLampadas(hora, divisoes);
                case "ligarColunas"     -> this.controller.criaEscalonamentoLigarColunas(hora, divisoes);
                case "desligarColunas"  -> this.controller.criaEscalonamentoDesligarColunas(hora, divisoes);
            }
            System.out.println("Escalonamento criado para as " + hora + ".");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listarEscalonamentosAdmin(String nomeCasa) {
        System.out.println("\nEscalonamentos de '" + nomeCasa + "':");
        var escalonamentos = this.controller.getEscalonamentos(nomeCasa);
        if (escalonamentos.isEmpty()) {
            System.out.println("(sem escalonamentos)");
            return;
        }
        escalonamentos.forEach(e ->
            System.out.println("- " + e.getId() + " | " + e.getHora()));
    }

    private void removerEscalonamentoAdmin(String nomeCasa) {
        listarEscalonamentosAdmin(nomeCasa);
        System.out.print("\nID do escalonamento a remover (ENTER para voltar): ");
        String id = this.sc.nextLine();
        if (id.isBlank()) return;
        try {
            this.controller.removeEscalonamento(nomeCasa, id);
            System.out.println("Escalonamento '" + id + "' removido.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void simularHora(String nomeCasa) {
        System.out.print("Hora atual (formato HH:mm): " + this.controller.getHoraAtual(nomeCasa));
        System.out.print("\nIntroduz a hora (formato HH:mm): ");
        String hora = this.sc.nextLine();
        if (hora.isBlank()) return;
        if (this.controller.getHoraAtual(nomeCasa).isBefore(LocalTime.parse(hora))) {
            try{
                this.controller.selecionaCasa(nomeCasa);
                this.controller.avancarTempo(hora, nomeCasa);
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    // ==================== ESTATÍSTICAS GLOBAIS ====================

    private void menuEstatisticasGlobais() {
        Menu menu = new Menu("Estatísticas Globais", new String[]{
                "Casa que mais consome (sistema todo)",
                "Top 3 dispositivos por tempo ligado",
                "Top 3 dispositivos por ativações",
                "Top 3 divisões com mais dispositivos"
        }, this.sc);

        menu.setHandler(1, () -> {
            var casa = this.controller.casaQueMaisConsome();
            if (casa == null) System.out.println("Sem dados.");
            else System.out.println("Casa: " + casa.getId() +
                    " — " + casa.calcularConsumoTotal() + " Wh");
        });
        menu.setHandler(2, () ->
            this.controller.top3DispositivosPorTempoLigado()
                .forEach(d -> System.out.println("- " + d.getIdentificador() +
                        " | " + d.getMinutosLigado() + " min"))
        );
        menu.setHandler(3, () ->
            this.controller.top3DispositivosPorAtivacoes()
                .forEach(d -> System.out.println("- " + d.getIdentificador() +
                        " | " + d.getNumActivacoes() + " ativações"))
        );
        menu.setHandler(4, () ->
            this.controller.top3DivisoesComMaisDispositivos()
                .forEach(e -> System.out.println("- " + e.getKey().getId() +
                        " / " + e.getValue().getId() +
                        " | " + e.getValue().getNumDispositivos() + " dispositivos"))
        );
        menu.run();
    }

}