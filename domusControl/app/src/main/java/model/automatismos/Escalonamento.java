package model.automatismos;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.dispositivos.Dispositivo;
import utils.SerializableConsumer;
import utils.SerializablePredicate;

public class Escalonamento implements Serializable{
    private String id;
    private LocalTime hora;
    private List<Dispositivo> dispositivos;
    private SerializablePredicate<Dispositivo> condicao;
    private SerializableConsumer<Dispositivo> acao;

    public Escalonamento(String id, LocalTime hora, List<Dispositivo> dispositivos, SerializablePredicate<Dispositivo> condicao, SerializableConsumer<Dispositivo> acao) {
        this.id = id;
        this.hora = hora;
        this.dispositivos = dispositivos;
        this.condicao = condicao;
        this.acao = acao;
    }

    public Escalonamento(Escalonamento outro) {
        this.id = outro.id;
        this.hora = outro.hora;
        this.dispositivos = new ArrayList<>(outro.dispositivos);
        this.condicao = outro.condicao;
        this.acao = outro.acao;
    }

    public Escalonamento() {
        this.id = "";
        this.hora = LocalTime.now();
        this.dispositivos = new ArrayList<>();
        this.condicao = d -> true;
        this.acao = d -> {};
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setDispositivos(List<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public SerializablePredicate<Dispositivo> getCondicao() {
        return condicao;
    }

    public void setCondicao(SerializablePredicate<Dispositivo> condicao) {
        this.condicao = condicao;
    }

    public SerializableConsumer<Dispositivo> getAcao() {
        return acao;
    }

    public void setAcao(SerializableConsumer<Dispositivo> acao) {
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

    public boolean verificaHora(LocalTime agora) {
        return this.hora.getHour() == agora.getHour() && this.hora.getMinute() == agora.getMinute();
    }

    public void executar() {
        this.dispositivos.stream()
            .filter(condicao)
            .forEach(acao);
    }
}