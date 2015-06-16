package business;

import java.util.List;

import persistence.AnuncioDAO;
import persistence.ISerializable;

public class Anuncio implements ISerializable {
	
	public static AnuncioDAO manager = new AnuncioDAO();
	
	int id;

	private String modelo;

	private int ano;

	private String motor;

	private String placa;

	private String cor;

	private String marca;

	private int potencia;

	private Float lanceMin;

	private Leilao leilao;
	
	private Boolean fechado;

	private List<Pergunta> perguntas;

	public Anuncio(String modelo, Integer ano, String motor, String placa,
			String cor, String marca, int potencia, Float lanceMin) {
		this.setModelo(modelo);
		this.setAno(ano);
		this.setMotor(motor);
		this.setPlaca(placa);
		this.setCor(cor);
		this.setMarca(marca);
		this.setPotencia(potencia);
		this.setLanceMin(lanceMin);
		this.leilao = null;
		this.perguntas = null;
		this.id = -1;
	}
	
	public Boolean getFechado() {
		return fechado;
	}

	public void setFechado(Boolean fechado) {
		this.fechado = fechado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) throws BusinessException{
		if(!modelo.matches("[\\w,\\/,_,-]{3,20}")){
			this.modelo = modelo;
		}
		else{
			throw new BusinessException("Modelo Invalido\n");
		}
	}

	public int getAno() {
		return ano;
	}

	public void setAno(Integer ano) throws BusinessException{
		if(!Integer.toString(ano).matches("\\d{4}")){
			this.ano = ano;
		}
		else{
			throw new BusinessException("Ano Invalido\n");
		}
	}

	public String getMotor() {
		return motor;
	}

	public void setMotor(String motor) throws BusinessException{
		if(!motor.matches("[\\w,\\/,_,-]{3,20}")){
			this.motor = motor;
		}
		else{
			throw new BusinessException("Motor Invalido\n");
		}
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) throws BusinessException{
		if(!placa.matches("\\D{3}-\\d{4}")){
			this.placa = placa;
		}
		else{
			throw new BusinessException("Placa Invalida\n");
		}
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) throws BusinessException{
		if(!cor.matches("\\D{3,12}")){
			this.cor = cor;
		}
		else{
			throw new BusinessException("Cor Invalida\n");
		}
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) throws BusinessException{
		if(!marca.matches("[\\w,\\/,_,-]{3,20}")){
			this.marca = marca;
		}
		else{
			throw new BusinessException("Marca Invalida\n");
		}
	}

	public int getPotencia() {
		return potencia;
	}

	public void setPotencia(int potencia) throws BusinessException{
		if(!Integer.toString(potencia).matches("\\d{4}")){
			this.potencia = potencia;
		}
		else{
			throw new BusinessException("Potencia Invalida\n");
		}
	}

	public Float getLanceMin() {
		return lanceMin;
	}

	public void setLanceMin(Float lanceMin) throws BusinessException{
		if(!Float.toString(lanceMin).matches("\\d*.\\d*")){
			this.lanceMin = lanceMin;
		}
		else{
			throw new BusinessException("Lance Minimo Invalido\n");
		}
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
	
	public void save(){
		manager.save(this);
	}
	
	public String toString(){
		return 	"Modelo: "	+ this.modelo +		"\n" +
				"Ano: "		+ this.ano +		"\n" +
				"Motor: " + this.motor +		"\n" +
				"Placa: " + this.placa +		"\n" +
				"Cor: " + this.cor +			"\n" +
				"Marca: " + this.marca +		"\n" +
				"Potencia " + this.potencia +	"\n" +
				"Lance minimo:" + this.lanceMin+"\n";
	}

}
