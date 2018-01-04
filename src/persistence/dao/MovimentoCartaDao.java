package persistence.dao;

import java.util.List;

import model.Conto;
import model.MovimentoCarta;

public interface MovimentoCartaDao {

	public void save(MovimentoCarta movimento);  // Create
	public List<MovimentoCarta> findAll(Conto conto);       
	public void delete(MovimentoCarta movimento); //Delete

}
