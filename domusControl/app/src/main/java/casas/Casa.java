package casas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Set;
import java.util.Comparator;

import automatismos.Automacao;
import automatismos.Escalonamento;
import dispositivos.Dispositivo;

import java.util.HashSet;

import utilizadores.Utilizador;

public class Casa{
    private String id;
    private String morada;
    private Map<String, Divisao> divisoes;
    private Set<Utilizador> utilizadores;
    private List<Cenario> cenarios;
    private List<Automacao> automacoes;
    private List<Escalonamento> escalonamentos;

    public Casa(String id, String morada, Map<String, Divisao> divisoes, Set<Utilizador> utilizadores,
            List<Cenario> cenarios, List<Automacao> automacoes, List<Escalonamento> escalonamentos) {
        this.id = id;
        this.morada = morada;
        this.divisoes = divisoes;
        this.utilizadores = utilizadores;
        this.cenarios = cenarios;
        this.automacoes = automacoes;
        this.escalonamentos = escalonamentos;
    }

    public Casa() {
        this.id = "";
        this.morada = "";
        this.divisoes = new HashMap<>();
        this.utilizadores = new HashSet<>();
        this.cenarios = new ArrayList<>();
        this.automacoes = new ArrayList<>();
        this.escalonamentos = new ArrayList<>();
    }

    public Casa(Casa outraCasa) {
        this.id = outraCasa.id;
        this.morada = outraCasa.morada;
        this.divisoes = new HashMap<>(outraCasa.divisoes);
        this.utilizadores = new HashSet<>(outraCasa.utilizadores);
        this.cenarios = new ArrayList<>(outraCasa.cenarios);
        this.automacoes = new ArrayList<>(outraCasa.automacoes);
        this.escalonamentos = new ArrayList<>(outraCasa.escalonamentos);
    }

    public String getId() {
        return id;
    }

    public String getMorada () {
        return morada;
    }

    public Map<String, Divisao> getDivisoes() {
        return new HashMap<>(divisoes);
    }

    public Set<Utilizador> getUtilizadores() {
        return utilizadores;
    }

    public List<Cenario> getCenarios() {
        return new ArrayList<>(cenarios);
    }

    public List<Automacao> getAutomacoes() {
        return new ArrayList<>(automacoes);
    }

    public List<Escalonamento> getEscalonamentos() {
        return new ArrayList<>(escalonamentos);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setDivisoes(Map<String, Divisao> divisoes) {
        this.divisoes = new HashMap<>(divisoes);
    }

    public void setUtilizadores(Set<Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public void setCenarios(List<Cenario> cenarios) {
        this.cenarios = new ArrayList<>(cenarios);
    }

    public void setAutomacoes(List<Automacao> automacoes) {
        this.automacoes = new ArrayList<>(automacoes);
    }

    public void setEscalonamentos(List<Escalonamento> escalonamentos) {
        this.escalonamentos = new ArrayList<>(escalonamentos);
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
                ", escalonamentos=" + escalonamentos +
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
        if (!escalonamentos.equals(casa.escalonamentos)) return false;
        return automacoes.equals(casa.automacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, morada, divisoes, utilizadores, cenarios, automacoes, escalonamentos);
    }

    public double calcularConsumoTotal() {
        return divisoes.values().stream()
            .mapToDouble(Divisao::calcularConsumoDivisao)
            .sum();
    }

    public void adicionarUtilizador(Utilizador utilizador) {
        this.utilizadores.add(utilizador);
    }

     public void adicionarCenario(Cenario cenario) {
        this.cenarios.add(cenario);
    }

    public void adicionarAutomacao(Automacao automacao) {
    this.automacoes.add(automacao);
    }

    public void adicionarDivisao(Divisao divisao) {
    this.divisoes.put(divisao.getId(), divisao);
    }

    public void adicionarEscalonamento(Escalonamento escalonamento) {
        this.escalonamentos.add(escalonamento);
    }

    public void removerUtilizador(Utilizador utilizador) {
        this.utilizadores.remove(utilizador);
    }

    public void removerCenario(Cenario cenario) {
        this.cenarios.remove(cenario);
    }

    public void removerAutomacao(Automacao automacao) {
        this.automacoes.remove(automacao);
    }

    public void removerDivisao(String idDivisao) {
        this.divisoes.remove(idDivisao);
    }

    public void removerEscalonamento(Escalonamento escalonamento) {
        this.escalonamentos.remove(escalonamento);
    }

    public List<Dispositivo> dispositivosMaisUtilizados(Comparator<Dispositivo> comparator){
        List<Dispositivo> lista = new ArrayList<>();
        for (Divisao divisao : divisoes.values()) {
            lista.addAll(divisao.getDispositivos().values());
        }
        lista.sort(comparator.reversed());
        return lista;
    }

    public List<Map.Entry<Casa, Divisao>> getDivisoesComCasa() {
        List<Map.Entry<Casa, Divisao>> lista = new ArrayList<>();
        for (Divisao divisao : this.divisoes.values()) {
            lista.add(new HashMap.SimpleEntry<>(this, divisao));
        }
        return lista;
    }

}