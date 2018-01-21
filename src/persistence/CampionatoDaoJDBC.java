package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.footballdata.Campionato;
import model.footballdata.Squadra;
import persistence.dao.CampionatoDao;

public class CampionatoDaoJDBC implements CampionatoDao {

	
	@Override
	public void save(Campionato campionato, Connection connection)throws SQLException {

		Campionato esistente = findByPrimaryKey(campionato.getCodice(),connection);
		if(esistente != null)
			return;
		
		String insert = "insert into campionato(codice,nome) values (?,?)";
		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setLong(1, campionato.getCodice());
		statement.setString(2, campionato.getNome());
		statement.executeUpdate();
	}

	@Override
	public Campionato findByPrimaryKey(Long codice, Connection connection)throws SQLException {

		Campionato campionato = null;
		PreparedStatement statement;
		String query = "select c.codice,c.nome from campionato as c where c.codice = ?"; 
		statement = connection.prepareStatement(query);
		statement.setLong(1, codice);
		ResultSet result = statement.executeQuery();
		if (result.next()) 
			campionato = new Campionato(codice, result.getString(2));
		return campionato;
	}

	@Override
	public List<Campionato> findAll() {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<Campionato> campionati = new ArrayList<>();
		try {
			
			PreparedStatement statement;
			String query = "select * from campionato";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Long codice = result.getLong(1);
				String nome = result.getString(2);
				Campionato campionato = new Campionato(codice, nome);
				campionati.add(campionato);
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
		return campionati;

	}


	

}
