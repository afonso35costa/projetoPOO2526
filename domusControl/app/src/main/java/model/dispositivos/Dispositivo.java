package model.dispositivos;

import java.io.Serializable;
import java.util.Objects;

public abstract class Dispositivo implements Serializable{

    public enum Estado {
        LIGADO,
        DESLIGADO,
        ABERTO,
        FECHADO
    }

    private String identificador;
    private String marca;
    private String modelo;
    private double consumoPorHora;
    private Estado estado;
    private int minutosLigado;
    private int numActivacoes;
    

    public Dispositivo(String identificador, String marca, String modelo, double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes) {
        this.identificador = identificador;
        this.marca = marca;
        this.modelo = modelo;
        this.consumoPorHora = consumoPorHora;
        this.estado = estado;
        this.minutosLigado = minutosLigado;
        this.numActivacoes = numActivacoes;
        }

    public Dispositivo() {
        this.identificador = "";
        this.marca = "";
        this.modelo = "";
        this.consumoPorHora = 0.0;
        this.estado = Estado.DESLIGADO;
        this.minutosLigado = 0;
        this.numActivacoes = 0;
    }

    public Dispositivo(Dispositivo outroDispositivo) {
        this.identificador = outroDispositivo.identificador;
        this.marca = outroDispositivo.marca;
        this.modelo = outroDispositivo.modelo;
        this.consumoPorHora = outroDispositivo.consumoPorHora;
        this.estado = outroDispositivo.estado;
        this.minutosLigado = outroDispositivo.minutosLigado;
        this.numActivacoes = outroDispositivo.numActivacoes;
    }

    public String getIdentificador() {
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
    public Estado getEstado() {
        return estado;
    }
    public int getMinutosLigado() {
        return minutosLigado;
    }
    public int getNumActivacoes() {
        return numActivacoes;
    }

    public void setIdentificador(String identificador) {
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

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setMinutosLigado(int minutosLigado) {
        this.minutosLigado = minutosLigado;
    }

    public void setNumActivacoes(int numActivacoes) {
        this.numActivacoes = numActivacoes;
    }

    public abstract Dispositivo clone();

    @Override
    public String toString() {
        return "Dispositivo{" +
                "identificador=" + identificador +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", consumoPorHora=" + consumoPorHora +
                ", estado=" + estado +
                ", minutosLigado=" + minutosLigado +
                ", numActivacoes=" + numActivacoes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dispositivo that = (Dispositivo) o;
        return this.identificador.equals(that.identificador) &&
                this.consumoPorHora == that.consumoPorHora &&
                this.estado == that.estado &&
                this.minutosLigado == that.minutosLigado &&
                this.numActivacoes == that.numActivacoes &&
                this.marca.equals(that.marca) &&
                this.modelo.equals(that.modelo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
    }

}