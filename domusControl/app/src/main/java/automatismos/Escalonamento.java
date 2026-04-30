package automatismos;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import dispositivos.Dispositivo;

public class Escalonamento {
    private String id;
    private LocalDateTime hora;
    private List<Dispositivo> dispositivos;
    private Predicate<Dispositivo> condicao;
    private Consumer<Dispositivo> acao;

    public Escalonamento(String id, LocalDateTime hora, List<Dispositivo> dispositivos, Predicate<Dispositivo> condicao, Consumer<Dispositivo> acao) {
        this.id = id;
        this.hora = hora;
        this.dispositivos = dispositivos;
        this.condicao = condicao;
        this.acao = acao;
    }

    public Escalonamento(Escalonamento outro) {
        this.id = outro.id;
        this.hora = outro.hora;
        this.dispositivos = outro.dispositivos;
        this.condicao = outro.condicao;
        this.acao = outro.acao;
    }

    public Escalonamento() {
        this.id = "";
        this.hora = LocalDateTime.now();
        this.dispositivos = new ArrayList<>();
        this.condicao = d -> true; // Condição padrão que sempre retorna true
        this.acao = d -> {}; // Ação padrão que não faz nada
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public Predicate<Dispositivo> getCondicao() {
        return condicao;
    }

    public void setCondicao(Predicate<Dispositivo> condicao) {
        this.condicao = condicao;
    }

    public Consumer<Dispositivo> getAcao() {
        return acao;
    }

    public void setAcao(Consumer<Dispositivo> acao) {
        this.acao = acao;
    }

    public Escalonamento clone() {
        return new Escalonamento(this);
    }

    @Override
    public String toString() {
        return "Escalonamento{" +
                "id='" + id + '\'' +
                ", hora=" + hora +
                ", dispositivos=" + dispositivos +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Escalonamento that = (Escalonamento) obj;
        return id.equals(that.id) && hora.equals(that.hora) && dispositivos.equals(that.dispositivos) && condicao.equals(that.condicao) && acao.equals(that.acao);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, hora, dispositivos, condicao, acao);
    }

    public boolean verificaHora(LocalDateTime agora) {
        return this.hora.getHour() == agora.getHour() && this.hora.getMinute() == agora.getMinute();
    }

    public void executar() {
        this.dispositivos.stream()
            .filter(condicao)
            .forEach(acao);
    }
}