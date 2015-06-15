package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import business.Lance;

public class LanceDAO extends SqlGenericDAO{
	
	private static String tableName = "leiloes_lance";
	protected static LinkedHashMap<String,String> fields;
	
	public LanceDAO(){
		fields = new LinkedHashMap<String,String>();
		fields.put("valor",	"REAL");
	}
	
	@Override
	public String serialize(ISerializable object) {
		return "valor";
	}

	@Override
	protected String getTableName() {
		return tableName;
	}

	@Override
	protected ISerializable getObject(ResultSet rs) throws SQLException {
		Float valor = rs.getFloat("valor");
		return new Lance(valor);
	}
	
	@Override
	protected String getFields() {
		if(fields != null){
			StringBuilder b = new StringBuilder();
			String delim = "";
			for (String field : fields.keySet()){
				b.append(delim).append(field);
				delim = ", ";
			}
			return b.toString();
		} else {
			throw new NullPointerException("Must initialize fields");
		}
	}
	
	@Override
	protected String getFieldsWithTypes() {
		StringBuilder b =  new StringBuilder();
		String delim = "";
		for (Map.Entry<String,String> entry : fields.entrySet()){
			b.append(delim).append(entry.getKey()).append(" ").append(entry.getValue());
			delim = ", ";
		}
		return b.toString();
	}	

}