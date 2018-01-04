package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Conto;
import model.EsitoPartita;
import model.Partita;
import model.SchemaDiScommessa;
import model.Scommessa;
import persistence.dao.ScommessaDao;

public class ScommessaDaoJDBC implements ScommessaDao {

	@Override
	public void save(Scommessa scommessa) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "insert into scommessa(codice, dataemissione, contoassociato, importo_giocato,quota_totale,bonus,numero_esiti,vincita_potenziale) values (?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, scommessa.getCodice());
			statement.setDate(2, new java.sql.Date(scommessa.getData_emissione().getTime()));
			statement.setLong(3, scommessa.getConto_associato().getCodice());
			statement.setFloat(4, scommessa.getSchema_scommessa().getImporto_giocato());
			statement.setFloat(5, scommessa.getSchema_scommessa().getQuota_totale());
			statement.setFloat(6, scommessa.getSchema_scommessa().getBonus());
			statement.setFloat(7, scommessa.getSchema_scommessa().getNumero_esiti());
			statement.setFloat(8, scommessa.getSchema_scommessa().getVincita_potenziale());
			statement.executeUpdate();
			
			for(EsitoPartita esito_giocato : scommessa.getSchema_scommessa().getEsiti_giocati()) {
				insert="insert into scommessa_esitopartita(scommessa, esito, partita) values (?,?,?)";
				statement=connection.prepareStatement(insert);
				statement.setLong(1, scommessa.getCodice());
				statement.setString(2, esito_giocato.getEsito().getDescrizione());
				statement.setLong(3, esito_giocato.getPartita().getCodice());
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
	public Scommessa findByPrimaryKey(Long codice) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		Scommessa scommessa = null;
		try {
			PreparedStatement statement;
			String query = "select s.dataemissione, s.contoassociato, s.importo_giocato,s.quota_totale,s.bonus,s.numero_esiti,"
					+ "s.vincita_potenziale,c.saldo,c.data_apertura from scommessa as s,conto as c where s.codice=? and s.contoassociato=c.codice"; 
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			ResultSet result = statement.executeQuery();
			if (result.next()){
				ArrayList<EsitoPartita> esiti_giocati = new ArrayList<EsitoPartita>();
				//controllare se va bene discutere ( aggiungere il join con la partita
				query = "select s.*,es.quota scommessa_esitopartita as s,esitopartita as es where s.scommessa=?,s.esito=es.esito,s.partita=es.partita";
				statement=connection.prepareStatement(query);
				statement.setLong(1, codice);
				ResultSet result2 = statement.executeQuery();
				while (result.next()) {
					EsitoPartita corrente = new EsitoPartita(new model.Esito(result.getString(2)), result.getFloat(4), new Partita(result.getLong(3), null,null,null,null,null,null,null));
					esiti_giocati.add(corrente);
				}
				SchemaDiScommessa schema_scommessa  = new SchemaDiScommessa(result.getFloat(3), result.getFloat(4), result.getFloat(5), result.getInt(6), result.getFloat(7), esiti_giocati);
				scommessa=new Scommessa(codice, result.getDate(1), new Conto(result.getLong(2), result.getFloat(8), result.getDate(9), null),schema_scommessa);
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
		return esitoPartita;
	}

	@Override
	public List<Scommessa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Scommessa scommessa) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Scommessa scommessa) {
		// TODO Auto-generated method stub

	}

}
