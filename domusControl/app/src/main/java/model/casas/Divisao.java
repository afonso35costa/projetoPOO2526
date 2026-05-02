package model.casas;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import model.dispositivos.Dispositivo;

public class Divisao implements Serializable{
    private String id;
    private Map<String, Dispositivo> dispositivos;

    public Divisao(String id, Map<String, Dispositivo> dispositivos) {
        this.id = id;
        this.dispositivos = dispositivos.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public Divisao() {
        this.id = "";
        this.dispositivos = new HashMap<>();
    }

    public Divisao(Divisao outraDivisao) {
        this.id = outraDivisao.id;
        this.dispositivos = outraDivisao.dispositivos.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public String getId() {
        return id;
    }

    public Map<String, Dispositivo> getDispositivos() {
        return this.dispositivos.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDispositivos(Map<String, Dispositivo> dispositivos) {
        this.dispositivos = dispositivos.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().clone()));
    }

    public Divisao clone() {
        return new Divisao(this);
    }

    @Override
    public String toString() {
        return "Divisao{" +
                "id=" + id + ", dispositivos=" + dispositivos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Divisao divisao = (Divisao) o;

        return id == divisao.id && dispositivos.equals(divisao.dispositivos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dispositivos);
    }

    public int getNumDispositivos() { 
        return this.dispositivos.size(); 
    }

    public void adicionarDispositivo(Dispositivo dispositivo) {
        this.dispositivos.put(dispositivo.getIdentificador(), dispositivo);
    }

    public void removerDispositivo(String dispositivoId) {
        this.dispositivos.remove(dispositivoId);
    }

    public Dispositivo getDispositivoById(String dispositivoId) {
        return this.dispositivos.get(dispositivoId);
    }

    public void setEstadoDispositivo(String dispositivoId, Dispositivo.Estado estado) {
        Dispositivo dispositivo = this.dispositivos.get(dispositivoId);
        if (dispositivo != null) {
            dispositivo.setEstado(estado);
        }
    }

    public double calcularConsumoDivisao() {
        return dispositivos.values().stream()
            .mapToDouble(d -> (d.getMinutosLigado() / 60.0) * d.getConsumoPorHora())
            .sum();
    }
}