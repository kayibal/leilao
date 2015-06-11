package persistence.dbmanager;

/*
 * This class should read configuration variables from an xml file
 * we will skip this for simplicity reasons
 */
public class SqlLiteConfiguration implements IDBConfiguration{
	private String DatabaseName = "leilaoDb";

	@Override
	public String getDatabaseName() {
		return DatabaseName;
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
