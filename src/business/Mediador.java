package business;

import exceptions.BusinessException;

public class Mediador extends Usuario {
	public Mediador(String nome, Integer tel, String username, String senha) throws BusinessException{
		super();
		this.setAttributes(-1, "", 0, "", "", 0, "");
		this.setUsername(username);
		this.setSenha(senha);
		this.setTelefone(tel);
	}
	public Mediador(){}

}