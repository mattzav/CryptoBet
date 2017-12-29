package model;

public class Campionato {

	private Long codice;
	private String nome;
	
	public Campionato() {
	}
	
	public Campionato(Long codice, String nome) {
		this.codice=codice;
		this.nome=nome;
	}
	
	public Long getCodice() {
		return codice;
	}
	public void setCodice(Long codice) {
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
