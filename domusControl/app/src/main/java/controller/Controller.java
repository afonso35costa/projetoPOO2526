package controller;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import exceptions.CasaJaExisteException;
import exceptions.CasaNaoExisteException;
import exceptions.CasaNaoSelecionadaException;
import exceptions.CredenciaisInvalidasException;
import exceptions.DispositivoNaoExisteException;
import exceptions.DivisaoNaoExisteException;
import exceptions.DivisaoNaoSelecionadaException;
import exceptions.SessaoNaoIniciadaException;
import exceptions.UtilizadorJaExisteException;
import exceptions.UtilizadorNaoExisteException;
import model.automatismos.Automacao;
import model.automatismos.AutomacaoFactory;
import model.automatismos.Escalonamento;
import model.casas.Casa;
import model.casas.Cenario;
import model.casas.Divisao;
import model.casas.EstrategiaCenario;
import model.dispositivos.ColunaDeSom;
import model.dispositivos.Cortina;
import model.dispositivos.Dispositivo;
import model.dispositivos.DispositivoFactory;
import model.dispositivos.Lampada;
import model.dispositivos.LampadaCor;
import model.dispositivos.Televisao;
import model.domus.DomusControl;
import model.utilizadores.Utilizador;

public class Controller {
    private DomusControl sistema;
    private String idUser;
    private String idCasa;
    private String idDivisao;
    private String idDispositivo;

    public Controller(DomusControl sistema, String idUser, String idCasa, String idDivisao, String idDispositivo) {
        this.sistema = sistema;
        this.idUser = idUser;
        this.idCasa = idCasa;
        this.idDivisao = idDivisao;
        this.idDispositivo = idDispositivo;
    }

    public Controller() {
        this.sistema = new DomusControl();
        this.idUser = "";
        this.idCasa = "";
        this.idDivisao = "";
        this.idDispositivo = "";
    }

    public Controller(Controller c){
        this.sistema = c.sistema.clone();
            this.idUser = c.idUser;
            this.idCasa = c.idCasa;
            this.idDivisao = c.idDivisao;
            this.idDispositivo = c.idDispositivo;
    }

    public DomusControl getSistema() {
        return sistema.clone();
    }

    public void setSistema(DomusControl sistema) {
        this.sistema = sistema.clone();
    }

    public Controller clone(){
        return new Controller(this);
    }

    @Override
    public String toString() {
        return "Controller{" +
                "sistema=" + sistema +
                ", idUser='" + idUser + '\'' +
                ", idCasa='" + idCasa + '\'' +
                ", idDivisao='" + idDivisao + '\'' +
                ", idDispositivo='" + idDispositivo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Controller that = (Controller) o;

        return sistema.equals(that.sistema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sistema);
    }

