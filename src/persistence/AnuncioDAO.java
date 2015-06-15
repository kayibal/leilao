package persistence;
import business.Anuncio;
import business.Leilao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnuncioDAO extends SqlGenericDAO{

	private static String tableName = "leilao_anuncio";
	protected static LinkedHashMap<String,String> fields;
	
	public AnuncioDAO(){
		super();
		fields = new LinkedHashMap<String,String>();
		fields.put("modelo", 	"TEXT");
		fields.put("ano", 		"INTEGER");
		fields.put("motor", 	"TEXT");
		fields.put("placa",		"TEXT");
		fields.put("cor",		"TEXT");
		fields.put("marca", 	"TEXT");
		fields.put("potencia", 	"INTEGER");
		fields.put("lance_min", "REAL");
		fields.put("leilao", 	"INTEGER");	
	}
	
	@Override
	protected ISerializable getObject(ResultSet rs) throws SQLException{
		String modelo = rs.getString("modelo");
		int ano = rs.getInt("ano");
		String motor = rs.getString("motor");
		String placa = rs.getString("placa");
		String cor = rs.getString("cor");
		String marca = rs.getString("marca");
		int potencia = rs.getInt("potencia");
		Float lanceMin = rs.getFloat("lance_min");
		int leilaoId = rs.getInt("leilao");
		LeilaoDAO leilaoManager = new LeilaoDAO();
		Leilao leilao = (Leilao) leilaoManager.get(leilaoId);
		Anuncio result = new Anuncio(modelo, ano, motor, placa, cor, marca, potencia, lanceMin);
		result.setLeilao(leilao);
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
				b.append(a.getLeilao().getId()).append(", ");
			} else {
				a.getLeilao().save();
				b.append(a.getLeilao().getId()).append(", ");
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