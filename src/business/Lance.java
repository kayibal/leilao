package business;

import persistence.ISerializable;
import persistence.LanceDAO;
import persistence.LeilaoDAO;

public class Lance implements ISerializable{
	
	public static LanceDAO manager = new LanceDAO();
	
	int id;
	
	private Float valor;
	
	public Lance(Float valor){
		this.valor = valor;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void save(){
		manager.save(this);
	}
}
