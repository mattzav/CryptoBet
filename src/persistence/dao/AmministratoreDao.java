package persistence.dao;

import java.util.List;

import model.users.Amministratore;
import model.users.Credenziali;
import model.users.Giocatore;


public interface AmministratoreDao {
	public void save(Amministratore admin);  // Create
	public Amministratore findByPrimaryKey(String codice);     // Retrieve
	public List<Amministratore> findAll();       
	public void update(Amministratore admin); //Update
	public void delete(Amministratore admin); //Delete
	public boolean findByCredenziali(Credenziali credenziali);
}
