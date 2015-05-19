package business;

import java.util.ArrayList;
import java.util.List;

public class Leilao {

	private int maxParticipantes;

	private int maxLances;

	private int maxTempo;

	private int pontuacao;

	private List<Lance> lances;
	
	public Leilao(int maxParticipantes, int maxLances, int maxTempo){
		this.maxParticipantes = maxParticipantes;
		this.maxLances = maxLances;
		this.maxTempo = maxTempo;
		
		this.pontuacao = 0;//?
		this.lances = new ArrayList<>();
		
	}

	public int getMaxParticipantes() {
		return maxParticipantes;
	}

	public void setMaxParticipantes(int maxParticipantes) {
		this.maxParticipantes = maxParticipantes;
	}

	public int getMaxLances() {
		return maxLances;
	}

	public void setMaxLances(int maxLances) {
		this.maxLances = maxLances;
	}

	public int getMaxTempo() {
		return maxTempo;
	}

	public void setMaxTempo(int maxTempo) {
		this.maxTempo = maxTempo;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public List<Lance> getLances() {
		return lances;
	}

	public void setLances(List<Lance> lances) {
		this.lances = lances;
	}

}
