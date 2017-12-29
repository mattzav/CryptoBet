package persistence.dao;

import java.util.List;

import model.Campionato;

public interface CampionatoDao {

	public void save(Campionato campionato);  // Create
	public Campionato findByPrimaryKey(Long codice);     // Retrieve
	public List<Campionato> findAll();       
	public void update(Campionato campionato); //Update
	public void delete(Campionato campionato); //Delete

}
