package model.dispositivos;

import java.util.Objects;

public class SensorLuminosidade extends Dispositivo implements DispositivoQueLiga {
    private double luminosidade;
    
    public static final double LUX_NOITE = 10;      // <10 lux = noite
    public static final double LUX_PORDOSOL = 100; // <100 lux = anoitecer
    public static final double LUX_DIA = 1000;       // >1000 lux = dia claro

    public SensorLuminosidade(String identificador, String marca, String modelo, double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes, double luminosidade) {
        super(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
        this.luminosidade = luminosidade;
    }

    public SensorLuminosidade() {
        super();
        this.luminosidade = 0;
    }

    public SensorLuminosidade(SensorLuminosidade outroSensor) {
        super(outroSensor);
        this.luminosidade = outroSensor.luminosidade;
    }

    public double getLuminosidade() {
        return luminosidade; 
    }
    
    public void setLuminosidade(double luminosidade) {
        if (luminosidade < 0) this.luminosidade = 0;
        else this.luminosidade = luminosidade;
    }

    public SensorLuminosidade clone() {
        return new SensorLuminosidade(this);
    }

    @Override
    public String toString() {
        return "SensorLuminosidade{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", estado=" + getEstado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", luminosidade=" + luminosidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SensorLuminosidade that = (SensorLuminosidade) o;

        return super.equals(that) && this.luminosidade == that.luminosidade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), luminosidade);
    }

    // Para automações!
    public boolean isNoite() { 
        return luminosidade < LUX_NOITE; 
    }

    public boolean isPordosol() { 
        return luminosidade >= LUX_NOITE && luminosidade < LUX_PORDOSOL; 
    }

    public boolean isDia() { 
        return luminosidade >= LUX_DIA; 
    }

    public boolean deveAcenderLuzes() { 
        return luminosidade < LUX_PORDOSOL; // noite ou anoitecer
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