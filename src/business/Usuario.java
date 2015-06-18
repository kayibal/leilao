package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import business.exceptions.BusinessException;


import persistence.UsuarioDAO;
import persistence.ISerializable;

public class Usuario implements ISerializable{
	
	public static UsuarioDAO manager = new UsuarioDAO();
	
	int id;

	private String nome;

	private Integer CPF;

	private String username;

	private String senha;

	private int telefone;

	private String endereco;
	public Usuario(String nome, String endereco, Integer CPF, int telefone, String username, String senha) throws BusinessException{
		this.setNome(nome);
		this.setEndereco(endereco);
		this.setCPF(CPF);
		this.setTelefone(telefone);
		this.setUsername(username);
		this.setSenha(senha);
		this.id = -1;
	}
	
	public Usuario(){}
	
	/**
	 * This pseudo constructor is used only by persistence to invoke an object from the database
	 * @param id
	 * @param nome
	 * @param cPF
	 * @param username
	 * @param senha
	 * @param telefone
	 * @param endereco
	 */
	public void setAttributes(int id, String nome, Integer cPF, String username,
			String senha, int telefone, String endereco) {
		this.id = id;
		this.nome = nome;
		CPF = cPF;
		this.username = username;
		this.senha = senha;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) throws BusinessException{
		if(nome.matches("[\\w,\\/,_,-, ]{6,30}")){
			this.nome = nome;
		}
		else{
			throw new BusinessException("Nome Invalido\n");
		}
	}

	public Integer getCPF() {
		return CPF;
	}

	public void setCPF(Integer cpf) {
		CPF = cpf;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws BusinessException{
		if(username.matches("[\\w,\\d,]{5,20}")){
			this.username = username;
		}
		else{
			throw new BusinessException("Username Invalido: " + username + "\n");
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws BusinessException {
		if(senha.matches("[\\w,\\d]{4,20}")){
			this.senha = senha;
		} else {
			throw new BusinessException("Senha Invalida\n");
		}
		
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}
	/**
	 * fetches all curent lances fresh from database
	 * @return
	 */
	public List<Lance> getAllLances() {
		HashMap<String,String> filters = new HashMap<String,String>();
		filters.put("usuario",Integer.toString(id));
		//@SuppressWarnings("unchecked")
		return (ArrayList<Lance>) (ArrayList<?>) Lance.manager.fetch(filters);
	}
	/**
	 * Returns the number of lances which have been already made for this instance
	 * @param leilao
	 * @return
	 */
	public int getNumLancesFromLeilao(Leilao leilao){
		int numLances=0;
		Iterator<Lance> itrUsuario=getAllLances().iterator();		
		while(itrUsuario.hasNext()){
			Lance lanceAtual=itrUsuario.next();
			Iterator<Lance> itrLeilao=leilao.getLances().iterator();
			while(itrLeilao.hasNext())
				if(lanceAtual.equals(itrLeilao.next()))
					numLances++;
		}
		return(numLances);
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) throws BusinessException{
		if(endereco.matches("[\\w,\\/,_,-]{5,40}")){
			this.endereco = endereco;
		}
		else{
			throw new BusinessException("Endereco Invalido\n");
		}
	}
	
	/**
	 * Checks if we already have reached our limit of lances for a given leilao
	 * it is expected that the given aid has already an active leilao
	 * @param aid
	 * @return
	 */
	public boolean limiteDeLancesAtingido(int aid){
		Usuario u = this;
		Anuncio a = (Anuncio) Anuncio.manager.get(aid);
		
		if(u.getNumLancesFromLeilao(a.getLeilao()) >=  a.getLeilao().getMaxLances()) return true;
		else return false;

	}
	/**
	 * shortcut method for saving or updating this instance in db
	 */
	public void save(){
		manager.save(this);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", CPF=" + CPF
				+ ", username=" + username + ", senha=" + senha + ", telefone="
				+ telefone + ", endereco=" + endereco
				+ "]";
	}
	


}
