package persistence;
import business.Endereco;
import business.Lance;
import business.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UsuarioDAO extends SqlGenericDAO{
	
	private static String tableName = "leiloes_usuario";
	protected static LinkedHashMap<String,String> fields;
	
	public UsuarioDAO() {
		super();
		fields = new LinkedHashMap<String,String>();
		fields.put("nome",		"TEXT");
		fields.put("cpf", "INTEGER");
		fields.put("username", "TEXT");
		fields.put("senha", "TEXT");
		fields.put("telefone", "INTEGER");
		fields.put("endereco", 	"TEXT");
	}
	
	@Override
	protected ISerializable getObject(ResultSet rs) throws SQLException {
		int id = rs.getInt("ID");
		String nome = rs.getString("nome");
		int CPF = rs.getInt("cpf");
		String username = rs.getString("username");
		String senha = rs.getString("senha");
		int telefone = rs.getInt("telefone");
		String endereco = rs.getString("endereco");
		
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("ID",Integer.toString(id));
		ArrayList<Lance> lances = (ArrayList<Lance>) Lance.manager.fetch(filters);
		
		Usuario u = new Usuario(nome, e, CPF, username, senha);
		u.setLances(lances);
		return u;
	}
	
	@Override
	public String serialize(ISerializable object) {
		if(object instanceof Usuario){
			Usuario u = (Usuario) object;
			
			StringBuilder lanceList = new StringBuilder();
			String delim ="";
			for(Lance l :u.getAllLances()){
				if (l.getId() < 0)
					l.save();
				lanceList.append(delim).append(l.getId());
				delim = ";";
			}
			
			Endereco e = u.getEndereco();
			if (e.getId() < 0)
				e.save();
			
			StringBuilder b = new StringBuilder();
			b.append(u.getNome()).append(", ");
			b.append(u.getCPF()).append(", ");
			b.append(u.getUsername()).append(", ");
			b.append(u.getSenha()).append(", ");
			b.append(u.getTelefone()).append(", ");
			b.append(lanceList).append(", ");
			b.append(e.getId());
			
			return b.toString();
			
		} else {
			throw new IllegalArgumentException("expected Usuario object");
		}
		
		return null;
	}
	
	@Override
	protected String getTableName() {
		return tableName;
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

