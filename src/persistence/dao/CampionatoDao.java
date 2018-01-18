package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.footballdata.Campionato;

public interface CampionatoDao {

	public void save(Campionato campionato, Connection connection)throws SQLException;  // Create
	public Campionato findByPrimaryKey(Long codice,Connection connection)throws SQLException;     // Retrieve
	public List<Campionato> findAll();       

}
