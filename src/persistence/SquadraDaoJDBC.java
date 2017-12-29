package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import model.Squadra;
import persistence.dao.SquadraDao;

public class SquadraDaoJDBC implements SquadraDao{

	private DataSource dataSource;

	public SquadraDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	@Override
	public void save(Squadra squadra) {
		Connection connection = this.dataSource.getConnection();
		try {
			
			Squadra esistente = findByPrimaryKey(squadra.getNome());
			if(esistente != null)
				return;
			
			String insert = "insert into squadra(nome) values (?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setString(1, squadra.getNome());
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
	public Squadra findByPrimaryKey(String nome) {
		Connection connection = this.dataSource.getConnection();
		Squadra squadra= null;
		try {
			PreparedStatement statement;
			String query = "select nome from squadra where nome = ?"; 
			statement = connection.prepareStatement(query);
			System.out.println(nome);
			statement.setString(1, nome);
			ResultSet result = statement.executeQuery();
			if (result.next()) 
				squadra=new Squadra(result.getString(1));
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new PersistenceException(e.getMessage());
			}
		}	
		return squadra;
	}

	@Override
	public List<Squadra> findAll() {
		Connection connection = this.dataSource.getConnection();
		List<Squadra> squadre = new LinkedList<>();
		try {
			
			PreparedStatement statement;
			String query = "select * from squadra";
			statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				String nome = result.getString(1);
				Squadra squadra = new Squadra(nome);
				squadre.add(squadra);
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
		return squadre;

	}

	@Override
	public void delete(Squadra squadra) {
		// TODO Auto-generated method stub
		
	}

}