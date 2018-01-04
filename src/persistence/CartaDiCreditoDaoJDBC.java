package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.CartaDiCredito;
import persistence.dao.CartaDiCreditoDao;


public class CartaDiCreditoDaoJDBC implements CartaDiCreditoDao {

	public CartaDiCreditoDaoJDBC() {
	}

	@Override
	public void save(CartaDiCredito carta) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "insert into cartaDiCredito(codice, data_scadenza, saldo) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, carta.getCodiceCarta());
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
	public void update(CartaDiCredito carta) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String update = "update cartaDiCredito SET dataScadenza = ?, saldo= ? where codice=? ";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setDate(1, new java.sql.Date(carta.getScadenza().getTime()));
			statement.setFloat(2, carta.getSaldo());
			statement.setString(3, carta.getCodiceCarta());
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
	public void delete(CartaDiCredito carta) {
		
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String delete = "delete FROM cartaDiCredito WHERE codice = ? ";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setString(1, carta.getCodiceCarta());
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

}
