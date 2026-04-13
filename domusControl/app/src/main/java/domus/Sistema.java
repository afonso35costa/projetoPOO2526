package domus;

import java.util.Set;
import java.util.HashSet;
import casas.Casa;

public class Sistema {
    private Set<Casa> casas;

    public Sistema(Sistema sistema) {
        this.casas = new HashSet<>(sistema.casas);
    }

    public Sistema(Set<Casa> casas) {
        this.casas = casas;
    }

    public Sistema() {
        this.casas = new HashSet<>();
    }

    public Set<Casa> getCasas() {
        return this.casas;
    }

    public void setCasas(Set<Casa> casas) {
        this.casas = casas;
    }

    public Sistema clone() {
        return new Sistema(this);
    }

    @Override
    public String toString() {
        return "Sistema{" +
                "casas=" + casas.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sistema)) return false;
        Sistema sistema = (Sistema) o;
        return casas.equals(sistema.casas);
    }

    @Override
    public int hashCode() {
        return casas.hashCode();
    }

    public void adicionarCasa(Casa casa) {
        this.casas.add(casa);
    }

    public void removerCasa(Casa casa) {
        this.casas.remove(casa);
    }

}