package dispositivos;

public class Dispositivo{
    private int identificador;
    private String marca;
    private String modelo;
    private double consumoPorHora;
    private boolean ligado;
    private int minutosLigado;
    private int numActivacoes;
    

    public Dispositivo(int identificador, String marca, String modelo, double consumoPorHora) {
        this.identificador = identificador;
        this.marca = marca;
        this.modelo = modelo;
        this.consumoPorHora = consumoPorHora;
        this.ligado = false;
        this.minutosLigado = 0;
        this.numActivacoes = 0;
    }
    
    public void ligar() {
        if (!ligado) {
            ligado = true;
            numActivacoes++;
        }
    }
    public void desligar() {
        if (ligado) {
            ligado = false;
        }
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
    public boolean isLigado() {
        return ligado;
    }
    public int getMinutosLigado() {
        return minutosLigado;
    }
    public int getNumActivacoes() {
        return numActivacoes;
    }
}