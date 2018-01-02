package persistence.dao;

import java.util.List;

import model.Conto;
import model.EsitoPartita;
import model.Partita;

public interface EsitoPartitaDao {

	public void save(EsitoPartita esito);  // Create
	public EsitoPartita findByPrimaryKey(Esito esito,Partita partita);     // Retrieve
	public List<EsitoPartita> findByPartita(Partita partita);       
	public void update(EsitoPartita esitoPartita); //Update
	public void delete(EsitoPartita esitoPartita); //Delete
}
