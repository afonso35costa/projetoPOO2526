package model.automatismos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.dispositivos.Cortina;
import model.dispositivos.Dispositivo;
import model.dispositivos.SensorChuva;

public class AutomacaoChuva implements Automacao, Serializable{
    private SensorChuva sensor;
    private List<Dispositivo> atuadores;

    public AutomacaoChuva() {
        this.sensor = null;
        this.atuadores = new ArrayList<>();
    }

    public AutomacaoChuva(SensorChuva sensor, List<Dispositivo> atuadores) {
        this.sensor = sensor;
        this.atuadores = atuadores;
    }

    public AutomacaoChuva(AutomacaoChuva automacao) {
        this.sensor = automacao.sensor;
        this.atuadores = new ArrayList<>(automacao.atuadores);
    }   

    public SensorChuva getSensor() {
        return this.sensor;
    }

    public void setSensor(SensorChuva sensor) {
        this.sensor = sensor;
    }

    public List<Dispositivo> getAtuadores() {
        return atuadores;
    }

    public void setAtuadores(List<Dispositivo> atuadores) {
        this.atuadores = atuadores;
    }

    public AutomacaoChuva clone() {
        return new AutomacaoChuva(this);
    }

    @Override
    public String toString() {
        return "AutomacaoChuva{" +
                "sensor=" + sensor +
                ", atuadores=" + atuadores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutomacaoChuva that = (AutomacaoChuva) o;

        if (!sensor.equals(that.sensor)) return false;
        return atuadores.equals(that.atuadores);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensor, atuadores);
    }

    @Override
    public boolean verificar() {
        if (this.sensor == null || this.atuadores.isEmpty()) {
            return false;
        }
        return this.sensor.estaAChover();
    }

    @Override
    public void executar() {
        this.atuadores.stream()
            .filter(d -> d instanceof Cortina)
            .map(d -> (Cortina) d)
            .filter(c -> c.getEstado() == Dispositivo.Estado.ABERTO)
            .forEach(c -> c.fechar(100));
    }
}