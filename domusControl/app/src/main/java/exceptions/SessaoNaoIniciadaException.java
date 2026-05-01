package exceptions;

public class SessaoNaoIniciadaException extends Exception {
    public SessaoNaoIniciadaException() {
        super("Sessão não iniciada. Faça login primeiro.");
    }
}