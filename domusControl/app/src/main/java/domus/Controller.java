package domus;

import java.util.Objects;

import casas.Casa;
import casas.Divisao;
import dispositivos.Dispositivo;
import utilizadores.Utilizador;

public class Controller {
    private DomusControl sistema;
    private String idUser;
    private String idCasa;
    private String idDivisao;

    public Controller(DomusControl sistema) {
        this.sistema = sistema;
    }

    public Controller() {
        this.sistema = new DomusControl();
    }

    public Controller(Controller c){
        this.sistema = c.sistema.clone();
    }

    public DomusControl getSistema() {
        return sistema;
    }

    public void setSistema(DomusControl sistema) {
        this.sistema = sistema;
    }

    public Controller clone(){
        return new Controller(this);
    }

    @Override
    public String toString() {
        return "Controller{" +
                "sistema=" + sistema +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Controller that = (Controller) o;

        return sistema.equals(that.sistema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sistema);
    }

    public void criaCasa(String nome, String morada){
        Casa c = new Casa();
        c.setId(nome);
        c.setMorada(morada);
        sistema.adicionarCasa(c, this.idUser);
    }

    public void removeCasa(String nome){
        sistema.removerCasa(nome);
    }

    public void criaUtilizador(String nome, String username, String isAdmin, String password){
        Utilizador u = new Utilizador();
        u.setNome(nome);
        u.setUsername(username);
        u.setAdmin(isAdmin.equalsIgnoreCase("sim"));
        u.setPassword(password);
        sistema.adicionarUtilizador(u);
    }

    public void loginUtilizador(String username, String password) {
        Utilizador u = sistema.getUtilizadores().get(username);
        if (u != null && u.getPassword().equals(password)) {
            this.idUser = username;
        } 
        // Ativar excecoes
    }

    public void removeUtilizador(String username){
        sistema.removerUtilizador(username);
    }

    public void criaDivisao(String nomeDivisao){
        Divisao d = new Divisao();
        d.setId(nomeDivisao);
        sistema.adicionarDivisao(this.idCasa, d);
    }

    public void removeDivisao(String nomeDivisao){
        sistema.removerDivisao(this.idCasa, nomeDivisao);
    }

    // Implemenar Factory de Dispositivos para poder instanciar o tipo

    public void criaDispositivo(String nomeDispositivo, String tipoDispositivo){
        Dispositivo d = ;
        sistema.adicionarDispositivo(this.idCasa, this.idDivisao, d);
    }
}