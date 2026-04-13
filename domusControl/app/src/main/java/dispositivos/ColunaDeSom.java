package dispositivos;

import java.util.Objects;

public class ColunaDeSom extends Dispositivo {
    private int volume; // 0 a 100

    public ColunaDeSom(int identificador, String marca, String modelo,
                       double consumoPorHora, boolean ligado, int minutosLigado, int numActivacoes, int volume) {
        super(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes);
        this.volume = volume;
    }

    public ColunaDeSom() {
        super();
        this.volume = 0;
    }

    public ColunaDeSom(ColunaDeSom outraColuna) {
        super(outraColuna);
        this.volume = outraColuna.volume;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (volume < 0) this.volume = 0;
        else if (volume > 100) this.volume = 100;
        else this.volume = volume;
    }

    public ColunaDeSom clone() {
        return new ColunaDeSom(this);
    }

    @Override
    public String toString() {
        return "ColunaDeSom{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", ligado=" + getLigado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", volume=" + volume +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColunaDeSom that = (ColunaDeSom) o;

        return super.equals(that) && this.volume == that.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }

    public void maxVolume() {
        this.volume = 100;
    }

    public void minVolume() {
        this.volume = 0;
    }

    public void aumentarVolume(int incremento) {
        setVolume(this.volume + incremento);
    }

    public void diminuirVolume(int decremento) {
        setVolume(this.volume - decremento);
    }

}
