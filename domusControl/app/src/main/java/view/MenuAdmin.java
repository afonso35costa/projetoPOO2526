package view;

import java.util.Scanner;

public class MenuAdmin {
    private final Scanner sc;
    private final Menu menu;

    public MenuAdmin(Scanner sc) {
        this.sc = sc;
        this.menu = new Menu("Menu Admin", new String[]{
            "Gerir Utilizadores",
            "Gerir Casas",
        }, this.sc);

        this.menu.setHandler(1, this::menuUtilizadores);
        this.menu.setHandler(2, this::menuCasas);
    }

    public void run() {
        this.menu.run();
    }
    
    private void menuUtilizadores() {
        System.out.println("\nGerir Utilizadores ainda não implementado.");
        System.out.print("Prima ENTER para voltar: ");
        this.sc.nextLine();
    }

    private void menuCasas() {
        System.out.println("\nGerir Casas ainda não implementado.");
        System.out.print("Prima ENTER para voltar: ");
        this.sc.nextLine();
    }
}
