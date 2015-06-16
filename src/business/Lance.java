package business;

import persistence.ISerializable;
import persistence.LanceDAO;

public class Lance implements ISerializable{
	public Lance(Float valor){
		this.valor=valor;
	}
	
	public static LanceDAO manager = new LanceDAO();
	
	int id;
	
	private Float valor;
	Leilao leilao;
	Usuario usuario;
	
	public Lance(Float valor, Leilao l, Usuario u){
		this.valor = valor;
		this.leilao = l;
		this.usuario = u;
		this.id = -1;
	}
	
	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Leilao getLeilao() {
		return leilao;
	}

	public void setLeilao(Leilao leilao) {
		this.leilao = leilao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void save(){
		manager.save(this);
	}
}
