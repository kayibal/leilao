package persistence.dbmanager;

import java.sql.Connection;

public interface IDatabaseManager {
	
	public void setConfiguration(IDBConfiguration conf);
	
	public Connection getConnectionObject();
}
