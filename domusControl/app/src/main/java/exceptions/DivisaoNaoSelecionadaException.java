package exceptions;

public class DivisaoNaoSelecionadaException extends Exception {
    public DivisaoNaoSelecionadaException() {
        super("Nenhuma divisão selecionada.");
    }
}