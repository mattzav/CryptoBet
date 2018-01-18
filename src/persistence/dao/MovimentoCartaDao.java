package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.users.Conto;
import model.users.MovimentoCarta;

public interface MovimentoCartaDao {

	public void save(MovimentoCarta movimento, Connection connection)throws SQLException;  // Create
	public List<MovimentoCarta> findAll(Conto conto);       
	public void delete(MovimentoCarta movimento); //Delete

}
