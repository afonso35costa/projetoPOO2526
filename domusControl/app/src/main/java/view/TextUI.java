package view;

import java.util.Scanner;

public class TextUI {
    private final Scanner sc;
    private final Menu menu;

    public TextUI() {
        this.sc = new Scanner(System.in);
        this.menu = new Menu("TextUI", new String[] {
                "Login",
                "Sair"
        });

        this.menu.setHandler(1, this::login);
        this.menu.setHandler(2, () -> System.out.println("A sair..."));
    }

    public void run() {
        System.out.println("Aplicação iniciada.");
        this.menu.run();
    }

    private void login() {
        System.out.print("Utilizador: ");
        String user = this.sc.nextLine();
        System.out.print("Palavra-passe: ");
        String pass = this.sc.nextLine();
        // Lógica de login aqui
    }
}

