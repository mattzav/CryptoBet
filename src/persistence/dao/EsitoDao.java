package persistence.dao;

import java.util.List;

import model.Esito;


public interface EsitoDao {

	public void save(Esito esito);  // Create
	public List<Esito> findAll();       
	
}
