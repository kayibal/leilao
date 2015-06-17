package persistence.dbmanager;
/**
 * A wrapper class to store meta information about a Database configuration
 * @author Alan
 *
 */
public interface IDBConfiguration {
	String getDatabaseName();
	String getDatabaseHost();
	String getDatabasePassword();
	String getDatabaseUser();
}
