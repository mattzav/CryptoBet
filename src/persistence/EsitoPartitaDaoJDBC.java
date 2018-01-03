package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Esito;
import model.EsitoPartita;
import model.Partita;
import persistence.dao.EsitoPartitaDao;

public class EsitoPartitaDaoJDBC implements EsitoPartitaDao {

	@Override
	public void save(EsitoPartita esitoPartita) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "insert into esitopartita(esito, partita, quota) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, esitoPartita.getEsito().getDescrizione());
			statement.setLong(2, esitoPartita.getPartita().getCodice());
			statement.setFloat(3, esitoPartita.getQuota());
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
	public EsitoPartita findByPrimaryKey(Esito esito, Partita partita) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		EsitoPartita esitoPartita= null;
		try {
			PreparedStatement statement;
			String query = "select t.quota from esitopartita as t where t.esito = ? and t.partita = ? "; 
			statement = connection.prepareStatement(query);
			statement.setString(1, esito.getDescrizione());
			statement.setLong(2, partita.getCodice());
			ResultSet result = statement.executeQuery();
			if (result.next()) 
				esitoPartita=new EsitoPartita(esito, result.getFloat(1), partita);
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		return esitoPartita;
	}

	@Override
	public void update(EsitoPartita esitoPartita) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "update esitopartita SET esito = ?, quota = ?, partita = ? where esito = ? and partita = ?";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, esitoPartita.getEsito().getDescrizione());
			statement.setFloat(2, esitoPartita.getQuota());
			statement.setLong(3, esitoPartita.getPartita().getCodice());
			statement.setString(4, esitoPartita.getEsito().getDescrizione());
			statement.setLong(5, esitoPartita.getPartita().getCodice());
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
	public void delete(EsitoPartita esitoPartita) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EsitoPartita> findByPartita(Partita partita) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<EsitoPartita> esiti_partita = new LinkedList<>();
		try {
			
			PreparedStatement statement;
			String query = "select p.esito,p.quota from esitopartita as p where p.partita = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, partita.getCodice());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				EsitoPartita corrente = new EsitoPartita(new model.Esito(result.getString(1)), result.getFloat(2), partita);
				esiti_partita.add(corrente);
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
		return esiti_partita;
	}

}
