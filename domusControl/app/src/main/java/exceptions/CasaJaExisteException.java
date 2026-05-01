package exceptions;

public class CasaJaExisteException extends Exception {
    public CasaJaExisteException(String id) {
        super("Casa '" + id + "' já existe.");
    }
}