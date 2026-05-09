package exceptions;

public class CenarioNaoExisteException extends Exception {
    public CenarioNaoExisteException(String id) {
        super("Cenário '" + id + "' não existe.");
    }
}