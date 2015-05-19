package persistence;

import java.util.ArrayList;

public class BaseDAO<Type> {

	private ArrayList<Type> data;
	
	public BaseDAO(){
		this.data = new ArrayList<Type>();
	}

	public boolean save(Type entry) {
		return data.add(entry);
	}
	
	public int getID(Type object){
		return data.indexOf(object);
	}

	public Type fetch(int ID) {
		try{
			return data.get(ID);
		} catch (IndexOutOfBoundsException e){
			return null;
		}
	}

	public ArrayList<Type> getAll() {
		//return (Type[]) data.toArray();
		return this.data;
	}

	public boolean delete(Type entry) {
		return data.remove(entry);
	}

	public boolean update(int ID, Type newEntry) {
		try{
			if(data.set(ID, newEntry) != null){
				return true;
			}
			return false;
		} catch (IndexOutOfBoundsException e){
			return false;
		}
	}

}