package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Giocatore;
import persistence.dao.GiocatoreDao;

public class GiocatoreDaoJDBC implements GiocatoreDao {
	private DataSource dataSource;

	public GiocatoreDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Giocatore giocatore) {
		Connection connection = this.dataSource.getConnection();
		try {
			String insert = "insert into giocatore(codice, nome, cognome, data_nascita, username, conto) values (?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			System.out.println(giocatore.getCodice());
			statement.setLong(1, giocatore.getCodice());
			statement.setString(2, giocatore.getNome());
			statement.setString(3, giocatore.getCognome());
//			long secs = giocatore.getDataNascita().getTime();
			statement.setDate(4,null);// new java.sql.Date(secs));
			statement.setString(5, giocatore.getCredenziali().getUsername());
			statement.setLong(6, giocatore.getConto().getCodice());
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
	public Giocatore findByPrimaryKey(String matricola) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Giocatore> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Giocatore giocatore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Giocatore giocatore) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPassword(Giocatore giocatore, String password) {
		// TODO Auto-generated method stub
		
	}
}