    public void criaCasa(String nome, String morada) throws SessaoNaoIniciadaException, CasaJaExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        Casa c = new Casa();
        c.setId(nome);
        c.setMorada(morada);
        sistema.adicionarCasa(c, this.idUser);
    }

    public void removeCasa(String nome) throws SessaoNaoIniciadaException, CasaNaoExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        sistema.removerCasa(nome);
    }

    public void selecionaCasa(String nome) throws CasaNaoExisteException {
        if (!sistema.casaExiste(nome))
            throw new CasaNaoExisteException(nome);
        this.idCasa = nome;
    }

    public void criaUtilizador(String nome, String username, String isAdmin, String password) throws UtilizadorJaExisteException{
        Utilizador u = new Utilizador();
        u.setNome(nome);
        u.setUsername(username);
        u.setAdmin(isAdmin.equalsIgnoreCase("sim"));
        u.setPassword(password);
        sistema.adicionarUtilizador(u);
    }

    public void loginUtilizador(String username, String password) throws CredenciaisInvalidasException {
        if (!sistema.existeUtilizador(username, password))
            throw new CredenciaisInvalidasException();
        this.idUser = username;
    }
    //ver se é assim estes metodos.

    public boolean utilizadorTemCasas() {
        if (this.idUser == null || this.idUser.isEmpty()) return false;
        return this.sistema.getCasas().values().stream()
                .anyMatch(c -> c.getUtilizadores().stream()
                        .anyMatch(u -> u.getUsername().equals(this.idUser)));
    }

    public Casa casaQueMaisConsome() {
        return this.sistema.casaQueMaisConsome();
    }

    public List<Dispositivo> top3DispositivosPorTempoLigado() {
        Casa casa = this.sistema.casaQueMaisConsome();
        if (casa == null) return List.of();
        return this.sistema.tresDispositivosMaisUtilizados(casa, Comparator.comparingInt(Dispositivo::getMinutosLigado));
    }

    public List<Dispositivo> top3DispositivosPorAtivacoes() {
        Casa casa = this.sistema.casaQueMaisConsome();
        if (casa == null) return List.of();
        return this.sistema.tresDispositivosMaisUtilizados(casa, Comparator.comparingInt(Dispositivo::getNumActivacoes));
    }

    public List<Map.Entry<Casa, Divisao>> top3DivisoesComMaisDispositivos() {
        return this.sistema.tresDivisoesComMaisDispositivos();
    }

    public String getDescricaoDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        Dispositivo dispositivo = this.sistema.getDispositivo(nomeCasa, nomeDivisao, idDisp);
        if (dispositivo == null) return "Dispositivo inexistente";
        return dispositivo.getClass().getSimpleName() + " [" + dispositivo.getIdentificador() + "]";
    }

    public boolean isDispositivoLigado(String nomeCasa, String nomeDivisao, String idDisp) {
        Dispositivo dispositivo = this.sistema.getDispositivo(nomeCasa, nomeDivisao, idDisp);
        if (dispositivo == null) return false;
        return dispositivo.getEstado() == Dispositivo.Estado.LIGADO || dispositivo.getEstado() == Dispositivo.Estado.ABERTO;
    }

    public void ligarDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        Dispositivo dispositivo = this.sistema.getDispositivo(nomeCasa, nomeDivisao, idDisp);
        if (dispositivo == null) return;
        Dispositivo.Estado novoEstado = dispositivo.getEstado() == Dispositivo.Estado.FECHADO
                ? Dispositivo.Estado.ABERTO
                : Dispositivo.Estado.LIGADO;
        this.sistema.setEstadoDispositivo(nomeCasa, nomeDivisao, idDisp, novoEstado);
    }

    public void desligarDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        Dispositivo dispositivo = this.sistema.getDispositivo(nomeCasa, nomeDivisao, idDisp);
        if (dispositivo == null) return;
        Dispositivo.Estado novoEstado = dispositivo.getEstado() == Dispositivo.Estado.ABERTO
                ? Dispositivo.Estado.FECHADO
                : Dispositivo.Estado.DESLIGADO;
        this.sistema.setEstadoDispositivo(nomeCasa, nomeDivisao, idDisp, novoEstado);
    }

    public String getEstadoDispositivo(String nomeCasa, String nomeDivisao, String idDisp) {
        Dispositivo dispositivo = this.sistema.getDispositivo(nomeCasa, nomeDivisao, idDisp);
        if (dispositivo == null) return "Dispositivo inexistente";
        return dispositivo.getEstado().name();
    }

    public double getConsumoDivisao(String nomeCasa, String nomeDivisao) {
        return this.sistema.getConsumoDivisao(nomeCasa, nomeDivisao);
    }

    //ate aqui

    
    public void removeUtilizador(String username) throws SessaoNaoIniciadaException, UtilizadorNaoExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        sistema.removerUtilizador(username);
    }

    public void criaDivisao(String nomeDivisao) throws SessaoNaoIniciadaException, CasaNaoSelecionadaException, CasaNaoExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        if (this.idCasa == null || this.idCasa.isEmpty())
            throw new CasaNaoSelecionadaException();
        Divisao d = new Divisao();
        d.setId(nomeDivisao);
        sistema.adicionarDivisao(this.idCasa, d);
    }

    public void removeDivisao(String nomeDivisao) throws SessaoNaoIniciadaException, CasaNaoSelecionadaException, CasaNaoExisteException, DivisaoNaoExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        if (this.idCasa == null || this.idCasa.isEmpty())
            throw new CasaNaoSelecionadaException();
        sistema.removerDivisao(this.idCasa, nomeDivisao);
    }

    public void selecionaDivisao(String nomeDivisao) throws CasaNaoSelecionadaException, DivisaoNaoExisteException {
        if (this.idCasa == null || this.idCasa.isEmpty())
            throw new CasaNaoSelecionadaException();
        if (!sistema.divisaoExiste(this.idCasa, nomeDivisao))
            throw new DivisaoNaoExisteException(nomeDivisao);
        this.idDivisao = nomeDivisao;
    }

    public void criaDispositivo(String classe, String idDispositivo, String marca, String modelo, String consumoPorHora) throws SessaoNaoIniciadaException, CasaNaoSelecionadaException, DivisaoNaoSelecionadaException, CasaNaoExisteException, DivisaoNaoExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        if (this.idCasa == null || this.idCasa.isEmpty())
            throw new CasaNaoSelecionadaException();
        if (this.idDivisao == null || this.idDivisao.isEmpty())
            throw new DivisaoNaoSelecionadaException();
        Dispositivo d = DispositivoFactory.criarDispositivo(classe);
        d.setIdentificador(idDispositivo);
        d.setMarca(marca);
        d.setModelo(modelo);
        d.setConsumoPorHora(Double.parseDouble(consumoPorHora));
        sistema.adicionarDispositivo(this.idCasa, this.idDivisao, d);
    }

    public void removeDispositivo(String nomeDispositivo) throws SessaoNaoIniciadaException, CasaNaoSelecionadaException, DivisaoNaoSelecionadaException, CasaNaoExisteException, DivisaoNaoExisteException, DispositivoNaoExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        if (this.idCasa == null || this.idCasa.isEmpty())
            throw new CasaNaoSelecionadaException();
        if (this.idDivisao == null || this.idDivisao.isEmpty())
            throw new DivisaoNaoSelecionadaException();
        sistema.removerDispositivo(this.idCasa, this.idDivisao, nomeDispositivo);
    }

    public void selecionaDispositivo(String nomeDispositivo) throws SessaoNaoIniciadaException, CasaNaoSelecionadaException, DivisaoNaoSelecionadaException, DispositivoNaoExisteException {
        if (this.idUser == null || this.idUser.isEmpty())
            throw new SessaoNaoIniciadaException();
        if (this.idCasa == null || this.idCasa.isEmpty())
            throw new CasaNaoSelecionadaException();
        if (this.idDivisao == null || this.idDivisao.isEmpty())
            throw new DivisaoNaoSelecionadaException();
        if (this.sistema.dispositivoExiste(nomeDispositivo, nomeDispositivo, nomeDispositivo))
            this.idDispositivo = nomeDispositivo;
        else throw new DispositivoNaoExisteException(nomeDispositivo);
    }

    public void criaAutomacao(String nomeAutomacao) {
        Automacao a = AutomacaoFactory.criarAutomacao(nomeAutomacao);
        this.sistema.adicionarAutomacao(this.idCasa, a);
    }

    public void removeAutomacao(String nomeAutomacao) {
        this.sistema.removerAutomacao(this.idCasa, nomeAutomacao);
    }

    public void ativaAutomacao(String nomeAutomacao) {
        this.sistema.executarAutomacoes(this.idCasa, a -> a.getClass().getSimpleName().equalsIgnoreCase(nomeAutomacao) && a.verificar());
    }

    public void ativarTodasAutomacoes(){
        this.sistema.executarAutomacoes(this.idCasa, a -> a.verificar());
    }

    public List<Automacao> getAutomacoes(String nomeCasa) {
        return this.sistema.getAutomacoes(nomeCasa);
    }

    public void criaEscalonamentoAbrirCortinas(String hora, List<String> idsDivisoes) {
        LocalTime h = LocalTime.parse(hora);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "cortina");
        Escalonamento e = new Escalonamento("abre_cortinas", h, dispositivos,
                                            d -> d instanceof Cortina,
                                            d -> ((Cortina) d).abrir(100));
        sistema.adicionarEscalonamento(this.idCasa, e);
    }

    public void criaEscalonamentoFecharCortinas(String hora, List<String> idsDivisoes) {
        LocalTime h = LocalTime.parse(hora);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "cortina");
        Escalonamento e = new Escalonamento("fecha_cortinas", h, dispositivos,
                                            d -> d instanceof Cortina,
                                            d -> ((Cortina) d).fechar(100));
        sistema.adicionarEscalonamento(this.idCasa, e);
    }

    public void criaEscalonamentoAcenderLampadas(String hora, List<String> idsDivisoes) {
        LocalTime h = LocalTime.parse(hora);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "lampada");
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "lampadaCor"));
        Escalonamento e = new Escalonamento("acende_lampadas", h, dispositivos,
                                            d -> d instanceof Lampada,
                                            d -> ((Lampada) d).ligar());
        sistema.adicionarEscalonamento(this.idCasa, e);
    }

    public void criaEscalonamentoDesligarLampadas(String hora, List<String> idsDivisoes) {
        LocalTime h = LocalTime.parse(hora);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "lampada");
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "lampadaCor"));
        Escalonamento e = new Escalonamento("desliga_lampadas", h, dispositivos,
                                            d -> d instanceof Lampada,
                                            d -> ((Lampada) d).desligar());
        sistema.adicionarEscalonamento(this.idCasa, e);
    }

    public void criaEscalonamentoLigarColunas(String hora, List<String> idsDivisoes) {
        LocalTime h = LocalTime.parse(hora);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "colunaDeSom");
        Escalonamento e = new Escalonamento("liga_colunas", h, dispositivos,
                                            d -> d instanceof ColunaDeSom,
                                            d -> ((ColunaDeSom) d).ligar());
        sistema.adicionarEscalonamento(this.idCasa, e);
    }

    public void criaEscalonamentoDesligarColunas(String hora, List<String> idsDivisoes) {
        LocalTime h = LocalTime.parse(hora);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(this.idCasa, idsDivisoes, "colunaDeSom");
        Escalonamento e = new Escalonamento("desliga_colunas", h, dispositivos,
                                            d -> d instanceof ColunaDeSom,
                                            d -> ((ColunaDeSom) d).desligar());
        sistema.adicionarEscalonamento(this.idCasa, e);
    }

    public void ativaEscalonamento(String idEscalonamento){
        this.sistema.executarEscalonamentos(this.idCasa, e -> e.getId().equals(idEscalonamento));
    }

    public void ativarTodosEscalonamentos(){
        this.sistema.executarEscalonamentos(idCasa, e -> e.verificaHora(LocalTime.now()));
    }

    public List<Escalonamento> getEscalonamentos(String nomeCasa) {
        return this.sistema.getEscalonamentos(nomeCasa);
    }

    public void removeEscalonamento(String nomeCasa, String idEscalonamento) {
        this.sistema.removerEscalonamento(nomeCasa, idEscalonamento);
    }

    public void criaCenarioSairdeCasa(){
        Cenario c = new Cenario();
        c.setNome("sair_de_casa");
        c.setAtivo(false);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "lampada");
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "lampadaCor"));
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "cortina"));
        c.setDispositivos(dispositivos);
        List<EstrategiaCenario> estrategias = List.of(
            new EstrategiaCenario(d -> d instanceof Lampada, d -> ((Lampada) d).desligar()),
            new EstrategiaCenario(d -> d instanceof Cortina, d -> ((Cortina) d).fechar(100))
        );
        c.setEstrategias(estrategias);
        sistema.adicionarCenario(idCasa, c);
    }

    public void criaCenarioJantarComAmigos(){
        Cenario c = new Cenario();
        c.setNome("jantar_com_amigos");
        c.setAtivo(false);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "lampadaCor");
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "cortina"));
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "colunaDeSom"));
        c.setDispositivos(dispositivos);
        List<EstrategiaCenario> estrategias = List.of(
            new EstrategiaCenario(d -> d instanceof LampadaCor, d -> ((LampadaCor) d).maisQuente()),
            new EstrategiaCenario(d -> d instanceof Cortina, d -> ((Cortina) d).fechar(75)),
            new EstrategiaCenario(d -> d instanceof ColunaDeSom, d -> ((ColunaDeSom) d).setVolume(50))
        );
        c.setEstrategias(estrategias);
        sistema.adicionarCenario(idCasa, c);
    }

    public void criaCenarioAcordar(){
        Cenario c = new Cenario();
        c.setNome("acordar");
        c.setAtivo(false);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "lampadaCor");
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "lampada"));
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "colunaDeSom"));
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "cortina"));
        c.setDispositivos(dispositivos);
        List<EstrategiaCenario> estrategias = List.of(
            new EstrategiaCenario(d -> d instanceof Lampada, d -> ((Lampada) d).ligar()),
            new EstrategiaCenario(d -> d instanceof Cortina, d -> ((Cortina) d).abrir(50)),
            new EstrategiaCenario(d -> d instanceof ColunaDeSom, d -> ((ColunaDeSom) d).setVolume(100))
        );
        c.setEstrategias(estrategias);
        sistema.adicionarCenario(idCasa, c);
    }

    public void criaCenarioDeitar(){
        Cenario c = new Cenario();
        c.setNome("deitar");
        c.setAtivo(false);
        List<Dispositivo> dispositivos = sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "lampadaCor");
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "lampada"));
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "colunaDeSom"));
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "cortina"));
        dispositivos.addAll(sistema.percorreDivisoesEfiltraDispositivos(idCasa, null, "televisao"));
        c.setDispositivos(dispositivos);
        List<EstrategiaCenario> estrategias = List.of(
            new EstrategiaCenario(d -> d instanceof Lampada, d -> ((Lampada) d).desligar()),
            new EstrategiaCenario(d -> d instanceof Cortina, d -> ((Cortina) d).fechar(100)),
            new EstrategiaCenario(d -> d instanceof ColunaDeSom, d -> ((ColunaDeSom) d).desligar()),
            new EstrategiaCenario(d -> d instanceof Televisao, d -> ((Televisao) d).desligar())
        );
        c.setEstrategias(estrategias);
        sistema.adicionarCenario(idCasa, c);
    }

    public List<Cenario> getCenarios(String nomeCasa) {
        return this.sistema.getCenariosAtivos(nomeCasa);
    }

    public void removeCenario(String nomeCasa, String nomeCenario) {
        this.sistema.removerCenario(nomeCasa, nomeCenario);
    }


    public void ativarCenario(String nomeCasa, String nomeCenario) {
        this.sistema.ativarCenario(nomeCasa, c -> c.getNome().equalsIgnoreCase(nomeCenario) && !c.isAtivo());
    }

    public void ativaCenario(String idCenario){
        this.sistema.ativarCenario(idCasa, c -> c.getNome().equals(idCenario) && !c.isAtivo());
    }

    public void ativarTodosCenarios(){
        this.sistema.ativarCenario(idCasa, c -> !c.isAtivo());
    }
}