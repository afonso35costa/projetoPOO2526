package exceptions;

public class EscalonamentoJaExisteException extends Exception {
    public EscalonamentoJaExisteException(String id) {
        super("Escalonamento '" + id + "' já existe.");
    }
}