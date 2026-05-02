package model.casas;

import java.io.Serializable;
import java.util.Objects;

import model.dispositivos.Dispositivo;
import utils.SerializableConsumer;
import utils.SerializablePredicate;

public class EstrategiaCenario implements Serializable{
    private SerializablePredicate<Dispositivo> condicao;
    private SerializableConsumer<Dispositivo> acao;

    public EstrategiaCenario(SerializablePredicate<Dispositivo> condicao, SerializableConsumer<Dispositivo> acao) {
        this.condicao = condicao;
        this.acao = acao;
    }

    public EstrategiaCenario() {
        this.condicao = d -> true; // Condição padrão que sempre retorna true
        this.acao = d -> {}; // Ação padrão que não faz nada
    }

    public EstrategiaCenario (EstrategiaCenario estrategia) {
        this.condicao = estrategia.condicao;
        this.acao = estrategia.acao;
    }

    public SerializablePredicate<Dispositivo> getCondicao() {
        return condicao; 
    }

    public SerializableConsumer<Dispositivo> getAcao() {
        return acao;
    }

    public void setCondicao(SerializablePredicate<Dispositivo> condicao) {
        this.condicao = condicao;
    }

    public void setAcao(SerializableConsumer<Dispositivo> acao) {
        this.acao = acao;
    }

    public EstrategiaCenario clone() {
        return new EstrategiaCenario(this);
    }

    @Override
    public String toString() {
        return "EstrategiaCenario{" +
                "condicao=" + condicao +
                ", acao=" + acao +
                '}';    
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EstrategiaCenario that = (EstrategiaCenario) obj;
        return Objects.equals(condicao, that.condicao) &&
               Objects.equals(acao, that.acao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condicao, acao);
    }
 
}