package persistence.dao;

import java.util.List;

import model.Conto;
import model.Giocatore;
import model.Scommessa;

public interface ScommessaDao {

	public void save(Scommessa scommessa);  // Create
	public Scommessa findByPrimaryKey(Long codice);     // Retrieve
	public void update(Scommessa scommessa); //Update
	public void delete(Scommessa scommessa); //Delete
	List<Scommessa> findAll(Giocatore giocatore);
}
