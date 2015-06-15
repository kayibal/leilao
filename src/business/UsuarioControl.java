package business;
import persistence.IGenericDAO;

public class UsuarioControl {

	private int loggedInUser;
	private IGenericDAO<Usuario> usuarioDAO;
	
	public UsuarioControl(){
		usuarioDAO = new IGenericDAO<Usuario>();
		Usuario standard = new Usuario("Fulano", null, 0, "fulano", "password");
		usuarioDAO.save(standard);
		loggedInUser = usuarioDAO.getID(standard);
	}

	public void cadastrarUsuario(Usuario user) {

	}

	public void trocarSenha(int uid, String senhaNova) {

	}
	
	public boolean existsUsuario(int id){
		if(this.usuarioDAO.fetch(id) != null){
			return true;
		}
		return false;
	}

	public boolean fazerLogin(String nome, String senha) {
		return false;
	}
	
	public Usuario getUserFromID(int id){
		return usuarioDAO.fetch(id);
	}
	
	public int getLoggedUserID(){
		return this.loggedInUser;
	}

}
