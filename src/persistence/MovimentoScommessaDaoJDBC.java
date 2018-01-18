package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.betting.MovimentoScommessa;
import model.betting.Scommessa;
import model.footballdata.EsitoPartita;
import model.users.Conto;
import model.users.MovimentoCarta;
import model.users.TipoMovimento;
import persistence.dao.MovimentoScommessaDao;

public class MovimentoScommessaDaoJDBC implements MovimentoScommessaDao {

	@Override
	public void save(MovimentoScommessa movimentoScommessa,Connection connection)throws SQLException {
		
		String insert = "insert into movimentoscommessa(codice, importo, tipo, scommessa) values (?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setLong(1, movimentoScommessa.getCodice_transazione());
		statement.setFloat(2, movimentoScommessa.getImporto());
		statement.setString(3, movimentoScommessa.getTipo_transazione());
		statement.setLong(4, movimentoScommessa.getScommessa().getCodice());
		statement.executeUpdate();
			
	}

	@Override
	public MovimentoScommessa findByPrimaryKey() {
		return null;
	}

	@Override
	public List<MovimentoScommessa> findAll(Conto c) {
		
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		List<MovimentoScommessa> movimenti = new ArrayList<>();
		try {

			PreparedStatement statement;
			String query = "select m.codice, m.importo, m.tipo, m.scommessa "
							+ "from movimentoScommessa as m, scommessa s "
							+ "where m.scommessa=s.codice and s.conto_associato=?";
			statement = connection.prepareStatement(query);
			statement.setLong(1, c.getCodice());
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				MovimentoScommessa movimento=new MovimentoScommessa(result.getLong(1), result.getFloat(2), result.getString(3), new Scommessa(result.getLong(4), null, null, null,"non conclusa"));
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

	@Override
	public void update(MovimentoScommessa movimentoScommessa) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MovimentoScommessa movimentoScommessa) {
		// TODO Auto-generated method stub
		
	}

}
