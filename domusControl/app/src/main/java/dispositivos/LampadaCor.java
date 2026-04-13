package dispositivos;

import java.util.Objects;

public class LampadaCor extends Lampada {
    private int temperaturaCor;

    public LampadaCor(int identificador, String marca, String modelo,
                      double consumoPorHora, boolean ligado, int minutosLigado, int numActivacoes, int intensidade, int temperaturaCor) {
        super(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes, intensidade);
        this.temperaturaCor = temperaturaCor;
    }

    public LampadaCor() {
        super();
        this.temperaturaCor = 2700; // valor mínimo
    }

    public LampadaCor(LampadaCor outraLampadaCor) {
        super(outraLampadaCor);
        this.temperaturaCor = outraLampadaCor.temperaturaCor;
    }

    public LampadaCor clone() {
        return new LampadaCor(this);
    }

    @Override
    public String toString() {
        return "LampadaCor{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", ligado=" + getLigado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", intensidade=" + getIntensidade() +
                ", temperaturaCor=" + temperaturaCor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!super.equals(o)) return false;
        LampadaCor that = (LampadaCor) o;
        return super.equals(that) && this.temperaturaCor == that.temperaturaCor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), temperaturaCor);
    }

    public int getTemperaturaCor() {
        return temperaturaCor; 
    }

    public void setTemperaturaCor(int temperaturaCor) {
        if (temperaturaCor < 2700) this.temperaturaCor = 2700;
        else if (temperaturaCor > 4000) this.temperaturaCor = 4000;
        else this.temperaturaCor = temperaturaCor;
    }

    public void maisQuente() {          // aproxima-se dos 2700K (amarelo)
        setTemperaturaCor(this.temperaturaCor - 100);
    }

    public void maisFria() {            // aproxima-se dos 4000K (branco)
        setTemperaturaCor(this.temperaturaCor + 100);
    }

}
