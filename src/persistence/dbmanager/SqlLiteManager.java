package persistence.dbmanager;

import java.sql.*;

public class SqlLiteManager implements IDatabaseManager {
	private static SqlLiteManager instance;
	private static final String JDBC_DRIVER = "org.sqlite.JDBC";
	private static SqlLiteConfiguration config;
	private Connection connection = null;
	
	private SqlLiteManager(){
		config = new SqlLiteConfiguration();
	}
	
	@Override
	public void setConfiguration(IDBConfiguration conf) {
		if (conf instanceof SqlLiteConfiguration){
			config = (SqlLiteConfiguration) conf;
		} else {
			throw new IllegalArgumentException("Expected instance of SqlLiteConfiguration");
		}
	}

	@Override
	public Connection getConnectionObject() {
		try{
			if (this.connection == null || this.connection.isClosed()){
				Connection c = null;
				Class.forName(JDBC_DRIVER);
				c = DriverManager.getConnection("jdbc:sqlite:" + config.getDatabaseName());
				return c;
			} else {
				return connection; 
			}
		}catch (SQLException e){
			throw new RuntimeException(e);
		}catch(ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * this object is a singleton and this method returns the unique instance
	 * @return
	 */
	public static SqlLiteManager getInstance(){
		if(SqlLiteManager.instance == null){
			SqlLiteManager.instance = new SqlLiteManager();
		}
		return SqlLiteManager.instance;
	}

}
