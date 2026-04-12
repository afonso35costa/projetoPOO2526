package dispositivos;

public class Lampada extends Dispositivo{
    private int intensidade; //0 a 100//
    
    public Lampada(int identificador, String marca, String modelo, double consumoPorHora, int intensidade) {
        super(identificador, marca, modelo, consumoPorHora); // chama o construtor do Dispositivo
        this.intensidade = intensidade;
    }

    public int getIntensidade() {
        return intensidade;
    }

    public void setIntensidade(int intensidade) {
        if (intensidade < 0) this.intensidade = 0;
        else if (intensidade > 100) this.intensidade = 100;
        else this.intensidade = intensidade;
    }

    public void ligar() {
        super.ligar();
        this.intensidade = 100;
    }

    public void desligar() {
        super.desligar();
        this.intensidade = 0;
    }

    public void aumentarIntensidade(int incremento) {
        setIntensidade(this.intensidade + incremento);
    }

    public void diminuirIntensidade(int decremento) {
        setIntensidade(this.intensidade - decremento);
    }

}