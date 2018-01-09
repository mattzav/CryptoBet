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
			String insert = "insert into esitopartita(esito, partita, quota, disponibile) values (?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, esitoPartita.getEsito().getDescrizione());
			statement.setLong(2, esitoPartita.getPartita().getCodice());
			statement.setFloat(3, esitoPartita.getQuota());
			statement.setBoolean(4, esitoPartita.isDisponibile());
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
			String query = "select t.quota,t.disponibile,t.stato from esitopartita as t where t.esito = ? and t.partita = ? "; 
			statement = connection.prepareStatement(query);
			statement.setString(1, esito.getDescrizione());
			statement.setLong(2, partita.getCodice());
			ResultSet result = statement.executeQuery();
			if (result.next()) 
				esitoPartita=new EsitoPartita(result.getBoolean(2), esito, result.getFloat(1), partita,result.getString(3));
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
			String insert = "update esitopartita SET esito = ?, quota = ?, partita = ?, disponibile = ?, stato = ? where esito = ? and partita = ?";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, esitoPartita.getEsito().getDescrizione());
			statement.setFloat(2, esitoPartita.getQuota());
			statement.setLong(3, esitoPartita.getPartita().getCodice());
			statement.setBoolean(4, esitoPartita.isDisponibile());
			statement.setString(5, esitoPartita.getStato());
			statement.setString(6, esitoPartita.getEsito().getDescrizione());
			statement.setLong(7, esitoPartita.getPartita().getCodice());
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
			String query = "select p.esito,p.quota,p.disponibile,p.stato from esitopartita as p where p.partita = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, partita.getCodice());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				EsitoPartita corrente = new EsitoPartita(result.getBoolean(3),new model.Esito(result.getString(1)), result.getFloat(2), partita,result.getString(4));
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
