package Business;
import java.util.List;

public class AnuncioControl {

	private UsuarioControl usuarioControl;

	public void aprovarAnuncio(int aid) {

	}

	public boolean criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) {
		
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
			//save a to database
			return true;
		} else {
			System.out.println("Anuncio nao foi criado:\n" + msg);
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

	public boolean darLance(int uid, int aid) {
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
