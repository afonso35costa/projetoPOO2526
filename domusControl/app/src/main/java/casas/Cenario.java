package casas;

import dispositivos.Dispositivo;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
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
        this.dispositivos = outroCenario.dispositivos;
        this.estrategias = new ArrayList<>(outroCenario.estrategias);
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public List<EstrategiaCenario> getEstrategias() {
        return estrategias;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public void setEstrategias(List<EstrategiaCenario> estrategias) {
        this.estrategias = estrategias;
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