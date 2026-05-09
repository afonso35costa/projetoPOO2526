package exceptions;

public class AutomacaoJaExisteException extends Exception {
    public AutomacaoJaExisteException(String id) {
        super("Automação '" + id + "' já existe.");
    }
}