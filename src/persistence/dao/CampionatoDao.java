package persistence.dao;

import java.sql.Connection;
import java.util.List;

import model.footballdata.Campionato;

public interface CampionatoDao {

	public void save(Campionato campionato, Connection connection);  // Create
	public Campionato findByPrimaryKey(Long codice);     // Retrieve
	public List<Campionato> findAll();       

}
