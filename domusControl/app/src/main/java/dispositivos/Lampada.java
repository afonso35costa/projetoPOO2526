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
        this.intensidade = intensidade;
    }
}