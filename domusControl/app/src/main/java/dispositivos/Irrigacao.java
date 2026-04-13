package dispositivos;

import java.util.Objects;

public class Irrigacao extends Dispositivo {
    private int duracaoMinutos;
    private int caudal;
    private double ultimaChuva;

    public static final double CHUVA_MINIMA = 5.0; // mm — abaixo disto deve regar

    public Irrigacao(int identificador, String marca, String modelo,
                     double consumoPorHora, boolean ligado, int minutosLigado, int numActivacoes, int duracaoMinutos, int caudal) {
        super(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes);
        this.duracaoMinutos = duracaoMinutos;
        this.caudal = caudal;
        this.ultimaChuva = 0;
    }

    public Irrigacao() {
        super();
        this.duracaoMinutos = 0;
        this.caudal = 0;
        this.ultimaChuva = 0;
    }

    public Irrigacao(Irrigacao outraIrrigacao) {
        super(outraIrrigacao);
        this.duracaoMinutos = outraIrrigacao.duracaoMinutos;
        this.caudal = outraIrrigacao.caudal;
        this.ultimaChuva = outraIrrigacao.ultimaChuva;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(int duracaoMinutos) {
        if (duracaoMinutos < 0) this.duracaoMinutos = 0;
        else this.duracaoMinutos = duracaoMinutos;
    }

    public int getCaudal() {
        return caudal;
    }

    public void setCaudal(int caudal) {
        if (caudal < 0) this.caudal = 0;
        else this.caudal = caudal;
    }

    public double getUltimaChuva() {
        return ultimaChuva;
    }

    public void setUltimaChuva(double ultimaChuva) {
        this.ultimaChuva = ultimaChuva;
    }

    public Irrigacao clone() {
        return new Irrigacao(this);
    }

    @Override
    public String toString() {
        return "Irrigacao{" +
                "identificador=" + getIdentificador() +
                ", marca='" + getMarca() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", consumoPorHora=" + getConsumoPorHora() +
                ", ligado=" + getLigado() +
                ", minutosLigado=" + getMinutosLigado() +
                ", numActivacoes=" + getNumActivacoes() +
                ", duracaoMinutos=" + duracaoMinutos +
                ", caudal=" + caudal +
                ", ultimaChuva=" + ultimaChuva +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Irrigacao irrigacao = (Irrigacao) o;

        return super.equals(irrigacao) &&
                this.duracaoMinutos == irrigacao.duracaoMinutos &&
                this.caudal == irrigacao.caudal &&
                this.ultimaChuva == irrigacao.ultimaChuva;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duracaoMinutos, caudal, ultimaChuva);
    }

    public double calcularAguaConsumida() {
        return (duracaoMinutos / 60.0) * caudal;
    }

    // Chamado pelas automações — verifica se faz sentido regar
    public boolean iniciarAutomatico(SensorChuva sensorChuva) {
        if (sensorChuva.estaAChover()) {
            System.out.println("Irrigação cancelada — está a chover na zona ");
            return false;
        }
        if (sensorChuva.getPluviosidade() == 0 && ultimaChuva >= CHUVA_MINIMA) {
            System.out.println("Irrigação cancelada — chuva recente suficiente na zona");
            return false;
        }
        ligar();
        System.out.println("Irrigação automática iniciada na zona ");
        return true;
    }
}