package model;

import persistence.IdBroker;

public class MovimentoScommessa {

	private Long codice_transazione;
	private float importo;
	private String tipo_transazione;
	private Scommessa scommessa;
	
	public MovimentoScommessa(Long codice_transazione, float importo, String tipo_transazione,Scommessa scommessa) {
		this.codice_transazione = codice_transazione;
		this.importo = importo;
		this.tipo_transazione = tipo_transazione;
		this.scommessa=scommessa;
	}
	
	public MovimentoScommessa(float importo_giocato, String versamento,Scommessa scommessa) {
		this.codice_transazione=IdBroker.getIstance("movimentoscommessa").getId();
		this.importo = importo_giocato;
		this.tipo_transazione = versamento;
		this.scommessa=scommessa;
	}

	public MovimentoScommessa(Conto contoUtente, Float valueOf, String prelievo) {
		// TODO Auto-generated constructor stub
	}

	public Long getCodice_transazione() {
		return codice_transazione;
	}
	
	public void setCodice_transazione(Long codice_transazione) {
		this.codice_transazione = codice_transazione;
	}
	
	public float getImporto() {
		return importo;
	}
	
	public void setImporto(float importo) {
		this.importo = importo;
	}
	
	public String getTipo_transazione() {
		return tipo_transazione;
	}
	
	public void setTipo_transazione(String tipo_transazione) {
		this.tipo_transazione = tipo_transazione;
	}

	public Scommessa getScommessa() {
		return scommessa;
	}

	public void setScommessa(Scommessa scommessa) {
		this.scommessa = scommessa;
	}

	
	
}
