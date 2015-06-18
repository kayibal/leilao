package business;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

import business.exceptions.BusinessException;

public class AnuncioControl {

	/**
	 * standard constructor
	 */
	public AnuncioControl(){}
	
	/**
	 * Approves a Anuncio object by specifying a concrete Leilao for it.
	 * Executes a use case
	 * @param aid User selected anuncio id
	 * @param maxPart maximal number of participants
	 * @param maxLances maximal number of lances
	 * @param tempoLimite duration of this leilao
	 * @param dataHoraInicio date and time when it was started
	 */
	public void aprovarAnuncio(int aid, int maxPart, int maxLances, int tempoLimite, Date dataHoraInicio) {
		
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		Leilao l = new Leilao(maxPart, maxLances, tempoLimite, dataHoraInicio);
		a.setLeilao(l);
		a.save();
	}
	
	/**
	 * creates a new Anuncio object and saves it to the Database
	 * @param modelo
	 * @param ano
	 * @param motor
	 * @param cor
	 * @param placa
	 * @param marca
	 * @param potencia
	 * @param lance
	 * @throws BusinessException
	 */
	public void criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) throws BusinessException {
		
		try{
			Anuncio a = new Anuncio(modelo, Integer.parseInt(ano), motor, placa, cor,  marca, Integer.parseInt(potencia), Float.parseFloat(lance) );
			a.save();
		} catch (BusinessException e) {
			throw new BusinessException("Anuncio nao foi criado:\n" + e.getMessage());
		}
		
		
	}
	/*
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
*/
	
	/**
	 * Closes an auction and finds the highest bidder
	 * @param aid anuncio id selected by the user
	 */
	public void fecharAnuncio(int aid) throws BusinessException {
		
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		if(a.getLeilao()!=null) {
		
			if(!a.getLeilao().getLances().isEmpty()){
				Lance max = null;
				for(Lance l: a.getLeilao().getLances()){
					if(max==null || l.getValor()>max.getValor()) max = l;
				}
				a.getLeilao().setVencedorID(max.getId());
			}
			a.setFechado(true);
		} else {
			throw new BusinessException("Anuncio nao tem uma leilao asociada");
		}
	}
	
	public void fecharAnuncios() throws BusinessException{
		ArrayList<Anuncio> candidates = getLeiloesAtivas();
		for (Anuncio a:candidates){
			long now = (new Date()).getTime();
			long end = a.getLeilao().getDataInicio().getTime() + a.getLeilao().getMaxTempo();
			if(now > end){
				fecharAnuncio(a.getId());
			}
		}
	}
	
	/**
	 * Adds a lance to some Leilao
	 * @param uid userid of the logged user
	 * @param aid anuncio id selected by user
	 * @param lanceValor value for the lance
	 * @throws BusinessException
	 */
	public void darLance(int uid, int aid, float lanceValor) throws BusinessException {
		
		Usuario u = (Usuario) Usuario.manager.get(uid);
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		if(!u.limiteDeLancesAtingido(a.getId()) && a.valorMinimoObservado(lanceValor)){
			
			if(a.getLeilao() != null){
				Lance l = new Lance(lanceValor,a.getLeilao(),u);
				l.save();
			} else {
				throw new BusinessException("O anuncio selecionado nao tem nenhuma leilao");
			}
		} else {
			String msg = "Ocorreram os seguintes erros: \n";
			if(u.limiteDeLancesAtingido(a.getId())) msg += "O usuario atingiu seu limite de lances para este leilao \n";
			if(!a.valorMinimoObservado(lanceValor)) msg += "O valor informado esta abaixo do valor do lance minimo\n";
			throw new BusinessException(msg);
		}
	
	}
	
	/**
	 * Method to find all Anuncios without an active Leilao
	 * the SQL in the Business Layer is not so nice but there was no faster way
	 * @return List of Anuncios
	 */
	public ArrayList<Anuncio> getAnuncioPendentes(){
		return (ArrayList<Anuncio>)(ArrayList<?>) Anuncio.manager.fetch("`leilao` IS NULL");
	}
	
	/**
	 * Method to find all active Leiloes
	 * @return
	 */
	public ArrayList<Anuncio> getLeiloesAtivas(){
		return (ArrayList<Anuncio>)(ArrayList<?>) Anuncio.manager.fetch("`leilao` IS NOT NULL AND `fechado` = 0");
	}

}