package exceptions;

public class AutomacaoNaoExisteException extends Exception {
    public AutomacaoNaoExisteException(String id) {
        super("Automação '" + id + "' não existe.");
    }
}