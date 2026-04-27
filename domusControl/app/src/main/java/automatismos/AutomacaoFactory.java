package automatismos;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AutomacaoFactory {
    private static Map<String, Supplier<Automacao>> automacao = new HashMap<>();
    
    static {
        automacao.put("chuva", AutomacaoChuva::new);
        automacao.put("animais", AutomacaoEscalonamentoAnimais::new);
        automacao.put("luz_exterior", AutomacaoLuzExterior::new);
        automacao.put("relva", AutomacaoRelva::new);
        automacao.put("temperatura", AutomacaoTemperatura::new);
    }
    
    public static Automacao criarAutomacao(String tipo) {
        Supplier<Automacao> supplier = automacao.get(tipo.toLowerCase());
        if (supplier == null) throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
        return supplier.get();
    }
}