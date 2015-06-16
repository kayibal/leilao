package business;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import exceptions.BusinessException;
import persistence.IGenericDAO;
import java.util.ArrayList;

public class AnuncioControl {
	
	public AnuncioControl(){}

	public void aprovarAnuncio(int aid, int maxPart, int maxLances, int tempoLimite, Date dataHoraInicio) {
		
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		Leilao l = new Leilao(maxPart, maxLances, tempoLimite, dataHoraInicio);
		a.setLeilao(l);
		a.save();
	}

	public void criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) throws BusinessException {
		
		try{
			Anuncio a = new Anuncio(modelo, Integer.parseInt(ano), motor, cor, placa,  marca, Integer.parseInt(potencia), Float.parseFloat(lance) );
			a.save();
		} catch (BusinessException e) {
			throw new BusinessException("Anuncio nao foi criado:\n" + e.getMessage());
		}
		
		
	}
	
	private void createSampleData(){
		String modelo = "Boxter";
		String ano = "1991";
		String cor = "vermelho";
		String placa = "abc-1234";
		String potencia = "2000";
		String motor = "super-23";
		String marca = "Porsche";
		String lance = "12000.00";
		try{
			criarAnuncio(modelo, ano, motor, cor, placa, marca, potencia, lance);
			criarAnuncio(modelo, ano, motor, cor, placa, marca, potencia, lance);
		} catch (BusinessException e){
			
		}
	}

	public ArrayList<Anuncio> getAnuncios() {
		return (ArrayList<Anuncio>) Anuncio.manager.all();
	}

	public Anuncio getAnuncio(int aid) {
		return null;
	}

	public boolean fecharAnuncio(int aid) {
		
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		if(a.getLeilao()==null) return false;
		
		if(!a.getLeilao().getLances().isEmpty()){
			Lance max = null;
			for(Lance l: a.getLeilao().getLances()){
				if(max==null || l.getValor()>max.getValor()) max = l;
			}
			a.getLeilao().setVencedorID(max.getId());
		}
		a.setFechado(true);
		
		return false;
	}
	
	private boolean limiteDeLancesAtingido(int uid, int aid){
		Usuario u = (Usuario) Usuario.manager.get(uid);
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		if(u.getNumLancesFromLeilao(a.getLeilao()) >=  a.getLeilao().getMaxLances()) return true;
		else return false;
	}
	
	private boolean valorMinimoObservado(int aid, Float lanceValor){
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		if(lanceValor >= a.getLanceMin()) return true;
		else return false;
	}

	public boolean darLance(int uid, int aid, float lanceValor) throws BusinessException {
		
		if(Anuncio.manager.exists(aid) && 
				Usuario.manager.exists(uid) &&
				!this.limiteDeLancesAtingido(uid, aid) && 
				this.valorMinimoObservado(aid, lanceValor)){
			
			Lance l = new Lance(lanceValor);
			Usuario u = (Usuario) Usuario.manager.get(uid);
			Anuncio a = (Anuncio) Anuncio.manager.get(aid);
			
			u.addLance(l);
			a.getLeilao().addLance(l);
			
			return true;
			
		} else {
			//build Exception
			String msg = "Ocorreram os seguintes erros: \n";
			if(Anuncio.manager.exists(aid) && Usuario.manager.exists(uid)){
				if(this.limiteDeLancesAtingido(uid, aid)) msg += "O usuario atingiu seu limite de lances para este leilao \n";
			
				if(!this.valorMinimoObservado(aid, lanceValor)) msg += "O valor informado esta abaixo do valor do lance minimo\n";
			} else {
				if( !Anuncio.manager.exists(aid) ) msg += "Anuncio nao foi encontrado";
				if( !Usuario.manager.exists(uid) ) msg += "Usuario nao foi encontrado";
			}
			throw new BusinessException(msg);
		}
	}

	public boolean darPontucao(int aid, int p) {
		return false;
	}

}