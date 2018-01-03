package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import model.Campionato;
import model.Esito;
import model.Partita;
import model.Squadra;
import persistence.dao.EsitoDao;
import persistence.dao.PartitaDao;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class PartitaDaoJDBC implements PartitaDao {

	public PartitaDaoJDBC() {
	}

	@Override
	public void save(Partita partita) {

		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {

			System.out.println(partita.getCodice());
			if (findExistingMatch(partita)) {
				update(partita);
				System.out.println("update");
				return;
			}

			String insert = "insert into partita(codice,squadraCasa,squadraOspite,campionato,data,ora,finita,goalCasa,goalOspite) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, partita.getCodice());
			statement.setString(2, partita.getSquadra_casa().getNome());
			statement.setString(3, partita.getSquadra_ospite().getNome());
			statement.setLong(4, partita.getCampionato().getCodice());
			statement.setDate(5, new java.sql.Date(partita.getData_ora().getTime()));
			SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm:ss aa");
			String time = localDateFormat.format(partita.getData_ora());
			try {
				statement.setTime(6, new java.sql.Time(localDateFormat.parse(time).getTime()));
			} catch (ParseException e) {
			}
			statement.setBoolean(7, partita.isFinita());
			statement.setInt(8, partita.getGoal_casa());
			statement.setInt(9, partita.getGoal_ospite());
			statement.executeUpdate();

			EsitoDao esitoDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getEsitoDao();
			for (Esito e : esitoDao.findAll()) {
				insert = "insert into esitopartita(esito,partita,quota) values (?,?,?)";
				statement = connection.prepareStatement(insert);
				statement.setString(1, e.getDescrizione());
				statement.setLong(2, partita.getCodice());
				statement.setFloat(3, 1.0f);
				statement.executeUpdate();
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
	}

	@Override
	public void update(Partita partita) {

		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String update = "update partita SET squadracasa = ?, squadraospite = ?, data = ?,ora = ?, campionato = ?, goalcasa=?, goalospite=?, finita=? where squadracasa=? and squadraospite=? and campionato=? ";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, partita.getSquadra_casa().getNome());
			statement.setString(2, partita.getSquadra_ospite().getNome());
			long secs = partita.getData_ora().getTime();
			statement.setDate(3, new java.sql.Date(secs));
			SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm:ss aa");
			String time = localDateFormat.format(partita.getData_ora());
			try {
				statement.setTime(4, new java.sql.Time(localDateFormat.parse(time).getTime()));
			} catch (ParseException e) {
			}
			statement.setLong(5, partita.getCampionato().getCodice());
			statement.setInt(6, partita.getGoal_casa());
			statement.setInt(7, partita.getGoal_ospite());
			statement.setBoolean(8, partita.isFinita());
			statement.setString(9, partita.getSquadra_casa().getNome());
			statement.setString(10, partita.getSquadra_ospite().getNome());
			statement.setLong(11, partita.getCampionato().getCodice());
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
	public boolean findExistingMatch(Partita p) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			PreparedStatement statement;
			String query = "select * from partita as p where p.squadracasa=? and p.squadraospite=? and p.campionato=? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, p.getSquadra_casa().getNome());
			statement.setString(2, p.getSquadra_ospite().getNome());
			java.sql.Date date = new java.sql.Date(p.getData_ora().getTime());
			statement.setLong(3, p.getCampionato().getCodice());
			ResultSet result = statement.executeQuery();
			return result.next();
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
	public List<Partita> findAll(String nomeCampionato) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<Partita> partite = new LinkedList<>();
		try {

			PreparedStatement statement;
			String query = "select p.codice,p.squadraCasa,p.squadraOspite,p.data,p.ora,p.finita,p.goalCasa,p.goalOspite,p.codice from partita as p, campionato as c where p.campionato=c.codice and c.nome=? and p.finita=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, nomeCampionato);
			statement.setBoolean(2, false);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				
				Partita partita = new Partita(result.getLong(1), new Squadra(result.getString(2)),
						new Squadra(result.getString(3)), result.getInt(7), result.getInt(8),
						new Campionato(result.getLong(9), nomeCampionato),
						result.getDate(4).getTime() + result.getTime(5).getTime() + 1000 * 3600, result.getBoolean(6));
				partite.add(partita);
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
		return partite;
	}

}
