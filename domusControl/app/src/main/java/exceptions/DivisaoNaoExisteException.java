package exceptions;

public class DivisaoNaoExisteException extends Exception {
    public DivisaoNaoExisteException(String id) {
        super("Divisão '" + id + "' não existe.");
    }
}