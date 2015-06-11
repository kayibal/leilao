package persistence.dbmanager;

import java.sql.*;

public class SqlLiteManager implements IDatabaseManager {
	static final String JDBC_DRIVER = "org.sqlite.JDBC";
	private SqlLiteConfiguration config;
	private Connection connection = null;
	@Override
	public void setConfiguration(IDBConfiguration conf) {
		if (conf instanceof SqlLiteConfiguration){
			this.config = (SqlLiteConfiguration) conf;
		} else {
			//TODO throw some db error and exit programm as error would be fatal
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

}
