package view;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Controller;
import exceptions.CasaJaExisteException;
import exceptions.CasaNaoExisteException;
import exceptions.CasaNaoSelecionadaException;
import exceptions.CenarioNaoExisteException;
import exceptions.CredenciaisInvalidasException;
import exceptions.DivisaoNaoExisteException;
import exceptions.SessaoNaoIniciadaException;
import exceptions.UtilizadorNaoExisteException;
import model.casas.Casa;
import model.casas.Divisao;
import model.dispositivos.Dispositivo;

public class TextUI {
    private final Scanner sc;
    private final Menu menu;
    private final Controller controller;

    public TextUI() {
        this(new Controller());
    }

    public TextUI(Controller controller) {
        this.sc = new Scanner(System.in);
        this.controller = controller;
        this.menu = new Menu("DomusControl", new String[] {
                "Login",
                "Registar",
                "Carregar Estado"
        }, this.sc);

        this.menu.setHandler(1, this::login);
        this.menu.setHandler(2, this::registar);
        menu.setHandler(3, this::carregarEstado);
    }

    public void run() {
        System.out.println("Aplicação iniciada.");
        this.menu.run();
    }

    //---------------LOGIN---------------------//

    private void login() {
        System.out.print("Utilizador: ");
        String user = this.sc.nextLine();
        System.out.print("Palavra-passe: ");
        String pass = this.sc.nextLine();
        try {
            this.controller.loginUtilizador(user, pass);

            System.out.println("\nLogin efetuado com sucesso!");
            menuUtilizador(user);
        } catch (CredenciaisInvalidasException e) {
            System.out.println("\nCredenciais inválidas!");
        }
    }

    //-------------------REGISTO---------------------//

    private void registar() {
        System.out.print("Nome: ");
        String nome = this.sc.nextLine();

        System.out.print("Username: ");
        String user = this.sc.nextLine();

        System.out.print("Palavra-passe: ");
        String pass = this.sc.nextLine();

        try {
            this.controller.criaUtilizador(nome, user, pass);
            System.out.println("Registo feito! Pode agora fazer login.");
        } catch (Exception e) {
            System.out.println("Não foi possível registar o utilizador.");
        }
        //volta ao menu principal automaticamente//
    }

    //------------------MENU UTILIZADOR-----------------//
    //...............MENU PRINCIPAL DO UTILIZADOR...........//

    private void menuUtilizador(String user) {
        Menu menu = new Menu("Menu Principal", new String[]{
            "As minhas casas",
            "Criar nova casa",
            "Estatísticas",
            "Guardar Estado",            
        }, this.sc);

        menu.setPreCondition(1, () -> this.controller.utilizadorTemCasas());

        menu.setHandler(1, () -> this.menuCasas(user));
        menu.setHandler(2, () -> this.criarCasa(user));
        menu.setHandler(3, this::menuEstatisticas);
        menu.setHandler(4, this::guardarEstado);
        menu.run(); //quando sai, volta ao menu principal
    }

    // ==================== FICHEIROS ====================

    private void guardarEstado() {
        System.out.print("Nome do ficheiro para guardar (ex: estado.obj): ");
        String nomeFicheiro = this.sc.nextLine();
        try {
            this.controller.guardarEstado(nomeFicheiro);
            System.out.println("Estado guardado com sucesso em '" + nomeFicheiro + "'.");
        } catch (Exception e) {
            System.out.println("Erro ao guardar: " + e.getMessage());
        }
    }

    private void carregarEstado() {
        System.out.print("Nome do ficheiro a carregar (ex: estado.obj): ");
        String nomeFicheiro = this.sc.nextLine();
        try {
            this.controller.carregarEstado(nomeFicheiro);
            System.out.println("Estado carregado com sucesso de '" + nomeFicheiro + "'.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar: " + e.getMessage());
        }
    }


    //------------------MENU ESTATÍSTICAS--CORRIGIR METODOS/

