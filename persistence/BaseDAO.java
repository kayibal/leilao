package persistence;

import java.util.Map;
import java.util.List;

public abstract class BaseDAO<Type> {
	
	IDatabaseManager manager;
	
	public void setDatabaseManager(IDatabaseManager m){
		this.manager = m;
	}
	/**
	 * This method updates the Obj if it has an id or saves the object if there is no id set yet
	 * @param Obj The Object to be saved
	 * @return -1 if there was an error, an id corresponding to the saved object if update or save was successfull
	 */
	protected int save(Type Obj){
		return -1;
	}
	
	/**
	 * Retrieves a single object from the databse given the primary key
	 * @param id
	 * @return
	 */
	protected Type get(int id){
		return null;
	}
	/**
	 * returns a list of objects from the database given some filters
	 * @param filters a map of strings
	 * @return
	 */
	protected List<Type> fetch(Map<String,String> filters){
		return null;
	}
	
	/**
	 * serializes the object into a database language
	 * @param object
	 * @return serialization string
	 */
	protected abstract String serialize(Type object);

}