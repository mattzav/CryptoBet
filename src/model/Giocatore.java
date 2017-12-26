package model;

import java.util.Date;

import persistence.DataSource;
import persistence.IdBroker;

public class Giocatore {

	private Long codice;
	private String nome;
	private String cognome;
	private Date dataDiNascita;
	private Conto conto;
	private Credenziali credenziali;
	
	public Giocatore(String nome2, String cognome2, Credenziali nuovaCredenziale,Conto conto) {
		DataSource dataSource=new DataSource("jdbc:postgresql://localhost:5432/CryptoBet","postgres","postgres");
		codice=IdBroker.getIstance("giocatore").getId(dataSource.getConnection());
		nome=nome2;
		cognome=cognome2;
		credenziali=nuovaCredenziale;
		this.conto=conto;
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
	public Date getDataNascita() {
		// TODO Auto-generated method stub
		return null;
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
	
}
