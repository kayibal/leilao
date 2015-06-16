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

	public boolean cadastrarUsuario(String nome, String endereco, Integer CPF, int telefone, String username, String senha) throws BusinessException {
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("username", username);
		ArrayList<Usuario> userlist = (ArrayList<Usuario>) Usuario.manager.fetch(filters);
		
		if(userlist!=null){
			if(userlist.size()>1) return false;
			
			String msg = "Username ja cadastrado no sistema\n";

			throw new BusinessException(msg);
		}
		
		Usuario u = new Usuario(nome, endereco, CPF, telefone, username, senha);
		u.save();
		
	}

	public boolean trocarSenha(int uid, String senhaNova) throws BusinessException {
		
		Usuario user = (Usuario) Usuario.manager.get(uid);
		user.setSenha(senhaNova);
		user.save();
			
	}

	public boolean fazerLogin(String username, String senha) throws BusinessException {
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("username", username);
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
				msg += "Usuario ou Senha incorretos\n";
			}
			throw new BusinessException(msg);
		}
	}
	
	public Usuario getUserFromID(int id){
		return (Usuario) Usuario.manager.get(id);
	}
	
	public int getLoggedUserID(){
		return this.loggedInUser;
	}
	
	private void setLoggedUserID(int id){
		this.loggedInUser = id;
	}

}
