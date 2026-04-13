package dispositivos;

import java.util.Objects;

public class Cortina extends Dispositivo{
    private int abertura;//0 a 100//

    public Cortina(int identificador, String marca, String modelo,
                   double consumoPorHora, boolean ligado, int minutosLigado, int numActivacoes, int abertura) {
        super(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes);
        this.abertura = abertura;
    }

    public Cortina() {
        super();
        this.abertura = 0;
    }

    public Cortina(Cortina outraCortina) {
        super(outraCortina);
        this.abertura = outraCortina.abertura;
    }

    public int getAbertura() {
        return abertura;
    }

    public void setAbertura(int abertura) {
        if (abertura < 0) this.abertura = 0;
        else if (abertura > 100) this.abertura = 100;
        else this.abertura = abertura;  
    }

    public Cortina clone() {
        return new Cortina(this);
    }

    @Override
    public String toString() {
        return "Cortina{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", ligado=" + getLigado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", abertura=" + abertura +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cortina cortina = (Cortina) o;
        return super.equals(cortina) && this.abertura == cortina.abertura;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), abertura);
    }

    public void abrirToda() {
        this.abertura = 100;
    }

    public void fecharToda() {
        this.abertura = 0;
    }

    public void abrirParcial(int incremento) {
        setAbertura(this.abertura + incremento);
    }

    public void fecharParcial(int decremento) {
        setAbertura(this.abertura - decremento);
    }

}