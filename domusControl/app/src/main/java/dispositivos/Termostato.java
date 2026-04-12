package dispositivos;

public class Termostato extends Dispositivo {
    private double temperaturaAlvo;  // em graus Celsius
    private double temperaturaAtual;
    private String modo; // "aquecimento", "arrefecimento", "auto

    public Termostato(int identificador, String marca, String modelo, double consumoPorHora, double temperaturaAlvo,
                      double temperaturaAtual, String modo) {
        super(identificador, marca, modelo, consumoPorHora);
        this.temperaturaAlvo = temperaturaAlvo;
        this.temperaturaAtual = temperaturaAtual;
        this.modo = modo;
    }

    public double getTemperaturaAlvo() {
        return temperaturaAlvo;
    }

    public void setTemperaturaAlvo(double temperaturaAlvo) {
        if (temperaturaAlvo < 5) this.temperaturaAlvo = 5;
        else if (temperaturaAlvo > 35) this.temperaturaAlvo = 35;
        else this.temperaturaAlvo = temperaturaAlvo;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public void setTemperaturaAtual(double temperaturaAtual) {
        this.temperaturaAtual = temperaturaAtual;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public void aumentarTemperatura(double incremento) {
        setTemperaturaAlvo(this.temperaturaAlvo + incremento);
    }

    public void diminuirTemperatura(double decremento) {
        setTemperaturaAlvo(this.temperaturaAlvo - decremento);
    }
}
