package business;

import java.util.HashMap;
import java.util.List;
import exceptions.BusinessException;
import persistence.IGenericDAO;
import java.util.ArrayList;

public class AnuncioControl {

	//private UsuarioControl usuarioControl;
	//private IGenericDAO<Anuncio> anuncioDAO;
	//private IGenericDAO<Leilao> leilaoDAO;
	//private IGenericDAO<Lance> lanceDAO;
	
	//public AnuncioControl(UsuarioControl uc){
		//this.usuarioControl = uc;
		//this.anuncioDAO = new IGenericDAO<Anuncio>();
		//this.leilaoDAO = new IGenericDAO<Leilao>();
		//this.lanceDAO = new IGenericDAO<Lance>();
		//this.createSampleData();
	//}

	public void aprovarAnuncio(int aid) {

	}

	public boolean criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) throws BusinessException {
		
	
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
		return Anuncio.manager.all();
	}

	public Anuncio getAnuncio(int aid) {
		return null;
	}

	public boolean fecharAnuncio(int aid) {
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
			Usuario u = (Usuario) Usuario.manager.get(uid);//this.usuarioControl.getUserFromID(uid);
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

	public List<Leilao> getLeiloes() {
		return null;
	}

	public Leilao getLeilao(int lid) {
		return null;
	}

	public boolean darPontucao(int aid, int p) {
		return false;
	}

	public boolean criarPergunta(String text, int aid) {
		return false;
	}

	public boolean criarResposta(String text, int pid) {
		return false;
	}

}