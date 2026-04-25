package automatismos;

import dispositivos.Termostato;
import dispositivos.Dispositivo.Estado;

public class AutomacaoTemperatura implements Automacao{
    private Termostato termostato;

    public AutomacaoTemperatura(Termostato termostato) {
        this.termostato = termostato;
    }

    public AutomacaoTemperatura() {
        this.termostato = new Termostato();
    }

    public AutomacaoTemperatura(AutomacaoTemperatura outraAutomacao) {
        this.termostato = outraAutomacao.termostato;
    }

    public Termostato getTermostato() {
        return this.termostato;
    }

    public void setTermostato(Termostato termostato) {
        this.termostato = termostato;
    }

    public AutomacaoTemperatura clone() {
        return new AutomacaoTemperatura(this);
    }

    @Override
    public String toString() {
        return "AutomacaoTemperatura{" +
                "termostato=" + this.termostato +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutomacaoTemperatura that = (AutomacaoTemperatura) o;
        return this.termostato.equals(that.termostato);
    }

    @Override
    public int hashCode() {
        return this.termostato.hashCode();
    }

    @Override
    public boolean verificar() {
        if (this.termostato == null) return false;
        return (this.termostato.getEstado().equals(Estado.DESLIGADO) && ((this.termostato.getTemperaturaAtual() - this.termostato.getTemperaturaAlvo()) > 5) || (this.termostato.getTemperaturaAtual() - this.termostato.getTemperaturaAlvo()) < -5) ||
                (this.termostato.getEstado().equals(Estado.LIGADO) && this.termostato.getTemperaturaAtual() == this.termostato.getTemperaturaAlvo());
    }

    @Override
    public void executar() {
        if (this.termostato != null) {
            if (this.termostato.getEstado().equals(Estado.LIGADO)) this.termostato.setEstado(Estado.DESLIGADO);
            else {
                this.termostato.setEstado(Estado.LIGADO);
                this.termostato.setTemperaturaAtual(this.termostato.getTemperaturaAlvo());
            }
        }
    }
}