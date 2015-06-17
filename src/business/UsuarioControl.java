package business;

import java.util.ArrayList;
import java.util.HashMap;
import exceptions.BusinessException;

public class UsuarioControl {

	private int loggedInUser;
	
	public UsuarioControl(){
		
	}
	/**
	 * registers a new user to the system
	 * @param nome
	 * @param endereco
	 * @param CPF
	 * @param telefone
	 * @param username
	 * @param senha
	 * @throws BusinessException
	 */
	public void cadastrarUsuario(String nome, String endereco, Integer CPF, int telefone, String username, String senha) throws BusinessException {
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("username", username);
		ArrayList<Usuario> userlist = (ArrayList<Usuario>)(ArrayList<?>) Usuario.manager.fetch(filters);
		
		if(userlist!=null){
			throw new BusinessException("Usuario nao foi cadastrado: username ja em uso\n");
		}
		
		try{
			Usuario u = new Usuario(nome, endereco, CPF, telefone, username, senha);
			u.save();
		} catch (BusinessException e) {
			throw new BusinessException("Usuario nao foi cadastrado:\n" + e.getMessage());
		}
		
		
		
	}
	/**
	 * changes the password for a given user
	 * @param uid the specified user
	 * @param senhaNova the new password
	 * @throws BusinessException in case new password is not conform with restrictions
	 */
	public void trocarSenha(int uid, String senhaNova) throws BusinessException {
		Usuario user = (Usuario) Usuario.manager.get(uid);
		user.setSenha(senhaNova);
		user.save();
	}
	
	/**
	 * Logs a User in and creates a new Session for him
	 * @param username
	 * @param senha
	 * @throws BusinessException
	 */
	public void fazerLogin(String username, String senha) throws BusinessException {
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("username", username);
		filters.put("senha", senha);
		
		ArrayList<Usuario> userlist = (ArrayList<Usuario>)(ArrayList<?>) Usuario.manager.fetch(filters);
		
		
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
	/**
	 * Finds a User by a given id in the database
	 * @param id a valid user id
	 * @return A user object or null if not found
	 */
	public Usuario getUserFromID(int id){
		return (Usuario) Usuario.manager.get(id);
	}
	
	/**
	 * Adds a rating to some Leilao
	 * it is only possible if the logged user is the winner of the leilao
	 * @param aid anuncio id with corresponding leilao
	 * @param p rating
	 * @throws BusinessException
	 */
	public void darPontucao(int aid, int p) throws BusinessException {
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		Leilao l = a.getLeilao();
		if(l.getVencedorID() > -1){
			l.setPontuacao(p);
			l.save();
		} else {
			throw new BusinessException("Voce nao pode dar pontuacao nesse leilao");
		}
	}
	
	public int getLoggedUserID(){
		return this.loggedInUser;
	}
	
	private void setLoggedUserID(int id){
		this.loggedInUser = id;
	}
	/**
	 * checks if the logged user is a mediador
	 * @return
	 */
	public boolean mediadorLogado(){
		return Usuario.manager.get(loggedInUser) instanceof Mediador;
	}

}
