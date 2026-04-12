package dispositivos;

public class ColunaDeSom extends Dispositivo {
    private int volume; // 0 a 100

    public ColunaDeSom(int identificador, String marca, String modelo,
                       double consumoPorHora, int volume) {
        super(identificador, marca, modelo, consumoPorHora);
        this.volume = volume;
    }

    public int getVolume() {
        return volume; }

    public void setVolume(int volume) {
        if (volume < 0) this.volume = 0;
        else if (volume > 100) this.volume = 100;
        else this.volume = volume;
    }

    public void maxVolume() {
        this.volume = 100;
    }

    public void minVolume() {
        this.volume = 0;
    }

    public void aumentarVolume(int incremento) {
        setVolume(this.volume + incremento);
    }

    public void diminuirVolume(int decremento) {
        setVolume(this.volume - decremento);
    }

}
