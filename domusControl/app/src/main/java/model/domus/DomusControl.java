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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import exceptions.AutomacaoJaExisteException;
import exceptions.CasaJaExisteException;
import exceptions.CasaNaoExisteException;
import exceptions.CenarioJaExisteException;
import exceptions.DispositivoNaoExisteException;
import exceptions.DivisaoNaoExisteException;
import exceptions.EscalonamentoJaExisteException;
import exceptions.UtilizadorJaExisteException;
import exceptions.UtilizadorNaoExisteException;
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
        this.casas = sistema.casas.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
         this.utilizadores = sistema.utilizadores.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
}
//      this.casas = new HashMap<>(sistema.casas);
//      this.utilizadores = new HashMap<>(sistema.utilizadores);


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

    public void removerUtilizador(String username) throws UtilizadorNaoExisteException {
        if (!this.utilizadores.containsKey(username))
            throw new UtilizadorNaoExisteException(username);
        this.utilizadores.remove(username);
    }

    public void desassociarUtilizador(String username, String idCasa) throws CasaNaoExisteException, UtilizadorNaoExisteException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaNaoExisteException(idCasa);
        this.casas.get(idCasa).removerUtilizadorPorUsername(username);
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

    public Casa casaQueMaisConsome(String idUser) {
        Comparator<Casa> comparador = (c1, c2) -> Double.compare(c1.calcularConsumoTotal(), c2.calcularConsumoTotal());
        return this.getCasasDoUtilizador(idUser).stream()
            .max(comparador)
            .orElse(null);
    }

    public List<Dispositivo> tresDispositivosMaisUtilizados(Casa casa, Comparator<Dispositivo> comparator){
        return casa.dispositivosMaisUtilizados(comparator).stream().limit(3).toList();
    }

    public List<Map.Entry<Casa, Divisao>> tresDivisoesComMaisDispositivos(String idUser) {
        List<Map.Entry<Casa, Divisao>> lista = new ArrayList<>();
        for (Casa casa : this.getCasasDoUtilizador(idUser)) {
            lista.addAll(casa.getDivisoesComCasa());
        }
        lista.sort(Comparator.comparingInt((Map.Entry<Casa, Divisao> e) -> e.getValue().getNumDispositivos()).reversed());
        return lista.stream().limit(3).toList();
    }

    public void adicionarAutomacao(String casaId, Automacao automacao) throws AutomacaoJaExisteException {
        Casa casa = this.casas.get(casaId);
        Automacao a = casa.getAutomacaoById(automacao.getClass().getSimpleName());
        if (a != null && a.equals(automacao)) {
            throw new AutomacaoJaExisteException(casaId);
        } else {
            casa.adicionarAutomacao(automacao);
        }
    }

    public void adicionarEscalonamento(String casaId, Escalonamento escalonamento) throws EscalonamentoJaExisteException {
        Casa casa = this.casas.get(casaId);
        Escalonamento e = casa.getEscalonamentoById(escalonamento.getId());
        if (e != null && e.getDispositivos().equals(escalonamento.getDispositivos()) && e.getHora().equals(escalonamento.getHora())) {
            throw new EscalonamentoJaExisteException(casaId);
        } else {
            casa.adicionarEscalonamento(escalonamento);
        }
    }

    public void adicionarCenario(String casaId, Cenario cenario) throws CenarioJaExisteException {
        Casa casa = this.casas.get(casaId);
        Cenario c = casa.getCenarioByNome(cenario.getNome());
        if (c != null && c.equals(cenario)) {
            throw new CenarioJaExisteException(casaId);
        } else {
            casa.adicionarCenario(cenario);
        }
    }

    public void removerAutomacao(String casaId, String idAutomacao) throws CasaNaoExisteException, exceptions.AutomacaoNaoExisteException {
        if (!this.casas.containsKey(casaId))
            throw new CasaNaoExisteException(casaId);
        Casa casa = this.casas.get(casaId);
        Automacao a = casa.getAutomacaoById(idAutomacao);
        if (a == null)
            throw new exceptions.AutomacaoNaoExisteException(idAutomacao);
        casa.removerAutomacaoPorId(idAutomacao);
    }

    public void removerEscalonamento(String casaId, String idEscalonamento) throws CasaNaoExisteException, exceptions.EscalonamentoNaoExisteException {
        if (!this.casas.containsKey(casaId))
            throw new CasaNaoExisteException(casaId);
        Casa casa = this.casas.get(casaId);
        Escalonamento e = casa.getEscalonamentoById(idEscalonamento);
        if (e == null)
            throw new exceptions.EscalonamentoNaoExisteException(idEscalonamento);
        casa.removerEscalonamentoPorId(idEscalonamento);
    }

    public void removerCenario(String casaId, String idCenario) throws CasaNaoExisteException, exceptions.CenarioNaoExisteException {
        if (!this.casas.containsKey(casaId))
            throw new CasaNaoExisteException(casaId);
        Casa casa = this.casas.get(casaId);
        Cenario c = casa.getCenarioByNome(idCenario);
        if (c == null)
            throw new exceptions.CenarioNaoExisteException(idCenario);
        casa.removerCenarioPorNome(idCenario);
    }

    public void ativarCenario(String idCasa, Predicate<Cenario> filtro) throws CasaNaoExisteException, exceptions.CenarioNaoExisteException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaNaoExisteException(idCasa);
        Casa casa = this.casas.get(idCasa);
        boolean any = casa.getCenarios().stream().anyMatch(filtro);
        if (!any)
            throw new exceptions.CenarioNaoExisteException("Nenhum cenário corresponde ao filtro");
        casa.ativarCenarioPorFiltro(filtro);
    }

    public void executarAutomacoes(String idCasa, Predicate<Automacao> filtro) throws CasaNaoExisteException, exceptions.AutomacaoNaoExisteException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaNaoExisteException(idCasa);
        Casa casa = this.casas.get(idCasa);
        casa.executarAutomacoesPorFiltro(filtro);
    }

    public void executarEscalonamentos(String idCasa, Predicate<Escalonamento> filtro) throws CasaNaoExisteException, exceptions.EscalonamentoNaoExisteException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaNaoExisteException(idCasa);
        Casa casa = this.casas.get(idCasa);
        boolean any = casa.getEscalonamentos().stream().anyMatch(filtro);
        if (!any)
            throw new exceptions.EscalonamentoNaoExisteException("Nenhum escalonamento corresponde ao filtro");
        casa.executarEscalonamentosPorFiltro(filtro);
    }

    public List<Cenario> getCenariosAtivos(String idCasa) {
        return this.casas.get(idCasa).getCenariosAtivos();
    }

    public List<Cenario> getCenarios(String idCasa) {
        return this.casas.get(idCasa).getCenarios();
    }

    public List<Automacao> getAutomacoes(String idCasa) {
        return this.casas.get(idCasa).getAutomacoes();
    }

    public List<Escalonamento> getEscalonamentos(String idCasa) {
        return this.casas.get(idCasa).getEscalonamentos();
    }
    
    public Automacao getAutomacao(String idCasa, String idAutomacao) {
        return this.casas.get(idCasa).getAutomacaoById(idAutomacao);
    }

    public Escalonamento getEscalonamento(String idCasa, String idEscalonamento) {
        return this.casas.get(idCasa).getEscalonamentoById(idEscalonamento);
    }

    public Cenario getCenario(String idCasa, String nomeCenario) {
        return this.casas.get(idCasa).getCenarioByNome(nomeCenario);
    }

    public List<Dispositivo> percorreDivisoesEfiltraDispositivos(String idCasa, List<String> idsDivisoes, String classe) throws CasaNaoExisteException, DivisaoNaoExisteException {
        if (!this.casas.containsKey(idCasa)) {
            throw new CasaNaoExisteException(idCasa);
        }
        Casa casa = this.casas.get(idCasa);
        if (idsDivisoes == null || idsDivisoes.isEmpty()) {       // explicar decisoes de arquitetura
            return casa.getDivisoes().values().stream()
            .flatMap(divisao -> divisao.getDispositivos().values().stream())
            .filter(d -> d.getClass().getSimpleName().equalsIgnoreCase(classe))
            .toList();
        }

        // lança DivisaoNaoExisteException se alguma das divisões não existir
        for (String idDivisao : idsDivisoes) {
            if (!casa.getDivisoes().containsKey(idDivisao))
            throw new DivisaoNaoExisteException(idDivisao);
        }

        return idsDivisoes.stream()
            .flatMap(idDivisao -> casa.getDivisoes().get(idDivisao).getDispositivos().values().stream())
            .filter(d -> d.getClass().getSimpleName().equalsIgnoreCase(classe))
            .toList();
    }

    public void guardarEstado(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }

    public DomusControl carregarEstado (String filename) throws IOException, ClassNotFoundException{
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


    // Necessário no Controller.associarUtilizadorACasa()
    public void associarUtilizadorACasa(String nomeCasa, String username) 
            throws CasaNaoExisteException, UtilizadorNaoExisteException {
        if (!this.casas.containsKey(nomeCasa))
            throw new CasaNaoExisteException(nomeCasa);
        if (!this.utilizadores.containsKey(username))
            throw new UtilizadorNaoExisteException(username);
        this.casas.get(nomeCasa).adicionarUtilizador(this.utilizadores.get(username));
    }

    // Necessário no Controller.associarUtilizadorACasa() e no MenuAdmin
    public boolean existeUtilizador(String username) {
        return this.utilizadores.containsKey(username);
    }

    // Necessário no top3DispositivosPorTempoLigado() e top3DispositivosPorAtivacoes()
    // que no Controller chamam casaQueMaisConsome() mas deviam percorrer TODAS as casas
    public List<Dispositivo> tresDispositivosMaisUtilizadosGlobal(String idUser,
            Comparator<Dispositivo> comparator) {
        return this.getCasasDoUtilizador(idUser).stream()
                .flatMap(casa -> casa.getDivisoes().values().stream())
                .flatMap(divisao -> divisao.getDispositivos().values().stream())
                .sorted(comparator.reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Casa> getCasasDoUtilizador(String idUser) {
        return this.casas.values().stream()
            .filter(c -> c.getUtilizadores().stream()
                .anyMatch(u -> u.getUsername().equals(idUser)))
            .toList();
    }

    public boolean utilizadorAssociadoACasas(String idUser) {
        return this.casas.values().stream()
            .anyMatch(c -> c.getUtilizadores().stream()
                .anyMatch(u -> u.getUsername().equals(idUser)));
    }

    public boolean utilizadorAssociadoACasa(String idUser, String idCasa) {
        if (idUser == null || idUser.isEmpty()) return false;
        if (idCasa == null || idCasa.isEmpty()) return false;
        return this.casas.get(idCasa).getUtilizadores().stream()
            .anyMatch(u -> u.getUsername().equals(idUser));
    }

    public boolean utilizadorIsAdmin(String idUser, String idCasa) {
        if (idUser == null || idUser.isEmpty()) return false;
        if (idCasa == null || idCasa.isEmpty()) return false;
        return this.casas.get(idCasa).isAdmin(idUser);
    }

    public Set<Utilizador> listarUtilizadoresCasa (String idCasa) {
        if (idCasa == null || idCasa.isEmpty()) return new HashSet<>();
        return new HashSet<>(this.casas.get(idCasa).getUtilizadores());
    }

    public void addAdminCasa(String idCasa, String idUser) throws CasaNaoExisteException, UtilizadorNaoExisteException {
        if (!this.casas.containsKey(idCasa))
            throw new CasaNaoExisteException(idCasa);
        if (!this.utilizadores.containsKey(idUser))
            throw new UtilizadorNaoExisteException(idUser);
        this.casas.get(idCasa).adicionarAdmin(idUser);
    }

    public void removerAdminCasa(String idCasa, String idUser) throws CasaNaoExisteException, UtilizadorNaoExisteException {
    if (!this.casas.containsKey(idCasa))
        throw new CasaNaoExisteException(idCasa);
    if (!this.utilizadores.containsKey(idUser))
        throw new UtilizadorNaoExisteException(idUser);
    this.casas.get(idCasa).removerAdmin(idUser);
    }

    public boolean casaTemDivisoes(String idCasa) {
        if (idCasa == null || idCasa.isEmpty()) return false;
        return !this.casas.get(idCasa).casaTemDivisoes();
    }

    public boolean casaTemCenarios(String idCasa) {
        if (idCasa == null || idCasa.isEmpty()) return false;
        return !this.casas.get(idCasa).casaTemCenarios();
    }

    public boolean casaTemAutomacoes(String idCasa) {
        if (idCasa == null || idCasa.isEmpty()) return false;
        return !this.casas.get(idCasa).casaTemAutomacoes();
    }

    public boolean casaTemEscalonamentos(String idCasa) {
        if (idCasa == null || idCasa.isEmpty()) return false;
        return !this.casas.get(idCasa).casaTemEscalonamentos();
    }

    public void alteraDispositivo(String idCasa, String idDivisao, String idDispositivo, Consumer<Dispositivo> acao) {
        if (!this.casas.containsKey(idCasa)) return;
        Casa casa = this.casas.get(idCasa);
        if (casa != null) {
            casa.alteraDispositivo(idDivisao, idDispositivo, acao);
        }
    }

    public List<String> percorreDivisoesEfiltraDispositivosIds(String idCasa, List<String> idsDivisoes, String classe) throws CasaNaoExisteException, DivisaoNaoExisteException {
        return percorreDivisoesEfiltraDispositivos(idCasa, idsDivisoes, classe).stream()
            .map(Dispositivo::getIdentificador)
            .toList();
    }
}