package model.footballdata;

import java.io.Serializable;

public class Squadra implements Serializable{

	private String nome;
	private String scudetto;
	
	
	public Squadra(String nome) {
		
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setScudetto(String scudetto) {
		this.scudetto = scudetto;
	}
	
	public String getScudetto() {
		return scudetto;
	}
	
}
