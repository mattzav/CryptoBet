package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Credenziali;
import model.Giocatore;
import persistence.dao.CredenzialiDao;

public class CredenzialiDaoJDBC implements CredenzialiDao {
	private DataSource dataSource;

	public CredenzialiDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Credenziali credenziali) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into credenziali(username, password, tipo) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, credenziali.getUsername());
			statement.setString(2, credenziali.getPassword());
			statement.setString(3, credenziali.getTipo());
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
	public Credenziali findByPrimaryKey(String matricola) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Credenziali> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Credenziali credenziali) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Credenziali credenziali) {
		// TODO Auto-generated method stub
		
	}

}