    private void menuEstatisticas() {
        Menu menu = new Menu("Estatísticas", new String[]{
                "Casa que mais consome",
                "Top 3 dispositivos por tempo ligado",
                "Top 3 dispositivos por ativações",
                "Top 3 divisões com mais dispositivos"
        }, this.sc);

        menu.setHandler(1, this::estatisticaCasaMaisConsome);
        menu.setHandler(2, this::estatisticaTop3Tempo);
        menu.setHandler(3, this::estatisticaTop3Activacoes);
        menu.setHandler(4, this::estatisticaTop3Divisoes);
        menu.run();
    }

    private void estatisticaCasaMaisConsome() {
        Casa casa = this.controller.casaQueMaisConsome();
        if (casa == null) {
            System.out.println("Sem dados de consumo.");
            return;
        }
        System.out.println("Casa que mais consome: " + casa.getId() + " (" + casa.calcularConsumoTotal() + ")");
    }

    private void estatisticaTop3Tempo() {
        List<Dispositivo> top3 = this.controller.top3DispositivosPorTempoLigado();
        if (top3.isEmpty()) {
            System.out.println("Sem dispositivos para apresentar.");
            return;
        }
        System.out.println("Top 3 dispositivos por tempo ligado:");
        top3.forEach(d -> System.out.println("- " + d.getIdentificador() + " (" + d.getMinutosLigado() + " min)"));
    }

    private void estatisticaTop3Activacoes() {
        List<Dispositivo> top3 = this.controller.top3DispositivosPorAtivacoes();
        if (top3.isEmpty()) {
            System.out.println("Sem dispositivos para apresentar.");
            return;
        }
        System.out.println("Top 3 dispositivos por ativações:");
        top3.forEach(d -> System.out.println("- " + d.getIdentificador() + " (" + d.getNumActivacoes() + " ativações)"));
    }

    private void estatisticaTop3Divisoes() {
        List<Map.Entry<Casa, Divisao>> top3 = this.controller.top3DivisoesComMaisDispositivos();
        if (top3.isEmpty()) {
            System.out.println("Sem divisões para apresentar.");
            return;
        }
        System.out.println("Top 3 divisões com mais dispositivos:");
        top3.forEach(e -> System.out.println("- " + e.getKey().getId() + " / " + e.getValue().getId() + " (" + e.getValue().getNumDispositivos() + " dispositivos)"));
    }

    //...................CASAS...................//

    private void menuCasas(String idUser) {
        System.out.println("\nAs suas casas: ");
        this.controller.getCasasDoUtilizador().forEach(c -> System.out.println("- " + c.getId()));

        System.out.println("Nome da casa a selecionar: (Enter para voltar)");
        String nomeCasa = sc.nextLine();
        if (nomeCasa.isEmpty()) return;

        try {
            this.controller.selecionaCasa(nomeCasa);
        } catch (CasaNaoExisteException e) {
            System.out.println("Casa não encontrada.");
            return;
        }
        try {
            if (this.controller.souAdminDaCasa(nomeCasa, idUser)) {
                MenuAdmin menuAdmin = new MenuAdmin(this.sc, this.controller, nomeCasa);
                menuAdmin.run();
            } else {
                System.out.println("Acedendo à casa '" + nomeCasa + "'...");
                menuCasa(nomeCasa);
            }
        } catch (CasaNaoExisteException e) {
            System.out.println("Erro: casa não selecionada.");
        }
    }

