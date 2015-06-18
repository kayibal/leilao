package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import business.Lance;
import business.Leilao;


public class LeilaoDAO extends SqlGenericDAO {
	
	protected static String tableName = "leiloes_leilao";
	protected static LinkedHashMap<String,String> fields;
	
	public LeilaoDAO(){
		fields = new LinkedHashMap<String,String>();
		fields.put("max_participantes",		"INTEGER");
		fields.put("max_lances", 			"INTEGER");
		fields.put("max_tempo", 			"INTEGER");
		fields.put("pontucao", 				"INTEGER");
		fields.put("data", 					"INTEGER");
		fields.put("vencedor", 				"INTEGER");
		System.out.println("LeilaoDAO constructed");
	}
	
	@Override
	protected ISerializable getObject(ResultSet rs) throws SQLException {
		int id = rs.getInt("ID");
		int maxParticipantes = rs.getInt("max_participantes");
		int maxLances = rs.getInt("max_lances");
		int maxTempo = rs.getInt("max_tempo");
		int pontuacao = rs.getInt("pontucao");
		long seconds = rs.getInt("data");
		int vencedor = rs.getInt("vencedor");
		
		Date data = new Date(seconds);
		Leilao leilao = new Leilao(maxParticipantes, maxLances, maxTempo, data);
		leilao.setPontuacao(pontuacao);
		leilao.setId(id);
		leilao.setVencedorID(vencedor);
		return leilao;
	}

	@Override
	public String serialize(ISerializable object) {
		if(object instanceof Leilao){
			Leilao l = (Leilao) object;
			StringBuilder b = new StringBuilder();
			b.append(l.getMaxParticipantes()).append(", ");
			b.append(l.getMaxLances()).append(", ");
			b.append(l.getMaxTempo()).append(", ");
			b.append(l.getPontuacao()).append(", ");
			b.append(l.getDataInicio().getTime()).append(", ");
			b.append(l.getVencedorID());
			
			return b.toString();
		} else {
			throw new IllegalArgumentException("expected Leilao object");
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
