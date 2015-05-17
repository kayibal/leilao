package View;

import Buisness.AnuncioControl;
import Buisness.UsuarioControl;
import java.util.Scanner;


public class UserInteraceView {
	
	Scanner user_input ;
	private AnuncioControl ac;
	private UsuarioControl uc;
	
	UserInteraceView(){
		user_input = new Scanner(System.in);
	}

	public void mostrarmenuPrincipal() {
		
		String selection;
		System.out.println("Select your desired action by typing the according number:");
		System.out.println("1. Criar Anuncio");
		
		selection = user_input.next();
		switch(selection.trim()){
			case "1":
				this.criarAnuncio();
				break;
		}
		
	}

	public void criarAnuncio() {
		String modelo;
		System.out.println("Enter your cars model");
		modelo = user_input.next();

		String ano;
		System.out.println("Enter your cars year");
		ano = user_input.next();
		
		String motor;
		System.out.println("Enter your cars motor");
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
		System.out.println("Digite o lance minimo de seu carro");
		lanceMin = user_input.next();
		
		if( ac.criarAnuncio(modelo, ano, motor, placa, cor, marca, potencia, lanceMin) ){
			System.out.println("O Anuncio foi criado com sucesso!");
			this.mostrarmenuPrincipal();
		}
		
	}

	public void fazerLogin() {

	}

	public void trocarSenha() {

	}

	public void mostrarLeiloes() {

	}

	public void mostrarLeilao() {

	}

	public void darLance() {

	}

	public void mostrarAnuncios() {

	}

	public void aprovarAnuncios() {

	}

	public void fecharAnuncio() {

	}

	public void mostrarPerguntas() {

	}

	public void fazerPergunta() {

	}

	public void darResposta() {

	}

	public void darPontucao() {

	}

}
