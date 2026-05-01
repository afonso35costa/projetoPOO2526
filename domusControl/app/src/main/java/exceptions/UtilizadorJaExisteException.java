package exceptions;

public class UtilizadorJaExisteException extends Exception {
    public UtilizadorJaExisteException(String username) {
        super("Utilizador '" + username + "' já existe.");
    }
}