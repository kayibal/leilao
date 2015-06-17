package persistence;
/**
 * this interface describes an object which can be used with our GenericDAO
 * @author Alan
 *
 */
public interface ISerializable {
	/**
	 * gets this objects id in the database
	 * @return
	 */
	int getId();
	/**
	 * sets this objects id in the database
	 * @param id
	 */
	void setId(int id);
	/**
	 * saves or updates the instance in the database
	 */
	void save();
}
