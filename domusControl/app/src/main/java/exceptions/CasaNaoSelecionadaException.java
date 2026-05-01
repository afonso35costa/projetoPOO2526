package exceptions;

public class CasaNaoSelecionadaException extends Exception {
    public CasaNaoSelecionadaException() {
        super("Nenhuma casa selecionada.");
    }
}