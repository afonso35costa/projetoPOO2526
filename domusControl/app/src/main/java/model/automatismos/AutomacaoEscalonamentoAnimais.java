package model.automatismos;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import model.dispositivos.DispensadorRacao;
import model.dispositivos.Dispositivo;

public class AutomacaoEscalonamentoAnimais extends Escalonamento implements Automacao{

    public AutomacaoEscalonamentoAnimais(String id, LocalTime hora, List<Dispositivo> dispositivos, Predicate<Dispositivo> condicao, Consumer<Dispositivo> acao) {
        super(id, hora, dispositivos, condicao, acao);
    }

    public AutomacaoEscalonamentoAnimais() {
        super();
    }

    public AutomacaoEscalonamentoAnimais(AutomacaoEscalonamentoAnimais automacaoEscalonamentoAnimais) {
        super(automacaoEscalonamentoAnimais);
    }

    public AutomacaoEscalonamentoAnimais clone() {
        return new AutomacaoEscalonamentoAnimais(this);
    }

    @Override
    public String toString() {
        return "AutomacaoEscalonamentoAnimais{" +
                "dispensadorRacao=" +  +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutomacaoEscalonamentoAnimais that = (AutomacaoEscalonamentoAnimais) o;
        return super.equals(that);
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean verificar() {
        DispensadorRacao dispensador = (DispensadorRacao) this.getDispositivos().get(0);
        return dispensador.racaoVazia() || dispensador.aguaVazia();
    }

    @Override
    public void executar() {
        DispensadorRacao dispensador = (DispensadorRacao) this.getDispositivos().get(0);
        if (this.verificar()) {
            dispensador.encherAguaSeVazia();
            dispensador.encherRacaoSeVazia();
        }
    }

    public DispensadorRacao getDispensadorRacao() {
        if (this.getDispositivos().isEmpty()) {
            return null;
        }
        return (DispensadorRacao) this.getDispositivos().get(0);
    }
}