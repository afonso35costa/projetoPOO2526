package view;

import java.util.Scanner;

public class TextUI {
    private final Scanner sc;
    private final Menu menu;

    public TextUI() {
        this.sc = new Scanner(System.in);
        this.menu = new Menu("DomusControl", new String[] {
                "Login",
                "Registar"
        });

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
        // Lógica de login aqui
        boolean autenticado = false; //depois isto troca-se com o que estiver no controller! e penso que seja assim (controller.loginUser(user,pass))
        boolean isAdmin = false; // por enquanto fica assim mas é boolean isAdmin = controller.isAdmin(user);

        if (autenticado) {
            System.out.println("\nLogin efetuado com sucesso!");
            if (isAdmin) {
                new MenuAdmin(sc).run();
            } else {
                menuUtilizador();
            }
        } else {
            System.out.println("\nCredenciais inválidas!");
        }
    }

    //-------------------REGISTO---------------------//

    private void registar() {
        System.out.print("Novo utilizador: ");
        String user = this.sc.nextLine();

        System.out.print("Nova palavra-passe: ");
        String pass = this.sc.nextLine();
        
        //deverá ser assim, not sure controller.criaUtilizador(nome, user, pass, "nao"(é admin?))//
        System.out.println("Registo feito!");
        //volta ao menu principal automaticamente//
    }

    //------------------MENU UTILIZADOR-----------------//
    //...............MENU PRINCIPAL DO UTILIZADOR...........//

    private void menuUtilizador() {
        Menu menu = new Menu("Menu Principal", new String[]{
            "As minhas casas",
            "Estatísticas"            
        });

        menu.setHandler(1, this::menuCasas);
        menu.setHandler(2, this::menuEstatisticas);
        menu.run(); //quando sai, volta ao menu principal
    }

    //...................CASAS...................//

    private void menuCasas() {
        System.out.println("As suas casas: ");
        //ir buscar as casas do user//
        System.out.println("[lista de casas]");

        System.out.println("Nome da casa a selecionar: ");
        String nomeCasa = sc.nextLine();
        if (nomeCasa.isEmpty()) return;

        //boolean existe = controller.selecionaCasa(nomeCasa);
        boolean existe = true;
        if (!existe) {
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
        });

        menu.setHandler(1, () -> menuDivisoes(nomeCasa));
        menu.setHandler(2, () -> menuCenarios(nomeCasa));
        menu.setHandler(3, () -> menuAutomacoes(nomeCasa));
        menu.setHandler(4, () -> menuEscalonamentos(nomeCasa));
        menu.run();
    }
    

    //...............DIVISÕES..................//

    private void menuDivisoes(String nomeCasa) {
        System.out.println(" Divisões de " + nomeCasa + "");
        // deverá ser assim ig ->  controller.getDivisoesDaCasa(nomeCasa).forEach(System.out::println);
        System.out.println("[lista de divisoes]");

        System.out.print("Nome da divisão a selecionar: ");
        String nomeDivisao = sc.nextLine();
        if (nomeDivisao.isEmpty()) return;

        // boolean existe = controller.selecionaDivisao(nomeDivisao);
        boolean existe = true;
        if (!existe) {
            System.out.println("Divisão não encontrada.");
            return;
        }
        menuDivisao(nomeCasa, nomeDivisao);
    }

    private void menuDivisao(String nomeCasa, String nomeDivisao) {
        Menu menu = new Menu("Divisão: " + nomeDivisao, new String[]{
            "Ver dispositivos",
            "Ver consumo da divisão"
        });

        menu.setHandler(1, () -> menuDispositivos(nomeCasa, nomeDivisao));
        menu.setHandler(2, () -> {//ir buscar o consumo da divisao
        });
        menu.run();
    }
    

    //.............CENÁRIOS.................//

    private void menuCenarios(String nomeCasa) {
        Menu menu = new Menu("Cenários - " + nomeCasa, new String[]{
            "Listar cenários",
            "Ativar cenário"
        });

        menu.setHandler(1, () -> listarCenarios(nomeCasa));
        menu.setHandler(2, () -> ativarCenario(nomeCasa));
        menu.run();
    }

    private void listarCenarios(String nomeCasa) {
        System.out.println(" Cenários de " + nomeCasa + "");
        //talvez seja idk controller.getCenarios(nomeCasa).forEach(System.out::println);
        System.out.println("[lista de cenários]");
    }

    private void ativarCenario(String nomeCasa) {
        listarCenarios(nomeCasa);
        System.out.print("Nome do cenário a ativar: ");
        String nome = sc.nextLine();
        //controller.ativarCenario(nomeCasa, nome);
        System.out.println("Cenário '" + nome + "' ativado.");
    }

    
    
}

