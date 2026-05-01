package model.dispositivos;

import java.util.Objects;

public class Termostato extends Dispositivo implements DispositivoQueLiga {
    private double temperaturaAlvo;  // em graus Celsius
    private double temperaturaAtual;
    private String modo; // "aquecimento", "arrefecimento", "auto"

    public Termostato(String identificador, String marca, String modelo, double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes, double temperaturaAlvo, double temperaturaAtual, String modo) {
        super(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
        this.temperaturaAlvo = temperaturaAlvo;
        this.temperaturaAtual = temperaturaAtual;
        this.modo = modo;
    }

    public Termostato() {
        super();
        this.temperaturaAlvo = 20.0;
        this.temperaturaAtual = 20.0;
        this.modo = "auto";
    }

    public Termostato(Termostato outroTermostato) {
        super(outroTermostato);
        this.temperaturaAlvo = outroTermostato.temperaturaAlvo;
        this.temperaturaAtual = outroTermostato.temperaturaAtual;
        this.modo = outroTermostato.modo;
    }

    public double getTemperaturaAlvo() {
        return temperaturaAlvo;
    }

    public void setTemperaturaAlvo(double temperaturaAlvo) {
        if (temperaturaAlvo < 5) this.temperaturaAlvo = 5;
        else if (temperaturaAlvo > 35) this.temperaturaAlvo = 35;
        else this.temperaturaAlvo = temperaturaAlvo;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(double temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public Termostato clone() {
        return new Termostato(this);
    }

    @Override
    public String toString() {
        return "Termostato{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", estado=" + getEstado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", temperaturaAlvo=" + temperaturaAlvo +
                ", temperaturaAtual=" + temperaturaAtual +
                ", modo='" + modo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Termostato)) return false;
        if (!super.equals(o)) return false;

        Termostato that = (Termostato) o;

        return super.equals(that) &&
                this.temperaturaAlvo == that.temperaturaAlvo &&
                this.temperaturaAtual == that.temperaturaAtual &&
                this.modo.equals(that.modo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), temperaturaAlvo, temperaturaAtual, modo);
    }

    public void aumentarTemperatura(double incremento) {
        setTemperaturaAlvo(this.temperaturaAlvo + incremento);
    }

    public void diminuirTemperatura(double decremento) {
        setTemperaturaAlvo(this.temperaturaAlvo - decremento);
    }

    @Override
    public void ligar() {
        super.setEstado(Estado.LIGADO);
        this.modo = "auto"; // Ao ligar, o termostato entra em modo automático
    }

    @Override
    public void desligar() {
        super.setEstado(Estado.DESLIGADO);
    }
}
