package model;

import java.util.Date;

import persistence.IdBroker;
import persistence.PostgresDAOFactory;
import persistence.UtilDao;

public class Conto {
	
	private Long codice;
	private float saldo;
	private Date dataApertura;
	private CartaDiCredito carta;

	public Conto(CartaDiCredito carta) {
		codice=IdBroker.getIstance("conto").getId(PostgresDAOFactory.dataSource.getConnection());
		this.carta=carta;
	}

	public long getCodice() {
		// TODO Auto-generated method stub
		return codice;
	}

	public float getSaldo() {
		return saldo;
	}

	public Date getDataApertura() {
		// TODO Auto-generated method stub
		return dataApertura;
	}

	public CartaDiCredito getCarta() {
		// TODO Auto-generated method stub
		return carta;
	}
}
