package persistence;

import java.util.Map;
import java.util.List;

import persistence.dbmanager.IDatabaseManager;

public interface IGenericDAO<T> {
	
	
	public void setDatabaseManager(IDatabaseManager m);
	/**
	 * This method updates the Obj if it has an id or saves the object if there is no id set yet
	 * @param Obj The Object to be saved
	 * @return -1 if there was an error, an id corresponding to the saved object if update or save was successfull
	 */
	int save(T Obj);
	
	/**
	 * Retrieves a single object from the databse given the primary key
	 * @param id
	 * @return
	 */
	T get(int id);
	/**
	 * returns a list of objects from the database given some filters
	 * @param filters a map of strings
	 * @return
	 */
	List<ISerializable> fetch(Map<String,String> filters);
	
	/**
	 * This method is run on start of the application and is used to create or update changed tables
	 * in sql would be: CREATE IF NOT EXISTS .....
	 */
	void initial();
	
	/**
	 * serializes the object into a database language
	 * @param object
	 * @return serialization string
	 */
	abstract String serialize(T object);

}