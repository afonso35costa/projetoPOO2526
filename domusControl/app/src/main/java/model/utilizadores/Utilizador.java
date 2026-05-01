package model.utilizadores;

import java.util.Objects;

public class Utilizador{
    private String username;
    private String nome;
    private boolean isAdmin;
    private String password;

    public Utilizador(String username, String nome, boolean isAdmin, String password) {
        this.username = username;
        this.nome = nome;
        this.isAdmin = isAdmin;
        this.password = password;
    }

    public Utilizador() {
        this.username = "";
        this.nome = "";
        this.isAdmin = false;
        this.password = "";
    }

     public Utilizador(Utilizador outroUtilizador) {
        this.username = outroUtilizador.username;
        this.nome = outroUtilizador.nome;
        this.isAdmin = outroUtilizador.isAdmin;
        this.password = outroUtilizador.password;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAdmin(boolean bool) {
        this.isAdmin = bool;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Utilizador clone() {
        return new Utilizador(this);
    }

     @Override
    public String toString() {
        return "Utilizador{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilizador that = (Utilizador) o;

        return this.username.equals(that.username) && this.nome.equals(that.nome) && this.isAdmin == that.isAdmin && this.password.equals(that.password);
    }

     @Override
    public int hashCode() {
        return Objects.hash(username, nome, isAdmin, password);
    }

}