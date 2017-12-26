package persistence.dao;

import java.util.List;

import model.Credenziali;
import model.Giocatore;

public interface CredenzialiDao {
	public void save(Credenziali credenziali);  // Create
	public Credenziali findByPrimaryKey(String username);     // Retrieve
	public List<Credenziali> findAll();       
	public void update(Credenziali credenziali); //Update
	public void delete(Credenziali credenziali); //Delete
}
