package model.users;

import java.io.Serializable;
import java.util.Date;

import persistence.IdBroker;

public class MovimentoCarta implements Serializable{

	private Long codice;
	private Date dataEffettuazione;
	private String tipo;
	private float importo;
	private Conto conto;
	
	public MovimentoCarta(Long codice, Date dataEffettuazione, String tipo, float importo, Conto conto) {
		super();
		this.codice = codice;
		this.dataEffettuazione = dataEffettuazione;
		this.tipo = tipo;
		this.importo = importo;
		this.conto = conto;
	}

	public MovimentoCarta(Date date, String prelievo, Float valueOf, Conto contoUtente) {
		codice=IdBroker.getIstance("movimentoCarta").getId();
		this.dataEffettuazione = date;
		this.tipo = prelievo;
		this.importo = valueOf;
		this.conto = contoUtente;
	}

	public Long getCodice() {
		return codice;
	}
	public void setCodice(Long codice) {
		this.codice = codice;
	}
	public Date getDataEffettuazione() {
		return dataEffettuazione;
	}
	public void setDataEffettuazione(Date dataEffettuazione) {
		this.dataEffettuazione = dataEffettuazione;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public float getImporto() {
		return importo;
	}
	public void setImporto(float importo) {
		this.importo = importo;
	}
	public Conto getConto() {
		return conto;
	}
	public void setConto(Conto conto) {
		this.conto = conto;
	}
	
	
}
