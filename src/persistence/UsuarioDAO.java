package persistence;
import business.Lance;
import business.Mediador;
import business.Usuario;
import business.exceptions.BusinessException;

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
		fields.put("mediador", "INTEGER");
		//System.out.println("UsuarioDAO constructed");
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
		
		Usuario u = new Usuario();
		u.setAttributes(id, nome, CPF, username, senha, telefone, endereco);
		if(rs.getInt("mediador") == 1){
			Mediador m = new Mediador();
			m.setAttributes(id, nome, CPF, username, senha, telefone, endereco);
			return m;
		}
		return u;
	}
	
	@Override
	public String serialize(ISerializable object) {
		if(object instanceof Usuario){
			int mediador = 0;
			if(object instanceof Mediador){
				mediador = 1;
			}
			Usuario u = (Usuario) object;
			
			String e = u.getEndereco();
			
			StringBuilder b = new StringBuilder();
			b.append("'").append(u.getNome()).append("'").append(", ");
			b.append(u.getCPF()).append(", ");
			b.append("'").append(u.getUsername()).append("'").append(", ");
			b.append("'").append(u.getSenha()).append("'").append(", ");
			b.append(u.getTelefone()).append(", ");
			b.append("'").append(e).append("'").append(", ");
			b.append(mediador);
			return b.toString();
			
		} else {
			throw new IllegalArgumentException("expected Usuario object");
		}
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

