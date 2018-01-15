package persistence.dao;

import java.util.List;

import model.users.CartaDiCredito;
import model.users.Conto;

public interface CartaDiCreditoDao {
	public void save(CartaDiCredito carta);  // Create
	public CartaDiCredito findByPrimaryKey(String matricola);     // Retrieve
	public void update(CartaDiCredito carta); //Update
	public void delete(CartaDiCredito carta); //Delete
}
