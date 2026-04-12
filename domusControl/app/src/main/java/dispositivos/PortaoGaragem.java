public class PortaoGaragem{
    private int abertura;
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