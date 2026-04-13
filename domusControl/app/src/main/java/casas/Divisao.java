package casas;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import dispositivos.Dispositivo;

public class Divisao {
    private int id;
    private Set<Dispositivo> dispositivos;

    public Divisao(int id, Set<Dispositivo> dispositivos) {
        this.id = id;
        this.dispositivos = new HashSet<>(dispositivos);
    }

    public Divisao() {
        this.id = 0;
        this.dispositivos = new HashSet<>();
    }

    public Divisao(Divisao outraDivisao) {
        this.id = outraDivisao.id;
        this.dispositivos = new HashSet<>(outraDivisao.dispositivos);
    }

    public int getId() {
        return id;
    }
    

    public Set<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDispositivos(Set<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
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

    public void adicionarDispositivo(Dispositivo dispositivo) {
        this.dispositivos.add(dispositivo);
    }

    public int getNumDispositivos() { 
        return this.dispositivos.size(); 
    }

    public boolean removerDispositivo(int identificador) {
        return this.dispositivos.removeIf(d -> d.getIdentificador() == identificador);
    }

    public double calcularConsumoDivisao() {
        return dispositivos.stream()
            .mapToDouble(d -> (d.getMinutosLigado() / 60.0) * d.getConsumoPorHora())
            .sum();
    }
}