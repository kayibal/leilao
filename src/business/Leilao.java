package business;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import persistence.ISerializable;
import persistence.LeilaoDAO;

public class Leilao implements ISerializable{
	public Leilao(Integer maxParticipantes, Integer maxLances, Integer maxTempo){
		this.maxParticipantes=maxParticipantes;
		this.maxLances=maxLances;
		this.maxTempo=maxTempo;
	}
	
	public static LeilaoDAO manager = new LeilaoDAO();

	int id;
	
	private int maxParticipantes;

	private int maxLances;

	private int maxTempo;
	
	private Date dataInicio;
	
	private int vencedorID;

	private int pontuacao;
	
	public Leilao(int maxParticipantes, int maxLances, int maxTempo, Date dataInicio){
		this.maxParticipantes = maxParticipantes;
		this.maxLances = maxLances;
		this.maxTempo = maxTempo;
		this.id = -1;
		this.setDataInicio(dataInicio);
		//this.pontuacao = 0;//?
		this.setVencedorID(-1);
	}
	
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
	
	@SuppressWarnings("unchecked")
	public List<Lance> getLances() {
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("leilao",Integer.toString(id));
		return (ArrayList<Lance>)(ArrayList<?>) Lance.manager.fetch(filters);
	}
	
	public void save(){
		manager.save(this);
	}



	public Date getDataInicio() {
		return dataInicio;
	}



	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}



	public int getVencedorID() {
		return vencedorID;
	}



	public void setVencedorID(int vencedorID) {
		this.vencedorID = vencedorID;
	}

}
