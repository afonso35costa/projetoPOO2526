package exceptions;

public class DispositivoNaoExisteException extends Exception {
    public DispositivoNaoExisteException(String id) {
        super("Dispositivo '" + id + "' não existe.");
    }
}