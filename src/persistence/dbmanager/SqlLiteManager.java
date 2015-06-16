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
		if (this.connection == null){
			Connection c = null;
			try{
				Class.forName(JDBC_DRIVER);
				c = DriverManager.getConnection("jdbc:sqlite:" + config.getDatabaseName());
			} catch (Exception e){
				System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			    System.exit(0);
			}
			return c;
		} else {
			return connection; 
		}
	}
	
	public static SqlLiteManager getInstance(){
		if(SqlLiteManager.instance == null){
			SqlLiteManager.instance = new SqlLiteManager();
		}
		return SqlLiteManager.instance;
	}

}
