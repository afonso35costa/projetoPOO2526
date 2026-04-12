public class Dispositivo{
    private int identificador;
    private String marca;
    private String modelo;
    private double consumoPorHora;
    

    public Dispositivo(int identificador, String marca, String modelo, double consumoPorHora) {
        this.identificador = identificador;
        this.marca = marca;
        this.modelo = modelo;
        this.consumoPorHora = consumoPorHora;
    }
    

    public int getIdentificador() {
        return identificador;
    }
    public String getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }
    public double getConsumoPorHora() {
        return consumoPorHora;
    }
}