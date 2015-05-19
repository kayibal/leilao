package business;

import java.util.List;
import persistence.BaseDAO;

public class AnuncioControl {

	private UsuarioControl usuarioControl;
	private BaseDAO<Anuncio> anuncioDAO;
	private BaseDAO<Leilao> leilaoDAO;
	private BaseDAO<Lance> lanceDAO;
	
	public AnuncioControl(){
		this.anuncioDAO = new BaseDAO<Anuncio>();
		this.leilaoDAO = new BaseDAO<Leilao>();
		this.lanceDAO = new BaseDAO<Lance>();
	}
	
	public void setUC(UsuarioControl uc){
		this.usuarioControl = uc;
	}

	public void aprovarAnuncio(int aid) {

	}

	public boolean criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) {
		
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
			//save a to database
			return true;
		} else {
			System.out.println("An�ncio nao foi criado:\n" + msg);
			return false;
		}
		
		
	}

	public List<Anuncio> getAnuncios() {
		return null;
	}

	public Anuncio getAnuncio(int aid) {
		return null;
	}

	public boolean fecharAnuncio(int aid) {
		return false;
	}
	
	public boolean limiteDeLancesAtingido(int uid, int aid){
		Usuario u = this.usuarioDAO.fetch(uid);
		Anuncio a = this.anuncioDAO.fetch(aid);
		
		if(u.getNumLancesFromLeilao(a.getLeilao()) >=  a.getLeilao().getMaxLances()) return true;
		else return false;
	}
	
	public boolean valorMinimoObservado(int aid, Float lanceValor){
		Anuncio a = this.anuncioDAO.fetch(aid);
		
		if(lanceValor >= a.getLanceMin()) return true;
		else return false;
	}

	public boolean darLance(int uid, int aid, int lanceValor) {
		Lance l = new Lance(lanceValor);
		
		Usuario u = this.usuarioDAO.fetch(uid);
		Anuncio a = this.anuncioDAO.fetch(aid);
		
		u.addLance(l);
		a.getLeilao().addLance(l);
		
		return true;
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