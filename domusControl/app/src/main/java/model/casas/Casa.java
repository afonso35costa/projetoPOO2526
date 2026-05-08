package model.casas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Comparator;
import java.util.stream.Collectors;

import model.automatismos.Automacao;
import model.automatismos.Escalonamento;
import model.dispositivos.Dispositivo;

import java.util.HashSet;

import model.utilizadores.Utilizador;

public class Casa implements Serializable{
    private String id;
    private String morada;
    private Map<String, Divisao> divisoes;
    private Set<Utilizador> utilizadores;
    private Set<String> admins;
    private List<Cenario> cenarios;
    private List<Automacao> automacoes;
    private List<Escalonamento> escalonamentos;

    public Casa(String id, String morada, Map<String, Divisao> divisoes, Set<Utilizador> utilizadores,
            List<Cenario> cenarios, List<Automacao> automacoes, List<Escalonamento> escalonamentos, Set<String> admins) {
        this.id = id;
        this.morada = morada;
        this.divisoes = divisoes.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.utilizadores = new HashSet<>(utilizadores);
        this.cenarios = cenarios.stream()
            .map(Cenario::clone)
            .collect(Collectors.toCollection(ArrayList::new));
        this.automacoes = new ArrayList<>(automacoes);
        this.escalonamentos = new ArrayList<>(escalonamentos);
        this.admins = new HashSet<>(admins);
    }

    public Casa() {
        this.id = "";
        this.morada = "";
        this.divisoes = new HashMap<>();
        this.utilizadores = new HashSet<>();
        this.cenarios = new ArrayList<>();
        this.automacoes = new ArrayList<>();
        this.escalonamentos = new ArrayList<>();
        this.admins = new HashSet<>();
    }

    public Casa(Casa outraCasa) {
        this.id = outraCasa.id;
        this.morada = outraCasa.morada;
        this.divisoes = outraCasa.divisoes.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
        this.utilizadores = new HashSet<>(outraCasa.utilizadores);
        this.cenarios = outraCasa.cenarios.stream()
            .map(Cenario::clone)
            .collect(Collectors.toCollection(ArrayList::new));
        this.automacoes = new ArrayList<>(outraCasa.automacoes);
        this.escalonamentos = new ArrayList<>(outraCasa.escalonamentos);
        this.admins = new HashSet<>(outraCasa.admins);
    }

    public String getId() {
        return id;
    }

    public String getMorada() {
        return morada;
    }

