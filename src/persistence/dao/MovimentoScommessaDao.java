package persistence.dao;

import java.util.List;

import model.betting.MovimentoScommessa;
import model.users.Conto;

public interface MovimentoScommessaDao {

	public void save(MovimentoScommessa movimentoScommessa);  // Create
	public MovimentoScommessa findByPrimaryKey();     // Retrieve
	public List<MovimentoScommessa> findAll(Conto c);       
	public void update(MovimentoScommessa movimentoScommessa); //Update
	public void delete(MovimentoScommessa movimentoScommessa); //Delete
}
