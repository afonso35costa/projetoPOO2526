import java.util.ArrayList;
import java.util.List;

import dispositivos.Dispositivo;

public class Divisao {
    private String titulo;
    private List<Dispositivo> dispositivos;
    
    public Divisao(String titulo) {
        this.titulo = titulo;
        this.dispositivos = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Dispositivo> getDispositivos() {
        return dispositivos;
    }

    public void adicionarDispositivo(Dispositivo dispositivo) {
        dispositivos.add(dispositivo);
    }

    public int getNumDispositivos() { 
        return dispositivos.size(); 
    }

    public boolean removerDispositivo(int identificador) {
        return dispositivos.removeIf(d -> d.getIdentificador() == identificador);
    }
}