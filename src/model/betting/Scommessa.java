package model.betting;

import java.io.Serializable;
import java.util.Date;

import model.users.Conto;
import persistence.IdBroker;

public class Scommessa implements Serializable{
	
	private String stato;
	private Long codice;
	private Date data_emissione;
	private Conto conto_associato;
	private SchemaDiScommessa schema_scommessa;
	
	public Scommessa(Long codice, Date data_emissione, Conto conto_associato,
			 SchemaDiScommessa schema_scommessa, String stato) {
		this.codice = codice;
		this.data_emissione = data_emissione;
		this.conto_associato = conto_associato;
		this.schema_scommessa = schema_scommessa;
		this.stato=stato;
	}
	
	public Scommessa(Date date, Conto contoUtente, SchemaDiScommessa schemaDiScommessa,String stato) {
		codice=IdBroker.getIstance("scommessa").getId();
		this.data_emissione = date;
		this.conto_associato = contoUtente;
		this.schema_scommessa = schemaDiScommessa;
		this.stato=stato;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}

	public Long getCodice() {
		return codice;
	}
	
	public void setCodice(Long codice) {
		this.codice = codice;
	}
	
	public Date getData_emissione() {
		return data_emissione;
	}
	
	public void setData_emissione(Date data_emissione) {
		this.data_emissione = data_emissione;
	}
	
	public Conto getConto_associato() {
		return conto_associato;
	}
	
	public void setConto_associato(Conto conto_associato) {
		this.conto_associato = conto_associato;
	}
	
	public SchemaDiScommessa getSchema_scommessa() {
		return schema_scommessa;
	}
	
	public void setSchema_scommessa(SchemaDiScommessa schema_scommessa) {
		this.schema_scommessa = schema_scommessa;
	}
	
	
}
