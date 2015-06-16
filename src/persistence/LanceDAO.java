package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import business.Lance;
import business.Leilao;
import business.Usuario;

public class LanceDAO extends SqlGenericDAO{
	
	private static String tableName = "leiloes_lance";
	protected static LinkedHashMap<String,String> fields;
	
	public LanceDAO(){
		fields = new LinkedHashMap<String,String>();
		fields.put("valor",	"REAL NOT NULL");
		fields.put("usuario", "INTEGER NOT NULL");
		fields.put("leilao", "INTEGER NOT NULL");
	}
	
	@Override
	public String serialize(ISerializable object) {
		if(object instanceof Lance){
			Lance l = (Lance) object;
			StringBuilder b = new StringBuilder();
			
			if (l.getLeilao().getId() < 0)
				l.getLeilao().save();
			if (l.getUsuario().getId() < 0)
				l.getUsuario().save();
			
			b.append(l.getValor()).append(", ");
			b.append(l.getUsuario().getId()).append(", ");
			b.append(l.getLeilao().getId());
			
			return b.toString();
		} else {
			throw new IllegalArgumentException("expected Lance object");
		}	
		
	}

	@Override
	protected String getTableName() {
		return tableName;
	}

	@Override
	protected ISerializable getObject(ResultSet rs) throws SQLException {
		int id = rs.getInt("ID");
		Float valor = rs.getFloat("valor");
		int leilaoId = rs.getInt("leilao");
		int usuarioId = rs.getInt("usuario");
		Lance l = new Lance(valor, ((Leilao) Leilao.manager.get(leilaoId)), (Usuario) (Usuario.manager.get(usuarioId)));
		l.setId(id);
		return l;
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