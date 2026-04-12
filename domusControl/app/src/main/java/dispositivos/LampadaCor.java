package dispositivos;

public class LampadaCor extends Lampada {
    private int temperaturaCor;

    public LampadaCor(int identificador, String marca, String modelo,
                      double consumoPorHora, int intensidade, int temperaturaCor) {
        super(identificador, marca, modelo, consumoPorHora, intensidade);
        this.temperaturaCor = temperaturaCor;
    }

    public int getTemperaturaCor() {
        return temperaturaCor; 
    }

    public void setTemperaturaCor(int temperaturaCor) {
        if (temperaturaCor < 2700) this.temperaturaCor = 2700;
        else if (temperaturaCor > 4000) this.temperaturaCor = 4000;
        else this.temperaturaCor = temperaturaCor;
    }

    public void maisQuente() {          // aproxima-se dos 2700K (amarelo)
        setTemperaturaCor(this.temperaturaCor - 100);
    }

    public void maisFria() {            // aproxima-se dos 4000K (branco)
        setTemperaturaCor(this.temperaturaCor + 100);
    }

}
