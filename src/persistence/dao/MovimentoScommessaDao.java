package persistence.dao;

import java.util.List;

import model.Conto;
import model.MovimentoScommessa;

public interface MovimentoScommessaDao {

	public void save(MovimentoScommessa movimentoScommessa);  // Create
	public MovimentoScommessa findByPrimaryKey();     // Retrieve
	public List<MovimentoScommessa> findAll();       
	public void update(MovimentoScommessa movimentoScommessa); //Update
	public void delete(MovimentoScommessa movimentoScommessa); //Delete
}
