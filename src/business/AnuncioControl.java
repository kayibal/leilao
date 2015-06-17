package business;

import java.sql.Date;
import exceptions.BusinessException;
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
	

	public void darLance(int uid, int aid, float lanceValor) throws BusinessException {
		
		Usuario u = (Usuario) Usuario.manager.get(uid);
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		if(!u.limiteDeLancesAtingido(a.getId()) && a.valorMinimoObservado(lanceValor)){
			
			if(a.getLeilao() != null){
				Lance l = new Lance(lanceValor,a.getLeilao(),u);
				l.save();
			} else {
				throw new BusinessException("O anuncio selecionado não tem nenhuma leilao");
			}
		} else {
			String msg = "Ocorreram os seguintes erros: \n";
			if(u.limiteDeLancesAtingido(a.getId())) msg += "O usuario atingiu seu limite de lances para este leilao \n";
			if(!a.valorMinimoObservado(lanceValor)) msg += "O valor informado esta abaixo do valor do lance minimo\n";
			throw new BusinessException(msg);
		}
	
	}
	
	public ArrayList<Anuncio> getAnuncioPendentes(){
		return (ArrayList<Anuncio>)(ArrayList<?>) Anuncio.manager.fetch("`leilao` IS NULL");
	}
	
	public ArrayList<Anuncio> getLeiloesAtivas(){
		return (ArrayList<Anuncio>)(ArrayList<?>) Anuncio.manager.fetch("`leilao` IS NOT NULL AND `fechado` = 0");
	}

}