package view;

import java.util.Scanner;

public class TextUI {
    private final Scanner sc;
    private final Menu menu;

    public TextUI() {
        this.sc = new Scanner(System.in);
        this.menu = new Menu("TextUI", new String[] {
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
        boolean autenticado = true //depois isto troca-se com o que estiver no controller!
        
        if (autenticado){
            System.out.println("\nLogin efetuado com sucesso!");
            //
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

        System.out.println("Registo feito!");
        //volta ao menu principal automaticamente//
    }

    //------------------MENU UTILIZADOR-----------------//

    private void menuUtilizador() {
        Menu menuUser = new Menu("Área do Utilizador", new String[]{
            "Ver casas",
            "Mexer em casas"            
        });

        menuUser.setHandler(1, this::verCasas);
        menuUser.setHandler(2, this::editarCasas);

        menuUser.run(); //quando sai, volta ao menu principal
    }

    //--------------------AÇÕES----------------------------//

    
    
}

