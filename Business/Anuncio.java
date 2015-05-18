package Business;
public class Anuncio {

	private String modelo;

	private int ano;

	private String motor;

	private String placa;

	private String cor;

	private String marca;

	private int potencia;

	private Float lanceMin;

	private Leilao leilao;

	private Pergunta[] pergunta;

	public Anuncio(String modelo, int ano, String motor, String placa,
			String cor, String marca, int potencia, Float lanceMin) {
		this.modelo = modelo;
		this.ano = ano;
		this.motor = motor;
		this.placa = placa;
		this.cor = cor;
		this.marca = marca;
		this.potencia = potencia;
		this.lanceMin = lanceMin;
		this.leilao = leilao;
		this.pergunta = pergunta;
	}
	
	

}
