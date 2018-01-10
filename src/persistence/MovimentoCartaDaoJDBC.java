package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.Campionato;
import model.Conto;
import model.MovimentoCarta;
import model.Partita;
import model.Squadra;
import persistence.dao.MovimentoCartaDao;

public class MovimentoCartaDaoJDBC implements MovimentoCartaDao{

	public MovimentoCartaDaoJDBC() {
	}

	@Override
	public void save(MovimentoCarta movimento) {
		
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "insert into movimentoCarta(codice, data, ora, tipo, importo, conto) values (?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, movimento.getCodice());
			statement.setDate(2, new java.sql.Date(movimento.getDataEffettuazione().getTime()));
			SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm:ss aa");
			String time = localDateFormat.format(movimento.getDataEffettuazione());
			try {
				statement.setTime(3, new java.sql.Time(localDateFormat.parse(time).getTime()));
			} catch (ParseException e) {
			}
			statement.setString(4, movimento.getTipo());
			statement.setFloat(5, movimento.getImporto());
			statement.setLong(6, movimento.getConto().getCodice());
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
	public List<MovimentoCarta> findAll(Conto conto) {
		
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<MovimentoCarta> movimenti = new ArrayList<>();
		try {

			PreparedStatement statement;
			String query = "select m.codice, m.data, m.ora, m.tipo, m.importo "
							+ "from movimentoCarta as m "
							+ "where m.conto=?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, conto.getCodice());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				
				MovimentoCarta movimento=new MovimentoCarta(result.getLong(1), new java.util.Date(result.getDate(2).getTime()), result.getString(4), result.getFloat(5), conto);
				movimenti.add(movimento);
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
		return movimenti;
	}


	public void delete(MovimentoCarta movimento) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String delete = "delete FROM movimento WHERE codice = ? ";
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.setLong(1, movimento.getCodice());
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
	
}
