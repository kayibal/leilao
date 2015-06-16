package persistence;
import business.Anuncio;
import business.Leilao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnuncioDAO extends SqlGenericDAO{

	private static String tableName = "leiloes_anuncio";
	protected static LinkedHashMap<String,String> fields;
	
	public AnuncioDAO(){
		super();
		fields = new LinkedHashMap<String,String>();
		fields.put("modelo", 	"TEXT NOT NULL");
		fields.put("ano", 		"INTEGER NOT NULL");
		fields.put("motor", 	"TEXT NOT NULL");
		fields.put("placa",		"TEXT NOT NULL");
		fields.put("cor",		"TEXT NOT NULL");
		fields.put("marca", 	"TEXT NOT NULL");
		fields.put("potencia", 	"INTEGER NOT NULL");
		fields.put("lance_min", "REAL NOT NULL");
		fields.put("fechado", 	"INTEGER NOT NULL");
		fields.put("leilao", 	"INTEGER UNIQUE");	//can be null
	}
	
	@Override
	protected ISerializable getObject(ResultSet rs) throws SQLException{
		int id = rs.getInt("ID");
		String modelo = rs.getString("modelo");
		int ano = rs.getInt("ano");
		String motor = rs.getString("motor");
		String placa = rs.getString("placa");
		String cor = rs.getString("cor");
		String marca = rs.getString("marca");
		int potencia = rs.getInt("potencia");
		Float lanceMin = rs.getFloat("lance_min");
		boolean fechado = (1 == rs.getInt("fechado"));
		int leilaoId = rs.getInt("leilao");
		
		Leilao leilao = null;
		if(!rs.wasNull())
			leilao = (Leilao) Leilao.manager.get(leilaoId);
		
		Anuncio result = new Anuncio();
		result.setAttributes(id, modelo, ano, motor, placa, cor, marca, potencia, lanceMin, leilao, fechado);
		return  result;
	}
	
	@Override
	public String serialize(ISerializable object) {
		if(object instanceof Anuncio){
			Anuncio a = (Anuncio) object;
			StringBuilder b = new StringBuilder();
			b.append(a.getModelo()).append(", ");
			b.append(a.getAno()).append(", ");
			b.append(a.getMotor()).append(", ");
			b.append(a.getPlaca()).append(", ");
			b.append(a.getCor()).append(", ");
			b.append(a.getMarca()).append(", ");
			b.append(a.getPotencia()).append(", ");
			b.append(a.getLanceMin()).append(", ");
			if (a.getLeilao().getId() > 0){
				b.append(a.getLeilao().getId());
			} else {
				a.getLeilao().save();
				b.append(a.getLeilao().getId());
			}
			return b.toString();
		} else {
			throw new IllegalArgumentException("expected Anuncio object");
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
