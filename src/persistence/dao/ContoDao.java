package persistence.dao;

import java.util.List;

import model.users.Conto;

public interface ContoDao {

	public void save(Conto conto);  // Create
	public Conto findByPrimaryKey(Long codice);     // Retrieve
	public List<Conto> findAll();       
	public void update(Conto conto); //Update
	public void delete(Conto conto); //Delete

}
