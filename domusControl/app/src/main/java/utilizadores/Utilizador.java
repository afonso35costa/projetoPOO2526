package utilizadores;

import java.util.Objects;

public class Utilizador{
    private int id;
    private String nome;
    private boolean isAdmin;

    public Utilizador(int id, String nome, boolean isAdmin) {
        this.id = id;
        this.nome = nome;
        this.isAdmin = isAdmin;
    }

    public Utilizador() {
        this.id = 0;
        this.nome = "";
        this.isAdmin = false;
    }

     public Utilizador(Utilizador outroUtilizador) {
        this.id = outroUtilizador.id;
        this.nome = outroUtilizador.nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAdmin(boolean bool) {
        this.isAdmin = bool;
    }

    public Utilizador clone() {
        return new Utilizador(this);
    }

     @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + id +
                ", nome='" + nome +
                ", isAdmin= " + isAdmin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilizador that = (Utilizador) o;

        return this.id == that.id && this.nome.equals(that.nome) && this.isAdmin == that.isAdmin;
    }

     @Override
    public int hashCode() {
        return Objects.hash(id, nome, isAdmin);
    }

}