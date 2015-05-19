package business;

import java.util.List;
import exceptions.UserInputException;
import persistence.BaseDAO;
import java.util.ArrayList;

public class AnuncioControl {

	private UsuarioControl usuarioControl;
	private BaseDAO<Anuncio> anuncioDAO;
	private BaseDAO<Leilao> leilaoDAO;
	private BaseDAO<Lance> lanceDAO;
	
	public AnuncioControl(UsuarioControl uc){
		this.usuarioControl = uc;
		this.anuncioDAO = new BaseDAO<Anuncio>();
		this.leilaoDAO = new BaseDAO<Leilao>();
		this.lanceDAO = new BaseDAO<Lance>();
		this.createSampleData();
	}

	public void aprovarAnuncio(int aid) {

	}

	public boolean criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) throws UserInputException {
		
		boolean validInput = true;
		String msg = "";
		
		if(!modelo.matches("[\\w,\\/,_,-]{3,20}")){
			msg += "Campo modelo inv�lido\n";
			validInput = false;
		}
		if(!ano.matches("\\d{4}")){
			msg += "Campo ano inv�lido\n";
			validInput = false;
		}
		if(!motor.matches("[\\w,\\/,_,-]{3,20}")){
			msg += "Campo motor inv�lido\n";
			validInput = false;
		}
		if(!placa.matches("\\D{3}-\\d{4}")){
			msg += "Campo placa inv�lido\n";
			validInput = false;
		}
		if(!cor.matches("\\D{3,12}")){
			msg += "Campo cor inv�lido\n";
			validInput = false;
		}
		if(!marca.matches("[\\w,\\/,_,-]{3,20}")){
			msg += "Campo marca inv�lido\n";
			validInput = false;
		}
		if(!potencia.matches("\\d{4}")){
			msg += "Campo pot�ncia inv�lido\n";
			validInput = false;
		}
		if(!lance.matches("\\d*.\\d*")){
			msg += "Campo lance m�nimo inv�lido\n";
			validInput = false;
		}
		
		if(validInput == true){
			Anuncio a = new Anuncio(modelo, Integer.parseInt(ano), motor, cor, placa,  marca, Integer.parseInt(potencia), Float.parseFloat(lance) );
			this.anuncioDAO.save(a);
			return true;
		} else {
			throw new UserInputException("An�ncio nao foi criado:\n" + msg);
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
		return this.anuncioDAO.getAll();
	}

	public Anuncio getAnuncio(int aid) {
		return null;
	}

	public boolean fecharAnuncio(int aid) {
		return false;
	}
	
	private boolean limiteDeLancesAtingido(int uid, int aid){
		Usuario u = this.usuarioControl.getUserFromID(uid);
		Anuncio a = this.anuncioDAO.fetch(aid);
		
		if(u.getNumLancesFromLeilao(a.getLeilao()) >=  a.getLeilao().getMaxLances()) return true;
		else return false;
	}
	
	private boolean valorMinimoObservado(int aid, Float lanceValor){
		Anuncio a = this.anuncioDAO.fetch(aid);
		
		if(lanceValor >= a.getLanceMin()) return true;
		else return false;
	}

	public boolean darLance(int uid, int aid, float lanceValor) throws UserInputException {
		
		if(this.existsAnuncio(aid) && 
				this.usuarioControl.existsUsuario(uid) &&
				!this.limiteDeLancesAtingido(uid, aid) && 
				this.valorMinimoObservado(aid, lanceValor)){
			
			Lance l = new Lance(lanceValor);
			Usuario u = this.usuarioControl.getUserFromID(uid);
			Anuncio a = this.anuncioDAO.fetch(aid);
			
			u.addLance(l);
			a.getLeilao().addLance(l);
			
			return true;
			
		} else {
			//build Exception
			String msg = "Aconteceram os seguintes erros: \n";
			if(this.existsAnuncio(aid) && this.usuarioControl.existsUsuario(uid)){
				if(this.limiteDeLancesAtingido(uid, aid)) msg += "O usuario atingiu seu limite de lances para este leilao \n";
			
				if(!this.valorMinimoObservado(aid, lanceValor)) msg += "O valor informado esta abaixo do valor do lance minimo\n";
			} else {
				if( !this.existsAnuncio(aid) ) msg += "Anuncio nao foi encontrado";
				if( !this.usuarioControl.existsUsuario(uid) ) msg += "Usuario nao foi encontrado";
			}
			throw new UserInputException(msg);
		}
	}
	
	public boolean existsAnuncio(int id){
		if(this.anuncioDAO.fetch(id) != null){
			return true;
		}
		return false;
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