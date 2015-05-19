package persistence;
import business.Anuncio;
import java.util.ArrayList;

public class AnuncioDAO {
	
	private ArrayList<Anuncio> data;

	public boolean save(Anuncio entry) {
		return data.add(entry);
	}

	public Anuncio fetch(int ID) {
		return data.get(ID);
	}

	public Anuncio[] getAll() {
		return (Anuncio[]) data.toArray();
	}

	public void delete(Anuncio entry) {
		data.remove(entry);
	}

	public boolean update(int ID, Anuncio newEntry) {
		if(data.set(ID, newEntry) != null){
			return true;
		}
		return false;
	}

}
