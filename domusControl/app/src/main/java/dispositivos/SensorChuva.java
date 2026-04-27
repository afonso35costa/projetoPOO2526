package dispositivos;

import java.util.Objects;

public class SensorChuva extends Dispositivo implements DispositivoQueLiga {
    private double pluviosidade; // mm/h
    private boolean estaAChover;

    public SensorChuva(String identificador, String marca, String modelo, double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes, double pluviosidade, boolean estaAChover) {
        super(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
        this.pluviosidade = pluviosidade;
        this.estaAChover = estaAChover;
    }

    public SensorChuva() {
        super();
        this.pluviosidade = 0;
        this.estaAChover = false;
    }

    public SensorChuva(SensorChuva outroSensor) {
        super(outroSensor);
        this.pluviosidade = outroSensor.pluviosidade;
        this.estaAChover = outroSensor.estaAChover;
    }

    public double getPluviosidade() {
        return pluviosidade;
    }

    public boolean estaAChover() {
        return estaAChover;
    }

    public void setPluviosidade(double pluviosidade) {
        this.pluviosidade = pluviosidade;
        this.estaAChover = pluviosidade > 0;
    }

    public void setEstaAChover(boolean estaAChover) {
        this.estaAChover = estaAChover;
        if (!estaAChover) {
            this.pluviosidade = 0;
        }
    }

    public SensorChuva clone() {
        return new SensorChuva(this);
    }

    @Override
    public String toString() {
        return "SensorChuva{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", estado=" + getEstado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", pluviosidade=" + pluviosidade +
                ", estaAChover=" + estaAChover +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorChuva that = (SensorChuva) o;
        return super.equals(that) &&
                this.pluviosidade == that.pluviosidade &&
                this.estaAChover == that.estaAChover;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pluviosidade, estaAChover);
    }

    @Override
    public void ligar() {
        super.setEstado(Estado.LIGADO);
    }

    @Override
    public void desligar() {
        super.setEstado(Estado.DESLIGADO);
    }
}
