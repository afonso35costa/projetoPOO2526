package model.dispositivos;

import java.util.Objects;

public class Televisao extends Dispositivo implements DispositivoQueLiga {
    private int canal;
    private int volume;

    public Televisao(String identificador, String marca, String modelo, double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes, int canal, int volume) {
        super(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
        setCanal(canal);
        setVolume(volume);
    }

    public Televisao() {
        super();
        this.canal = 1;
        this.volume = 0;
    }

    public Televisao(Televisao outraTelevisao) {
        super(outraTelevisao);
        this.canal = outraTelevisao.canal;
        this.volume = outraTelevisao.volume;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        if (canal < 1) this.canal = 1;
        else if (canal > 300) this.canal = 300;
        else this.canal = canal;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (volume < 0) this.volume = 0;
        else if (volume > 100) this.volume = 100;
        else this.volume = volume;
    }

    public Televisao clone() {
        return new Televisao(this);
    }

    @Override
    public String toString() {
        return "Televisao{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", estado=" + getEstado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", canal=" + canal +
                ", volume=" + volume +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Televisao that = (Televisao) o;

        return super.equals(that) && this.canal == that.canal && this.volume == that.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), canal, volume);
    }

    public void aumentarVolume() {
        setVolume(this.volume + 5);
    }

    public void diminuirVolume() {
        setVolume(this.volume - 5);
    }

    public void mudarCanal(int novoCanal) {
        setCanal(novoCanal);
    }

    public void proximoCanal() {
        setCanal(this.canal + 1); 
    }

    public void canalAnterior() {
        setCanal(this.canal - 1); 
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