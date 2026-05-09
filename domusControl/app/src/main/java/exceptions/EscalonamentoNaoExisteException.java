package exceptions;

public class EscalonamentoNaoExisteException extends Exception {
    public EscalonamentoNaoExisteException(String id) {
        super("Escalonamento '" + id + "' não existe.");
    }
}