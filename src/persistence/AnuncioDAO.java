package persistence;
import business.Anuncio;
import business.Leilao;
import java.util.ArrayList;

public class AnuncioDAO {
	
	private ArrayList<Anuncio> dataAnuncio;

	public boolean save(Anuncio entry) {
		return dataAnuncio.add(entry);
	}

	public Anuncio fetch(int ID) {
		return dataAnuncio.get(ID);
	}

	public Anuncio[] getAll() {
		return (Anuncio[]) dataAnuncio.toArray();
	}

	public boolean delete(Anuncio entry) {
		return dataAnuncio.remove(entry);
	}

	public boolean update(int ID, Anuncio newEntry) {
		if(dataAnuncio.set(ID, newEntry) != null){
			return true;
		}
		return false;
	}

}
