package persistence.dao;

import java.util.List;

import model.Giocatore;


public interface GiocatoreDao {

	public void save(Giocatore giocatore);  // Create
	public Giocatore findByPrimaryKey(String matricola);     // Retrieve
	public List<Giocatore> findAll();       
	public void update(Giocatore giocatore); //Update
	public void delete(Giocatore giocatore); //Delete
	public void setPassword(Giocatore giocatore, String password);

}
