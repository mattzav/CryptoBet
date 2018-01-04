package model;

import java.util.Date;

public class CartaDiCredito {

	private String codiceCarta;
	private Date scadenza;
	private float saldo;

	public CartaDiCredito(String codCarta) {
		codiceCarta=codCarta;
		saldo=100;
	}

	public String getCodiceCarta() {
		return codiceCarta;
	}

	public void setCodiceCarta(String codiceCarta) {
		this.codiceCarta = codiceCarta;
	}

	public Date getScadenza() {
		return scadenza;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	

}
