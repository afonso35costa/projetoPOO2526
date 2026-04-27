package dispositivos;

import java.util.Objects;

public class Cortina extends Dispositivo{
    private int abertura;//0 a 100//

    public Cortina(String identificador, String marca, String modelo,
                   double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes, int abertura) {
        super(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
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
                ", estado=" + getEstado() +
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

    public void abrir(int abertura) {
        super.setEstado(Estado.ABERTO);
        if (abertura < 0) this.abertura = 0;
        else if (abertura > 100) this.abertura = 100;
        else this.abertura = abertura;
    }

    public void fechar(int fechamento) {
        super.setEstado(Estado.FECHADO);
        if (fechamento < 0) this.abertura = 0;
        else if (fechamento > 100) this.abertura = 100;
        else this.abertura = 100 - fechamento;
    }

}