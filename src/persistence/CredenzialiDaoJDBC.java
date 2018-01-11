package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.CartaDiCredito;
import model.Conto;
import model.Credenziali;
import model.Giocatore;
import model.TipoCredenziali;
import persistence.dao.CredenzialiDao;

public class CredenzialiDaoJDBC implements CredenzialiDao {

	@Override
	public void save(Credenziali credenziali) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
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
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "select c.password "
							+ "from credenziali as c "
							+ "where c.username=?";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, matricola);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				System.out.println("trovato");
				Credenziali c=new Credenziali(matricola,result.getString(1));
				return c;
			}
			return null;
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
