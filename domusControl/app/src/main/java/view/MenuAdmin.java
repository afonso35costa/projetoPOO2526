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
        });

        this.menu.setHandler(1, this::menuUtilizadores);
        this.menu.setHandler(2, this::menuCasas);
    }

    public void run() {
        this.menu.run();
    }
    
}
