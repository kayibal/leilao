package persistence.dbmanager;

import java.sql.Connection;

public interface IDatabaseManager {
	
	/**
	 * setter for the Configuration
	 * @param conf
	 */
	public void setConfiguration(IDBConfiguration conf);
	
	/**
	 * Used by other classes to obtain the Connection object
	 * @return
	 */
	public Connection getConnectionObject();
}
