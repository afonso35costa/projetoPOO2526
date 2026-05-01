package model.automatismos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.dispositivos.Lampada;
import model.dispositivos.SensorLuminosidade;
import model.dispositivos.Dispositivo.Estado;

public class AutomacaoLuzExterior implements Automacao{
    private SensorLuminosidade sensorLuminosidade;
    private List<Lampada> lampadas;

    public AutomacaoLuzExterior(SensorLuminosidade sensorLuminosidade, List<Lampada> lampadas) {
        this.sensorLuminosidade = sensorLuminosidade;
        this.lampadas = lampadas;
    }

    public AutomacaoLuzExterior(AutomacaoLuzExterior automacao) {
        this.sensorLuminosidade = automacao.sensorLuminosidade;
        this.lampadas = automacao.lampadas;
    }

    public AutomacaoLuzExterior() {
        this.sensorLuminosidade = null;;
        this.lampadas = new ArrayList<Lampada>();
    }

    public SensorLuminosidade getSensorLuminosidade() {
        return this.sensorLuminosidade;
    }

    public void setSensorLuminosidade(SensorLuminosidade sensorLuminosidade) {
        this.sensorLuminosidade = sensorLuminosidade;
    }

    public List<Lampada> getLampadas() {
        return this.lampadas;
    }

    public void setLampadas(List<Lampada> lampadas) {
        this.lampadas = lampadas;
    }

    public AutomacaoLuzExterior clone() {
        return new AutomacaoLuzExterior(this);
    }

    @Override 
    public String toString() {
        return "AutomacaoLuzExterior [sensorLuminosidade=" + sensorLuminosidade + ", lampadas=" + lampadas + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AutomacaoLuzExterior other = (AutomacaoLuzExterior) obj;
        return sensorLuminosidade.equals(other.sensorLuminosidade) && lampadas.equals(other.lampadas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorLuminosidade, lampadas);
    }

    @Override
    public boolean verificar() {
        if (sensorLuminosidade != null) {
            return sensorLuminosidade.deveAcenderLuzes();
        }
        return false;
    }

    @Override
    public void executar() {
        lampadas.stream().filter(l -> l.getEstado() != Estado.LIGADO).forEach(l -> l.ligar());
    }
}