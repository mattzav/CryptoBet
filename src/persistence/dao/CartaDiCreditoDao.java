package persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.users.CartaDiCredito;
import model.users.Conto;

public interface CartaDiCreditoDao {
	public void save(CartaDiCredito carta, Connection connection)throws SQLException;  // Create
	public CartaDiCredito findByPrimaryKey(String matricola);     // Retrieve
	public void update(CartaDiCredito carta, Connection connection)throws SQLException; //Update
	public void delete(CartaDiCredito carta); //Delete
}
