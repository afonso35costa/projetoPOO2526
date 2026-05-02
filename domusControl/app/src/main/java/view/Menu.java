package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public interface Handler {
        void execute();
    }

    public interface PreCondition {
        boolean validate();
    }

    private final Scanner is;

    private final String titulo;
    private final List<String> opcoes;
    private final List<PreCondition> disponivel;
    private final List<Handler> handlers;

    public Menu(String[] opcoes) {
        this("Menu", opcoes, new Scanner(System.in));
    }

    public Menu(String titulo, String[] opcoes) {
        this(titulo, opcoes, new Scanner(System.in));
    }

    public Menu(String titulo, String[] opcoes, Scanner scanner) {
        this.titulo = titulo;
        this.opcoes = Arrays.asList(opcoes);
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
        this.is = scanner;

        this.opcoes.forEach(s -> {
            this.disponivel.add(() -> true);
            this.handlers.add(() -> System.out.println("\nATENÇÃO: Opção não implementada!"));
        });
    }

    public void run() {
        int op;
        do {
            show();
            op = readOption();
            if (op > 0 && !this.disponivel.get(op - 1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op > 0) {
                this.handlers.get(op - 1).execute();
            }
        } while (op != 0);
    }

    public void setPreCondition(int i, PreCondition b) {
        this.disponivel.set(i - 1, b);
    }

    public void setHandler(int i, Handler h) {
        this.handlers.set(i - 1, h);
    }

    private void show() {
        System.out.println("\n *** " + this.titulo + " *** ");
        for (int i = 0; i < this.opcoes.size(); i++) {
            System.out.print(i + 1);
            System.out.print(" - ");
            System.out.println(this.disponivel.get(i).validate() ? this.opcoes.get(i) : "---");
        }
        System.out.println("0 - Sair");
    }

    private int readOption() {
        int op;

        System.out.print("Opção: ");
        try {
            String line = is.nextLine();
            op = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            op = -1;
        }

        if (op < 0 || op > this.opcoes.size()) {
            System.out.println("Opção Inválida!!!");
            op = -1;
        }
        return op;
    }
}