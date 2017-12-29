package persistence.dao;

import java.util.List;

import model.Squadra;

public interface SquadraDao {

	public void save(Squadra squadra);  // Create
	public Squadra findByPrimaryKey(String nome);     // Retrieve
	public List<Squadra> findAll();       
	public void delete(Squadra squadra); //Delete

}
