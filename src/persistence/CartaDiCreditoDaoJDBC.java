package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CartaDiCredito;
import persistence.dao.CartaDiCreditoDao;


public class CartaDiCreditoDaoJDBC implements CartaDiCreditoDao {
	private DataSource dataSource;

	public CartaDiCreditoDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(CartaDiCredito carta) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into cartaDiCredito(codice, data_scadenza, saldo) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, carta.getCodice());
			statement.setDate(2, null);
			statement.setFloat(3, carta.getSaldo());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	@Override
	public CartaDiCredito findByPrimaryKey(String matricola) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CartaDiCredito> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(CartaDiCredito carta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(CartaDiCredito carta) {
		// TODO Auto-generated method stub
		
	}

}
