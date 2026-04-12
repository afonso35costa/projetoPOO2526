package dispositivos;

public class SensorLuminosidade extends Dispositivo {
    private double luminosidade;
    
    public static final double LUX_NOITE = 10;      // <10 lux = noite
    public static final double LUX_PORDOSOL = 100; // <100 lux = anoitecer
    public static final double LUX_DIA = 1000;       // >1000 lux = dia claro

    public SensorLuminosidade (int identificador, String marca, String modelo, double consumoPorHora) {
        super(identificador, marca, modelo, consumoPorHora);
        this.luminosidade = 0;
    }

    public double getLuminosidade() {
        return luminosidade; 
    }
    
    public void setLuminosidade(double luminosidade) {
        if (luminosidade < 0) this.luminosidade = 0;
        else this.luminosidade = luminosidade;
    }

    // Para automações!
    public boolean isNoite() { 
        return luminosidade < LUX_NOITE; 
    }

    public boolean isPordosol() { 
        return luminosidade >= LUX_NOITE && luminosidade < LUX_PORDOSOL; 
    }

    public boolean isDia() { 
        return luminosidade >= LUX_DIA; 
    }

    public boolean deveAcenderLuzes() { 
        return luminosidade < LUX_PORDOSOL; // noite ou anoitecer
    }
}