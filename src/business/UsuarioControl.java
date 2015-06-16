package business;

import java.util.ArrayList;
import java.util.HashMap;
import exceptions.BusinessException;

public class UsuarioControl {

	private int loggedInUser;

	public void cadastrarUsuario(String nome, String endereco, Integer CPF, int telefone, String username, String senha) throws BusinessException {
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("username", username);
		ArrayList<Usuario> userlist = (ArrayList<Usuario>) Usuario.manager.fetch(filters);
		
		if(userlist!=null){
			throw new BusinessException("Usuario não foi cadastrado: username já em uso\n");
		}
		
		try{
			Usuario u = new Usuario(nome, endereco, CPF, telefone, username, senha);
			u.save();
		} catch (BusinessException e) {
			throw new BusinessException("Usuario nao foi cadastrado:\n" + e.getMessage());
		}
		
		
		
	}

	public void trocarSenha(int uid, String senhaNova) throws BusinessException {
		
		Usuario user = (Usuario) Usuario.manager.get(uid);
		user.setSenha(senhaNova);
		user.save();
			
	}

	public void fazerLogin(String username, String senha) throws BusinessException {
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("username", username);
		filters.put("senha", senha);
		
		ArrayList<Usuario> userlist = (ArrayList<Usuario>) Usuario.manager.fetch(filters);
		
		
		if(userlist!=null){
			int id = userlist.get(0).getId();
			setLoggedUserID(id);
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
