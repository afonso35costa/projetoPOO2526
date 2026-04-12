public class Cortina{
    private int abertura;
    }


    public int getAbertura() {
        return abertura;
    }

    public void setAbertura(int abertura) {
        this.abertura = abertura;
    }

    public void abrirToda() {
        this.abertura = 100;
    }

    public void fecharToda() {
        this.abertura = 0;
    }