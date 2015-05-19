package business;

import java.util.List;

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

	private List<Pergunta> perguntas;

	public Anuncio(String modelo, int ano, String motor, String placa,
			String cor, String marca, int potencia, Float lanceMin) {
		this.setModelo(modelo);
		this.setAno(ano);
		this.setMotor(motor);
		this.setPlaca(placa);
		this.setCor(cor);
		this.setMarca(marca);
		this.setPotencia(potencia);
		this.setLanceMin(lanceMin);
		//this.leilao = NULL;
		//abstraído temporariamente
		this.setLeilao(new Leilao(5, 20, 60));
		//this.pergunta = pergunta;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) {
		this.motor = motor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) {
		this.potencia = potencia;
	}

	public Float getLanceMin() {
		return lanceMin;
	}

	public void setLanceMin(Float lanceMin) {
		this.lanceMin = lanceMin;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public List<Pergunta> getPerguntas() {
		return perguntas;
	}

	public void setPerguntas(List<Pergunta> perguntas) {
		this.perguntas = perguntas;
	}
	
	

}
