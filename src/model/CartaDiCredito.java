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

	public String getCodice() {
		// TODO Auto-generated method stub
		return codiceCarta;
	}

	public float getSaldo() {
		// TODO Auto-generated method stub
		return saldo;
	}
}
