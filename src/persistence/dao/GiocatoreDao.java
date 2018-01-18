package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.users.Credenziali;
import model.users.Giocatore;


public interface GiocatoreDao {

	public void save(Giocatore giocatore, Connection connection)throws SQLException;  // Create
	public Giocatore findByPrimaryKey(String matricola);     // Retrieve
	public Giocatore findByCredenziali(Credenziali credenziali);
	public List<Giocatore> findAll();       
	public void update(Giocatore giocatore); //Update
	public void delete(Giocatore giocatore); //Delete
	public void setPassword(Giocatore giocatore, String password);

}
