package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.users.Conto;

public interface ContoDao {

	public void save(Conto conto, Connection connection)throws SQLException;  // Create
	public Conto findByPrimaryKey(Long codice);     // Retrieve
	public List<Conto> findAll();       
	public void update(Conto conto, Connection connection)throws SQLException; //Update
	public void delete(Conto conto); //Delete

}
