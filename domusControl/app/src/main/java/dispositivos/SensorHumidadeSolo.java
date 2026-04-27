package dispositivos;

import java.util.Objects;

public class SensorHumidadeSolo extends Dispositivo implements DispositivoQueLiga {
    private double humidade;
    private boolean estaSeco;

    public SensorHumidadeSolo(String identificador, String marca, String modelo, double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes, double humidade, boolean estaSeco) {
        super(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
        this.humidade = humidade;
        this.estaSeco = estaSeco;
    }

    public SensorHumidadeSolo() {
        super();
        this.humidade = 0;
        this.estaSeco = false;
    }

    public SensorHumidadeSolo(SensorHumidadeSolo outroSensor) {
        super(outroSensor);
        this.humidade = outroSensor.humidade;
        this.estaSeco = outroSensor.estaSeco;
    }

    public double getHumidade() {
        return humidade;
    }

    public boolean estaSeco() {
        return estaSeco;
    }

    public void setHumidade(double humidade) {
        this.humidade = humidade;
        this.estaSeco = humidade == 0;
    }

    public void setEstaSeco(boolean estaSeco) {
        this.estaSeco = estaSeco;
        if (!estaSeco) {
            this.humidade = 0;
        }
    }

    public SensorHumidadeSolo clone() {
        return new SensorHumidadeSolo(this);
    }

    @Override
    public String toString() {
        return "SensorHumidadeSolo{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", estado=" + getEstado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", humidade=" + humidade +
                ", estaSeco=" + estaSeco +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorHumidadeSolo that = (SensorHumidadeSolo) o;
        return super.equals(that) &&
                this.humidade == that.humidade &&
                this.estaSeco == that.estaSeco;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), humidade, estaSeco);
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
