package persistence.dao;

import java.util.List;

import model.MovimentoCarta;

public interface MovimentoCartaDao {

	public void save(MovimentoCarta movimento);  // Create
	public MovimentoCarta findByPrimaryKey(Long codice);     // Retrieve
	public List<MovimentoCarta> findAll();       
	public void update(MovimentoCarta movimento); //Update
	public void delete(MovimentoCarta movimento); //Delete

}
