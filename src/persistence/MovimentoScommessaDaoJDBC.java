package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.EsitoPartita;
import model.MovimentoScommessa;
import model.TipoMovimento;
import persistence.dao.MovimentoScommessaDao;

public class MovimentoScommessaDaoJDBC implements MovimentoScommessaDao {

	@Override
	public void save(MovimentoScommessa movimentoScommessa) {
		
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String insert = "insert into movimentoscommessa(codice, importo, tipo, scommessa) values (?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, movimentoScommessa.getCodice_transazione());
			statement.setFloat(2, movimentoScommessa.getImporto());
			statement.setString(3, movimentoScommessa.getTipo_transazione());
			statement.setLong(4, movimentoScommessa.getScommessa().getCodice());
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
	public MovimentoScommessa findByPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovimentoScommessa> findAll() {
		// TODO Auto-generated method stub
		return null;
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
