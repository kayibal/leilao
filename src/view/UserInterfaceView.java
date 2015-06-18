package view;

import business.AnuncioControl;
import business.UsuarioControl;
import business.Anuncio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
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
		Boolean keep=true;
		do{
			String selection;
			if(uc.getLoggedUserID() != 0){
				if(uc.mediadorLogado()){
					System.out.println("Selecione a acão desejada:");
					System.out.println("1. Listar anuncios pendentes");
					System.out.println("2. Listar anuncios ativos");
					System.out.println("3. Aprovar anuncio");
					System.out.println("4. Fechar anuncio");
					System.out.println("5. Logout");
					System.out.println("6. Sair");
		
					selection = user_input.nextLine();
					switch(selection.trim()){
					case "1":
						this.mostrarAnunciosPendentes();
						break;
					case "2":
						this.mostrarLeiloesAtivos();
						break;
					case "3":
						this.aprovarAnuncios();
						break;
					case "4":
						this.fecharAnuncio();
						break;
					case "5":
						uc.fazerLogout();
						break;
					case "6":
						keep=false;
						break;
					default:
						System.out.println("Opcão inválida!");
						break;
					}
				}else{
					System.out.println("Escolha a acão desejada:");
					System.out.println("1. Criar Anuncio");
					System.out.println("2. Realizar Lance");
					System.out.println("3. Mostrar Anúncios");
					System.out.println("4. Trocar senha");
					System.out.println("5. Dar pontuacão");
					System.out.println("6. Logout");
					System.out.println("7. Sair");
		
					selection = user_input.nextLine();
					switch(selection.trim()){
					case "1":
						this.criarAnuncio();
						break;
					case "2":
						this.darLance();
						break;
					case "3":
						this.mostrarLeiloesAtivos();
						break;
					case "4":
						this.trocarSenha();
						break;
					case "5":
						this.darPontucao();
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
				}
			}else if(uc.getLoggedUserID() == 0){
				System.out.println("Selecione a acão desejada:");
				System.out.println("1. Mostrar Anúncios");
				System.out.println("2. Realizar cadastro");
				System.out.println("3. Fazer login");
				System.out.println("4. Sair");
				selection = user_input.nextLine();
				switch(selection.trim()){
				case "1":
					this.mostrarLeiloesAtivos();
					break;
				case "2":
					this.registrarUsuario();
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
			}
		}while(keep);
	}


	private void criarAnuncio() {
		String modelo;
		System.out.println("Digite o modelo de seu carro");
		modelo = user_input.nextLine();

		String ano;
		System.out.println("Digite o ano de seu carro");
		ano = user_input.nextLine();
		
		String motor;
		System.out.println("Digite o motor de seu carro");
		motor = user_input.nextLine();
		
		String placa;
		System.out.println("Digite a placa de seu carro");
		placa = user_input.nextLine();
		
		String cor;
		System.out.println("Digite a cor de seu carro");
		cor = user_input.nextLine();
		
		String marca;
		System.out.println("Digite a marca de seu carro");
		marca = user_input.nextLine();
		
		String potencia;
		System.out.println("Digite a potencia de seu carro");
		potencia = user_input.nextLine();
		
		String lanceMin;

		System.out.println("Digite o lance minimo de seu anúncio");
		lanceMin = user_input.nextLine();
		
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
		System.out.println("Digite seu usuário:");
		user=user_input.nextLine();
		System.out.println("Digite sua senha:");
		password=user_input.nextLine();
		try{
			uc.fazerLogin(user,password);
		}catch(BusinessException e){
			System.out.println(e.getMessage());
		}
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
		if(newpass1.equals(newpass2))
			try {
				uc.trocarSenha(uc.getLoggedUserID(), newpass1);
			} catch (BusinessException e) {
				e.getMessage();
			}
	}

	private void mostrarLeiloesAtivos() {
		ArrayList<Anuncio> anuncios=ac.getLeiloesAtivas();
		Iterator<Anuncio> anunciosIt=anuncios.iterator();
		if(anuncios.isEmpty())
			System.out.println("Não há anúncios ativos!");
		else
			while(anunciosIt.hasNext()){
				Anuncio anuncio=anunciosIt.next();
				System.out.println(anuncio.getMarca() + " " +
						anuncio.getModelo() + " " +
						anuncio.getAno() + " " +
						anuncio.getCor() + " " +
						anuncio.getPotencia() + " " +
						"#" + anuncio.getId());
			}
	}
	private void darLance() {
		//TODO Refactor might be necessary
		int uid = this.uc.getLoggedUserID();
		this.mostrarLeiloesAtivos();
		String anuncioAlvo;
		System.out.println("Digite o leilão para o qual deseja realizar um lance");
		anuncioAlvo = user_input.nextLine();

		int aid = Integer.parseInt(anuncioAlvo);

		String lanceValorString;
		System.out.println("Digite o valor do lance");
		lanceValorString = user_input.nextLine();
		float lanceValor = Float.parseFloat(lanceValorString);

		try{
		  ac.darLance(uid, aid, lanceValor);
		  System.out.println("O Lance foi realizado com sucesso!");
		  this.mostrarMenuPrincipal();
		}catch(BusinessException e){
		  System.out.println(e.getMessage());
		}

	}
	
	private void registrarUsuario(){
		String nome, endereco, CPF, telefone, username, senha1, senha2;
		Boolean keep=true;
		
		System.out.println("Insira seu nome completo:");
		nome=user_input.nextLine();
		System.out.println("Insira seu endereco:");
		endereco=user_input.nextLine();
		System.out.println("Insira seu CPF:");
		CPF=user_input.nextLine();
		System.out.println("Insira seu telefone:");
		telefone=user_input.nextLine();
		System.out.println("Insira seu username:");
		username=user_input.nextLine();
		do{
			System.out.println("Insira sua senha:");
			senha1=user_input.nextLine();
			System.out.println("Confirme sua senha:");
			senha2=user_input.nextLine();
			if(senha1.equals(senha2))
				keep=false;
			else
				keep=true;
		}while(keep);
		try {
			uc.cadastrarUsuario(nome, endereco, Integer.decode(CPF), Integer.decode(telefone), username, senha1);
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
	}

	private void mostrarAnunciosPendentes(){
		ArrayList<Anuncio> anuncios=ac.getAnuncioPendentes();
		Iterator<Anuncio> anunciosIt=anuncios.iterator();
		if(anuncios.isEmpty())
			System.out.println("Não há anúncios pendentes!");
		else
			while(anunciosIt.hasNext()){
				Anuncio anuncio=anunciosIt.next();
				System.out.println(anuncio.getMarca() + " " +
						anuncio.getModelo() + " " +
						anuncio.getAno() + " " +
						anuncio.getCor() + " " +
						anuncio.getPotencia() + " " +
						"#" + anuncio.getId());
			}
	}

	private void aprovarAnuncios() {
		String aid, maxPart, maxLances, tempoLimite, dataHoraInicio;
		DateFormat df = new SimpleDateFormat("MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
		this.mostrarAnunciosPendentes();
		System.out.println("Escolha o anúncio a ser aprovado:");
		aid=user_input.nextLine();
		System.out.println("Insira o número máximo de participantes:");
		maxPart=user_input.nextLine();
		System.out.println("Insira o número máximo de lances:");
		maxLances=user_input.nextLine();
		System.out.println("Insira o tempo limite:");
		tempoLimite=user_input.nextLine();
		System.out.println("Insira a data e hora de início:");
		dataHoraInicio=user_input.nextLine();
		java.sql.Date fim = new Date(Calendar.getInstance().getTimeInMillis());
		ac.aprovarAnuncio(Integer.parseInt(aid), Integer.parseInt(maxPart),
				Integer.parseInt(maxLances), Integer.parseInt(tempoLimite),
				fim);
	}
	/**
	 * lets the mediador close some Anuncio
	 */
	private void fecharAnuncio() {

	}
	/**
	 * lets a winner add a rating to his winning Leilao
	 */
	private void darPontucao() {
		Integer pont;
		Boolean error=false;
		this.mostrarLeiloesAtivos();
		System.out.println("Escolha o leilão:");
		String aid = user_input.nextLine();
		do{
			System.out.println("Insira a pontuacão:");
			pont=Integer.valueOf(user_input.nextLine());
			if(pont>=0&&pont<=2){
				try {
					uc.darPontucao(Integer.parseInt(aid), pont);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BusinessException e) {
					System.out.println(e.getMessage());
				}
				error=false;
			}else{
				error=true;
				System.out.println("O valor deve estar compreendido entre 0 e 2. Tente novamente.");
			}
		}while(error);
	}

}
