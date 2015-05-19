package business;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

	private String nome;

	private Integer CPF;

	private String username;

	private String senha;

	private int telefone;

	private List<Lance> lances;

	private Endereco endereco;
	
	public Usuario(String nome, Endereco endereco, Integer CPF, String username, String senha){
		this.nome = nome;
		this.endereco = endereco;
		this.CPF = CPF;
		this.username = username;
		this.senha = senha;
		this.lances = new ArrayList<>();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
	
	public void addLance(Lance lance){
		this.lances.add(lance);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
