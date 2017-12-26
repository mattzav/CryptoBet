package persistence.dao;

import java.util.List;

import model.Conto;

public interface ContoDao {

	public void save(Conto conto);  // Create
	public Conto findByPrimaryKey(String matricola);     // Retrieve
	public List<Conto> findAll();       
	public void update(Conto conto); //Update
	public void delete(Conto conto); //Delete

}
