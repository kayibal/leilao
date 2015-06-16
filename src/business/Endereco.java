package business;

public class Endereco{
	
	private String rua;
	private int numero;
	private int CEP;
	
	int id;

	public Endereco(String rua, int numero, int CEP) {
		this.setRua(rua);
		this.setNumero(numero);
		this.setCEP(CEP);
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getCEP() {
		return CEP;
	}

	public void setCEP(int cEP) {
		CEP = cEP;
	}
	
}
