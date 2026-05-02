package model.automatismos;

import java.io.Serializable;
import java.util.Objects;

import model.dispositivos.Irrigacao;
import model.dispositivos.SensorChuva;
import model.dispositivos.SensorHumidadeSolo;

public class AutomacaoRelva implements Automacao, Serializable {

    private Irrigacao irrigacao;
    private SensorChuva sensorChuva;
    private SensorHumidadeSolo sensorHumidade;

    public AutomacaoRelva(Irrigacao irrigacao, SensorChuva sensorChuva, SensorHumidadeSolo sensorHumidade) {
        this.irrigacao = irrigacao;
        this.sensorChuva = sensorChuva;
        this.sensorHumidade = sensorHumidade;
    }

    public AutomacaoRelva() {
        this.irrigacao = null;
        this.sensorChuva = null;
        this.sensorHumidade = null;
    }

    public AutomacaoRelva(AutomacaoRelva automacaoRelva) {
        this.irrigacao = automacaoRelva.irrigacao;
        this.sensorChuva = automacaoRelva.sensorChuva;
        this.sensorHumidade = automacaoRelva.sensorHumidade;
    }

    public Irrigacao getIrrigacao() {
        return this.irrigacao;
    }

    public void setIrrigacao(Irrigacao irrigacao) {
        this.irrigacao = irrigacao;
    }

    public SensorChuva getSensorChuva() {
        return this.sensorChuva;
    }

    public void setSensorChuva(SensorChuva sensorChuva) {
        this.sensorChuva = sensorChuva;
    }

    public SensorHumidadeSolo getSensorHumidade() {
        return this.sensorHumidade;
    }

    public void setSensorHumidade(SensorHumidadeSolo sensorHumidade) {
        this.sensorHumidade = sensorHumidade;
    }

    public AutomacaoRelva clone() {
        return new AutomacaoRelva(this);
    }

    @Override
    public String toString() {
        return "AutomacaoRelva{" +
                "irrigacao=" + this.irrigacao +
                ", sensorChuva=" + this.sensorChuva +
                ", sensorHumidade=" + this.sensorHumidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutomacaoRelva that = (AutomacaoRelva) o;

        if (!irrigacao.equals(that.irrigacao)) return false;
        if (!sensorChuva.equals(that.sensorChuva)) return false;
        return sensorHumidade.equals(that.sensorHumidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(irrigacao, sensorChuva, sensorHumidade);
    }

    @Override
    public boolean verificar() {
        return !sensorChuva.estaAChover() && sensorHumidade.estaSeco();
    }

    @Override
    public void executar() {
        if (verificar()) {
            irrigacao.ligar();
            sensorHumidade.setHumidade(100);
        }
    }
}