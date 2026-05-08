package model.casas;

import model.dispositivos.Dispositivo;
import utils.SerializableConsumer;
import utils.SerializablePredicate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.io.Serializable;
import java.util.ArrayList;

public class Cenario implements Serializable{
    private String nome;
    private boolean ativo;
    private List<String> dispositivos;
    private List<EstrategiaCenario> estrategias;

    public Cenario(String nome, boolean ativo, List<String> dispositivos, List<EstrategiaCenario> estrategias) {
        this.nome = nome;
        this.ativo = ativo;
        this.dispositivos = dispositivos;
        this.estrategias = estrategias;
    }

    public Cenario() {
        this.nome = "";
        this.ativo = false;
        this.dispositivos = new ArrayList<>();
        this.estrategias = new ArrayList<>();
    }

    public Cenario(Cenario outroCenario) {
        this.nome = outroCenario.nome;
        this.ativo = outroCenario.ativo;
        this.dispositivos = new ArrayList<>(outroCenario.dispositivos);
        this.estrategias = outroCenario.estrategias.stream()
            .map(EstrategiaCenario::clone)
            .collect(Collectors.toCollection(ArrayList::new)); // composição
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public List<String> getDispositivos() {
        return new ArrayList<>(this.dispositivos);
    }

    public List<EstrategiaCenario> getEstrategias() {
        return this.estrategias.stream()
            .map(EstrategiaCenario::clone)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setDispositivos(List<String> dispositivos) {
        this.dispositivos = new ArrayList<>(dispositivos);
    }

    public void setEstrategias(List<EstrategiaCenario> estrategias) {
        this.estrategias = estrategias.stream().map(EstrategiaCenario::clone).toList();
    }

    public Cenario clone() {
        return new Cenario(this);
    }

    @Override
    public String toString() {
        return "Cenario{" +
                "nome='" + nome + '\'' +
                ", estrategias=" + estrategias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cenario cenario = (Cenario) o;

        if (!nome.equals(cenario.nome)) return false;
        return estrategias.equals(cenario.estrategias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, estrategias);
    }


    public void adicionarEstrategia(SerializablePredicate<Dispositivo> condicao, SerializableConsumer<Dispositivo> acao) {
        this.estrategias.add(new EstrategiaCenario(condicao, acao));
    }

    public void executar(Casa casa) {
        if (!this.ativo) return;
        for (EstrategiaCenario e : this.estrategias) {
            casa.atuarSobreDispositivos(this.dispositivos, e.getCondicao(), e.getAcao());
        }
    }
}