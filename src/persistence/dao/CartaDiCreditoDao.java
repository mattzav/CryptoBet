package persistence.dao;

import java.util.List;

import model.CartaDiCredito;
import model.Conto;

public interface CartaDiCreditoDao {
	public void save(CartaDiCredito carta);  // Create
	public CartaDiCredito findByPrimaryKey(String matricola);     // Retrieve
	public List<CartaDiCredito> findAll();       
	public void update(CartaDiCredito carta); //Update
	public void delete(CartaDiCredito carta); //Delete
}
