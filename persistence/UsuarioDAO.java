package persistence;
import business.Usuario;
import java.util.ArrayList;

public class UsuarioDAO {
	
	private ArrayList<Usuario> data;

	public boolean save(Usuario entry) {
		return data.add(entry);
	}

	public Usuario fetch(int ID) {
		return data.get(ID);
	}

	public Usuario[] getAll() {
		return (Usuario[]) data.toArray();
	}

	public void delete(Usuario entry) {
		data.remove(entry);
	}

	public boolean update(int ID, Usuario newEntry) {
		if(data.set(ID, newEntry) != null){
			return true;
		}
		return false;
	}

}

