package persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import persistence.dbmanager.IDatabaseManager;
import persistence.dbmanager.SqlLiteManager;

public abstract class SqlGenericDAO implements IGenericDAO<ISerializable> {
	
	private SqlLiteManager manager;
	protected static String tableName = null;
	
	protected SqlGenericDAO(){
		this.manager = SqlLiteManager.getInstance();
	}
	
	@Override
	public void setDatabaseManager(IDatabaseManager m) {
		if( m instanceof SqlLiteManager){
			this.manager = (SqlLiteManager) m;
		} else {
			throw new IllegalArgumentException("Expected an instance of SqlLiteManager");
		}
	}

	@Override
	public int save(ISerializable Obj) {
		String query;
		if(Obj.getId() < 0){
			query = "INSERT INTO " + getTableName() + " (" + getFields() + ") VALUES (" + serialize(Obj) + ");";
		} else {
			String[] fields = getFields().split(",");
			String[] values = serialize(Obj).split(",");
			StringBuilder b = new StringBuilder();
			if(fields.length == values.length){
				for (int i = 0; i < fields.length; i++){
					try{
						Double.parseDouble(values[i]);
						b.append(fields[i]).append(" = ").append(values[i]);
					} catch(NumberFormatException e){
						b.append(fields[i]).append(" = ").append(values[i]);
					}
					if(i < fields.length - 1){
						b.append(", ");
					}
				}
			} else {
				throw new RuntimeException("Field and Value size don't match\n" + Arrays.toString(fields) + "\n" + Arrays.toString(values));
			}
			query = "UPDATE " + getTableName() + " SET " + b.toString() + " WHERE ID = " + Obj.getId();
		}
		try(
			Connection c = manager.getConnectionObject();
			Statement stmt = c.createStatement();
			Statement stmt2 = c.createStatement();
			){
			//System.out.println(query);
			int affectedRows = stmt.executeUpdate(query);
			//System.out.println(affectedRows);
			if(affectedRows > 0){
				ResultSet affectedKeys = stmt2.executeQuery("SELECT last_insert_rowid()");
				if(affectedKeys.next()){
					//Maybe change id to long
					Obj.setId(affectedKeys.getInt(1));
					//System.out.println(Obj.getId());
					affectedKeys.close();
					return Obj.getId();
				} else {
					throw new SQLException("No updated or inserted id obtained");
				}
			} else {
				throw new SQLException("No rows where affected");
			}
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public ISerializable get(int id) {
		Connection c = manager.getConnectionObject();
		String query = "SELECT * FROM " + getTableName() + " WHERE ID = " + Integer.toString(id);
		try(
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query)
			){
			if(rs.next()){	
				ISerializable result = getObject(rs);
				return result;
			} else {
				return null;
				//throw new SQLException("there were no records found with this id");
			}
		} catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * this method is used to obtain all entries in the database
	 * @return a list of ISerialzable
	 */
	public List<? extends ISerializable> all(){
		ArrayList<ISerializable> result = new ArrayList<ISerializable>();
		try(
			Connection c = manager.getConnectionObject();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + getTableName() + ";");
			){
			while(rs.next()){
				result.add(getObject(rs));
			}
			return result;
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<ISerializable> fetch(Map<String, String> filters) {
		ArrayList<ISerializable> result = new ArrayList<ISerializable>();
		StringBuilder b = new StringBuilder();
		String delim = "";
		for(String lookup:filters.keySet()){
			b.append(delim);
			b.append(lookup).append(" = ");
			try{
				Double.parseDouble(filters.get(lookup));
				b.append(filters.get(lookup));
			} catch(NumberFormatException e){
				b.append("'").append(filters.get(lookup)).append("'");
			}
			delim = " AND ";
		}
		try(
			Connection c = manager.getConnectionObject();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + getTableName() + " WHERE " + b.toString() + ";");
		){
			while(rs.next()){
				result.add(getObject(rs));
			}
			return result;
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * Custom fetch method allows to specify an SQL string inserted after the WHERE statement in the query
	 * @param filters custom SQL query string
	 * @return a list of ISerializables
	 */
	public List<ISerializable> fetch(String filters) {
		
		ArrayList<ISerializable> result = new ArrayList<ISerializable>();
		try(
			Connection c = manager.getConnectionObject();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + getTableName() + " WHERE " + filters + ";");
		){
			while(rs.next()){
				result.add(getObject(rs));
			}
			return result;
		} catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * shortcut mehtod to check if a given id has an object representation in the database
	 * @param id
	 * @return
	 */
	public Boolean exists(int id){
		if(get(id) != null){
			return true;
		}
		return false;
	}

	@Override
	public void initial() {
		try (
			Connection c = manager.getConnectionObject();
			Statement stmt = c.createStatement();
			){
			String query = "CREATE TABLE IF NOT EXISTS " + getTableName() + "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," + getFieldsWithTypes() + ");";
			stmt.executeUpdate(query);
			//stmt.close();
			//c.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * returns values of attributes as serialized string for saving in database
	 * values must be in same order as in getFields method
	 */
	public abstract String serialize(ISerializable object);
	
	/**
	 * Access the tablename this Entity refers to
	 * @return SqlLite database tablename refering to linked entity
	 */
	protected abstract String getTableName();

	/**
	 * Create an object from a ResultSet
	 * @throws SQLException 
	 */
	protected abstract ISerializable getObject(ResultSet rs) throws SQLException;
	/**
	 * Method used to specify the fields this objects posseses in the db
	 * Must be of same size as getfieldsWithType method
	 * @return a list of fieldnames 
	 */
	protected abstract String getFields();
	
	/**
	 * Method used to specify the fields this objects posseses in the db and also their type an other SQL specific constraints etc.
	 * Must be of same size as getFields method
	 * @return a list of fieldnames 
	 */
	protected abstract String getFieldsWithTypes();
}
