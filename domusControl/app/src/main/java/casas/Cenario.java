package casas;

import automatismos.Automacao;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;


public class Cenario {
    private String nome;
    private List<Automacao> automacoes;

    public Cenario(String nome, List<Automacao> automacoes) {
        this.nome = nome;
        this.automacoes = automacoes;
    }

    public Cenario() {
        this.nome = "";
        this.automacoes = new ArrayList<>();
    }

    public Cenario(Cenario outroCenario) {
        this.nome = outroCenario.nome;
        this.automacoes = new ArrayList<>(outroCenario.automacoes);
    }

    public String getNome() {
        return nome;
    }

    public List<Automacao> getAutomacoes() {
        return automacoes;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAutomacoes(List<Automacao> automacoes) {
        this.automacoes = automacoes;
    }

    public Cenario clone() {
        return new Cenario(this);
    }

    @Override
    public String toString() {
        return "Cenario{" +
                "nome='" + nome + '\'' +
                ", automacoes=" + automacoes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cenario cenario = (Cenario) o;

        if (!nome.equals(cenario.nome)) return false;
        return automacoes.equals(cenario.automacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, automacoes);
    }
}