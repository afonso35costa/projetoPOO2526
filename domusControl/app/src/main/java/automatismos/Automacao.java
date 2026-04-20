package automatismos;

import java.util.List;

import dispositivos.Dispositivo;

public interface Automacao {
    boolean verificar(Dispositivo dispositivo);
    void executar(List<Dispositivo> dispositivos);
}
