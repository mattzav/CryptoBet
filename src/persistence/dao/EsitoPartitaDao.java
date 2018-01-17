package persistence.dao;

import java.sql.Connection;
import java.util.List;

import model.footballdata.Esito;
import model.footballdata.EsitoPartita;
import model.footballdata.Partita;
import model.users.Conto;

public interface EsitoPartitaDao {

	public void save(EsitoPartita esito, Connection connection);  // Create
	public EsitoPartita findByPrimaryKey(Esito esito,Partita partita);     // Retrieve
	public List<EsitoPartita> findByPartita(Partita partita);       
	public void update(EsitoPartita esitoPartita); //Update
	public void delete(EsitoPartita esitoPartita); //Delete
}
