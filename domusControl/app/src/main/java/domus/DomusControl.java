package domus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import automatismos.Automacao;
import automatismos.Escalonamento;
import casas.Casa;
import casas.Cenario;
import casas.Divisao;
import dispositivos.Dispositivo;
import utilizadores.Utilizador;

public class DomusControl {
    private Map<String, Casa> casas;
    private Map<String, Utilizador> utilizadores;

    public DomusControl(DomusControl sistema) {
        this.casas = new HashMap<>(sistema.casas);
        this.utilizadores = new HashMap<>(sistema.utilizadores);
    }

    public DomusControl(Map<String, Casa> casas, Map<String, Utilizador> utilizadores) {
        this.casas = casas;
        this.utilizadores = utilizadores;
    }

    public DomusControl() {
        this.casas = new HashMap<>();
        this.utilizadores = new HashMap<>();
    }

    public Map<String, Casa> getCasas() {
        return new HashMap<>(this.casas);
    }

    public void setCasas(Map<String, Casa> casas) {
        this.casas = casas;
    }

    public Map<String, Utilizador> getUtilizadores() {
        return new HashMap<>(this.utilizadores);
    }

    public void setUtilizadores(Map<String, Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public DomusControl clone() {
        return new DomusControl(this);
    }

    @Override
    public String toString() {
        return "Sistema{" +
                "casas=" + casas.toString() +
                ", utilizadores=" + utilizadores.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o.getClass() != this.getClass()) return false;
        DomusControl sistema = (DomusControl) o;
        return casas.equals(sistema.casas) && utilizadores.equals(sistema.utilizadores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(casas, utilizadores);
    }

    public void adicionarCasa(Casa casa, String idUser) {
        casa.adicionarUtilizador(this.utilizadores.get(idUser));
        this.casas.put(casa.getId(), casa);
    }

    public void removerCasa(String id) {
        this.casas.remove(id);
    }

    public void adicionarUtilizador(Utilizador utilizador) {
        this.utilizadores.put(utilizador.getUsername(), utilizador);
    }

    public void removerUtilizador(String username) {
        this.utilizadores.remove(username);
        this.casas.values().forEach(casa -> casa.getUtilizadores().removeIf(utilizador -> utilizador.getUsername().equals(username)));
    }

    public void adicionarDivisao (String casaId, Divisao divisao) {
        this.casas.get(casaId).adicionarDivisao(divisao);
    }

    public void removerDivisao (String casaId, String divisaoId) {
        this.casas.get(casaId).removerDivisao(divisaoId);
    }

    public void adicionarDispositivo (String casaId, String divisaoId, Dispositivo dispositivo) {
        this.casas.get(casaId).getDivisoes().get(divisaoId).adicionarDispositivo(dispositivo);
    }

    public void removerDispositivo (String casaId, String divisaoId, Dispositivo dispositivo) {
        this.casas.get(casaId).getDivisoes().get(divisaoId).removerDispositivo(dispositivo);
    }

    public Casa casaQueMaisConsome() {
        Comparator<Casa> comparador = (c1, c2) -> Double.compare(c1.calcularConsumoTotal(), c2.calcularConsumoTotal());
        return this.casas.values().stream()
            .max(comparador)
            .orElse(null);
    }

    public List<Dispositivo> tresDispositivosMaisUtilizados(Casa casa, Comparator<Dispositivo> comparator){
        return casa.dispositivosMaisUtilizados(comparator).stream().limit(3).toList();
    }

    public List<Map.Entry<Casa, Divisao>> tresDivisoesComMaisDispositivos() {
        List<Map.Entry<Casa, Divisao>> lista = new ArrayList<>();
        for (Casa casa : this.casas.values()) {
            lista.addAll(casa.getDivisoesComCasa());
        }
        lista.sort(Comparator.comparingInt((Map.Entry<Casa, Divisao> e) -> e.getValue().getNumDispositivos()).reversed());
        return lista.stream().limit(3).toList();
    }

    public void executarAutomacoes(Casa casa, Predicate<Automacao> filtro) {
        casa.getAutomacoes().stream()
            .filter(filtro)
            .forEach(Automacao::executar);
    }

    public void executarEscalonamentos(Casa casa, Predicate<Escalonamento> filtro) {
        casa.getEscalonamentos().stream()
            .filter(filtro)
            .forEach(Escalonamento::executar);
    }

    public void ativarCenario(Casa casa, Predicate<Cenario> filtro) {
        casa.getCenarios().stream()
            .filter(filtro)
            .forEach(c -> { c.setAtivo(true); c.executar(); });
    }

    public void writeToFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public DomusControl readFromFile (String filename) throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        DomusControl domus = null;
        Object obj = ois.readObject();
        if (obj instanceof DomusControl) {
            domus = (DomusControl) obj;
        }
        ois.close();
        fis.close();
        return domus;
    }

}