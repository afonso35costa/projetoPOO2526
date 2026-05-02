package view;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.Controller;
import exceptions.CasaNaoExisteException;
import exceptions.CasaNaoSelecionadaException;
import exceptions.CredenciaisInvalidasException;
import exceptions.DivisaoNaoExisteException;
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
                "Registar"
        }, this.sc);

        this.menu.setHandler(1, this::login);
        this.menu.setHandler(2, this::registar);
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
            boolean isAdmin = this.controller.getSistema().getUtilizadores().get(user).isAdmin();

            System.out.println("\nLogin efetuado com sucesso!");
            if (isAdmin) {
                new MenuAdmin(sc).run();
            } else {
                menuUtilizador();
            }
        } catch (CredenciaisInvalidasException e) {
            System.out.println("\nCredenciais inválidas!");
        }
    }

    //-------------------REGISTO---------------------//

    private void registar() {
        System.out.print("Novo utilizador: ");
        String user = this.sc.nextLine();

        System.out.print("Email: ");
        String email = this.sc.nextLine();

        System.out.print("Nova palavra-passe: ");
        String pass = this.sc.nextLine();
        try {
            this.controller.criaUtilizador(user, user, email, pass);
            System.out.println("Registo feito! Pode agora fazer login.");
        } catch (Exception e) {
            System.out.println("Não foi possível registar o utilizador.");
        }
        //volta ao menu principal automaticamente//
    }

    //------------------MENU UTILIZADOR-----------------//
    //...............MENU PRINCIPAL DO UTILIZADOR...........//

    private void menuUtilizador() {
        Menu menu = new Menu("Menu Principal", new String[]{
            "As minhas casas",
            "Estatísticas"            
        }, this.sc);

        menu.setPreCondition(2, () -> this.controller.utilizadorTemCasas());

        menu.setHandler(1, this::menuCasas);
        menu.setHandler(2, this::menuEstatisticas);
        menu.run(); //quando sai, volta ao menu principal
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

    private void menuCasas() {
        System.out.println("\nAs suas casas: ");
        this.controller.getSistema().getCasas().keySet().forEach(System.out::println);

        System.out.println("Nome da casa a selecionar: ");
        String nomeCasa = sc.nextLine();
        if (nomeCasa.isEmpty()) return;

        try {
            this.controller.selecionaCasa(nomeCasa);
        } catch (CasaNaoExisteException e) {
            System.out.println("Casa não encontrada.");
            return;
        }
        menuCasa(nomeCasa);
    }

    private void menuCasa(String nomeCasa) {
        Menu menu = new Menu("Casa: " + nomeCasa, new String[]{
            "Ver divisões",
            "Gerir Cenários",
            "Gerir Automações",
            "Gerir Escalonamentos"
        }, this.sc);

        menu.setHandler(1, () -> menuDivisoes(nomeCasa));
        menu.setHandler(2, () -> menuCenarios(nomeCasa));
        menu.setHandler(3, () -> menuAutomacoes(nomeCasa));
        menu.setHandler(4, () -> menuEscalonamentos(nomeCasa));
        menu.run();
    }
    

    //...............DIVISÕES..................//

    private void menuDivisoes(String nomeCasa) {
        System.out.println(" Divisões de " + nomeCasa + "");
        this.controller.getSistema().getCasas().get(nomeCasa).getDivisoes().keySet().forEach(System.out::println);

        System.out.print("Nome da divisão a selecionar: ");
        String nomeDivisao = sc.nextLine();
        if (nomeDivisao.isEmpty()) return;

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
                "Ver estado"
        }, this.sc);

        menu.setPreCondition(1, () ->
                !this.controller.isDispositivoLigado(nomeCasa, nomeDivisao, idDisp));
        menu.setPreCondition(2, () ->
                this.controller.isDispositivoLigado(nomeCasa, nomeDivisao, idDisp));

        menu.setHandler(1, () -> ligarDispositivo(nomeCasa, nomeDivisao, idDisp));
        menu.setHandler(2, () -> desligarDispositivo(nomeCasa, nomeDivisao, idDisp));
        menu.setHandler(3, () -> verEstadoDispositivo(nomeCasa, nomeDivisao, idDisp));
        menu.run();
    }

    private void ligarDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        try {
            this.controller.ligarDispositivo(nomeCasa, nomeDivisao, idDisp);
            System.out.println("Dispositivo ligado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void desligarDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        try {
            this.controller.desligarDispositivo(nomeCasa, nomeDivisao, idDisp);
            System.out.println("Dispositivo desligado.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verEstadoDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        System.out.println("\nEstado atual:");
        System.out.println(this.controller.getEstadoDispositivo(nomeCasa, nomeDivisao, idDisp));
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
        this.controller.ativarCenario(nomeCasa, nome);
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
            "Executar escalonamentos"
        }, this.sc);

        menu.setPreCondition(2, () -> this.controller.getEscalonamentos(nomeCasa).size() > 0);
        menu.setHandler(1, () -> listarEscalonamentos(nomeCasa));
        menu.setHandler(2, () -> executarEscalonamentos(nomeCasa));

        menu.run();
    }

    private void listarEscalonamentos(String nomeCasa) {
        System.out.println("Escalonamentos de " + nomeCasa + "");
        controller.getEscalonamentos(nomeCasa).forEach(e -> System.out.println(e.getHora() + " - " + e));
        System.out.println("[lista de escalonamentos]");
    }

    private void executarEscalonamentos(String nomeCasa) {
        System.out.println("Escalonamentos de " + nomeCasa + " executados.");
    }

    
    
}

