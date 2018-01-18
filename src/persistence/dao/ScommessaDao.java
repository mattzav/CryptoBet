package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.betting.Scommessa;
import model.users.Conto;
import model.users.Giocatore;

public interface ScommessaDao {

	public void save(Scommessa scommessa, Connection connection)throws SQLException;  // Create
	public Scommessa findByPrimaryKey(Long codice);     // Retrieve
	public void update(Scommessa scommessa); //Update
	public void delete(Scommessa scommessa); //Delete
	public List<Scommessa> findAll(Giocatore giocatore);
	public String verificaScommessa(Long valueOf);
}
