package persistence.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import model.footballdata.Squadra;

public interface SquadraDao {

	public void save(Squadra squadra, Connection connection);  // Create
	public Squadra findByPrimaryKey(String nome);     // Retrieve
	public List<Squadra> findAll();       
	public void update(Squadra squadra, Connection connection);
	public ArrayList<Squadra> findAllWithTitle();
}
