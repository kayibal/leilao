package business;

import java.util.HashMap;
import java.util.List;
import exceptions.BusinessException;
import persistence.IGenericDAO;
import java.util.ArrayList;

public class AnuncioControl {

	//private UsuarioControl usuarioControl;
	//private IGenericDAO<Anuncio> anuncioDAO;
	//private IGenericDAO<Leilao> leilaoDAO;
	//private IGenericDAO<Lance> lanceDAO;
	
	//public AnuncioControl(UsuarioControl uc){
		//this.usuarioControl = uc;
		//this.anuncioDAO = new IGenericDAO<Anuncio>();
		//this.leilaoDAO = new IGenericDAO<Leilao>();
		//this.lanceDAO = new IGenericDAO<Lance>();
		//this.createSampleData();
	//}

	public void aprovarAnuncio(int aid) {

	}

	public void criarAnuncio(String modelo, String ano, String motor, String cor, String placa,  String marca, String potencia, String lance) throws BusinessException {
		
	
		try{
			Anuncio a = new Anuncio(modelo, Integer.parseInt(ano), motor, cor, placa,  marca, Integer.parseInt(potencia), Float.parseFloat(lance) );
			a.save();
		} catch (BusinessException e) {
			throw new BusinessException("Anuncio nao foi criado:\n" + e.getMessage());

		}
		
		
	}
	
	private void createSampleData(){
		String modelo = "Boxter";
		String ano = "1991";
		String cor = "vermelho";
		String placa = "abc-1234";
		String potencia = "2000";
		String motor = "super-23";
		String marca = "Porsche";
		String lance = "12000.00";
		try{
			criarAnuncio(modelo, ano, motor, cor, placa, marca, potencia, lance);
			criarAnuncio(modelo, ano, motor, cor, placa, marca, potencia, lance);
		} catch (BusinessException e){
			
		}
	}

	public boolean fecharAnuncio(int aid) {
		//TODO
		return false;
	}
	

	public void darLance(int uid, int aid, float lanceValor) throws BusinessException {
		
		Usuario u = (Usuario) Usuario.manager.get(uid);
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		
		if(!u.limiteDeLancesAtingido(a.getId()) && a.valorMinimoObservado(lanceValor)){
			
			if(a.getLeilao() != null){
				Lance l = new Lance(lanceValor,a.getLeilao(),u);
				l.save();
			} else {
				throw new BusinessException("O anuncio selecionado não tem nenhuma leilao");
			}
		} else {
			String msg = "Ocorreram os seguintes erros: \n";
			if(u.limiteDeLancesAtingido(a.getId())) msg += "O usuario atingiu seu limite de lances para este leilao \n";
			if(!a.valorMinimoObservado(lanceValor)) msg += "O valor informado esta abaixo do valor do lance minimo\n";
			throw new BusinessException(msg);
		}
	
	}
	
	public ArrayList<Anuncio> getAnuncioPendentes(){
		return (ArrayList<Anuncio>) Anuncio.manager.fetch("`leilao` IS NULL");
	}
	
	public ArrayList<Anuncio> getLeiloesAtivas(){
		return (ArrayList<Anuncio>) Anuncio.manager.fetch("`leilao` IS NOT NULL AND `fechado` = 0");
	}

}