package persistence.dbmanager;

/*
 * This class should read configuration variables from an xml file
 * we will skip this for simplicity reasons
 */
public class SqlLiteConfiguration implements IDBConfiguration{
	private String databaseName = "leilaoDb";

	@Override
	public String getDatabaseName() {
		return databaseName;
	}

	@Override
	public String getDatabaseHost() {
		return "";
	}

	@Override
	public String getDatabasePassword() {
		return "";
	}

	@Override
	public String getDatabaseUser() {
		return "";
	}
	
}
