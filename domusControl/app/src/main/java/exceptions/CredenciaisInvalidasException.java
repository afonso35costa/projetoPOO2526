package exceptions;

public class CredenciaisInvalidasException extends Exception {
    public CredenciaisInvalidasException() {
        super("Username ou password incorretos.");
    }
}