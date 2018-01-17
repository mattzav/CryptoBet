package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.footballdata.EsitoPartita;
import model.footballdata.Squadra;
import persistence.dao.SquadraDao;

public class SquadraDaoJDBC implements SquadraDao {

	@Override
	public void save(Squadra squadra, Connection connection) {
			Squadra esistente = findByPrimaryKey(squadra.getNome());
			if (esistente != null)
				return;

			String insert = "insert into squadra(nome,scudetto) values (?,?)";
			try {
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setString(1, squadra.getNome());
				statement.setString(2, squadra.getScudetto());
				statement.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public Squadra findByPrimaryKey(String nome) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		Squadra squadra = null;
		try {
			PreparedStatement statement;
			String query = "select nome,scudetto from squadra where nome = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, nome);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				squadra = new Squadra(result.getString(1));
				squadra.setScudetto(result.getString(2));
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return squadra;
	}

	@Override
	public List<Squadra> findAll() {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<Squadra> squadre = new LinkedList<>();
		try {

			PreparedStatement statement;
			String query = "select * from squadra";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				String nome = result.getString(1);
				Squadra squadra = new Squadra(nome);
				squadra.setScudetto(result.getString(2));
				squadre.add(squadra);
			}

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return squadre;

	}

	@Override
	public void update(Squadra squadra, Connection connection) {

		try {
			String insert = "update squadra SET scudetto = ? where nome=?";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, squadra.getScudetto());
			statement.setString(2, squadra.getNome());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} 

	}

}
