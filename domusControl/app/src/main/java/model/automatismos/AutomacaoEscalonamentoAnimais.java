package model.automatismos;

import model.dispositivos.DispensadorRacao;

public class AutomacaoEscalonamentoAnimais implements Automacao{
    private DispensadorRacao dispensadorRacao;

    public AutomacaoEscalonamentoAnimais(DispensadorRacao dispensadorRacao) {
        this.dispensadorRacao = dispensadorRacao;
    }

    public AutomacaoEscalonamentoAnimais() {
        this.dispensadorRacao = null;
    }

    public AutomacaoEscalonamentoAnimais(AutomacaoEscalonamentoAnimais automacaoEscalonamentoAnimais) {
        this.dispensadorRacao = automacaoEscalonamentoAnimais.dispensadorRacao;
    }

    public DispensadorRacao getDispensadorRacao() {
        return this.dispensadorRacao;
    }

    public void setDispensadorRacao(DispensadorRacao dispensadorRacao) {
        this.dispensadorRacao = dispensadorRacao;
    }

    public AutomacaoEscalonamentoAnimais clone() {
        return new AutomacaoEscalonamentoAnimais(this);
    }

    @Override
    public String toString() {
        return "AutomacaoEscalonamentoAnimais{" +
                "dispensadorRacao=" + this.dispensadorRacao +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutomacaoEscalonamentoAnimais that = (AutomacaoEscalonamentoAnimais) o;
        return this.dispensadorRacao.equals(that.dispensadorRacao);
    }

    public int hashCode() {
        return this.dispensadorRacao.hashCode();
    }

    @Override
    public boolean verificar() {
        if (this.dispensadorRacao == null) return false;
        return this.dispensadorRacao.racaoVazia() || this.dispensadorRacao.aguaVazia();
    }

    @Override
    public void executar() {
        if (this.verificar()) {
            this.dispensadorRacao.encherAguaSeVazia();
            this.dispensadorRacao.encherRacaoSeVazia();
        }
    }
}