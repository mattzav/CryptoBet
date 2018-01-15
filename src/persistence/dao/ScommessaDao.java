package persistence.dao;

import java.util.List;

import model.betting.Scommessa;
import model.users.Conto;
import model.users.Giocatore;

public interface ScommessaDao {

	public void save(Scommessa scommessa);  // Create
	public Scommessa findByPrimaryKey(Long codice);     // Retrieve
	public void update(Scommessa scommessa); //Update
	public void delete(Scommessa scommessa); //Delete
	public List<Scommessa> findAll(Giocatore giocatore);
	public String verificaScommessa(Long valueOf);
}
