package model;

import persistence.IdBroker;

public class MovimentoScommessa {

	private Long codice_transazione;
	private float importo;
	private String tipo_transazione;
	
	public MovimentoScommessa(Long codice_transazione, float importo, String tipo_transazione) {
		this.codice_transazione = codice_transazione;
		this.importo = importo;
		this.tipo_transazione = tipo_transazione;
	}
	
	public MovimentoScommessa(float importo_giocato, String versamento) {
		this.codice_transazione=IdBroker.getIstance("movimentoscommessa").getId();
		this.importo = importo_giocato;
		this.tipo_transazione = versamento;
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
	
	
}
