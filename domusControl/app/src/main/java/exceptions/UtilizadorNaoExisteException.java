package exceptions;

public class UtilizadorNaoExisteException extends Exception {
    public UtilizadorNaoExisteException(String username) {
        super("Utilizador '" + username + "' não existe.");
    }
}