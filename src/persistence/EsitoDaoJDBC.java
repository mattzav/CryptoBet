package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.footballdata.Esito;
import model.footballdata.Squadra;
import persistence.dao.EsitoDao;

public class EsitoDaoJDBC implements EsitoDao {

	@Override
	public void save(Esito esito) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "insert into esito(descrizione) values (?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, esito.getDescrizione());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
	}

	@Override
	public List<Esito> findAll() {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<Esito> esiti = new ArrayList<>();
		try {
			
			PreparedStatement statement;
			String query = "select * from esito";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				String nome = result.getString(1);
				Esito esito = new Esito(nome);
				esiti.add(esito);
			}

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}
		return esiti;

	}

}
