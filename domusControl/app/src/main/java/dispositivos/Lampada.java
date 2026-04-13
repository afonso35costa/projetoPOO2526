package dispositivos;

import java.util.Objects;

public class Lampada extends Dispositivo{
    private int intensidade; //0 a 100//

    public Lampada(int identificador, String marca, String modelo,
                   double consumoPorHora, boolean ligado, int minutosLigado, int numActivacoes, int intensidade) {
        super(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes);
        this.intensidade = intensidade;
    }

    public Lampada() {
        super();
        this.intensidade = 0;
    }

    public Lampada(Lampada outraLampada) {
        super(outraLampada);
        this.intensidade = outraLampada.intensidade;
    }

    public int getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(int intensidade) {
        if (intensidade < 0) this.intensidade = 0;
        else if (intensidade > 100) this.intensidade = 100;
        else this.intensidade = intensidade;
    }

    public Lampada clone() {
        return new Lampada(this);
    }

    @Override
    public String toString() {
        return "Lampada{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", ligado=" + getLigado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", intensidade=" + intensidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lampada lampada = (Lampada) o;

        return super.equals(lampada) && this.intensidade == lampada.intensidade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), intensidade);
    }

    @Override
    public void ligar() {
        super.ligar();
        this.intensidade = 100;
    }

    @Override
    public void desligar() {
        super.desligar();
        this.intensidade = 0;
    }

    public void aumentarIntensidade(int incremento) {
        setIntensidade(this.intensidade + incremento);
    }

    public void diminuirIntensidade(int decremento) {
        setIntensidade(this.intensidade - decremento);
    }

}