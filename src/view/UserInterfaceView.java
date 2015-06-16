package view;

import business.AnuncioControl;
import business.UsuarioControl;
import business.Anuncio;
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
		this.ac = new AnuncioControl(this.uc);
	}

	public void mostrarMenuPrincipal() {
		
		String selection;
		System.out.println("Select your desired action by typing the according number:");
		System.out.println("1. Criar Anuncio");
		System.out.println("2. Realizar Lance");
		System.out.println("3. Mostrar Leilões");
		
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
				this.mostrarMenuPrincipal();
				break;
		}
		
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
		do{
			System.out.println("Digite seu usuário:");
			user=user_input.next();
			System.out.println("Digite sua senha:");
			password=user_input.next();
			try{
				uc.fazerLogin(user,password);
			}catch(BusinessException ex){
				System.out.println("Usuário ou senha incorretos. Tente novamente.");
			}
		}while(true);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	private void mostrarLeiloes() {

	}

	private void mostrarLeilao() {

	}

	private void darLance() {
		
		int uid = this.uc.getLoggedUserID();
		this.mostrarAnuncios();
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

	private void mostrarAnuncios() {
		
		ArrayList<Anuncio> all = this.ac.getAnuncios();

		for(int i = 0; i < all.size(); i++){
			System.out.println("--------- " + i + " -----------\n");
			
			System.out.println(all.get(i).toString());
			
			System.out.println("-----------------------\n");
		}
		

	}

	private void aprovarAnuncios() {

	}

	private void fecharAnuncio() {

	}

	private void mostrarPerguntas() {

	}

	private void fazerPergunta() {

	}

	private void darResposta() {

	}

	private void darPontucao() {

	}

}
