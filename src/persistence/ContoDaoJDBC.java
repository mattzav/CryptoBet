package persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.footballdata.EsitoPartita;
import model.users.CartaDiCredito;
import model.users.Conto;
import persistence.dao.ContoDao;

public class ContoDaoJDBC implements ContoDao {


	@Override
	public void save(Conto conto,Connection connection)throws SQLException {

		String insert = "insert into conto(codice, saldo, data_apertura, codice_carta) values (?,?,?,?)";
		PreparedStatement statement = connection.prepareStatement(insert);
		statement.setLong(1,conto.getCodice());
		statement.setFloat(2,conto.getSaldo());
		statement.setDate(3, null);
		statement.setString(4, conto.getCarta().getCodiceCarta());
		statement.executeUpdate();
	}

	@Override
	public Conto findByPrimaryKey(Long codice) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		Conto conto= null;
		try {
			PreparedStatement statement;
			String query = "select c.saldo,c.data_apertura,c.codice_carta from conto as c where c.codice = ?"; 
			statement = connection.prepareStatement(query);
			statement.setLong(1, codice);
			ResultSet result = statement.executeQuery();
			if (result.next()) 
				conto=new Conto(codice, result.getFloat(1), result.getDate(2), new CartaDiCredito(result.getString(3)));
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		return conto;
	}

	@Override
	public List<Conto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Conto conto,Connection connection)throws SQLException {
		
			String update = "update conto SET saldo = ? where codice=? ";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setFloat(1, conto.getSaldo());
			statement.setLong(2, conto.getCodice());
			statement.executeUpdate();
	}

	@Override
	public void delete(Conto conto) {
		// TODO Auto-generated method stub
		
	}

	
}
