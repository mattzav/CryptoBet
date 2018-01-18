package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.users.Credenziali;
import model.users.Giocatore;

public interface CredenzialiDao {
	public void save(Credenziali credenziali, Connection connection)throws SQLException;  // Create
	public Credenziali findByPrimaryKey(String username);     // Retrieve
	public List<Credenziali> findAll();       
	public void update(Credenziali credenziali); //Update
	public void delete(Credenziali credenziali); //Delete
}