    public Map<String, Divisao> getDivisoes() {
        return this.divisoes.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public Set<Utilizador> getUtilizadores() {
        return new HashSet<>(this.utilizadores);
    }

    public List<Cenario> getCenarios() {
        return this.cenarios.stream()
            .map(Cenario::clone)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Automacao> getAutomacoes() {
        return new ArrayList<>(this.automacoes);
    }

    public List<Escalonamento> getEscalonamentos() {
        return new ArrayList<>(this.escalonamentos);
    }

    public Set<String> getAdmins() {
        return new HashSet<>(this.admins);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setDivisoes(Map<String, Divisao> divisoes) {
        this.divisoes = divisoes.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public void setUtilizadores(Set<Utilizador> utilizadores) {
        this.utilizadores = utilizadores.stream()
            .map(Utilizador::clone)
            .collect(Collectors.toCollection(HashSet::new));
    }

    public void setCenarios(List<Cenario> cenarios) {
        this.cenarios = cenarios.stream()
            .map(Cenario::clone)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public void setAutomacoes(List<Automacao> automacoes) {
        this.automacoes = new ArrayList<>(automacoes);
    }

    public void setEscalonamentos(List<Escalonamento> escalonamentos) {
        this.escalonamentos = new ArrayList<>(escalonamentos);
    }

    public void setAdmins(Set<String> admins) {
        this.admins = new HashSet<>(admins);
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
                ", admins=" + admins +
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
        if (!admins.equals(casa.admins)) return false;
        return automacoes.equals(casa.automacoes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, morada, divisoes, utilizadores, cenarios, automacoes, escalonamentos, admins);
    }

    public double calcularConsumoTotal() {
        return divisoes.values().stream()
            .mapToDouble(Divisao::calcularConsumoDivisao)
            .sum();
    }

    public void adicionarUtilizador(Utilizador utilizador) {
        this.utilizadores.add(utilizador.clone());
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

    public void adicionarAdmin(String username) {
        this.admins.add(username);
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

    public void removerAdmin(String username) {
        this.admins.remove(username);
    }

    public List<Dispositivo> dispositivosMaisUtilizados(Comparator<Dispositivo> comparator) {
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
            lista.add(new java.util.AbstractMap.SimpleEntry<>(this, divisao));
        }
        return lista;
    }

    public Divisao getDivisaoById(String id) {
        return this.divisoes.get(id);
    }

    public Automacao getAutomacaoById(String id) {
        return this.automacoes.stream()
            .filter(a -> a.getClass().getSimpleName().equalsIgnoreCase(id))
            .findFirst()
            .orElse(null);
    }

    public Escalonamento getEscalonamentoById(String id) {
        return this.escalonamentos.stream()
            .filter(e -> e.getId().equalsIgnoreCase(id))
            .findFirst()
            .orElse(null);
    }

    public Cenario getCenarioByNome(String nome) {
        return this.cenarios.stream()
            .filter(c -> c.getNome().equalsIgnoreCase(nome))
            .findFirst()
            .orElse(null);
    }

    public void removerAutomacaoPorId(String idAutomacao) {
        this.automacoes.removeIf(a -> a.getClass().getSimpleName().equalsIgnoreCase(idAutomacao));
    }

    public void removerEscalonamentoPorId(String idEscalonamento) {
        this.escalonamentos.removeIf(e -> e.getId().equalsIgnoreCase(idEscalonamento));
    }

    public void removerCenarioPorNome(String nomeCenario) {
        this.cenarios.removeIf(c -> c.getNome().equalsIgnoreCase(nomeCenario));
    }

    public void ativarCenarioPorFiltro(Predicate<Cenario> filtro) {
        this.cenarios.stream()
            .filter(filtro)
            .forEach(c -> { c.setAtivo(true); c.executar(this); });
    }

    public void executarAutomacoesPorFiltro(Predicate<Automacao> filtro) {
        this.automacoes.stream()
            .filter(filtro)
            .forEach(Automacao::executar);
    }

    public void executarEscalonamentosPorFiltro(Predicate<Escalonamento> filtro) {
        this.escalonamentos.stream()
            .filter(filtro)
            .forEach(e -> e.executar(this));
    }

    public void removerUtilizadorPorUsername(String username) {
        this.utilizadores.removeIf(u -> u.getUsername().equals(username));
    }

    public List<Cenario> getCenariosAtivos() {
        return this.cenarios.stream()
            .filter(Cenario::isAtivo)
            .map(Cenario::clone)
            .collect(Collectors.toList());
    }

    public List<Automacao> getAutomacoesAtivas() {
        return this.automacoes.stream()
            .filter(a -> a.verificar())
            .map(Automacao::clone)
            .collect(Collectors.toList());
    }

    public boolean isAdmin(String username) {
        return this.admins.contains(username);
    }

    public boolean casaTemDivisoes() {
        return !this.divisoes.isEmpty();
    }

     public boolean casaTemCenarios() {
        return !this.cenarios.isEmpty();
    }

     public boolean casaTemAutomacoes() {
        return !this.automacoes.isEmpty();
    }

     public boolean casaTemEscalonamentos() {
        return !this.escalonamentos.isEmpty();
    }

    public void alteraDispositivo(String idDivisao, String idDispositivo, Consumer<Dispositivo> acao) {
        Divisao divisao = this.divisoes.get(idDivisao);
        if (divisao != null) {
            divisao.alteraDispositivo(idDispositivo, acao);
        }
    }

    public void atuarSobreDispositivos(List<String> ids, Predicate<Dispositivo> condicao, Consumer<Dispositivo> acao) {
        this.divisoes.values().forEach(d -> d.atuarSobreDispositivos(ids, condicao, acao));
    }
}