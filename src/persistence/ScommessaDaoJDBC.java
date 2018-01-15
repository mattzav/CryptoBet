package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.betting.MovimentoScommessa;
import model.betting.SchemaDiScommessa;
import model.betting.Scommessa;
import model.footballdata.EsitoPartita;
import model.footballdata.Partita;
import model.footballdata.Squadra;
import model.users.Conto;
import model.users.Giocatore;
import model.users.TipoMovimento;
import persistence.dao.MovimentoScommessaDao;
import persistence.dao.ScommessaDao;

public class ScommessaDaoJDBC implements ScommessaDao {

	@Override
	public void save(Scommessa scommessa) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "insert into scommessa(codice, data_emissione, conto_associato, importo_giocato,quota_totale,bonus,numero_esiti,vincita_potenziale,stato) values (?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, scommessa.getCodice());
			statement.setDate(2, new java.sql.Date(scommessa.getData_emissione().getTime()));
			statement.setLong(3, scommessa.getConto_associato().getCodice());
			statement.setFloat(4, scommessa.getSchema_scommessa().getImporto_giocato());
			statement.setFloat(5, scommessa.getSchema_scommessa().getQuota_totale());
			statement.setFloat(6, scommessa.getSchema_scommessa().getBonus());
			statement.setFloat(7, scommessa.getSchema_scommessa().getNumero_esiti());
			statement.setFloat(8, scommessa.getSchema_scommessa().getVincita_potenziale());
			statement.setString(9, "non conclusa");
			statement.executeUpdate();

			for (EsitoPartita esito_giocato : scommessa.getSchema_scommessa().getEsiti_giocati()) {
				insert = "insert into scommessa_esitopartita(scommessa, esito, partita) values (?,?,?)";
				statement = connection.prepareStatement(insert);
				statement.setLong(1, scommessa.getCodice());
				statement.setString(2, esito_giocato.getEsito().getDescrizione());
				statement.setLong(3, esito_giocato.getPartita().getCodice());
				statement.executeUpdate();
			}
			MovimentoScommessa movimento = new MovimentoScommessa(scommessa.getSchema_scommessa().getImporto_giocato(),
					TipoMovimento.PRELIEVO, scommessa);
			MovimentoScommessaDao movimentoDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL)
					.getMovimentoScommessaDAO();
			movimentoDao.save(movimento);

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
	public Scommessa findByPrimaryKey(Long codice) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		Scommessa scommessa = null;
		try {
			PreparedStatement statement;
			String query = "select s.dataemissione, s.contoassociato, s.importo_giocato,s.quota_totale,s.bonus,s.numero_esiti,"
					+ "s.vincita_potenziale,s.stato from scommessa as s where s.codice=? ";
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				ArrayList<EsitoPartita> esiti_giocati = new ArrayList<EsitoPartita>();
				// controllare se va bene discutere ( aggiungere il join con la partita
				query = "select e.esito,e.quota,p.codice,p.squadraCasa,p.squadraOspite,p.data,e.stato from scommessa_esitopartita as se, esitopartita as e, partita as p where se.partita=e.partita and se.esito = e.esito and e.partita=p.codice and se.scommessa=?";
				statement = connection.prepareStatement(query);
				statement.setLong(1, codice);
				ResultSet result2 = statement.executeQuery();
				while (result2.next()) {
					EsitoPartita corrente = new EsitoPartita(true, new model.footballdata.Esito(result.getString(1)),
							result.getFloat(2),
							new Partita(result.getLong(3), new Squadra(result.getString(4)),
									new Squadra(result.getString(5)), -1, -1, null, result.getDate(6).getTime(), false),
							result2.getString(7));
					esiti_giocati.add(corrente);
				}
				SchemaDiScommessa schema_scommessa = new SchemaDiScommessa(result.getFloat(3), result.getFloat(4),
						result.getFloat(5), result.getInt(6), result.getFloat(7), esiti_giocati);
				scommessa = new Scommessa(codice, result.getDate(1), new Conto(result.getLong(2), 0.0f, null, null),
						schema_scommessa, result.getString(8));
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
		return scommessa;
	}

	@Override
	public List<Scommessa> findAll(Giocatore giocatore) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<Scommessa> scommesse = new ArrayList<>();
		try {

			PreparedStatement statement;
			String query = "select s.codice,s.data_emissione,s.importo_giocato,s.numero_esiti,s.vincita_potenziale,s.stato from scommessa as s where s.conto_associato = ?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, giocatore.getConto().getCodice());
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Scommessa scommessa = new Scommessa(result.getLong(1), result.getDate(2), null,
						new SchemaDiScommessa(result.getFloat(3), 0, 0, result.getInt(4), result.getFloat(5), null),
						result.getString(6));
				scommesse.add(scommessa);
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
		return scommesse;
	}

	@Override
	public void update(Scommessa scommessa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Scommessa scommessa) {
		// TODO Auto-generated method stub

	}

	@Override
	public String verificaScommessa(Long codiceScommessa) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		String esito_scommessa = "vinta";
		try {
			String query = "select (e.stato) from scommessa_esitopartita as s, esitopartita as e where s.scommessa=? and s.esito=e.esito and s.partita=e.partita";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, codiceScommessa);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				if (result.getString(1).equals("sbagliato")) {
					esito_scommessa = "persa";
					break;
				} else if (result.getString(1).equals("non verificato")) {
					esito_scommessa = "non conclusa";
				}
			}

			// dopo aver controllato l'esito aggiorno la scommessa sul db
			String update = "UPDATE scommessa  SET stato=? where codice=?";
			statement = connection.prepareStatement(update);
			statement.setString(1, esito_scommessa);
			statement.setLong(2, codiceScommessa);
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
		return esito_scommessa;
	}

}
