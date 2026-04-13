package utilizadores;

import java.util.Objects;

public class Utilizador{
    private int id;
    private String nome;

    public Utilizador(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Utilizador() {
        this.id = 0;
        this.nome = "";
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Utilizador clone() {
        return new Utilizador(this.id, this.nome);
    }

     @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilizador that = (Utilizador) o;

        return this.id == that.id && this.nome.equals(that.nome);
    }

     @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }

}