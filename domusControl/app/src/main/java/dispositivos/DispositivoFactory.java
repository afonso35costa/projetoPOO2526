package dispositivos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DispositivoFactory {
    private static Map<String, Supplier<Dispositivo>> dispositivos = new HashMap<>();
    
    static {
        dispositivos.put("lampada", Lampada::new);
        dispositivos.put("televisao", Televisao::new);
        dispositivos.put("cortina", Cortina::new);
        dispositivos.put("sensor_chuva", SensorChuva::new);
        dispositivos.put("sensor_humidade_solo", SensorHumidadeSolo::new);
        dispositivos.put("sensor_luminosidade", SensorLuminosidade::new);
        dispositivos.put("termostato", Termostato::new);
        dispositivos.put("portao_garagem", PortaoGaragem::new);
        dispositivos.put("rele", Rele::new);
        dispositivos.put("lampada_cor", LampadaCor::new);
        dispositivos.put("coluna_som", ColunaDeSom::new);
        dispositivos.put("dispensador_racao", DispensadorRacao::new);
        dispositivos.put("irrigacao", Irrigacao::new);
    }
    
    public static Dispositivo criarDispositivo(String tipo) {
        Supplier<Dispositivo> supplier = dispositivos.get(tipo.toLowerCase());
        if (supplier == null) throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
        return supplier.get();
    }
}