package model.footballdata;

import java.io.Serializable;

public class Squadra implements Serializable{

	private String nome;
	
	
	public Squadra(String nome) {
		
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
