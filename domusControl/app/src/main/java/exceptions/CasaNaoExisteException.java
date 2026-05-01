package exceptions;

public class CasaNaoExisteException extends Exception {
    public CasaNaoExisteException(String id) {
        super("Casa '" + id + "' não existe.");
    }
}