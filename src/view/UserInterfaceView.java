package view;

import business.AnuncioControl;
import business.UsuarioControl;
import business.Anuncio;

import java.util.Iterator;
import java.util.Scanner;
import java.util.ArrayList;

import exceptions.BusinessException;


public class UserInterfaceView {
	
	Scanner user_input ;
	private AnuncioControl ac;
	private UsuarioControl uc;
	
	public UserInterfaceView(){
		this.user_input = new Scanner(System.in);
		
		this.uc = new UsuarioControl();
		this.ac = new AnuncioControl();
	}
	/**
	 * shows the main menu
	 */
	public void mostrarMenuPrincipal() {
		//TODO ver quem esta logado
		
		String selection;
		System.out.println("Select your desired action by typing the according number:");
		System.out.println("1. Criar Anuncio");
		System.out.println("2. Realizar Lance");
		System.out.println("3. Mostrar Anuncios");
		System.out.println("4. Trocar senha");
		
		selection = user_input.next();
		switch(selection.trim()){
			case "1":
				this.criarAnuncio();
				break;
			case "2":
				this.darLance();
				break;
			case "3":
				//this.mostrarAnuncios();
				this.mostrarMenuPrincipal();
				break;
			case "4":
				this.trocarSenha();
				this.mostrarMenuPrincipal();
				break;
		}
		
	}
	/**
	 * starts the use case criarAnuncio
	 * allow the user to create a new Anuncio and save it
	 */
	private void criarAnuncio() {
		String modelo;
		System.out.println("Digite o modelo de seu carro");
		modelo = user_input.next();

		String ano;
		System.out.println("Digite o ano de seu carro");
		ano = user_input.next();
		
		String motor;
		System.out.println("Digite o motor de seu carro");
		motor = user_input.next();
		
		String placa;
		System.out.println("Digite a placa de seu carro");
		placa = user_input.next();
		
		String cor;
		System.out.println("Digite a cor de seu carro");
		cor = user_input.next();
		
		String marca;
		System.out.println("Digite a marca de seu carro");
		marca = user_input.next();
		
		String potencia;
		System.out.println("Digite a potencia de seu carro");
		potencia = user_input.next();
		
		String lanceMin;
		System.out.println("Digite o lance minimo de seu anuncio");
		lanceMin = user_input.next();
		
		try{
			ac.criarAnuncio(modelo, ano, motor, cor, placa, marca, potencia, lanceMin);
			System.out.println("O Anuncio foi criado com sucesso!");
			this.mostrarMenuPrincipal();
		}catch(BusinessException e){
			System.out.println(e.getMessage());
		}
		
	}
	/**
	 * Used to log users in
	 */
	public void fazerLogin() {
		String user,password;
		System.out.println("Sistema de Leilao");
		do{
			System.out.println("Digite seu usuario:");
			user=user_input.next();
			System.out.println("Digite sua senha:");
			password=user_input.next();
			try{
				uc.fazerLogin(user,password);
			}catch(BusinessException ex){
				System.out.println("Usuario ou senha incorretos. Tente novamente.");
			}
		}while(true);
	}
	/**
	 * password change
	 */
	private void trocarSenha() {
		String newpass1, newpass2;
		System.out.println("Digite sua nova senha:");
		newpass1=user_input.next();
		System.out.println("Confirme sua nova senha:");
		newpass2=user_input.next();
		if(newpass1==newpass2)
			try {
				uc.trocarSenha(uc.getLoggedUserID(), newpass1);
			} catch (BusinessException e) {
				e.getMessage();
			}
	}
/*
	private void mostrarLeiloes() {
		ArrayList<Anuncio> anuncios=ac.getAnuncios();
		Iterator<Anuncio> anunciosIt=anuncios.iterator();
		Integer i=0;
		Boolean error=false;
		while(anunciosIt.hasNext()){
			Anuncio anuncio=anunciosIt.next();
			System.out.println(i.toString() + ")" +
					anuncio.getMarca() + " " +
					anuncio.getModelo() + " " +
					anuncio.getAno() + " " +
					anuncio.getCor() + " " +
					"#" + anuncio.getId());
			i++;
		}
		do{
			System.out.println("Escolha um leilao:");
			Integer lid=Integer.parseInt(user_input.next());
			if(lid>=0 && lid<i){
				error=false;
				this.mostrarLeilao(anuncios.get(lid).getId());
			}else{
				System.out.println("Numero invalido. Tente novamente.");
				error=true;
			}
		}while(error);

	}

	private void mostrarLeilao(int id) {
		
	}
*/
	/**
	 * Use case dar lance
	 * add an lance to some Leilao
	 */
	private void darLance() {
		//TODO Refactor might be necessary
		int uid = this.uc.getLoggedUserID();
		this.mostrarLeiloesAtivas();
		String anuncioAlvo;
		System.out.println("Digite o leilao para o qual deseja realizar um lance");
		anuncioAlvo = user_input.next();

		int aid = Integer.parseInt(anuncioAlvo);

		String lanceValorString;
		System.out.println("Digite o valor do lance");
		lanceValorString = user_input.next();
		float lanceValor = Float.parseFloat(lanceValorString);

		try{
		  ac.darLance(uid, aid, lanceValor);
		  System.out.println("O Lance foi realizado com sucesso!");
		  this.mostrarMenuPrincipal();
		}catch(BusinessException e){
		  System.out.println(e.getMessage());
		}

	}
	/**
	 * shows all pending anuncios to a mediador
	 */
	private void mostrarAnunciosPendentes(){
		//TODO
	}
	
	/**
	 * shows all active Leilaoes
	 */
	private void mostrarLeiloesAtivas(){
		//TODO
	}
	/**
	 * Approvar Anuncio Use Case
	 * lets a mediador approve an Anuncio
	 */
	private void aprovarAnuncios() {
		//TODO
	}
	/**
	 * lets the mediador close some Anuncio
	 */
	private void fecharAnuncio() {
		//TODO
	}
	/**
	 * lets a winner add a rating to his winning Leilao
	 */
	private void darPontucao() {
		//TODO
	}

}
