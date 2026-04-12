package dispositivos;

public class SensorChuva extends Dispositivo {
    private double pluviosidade; // mm/h
    private boolean estaACHover;

    public SensorChuva(int identificador, String marca, String modelo, double consumoPorHora) {
        super(identificador, marca, modelo, consumoPorHora);
        this.pluviosidade = 0;
        this.estaACHover = false;
    }

    public double getPluviosidade() {
        return pluviosidade;
    }

    public boolean isEstaACHover() {
        return estaACHover;
    }

    public void setPluviosidade(double pluviosidade) {
        this.pluviosidade = pluviosidade;
        this.estaACHover = pluviosidade > 0;
    }
}
