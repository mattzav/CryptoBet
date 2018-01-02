package model;

public class Esito {
	String descrizione;

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
