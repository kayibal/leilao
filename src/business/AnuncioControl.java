package business;

import java.util.HashMap;
import java.util.List;
import exceptions.UserInputException;
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

	public boolean criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) throws UserInputException {
		
		boolean validInput = true;
		String msg = "";
		
		if(!modelo.matches("[\\w,\\/,_,-]{3,20}")){
			msg += "Campo modelo invalido\n";
			validInput = false;
		}
		if(!ano.matches("\\d{4}")){
			msg += "Campo ano invalido\n";
			validInput = false;
		}
		if(!motor.matches("[\\w,\\/,_,-]{3,20}")){
			msg += "Campo motor invalido\n";
			validInput = false;
		}
		if(!placa.matches("\\D{3}-\\d{4}")){
			msg += "Campo placa invalido\n";
			validInput = false;
		}
		if(!cor.matches("\\D{3,12}")){
			msg += "Campo cor invalido\n";
			validInput = false;
		}
		if(!marca.matches("[\\w,\\/,_,-]{3,20}")){
			msg += "Campo marca invalido\n";
			validInput = false;
		}
		if(!potencia.matches("\\d{4}")){
			msg += "Campo potencia invalido\n";
			validInput = false;
		}
		if(!lance.matches("\\d*.\\d*")){
			msg += "Campo lance minimo invalido\n";
			validInput = false;
		}
		
		if(validInput == true){
			Anuncio a = new Anuncio(modelo, Integer.parseInt(ano), motor, cor, placa,  marca, Integer.parseInt(potencia), Float.parseFloat(lance) );
			a.save();
			/*
			HashMap<String,String> filters = new HashMap<String,String>();
			filters.put("modelo","bmw");
			ArrayList<Anuncio> list = (ArrayList<Anuncio>) Anuncio.manager.fetch(filters);
			*/
			return true;
		} else {
			throw new UserInputException("Anuncio nao foi criado:\n" + msg);
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
		} catch (UserInputException e){
			
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

	public boolean darLance(int uid, int aid, float lanceValor) throws UserInputException {
		
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
			throw new UserInputException(msg);
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