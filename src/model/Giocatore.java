package model;

import java.io.Serializable;

import persistence.IdBroker;

public class Giocatore implements Serializable{

	private Long codice;
	private String nome;
	private String cognome;
	private Conto conto;
	private Credenziali credenziali;
	private String tipoUtente;
	
	public Giocatore(String nome2, String cognome2, Credenziali nuovaCredenziale,Conto conto) {
		codice=IdBroker.getIstance("giocatore").getId();
		nome=nome2;
		cognome=cognome2;
		credenziali=nuovaCredenziale;
		this.conto=conto;
		tipoUtente=TipoCredenziali.USER;
	}
	public Long getCodice() {
		// TODO Auto-generated method stub
		return codice;
	}
	public String getNome() {
		// TODO Auto-generated method stub
		return nome;
	}
	public String getCognome() {
		// TODO Auto-generated method stub
		return cognome;
	}

	public Credenziali getCredenziali() {
		return credenziali;
	}
	public Conto getConto() {
		// TODO Auto-generated method stub
		return conto;
	}
	public void setCognome(String string) {
		cognome=string;		
	}
	public void setNome(String string) {
		nome=string;
	}
	public void setConto(Conto c) {
		conto=c;
	}
	public String getTipoUtente() {
		return tipoUtente;
	}
	public void setTipoUtente(String tipoUtente) {
		this.tipoUtente = tipoUtente;
	}
	public void setCodice(Long codice) {
		this.codice = codice;
	}
	public void setCredenziali(Credenziali credenziali) {
		this.credenziali = credenziali;
	}
	
	
}