    private void menuCasa(String nomeCasa) {
        Menu menu = new Menu("Casa: " + nomeCasa, new String[]{
            "Ver divisões",
            "Gerir Cenários",
            "Gerir Automações",
            "Gerir Escalonamentos",
        }, this.sc);

        menu.setPreCondition(1, () -> this.controller.casaTemDivisoes(nomeCasa));
        menu.setPreCondition(2, () -> this.controller.casaTemCenarios(nomeCasa));
        menu.setPreCondition(3, () -> this.controller.casaTemAutomacoes(nomeCasa));
        menu.setPreCondition(4, () -> this.controller.casaTemEscalonamentos(nomeCasa));

        menu.setHandler(1, () -> menuDivisoes(nomeCasa));
        menu.setHandler(2, () -> menuCenarios(nomeCasa));
        menu.setHandler(3, () -> menuAutomacoes(nomeCasa));
        menu.setHandler(4, () -> menuEscalonamentos(nomeCasa));
        menu.run();
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

    private void criarCasa(String idUser) {
        System.out.print("Nome/ID da casa: ");
        String nome = this.sc.nextLine();
        System.out.print("Morada: ");
        String morada = this.sc.nextLine();
        try {
            this.controller.criaCasa(nome, morada);
            System.out.println("Casa '" + nome + "' criada.");
            this.controller.associarUtilizadorACasa(nome, idUser);
            this.controller.addAdminCasa(nome, idUser);
            // pergunta se quer associar um utilizador logo
            System.out.print("Associar utilizador à casa? (s/n): ");
            String resp = this.sc.nextLine();
            if (resp.equalsIgnoreCase("s")) {
                associarUtilizadorACasa(nome);
            }
        } catch (SessaoNaoIniciadaException e) {
            System.out.println("Erro: sessão não iniciada.");
        } catch (CasaJaExisteException e) {
            System.out.println("Erro: casa já existe.");
        } catch (CasaNaoExisteException e) {
            System.out.println("Erro: casa não encontrada.");
        } catch (UtilizadorNaoExisteException e) {
            System.out.println("Erro: utilizador não encontrado.");
        }
    }

    //...............DIVISÕES..................//

    private void menuDivisoes(String nomeCasa) {
        System.out.println(" Divisões de " + nomeCasa + "");
        this.controller.getSistema().getCasas().get(nomeCasa).getDivisoes().keySet().forEach(System.out::println);

        System.out.print("Nome da divisão a selecionar: (Enter para voltar) ");
        String nomeDivisao = sc.nextLine();
        if (nomeDivisao.isBlank()) return;

        try {
            this.controller.selecionaDivisao(nomeDivisao);
        } catch (CasaNaoSelecionadaException | DivisaoNaoExisteException e) {
            System.out.println("Divisão não encontrada.");
            return;
        }
        menuDivisao(nomeCasa, nomeDivisao);
    }

    private void menuDivisao(String nomeCasa, String nomeDivisao) {
        Menu menu = new Menu("Divisão: " + nomeDivisao, new String[]{
            "Ver dispositivos",
            "Ver consumo da divisão"
        }, this.sc);

        menu.setHandler(1, () -> menuDispositivos(nomeCasa, nomeDivisao));
        menu.setHandler(2, () -> verConsumoDivisao(nomeCasa, nomeDivisao));
        menu.run();
    }


    //------------------MENU DISPOSITIVOS-----------------//

    private void menuDispositivos(String nomeCasa, String nomeDivisao) {
        System.out.println("\nDispositivos de " + nomeDivisao + " (casa: " + nomeCasa + "):");
        this.controller.getSistema().getCasas().get(nomeCasa).getDivisaoById(nomeDivisao)
                .getDispositivos().keySet().forEach(System.out::println);
        System.out.print("ID do dispositivo (ENTER para voltar): ");
        String idDisp = this.sc.nextLine();
        if (idDisp.isEmpty()) return;
        menuDispositivo(nomeCasa, nomeDivisao, idDisp);
    }
    
    private void menuDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        String descricao = this.controller.getDescricaoDispositivo(nomeCasa, nomeDivisao, idDisp);
        System.out.println("\nDispositivo selecionado: " + descricao);

        Menu menu = new Menu("Dispositivo " + idDisp, new String[]{
                "Ligar",
                "Desligar",
                "Ver estado",
                "Consultar Info Dispositivo"
        }, this.sc);

        menu.setPreCondition(1, () ->
                !this.controller.isDispositivoLigado(nomeCasa, nomeDivisao, idDisp));
        menu.setPreCondition(2, () ->
                this.controller.isDispositivoLigado(nomeCasa, nomeDivisao, idDisp));

        menu.setHandler(1, () -> ligarDispositivo(nomeCasa, nomeDivisao, idDisp));
        menu.setHandler(2, () -> desligarDispositivo(nomeCasa, nomeDivisao, idDisp));
        menu.setHandler(3, () -> verEstadoDispositivo(nomeCasa, nomeDivisao, idDisp));
        menu.setHandler(4, () -> consultarInfoDispositivo(nomeCasa, nomeDivisao, idDisp));
        menu.run();
    }

