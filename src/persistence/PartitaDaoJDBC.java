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

import model.footballdata.Campionato;
import model.footballdata.Esito;
import model.footballdata.EsitoPartita;
import model.footballdata.Partita;
import model.footballdata.Squadra;
import persistence.dao.EsitoDao;
import persistence.dao.PartitaDao;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class PartitaDaoJDBC implements PartitaDao {

	@Override
	public void save(Partita partita, Connection connection) {

		Long codice = findExistingMatch(partita,connection);

		if (codice != null) {
			partita.setCodice(codice);
			update(partita, connection);
		} else {
			String insert = "insert into partita(codice,squadraCasa,squadraOspite,campionato,data,ora,finita,goalCasa,goalOspite) values (?,?,?,?,?,?,?,?,?)";
			try {
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
				PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getPartitaDao();
				for (Esito e : esitoDao.findAll()) {
					float quota = partitaDao.getQuota(partita.getCodice(), e.getDescrizione(),connection);
					insert = "insert into esitopartita(esito,partita,quota,disponibile,stato) values (?,?,?,?,?)";
					statement = connection.prepareStatement(insert);
					statement.setString(1, e.getDescrizione());
					statement.setLong(2, partita.getCodice());
					statement.setFloat(3, quota);
					statement.setBoolean(4, true);
					statement.setString(5, "non verificato");
					statement.executeUpdate();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	@Override
	public void update(Partita partita, Connection connection) {

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
			String[] esiti;
			if (partita.isFinita()) {
				if (partita.getGoal_casa() > partita.getGoal_ospite()) {
					update = "update esitopartita SET stato=? where partita=?  and (esito=? or esito=? or esito=?)";
					esiti = new String[] { "1", "1X", "12" };
				}

				else if (partita.getGoal_casa() < partita.getGoal_ospite()) {
					update = "update esitopartita SET stato=? where partita=? and (esito=? or esito=? or esito=?)";
					esiti = new String[] { "2", "X2", "12" };
				}

				else {
					update = "update esitopartita SET stato=? where partita=? and (esito=? or esito=? or esito=?)";
					esiti = new String[] { "X", "1X", "X2" };
				}
				statement = connection.prepareStatement(update);
				statement.setString(1, "indovinato");
				statement.setLong(2, partita.getCodice());
				int i = 3;
				for (String s : esiti) {
					statement.setString(i, s);
					i++;
				}
				statement.executeUpdate();

				esiti = null;
				if (partita.getGoal_casa() + partita.getGoal_ospite() >= 3) {
					update = "update esitopartita SET stato=? where partita=? and esito=?";
					esiti = new String[] { "O" };
				} else {
					update = "update esitopartita SET stato=? where partita=? and esito=?";
					esiti = new String[] { "U" };
				}
				statement = connection.prepareStatement(update);
				statement.setString(1, "indovinato");
				statement.setLong(2, partita.getCodice());
				statement.setString(3, esiti[0]);
				statement.executeUpdate();

				if (partita.getGoal_casa() > 0 && partita.getGoal_ospite() > 0) {
					update = "update esitopartita SET stato=? where partita=? and esito=?";
					esiti[0] = "GG";
				} else {
					update = "update esitopartita SET stato=? where partita=? and esito=?";
					esiti[0] = "NG";
				}
				statement = connection.prepareStatement(update);
				statement.setString(1, "indovinato");
				statement.setLong(2, partita.getCodice());
				statement.setString(3, esiti[0]);
				statement.executeUpdate();

				update = "update esitopartita SET stato=? where partita=? and stato=?";
				statement = connection.prepareStatement(update);
				statement.setString(1, "sbagliato");
				statement.setLong(2, partita.getCodice());
				statement.setString(3, "non verificato");
				statement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
	}

	@Override
	public Long findExistingMatch(Partita p, Connection connection) {
		try {
			PreparedStatement statement;
			String query = "select p.codice from partita as p where p.squadracasa=? and p.squadraospite=? and p.campionato=? ";
			statement = connection.prepareStatement(query);
			statement.setString(1, p.getSquadra_casa().getNome());
			statement.setString(2, p.getSquadra_ospite().getNome());
			java.sql.Date date = new java.sql.Date(p.getData_ora().getTime());
			statement.setLong(3, p.getCampionato().getCodice());
			ResultSet result = statement.executeQuery();
			if (result.next())
				return result.getLong(1);
			return null;
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
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

	@Override
	public float[] getPuntiSquadre(long codice,Connection connection) {
		float media_punti_squadre[] = new float[2];

		try {
			PreparedStatement statement;

			float punti_casa = 0;

			// query vittorie squadra casa
			String query = "select count(*) from partita as p1,partita as p2 where p1.codice=? and (p2.finita=true and ((p1.squadracasa=p2.squadracasa and p2.goalcasa>p2.goalospite) or (p1.squadracasa=p2.squadraospite and p2.goalcasa<p2.goalospite)))";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			ResultSet result = statement.executeQuery();
			result.next();
			punti_casa = result.getInt(1) * 3;

			// query pareggi squadra casa
			query = "select count(*) from partita as p1,partita as p2 where p1.codice=? and (p2.finita=true and ((p1.squadracasa=p2.squadracasa and p2.goalcasa=p2.goalospite) or (p1.squadracasa=p2.squadraospite and p2.goalcasa=p2.goalospite)))";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			result = statement.executeQuery();
			result.next();
			punti_casa += result.getInt(1);

			// query pareggi squadra casa
			query = "select count(*) from partita as p, partita as p1 where p.codice=? and (p1.squadracasa=p.squadracasa or p1.squadraospite=p.squadracasa) and p1.finita=true";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			result = statement.executeQuery();
			result.next();
			if (result.getInt(1) != 0)
				punti_casa /= result.getInt(1);

			float punti_ospite = 0;

			// query vittorie squadra ospite
			query = "select count(*) from partita as p1,partita as p2 where p1.codice=? and (p2.finita = true and ((p1.squadraospite=p2.squadracasa and p2.goalcasa>p2.goalospite) or (p1.squadraospite=p2.squadraospite and p2.goalcasa<p2.goalospite)))";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			result = statement.executeQuery();
			result.next();
			punti_ospite = result.getInt(1) * 3;

			// query pareggi squadra ospite
			query = "select count(*) from partita as p1,partita as p2 where p1.codice=? and (p2.finita=true and ((p1.squadraospite=p2.squadracasa and p2.goalcasa=p2.goalospite) or (p1.squadraospite=p2.squadraospite and p2.goalcasa=p2.goalospite)))";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			result = statement.executeQuery();
			result.next();
			punti_ospite += result.getInt(1);

			// query pareggi squadra casa
			query = "select count(*) from partita as p, partita as p1 where p.codice=? and (p1.squadracasa=p.squadraospite or p1.squadraospite=p.squadraospite) and p1.finita=true";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			result = statement.executeQuery();
			result.next();
			if (result.getInt(1) != 0)
				punti_ospite /= result.getInt(1);

			media_punti_squadre[0] = punti_casa;
			media_punti_squadre[1] = punti_ospite;

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}
		return media_punti_squadre;
	}

	@Override
	public float[] getMediaPartiteASegno(long codicePartita,Connection connection) {
		float media_partite_a_segno_casa;
		float media_partite_a_segno_ospite;
		try {
			PreparedStatement statement;

			String query = "select count(*) from partita as p, partita as p1 where p.codice=? and ((p.squadracasa=p1.squadracasa and p1.goalCasa>0) or (p.squadracasa=p1.squadraospite and p1.goalOspite>0)) ";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			ResultSet result = statement.executeQuery();
			result.next();
			float num_partite_a_segno_casa = result.getInt(1);

			query = "select count(*) from partita as p, partita as p1 where p.codice=? and ((p.squadracasa=p1.squadracasa) or (p.squadracasa=p1.squadraospite)) and p1.finita=true ";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();

			if (result.getInt(1) != 0)
				media_partite_a_segno_casa = num_partite_a_segno_casa / result.getInt(1);
			else
				media_partite_a_segno_casa = 0.5f;

			query = "select count(*) from partita as p, partita as p1 where p.codice=? and ((p.squadraospite=p1.squadracasa and p1.goalCasa>0) or (p.squadraospite=p1.squadraospite and p1.goalOspite>0)) ";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();
			float num_partite_a_segno_ospite = result.getInt(1);

			query = "select count(*) from partita as p, partita as p1 where p.codice=? and ((p.squadraospite=p1.squadracasa) or (p.squadraospite=p1.squadraospite)) and p1.finita=true ";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();
			if (result.getInt(1) != 0)
				media_partite_a_segno_ospite = num_partite_a_segno_ospite / result.getInt(1);
			else
				media_partite_a_segno_ospite = 0.5f;
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}

		float media_partite_a_segno[] = new float[2];
		media_partite_a_segno[0] = media_partite_a_segno_casa;
		media_partite_a_segno[1] = media_partite_a_segno_ospite;
		return media_partite_a_segno;
	}

	@Override
	public float[] getMediaGoal(long codicePartita,Connection connection) {
		float media_goal_casa;
		float media_goal_ospite;
		try {
			PreparedStatement statement;

			// numero di goal che la squadra che gioca in casa ha fatto in casa
			String query = "select sum(p1.goalCasa) from partita as p, partita as p1 where p.codice=? and p.squadracasa=p1.squadracasa and p1.goalCasa>0";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			ResultSet result = statement.executeQuery();
			result.next();
			float num_goal_casa = result.getInt(1);

			// numero di goal che la squadra che gioca in casa ha fatto fuori casa
			query = "select sum(p1.goalOspite) from partita as p, partita as p1 where p.codice=? and p.squadracasa=p1.squadraospite and p1.goalOspite>0";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();
			num_goal_casa += result.getInt(1);

			// numero di partite disputate dalla squadra che gioca in casa
			query = "select count(*) from partita as p, partita as p1 where p.codice=? and ((p.squadracasa=p1.squadracasa) or (p.squadracasa=p1.squadraospite)) and p1.finita=true ";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();

			// media ottenuta dai goal divisi per le partite disputate
			if (result.getInt(1) != 0)
				media_goal_casa = num_goal_casa / result.getInt(1);
			else
				media_goal_casa = 1.5f;

			// numero di goal che la squadra che gioca fuori casa ha fatto in casa
			query = "select sum(p1.goalCasa) from partita as p, partita as p1 where p.codice=? and p.squadraospite=p1.squadracasa and p1.goalCasa>0";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();
			float num_goal_ospite = result.getInt(1);

			// numero di goal che la squadra che gioca fuori casa ha fatto fuori casa
			query = "select sum(p1.goalOspite) from partita as p, partita as p1 where p.codice=? and p.squadraospite=p1.squadraospite and p1.goalOspite>0";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();
			num_goal_ospite += result.getInt(1);

			// numero di partite disputate dalla squadra che gioca fuori casa
			query = "select count(*) from partita as p, partita as p1 where p.codice=? and ((p.squadraospite=p1.squadracasa) or (p.squadraospite=p1.squadraospite)) and p1.finita=true ";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codicePartita);
			result = statement.executeQuery();
			result.next();

			// media ottenuta dai goal divisi per le partite disputate
			if (result.getInt(1) != 0)
				media_goal_ospite = num_goal_ospite / result.getInt(1);
			else
				media_goal_ospite = 1.5f;
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}

		float media_gol[] = new float[2];
		media_gol[0] = media_goal_casa;
		media_gol[1] = media_goal_ospite;
		return media_gol;
	}

	public float getQuota(long codicePartita, String esito,Connection connection) {
		String tipo_esito = "";

		// controllo riguardo tipo dell' esito per cui il client ha chiesto il
		// suggerimento
		if (esito.contains("1") || esito.contains("2") || esito.contains("X"))
			tipo_esito += "classico";
		else if (esito.contains("G"))
			tipo_esito += "goal";
		else
			tipo_esito += "numerogoal";

		PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getPartitaDao();
		if (tipo_esito.equals("classico")) {

			// se ha chiesto un suggerimento riguardo gli esiti classici, chiedo al db la
			// media punti delle due squadre
			float punti[] = partitaDao.getPuntiSquadre(codicePartita,connection);

			float probabilita_eventi[] = new float[3];

			// se le due squadre hanno media punti pari a 0, inizializzo le probabilita
			if (punti[0] == 0 && punti[1] == 0) {
				probabilita_eventi[0] = 0.5f;
				probabilita_eventi[2] = 0.5f;
				probabilita_eventi[1] = 1 / 2.5f;
			}
			// altrimenti le calcolo come proporzioni delle due medie punti
			else {
				probabilita_eventi[0] = (float) (punti[0] / (punti[0] + punti[1]));
				probabilita_eventi[1] = (float) (1 / (3.0f + (Math.abs(punti[0] - punti[1]))));
				probabilita_eventi[2] = (float) (punti[1] / (punti[0] + punti[1]));
			}

			float probabilita = 0;

			// calcolo la probabilità dell'esito come somma delle probabilità degli esiti
			// giocati
			if (esito.contains("1"))
				probabilita += probabilita_eventi[0];
			if (esito.contains("X"))
				probabilita += probabilita_eventi[1];
			if (esito.contains("2"))
				probabilita += probabilita_eventi[2];

			// evito di avere una probabilita pari a 0 che originirebbe quota infinita o una
			// probabilita pari a 1 che originirebbe una quota pari a 1
			if (probabilita == 0)
				probabilita = 0.1f;
			else if (probabilita >= 1)
				probabilita = 0.9f;

			// calcolo il valore atteso (quota finale)
			float valore_atteso = (1 / probabilita);

			// la restituisco
			String val = String.valueOf(valore_atteso);
			return Float.valueOf(val.substring(0, Math.min(4, val.length())));
		} else if (tipo_esito.equals("goal")) {

			// se ha chiesto un suggerimento riguardo gli esiti (GG o NG), chiedo al db la
			// media delle partite in cui le due squadre hanno segnato almeno un goal
			float media_partite_a_segno[] = partitaDao.getMediaPartiteASegno(codicePartita,connection);

			float probabilita = 0;

			// calcolo la probabilita del GG come il prodotto delle medie delle partite in
			// cui entrambi hanno segnato
			if (esito.equals("GG"))
				probabilita = media_partite_a_segno[0] * media_partite_a_segno[1];
			// calcolo la probabilita del NG come (1-P(GG))
			else if (esito.equals("NG"))
				probabilita = (1 - (media_partite_a_segno[0] * media_partite_a_segno[1]));

			// evito probabilita pari a 0 o maggiori di 1
			if (probabilita == 0)
				probabilita = 0.1f;
			else if (probabilita >= 1)
				probabilita = 0.9f;

			float valore_atteso = (1 / probabilita);
			String val = String.valueOf(valore_atteso);
			return Float.valueOf(val.substring(0, Math.min(4, val.length())));
		} else if (tipo_esito.equals("numerogoal")) {

			// se ha chiesto un suggerimento riguardo gli esiti (U o O), chiedo al db la
			// media goal delle due squadre
			float media_goal_a_partita[] = partitaDao.getMediaGoal(codicePartita,connection);

			float probabilita = 0;

			// calcolo la probabilita dell'under
			if (esito.equals("U")) {
				probabilita = 0.5f + ((3 - (media_goal_a_partita[0] + media_goal_a_partita[1])) / 10);
			}
			// altrimenti quella dell'over
			else {
				probabilita = 0.5f + (((media_goal_a_partita[0] + media_goal_a_partita[1]) - 3) / 10);
			}

			// evito probabilita pari a 0 o maggiori di 1
			if (probabilita == 0)
				probabilita = 0.1f;
			else if (probabilita >= 1)
				probabilita = 0.9f;

			// calcolo il valore atteso che sarebbe la quota finale
			float valore_atteso = (1 / probabilita);

			// restituisco la quota
			String val = String.valueOf(valore_atteso);
			return Float.valueOf(val.substring(0, Math.min(4, val.length())));

		}
		return 1.0f;

	}
}
