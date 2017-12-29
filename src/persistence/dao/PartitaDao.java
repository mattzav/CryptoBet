package persistence.dao;

import java.util.List;

import model.Partita;

public interface PartitaDao {

	public void save(Partita partita);  // Create
	public Partita findByPrimaryKey(Long codice);     // Retrieve
	public List<Partita> findAll();       
	public void update(Partita partita); //Update
	public void delete(Partita partita); //Delete

}
