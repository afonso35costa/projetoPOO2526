package view;

import controller.Controller;
import model.domus.DomusControl;
import java.lang.reflect.Method;

public class MainUI {
    public static void main(String[] args) {
        TextUI ui = new TextUI(carregarController());
        ui.run();
    }

    private static Controller carregarController() {
        Controller controller = new Controller();
        try {
            Class<?> testes = Class.forName("model.domus.Testes");
            Method metodo = testes.getMethod("criarSistemaTeste");
            DomusControl sistemaTeste = (DomusControl) metodo.invoke(null);
            controller.setSistema(sistemaTeste);
            System.out.println("Dados de teste carregados.");
        } catch (ClassNotFoundException e) {
            System.out.println("Dados de teste não encontrados — a arrancar vazio.");
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados de teste: " + e.getMessage());
        }
        return controller;
    }
}