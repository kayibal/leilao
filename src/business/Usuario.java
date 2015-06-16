package business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import exceptions.BusinessException;

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

	private List<Lance> lances;

	private String endereco;
	
	public Usuario(String nome, String endereco, Integer CPF, int telefone, String username, String senha) throws BusinessException{
		this.setNome(nome);
		this.setEndereco(endereco);
		this.setCPF(CPF);
		this.setTelefone(telefone);
		this.setUsername(username);
		this.setSenha(senha);
		this.lances = new ArrayList<>();
		this.id = -1;
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
		if(!nome.matches("[\\w,\\/,_,-]{6,30}")){
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
		if(!username.matches("[\\w,\\d,]{5,20}")){
			this.username = username;
		}
		else{
			throw new BusinessException("Username Invalido\n");
		}
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) throws BusinessException {
		if(!senha.matches("[\\w,\\d]{4,20}")){
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

	public List<Lance> getAllLances() {
		return lances;
	}
	
	public int getNumLancesFromLeilao(Leilao leilao){
		int numLances=0;
		Iterator<Lance> itrUsuario=lances.iterator();		
		while(itrUsuario.hasNext()){
			Lance lanceAtual=itrUsuario.next();
			Iterator<Lance> itrLeilao=leilao.getLances().iterator();
			while(itrLeilao.hasNext())
				if(lanceAtual.equals(itrLeilao.next()))
					numLances++;
		}
		return(numLances);
	}
	
	public void addLance(Lance lance){
		this.lances.add(lance);
	}
	
	public void setLances(ArrayList<Lance> lances){
		this.lances = lances;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) throws BusinessException{
		if(!endereco.matches("[\\w,\\/,_,-]{5,40}")){
			this.endereco = endereco;
		}
		else{
			throw new BusinessException("Endereco Invalido\n");
		}
	}
	
	public void save(){
		manager.save(this);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", CPF=" + CPF
				+ ", username=" + username + ", senha=" + senha + ", telefone="
				+ telefone + ", lances=" + lances + ", endereco=" + endereco
				+ "]";
	}
	


}
