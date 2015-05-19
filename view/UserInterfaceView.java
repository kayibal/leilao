package view;

import business.AnuncioControl;
import business.UsuarioControl;
import java.util.Scanner;


public class UserInterfaceView {
	
	Scanner user_input ;
	private AnuncioControl ac;
	private UsuarioControl uc;
	
	UserInterfaceView(){
		user_input = new Scanner(System.in);
	}

	public void mostrarMenuPrincipal() {
		
		String selection;
		System.out.println("Select your desired action by typing the according number:");
		System.out.println("1. Criar Anúncio");
		System.out.println("2. Realizar Lance");
		
		selection = user_input.next();
		switch(selection.trim()){
			case "1":
				this.criarAnuncio();
				break;
			case "2":
				this.darLance();
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
		System.out.println("Digite a potência de seu carro");
		potencia = user_input.next();
		
		String lanceMin;
		System.out.println("Digite o lance mínimo de seu anúncio");
		lanceMin = user_input.next();
		
		if( ac.criarAnuncio(modelo, ano, motor, placa, cor, marca, potencia, lanceMin) ){
			System.out.println("O Anúncio foi criado com sucesso!");
			this.mostrarMenuPrincipal();
		}
		
	}

	private void fazerLogin() {

	}

	private void trocarSenha() {

	}

	private void mostrarLeiloes() {

	}

	private void mostrarLeilao() {

	}
	
	//temporary vars to stop errors
	private int uid = 0;
	private int aid = 0;

	private void darLance() {
		if( ac.limiteDeLancesAtingido(uid, aid) ){
			System.out.println("O usuário atingiu seu limite de lances.");
			this.mostrarMenuPrincipal();
			return;
		}
		
		String lanceValorString;
		System.out.println("Digite o valor do lance");
		lanceValorString = user_input.next();
		int lanceValor = Integer.parseInt(lanceValorString);
		
		
		if( !ac.valorMinimoObservado(aid, lanceValor) ){
			System.out.println("O valor informado está abaixo do valor de lance mínimo.");
			this.mostrarMenuPrincipal();
			return;
		}
		
		if( ac.darLance(uid, aid, lanceValor) ){
			System.out.println("O Lance foi realizado com sucesso!");
			this.mostrarMenuPrincipal();
		}
	}

	private void mostrarAnuncios() {

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