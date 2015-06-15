package persistence;

public interface ISerializable {
	int getId();
	void setId(int id);
	void save();
}
