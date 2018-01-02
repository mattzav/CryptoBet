package model;

public class EsitoPartita {

	private Esito esito;
	private float quota;
	private Partita partita;

	public Esito getEsito() {
		return esito;
	}

	public void setEsito(Esito esito) {
		this.esito = esito;
	}

	public float getQuota() {
		return quota;
	}

	public void setQuota(float quota) {
		this.quota = quota;
	}

	public Partita getPartita() {
		return partita;
	}

	public void setPartita(Partita partita) {
		this.partita = partita;
	}

	public EsitoPartita(Esito esito, float quota, Partita partita) {
		super();
		this.esito = esito;
		this.quota = quota;
		this.partita = partita;
	}

}
