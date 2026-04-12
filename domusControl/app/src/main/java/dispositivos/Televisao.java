package dispositivos;

public class Televisao extends Dispositivo {
    private int canal;
    private int volume;
    
    public Televisao(int identificador, String marca, String modelo,
                     double consumoPorHora, int canal, int volume) {
        super(identificador, marca, modelo, consumoPorHora);
        this.canal = canal;
        this.volume = volume;;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        if (canal < 1) this.canal = 1;
        else if (canal > 300) this.canal = 300;
        else this.canal = canal;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        if (volume < 0) this.volume = 0;
        else if (volume > 100) this.volume = 100;
        else this.volume = volume;
    }

    public void aumentarVolume(int incremento) {
        setVolume(this.volume + incremento);
    }

    public void diminuirVolume(int decremento) {
        setVolume(this.volume - decremento);
    }

    public void mudarCanal(int novoCanal) {
        setCanal(novoCanal);
    }

    public void proximoCanal() {
        setCanal(this.canal + 1); 
    }

    public void canalAnterior() {
        setCanal(this.canal - 1); 
    }
}