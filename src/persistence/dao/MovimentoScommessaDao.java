package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.betting.MovimentoScommessa;
import model.users.Conto;

public interface MovimentoScommessaDao {

	public void save(MovimentoScommessa movimentoScommessa,Connection connection)throws SQLException;  // Create
	public MovimentoScommessa findByPrimaryKey();     // Retrieve
	public List<MovimentoScommessa> findAll(Conto c);       
	public void update(MovimentoScommessa movimentoScommessa); //Update
	public void delete(MovimentoScommessa movimentoScommessa); //Delete
}
