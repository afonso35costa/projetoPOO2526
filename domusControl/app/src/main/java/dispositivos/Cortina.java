package dispositivos;
    
    public class Cortina extends Dispositivo{
    private int abertura;//0 a 100//

    public Cortina(int identificador, String marca, String modelo, double consumoPorHora, int abertura) {
        super(identificador, marca, modelo, consumoPorHora);
        this.abertura = abertura;
    }

    public int getAbertura() {
        return abertura;
    }

    public void setAbertura(int abertura) {
        if (abertura < 0) this.abertura = 0;
        else if (abertura > 100) this.abertura = 100;
        else this.abertura = abertura;  
    }

    public void abrirToda() {
        this.abertura = 100;
    }

    public void fecharToda() {
        this.abertura = 0;
    }

    public void abrirParcial(int incremento) {
        setAbertura(this.abertura + incremento);
    }

    public void fecharParcial(int decremento) {
        setAbertura(this.abertura - decremento);
    }

}