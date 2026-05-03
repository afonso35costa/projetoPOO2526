package exceptions;

public class UtilizadorNaoRemovivelException extends Exception {
    public UtilizadorNaoRemovivelException(String username) {
        super("Utilizador '" + username + "' não pode ser removido.");
    }
}