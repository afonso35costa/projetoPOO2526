package dispositivos;

import java.util.Objects;

public abstract class Dispositivo{
    private int identificador;
    private String marca;
    private String modelo;
    private double consumoPorHora;
    private boolean ligado;
    private int minutosLigado;
    private int numActivacoes;
    

    public Dispositivo(int identificador, String marca, String modelo, double consumoPorHora, boolean ligado, int minutosLigado, int numActivacoes) {
        this.identificador = identificador;
        this.marca = marca;
        this.modelo = modelo;
        this.consumoPorHora = consumoPorHora;
        this.ligado = ligado;
        this.minutosLigado = minutosLigado;
        this.numActivacoes = numActivacoes;
        }

    public Dispositivo() {
        this.identificador = 0;
        this.marca = "";
        this.modelo = "";
        this.consumoPorHora = 0.0;
        this.ligado = false;
        this.minutosLigado = 0;
        this.numActivacoes = 0;
    }

    public Dispositivo(Dispositivo outroDispositivo) {
        this.identificador = outroDispositivo.identificador;
        this.marca = outroDispositivo.marca;
        this.modelo = outroDispositivo.modelo;
        this.consumoPorHora = outroDispositivo.consumoPorHora;
        this.ligado = outroDispositivo.ligado;
        this.minutosLigado = outroDispositivo.minutosLigado;
        this.numActivacoes = outroDispositivo.numActivacoes;
    }

    public int getIdentificador() {
        return identificador;
    }
    public String getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }
    public double getConsumoPorHora() {
        return consumoPorHora;
    }
    public boolean getLigado() {
        return ligado;
    }
    public int getMinutosLigado() {
        return minutosLigado;
    }
    public int getNumActivacoes() {
        return numActivacoes;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setConsumoPorHora(double consumoPorHora) {
        this.consumoPorHora = consumoPorHora;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    public void setMinutosLigado(int minutosLigado) {
        this.minutosLigado = minutosLigado;
    }

    public void setNumActivacoes(int numActivacoes) {
        this.numActivacoes = numActivacoes;
    }

    public Dispositivo clone() {
        return new Dispositivo(this) {};
    }

     @Override
    public String toString() {
        return "Dispositivo{" +
                "identificador=" + identificador +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", consumoPorHora=" + consumoPorHora +
                ", ligado=" + ligado +
                ", minutosLigado=" + minutosLigado +
                ", numActivacoes=" + numActivacoes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispositivo that = (Dispositivo) o;
        return this.identificador == that.identificador &&
                this.consumoPorHora == that.consumoPorHora &&
                this.ligado == that.ligado &&
                this.minutosLigado == that.minutosLigado &&
                this.numActivacoes == that.numActivacoes &&
                this.marca.equals(that.marca) &&
                this.modelo.equals(that.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes);
    }

    public void ligar() {
        if (!ligado) {
            ligado = true;
            numActivacoes++;
        }
    }
    public void desligar() {
        if (ligado) {
            ligado = false;
        }
    }

}