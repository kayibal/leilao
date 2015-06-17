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

	public void mostrarMenuPrincipal() {		
		Boolean keep=true;
		do{
			String selection;
			if(uc.getLoggedUserID() != 0){
				System.out.println("Select your desired action by typing the according number:");
				System.out.println("1. Criar Anuncio");
				System.out.println("2. Realizar Lance");
				System.out.println("3. Mostrar Anúncios");
				System.out.println("4. Trocar senha");
				System.out.println("5. Dar pontuacão");
				System.out.println("6. Logout");
				System.out.println("7. Sair");
	
				selection = user_input.next();
				switch(selection.trim()){
				case "1":
					this.criarAnuncio();
					break;
				case "2":
					this.darLance();
					break;
				case "3":
					this.mostrarAnuncios();
					break;
				case "4":
					this.trocarSenha();
					break;
				case "5":
					this.darPontucao(null);
					break;
				case "6":
					uc.fazerLogout();
					break;
				case "7":
					keep=false;
					break;
				default:
					System.out.println("Opcão inválida!");
					break;
				}
			}else if(uc.getLoggedUserID() == 0){
				System.out.println("Selecione a acao desejada:");
				System.out.println("1. Mostrar Anúncios");
				System.out.println("2. Realizar cadastro");
				System.out.println("3. Fazer login");
				System.out.println("4. Sair");
	
				selection = user_input.next();
				switch(selection.trim()){
				case "1":
					this.mostrarAnuncios();
					break;
				case "2":
					this.darLance();
					break;
				case "3":
					this.fazerLogin();
					break;
				case "4":
					keep=false;
					break;
				default:
					System.out.println("Opcão inválida!");
					break;
				}
			}else if(uc.mediadorLogado()){
				System.out.println("Selecione a acao desejada:");
				System.out.println("1. Aprovar anuncios");
				System.out.println("2. Logout");
				System.out.println("3. Sair");
	
				selection = user_input.next();
				switch(selection.trim()){
				case "1":
					this.criarAnuncio();
					break;
				case "2":
					uc.fazerLogout();
					break;
				case "4":
					keep=false;
					break;
				default:
					System.out.println("Opcão inválida!");
					break;
				}
			}
		}while(keep);
	}


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
		System.out.println("Digite o lance minimo de seu anúncio");
		lanceMin = user_input.next();
		
		try{
			ac.criarAnuncio(modelo, ano, motor, cor, placa, marca, potencia, lanceMin);
			System.out.println("O Anuncio foi criado com sucesso!");
			this.mostrarMenuPrincipal();
		}catch(BusinessException e){
			System.out.println(e.getMessage());
		}
		
	}

	public void fazerLogin() {
		String user,password;
		System.out.println("Sistema de Leilao");
		System.out.println("Digite seu usuário:");
		user=user_input.next();
		System.out.println("Digite sua senha:");
		password=user_input.next();
		try{
			uc.fazerLogin(user,password);
		}catch(BusinessException e){
			System.out.println(e.getMessage());
		}
	}

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

	private void mostrarAnuncios() {
		ArrayList<Anuncio> anuncios=ac.getLeiloesAtivas();
		Iterator<Anuncio> anunciosIt=anuncios.iterator();
		Integer i=0;
		while(anunciosIt.hasNext()){
			Anuncio anuncio=anunciosIt.next();
			System.out.println(i.toString() + ")" +
					anuncio.getMarca() + " " +
					anuncio.getModelo() + " " +
					anuncio.getAno() + " " +
					anuncio.getCor() + " " +
					anuncio.getPotencia() + " " +
					"#" + anuncio.getId());
			i++;
		}
	}


	private void mostrarLeilao(int id) {
		
	}

	private void darLance() {
		//TODO Refactor might be necessary
		int uid = this.uc.getLoggedUserID();
		this.mostrarLeiloesAtivas();
		String anuncioAlvo;
		System.out.println("Digite o leilão para o qual deseja realizar um lance");
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

	
	private void mostrarAnunciosPendentes(){
		//TODO
	}
	
	private void mostrarLeiloesAtivas(){
		//TODO
	}

	private void aprovarAnuncios() {
		//TODO
	}

	private void fecharAnuncio(Integer lid) {

	}

	private void darPontucao(Integer lid) {
		Integer pont;
		Boolean error=false;
		do{
			System.out.println("Insira a pontuacão:");
			pont=Integer.valueOf(user_input.next());
			if(pont>=0&&pont<=2){
				//anuncio.///////
				error=false;
			}else{
				error=true;
				System.out.println("O valor deve estar compreendido entre 0 e 2. Tente novamente.");
			}
		}while(error);
	}

}
