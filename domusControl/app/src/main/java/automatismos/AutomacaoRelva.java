package automatismos;

import dispositivos.Irrigacao;
import dispositivos.SensorChuva;
import dispositivos.SensorHumidadeSolo;

public class AutomacaoRelva implements Automacao {

    private Irrigacao irrigacao;
    private SensorChuva sensorChuva;
    private SensorHumidadeSolo sensorHumidade;

    public AutomacaoRelva(Irrigacao irrigacao, SensorChuva sensorChuva, SensorHumidadeSolo sensorHumidade) {
        this.irrigacao = irrigacao;
        this.sensorChuva = sensorChuva;
        this.sensorHumidade = sensorHumidade;
    }

    @Override
    public boolean verificar() {
        return !sensorChuva.estaAChover() && sensorHumidade.estaSeco();
    }

    @Override
    public void executar() {
        if (verificar()) {
            irrigacao.ligar();
            sensorHumidade.setHumidade(100);
        }
    }
}