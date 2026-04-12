package dispositivos;

public class Irrigaçao extends Dispositivo {
    private int duracaoMinutos;
    private int caudal;
    private double ultimaChuva;

    public static final double CHUVA_MINIMA = 5.0; // mm — abaixo disto deve regar

    public Irrigaçao(int identificador, String marca, String modelo, double consumoPorHora, int duracaoMinutos, int caudal) {
        super(identificador, marca, modelo, consumoPorHora);
        this.duracaoMinutos = duracaoMinutos;
        this.caudal = caudal;
        this.ultimaChuva = 0;
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

    public double calcularAguaConsumida() {
        return (duracaoMinutos / 60.0) * caudal;
    }

    // Chamado pelas automações — verifica se faz sentido regar
    public boolean iniciarAutomatico(SensorChuva sensorChuva) {
        if (sensorChuva.isEstaACHover()) {
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