package domus;

import java.util.Objects;

import automatismos.Automacao;
import automatismos.AutomacaoFactory;
import casas.Casa;
import casas.Divisao;
import dispositivos.Dispositivo;
import dispositivos.DispositivoFactory;
import utilizadores.Utilizador;

public class Controller {
    private DomusControl sistema;
    private String idUser;
    private String idCasa;
    private String idDivisao;
    private String idDispositivo;

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
        return sistema.clone();
    }

    public void setSistema(DomusControl sistema) {
        this.sistema = sistema.clone();
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

    public void selecionaCasa(String nome){
        this.idCasa = this.sistema.getCasas().containsKey(nome) ? nome : null;
        // Lançar excecao se a casa nao existir
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
        if (sistema.existeUtilizador(username, password)) {
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

    public void selecionaDivisao(String nomeDivisao){
        this.idDivisao = this.sistema.divisaoExiste(this.idCasa, nomeDivisao) ? nomeDivisao : null;
        // Lancar excecao se a divisao nao existir
    }

    public void criaDispositivo(String tipoDispositivo, String marca, String modelo, String consumoPorHora){
        Dispositivo d = DispositivoFactory.criarDispositivo(tipoDispositivo);
        d.setIdentificador(tipoDispositivo + "_" + marca + "_" + modelo);
        d.setMarca(marca);
        d.setModelo(modelo);
        d.setConsumoPorHora(Double.parseDouble(consumoPorHora));
        sistema.adicionarDispositivo(this.idCasa, this.idDivisao, d);
    }

    public void removeDispositivo(String nomeDispositivo){
        sistema.removerDispositivo(this.idCasa, this.idDivisao, nomeDispositivo);
    }

    public void selecionaDispositivo(String nomeDispositivo){
        this.idDispositivo = this.sistema.dispositivoExiste(this.idCasa, this.idDivisao, nomeDispositivo) ? nomeDispositivo : null;
        // Lancar excecao se o dispositivo nao existir
    }

    public void criaAutomacao(String nomeAutomacao) {
        Automacao a = AutomacaoFactory.criarAutomacao(nomeAutomacao);
        sistema.adicionarAutomacao(this.idCasa, a);
    }
}