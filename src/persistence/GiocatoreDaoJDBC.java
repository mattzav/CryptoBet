package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Conto;
import model.Credenziali;
import model.Giocatore;
import model.TipoCredenziali;
import persistence.dao.GiocatoreDao;

public class GiocatoreDaoJDBC implements GiocatoreDao {
	

	public GiocatoreDaoJDBC() {
	}

	@Override
	public void save(Giocatore giocatore) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
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

	@Override
	public Giocatore findByCredenziali(Credenziali credenziali) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "select g.nome,g.cognome, g.conto from giocatore as g, credenziali as c where g.username=? and g.username=c.username and c.password=? and c.tipo=?";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, credenziali.getUsername());
			statement.setString(2, credenziali.getPassword());
			statement.setString(3, TipoCredenziali.USER);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				Giocatore g=new Giocatore("", "", credenziali, null);
				g.setNome(result.getString("nome"));
				g.setCognome(result.getString("cognome"));
				Conto c=new Conto(null);
				c.setCodice(result.getLong("conto"));
				g.setConto(c);
				return g;
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
}
