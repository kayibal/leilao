package business;

import exceptions.BusinessException;

public class Mediador extends Usuario {
	public Mediador(String nome, String endereco, Integer CPF, Integer telefone, String username, String senha) throws BusinessException{
		  
		super(nome, endereco, CPF, telefone, username, senha);
		 
	}

}