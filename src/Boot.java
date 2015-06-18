import java.util.Date;

import view.UserInterfaceView;
import business.*;
import business.exceptions.BusinessException;

public class Boot {

	public static void main(String[] args) throws InterruptedException{
		initial();
		UserInterfaceView UI = new UserInterfaceView();
		UI.mostrarMenuPrincipal();
	}
	
	public static void initial(){
		install();
		if(!Usuario.manager.exists(1)){
			createMediador();
			createSampleData();
		}
	}
	
	public static void createMediador(){
		try {		
				Mediador m = new Mediador("Superuser", 12345678, "admin", "password");
				m.save();
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void createSampleData(){
		try{
			Anuncio porsche = new Anuncio("Boxter", 1991, "super-23", "ABC-1234", "vermelho", "Porsche", 2000, 12000f);
			Anuncio fiat = new Anuncio("Twingo", 2000, "economy", "CGD-3452", "cinzo", "Fiat", 1000, 2500f);
			Anuncio ford = new Anuncio("mustang", 1970, "turbo27", "OLD-1970", "preto", "Ford", 1500, 30000f);
			Anuncio LandRover = new Anuncio("SUV-X5", 2010, "offroad-X4", "OFF-4568", "verde", "LandRover", 6000, 20000f);
			
			porsche.save();
			fiat.save();
			ford.save();
			LandRover.save();
			
			Usuario alan = new Usuario("Alan Francisco", "akjsdfhd", 12345678, 12345678, "alan91", "blabla");
			Usuario elias = new Usuario("Elias S", "akjsdfhd", 12345678, 12345678, "elias", "blabla");
			Usuario bernardo = new Usuario("Bernardo Donadio", "akjsdfhd", 12345678, 12345678, "bernardo", "blabla");
			
			alan.save();
			elias.save();
			bernardo.save();
			
			Leilao l1 = new Leilao(300,1000,30000,(new Date()));
			l1.save();
			porsche.setLeilao(l1);
			porsche.save();
			Leilao l2 = new Leilao(50, 123, 300000, new Date());
			l2.save();
			ford.setLeilao(l2);
			ford.save();
			
			Lance la = new Lance(100f, l1, alan);
			la.save();
			Lance la2 = new Lance(200f, l1, bernardo);
			la2.save();
			
		} catch (BusinessException e){
			System.out.println("error creating sample data: " + e.getMessage());
		}
		
	}
	public static void DAOtest(){
		String modelo = "Boxter";
		int ano = 1991;
		String cor = "vermelho";
		String placa = "abc-1234";
		int potencia = 2000;
		String motor = "super-23";
		String marca = "Porsche";
		Float lanceMin = 12000.0f;
		try{
			Anuncio a = new Anuncio(modelo, ano, motor, placa, cor, marca, potencia, lanceMin);
			Usuario u = new Usuario("Alan Francisco", "akjsdfhd", 12345678, 12345678, "JohnDoe", "blabla");
			Leilao l = new Leilao(123, 123, 123, new Date());
			Lance la = new Lance(12321f, l, u);
			a.save();
			u.save();
			l.save();
			la.save();
			Anuncio a1 = (Anuncio) Anuncio.manager.get(1);
			Usuario u1 = (Usuario) Usuario.manager.get(1);
			Leilao l1 = (Leilao) Leilao.manager.get(1);
			Lance la1 = (Lance) Lance.manager.get(1);
		} catch(BusinessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void install(){
		try{
			Anuncio.manager.initial();
			Usuario.manager.initial();
			Leilao.manager.initial();
			Lance.manager.initial();
		} catch (Exception e){
			//ignore exceptions
		}
	}
}
