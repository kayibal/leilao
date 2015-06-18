import java.util.Date;

import exceptions.BusinessException;
import view.UserInterfaceView;
import business.*;

public class Boot {

	public static void main(String[] args) throws InterruptedException{
		install();
		UserInterfaceView UI = new UserInterfaceView();
		UI.mostrarMenuPrincipal();
	}
	
	public static void createMeadiador(){
		try {
			Mediador m = new Mediador("Superuser", 12345678, "admin", "password");
			m.save();
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
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