    private void ligarDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        try {
            this.controller.ligarOuAbrirDispositivo(nomeCasa, nomeDivisao, idDisp);
            System.out.println("Dispositivo ligado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void desligarDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        try {
            this.controller.desligarOuFecharDispositivo(nomeCasa, nomeDivisao, idDisp);
            System.out.println("Dispositivo desligado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verEstadoDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        System.out.println("\nEstado atual:");
        System.out.println(this.controller.getEstadoDispositivo(nomeCasa, nomeDivisao, idDisp));
    }

    private void consultarInfoDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        System.out.println("\nInformação do dispositivo:");
        System.out.println(this.controller.getInfoDispositivo(nomeCasa, nomeDivisao, idDisp));
    }

    private void verConsumoDivisao(String nomeCasa, String nomeDivisao) {
        double consumo = this.controller.getConsumoDivisao(nomeCasa, nomeDivisao);
        System.out.println("\nConsumo atual de " + nomeDivisao + ": " + consumo + " Wh");
    }

    //.............CENÁRIOS.................//

    private void menuCenarios(String nomeCasa) {
        Menu menu = new Menu("Cenários - " + nomeCasa, new String[]{
            "Listar cenários",
            "Ativar cenário"
        }, this.sc);

        menu.setPreCondition(2, () -> this.controller.getCenarios(nomeCasa).size() > 0);

        menu.setHandler(1, () -> listarCenarios(nomeCasa));
        menu.setHandler(2, () -> ativarCenario(nomeCasa));
        menu.run();
    }

    private void listarCenarios(String nomeCasa) {
        System.out.println(" Cenários de " + nomeCasa + "");
        this.controller.getCenarios(nomeCasa).forEach(System.out::println);
        System.out.println("[lista de cenários]");
    }

    private void ativarCenario(String nomeCasa) {
        listarCenarios(nomeCasa);
        System.out.print("Nome do cenário a ativar: ");
        String nome = sc.nextLine();
        try {
            this.controller.ativarCenario(nomeCasa, nome);
        } catch (CasaNaoExisteException e) {
            System.out.println(e.getMessage());
        } catch (CenarioNaoExisteException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Cenário '" + nome + "' ativado.");
    }


    //..................AUTOMAÇÕES.......................//

    private void menuAutomacoes(String nomeCasa) {
        Menu menu = new Menu("Automações de " + nomeCasa, new String[]{
            "Criar automação",
            "Listar automações",
            "Executar automações"
        }, this.sc);

        menu.setPreCondition(2, () -> this.controller.getAutomacoes(nomeCasa).size() > 0);

        menu.setHandler(1, () -> listarAutomacoes(nomeCasa));
        menu.setHandler(2, () -> executarAutomacoes(nomeCasa));

        menu.run();
    }

    private void listarAutomacoes(String nomeCasa) {
        System.out.println("\n-- Automações de " + nomeCasa + " --");
        controller.getAutomacoes(nomeCasa).forEach(System.out::println);
        System.out.println("[lista de automações]");
    }

    private void executarAutomacoes(String nomeCasa) {
        // controller.executarAutomacoes(nomeCasa);
        System.out.println("Automações de " + nomeCasa + " verificadas e executadas.");
    }


    //..................ESCALONAMENTOS.......................//

    private void menuEscalonamentos(String nomeCasa) {
        Menu menu = new Menu("Escalonamentos de " + nomeCasa, new String[]{
            "Listar escalonamentos",
            "Simular avanço de tempo"
        }, this.sc);

        menu.setPreCondition(2, () -> this.controller.getEscalonamentos(nomeCasa).size() > 0);
        menu.setHandler(1, () -> listarEscalonamentos(nomeCasa));
        menu.setHandler(2, () -> simularHora(nomeCasa));

        menu.run();
    }

    private void listarEscalonamentos(String nomeCasa) {
        System.out.println("Escalonamentos de " + nomeCasa + "");
        controller.getEscalonamentos(nomeCasa).forEach(e -> System.out.println(e.getHora() + " - " + e));
        System.out.println("[lista de escalonamentos]");
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
    
}

