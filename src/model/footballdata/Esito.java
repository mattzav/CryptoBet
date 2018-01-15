package model.footballdata;

import java.io.Serializable;

public class Esito implements Serializable {
	private String descrizione;

	public Esito(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public Esito() {
		descrizione="";
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
}
