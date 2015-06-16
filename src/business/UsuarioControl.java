package business;

import java.util.ArrayList;
import java.util.HashMap;
import exceptions.UserInputException;
import persistence.IGenericDAO;

public class UsuarioControl {

	private int loggedInUser;
	//private IGenericDAO<Usuario> usuarioDAO;
	
	/*public UsuarioControl(){
		usuarioDAO = new IGenericDAO<Usuario>();
		Usuario standard = new Usuario("Fulano", null, 0, "fulano", "password");
		usuarioDAO.save(standard);
		loggedInUser = usuarioDAO.getID(standard);
	}*/

	public boolean cadastrarUsuario(String nome, Endereco endereco, Integer CPF, int telefone, String username, String senha) {
		
		boolean validInput = true;
		String msg = "";
		
		if(!nome.matches("[\\w,\\/,_,-]{3,20}")){
			msg += "Campo nome invalido\n";
			validInput = false;
		}
		if(!endereco.matches("\\d{4}")){
			msg += "Campo ano invalido\n";
			validInput = false;
		}
		if(!CPF.matches("\\d{11}")){
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
			Usuario u = new Usuario(nome, endereco, CPF, telefone, username, senha);
			u.save();
			
			return true;
		} else {
			throw new UserInputException("Usuario nao foi cadastrado:\n" + msg);
		}
		
		
	}

	public void trocarSenha(int uid, String senhaNova) {
		
	}

	public boolean fazerLogin(String nome, String senha) {
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("nome", nome);
		filters.put("senha", senha);
		ArrayList<Usuario> userlist = (ArrayList<Usuario>) Usuario.manager.fetch(filters);
		
		
		if(userlist!=null){
			if(userlist.size()>1) return false;
		
			int id = userlist.get(0).getId();
			setLoggedUserID(id);
			
			return true;
		}
		else{
			//build Exception
			String msg = "Falha no login: \n";
			
			if(userlist==null){
				msg += "Usuario ou Senha incorretos.";
			}
			throw new UserInputException(msg);
		}
	}
	
	public Usuario getUserFromID(int id){
		return Usuario.manager.get(id);
	}
	
	public int getLoggedUserID(){
		return this.loggedInUser;
	}
	
	private void setLoggedUserID(int id){
		this.loggedInUser = id;
	}

}
