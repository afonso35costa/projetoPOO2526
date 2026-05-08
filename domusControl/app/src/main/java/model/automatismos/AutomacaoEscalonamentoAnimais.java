package model.automatismos;

import java.time.LocalTime;
import java.util.List;

import model.dispositivos.DispensadorRacao;
import model.dispositivos.Dispositivo;
import utils.SerializableConsumer;
import utils.SerializablePredicate;

public class AutomacaoEscalonamentoAnimais extends Escalonamento implements Automacao{
    private DispensadorRacao dispensadorRacao;

    public AutomacaoEscalonamentoAnimais(String id, LocalTime hora, List<String> dispositivos, SerializablePredicate<Dispositivo> condicao, SerializableConsumer<Dispositivo> acao, DispensadorRacao dispensadorRacao) {
        super(id, hora, dispositivos, condicao, acao);
        this.dispensadorRacao = dispensadorRacao;
    }

    public AutomacaoEscalonamentoAnimais() {
        super();
        this.dispensadorRacao = null;
    }

    public AutomacaoEscalonamentoAnimais(AutomacaoEscalonamentoAnimais automacaoEscalonamentoAnimais) {
        super(automacaoEscalonamentoAnimais);
        this.dispensadorRacao = automacaoEscalonamentoAnimais.dispensadorRacao;
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
        return this.dispensadorRacao.racaoVazia() || this.dispensadorRacao.aguaVazia();
    }

    @Override
    public void executar() {
        if (this.verificar()) {
            this.dispensadorRacao.encherAguaSeVazia();
            this.dispensadorRacao.encherRacaoSeVazia();
        }
    }

    public DispensadorRacao getDispensadorRacao() {
        return dispensadorRacao;
    }

}