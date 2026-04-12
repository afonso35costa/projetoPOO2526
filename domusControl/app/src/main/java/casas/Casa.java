import java.util.ArrayList;
import java.util.List;

public class Casa{
    private String nome;
    private String morada;
    private List<Divisao> divisoes;

    public Casa(String nome, String morada) {
        this.nome = nome;
        this.morada = morada;
        this.divisoes = new ArrayList<>();
    }    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada () {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public List<Divisao> getDivisoes() {
        return divisoes;
    }

}