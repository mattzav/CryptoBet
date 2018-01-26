package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.users.Amministratore;
import model.users.Conto;
import model.users.Credenziali;
import model.users.Giocatore;
import model.users.TipoCredenziali;
import persistence.dao.AmministratoreDao;

public class AmministratoreDaoJDBC implements AmministratoreDao {

	public AmministratoreDaoJDBC() {
	}

	@Override
	public void save(Amministratore admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Amministratore findByPrimaryKey(String codice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Amministratore> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Amministratore admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Amministratore admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean findByCredenziali(Credenziali credenziali) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		
		try {
			String insert = "select * from credenziali as c where c.username=? and c.password=? and c.tipo=?";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, credenziali.getUsername());
			statement.setString(2, credenziali.getPassword());
			statement.setString(3, TipoCredenziali.ADMIN);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				return true;
			}
			return false;
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