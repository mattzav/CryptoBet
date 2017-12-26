package persistence.dao;

import java.util.List;

import model.Amministratore;


public interface AmministratoreDao {
	public void save(Amministratore admin);  // Create
	public Amministratore findByPrimaryKey(String codice);     // Retrieve
	public List<Amministratore> findAll();       
	public void update(Amministratore admin); //Update
	public void delete(Amministratore admin); //Delete
}
