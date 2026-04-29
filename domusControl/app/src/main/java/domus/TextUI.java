package domus;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import casas.Casa;
import casas.Divisao;
import dispositivos.Dispositivo;
import dispositivos.Lampada;
import dispositivos.Rele;
import dispositivos.Televisao;
import utilizadores.Utilizador;

public class TextUI {
    private final DomusControl model;
    private final Scanner sc;

    public TextUI() {
        this.model = new DomusControl();
        this.sc = new Scanner(System.in);
    }

    public TextUI(DomusControl model) {
        this.model = model;
        this.sc = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            Utilizador utilizadorAtual = autenticarUtilizador();
            if (utilizadorAtual == null) {
                System.out.println("A sair da aplicação.");
                return;
            }

            if (utilizadorAtual.isAdmin()) {
                menuAdmin(utilizadorAtual).run();
            } else {
                menuUtilizador(utilizadorAtual).run();
            }
        }
    }

    private Menu menuUtilizador(Utilizador utilizadorAtual) {
        Menu menu = new Menu("Menu Utilizador", new String[] {
                "Listar Minhas Casas",
                "Gestão da Minha Casa",
                "Relatórios das Minhas Casas"
        });

        menu.setHandler(1, () -> listMinhasCasas(utilizadorAtual));
        menu.setHandler(2, () -> menuGestaoMinhaCasa(utilizadorAtual).run());
        menu.setHandler(3, () -> relatoriosMinhasCasas(utilizadorAtual));
        menu.setPreCondition(1, () -> !casasDoUtilizador(utilizadorAtual).isEmpty());
        menu.setPreCondition(2, () -> !casasDoUtilizador(utilizadorAtual).isEmpty());
        menu.setPreCondition(3, () -> !casasDoUtilizador(utilizadorAtual).isEmpty());
        return menu;
    }

    private Menu menuGestaoMinhaCasa(Utilizador utilizadorAtual) {
        Menu menu = new Menu("Gestão da Minha Casa", new String[] {
                "Adicionar Divisão",
                "Remover Divisão",
                "Adicionar Dispositivo",
                "Remover Dispositivo"
        });

        menu.setHandler(1, () -> addDivisaoEmCasa(casasDoUtilizador(utilizadorAtual)));
        menu.setHandler(2, () -> remDivisaoEmCasa(casasDoUtilizador(utilizadorAtual)));
        menu.setHandler(3, () -> addDispositivoEmCasa(casasDoUtilizador(utilizadorAtual)));
        menu.setHandler(4, () -> remDispositivoEmCasa(casasDoUtilizador(utilizadorAtual)));
        menu.setPreCondition(1, () -> !casasDoUtilizador(utilizadorAtual).isEmpty());
        menu.setPreCondition(2, () -> !casasDoUtilizador(utilizadorAtual).isEmpty());
        menu.setPreCondition(3, () -> !casasDoUtilizador(utilizadorAtual).isEmpty());
        menu.setPreCondition(4, () -> !casasDoUtilizador(utilizadorAtual).isEmpty());
        return menu;
    }

    private Menu menuAdmin(Utilizador utilizadorAtual) {
        Menu menu = new Menu("Menu Admin", new String[] {
                "Gestão de Casas",
                "Gestão de Utilizadores",
                "Relatórios Globais"
        });

        menu.setHandler(1, () -> menuGestaoCasasAdmin().run());
        menu.setHandler(2, () -> menuGestaoUtilizadores().run());
        menu.setHandler(3, this::relatoriosGlobais);
        return menu;
    }

    private Menu menuGestaoCasasAdmin() {
        Menu menu = new Menu("Gestão de Casas", new String[] {
                "Adicionar Casa",
                "Listar Casas",
                "Remover Casa",
                "Adicionar Divisão",
                "Remover Divisão",
                "Adicionar Dispositivo",
                "Remover Dispositivo",
                "Associar Utilizador à Casa"
        });

        menu.setHandler(1, this::addCasa);
        menu.setHandler(2, this::listCasas);
        menu.setHandler(3, this::remCasa);
        menu.setHandler(4, () -> addDivisaoEmCasa(List.copyOf(this.model.getCasas().values())));
        menu.setHandler(5, () -> remDivisaoEmCasa(List.copyOf(this.model.getCasas().values())));
        menu.setHandler(6, () -> addDispositivoEmCasa(List.copyOf(this.model.getCasas().values())));
        menu.setHandler(7, () -> remDispositivoEmCasa(List.copyOf(this.model.getCasas().values())));
        menu.setHandler(8, this::associarUtilizadorCasa);
        menu.setPreCondition(3, () -> !this.model.getCasas().isEmpty());
        menu.setPreCondition(4, () -> !this.model.getCasas().isEmpty());
        menu.setPreCondition(5, () -> !this.model.getCasas().isEmpty());
        menu.setPreCondition(6, () -> !this.model.getCasas().isEmpty());
        menu.setPreCondition(7, () -> !this.model.getCasas().isEmpty());
        menu.setPreCondition(8, () -> !this.model.getCasas().isEmpty() && !this.model.getUtilizadores().isEmpty());
        return menu;
    }

    private Menu menuGestaoUtilizadores() {
        Menu menu = new Menu("Gestão de Utilizadores", new String[] {
                "Adicionar Utilizador",
                "Listar Utilizadores",
                "Remover Utilizador"
        });

        menu.setHandler(1, this::addUtilizador);
        menu.setHandler(2, this::listUtilizadores);
        menu.setHandler(3, this::remUtilizador);
        return menu;
    }

    private Utilizador autenticarUtilizador() {
        if (this.model.getUtilizadores().isEmpty()) {
            criarPrimeiroAdmin();
        }

        while (true) {
            int id = lerInt("Id do utilizador (0 para sair): ");
            if (id == 0) {
                return null;
            }

            Utilizador utilizador = procurarUtilizadorPorId(id);
            if (utilizador != null) {
                System.out.println("Sessão iniciada: " + utilizador.getNome() +
                        (utilizador.isAdmin() ? " (ADMIN)" : " (NORMAL)"));
                return utilizador;
            }

            System.out.println("Utilizador não encontrado.");
        }
    }

    private void criarPrimeiroAdmin() {
        System.out.println("Não existem utilizadores. Criar administrador inicial.");
        int id = lerInt("Id do administrador: ");
        String nome = lerTextoObrigatorio("Nome do administrador: ");

        Utilizador admin = new Utilizador(id, nome, true);
        this.model.adicionarUtilizador(admin);
    }

    private void listMinhasCasas(Utilizador utilizadorAtual) {
        List<Casa> casas = casasDoUtilizador(utilizadorAtual);
        if (casas.isEmpty()) {
            System.out.println("Não tens casas associadas.");
            return;
        }

        System.out.println("As tuas casas:");
        casas.forEach(c -> System.out.println("- [" + c.getId() + "] " + c.getMorada()));
    }

    private void relatoriosMinhasCasas(Utilizador utilizadorAtual) {
        List<Casa> casas = casasDoUtilizador(utilizadorAtual);
        if (casas.isEmpty()) {
            System.out.println("Não existem casas associadas a este utilizador.");
            return;
        }

        Casa casa = casas.stream()
                .max(Comparator.comparingDouble(Casa::calcularConsumoTotal))
                .orElse(null);

        if (casa == null) {
            System.out.println("Não foi possível calcular o relatório.");
            return;
        }

        System.out.println("A tua casa com maior consumo:");
        System.out.println("Id: " + casa.getId());
        System.out.println("Morada: " + casa.getMorada());
        System.out.println("Consumo total: " + casa.calcularConsumoTotal());
    }

    private void relatoriosGlobais() {
        Casa casa = this.model.casaQueMaisConsome();
        if (casa == null) {
            System.out.println("Não existem casas registadas.");
            return;
        }

        System.out.println("Casa que mais consome:");
        System.out.println("Id: " + casa.getId());
        System.out.println("Morada: " + casa.getMorada());
        System.out.println("Consumo total: " + casa.calcularConsumoTotal());
    }

    private void addCasa() {
        String id = lerTextoObrigatorio("Id da casa: ");

        if (this.model.getCasas().containsKey(id)) {
            System.out.println("Já existe uma casa com esse id.");
            return;
        }

        String morada = lerTextoObrigatorio("Morada da casa: ");

        Casa casa = new Casa();
        casa.setId(id);
        casa.setMorada(morada);

        this.model.adicionarCasa(casa);
        System.out.println("Casa adicionada com sucesso.");
    }

    private void listCasas() {
        Map<String, Casa> casas = this.model.getCasas();
        if (casas.isEmpty()) {
            System.out.println("Não existem casas registadas.");
            return;
        }

        System.out.println("Casas registadas:");
        casas.values().forEach(c ->
                System.out.println("- [" + c.getId() + "] " + c.getMorada()));
    }

    private void remCasa() {
        String id = lerTextoObrigatorio("Id da casa a remover: ");

        Casa casa = this.model.getCasas().get(id);
        if (casa == null) {
            System.out.println("Casa não encontrada.");
            return;
        }

        this.model.removerCasa(casa);
        System.out.println("Casa removida com sucesso.");
    }

    private void addUtilizador() {
        int id = lerInt("Id do utilizador: ");
        if (procurarUtilizadorPorId(id) != null) {
            System.out.println("Já existe utilizador com esse id.");
            return;
        }

        String nome = lerTextoObrigatorio("Nome do utilizador: ");
        System.out.print("É administrador? (s/n): ");
        String adminInput = sc.nextLine();

        Utilizador utilizador = new Utilizador();
        utilizador.setId(id);
        utilizador.setNome(nome);
        utilizador.setAdmin("s".equalsIgnoreCase(adminInput));

        this.model.adicionarUtilizador(utilizador);
        System.out.println("Utilizador adicionado com sucesso.");
    }

    private void listUtilizadores() {
        List<Utilizador> utilizadores = this.model.getUtilizadores();
        if (utilizadores.isEmpty()) {
            System.out.println("Não existem utilizadores registados.");
            return;
        }

        System.out.println("Utilizadores:");
        utilizadores.forEach(u -> System.out.println("- [" + u.getId() + "] " + u.getNome() +
                (u.isAdmin() ? " (ADMIN)" : " (NORMAL)")));
    }

    private void remUtilizador() {
        int id = lerInt("Id do utilizador a remover: ");
        Utilizador utilizador = procurarUtilizadorPorId(id);
        if (utilizador == null) {
            System.out.println("Utilizador não encontrado.");
            return;
        }

        this.model.removerUtilizador(utilizador);
        System.out.println("Utilizador removido com sucesso.");
    }

    private void associarUtilizadorCasa() {
        Casa casa = selecionarCasa(List.copyOf(this.model.getCasas().values()));
        if (casa == null) {
            return;
        }

        int idUtilizador = lerInt("Id do utilizador a associar: ");
        Utilizador utilizador = procurarUtilizadorPorId(idUtilizador);
        if (utilizador == null) {
            System.out.println("Utilizador não encontrado.");
            return;
        }

        casa.adicionarUtilizador(utilizador);
        System.out.println("Utilizador associado à casa com sucesso.");
    }

    private void addDivisaoEmCasa(List<Casa> casasPermitidas) {
        Casa casa = selecionarCasa(casasPermitidas);
        if (casa == null) return;

        String idDivisao = lerTextoObrigatorio("Id da divisão: ");
        if (casa.getDivisoes().containsKey(idDivisao)) {
            System.out.println("Já existe uma divisão com esse id nesta casa.");
            return;
        }

        Divisao divisao = new Divisao();
        divisao.setId(idDivisao);
        this.model.adicionarDivisao(casa, divisao);
        System.out.println("Divisão adicionada com sucesso.");
    }

    private void remDivisaoEmCasa(List<Casa> casasPermitidas) {
        Casa casa = selecionarCasa(casasPermitidas);
        if (casa == null) return;

        String idDivisao = lerTextoObrigatorio("Id da divisão a remover: ");
        Divisao divisao = casa.getDivisoes().get(idDivisao);
        if (divisao == null) {
            System.out.println("Divisão não encontrada.");
            return;
        }

        this.model.removerDivisao(casa, divisao);
        System.out.println("Divisão removida com sucesso.");
    }

    private void addDispositivoEmCasa(List<Casa> casasPermitidas) {
        Casa casa = selecionarCasa(casasPermitidas);
        if (casa == null) return;

        Divisao divisao = selecionarDivisao(casa);
        if (divisao == null) return;

        Dispositivo dispositivo = criarDispositivo();
        if (dispositivo == null) return;

        if (divisao.getDispositivos().containsKey(dispositivo.getIdentificador())) {
            System.out.println("Já existe um dispositivo com esse identificador nesta divisão.");
            return;
        }

        this.model.adicionarDispositivo(casa, divisao, dispositivo);
        System.out.println("Dispositivo adicionado com sucesso.");
    }

    private void remDispositivoEmCasa(List<Casa> casasPermitidas) {
        Casa casa = selecionarCasa(casasPermitidas);
        if (casa == null) return;

        Divisao divisao = selecionarDivisao(casa);
        if (divisao == null) return;

        int idDispositivo = lerInt("Identificador do dispositivo a remover: ");
        Dispositivo dispositivo = divisao.getDispositivos().get(idDispositivo);
        if (dispositivo == null) {
            System.out.println("Dispositivo não encontrado.");
            return;
        }

        this.model.removerDispositivo(casa, divisao, dispositivo);
        System.out.println("Dispositivo removido com sucesso.");
    }

    private Casa selecionarCasa(List<Casa> casasPermitidas) {
        if (casasPermitidas == null || casasPermitidas.isEmpty()) {
            System.out.println("Não existem casas disponíveis.");
            return null;
        }

        System.out.println("Casas disponíveis:");
        casasPermitidas.forEach(c -> System.out.println("- [" + c.getId() + "] " + c.getMorada()));

        String idCasa = lerTextoObrigatorio("Id da casa: ");
        return casasPermitidas.stream()
                .filter(c -> c.getId().equals(idCasa))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Casa não encontrada ou sem permissão.");
                    return null;
                });
    }

    private Divisao selecionarDivisao(Casa casa) {
        Map<String, Divisao> divisoes = casa.getDivisoes();
        if (divisoes.isEmpty()) {
            System.out.println("A casa não tem divisões.");
            return null;
        }

        System.out.println("Divisões disponíveis:");
        divisoes.values().forEach(d -> System.out.println("- " + d.getId()));
        String idDivisao = lerTextoObrigatorio("Id da divisão: ");

        Divisao divisao = divisoes.get(idDivisao);
        if (divisao == null) {
            System.out.println("Divisão não encontrada.");
        }
        return divisao;
    }

    private Dispositivo criarDispositivo() {
        System.out.println("Tipo de dispositivo:");
        System.out.println("1 - Rele");
        System.out.println("2 - Lampada");
        System.out.println("3 - Televisao");

        int tipo = lerInt("Opção: ");
        int identificador = lerInt("Identificador: ");
        String marca = lerTextoObrigatorio("Marca: ");
        String modelo = lerTextoObrigatorio("Modelo: ");
        double consumo = lerDouble("Consumo por hora: ");

        return switch (tipo) {
            case 1 -> new Rele(identificador, marca, modelo, consumo, Dispositivo.Estado.DESLIGADO, 0, 0);
            case 2 -> new Lampada(identificador, marca, modelo, consumo, Dispositivo.Estado.DESLIGADO, 0, 0, 0);
            case 3 -> new Televisao(identificador, marca, modelo, consumo, Dispositivo.Estado.DESLIGADO, 0, 0, 1, 0);
            default -> {
                System.out.println("Tipo de dispositivo inválido.");
                yield null;
            }
        };
    }

    private List<Casa> casasDoUtilizador(Utilizador utilizadorAtual) {
        return this.model.getCasas().values().stream()
                .filter(c -> c.getUtilizadores().contains(utilizadorAtual))
                .toList();
    }

    private Utilizador procurarUtilizadorPorId(int id) {
        return this.model.getUtilizadores().stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Introduza um número inteiro.");
            }
        }
    }

    private double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Introduza um número decimal.");
            }
        }
    }

    private String lerTextoObrigatorio(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String valor = sc.nextLine();
            if (valor != null && !valor.isBlank()) {
                return valor;
            }
            System.out.println("Valor obrigatório.");
        }
    }
}