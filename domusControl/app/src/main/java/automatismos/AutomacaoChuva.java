package automatismos;

import java.util.List;

import dispositivos.Cortina;
import dispositivos.Dispositivo;
import dispositivos.SensorChuva;

public class AutomacaoChuva implements Automacao{
    @Override
    public boolean verificar(Dispositivo dispositivo) {
        if (dispositivo instanceof SensorChuva) {
            SensorChuva sensor = (SensorChuva) dispositivo;
            return sensor.estaAChover();
        }
        return false;
    }

    @Override
    public void executar(List<Dispositivo> dispositivos) {
        dispositivos.stream()
            .filter(d -> d instanceof Cortina)
            .map(d -> (Cortina) d)
            .filter(c -> c.getEstado() == Dispositivo.Estado.ABERTO)
            .forEach(c -> c.fechar(100));
    }
}