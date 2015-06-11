package persistence;

public interface IDatabaseManager {
	
	public void setConfiguration(DBConfiguration conf);
	
	public Connection getConnectionObject();
}
