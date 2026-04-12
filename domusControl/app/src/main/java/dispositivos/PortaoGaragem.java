public class PortaoGaragem extends Dispositivo{
    private int abertura;//0 a 100//
    
    public PortaoGaragem(int identificador, String marca, String modelo, double consumoPorHora, int abertura) {
        super(identificador, marca, modelo, consumoPorHora);
        this.abertura = abertura;
    }

    public int getAbertura() {
        return abertura;
    }

    public void setAbertura(int abertura) {
        this.abertura = abertura;
    }

    public void abrir() {
        this.abertura = 100;
    }

    public void fechar() {
        this.abertura = 0;
    }
}