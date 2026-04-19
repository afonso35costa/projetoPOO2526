package casas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Set;

import automatismos.Automacao;

import java.util.HashSet;

import utilizadores.Utilizador;

public class Casa{
    private String id;
    private String morada;
    private Map<String, Divisao> divisoes;
    private Set<Utilizador> utilizadores;
    private List<Cenario> cenarios;
    private List<Automacao> automacoes;

    public Casa(String id, String morada, Map<String, Divisao> divisoes, Set<Utilizador> utilizadores,
            List<Cenario> cenarios, List<Automacao> automacoes) {
        this.id = id;
        this.morada = morada;
        this.divisoes = divisoes;
        this.utilizadores = utilizadores;
        this.cenarios = cenarios;
        this.automacoes = automacoes;
    }

    public Casa() {
        this.id = "";
        this.morada = "";
        this.divisoes = new HashMap<>();
        this.utilizadores = new HashSet<>();
        this.cenarios = new ArrayList<>();
        this.automacoes = new ArrayList<>();
    }

    public Casa(Casa outraCasa) {
        this.id = outraCasa.id;
        this.morada = outraCasa.morada;
        this.divisoes = new HashMap<>(outraCasa.divisoes);
        this.utilizadores = new HashSet<>(outraCasa.utilizadores);
        this.cenarios = new ArrayList<>(outraCasa.cenarios);
        this.automacoes = new ArrayList<>(outraCasa.automacoes);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMorada () {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public Map<String, Divisao> getDivisoes() {
        return divisoes;
    }

    public void setDivisoes(Map<String, Divisao> divisoes) {
        this.divisoes = divisoes;
    }

    public Set<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public void setUtilizadores(Set<Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public List<Cenario> getCenarios() {
        return cenarios;
    }

    public void setCenarios(List<Cenario> cenarios) {
        this.cenarios = cenarios;
    }

    public List<Automacao> getAutomacoes() {
        return automacoes;
    }

    public void setAutomacoes(List<Automacao> automacoes) {
        this.automacoes = automacoes;
    }

    public Casa clone() {
        return new Casa(this);
    }

     @Override
    public String toString() {
        return "Casa{" +
                "id='" + id + '\'' +
                ", morada='" + morada + '\'' +
                ", divisoes=" + divisoes +
                ", utilizadores=" + utilizadores +
                ", cenarios=" + cenarios +
                ", automacoes=" + automacoes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Casa casa = (Casa) o;

        if (!id.equals(casa.id)) return false;
        if (!morada.equals(casa.morada)) return false;
        if (!divisoes.equals(casa.divisoes)) return false;
        if (!utilizadores.equals(casa.utilizadores)) return false;
        if (!cenarios.equals(casa.cenarios)) return false;
        return automacoes.equals(casa.automacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, morada, divisoes, utilizadores, cenarios, automacoes);
    }

    public double calcularConsumoTotal() {
        return divisoes.values().stream()
            .mapToDouble(Divisao::calcularConsumoDivisao)
            .sum();
    }

}