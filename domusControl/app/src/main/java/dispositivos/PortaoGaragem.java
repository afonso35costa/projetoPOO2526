package dispositivos;

import java.util.Objects;

public class PortaoGaragem extends Dispositivo{
    private int abertura;//0 a 100//

    public PortaoGaragem(int identificador, String marca, String modelo,
                      double consumoPorHora, boolean ligado, int minutosLigado, int numActivacoes, int abertura) {
        super(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes);
        this.abertura = abertura;
    }

    public PortaoGaragem() {
        super();
        this.abertura = 0;
    }

    public PortaoGaragem(PortaoGaragem outroPortao) {
        super(outroPortao);
        this.abertura = outroPortao.abertura;
    }

    public int getAbertura() {
        return abertura;
    }

    public void setAbertura(int abertura) {
        this.abertura = abertura;
    }

    public PortaoGaragem clone() {
        return new PortaoGaragem(this);
    }

    @Override
    public String toString() {
        return "PortaoGaragem{" +
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
        PortaoGaragem that = (PortaoGaragem) o;
        return super.equals(that) && this.abertura == that.abertura;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), abertura);
    }

    public void abrir() {
        this.abertura = 100;
    }

    public void fechar() {
        this.abertura = 0;
    }
}