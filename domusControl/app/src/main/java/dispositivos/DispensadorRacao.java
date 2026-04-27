package dispositivos;

import java.util.Objects;

public class DispensadorRacao extends Dispositivo {
	private int nivelAgua;
	private int nivelRacao;

	public DispensadorRacao(int identificador, String marca, String modelo,
				 double consumoPorHora, Estado ligado, int minutosLigado, int numActivacoes,
				 int nivelAgua, int nivelRacao) {
		super(identificador, marca, modelo, consumoPorHora, ligado, minutosLigado, numActivacoes);
		setNivelAgua(nivelAgua);
		setNivelRacao(nivelRacao);
	}

	public DispensadorRacao() {
		super();
		this.nivelAgua = 100;
		this.nivelRacao = 100;
	}

	public DispensadorRacao(DispensadorRacao outraRacao) {
		super(outraRacao);
		this.nivelAgua = outraRacao.nivelAgua;
		this.nivelRacao = outraRacao.nivelRacao;
	}

	public int getNivelAgua() {
		return nivelAgua;
	}

	public int getNivelRacao() {
		return nivelRacao;
	}

	public void setNivelAgua(int nivelAgua) {
		if (nivelAgua < 0) this.nivelAgua = 0;
		else if (nivelAgua > 100) this.nivelAgua = 100;
		else this.nivelAgua = nivelAgua;
	}

	public void setNivelRacao(int nivelRacao) {
		if (nivelRacao < 0) this.nivelRacao = 0;
		else if (nivelRacao > 100) this.nivelRacao = 100;
		else this.nivelRacao = nivelRacao;
	}

	public DispensadorRacao clone() {
		return new DispensadorRacao(this);
	}

	@Override
	public String toString() {
		return "Raçao{" +
				"identificador=" + getIdentificador() +
				", marca='" + getMarca() + '\'' +
				", modelo='" + getModelo() + '\'' +
				", consumoPorHora=" + getConsumoPorHora() +
				", estado=" + getEstado() +
				", minutosLigado=" + getMinutosLigado() +
				", numActivacoes=" + getNumActivacoes() +
				", nivelAgua=" + nivelAgua +
				", nivelRacao=" + nivelRacao +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DispensadorRacao racao = (DispensadorRacao) o;
		return super.equals(racao)
				&& this.nivelAgua == racao.nivelAgua
				&& this.nivelRacao == racao.nivelRacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), nivelAgua, nivelRacao);
	}

	public boolean aguaVazia() {
		return this.nivelAgua == 0;
	}

	public boolean racaoVazia() {
		return this.nivelRacao == 0;
	}

	public void encherAguaSeVazia() {
		if (aguaVazia()) {
			this.nivelAgua = 100;
		}
	}

	public void encherRacaoSeVazia() {
		if (racaoVazia()) {
			this.nivelRacao = 100;
		}
	}
}
