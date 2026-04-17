package domus;

import java.util.Set;
import java.util.HashSet;
import casas.Casa;

public class DomusControl {
    private Set<Casa> casas;

    public DomusControl(DomusControl sistema) {
        this.casas = new HashSet<>(sistema.casas);
    }

    public DomusControl(Set<Casa> casas) {
        this.casas = casas;
    }

    public DomusControl() {
        this.casas = new HashSet<>();
    }

    public Set<Casa> getCasas() {
        return this.casas;
    }

    public void setCasas(Set<Casa> casas) {
        this.casas = casas;
    }

    public DomusControl clone() {
        return new DomusControl(this);
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
        if (!(o instanceof DomusControl)) return false;
        DomusControl sistema = (DomusControl) o;
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