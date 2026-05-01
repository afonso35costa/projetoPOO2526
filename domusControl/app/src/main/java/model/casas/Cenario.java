package model.casas;

import model.dispositivos.Dispositivo;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Cenario {
    private String nome;
    private boolean ativo;
    private List<Dispositivo> dispositivos;
    private List<EstrategiaCenario> estrategias;

    public Cenario(String nome, boolean ativo, List<Dispositivo> dispositivos, List<EstrategiaCenario> estrategias) {
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
        this.dispositivos = new ArrayList<>(outroCenario.dispositivos); // agregação
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

    public List<Dispositivo> getDispositivos() {
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

    public void setDispositivos(List<Dispositivo> dispositivos) {
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


    public void adicionarEstrategia(Predicate<Dispositivo> condicao, Consumer<Dispositivo> acao) {
        this.estrategias.add(new EstrategiaCenario(condicao, acao));
    }

    public void executar() {
        if (!this.ativo) return;
        for (EstrategiaCenario estrategia : this.estrategias) {
            this.dispositivos.stream()
                .filter(estrategia.getCondicao())
                .forEach(estrategia.getAcao());
        }
    }
}