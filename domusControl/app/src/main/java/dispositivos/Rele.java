package dispositivos;

public class Rele extends Dispositivo implements DispositivoQueLiga{

    public Rele(String identificador, String marca, String modelo, double consumoPorHora, Estado estado, int minutosLigado, int numActivacoes){
        super(identificador, marca, modelo, consumoPorHora, estado, minutosLigado, numActivacoes);
    }

    public Rele() {
        super();
    }

    public Rele(Rele outroRele) {
        super(outroRele);
    }

    public Rele clone() {
        return new Rele(this);
    }

    @Override
    public String toString() {
        return "Rele{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", estado=" + getEstado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rele rele = (Rele) o;
        return super.equals(rele);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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

