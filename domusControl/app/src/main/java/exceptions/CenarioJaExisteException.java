package exceptions;

public class CenarioJaExisteException extends Exception {
    public CenarioJaExisteException(String id) {
        super("Cenário '" + id + "' já existe.");
    }
}