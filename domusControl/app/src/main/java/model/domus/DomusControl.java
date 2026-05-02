package model.domus;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import exceptions.CasaJaExisteException;
import exceptions.CasaNaoExisteException;
import exceptions.DispositivoNaoExisteException;
import exceptions.DivisaoNaoExisteException;
import exceptions.UtilizadorJaExisteException;
import model.automatismos.Automacao;
import model.automatismos.Escalonamento;
import model.casas.Casa;
import model.casas.Cenario;
import model.casas.Divisao;
import model.dispositivos.Dispositivo;
import model.utilizadores.Utilizador;

public class DomusControl implements Serializable{
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
        this.casas = casas.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public Map<String, Utilizador> getUtilizadores() {
        return new HashMap<>(this.utilizadores);
    }

    public void setUtilizadores(Map<String, Utilizador> utilizadores) {
        this.utilizadores = utilizadores.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
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

    public void adicionarCasa(Casa casa, String idUser) throws CasaJaExisteException {
        if (this.casas.containsKey(casa.getId()))
            throw new CasaJaExisteException(casa.getId());
        casa.adicionarUtilizador(this.utilizadores.get(idUser));
        this.casas.put(casa.getId(), casa.clone());
    }

    public void removerCasa(String id) throws CasaNaoExisteException {
        if (!this.casas.containsKey(id))
            throw new CasaNaoExisteException(id);
        this.casas.remove(id);
    }

    public boolean casaExiste(String id) {
        return this.casas.containsKey(id);
    }

    public void adicionarUtilizador(Utilizador utilizador) throws UtilizadorJaExisteException {
        if (this.utilizadores.containsKey(utilizador.getUsername()))
            throw new UtilizadorJaExisteException(utilizador.getUsername());
        this.utilizadores.put(utilizador.getUsername(), utilizador.clone());
    }

    public void removerUtilizador(String username) {
        this.utilizadores.remove(username);
        this.casas.values().forEach(casa -> casa.removerUtilizadorPorUsername(username));
    }

    public boolean existeUtilizador(String username, String password) {
        return this.utilizadores.containsKey(username) && this.utilizadores.get(username).getPassword().equals(password);
    }

    public void adicionarDivisao(String casaId, Divisao divisao) throws CasaNaoExisteException {
        if (!this.casas.containsKey(casaId))
            throw new CasaNaoExisteException(casaId);
        this.casas.get(casaId).adicionarDivisao(divisao);
    }

    public void removerDivisao(String casaId, String divisaoId) throws CasaNaoExisteException, DivisaoNaoExisteException {
        if (!this.casas.containsKey(casaId))
            throw new CasaNaoExisteException(casaId);
        if (this.casas.get(casaId).getDivisaoById(divisaoId) == null)
            throw new DivisaoNaoExisteException(divisaoId);
        this.casas.get(casaId).removerDivisao(divisaoId);
    }

    public boolean divisaoExiste (String casaId, String divisaoId) {
        return this.casas.get(casaId).getDivisoes().containsKey(divisaoId);
    }

    public void adicionarDispositivo(String casaId, String divisaoId, Dispositivo dispositivo) throws CasaNaoExisteException, DivisaoNaoExisteException {
        if (!this.casas.containsKey(casaId))
            throw new CasaNaoExisteException(casaId);
        if (this.casas.get(casaId).getDivisaoById(divisaoId) == null)
            throw new DivisaoNaoExisteException(divisaoId);
        this.casas.get(casaId).getDivisaoById(divisaoId).adicionarDispositivo(dispositivo);
    }

    public void removerDispositivo(String casaId, String divisaoId, String idDispositivo) throws CasaNaoExisteException, DivisaoNaoExisteException, DispositivoNaoExisteException {
        if (!this.casas.containsKey(casaId))
            throw new CasaNaoExisteException(casaId);
        if (this.casas.get(casaId).getDivisaoById(divisaoId) == null)
            throw new DivisaoNaoExisteException(divisaoId);
        if (!this.casas.get(casaId).getDivisaoById(divisaoId).getDispositivos().containsKey(idDispositivo))
            throw new DispositivoNaoExisteException(idDispositivo);
        this.casas.get(casaId).getDivisaoById(divisaoId).removerDispositivo(idDispositivo);
    }

    public boolean dispositivoExiste(String casaId, String divisaoId, String idDispositivo) {
        return this.casas.get(casaId).getDivisaoById(divisaoId).getDispositivos().containsKey(idDispositivo);
    }

    public Dispositivo getDispositivo(String casaId, String divisaoId, String idDispositivo) {
        if (!this.casas.containsKey(casaId)) return null;
        Divisao divisao = this.casas.get(casaId).getDivisaoById(divisaoId);
        if (divisao == null) return null;
        return divisao.getDispositivoById(idDispositivo);
    }

    public void setEstadoDispositivo(String casaId, String divisaoId, String idDispositivo, Dispositivo.Estado estado) {
        if (!this.casas.containsKey(casaId)) return;
        Divisao divisao = this.casas.get(casaId).getDivisaoById(divisaoId);
        if (divisao == null) return;
        divisao.setEstadoDispositivo(idDispositivo, estado);
    }

    public double getConsumoDivisao(String casaId, String divisaoId) {
        if (!this.casas.containsKey(casaId)) return 0.0;
        Divisao divisao = this.casas.get(casaId).getDivisaoById(divisaoId);
        if (divisao == null) return 0.0;
        return divisao.calcularConsumoDivisao();
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

    public void adicionarAutomacao(String casaId, Automacao automacao) {
        this.casas.get(casaId).adicionarAutomacao(automacao);
    }

    public void adicionarEscalonamento(String casaId, Escalonamento escalonamento) {
        this.casas.get(casaId).adicionarEscalonamento(escalonamento);
    }

    public void adicionarCenario(String casaId, Cenario cenario) {
        this.casas.get(casaId).adicionarCenario(cenario);
    }

    public void removerAutomacao(String casaId, String idAutomacao) {
        this.casas.get(casaId).removerAutomacaoPorId(idAutomacao);
    }

    public void removerEscalonamento(String casaId, String idEscalonamento) {
        this.casas.get(casaId).removerEscalonamentoPorId(idEscalonamento);
    }

    public void removerCenario(String casaId, String idCenario) {
        this.casas.get(casaId).removerCenarioPorNome(idCenario);
    }

    public void ativarCenario(String idCasa, Predicate<Cenario> filtro) {
        this.casas.get(idCasa).ativarCenarioPorFiltro(filtro);
    }

    public void executarAutomacoes(String idCasa, Predicate<Automacao> filtro) {
        this.casas.get(idCasa).executarAutomacoesPorFiltro(filtro);
    }

    public void executarEscalonamentos(String idCasa, Predicate<Escalonamento> filtro) {
        this.casas.get(idCasa).executarEscalonamentosPorFiltro(filtro);
    }

    public List<Cenario> getCenariosAtivos(String idCasa) {
        return this.casas.get(idCasa).getCenariosAtivos();
    }

    public List<Automacao> getAutomacoes(String idCasa) {
        return this.casas.get(idCasa).getAutomacoesAtivas();
    }

    public List<Escalonamento> getEscalonamentos(String idCasa) {
        return this.casas.get(idCasa).getEscalonamentos();
    }

    public List<Dispositivo> percorreDivisoesEfiltraDispositivos(String idCasa, List<String> idsDivisoes, String classe) {
        if (idsDivisoes == null || idsDivisoes.isEmpty()) {
            return this.casas.get(idCasa).getDivisoes().values().stream()
                .flatMap(divisao -> divisao.getDispositivos().values().stream())
                .filter(d -> d.getClass().getSimpleName().equalsIgnoreCase(classe))
                .toList();
        }
        return idsDivisoes.stream()
            .flatMap(idDivisao -> this.casas.get(idCasa).getDivisoes().get(idDivisao).getDispositivos().values().stream())
            .filter(d -> d.getClass().getSimpleName().equalsIgnoreCase(classe))
            .toList();
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